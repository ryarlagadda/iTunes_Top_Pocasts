package com.example.raviteja.inclassassignment7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by RAVITEJA on 10/3/2016.
 */

public class GetDataAsyncTask extends AsyncTask<String,Void,ArrayList<Items>> {
    ProgressDialog progressDialog;
    MainActivity activity;
    ItemAdapter adapter;
    int flag=0;
    ScrollView scrollView;
    ArrayList<Items> ItemNameList = new ArrayList<>();
    ArrayList<Items> ItemNameList2 = new ArrayList<>();
    ArrayList<Items> ItemNameList3 = new ArrayList<>();

    public GetDataAsyncTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(10000);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading News...");
        progressDialog.show();
        super.onPreExecute();

    }

    @Override
    protected ArrayList<Items> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();

                return ItemsUtil.ItemPullParser.parseItem(in);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(final ArrayList<Items> items) {
        super.onPostExecute(items);
        progressDialog.dismiss();
        if (items != null) {
            Log.d("checking", items.get(1).toString());
        }

        ItemNameList = items;
        Log.d("chck", "checking");
        final String[] item_titles = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            item_titles[i] = items.get(i).getTitle();
        }
        Button search = (Button) activity.findViewById(R.id.Go_button);
        Button clear = (Button) activity.findViewById(R.id.Clear_button);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText) activity.findViewById(R.id.editText);
                adapter = new ItemAdapter(activity, R.layout.row_layout, ItemNameList);
                text.setText("");
                ListView listView = (ListView) activity.findViewById(R.id.listView);
                listView.setAdapter(adapter);
            }
        });
        final EditText searchText = (EditText) activity.findViewById(R.id.editText);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchText.getText() != null) {
                    for (int i = 0; i < ItemNameList.size(); i++) {
                        if (ItemNameList.get(i).getTitle().contains(searchText.getText())) {
                            ItemNameList2.add(ItemNameList.get(i));
                        }
                    }
                }
                Log.d("size2", Integer.toString(ItemNameList2.size()));
                Log.d("size1", Integer.toString(ItemNameList.size()));

              for(int i=0;i<ItemNameList.size();i++)
              {
                  if(ItemNameList2.contains(ItemNameList.get(i)))
                  {

                  }
                  else
                  {
                     ItemNameList2.add(ItemNameList.get(i));
                  }
              }

                for(int m=0;m<ItemNameList2.size();m++){
                    Log.d("disp",ItemNameList2.get(m).getTitle());
                }

               // ItemNameList.add(ItemNameList2.size());
                adapter.setNotifyOnChange(true);
                adapter.notifyDataSetChanged();
                adapter = new ItemAdapter(activity, R.layout.row_layout, ItemNameList2);
                ListView listView = (ListView) activity.findViewById(R.id.listView);
                listView.setAdapter(adapter);
            }
        });
        progressDialog.dismiss();
        ListView listView = (ListView) activity.findViewById(R.id.listView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, item_titles);
        adapter = new ItemAdapter(activity, R.layout.row_layout, ItemNameList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("demo", "position" + i + ",value" + item_titles[i]);
                Intent intent = new Intent(activity, ItemDetailsActivity.class);
                Bundle bundle = new Bundle();
                for(int j=0;j<ItemNameList.size();j++)
                {
                    Log.d("inside loop item title",item_titles[i]);
                    Log.d("itemNamelist title", ItemNameList.get(j).getTitle());
                    if(item_titles[i].equals(ItemNameList.get(j).getTitle()))
                    {
                        bundle.putSerializable("ITEM_KEY",ItemNameList.get(j));
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                }

            }
        });

    }
}