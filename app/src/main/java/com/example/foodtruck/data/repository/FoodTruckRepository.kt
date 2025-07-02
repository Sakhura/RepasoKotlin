import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import getItemsByCategory
import getCategorias
import getCartaItem
import getCartaItemCount

//MÉTODOS Menu 		pedidos 		order 		auxiliar* 

//todo del menu
fun getAllAvailableItems(): Flow<List<FoodItem>> =dao.getAllAvailableItems()

//item categoria
fun getItemsByCategory(category:String): Flow<List<FoodItem>> = dao.getItemsByCategory()

//item todas las catagorias

fun getCategorias(): Flow<List<String>> = dao.getCategorias()

//pedidos CartaItem
fun getCartaItem(): Flow<List<String>> = dao.getCartaItem()

//conteo por articulo 
fun getCartaItemCount(): Flow<List<String>> = dao.getCartaItemCount()

