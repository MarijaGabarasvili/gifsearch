package gifsearch2.com

import retrofit2.http.GET

interface Service {

    @GET("gifs/trending?api_key=D9GgEgEZN2GLaTIPVsFIbt6lSuVCIRMM")
    fun getGif(): retrofit2.Call<DataResult>
}