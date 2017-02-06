package zappos.abhishek.ilovezapposapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abhis on 2/5/2017.
 */

public interface RESTInterface {
    @GET("Search")
    Call<SearchResult> getProducts(
            @Query("term") String term,
            @Query("key") String key);
}
