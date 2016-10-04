package com.example.raviteja.inclassassignment7;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by RAVITEJA on 10/3/2016.
 */
public class ItemsUtil {
    static public class ItemPullParser{

        static ArrayList<Items> parseItem(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            ArrayList<Items> ItemList = new ArrayList<Items>();
            Items Item = null;
            int event = parser.getEventType();
            int h;
            HashMap<Integer,String> heightValues =new HashMap<>();
            int i=0;
            boolean flag = false;

            while(event !=XmlPullParser.END_DOCUMENT){
                switch(event) {
                    case XmlPullParser.START_TAG:

                        if (parser.getName().equals("entry")) {
                            Item = new Items();
                            flag = true;
                        }

                        if (parser.getName().equals("title") && flag == true) {
                            Item.setTitle(parser.nextText().trim());
                            Log.d("title", Item.getTitle());
                        } else if (parser.getName().equals("im:releaseDate") && flag == true) {
                            Item.setReleaseDate(parser.nextText().trim());
                            Log.d("date", Item.getReleaseDate());
                        } else if (parser.getName().equals("summary") && flag == true) {
                            Item.setSummary(parser.nextText().trim());
                            Log.d("summary", Item.getSummary());
                        } else if (parser.getName().equals("im:image") && flag == true) {
                            //   h= Integer.parseInt(parser.getAttributeValue(null,"height"));
                            //heightValues.put(Integer.parseInt(parser.getAttributeValue(null, "height")), parser.getAttributeValue(null, "height"));
                            Item.setSmallImageUrl(parser.nextText().trim());
                            Item.setLargeImageUrl(Item.getSmallImageUrl());
                            Log.d("image", Item.getLargeImageUrl());
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("entry")) {

                            ItemList.add(Item);
                            Item = null;
                            flag = false;

                            for (int j = 1; j < ItemList.size(); j++) {
                                String str_date1 = ItemList.get(j - 1).getReleaseDate();
                                String str_date2 = ItemList.get(j).getReleaseDate();
                                //Log.d("date",str_date);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

                                SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                                try {
                                    Date date1 = sdf.parse(str_date1);
                                    Date date2 = sdf.parse(str_date2);
                                    Log.d("list", ItemList.get(j).getTitle().toString());
                                    Log.d("date1", date1.toString());
                                    Log.d("date2", date2.toString());

                                    String date_req1 = sdf2.format(date1);
                                    String date_req2 = sdf2.format(date2);

                                    if (date2.compareTo(date1) > 0) {
                                        Items temp_newsItem = ItemList.get(j - 1);
                                        ItemList.set(j - 1, ItemList.get(j));
                                        ItemList.set(j, temp_newsItem);
                                        Log.d("date1 inside loop", date1.toString());
                                        Log.d("date2 inside loop", date2.toString());
                                        j = 0;
                                    }

                                    //Log.d("date changed",date.toString());

                                    //Date final_date = (Date)sdf2.parse(date_req);


                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                }
                event = parser.next();
            }
            return ItemList;
        }
    }

}
