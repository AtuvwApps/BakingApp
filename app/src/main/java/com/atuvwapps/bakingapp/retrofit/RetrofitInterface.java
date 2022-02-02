package com.atuvwapps.bakingapp.retrofit;

import com.atuvwapps.bakingapp.data.Recipe;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("{path}")
    Call<List<Recipe>> getRecipes(
            @Path("path") String path
    );
}
