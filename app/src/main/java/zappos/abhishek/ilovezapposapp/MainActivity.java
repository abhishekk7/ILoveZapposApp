package zappos.abhishek.ilovezapposapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zappos.abhishek.ilovezapposapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "https://api.zappos.com";
    public static final String KEY = "b743e26728e16b81da139182bb2094357c31d331";

    SearchResult searchResultBase;
    ActivityMainBinding binding;
    SearchView productSearchView;
    ListView productListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        productSearchView = (SearchView) findViewById(R.id.productSearchView);

        productSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
                searchResultBase = response.body();
                binding.setSearchResult(searchResultBase);

                productListView = (ListView) findViewById(R.id.productListView);
                AdapterProduct adapterProduct = new AdapterProduct(getApplicationContext(), searchResultBase.getResults());
                productListView.setAdapter(adapterProduct);

                productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Result result = (Result) productListView.getAdapter().getItem(position);
                        Toast.makeText(getApplicationContext(), result.getBrandName() + position, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(), ProductActivity.class);
                        intent.putExtra("product", result);
                        startActivity(intent);
                    }
                });

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
