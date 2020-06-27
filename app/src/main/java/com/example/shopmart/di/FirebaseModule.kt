package com.example.shopmart.di

import com.example.shopmart.util.ACCOUNT
import com.example.shopmart.util.CART
import com.example.shopmart.util.PRODUCT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseUser(firebaseAuth: FirebaseAuth): FirebaseUser? =
        firebaseAuth.currentUser

    @Provides
    @ProductReference
    fun provideProductReference(firebaseFirestore: FirebaseFirestore): CollectionReference =
        firebaseFirestore.collection(PRODUCT)

    @Provides
    @CartReference
    fun provideCartReference(
        firebaseFirestore: FirebaseFirestore,
        firebaseUser: FirebaseUser?
    ): CollectionReference? =
        firebaseUser?.let {
            firebaseFirestore.collection(
                String.format("%s/%s/%s", ACCOUNT, it.uid, CART)
            )
        }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CartReference