package ru.javaapp.openeventmaterial.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import ru.javaapp.openeventmaterial.R;

public class AddEventsActivity extends ActionBarActivity {

    TextView tv_input;
    EditText et_title, et_about, et_time, et_address, et_org;

    int selectCategory = 0;
    int cityId = 0;
    Random r;
    int code;

    ArrayAdapter<String> categoryAdapter = null;
    ArrayAdapter<String> cityAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // РњРµС‚РѕРґ РґР»СЏ РєРЅРѕРїРєРё back РІ Toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Spinner s1 = (Spinner) findViewById(R.id.spinner);
        s1.setAdapter(fillCitySpinner());
        Spinner s2 = (Spinner) findViewById(R.id.spinner2);
        s2.setAdapter(fillCategorySpinner());
        Button btnGet = (Button) findViewById(R.id.btn_get);

        et_title = (EditText) findViewById(R.id.et_title);
        et_about = (EditText) findViewById(R.id.et_about);
        et_time = (EditText) findViewById(R.id.et_time);
        et_address = (EditText) findViewById(R.id.et_address);
        et_org = (EditText) findViewById(R.id.et_org);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityId = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectCategory = position + 2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFillAllFields()) {
                    addEventToDb();
                }
                else {
                    Toast.makeText(AddEventsActivity.this, R.string.notFillFields, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }

    boolean checkFillAllFields(){
        if(
                et_title.getText().toString().trim().length() == 0 ||
                et_about.getText().toString().trim().length() == 0 ||
                et_time.getText().toString().trim().length() == 0 ||
                et_address.getText().toString().trim().length() == 0 ||
                et_org.getText().toString().trim().length() == 0)
        {
            return false;
        }
        else{
            return true;
        }
    }

    private void addEventToDb(){
        InsertData task1 = new InsertData();
        task1.execute(new String[]{"http://javaapp.ru/insert_event_to_Events_table.php"});
        finish();
    }

    private class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(AddEventsActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Sending Data...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            InputStream is = null;
            String result = null;

            final int CONN_WAIT_TIME = 8000;
            final int CONN_DATA_WAIT_TIME = 7000;

            for(String url : urls) {
                try {
                    ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("id", UUID.randomUUID().toString()));
                    pairs.add(new BasicNameValuePair("categoryId", Integer.toString(selectCategory)));
                    pairs.add(new BasicNameValuePair("placeId", Integer.toString(cityId)));
                    pairs.add(new BasicNameValuePair("managerId", et_org.getText().toString()));
                    pairs.add(new BasicNameValuePair("name", et_title.getText().toString()));
                    pairs.add(new BasicNameValuePair("time", et_time.getText().toString()));
                    pairs.add(new BasicNameValuePair("description", et_about.getText().toString()));
                    pairs.add(new BasicNameValuePair("address", et_address.getText().toString()));
                    pairs.add(new BasicNameValuePair("coast", "0"));
                    pairs.add(new BasicNameValuePair("blocked", "0"));

                    HttpParams httpParams = new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(httpParams, CONN_WAIT_TIME);
                    HttpConnectionParams.setSoTimeout(httpParams, CONN_DATA_WAIT_TIME);

                    DefaultHttpClient postClient = new DefaultHttpClient(httpParams);
                    HttpClient client = postClient;
                    HttpPost post = new HttpPost(url);
                    post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
                    HttpResponse responce = client.execute(post);
                    HttpEntity entity = responce.getEntity();
                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json_data = new JSONObject(result);
                code=json_data.getInt("code");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(code==1)
            {
                Toast.makeText(getBaseContext(), "Событие успешно сохранено",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getBaseContext(), "Событие не сохранено, попробуйте еще раз",
                        Toast.LENGTH_LONG).show();
            }

        }
    }

    //РњРµС‚РѕРґ РґР»СЏ Р·Р°РїРѕР»РЅРµРЅРёСЏ СЃРїРёСЃРєР° СЃ РіРѕСЂРѕРґР°РјРё РІ Р°РґР°РїС‚РµСЂ
    private ArrayAdapter fillCitySpinner(){
        String[] cities = getResources().getStringArray(R.array.cities);
        cityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, cities);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return cityAdapter;
    }

    //РњРµС‚РѕРґ РґР»СЏ Р·Р°РїРѕР»РЅРµРЅРёСЏ СЃРїРёСЃРєР° СЃ РєР°С‚РµРіРѕСЂРёСЏРјРё РІ Р°РґР°РїС‚РµСЂ
    private ArrayAdapter fillCategorySpinner(){
        String[] category = new String[12];
        for(int i = 1; i < getResources().getStringArray(R.array.nav_rv).length; i++){
            category[i-1] = getResources().getStringArray(R.array.nav_rv)[i];
        }
        //String[] category = getResources().getStringArray(R.array.nav_rv);
        categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, category);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return categoryAdapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_events, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.d("My", "OnBackPressed");
        try {
            finish();
        }
        catch (Exception e){

        }
    }
}
