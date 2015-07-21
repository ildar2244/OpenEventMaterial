package ru.javaapp.openeventmaterial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ru.javaapp.openeventmaterial.activities.AddEventsActivity;
import ru.javaapp.openeventmaterial.fragments.FragmentDrawer;
import ru.javaapp.openeventmaterial.fragments.FragmentMain;


public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {

    private FragmentDrawer drawerFragment;
    int cityId;
    ArrayAdapter<String> cityAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.city_change) {
            setCity();
        }

        return super.onOptionsItemSelected(item);
    }

    //Метод для выбора города из списка
    protected void setCity(){

        //Создаем ListView для отображения списка городов
        ListView lvCity = new ListView(MainActivity.this);
        lvCity.setAdapter(fillCitySpinner());
        lvCity.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityId = position + 1;
            }
        });

        // Диалог выбора городов, внутри которого находится ListView
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Выберите город");
        builder.setView(lvCity);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Отпускает диалоговое окно
                //onDrawerItemSelected(0);
            }
        });
        builder.show();
    }

    //Метод для заполнения списка с городами в адаптер
    private ArrayAdapter fillCitySpinner(){
        String[] cities = getResources().getStringArray(R.array.cities);
        cityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, cities);
        return cityAdapter;
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentMain fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(-1);
                break;
            case 1:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            case 2:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            case 3:
                //onSectionAttached(position + 1);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            case 4:
                //onSectionAttached(position + 1);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            case 5:
                //onSectionAttached(position + 1);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            case 6:
                //onSectionAttached(position + 1);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            case 7:
                //onSectionAttached(position + 1);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            case 8:
                //onSectionAttached(position + 1);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            case 9:
                //onSectionAttached(position + 1);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            case 10:
                //onSectionAttached(position + 1);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            case 11:
                //onSectionAttached(position + 1);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            case 12:
                //onSectionAttached(position + 1);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                break;
            default:
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_main, fragment)
                .commit();
    }
}
