import com.abdallahyasser.maslahty.data.models.PriceWarningDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferRequestDto(
    @SerialName("id")
    val id: String,

    @SerialName("vehicle_id")
    val vehicleId: String,

    @SerialName("seller_id")
    val sellerId: String,

    @SerialName("buyer_id")
    val buyerId: String,

    @SerialName("price")
    val price: Double,

    @SerialName("status")
    val status: String,

    @SerialName("seller_name")
    val sellerName: String,

    @SerialName("buyer_name")
    val buyerName: String,

    @SerialName("created_at")
    val createdAt: Long,

    @SerialName("updated_at")
    val updatedAt: Long,

    @SerialName("notes")
    val notes: String = "",

    @SerialName("price_warning")
    val priceWarning: PriceWarningDto? = null
)