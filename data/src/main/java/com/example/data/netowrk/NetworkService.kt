package com.example.data.netowrk

import com.example.data.datamodels.NetworkResponse
import com.google.gson.Gson

class NetworkService(
    private val movieApi: MovieApi
){
    private val gson by lazy {
        Gson()
    }
    internal suspend inline fun<reified T> makeGetRequest(url : String) : NetworkResponse<T>{
        val response = movieApi.getRequest(url)
        if(response.isSuccessful){
            val jsonResponse = response.body()?.string()
            return convertToDataModel<T>(jsonResponse)
        }else{
            return NetworkResponse.Error(response.errorBody()?.toString()?:"")
        }
    }

    private  inline fun<reified T> convertToDataModel(jsonString: String?): NetworkResponse<T> {
        try {
            jsonString?.let {
                val json = it
                if(T::class == String::class){
                    return NetworkResponse.Success(json as T)
                }else{
                    val datamodel : T  = gson.fromJson(json,T::class.java)
                    return NetworkResponse.Success(datamodel)
                }
            }?:return NetworkResponse.Error("Invalid Response")

        }catch (e: Exception){
            return NetworkResponse.Error("Gson Parsing Error ${e.message}")
        }
    }
}