package com.example.capstone.data.api.retrofit

import com.example.capstone.data.api.response.AllArticleResponse
import com.example.capstone.data.api.response.AllFranchiseResponse
import com.example.capstone.data.api.response.DetailArticleResponse
import com.example.capstone.data.api.response.DetailFranchiseResponse
import com.example.capstone.data.api.response.LoginResponse
import com.example.capstone.data.api.response.ProfileImageResponse
import com.example.capstone.data.api.response.ProfileResponse
import com.example.capstone.data.api.response.RegisterResponse
import retrofit2.Call
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
    @POST("/uploads/profile/:userId")
    suspend fun uploadImage(
        @Field("verifyPassword") verpassword: String
    ): ProfileImageResponse


    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse


    @GET("articles")
    suspend fun getArticles(
        @Header("Authorization") token: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 10
    ): AllArticleResponse

    @GET("articles/{id}")
    suspend fun getArticlesById(
        @Path("id") articleId: String
    ): DetailArticleResponse



//    @GET("franchises")
//    suspend fun getFranchises(
//        @Header("Authorization") token: String,
//    ) : List<FranchiseResponseItem>

    @GET("franchises")
    suspend fun getFranchises(
        @Header("Authorization") token : String,
        @Query("page") page : Int = 1,
        @Query("size") size : Int = 10
    ) : AllFranchiseResponse


    @GET("franchises/{id}")
     fun getDetailFranchise(
        @Header("Authorization") token : String,
        @Path("id") id : String
    ) : Call <DetailFranchiseResponse>

}