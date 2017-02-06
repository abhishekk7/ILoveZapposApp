package zappos.abhishek.ilovezapposapp;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import zappos.abhishek.ilovezapposapp.databinding.ActivityProductBinding;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;
    ImageLoadTask imageLoadTask;
    ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        productImage = (ImageView) findViewById(R.id.product_page_image);
        imageLoadTask = new ImageLoadTask();
        Bitmap productImageBm = null;
        Result product = (Result) getIntent().getSerializableExtra("product");

        try {
            productImageBm = imageLoadTask.execute(product.getThumbnailImageUrl()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        binding.setProduct(product);
        productImage.setImageBitmap(productImageBm);

        Toast.makeText(getApplicationContext(), product.getBrandName(), Toast.LENGTH_LONG).show();
    }
}
