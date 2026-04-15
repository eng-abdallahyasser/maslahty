package com.abdallahyasser.maslahty.presentaion.screens.vechicle.ImageUpload

data class ImageUploadState(
    val licenseImageUrl: String = "",
    val vehicleImageUrl: String = "",
    val chassisImageUrl: String = "",
    val engineImageUrl: String = "",
    val error: String? = null,
    val isLoading: Boolean = false
) {
    // حساب عدد الصور المرفوعة تلقائياً داخل الـ State
    /*"ده مش Business Logic، ده UI Derived State (حالة مشتقة).
     وضعتها هنا عشان أضمن الـ Consistency (التطابق) بين واجهة المستخدم ومنطق التحقق،
     وعشان أحافظ على الـ ViewModel نحيف (Lean ViewModel)."
     */
    val uploadedCount: Int
        get() = listOf(licenseImageUrl, vehicleImageUrl, chassisImageUrl, engineImageUrl)
            .count { it.isNotBlank() }
}

