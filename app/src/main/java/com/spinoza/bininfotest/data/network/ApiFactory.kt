package com.spinoza.bininfotest.data.network

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ApiFactory {
    private const val BASE_URL = "https://lookup.binlist.net/"
    // fix: "CertPathValidatorException: Trust anchor for certificate path not found"
    // for old android versions:
    // https://stackoverflow.com/questions/29273387/certpathvalidatorexception-trust-anchor-for-certificate-path-not-found-retro
    private val retrofit = if (android.os.Build.VERSION.SDK_INT < 26) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    } else {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @JvmField
    val apiService: ApiService = retrofit.create(ApiService::class.java)

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                @SuppressLint("CustomX509TrustManager")
                object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String,
                    ) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String,
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
