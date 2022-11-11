package org.catApi.api;

import com.google.gson.Gson;
import okhttp3.*;
import org.catApi.enums.ApiKey;
import org.catApi.model.CatModel;
import org.catApi.model.FavoriteCatModel;

import java.io.IOException;

public class CatApi {

    /**
     *Descripción: Método que hace una petición GET a la api pública Thecarapi.com
     * que devuelve fotos random de michis
     *
     * @return cat;
     */
    public static CatModel getApiCat() {

        try{
            //Creamos una petición GET a la api pública Thecarapi.com
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/images/search")
                    .get()
                    .build();
            //Se ejecuta el GET
            Response response = client.newCall(request).execute();

            //Convertimos la respuesta de JSON a String
            //Ejemplo: [{"id": "MjAxOTAzNA","url": "https://cdn2.thecatapi.com/images/MjAxOTAzNA.jpg","width": 715,"height": 477}]
            String json = response.body().string();
            //Eliminamos los corchetes de inicio [ y fin ] para poder hacer la conversión a un objeto
            json = json.substring(1, json.length()-1);

            //Conversión de String a objeto Cat
            Gson gson = new Gson();
            CatModel catModel = gson.fromJson(json, CatModel.class);

            return catModel;
        }catch (IOException ioException){
            System.out.println(ioException);
        }

        return null;
    }

    /**
     * Descripción: Método que hace una petición POST a la api pública Thecarapi.com
     * para agregar una foto a favoritos
     *
     * @param catId
     *
     * @return boolean
     */
    public static boolean postApiAddFavoriteCat(String catId){
        try {

            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"image_id\":\""+catId+"\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("x-api-key", ApiKey.API_KEY.getKey())
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            return true;
        }catch (IOException ioException){
            System.out.println(ioException);
        }
        return false;
    }

    /**
     * Descripción: Método que que hace una petición POST a la api pública Thecarapi.com
     * y devuelve un array de objetos favoriteCats
     *
     * @return favoriteCats
     */
    public static FavoriteCatModel[] getApiFavoriteCats(){

        try{
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .get()
                    .addHeader("x-api-key", ApiKey.API_KEY.getKey())
                    .build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            Gson gson = new Gson();
            FavoriteCatModel[] favoriteCatModels = gson.fromJson(json, FavoriteCatModel[].class);

            return favoriteCatModels;

        }catch (IOException ioException){
            System.out.println(ioException);
        }
        return null;
    }

    public static boolean deleteApiFavoriteCat(String id){
        try{
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            //MediaType mediaType = MediaType.parse("application/json");
            //RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/"+id+"")
                    .delete(null)
                    .addHeader("x-api-key", ApiKey.API_KEY.getKey())
                    //.addHeader("content-type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            return true;
        }catch (IOException ioException){
            System.out.println(ioException);
        }
        return false;
    }
}
