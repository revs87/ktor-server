package pt.rvcoding.domain.models

import kotlinx.serialization.Serializable
import pt.rvcoding.domain.models.CustomerService.Companion.DefaultCustomerServices

@Serializable
data class CustomerCategory(
    val code: Int,
    val name: String,
    val services: List<CustomerService>
) {
    companion object {
        val DefaultCustomerCategories by lazy {
            listOf(
                CustomerCategory(1, "Nails", DefaultCustomerServices),
            )
        }
    }
}

@Serializable
data class CustomerService(
    val code: Int,
    val description: String,
    val price: Double,
    val salesPrice: Double? = null,
    val currency: String
) {
    companion object {
        val DefaultCustomerServices by lazy {
            listOf(
                CustomerService(1, "Maintenance Size S", 20.0, 20.0, "EUR"),
                CustomerService(2, "Maintenance Size M", 23.0, 23.0, "EUR"),
            )
        }
    }
}
