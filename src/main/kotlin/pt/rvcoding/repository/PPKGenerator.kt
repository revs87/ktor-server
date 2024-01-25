package pt.rvcoding.repository

import io.ktor.utils.io.core.*
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.text.toByteArray

class PPKGenerator {

    lateinit var privateKey: PrivateKey
    lateinit var publicKey: PublicKey

    init {
        generateNewKeyPair()
    }

    fun generateNewKeyPair() {
        // Generate key pair
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val keyPair: KeyPair = keyPairGenerator.generateKeyPair()

        // Extract public and private keys from the key pair
        publicKey = keyPair.public
        privateKey = keyPair.private

//        publicKey = convertStringToPublicKey(publicKeyAsString())
//        privateKey = convertStringToPrivateKey(privateKey.encoded.decodeToString())

//        // Text to be encrypted
//        val plaintext = "Hello, this is a secret message!"
//
//        // Encryption
//        val encryptedText = encrypt(plaintext)
//        println("Encrypted Text: $encryptedText")
//
//        // Decryption
//        val decryptedText = decrypt(encryptedText)
//        println("Decrypted Text: $decryptedText")
    }

    fun publicKeyAsString(): String = publicKey.encoded.decodeToString()

    private fun convertStringToPrivateKey(keyString: String): PrivateKey {
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(Base64.getDecoder().decode(keyString))
        return keyFactory.generatePrivate(keySpec)
    }

    private fun convertStringToPublicKey(keyString: String): PublicKey {
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(Base64.getDecoder().decode(keyString))
        return keyFactory.generatePublic(keySpec)
    }

    fun encrypt(input: String): String {
        // Generate a random symmetric key
        val symmetricKey = generateSymmetricKey()

        // Encrypt the plaintext with the symmetric key using a symmetric encryption algorithm (AES in this case)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, symmetricKey)
        val encryptedBytes = cipher.doFinal(input.toByteArray())

        // Encrypt the symmetric key with the RSA public key
        val rsaCipher = Cipher.getInstance("RSA")
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedSymmetricKey = rsaCipher.doFinal(symmetricKey.encoded)

        // Combine the encrypted symmetric key and the encrypted data
        val combinedBytes = encryptedSymmetricKey + encryptedBytes

        // Base64 encode the combined bytes
        return Base64.getEncoder().encodeToString(combinedBytes)
    }

    fun decrypt(input: String): String {
        // Decode the input from Base64
        val combinedBytes = Base64.getDecoder().decode(input)

        // Extract the encrypted symmetric key and data
        val encryptedSymmetricKey = combinedBytes.copyOfRange(0, 256) // Assuming a 2048-bit key, which is 256 bytes
        val encryptedData = combinedBytes.copyOfRange(256, combinedBytes.size)

        // Decrypt the symmetric key with the RSA private key
        val rsaCipher = Cipher.getInstance("RSA")
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decryptedSymmetricKey = rsaCipher.doFinal(encryptedSymmetricKey)

        // Reconstruct the SecretKey from the decrypted key material
        val reconstructedSymmetricKey = SecretKeySpec(decryptedSymmetricKey, "AES")

        // Decrypt the data with the reconstructed symmetric key using a symmetric encryption algorithm (AES in this case)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, reconstructedSymmetricKey)
        val decryptedBytes = cipher.doFinal(encryptedData)

        // Convert the decrypted bytes back to a string
        return String(decryptedBytes)
    }

    private fun generateSymmetricKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256) // Use a key size suitable for your needs
        return keyGenerator.generateKey()
    }
}

fun ByteArray.decodeToString(): String = Base64
    .getEncoder()
    .encodeToString(this)
//fun ByteArray.print2(): String = this.joinToString(separator = ", ") { byte -> String.format("%02X", byte) }
