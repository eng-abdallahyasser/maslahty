

import androidx.lifecycle.ViewModel
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraft
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraftStore
import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.vehicle.entity.VehicleCondition
import com.abdallahyasser.maslahty.presentation.vechicle.VehicleDetails.VehicleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

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
    // ✅ هذه الدالة الجديدة تحفظ البيانات
    fun saveVehicleDataAndNavigate(licensePlate: String, onNavigate: () -> Unit) {
        val state = _uiState.value

        // استيراد الـ imports
        val vehicle = Vehicle(
            id = licensePlate,
            ownerId = "user1",
            licensePlate = licensePlate,
            chassisNumber = state.chassisNumber,
            engineNumber = state.engineNumber,
            model = "Unknown",
            manufacturingYear = 2024,
            color = "Unknown",
            kilometers = 0,
            condition = VehicleCondition.GOOD,
            licenseImageUrl = null,
            vehicleImageUrl = null,
            chassisImageUrl = null,
            engineImageUrl = null,
            createdAt = Date(),
            updatedAt = Date()
        )

        val draft = TransferDraft(
            vehicle = vehicle,
            salePrice = null,
            marketPrice = null,
            notes = "",
            buyerNationalId = state.newOwnerNationalId
        )

        // حفظ في TransferDraftStore
        TransferDraftStore.drafts[licensePlate] = draft

        onNavigate()
    }

}