import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ordenes")

data class Order(
    @PrimaryKey
    val id: String,
    val nombre_comensal: String,
    val cel_comensal: String,
    val item : String,
    val montototal: Double


) Parcelable