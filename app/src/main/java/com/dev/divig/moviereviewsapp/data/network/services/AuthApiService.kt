package com.dev.divig.moviereviewsapp.data.network.services

import com.dev.divig.moviereviewsapp.BuildConfig
import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import java.util.concurrent.TimeUnit

interface AuthApiService {
    @POST("auth/login")
    suspend fun postLoginUser(@Body loginRequest: AuthRequest): BaseAuthResponse<UserData, String>

    @POST("auth/register")
    suspend fun postRegisterUser(@Body registerRequest: AuthRequest): BaseAuthResponse<UserData, String>

    @GET("auth/me")
    suspend fun getSyncUser(): BaseAuthResponse<UserData, String>

    @GET("users")
    suspend fun getUserData(): BaseAuthResponse<UserData, String>

    @PUT("users")
    suspend fun putUserData(@Body data: RequestBody): BaseAuthResponse<UserData, String>


    companion object {
        @JvmStatic
        operator fun invoke(localDataSource: LocalDataSource): AuthApiService {
            val authInterceptor = Interceptor {
                val requestBuilder = it.request().newBuilder()
                localDataSource.authToken(null)?.let { token ->
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                it.proceed(requestBuilder.build())
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.base_url_binar)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(AuthApiService::class.java)
        }
    }
}