package com.example.pdm_pet.data.remote

import com.example.pdm_pet.utils.UserSession
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://patas-api.despesai.com.br/"

    fun animalImageUrl(photoName: String?): String? =
        photoName?.takeIf { it.isNotBlank() }?.let { "${BASE_URL}animalprofile/image/$it" }

    fun userImageUrl(photoName: String?): String? =
        photoName?.takeIf { it.isNotBlank() }?.let { "${BASE_URL}user/image/$it" }

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val original = chain.request()
        val token = UserSession.currentUser?.token

        // Log para debug
        if (token != null) {
            println("TOKEN ENVIADO: ${token.take(10)}...")
        } else {
            println("ALERTA: Token nulo ou usuário não logado.")
        }

        val requestBuilder = original.newBuilder()
        if (!token.isNullOrBlank()) {
            requestBuilder.header("Authorization", "Bearer $token")
        }

        val request = requestBuilder.build()
        chain.proceed(request)
    }.build()

    val api: PatasUnidasApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PatasUnidasApi::class.java)
    }
}