package br.com.fiap.onghub.ui.home.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.onghub.api.OngsRepository
import br.com.fiap.onghub.api.response.ONG
import br.com.fiap.onghub.screens.Category
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val addressInput: String = "",
    val city: String? = null,
    val state: String? = null,
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val items: List<ONG> = emptyList(),
    val page: Int = 1,
    val hasMore: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val repo: OngsRepository = OngsRepository()
) : ViewModel() {

    private val _ui = MutableStateFlow(HomeUiState())
    val ui: StateFlow<HomeUiState> = _ui

    private var loadJob: Job? = null

    init {
        val cats = repo.categories()
        _ui.value = _ui.value.copy(categories = cats)
        refresh()
    }

    fun onAddressChange(value: String) {
        _ui.value = _ui.value.copy(addressInput = value)
    }

    fun applyAddress() {
        val (c, s) = parseCityState(_ui.value.addressInput)
        _ui.value = _ui.value.copy(city = c, state = s)
        refresh()
    }

    fun toggleCategory(cat: Category?) {
        val next = if (_ui.value.selectedCategory == cat) null else cat
        _ui.value = _ui.value.copy(selectedCategory = next)
        refresh()
    }

    fun refresh() {
        loadJob?.cancel()
        _ui.value = _ui.value.copy(page = 1, items = emptyList(), hasMore = true, error = null)
        loadNext()
    }

    fun loadNext() {
        val st = _ui.value
        if (st.isLoading || !st.hasMore) return
        loadJob = viewModelScope.launch {
            _ui.value = st.copy(isLoading = true, error = null)
            runCatching {
                repo.fetchOngs(
                    city = st.city,
                    state = st.state,
                    category = st.selectedCategory?.label,
                    page = st.page,
                    limit = 10
                )
            }.onSuccess { resp ->
                _ui.value = st.copy(
                    items = st.items + resp.data,
                    page = (resp.pagination?.current_page ?: st.page) + 1,
                    hasMore = resp.pagination?.has_next_page ?: false,
                    isLoading = false
                )
            }.onFailure { e ->
                _ui.value = st.copy(isLoading = false, error = e.message ?: "Erro ao carregar")
            }
        }
    }

    private fun parseCityState(input: String): Pair<String?, String?> {
        val parts = input.split("-").map { it.trim() }.filter { it.isNotEmpty() }
        return when (parts.size) {
            2 -> parts[0] to parts[1]
            1 -> parts[0] to null
            else -> null to null
        }
    }
}