package com.example.shopmart.di

import com.example.shopmart.util.PRODUCT
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Provides
    @ProductReference
    fun provideProductReference(firebaseFirestore: FirebaseFirestore): CollectionReference =
        firebaseFirestore.collection(PRODUCT)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductReference