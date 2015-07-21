package ru.javaapp.openeventmaterial.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.javaapp.openeventmaterial.R;

public class CardViewEventActivity extends ActionBarActivity {

    TextView tv_name;
    TextView tv_about;
    TextView tv_clock;
    TextView tv_place;
    TextView tv_admin;

    ImageView icon_clock;
    ImageView icon_place;
    ImageView icon_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_about);

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
        tv_clock = (TextView) findViewById(R.id.tv_clock);
        tv_place = (TextView) findViewById(R.id.tv_place);
        tv_admin = (TextView) findViewById(R.id.tv_admin);

        icon_clock = (ImageView) findViewById(R.id.icon_clock);
        icon_clock.setImageResource(R.drawable.ic_access_time_black_24dp);
        icon_place = (ImageView) findViewById(R.id.icon_place);
        icon_place.setImageResource(R.drawable.ic_place_black_24dp);
        icon_admin = (ImageView) findViewById(R.id.icon_admin);
        icon_admin.setImageResource(R.drawable.ic_group_black_24dp);

        String text1 = i1.getStringExtra("sName");
        String text2 = i1.getStringExtra("sAbout");
        String text3 = i1.getStringExtra("sDate");

        tv_name.setText(text1);
        tv_about.setText(text2);
        tv_clock.setText(text3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_view_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.home) {
            finish();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
