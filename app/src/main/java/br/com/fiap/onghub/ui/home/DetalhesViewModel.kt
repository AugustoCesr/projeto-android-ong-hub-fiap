package br.com.fiap.onghub.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.onghub.api.OngsRepository
import br.com.fiap.onghub.api.response.ONG
import androidx.compose.runtime.getValue
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch

sealed interface DetalheUiState {
    data object Loading : DetalheUiState
    data object Empty : DetalheUiState
    data class Error(val message: String) : DetalheUiState
    data class Success(val ong: ONG) : DetalheUiState
}

class DetalheViewModel(
    private val repo: OngsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val ongId: String = checkNotNull(savedStateHandle["ongId"])

    var uiState by mutableStateOf<DetalheUiState>(DetalheUiState.Loading)
        private set

    init { load() }

    fun load() {
        viewModelScope.launch {
            uiState = DetalheUiState.Loading
            runCatching { repo.fetchOngById(ongId) }
                .onSuccess { ong ->
                    uiState = if (ong != null) DetalheUiState.Success(ong) else DetalheUiState.Empty
                }
                .onFailure { e ->
                    uiState = DetalheUiState.Error(e.message ?: "Erro")
                }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val handle = createSavedStateHandle()
                DetalheViewModel(
                    repo = OngsRepository(),
                    savedStateHandle = handle
                )
            }
        }
    }
}