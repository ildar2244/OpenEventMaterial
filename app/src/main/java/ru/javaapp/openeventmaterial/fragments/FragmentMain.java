package ru.javaapp.openeventmaterial.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.javaapp.openeventmaterial.DividerItemDecoration;
import ru.javaapp.openeventmaterial.R;
import ru.javaapp.openeventmaterial.RecyclerItemClickListener;
import ru.javaapp.openeventmaterial.activities.CardViewEventActivity;
import ru.javaapp.openeventmaterial.adapters.RVAdapter;
import ru.javaapp.openeventmaterial.dao.Events;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {

    private RecyclerView rv;
    private RVAdapter adapter;

    private Events event; // для хранения события
    private List<Events> eventsList; // для хранения всех событий

    Elements name, date, address, buy, desscr, img, organisator;
    Bitmap image;
    InputStream inp = null;
    List<Bitmap> bitmapList;

    private String url = "http://javaapp.ru/select_all_events_by_category_and_city.php";
    private String jsonResult;

    int positionCategory = 0;
    int cityId;

    public FragmentMain() {}

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setPositionCategory(int positionCategory) {
        this.positionCategory = positionCategory + 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_fragment_main, container, false);
        rv = (RecyclerView) layout.findViewById(R.id.rv_events);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        new JsonReadDataFromMySql().execute();

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {

                        Events itemEvent = eventsList.get(position);
                        String itemDate = eventsList.get(position).getDate();
                        String itemAddress = eventsList.get(position).getAddress();
                        String itemName = eventsList.get(position).getName();
                        String itemAbout = eventsList.get(position).getDescription();
                        String itemLink = eventsList.get(position).getCoastLink();
                        Bitmap itemImage = eventsList.get(position).getImage();
                        String itemOrganisator = eventsList.get(position).getOrganisator();

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        itemImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        Intent i1 = new Intent(getActivity(), CardViewEventActivity.class);
                        i1.putExtra("sName", itemName);
                        i1.putExtra("sAbout", itemAbout);
                        i1.putExtra("sDate", itemDate);
                        i1.putExtra("sImage", byteArray);
                        i1.putExtra("sLink", itemLink);
                        i1.putExtra("sOrganisator", itemOrganisator);
                        i1.putExtra("sAddress", itemAddress);
                        startActivity(i1);
                    }
                })
        );

        return layout;
    }

    // Метод проверки подключения интернета
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            return cm.getActiveNetworkInfo().isConnected();
        }
        return false;
    }

    public void parseFromInternet() {
        // Астрахань и Категории
        if(cityId==1 && positionCategory==0){parse("https://my.timepad.ru/astrahan/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==1 && positionCategory==2){parse("https://my.timepad.ru/astrahan/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==1 && positionCategory==3){parse("https://my.timepad.ru/astrahan/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==1 && positionCategory==4){parse("https://my.timepad.ru/astrahan/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==1 && positionCategory==5){parse("https://my.timepad.ru/astrahan/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==1 && positionCategory==6){parse("https://my.timepad.ru/astrahan/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==1 && positionCategory==8){parse("https://my.timepad.ru/astrahan/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==1 && positionCategory==10){parse("https://my.timepad.ru/astrahan/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==1 && positionCategory==11){parse("https://my.timepad.ru/astrahan/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==1 && positionCategory==13){parse("https://my.timepad.ru/astrahan/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==1 && positionCategory==14){parse("https://my.timepad.ru/astrahan/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Волгоград и Категории
        if(cityId==2 && positionCategory==0){parse("https://my.timepad.ru/volgograd/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==2 && positionCategory==2){parse("https://my.timepad.ru/volgograd/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==2 && positionCategory==3){parse("https://my.timepad.ru/volgograd/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==2 && positionCategory==4){parse("https://my.timepad.ru/volgograd/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==2 && positionCategory==5){parse("https://my.timepad.ru/volgograd/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==2 && positionCategory==6){parse("https://my.timepad.ru/volgograd/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==2 && positionCategory==8){parse("https://my.timepad.ru/volgograd/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==2 && positionCategory==10){parse("https://my.timepad.ru/volgograd/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==2 && positionCategory==11){parse("https://my.timepad.ru/volgograd/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==2 && positionCategory==13){parse("https://my.timepad.ru/volgograd/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==2 && positionCategory==14){parse("https://my.timepad.ru/volgograd/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Екатеринбург и Категории
        if(cityId==3 && positionCategory==0){parse("https://my.timepad.ru/ekaterinburg/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==3 && positionCategory==2){parse("https://my.timepad.ru/ekaterinburg/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==3 && positionCategory==3){parse("https://my.timepad.ru/ekaterinburg/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==3 && positionCategory==4){parse("https://my.timepad.ru/ekaterinburg/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==3 && positionCategory==5){parse("https://my.timepad.ru/ekaterinburg/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==3 && positionCategory==6){parse("https://my.timepad.ru/ekaterinburg/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==3 && positionCategory==8){parse("https://my.timepad.ru/ekaterinburg/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==3 && positionCategory==10){parse("https://my.timepad.ru/ekaterinburg/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==3 && positionCategory==11){parse("https://my.timepad.ru/ekaterinburg/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==3 && positionCategory==13){parse("https://my.timepad.ru/ekaterinburg/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==3 && positionCategory==14){parse("https://my.timepad.ru/ekaterinburg/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Иркутск и Категории
        if(cityId==4 && positionCategory==0){parse("https://my.timepad.ru/irkutsk/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==4 && positionCategory==2){parse("https://my.timepad.ru/irkutsk/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==4 && positionCategory==3){parse("https://my.timepad.ru/irkutsk/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==4 && positionCategory==4){parse("https://my.timepad.ru/irkutsk/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==4 && positionCategory==5){parse("https://my.timepad.ru/irkutsk/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==4 && positionCategory==6){parse("https://my.timepad.ru/irkutsk/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==4 && positionCategory==8){parse("https://my.timepad.ru/irkutsk/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==4 && positionCategory==10){parse("https://my.timepad.ru/irkutsk/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==4 && positionCategory==11){parse("https://my.timepad.ru/irkutsk/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==4 && positionCategory==13){parse("https://my.timepad.ru/irkutsk/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==4 && positionCategory==14){parse("https://my.timepad.ru/irkutsk/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Казань и Категории
        if(cityId==5 && positionCategory==0){parse("https://my.timepad.ru/kazan/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==5 && positionCategory==2){parse("https://my.timepad.ru/kazan/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==5 && positionCategory==3){parse("https://my.timepad.ru/kazan/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==5 && positionCategory==4){parse("https://my.timepad.ru/kazan/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==5 && positionCategory==5){parse("https://my.timepad.ru/kazan/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==5 && positionCategory==6){parse("https://my.timepad.ru/kazan/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==5 && positionCategory==8){parse("https://my.timepad.ru/kazan/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==5 && positionCategory==10){parse("https://my.timepad.ru/kazan/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==5 && positionCategory==11){parse("https://my.timepad.ru/kazan/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==5 && positionCategory==13){parse("https://my.timepad.ru/kazan/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==5 && positionCategory==14){parse("https://my.timepad.ru/kazan/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Краснодар и Категории
        if(cityId==6 && positionCategory==0){parse("https://my.timepad.ru/krasnodar/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==6 && positionCategory==2){parse("https://my.timepad.ru/krasnodar/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==6 && positionCategory==3){parse("https://my.timepad.ru/krasnodar/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==6 && positionCategory==4){parse("https://my.timepad.ru/krasnodar/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==6 && positionCategory==5){parse("https://my.timepad.ru/krasnodar/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==6 && positionCategory==6){parse("https://my.timepad.ru/krasnodar/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==6 && positionCategory==8){parse("https://my.timepad.ru/krasnodar/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==6 && positionCategory==10){parse("https://my.timepad.ru/krasnodar/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==6 && positionCategory==11){parse("https://my.timepad.ru/krasnodar/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==6 && positionCategory==13){parse("https://my.timepad.ru/krasnodar/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==6 && positionCategory==14){parse("https://my.timepad.ru/krasnodar/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Москва и Категории
        if(cityId==7 && positionCategory==0){parse("https://my.timepad.ru/moskva/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==7 && positionCategory==2){parse("https://my.timepad.ru/moskva/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==7 && positionCategory==3){parse("https://my.timepad.ru/moskva/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==7 && positionCategory==4){parse("https://my.timepad.ru/moskva/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==7 && positionCategory==5){parse("https://my.timepad.ru/moskva/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==7 && positionCategory==6){parse("https://my.timepad.ru/moskva/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==7 && positionCategory==8){parse("https://my.timepad.ru/moskva/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==7 && positionCategory==10){parse("https://my.timepad.ru/moskva/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==7 && positionCategory==11){parse("https://my.timepad.ru/moskva/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==7 && positionCategory==13){parse("https://my.timepad.ru/moskva/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==7 && positionCategory==14){parse("https://my.timepad.ru/moskva/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Набережные Челны и Категории
        if(cityId==8 && positionCategory==0){parse("https://my.timepad.ru/naberezhnye-chelny/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==8 && positionCategory==2){parse("https://my.timepad.ru/naberezhnye-chelny/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==8 && positionCategory==3){parse("https://my.timepad.ru/naberezhnye-chelny/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==8 && positionCategory==4){parse("https://my.timepad.ru/naberezhnye-chelny/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==8 && positionCategory==5){parse("https://my.timepad.ru/naberezhnye-chelny/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==8 && positionCategory==6){parse("https://my.timepad.ru/naberezhnye-chelny/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==8 && positionCategory==8){parse("https://my.timepad.ru/naberezhnye-chelny/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==8 && positionCategory==10){parse("https://my.timepad.ru/naberezhnye-chelny/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==8 && positionCategory==11){parse("https://my.timepad.ru/naberezhnye-chelny/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==8 && positionCategory==13){parse("https://my.timepad.ru/naberezhnye-chelny/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==8 && positionCategory==14){parse("https://my.timepad.ru/naberezhnye-chelny/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Нижний Новгород и Категории
        if(cityId==9 && positionCategory==0){parse("https://my.timepad.ru/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==9 && positionCategory==2){parse("https://my.timepad.ru/nizhniy-novgorod/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==9 && positionCategory==3){parse("https://my.timepad.ru/nizhniy-novgorod/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==9 && positionCategory==4){parse("https://my.timepad.ru/nizhniy-novgorod/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==9 && positionCategory==5){parse("https://my.timepad.ru/nizhniy-novgorod/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==9 && positionCategory==6){parse("https://my.timepad.ru/nizhniy-novgorod/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==9 && positionCategory==8){parse("https://my.timepad.ru/nizhniy-novgorod/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==9 && positionCategory==10){parse("https://my.timepad.ru/nizhniy-novgorod/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==9 && positionCategory==11){parse("https://my.timepad.ru/nizhniy-novgorod/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==9 && positionCategory==13){parse("https://my.timepad.ru/nizhniy-novgorod/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==9 && positionCategory==14){parse("https://my.timepad.ru/nizhniy-novgorod/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Новосибирск и Категории
        if(cityId==10 && positionCategory==0){parse("https://my.timepad.ru/novosibirsk/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==10 && positionCategory==2){parse("https://my.timepad.ru/novosibirsk/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==10 && positionCategory==3){parse("https://my.timepad.ru/novosibirsk/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==10 && positionCategory==4){parse("https://my.timepad.ru/novosibirsk/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==10 && positionCategory==5){parse("https://my.timepad.ru/novosibirsk/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==10 && positionCategory==6){parse("https://my.timepad.ru/novosibirsk/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==10 && positionCategory==8){parse("https://my.timepad.ru/novosibirsk/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==10 && positionCategory==10){parse("https://my.timepad.ru/novosibirsk/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==10 && positionCategory==11){parse("https://my.timepad.ru/novosibirsk/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==10 && positionCategory==13){parse("https://my.timepad.ru/novosibirsk/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==10 && positionCategory==14){parse("https://my.timepad.ru/novosibirsk/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Омск и Категории
        if(cityId==11 && positionCategory==0){parse("https://my.timepad.ru/omsk/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==11 && positionCategory==2){parse("https://my.timepad.ru/omsk/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==11 && positionCategory==3){parse("https://my.timepad.ru/omsk/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==11 && positionCategory==4){parse("https://my.timepad.ru/omsk/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==11 && positionCategory==5){parse("https://my.timepad.ru/omsk/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==11 && positionCategory==6){parse("https://my.timepad.ru/omsk/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==11 && positionCategory==8){parse("https://my.timepad.ru/omsk/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==11 && positionCategory==10){parse("https://my.timepad.ru/omsk/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==11 && positionCategory==11){parse("https://my.timepad.ru/omsk/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==11 && positionCategory==13){parse("https://my.timepad.ru/omsk/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==11 && positionCategory==14){parse("https://my.timepad.ru/omsk/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Санкт-Петербург и Категории
        if(cityId==12 && positionCategory==0){parse("https://my.timepad.ru/sankt-peterburg/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==12 && positionCategory==2){parse("https://my.timepad.ru/sankt-peterburg/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==12 && positionCategory==3){parse("https://my.timepad.ru/sankt-peterburg/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==12 && positionCategory==4){parse("https://my.timepad.ru/sankt-peterburg/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==12 && positionCategory==5){parse("https://my.timepad.ru/sankt-peterburg/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==12 && positionCategory==6){parse("https://my.timepad.ru/sankt-peterburg/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==12 && positionCategory==8){parse("https://my.timepad.ru/sankt-peterburg/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==12 && positionCategory==10){parse("https://my.timepad.ru/sankt-peterburg/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==12 && positionCategory==11){parse("https://my.timepad.ru/sankt-peterburg/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==12 && positionCategory==13){parse("https://my.timepad.ru/sankt-peterburg/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==12 && positionCategory==14){parse("https://my.timepad.ru/sankt-peterburg/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Ульяновск и Категории
        if(cityId==13 && positionCategory==0){parse("https://my.timepad.ru/ulyanovsk/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==13 && positionCategory==2){parse("https://my.timepad.ru/ulyanovsk/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==13 && positionCategory==3){parse("https://my.timepad.ru/ulyanovsk/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==13 && positionCategory==4){parse("https://my.timepad.ru/ulyanovsk/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==13 && positionCategory==5){parse("https://my.timepad.ru/ulyanovsk/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==13 && positionCategory==6){parse("https://my.timepad.ru/ulyanovsk/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==13 && positionCategory==8){parse("https://my.timepad.ru/ulyanovsk/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==13 && positionCategory==10){parse("https://my.timepad.ru/ulyanovsk/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==13 && positionCategory==11){parse("https://my.timepad.ru/ulyanovsk/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==13 && positionCategory==13){parse("https://my.timepad.ru/ulyanovsk/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==13 && positionCategory==14){parse("https://my.timepad.ru/ulyanovsk/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

        // Челябинск и Категории
        if(cityId==14 && positionCategory==0){parse("https://my.timepad.ru/chelyabinsk/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==14 && positionCategory==2){parse("https://my.timepad.ru/chelyabinsk/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==14 && positionCategory==3){parse("https://my.timepad.ru/chelyabinsk/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==14 && positionCategory==4){parse("https://my.timepad.ru/chelyabinsk/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==14 && positionCategory==5){parse("https://my.timepad.ru/chelyabinsk/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==14 && positionCategory==6){parse("https://my.timepad.ru/chelyabinsk/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==14 && positionCategory==8){parse("https://my.timepad.ru/chelyabinsk/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==14 && positionCategory==10){parse("https://my.timepad.ru/chelyabinsk/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==14 && positionCategory==11){parse("https://my.timepad.ru/chelyabinsk/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==14 && positionCategory==13){parse("https://my.timepad.ru/chelyabinsk/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");}
        if(cityId==14 && positionCategory==14){parse("https://my.timepad.ru/chelyabinsk/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");}

    }

    // запускается фоновый потток для получения данных из БД
    public class JsonReadDataFromMySql extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        final int CONN_WAIT_TIME = 10000;
        final int CONN_DATA_WAIT_TIME = 9000;

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Загрузка мероприятий");
            dialog.setMessage("Пожалуйста, подождите...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            parseFromInternet();

            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();

            //Если позиция категории = 0, т.е. Все, то мы отправляем в БД только город
            //иначе отправляем и город и выбранную категорию
            if(positionCategory == 0){
                pairs.add(new BasicNameValuePair("cityId", Integer.toString(cityId)));
            }
            else{
                pairs.add(new BasicNameValuePair("catId", Integer.toString(positionCategory)));
                pairs.add(new BasicNameValuePair("cityId", Integer.toString(cityId)));
            }

            try{
                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, CONN_WAIT_TIME);
                HttpConnectionParams.setSoTimeout(httpParams, CONN_DATA_WAIT_TIME);

                DefaultHttpClient postClient = new DefaultHttpClient(httpParams);

                HttpClient httpclient = postClient;
                HttpPost httppost = new HttpPost(url);
                httppost.setEntity(new UrlEncodedFormEntity(pairs));
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // e.printStackTrace();
                Toast.makeText(getActivity(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        protected void onPostExecute(String result) {
            ListDrawer();
            dialog.dismiss();
        }
    }

    public void ListDrawer() {

        try {
            eventsList = new ArrayList<Events>();
            Log.d("my", "Json: ListDrawer()");
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("allEvents_info_by_category_and_city");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                event = new Events();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                //event.setid(Integer.parseInt(jsonChildNode.optString("id")));
                //event.setCategoryId(Integer.parseInt(jsonChildNode.optString("categoryId")));
                event.setPlaceId(Integer.parseInt(jsonChildNode.optString("placeId")));
                //event.setManagerId(Integer.parseInt(jsonChildNode.optString("managerId")));
                event.setName(jsonChildNode.optString("name"));
                event.setDate(jsonChildNode.optString("data"));
                event.setTime(jsonChildNode.optString("vremya"));
                event.setDescription(jsonChildNode.optString("description"));
                event.setAddress(jsonChildNode.optString("address"));
                //event.setCoastId(Integer.parseInt(jsonChildNode.optString("coast")));
                //event.setBlocked(Integer.parseInt(jsonChildNode.optString("blocked")));

                eventsList.add(event); // Добавляем объект event в список
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < name.size(); i++) {
                event = new Events();
                // get the value from href attribute
                event.setName(name.get(i).text());
                event.setDate(date.get(i).text());
                //event.setTime(time.get(i).text());
                event.setAddress(address.get(i).text());
                event.setDescription(desscr.get(i).text());
                event.setCoastLink(buy.get(i).attr("href"));
                event.setImage(bitmapList.get(i));
                event.setOrganisator(organisator.get(i).text());
                //event.setPlaceId(1);
                eventsList.add(event); // Добавляем объект event в список
            }
            bitmapList.clear();
        }
        catch (Exception ee){ee.printStackTrace();}

            adapter = new RVAdapter(getActivity(), eventsList);
            rv.setAdapter(adapter);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rv.setLayoutManager(llm);
    }

    public void parse(String url){
        Document doc = null;
        String imgSrc;
        bitmapList = new ArrayList<Bitmap>();

        try {
            doc = Jsoup.connect(url).get();
            name = doc.select("h2[class=b-unit__header_size_small b-event__header] > a[href]");
            date = doc.select("span[class=b-unit__text_size_small b-unit__text_color_black]:contains(2015)");
            organisator = doc.select("span[class=b-unit__text_size_small b-unit__text_color_black] > a[href]");
            address = doc.select("span[class=b-unit__text_size_small b-unit__text_color_black]:not(:contains(2015)):not(:has(span a)):not(:contains(руб.))");
            desscr = doc.select("div[class=b-event__info] > p[class = b-unit__text b-event__description]:not(:contains(Зарегистрируйтесь и опубликуйте))");
            buy = doc.select("h2[class=b-unit__header_size_small b-event__header] > a[href]");
            img = doc.select("div[class=b-event__pic] > img[src*=https]");

            if(bitmapList.isEmpty()){
                for(int i = 0; i < img.size(); i++) {
                    imgSrc = img.get(i).attr("src");
                    inp = new java.net.URL(imgSrc).openStream();
                    bitmapList.add(BitmapFactory.decodeStream(inp));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
