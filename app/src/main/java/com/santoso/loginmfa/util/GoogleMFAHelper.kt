package com.santoso.loginmfa.util

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base32
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Hex
import de.taimos.totp.TOTP
import java.security.SecureRandom

object GoogleMFAHelper {
    fun generateSecret(): String {
        val buffer = ByteArray(10)
        SecureRandom().nextBytes(buffer)
        return Base32().encodeToString(buffer).replace("=", "")
    }

    fun getOtpAuthUrl(user: String, issuer: String, secret: String): String {
        return "otpauth://totp/$issuer:$user?secret=$secret&issuer=$issuer"
    }

    private fun getTOTPCode(secretKey: String): String {
        val bytes = Base32().decode(secretKey)
        val hexKey = Hex.encodeHexString(bytes)
        return TOTP.getOTP(hexKey)
    }

    fun verifyCode(secretKey: String, code: String): Boolean {
        return getTOTPCode(secretKey) == code
    }
}