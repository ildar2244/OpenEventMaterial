package ru.javaapp.openeventmaterial.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ru.javaapp.openeventmaterial.R;

public class CardViewEventActivity extends ActionBarActivity {

    TextView tv_name;
    TextView tv_about;
    TextView tv_date;
    TextView tv_clock;
    TextView tv_place;
    TextView tv_admin;

    ImageView imageCity;
    ImageView icon_clock;
    ImageView icon_place;
    ImageView icon_admin;
    String link;

    Button btnLink;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_event);

        Intent i1 = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Метод для кнопки back в Toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_date = (TextView) findViewById(R.id.tv_date);
        //tv_clock = (TextView) findViewById(R.id.tv_clock);
        tv_place = (TextView) findViewById(R.id.tv_place);
        tv_admin = (TextView) findViewById(R.id.tv_admin);

        imageCity = (ImageView) findViewById(R.id.iv_city);
        icon_clock = (ImageView) findViewById(R.id.icon_clock);
        icon_clock.setImageResource(R.drawable.ic_access_time_black_24dp);
        icon_place = (ImageView) findViewById(R.id.icon_place);
        icon_place.setImageResource(R.drawable.ic_place_black_24dp);
        icon_admin = (ImageView) findViewById(R.id.icon_admin);
        icon_admin.setImageResource(R.drawable.ic_group_black_24dp);

        btnLink = (Button) findViewById(R.id.btn_ticket);

        String text1 = i1.getStringExtra("sName");
        String text2 = i1.getStringExtra("sAbout");
        String text3 = i1.getStringExtra("sDate");
        String text4 = i1.getStringExtra("sAddress");
        byte[] byteArray = getIntent().getByteArrayExtra("sImage");
        link = i1.getStringExtra("sLink");
        String text5 = i1.getStringExtra("sOrganisator");

        try {
           bmp  = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        tv_name.setText(text1);
        tv_about.setText(text2);
        //tv_clock.setText(text3);
        tv_date.setText(text3);
        //imageCity.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.img_nch_01));
        tv_place.setText(text4);
        tv_admin.setText(text5);

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_view_event, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.d("My", "On Back Pressed");
        super.onBackPressed();
        finish();
    }
}
