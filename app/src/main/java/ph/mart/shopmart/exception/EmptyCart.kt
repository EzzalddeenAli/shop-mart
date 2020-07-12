package ph.mart.shopmart.exception

class EmptyCart : Exception() {
    override val message: String?
        get() = "Empty Cart"
}