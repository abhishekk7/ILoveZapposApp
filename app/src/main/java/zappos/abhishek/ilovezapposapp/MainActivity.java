package zappos.abhishek.ilovezapposapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zappos.abhishek.ilovezapposapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    SearchResult searchResultBase;
    ActivityMainBinding binding;
    public static final String URL = "https://api.zappos.com";
    public static final String KEY = "b743e26728e16b81da139182bb2094357c31d331";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }

    private void fetchData(String term) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RESTInterface rest = retrofit.create(RESTInterface.class);
        Call<SearchResult> call = rest.getProducts(term, KEY);
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                int statusCode = response.code();
                searchResultBase = response.body();
                binding.setSearchResult(searchResultBase);
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                searchResultBase = new SearchResult();
                searchResultBase.setOriginalTerm("fail");
                binding.setSearchResult(searchResultBase);
            }
        });
    }
}