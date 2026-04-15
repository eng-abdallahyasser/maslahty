

import androidx.lifecycle.ViewModel
import com.abdallahyasser.maslahty.presentaion.screens.vechicle.VehicleDetails.VehicleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VehicleDetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(VehicleState.VehicleIdentificationState())
    var uiState : StateFlow<VehicleState.VehicleIdentificationState> = _uiState.asStateFlow()


    fun onLicensePlateChange(value: String) {
        _uiState.value = _uiState.value.copy(licensePlate = value.uppercase())
    }

    fun onChassisNumberChange(value: String) {
        _uiState.value = _uiState.value.copy(chassisNumber = value.uppercase())
    }

    fun onEngineNumberChange(value: String) {
        _uiState.value = _uiState.value.copy(engineNumber = value.uppercase())
    }

    fun onNewOwnerNationalIdChange(value: String) {
        if (value.length <= 14 && value.all { it.isDigit() }) {
            _uiState.value = _uiState.value.copy(newOwnerNationalId = value)
        }
    }

    fun setError(message: String?) {
        _uiState.value = _uiState.value.copy(error = message)
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}