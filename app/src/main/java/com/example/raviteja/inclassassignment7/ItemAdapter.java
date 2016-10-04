package com.example.raviteja.inclassassignment7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RAVITEJA on 10/3/2016.
 */
public class ItemAdapter extends ArrayAdapter<Items> {

    List<Items> itemList;
    Context mContext;
    int mResource;
    ImageView imageView;


    public ItemAdapter(Context context, int resource, List<Items> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
        this.itemList=objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }
        Items item = itemList.get(position);
        imageView = (ImageView) convertView.findViewById(R.id.imageView_logo);
        TextView textView_itemtitle = (TextView) convertView.findViewById(R.id.title_text);
        textView_itemtitle.setText(item.getTitle());

        Picasso.with(mContext).load(item.getLargeImageUrl()).into(imageView);
        return convertView;
    }
}
