package com.example.shopmart.exception

class NoAccount: Exception() {
    override val message: String?
        get() = "No account found"
}