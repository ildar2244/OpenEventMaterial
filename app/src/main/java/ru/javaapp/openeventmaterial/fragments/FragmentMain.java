package ru.javaapp.openeventmaterial.fragments;


import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ru.javaapp.openeventmaterial.DividerItemDecoration;
import ru.javaapp.openeventmaterial.R;
import ru.javaapp.openeventmaterial.RecyclerItemClickListener;
import ru.javaapp.openeventmaterial.activities.CardViewEventActivity;
import ru.javaapp.openeventmaterial.adapters.RVAdapter;
import ru.javaapp.openeventmaterial.dao.Events;
import ru.javaapp.openeventmaterial.parserFromInternet.ParseFromTimePad;


public class FragmentMain extends Fragment {

    ArrayList<NameValuePair> pairs;
    ParseFromTimePad parseFromTimePad;

    private RecyclerView rv;
    private RVAdapter adapter;

    private Events event; // для хранения события
    private List<Events> eventsList; // для хранения всех событий

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
                        Bitmap itemImage = null;
                        byte[] byteArray = null;
                        if(eventsList.get(position).getImage() == null)
                        {
                            itemImage = null;
                        }
                        else {
                            itemImage = eventsList.get(position).getImage();
                        }
                        String itemOrganisator = eventsList.get(position).getOrganisator();

                        try {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            itemImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byteArray = stream.toByteArray();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

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
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cancel(true);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                parseFromTimePad = new ParseFromTimePad(cityId, positionCategory);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            pairs = new ArrayList<NameValuePair>();

            //Если позиция категории = 0, т.е. Все, то мы отправляем в БД только город
            //иначе отправляем и город и выбранную категорию
            if(positionCategory == 0){
                pairs.add(new BasicNameValuePair("cityId", Integer.toString(cityId)));
            }
            else{
                pairs.add(new BasicNameValuePair("catId", Integer.toString(positionCategory)));
                pairs.add(new BasicNameValuePair("cityId", Integer.toString(cityId)));
            }

            if(isConnected(getActivity())) {
                try {
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
            }
            else {
                return "нет интернета";
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
            if(result == "нет интернета")
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("Ошибка");
                builder.setMessage("Нет соединения или слабый интернет.");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Отпускает диалоговое окно
                    }
                });
                builder.show();
            }
            else {
                ListDrawer();
            }
            dialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getActivity(), "Задача прервана, попробуйте заново", Toast.LENGTH_SHORT).show();
        }
    }

    public void ListDrawer() {
        JSONObject jsonResponse = null;
        JSONArray jsonMainNode = null;
        try {
            eventsList = new ArrayList<Events>();
            Log.d("my", "Json: ListDrawer()");
            jsonResponse = new JSONObject(jsonResult);
            jsonMainNode = jsonResponse.optJSONArray("allEvents_info_by_category_and_city");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                event = new Events();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                //event.setid(Integer.parseInt(jsonChildNode.optString("id")));
                event.setCategoryId(Integer.parseInt(jsonChildNode.optString("categoryId")));
                event.setPlaceId(Integer.parseInt(jsonChildNode.optString("placeId")));
                event.setOrganisator(jsonChildNode.optString("managerId"));
                event.setName(jsonChildNode.optString("name"));
                event.setDate(jsonChildNode.optString("time"));
                event.setDescription(jsonChildNode.optString("description"));
                event.setAddress(jsonChildNode.optString("address"));
                event.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_bitmap));
                //event.setCoastId(Integer.parseInt(jsonChildNode.optString("coast")));
                //event.setBlocked(Integer.parseInt(jsonChildNode.optString("blocked")));

                eventsList.add(event); // Добавляем объект event в список
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < parseFromTimePad.getName().size(); i++) {
                event = new Events();
                // get the value from href attribute
                event.setName(parseFromTimePad.getName().get(i).text());
                event.setDate(parseFromTimePad.getDate().get(i).text());
                event.setAddress(parseFromTimePad.getAddress().get(i).text());
                event.setDescription(parseFromTimePad.getDesscr().get(i).text());
                event.setCoastLink(parseFromTimePad.getBuy().get(i).attr("href"));
                event.setImage(parseFromTimePad.getBitmapList().get(i));
                event.setOrganisator(parseFromTimePad.getOrganisator().get(i).text());
                eventsList.add(event); // Добавляем объект event в список
            }
            parseFromTimePad.getBitmapList().clear();
        }
        catch (Exception ee){ee.printStackTrace();}

        try {
            adapter = new RVAdapter(getActivity(), eventsList);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rv.setAdapter(adapter);
            rv.setLayoutManager(llm);
        }
        catch(Exception e){}
    }

}
