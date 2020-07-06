package com.example.shopmart.di

import com.example.shopmart.util.PRODUCT
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage =
        FirebaseStorage.getInstance()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductReference