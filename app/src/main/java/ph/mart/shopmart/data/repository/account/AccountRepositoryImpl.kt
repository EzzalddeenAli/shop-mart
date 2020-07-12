package ph.mart.shopmart.data.repository.account

import android.content.SharedPreferences
import ph.mart.shopmart.util.IS_LOGGED_IN
import ph.mart.shopmart.util.getFirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AccountRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AccountRepository {

    override suspend fun createAccount(email: String, password: String) {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<Unit> { continuation ->
                getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { continuation.resume(Unit) }
                    .addOnFailureListener { continuation.resumeWithException(it) }
            }
        }
    }

    override suspend fun loginAccount(email: String, password: String) {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<Unit> { continuation ->
                getFirebaseAuth().signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        saveAccount()
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener { continuation.resumeWithException(it) }
            }
        }
    }

    override suspend fun firebaseAuthWithGoogle(idToken: String?) {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<Unit> { continuation ->
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                getFirebaseAuth().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveAccount()
                            continuation.resume(Unit)
                        } else {
                            task.exception?.cause?.let { continuation.resumeWithException(it) }
                            Timber.d("signInWithCredential:failure ${task.exception}")
                        }
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
        }
    }

    private fun saveAccount() {
        with(sharedPreferences.edit()) {
            putBoolean(IS_LOGGED_IN, true)
            apply()
        }
    }

    override fun signOut() {
        getFirebaseAuth().signOut()
        with(sharedPreferences.edit()) {
            remove(IS_LOGGED_IN)
            apply()
        }
    }

    override fun getCurrentUser(): FirebaseUser? =
        getFirebaseAuth().currentUser

    override fun isLoggedIn(): Boolean =
        if (sharedPreferences.getBoolean(IS_LOGGED_IN, false) && getCurrentUser() != null) {
            true
        } else {
            signOut()
            false
        }

}