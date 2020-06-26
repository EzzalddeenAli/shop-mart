package com.example.shopmart.di

import com.example.shopmart.data.repository.ProductRepository
import com.example.shopmart.data.repository.ProductRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
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
    fun provideProductRepository(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth): ProductRepository =
        ProductRepositoryImpl(firebaseFirestore, firebaseAuth)

}