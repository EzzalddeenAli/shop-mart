package ph.mart.shopmart.exception

class NoAccount: Exception() {
    override val message: String?
        get() = "No account found"
}