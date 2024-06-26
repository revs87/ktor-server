package pt.rvcoding.domain

class Configuration {
    companion object {
        val COMPANY_ID: String by lazy { "rvc" }

        const val PKK_ENCRYPTION_IS_ACTIVE: Boolean = true
        const val TOKEN_IS_ACTIVE: Boolean = true

        const val EXPIRATION_TIME: Long = 86400000L
        const val TIMEOUT: Long = 60000L
    }
}