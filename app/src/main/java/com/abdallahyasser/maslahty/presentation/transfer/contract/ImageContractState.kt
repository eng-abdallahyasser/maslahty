package com.abdallahyasser.maslahty.presentation.transfer.contract

data class ImageContractState(


    val imageContract: String = "",
    val error: String? = null,
    val isLoading: Boolean = false
) {
    // حساب عدد الصور المرفوعة تلقائياً داخل الـ State
    /*"ده مش Business Logic، ده UI Derived State (حالة مشتقة).
     وضعتها هنا عشان أضمن الـ Consistency (التطابق) بين واجهة المستخدم ومنطق التحقق،
     وعشان أحافظ على الـ ViewModel نحيف (Lean ViewModel)."
     */
    val uploadedCount: Int
        get() = listOf(imageContract)
            .count { it.isNotBlank() }
}



