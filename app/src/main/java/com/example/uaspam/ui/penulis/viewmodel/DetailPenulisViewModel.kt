package com.example.uaspam.ui.penulis.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Penulis
import com.example.uaspam.repository.PenulisRepository
import com.example.uaspam.ui.penulis.view.DestinasiDetPenulis
import kotlinx.coroutines.launch

class DetailPenulisViewModel (
        savedStateHandle: SavedStateHandle,
        private val penulisRepository: PenulisRepository
    ) : ViewModel() {
        private val idPenulis: String = checkNotNull(savedStateHandle[DestinasiDetPenulis.IDPENULIS.toString()])

        var detailUiState: DetailUiState by mutableStateOf(DetailUiState())
            private set

        init {
            getPenulisbyId()
        }

        private fun getPenulisbyId() {
            viewModelScope.launch {
                detailUiState = DetailUiState(isLoading = true)
                try {
                    val result = penulisRepository.getPenulisbyId(idPenulis)
                    detailUiState = DetailUiState(
                        detailUiEvent = result.toDetailUiEvent(),
                        isLoading = false
                    )
                } catch (e: Exception) {
                    detailUiState = DetailUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = e.message ?: "Unknown"
                    )
                }
            }
        }

        fun deletePenulis() {
            viewModelScope.launch {
                detailUiState = DetailUiState(isLoading = true)
                try {
                    penulisRepository.deletePenulis(idPenulis)

                    detailUiState = DetailUiState(isLoading = false)
                } catch (e: Exception) {
                    detailUiState = DetailUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = e.message ?: "Unknown Error"
                    )
                }
            }
        }
    }

    data class DetailUiState(
        val detailUiEvent: InsertUiEvent = InsertUiEvent(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String = ""
    ){
        val isUiEventEmpty: Boolean
            get() = detailUiEvent == InsertUiEvent()

        val isUiEventNotEmpty: Boolean
            get() = detailUiEvent != InsertUiEvent()
    }

    fun Penulis.toDetailUiEvent(): InsertUiEvent {
        return InsertUiEvent(
            idPenulis = idPenulis,
            namaPenulis = namaPenulis,
            biografi = biografi,
            kontak = kontak
        )
    }

    data class PenulisUiEvent(
        val idPenulis : String = "",
        val namaPenulis : String = "",
        val biografi : String = "",
        val kontak : String = ""
    )
