import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "food_items")

data class FoodItem(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val descripcion : String,
    val precio: Double,
    val categoria: String,
    val imagenUrl: "",

) : Parcelable