package ph.mart.home.di

import com.google.firebase.firestore.CollectionReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewWithFragmentComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ph.mart.home.data.repository.product.ProductRepository
import ph.mart.home.data.repository.product.ProductRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewWithFragmentComponent::class)
object HomeModule {

    @Singleton
    @Provides
    fun provideString(): String = "sdfadf"

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideProductRepository(
//        @ProductReference productReference: CollectionReference
    ): ProductRepository =
        ProductRepositoryImpl()


}