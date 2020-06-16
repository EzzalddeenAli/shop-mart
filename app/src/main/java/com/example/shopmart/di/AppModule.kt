package com.example.shopmart.di

import com.example.shopmart.data.repository.ProductRepository
import com.example.shopmart.data.repository.ProductRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideProductRepository(
        firebaseFirestore: FirebaseFirestore
    ): ProductRepository {
        return ProductRepositoryImpl(
            firebaseFirestore
        )
    }
}
