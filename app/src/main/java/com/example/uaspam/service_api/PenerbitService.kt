package com.example.uaspam.service_api

import com.example.uaspam.model.Penerbit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PenerbitService {
    @Headers(
        "Accept : application/json",
        "Content-Type : application/json",
    )

    @POST("penerbit")
    suspend fun insertPenerbit(@Body penerbit: Penerbit)

    @GET("penerbit")
    suspend fun getAllPenerbit(): List<Penerbit>

    @GET("penerbit/:id")
    suspend fun getPenerbitbyId(@Query("id") id:String): Penerbit

    @PUT("penerbit/:id")
    suspend fun updatePenerbit(@Query("id") id:String, @Body penerbit: Penerbit)

    @DELETE("penerbit/:id")
    suspend fun deletePenerbit(@Query("id") id:String): Response<Void>
}