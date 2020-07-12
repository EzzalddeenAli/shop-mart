package ph.mart.shopmart.di

import android.content.SharedPreferences
import ph.mart.shopmart.data.repository.account.AccountRepository
import ph.mart.shopmart.data.repository.account.AccountRepositoryImpl
import ph.mart.shopmart.data.repository.cart.CartRepository
import ph.mart.shopmart.data.repository.cart.CartRepositoryImpl
import ph.mart.shopmart.data.repository.product.ProductRepository
import ph.mart.shopmart.data.repository.product.ProductRepositoryImpl
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideProductRepository(
        @ProductReference productReference: CollectionReference
    ): ProductRepository =
        ProductRepositoryImpl(productReference)

    @Singleton
    @Provides
    fun provideCartRepository(
        @ProductReference productReference: CollectionReference,
        firebaseFirestore: FirebaseFirestore
    ): CartRepository =
        CartRepositoryImpl(firebaseFirestore, productReference)

    @Singleton
    @Provides
    fun provideAccountRepository(
        sharedPreferences: SharedPreferences
    ): AccountRepository =
        AccountRepositoryImpl(sharedPreferences)

}