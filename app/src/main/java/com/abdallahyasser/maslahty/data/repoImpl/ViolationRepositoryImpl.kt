package com.abdallahyasser.maslahty.data.repoImpl

import com.example.maslahty.domain.common.Result
import com.example.maslahty.domain.entities.Violation
import com.example.maslahty.domain.entities.ViolationStatus
import com.example.maslahty.domain.repositories.ViolationRepository
import java.util.Date

class ViolationRepositoryImpl : ViolationRepository {

	override suspend fun getVehicleViolations(vehicleId: String): Result<List<Violation>> {
		return try {



			val mockViolations = listOf(
				Violation(
					id = "V-001",
					vehicleId = vehicleId,
					violationType = "تجاوز السرعة",
					description = "تجاوز السرعة المسموح بها بـ 30 كم",
					date = Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000),
					amount = 500.0,
					status = ViolationStatus.UNPAID,
					location = "الكورنيش"
				),
				Violation(
					id = "V-002",
					vehicleId = vehicleId,
					violationType = "وقوف غير قانوني",
					description = "وقوف في منطقة محظورة",
					date = Date(System.currentTimeMillis() - 15L * 24 * 60 * 60 * 1000),
					amount = 300.0,
					status = ViolationStatus.PAID,
					location = "الدقي"
				),
				Violation(
					id = "V-003",
					vehicleId = vehicleId,
					violationType = "عدم ارتداء حزام الأمان",
					description = "لم يكن هناك حزام أمان",
					date = Date(System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000),
					amount = 250.0,
					status = ViolationStatus.UNPAID,
					location = "شارع النيل"
				)
			)
			Result.Success(mockViolations)
		} catch (e: Exception) {
			Result.Error(e)
		}
	}

	override suspend fun getViolationById(id: String): Result<Violation> {
		return try {
			Result.Success(
				Violation(
					id = id,
					vehicleId = "V-8822",
					violationType = "تجاوز السرعة",
					description = "تجاوز السرعة المسموح بها بـ 25 كم",
					date = Date(),
					amount = 450.0,
					status = ViolationStatus.UNPAID,
					location = "طريق الملك فهد"
				)
			)
		} catch (e: Exception) {
			Result.Error(e)
		}
	}

	override suspend fun hasUnpaidViolations(vehicleId: String): Result<Boolean> {
		return when (val result = getVehicleViolations(vehicleId)) {
			is Result.Success -> Result.Success(result.data.any { it.status == ViolationStatus.UNPAID })
			is Result.Error -> Result.Error(result.exception)
			is Result.Loading -> Result.Loading
		}
	}
}

