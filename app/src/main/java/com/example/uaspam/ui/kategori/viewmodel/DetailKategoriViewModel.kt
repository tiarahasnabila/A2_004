package com.example.uaspam.ui.kategori.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Kategori
import com.example.uaspam.repository.KategoriRepository
import com.example.uaspam.ui.kategori.view.DestinasiDKategori
import com.example.uaspam.ui.kategori.viewmodel.InsertUiEvent
import kotlinx.coroutines.launch

class DetailKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {
    private val idKategori: String = checkNotNull(savedStateHandle[DestinasiDKategori.IDKATEGORI.toString()])

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState())
        private set

    init {
        getKategoribyId()
    }

    private fun getKategoribyId() {
        viewModelScope.launch {
            detailUiState = DetailUiState(isLoading = true)
            try {
                val result = kategoriRepository.getKategoribyId(idKategori)
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

    fun deleteKategori() {
        viewModelScope.launch {
            detailUiState = DetailUiState(isLoading = true)
            try {
                kategoriRepository.deleteKategori(idKategori)

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


