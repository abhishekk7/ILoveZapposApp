package zappos.abhishek.ilovezapposapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Abhishek on 2/5/2017.
 */

public class AdapterProduct extends ArrayAdapter<Result> {
    public AdapterProduct(Context context, List<Result> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Result result = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_list_item, parent, false);
        }

        TextView productName = (TextView) convertView.findViewById(R.id.product_name);
        TextView brandName = (TextView) convertView.findViewById(R.id.brand_name);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        ImageView productImage = (ImageView) convertView.findViewById(R.id.product_image);

        productName.setText(result.getProductName());
        brandName.setText(result.getBrandName());
        price.setText(result.getPrice());

        ImageLoadTask imageDownloader = new ImageLoadTask();
        Bitmap productImageBm = null;
        try {
            productImageBm = imageDownloader.execute(result.getThumbnailImageUrl()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        productImage.setImageBitmap(productImageBm);
        return convertView;
    }
}
