package com.solu4b.nrafalow.popularmoviesstage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/* I check this example to understand how to add things to a gridlayout dynamically.
 * My implementation is based on this:
 * http://javatechig.com/android/android-gridview-example-building-image-gallery-in-android
 */

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String baseUrl = "http://image.tmdb.org/t/p/w342/";

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = inflater.inflate(R.layout.movie, null);
        ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

        String path = baseUrl + data.get(position);
        Picasso.with(context).load(path).into(imageView);

        return gridView;
    }
}