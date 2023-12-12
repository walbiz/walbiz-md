package com.example.capstone.data.api.retrofit

import com.example.capstone.data.api.response.AllArticleResponse
import com.example.capstone.data.api.response.DetailArticleResponse
import com.example.capstone.data.api.response.LoginResponse
import com.example.capstone.data.api.response.ProfileResponse
import com.example.capstone.data.api.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @GET("users")
    suspend fun getUsersProfile(
        @Header("Authorization") token: String,
        @Path("id") storyId: String
    ): ProfileResponse

    @FormUrlEncoded
    @POST("users/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("verifyPassword") verpassword: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse


    @GET("articles")
    suspend fun getArticles(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): AllArticleResponse

    @FormUrlEncoded
    @GET("articles/{id}")
    suspend fun getArticlesById(
        @Path("id") storyId: String
    ): DetailArticleResponse
}