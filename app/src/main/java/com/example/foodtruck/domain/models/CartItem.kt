import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "carta_items")

data class CartaItem(
    @PrimaryKey(autogenerate = true)
    val id: Long = 0,
    val foodItemId: String,
    val cantidad: Int,
    val campoObservacaion : String =""
    
    ) : Parcelable

    