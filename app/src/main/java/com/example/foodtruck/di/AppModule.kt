import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FoodTruckDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            FoodTruckDatabase::class.java, 
            "foodtruck_database")
            .fallbackToDestructiveMigration()
            .build()
    }


}