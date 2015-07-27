package ru.javaapp.openevent;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ru.javaapp.openevent.activities.AddEventsActivity;
import ru.javaapp.openevent.fragments.FragmentDrawer;
import ru.javaapp.openevent.fragments.FragmentMain;


public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {

    private FragmentDrawer drawerFragment;
    int cityId;
    ArrayAdapter<String> cityAdapter = null;
    String cityName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("My", "Finish MainActivity -> setContentView)");

        // Устанавливаем Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Log.d("My", "Finish MainActivity -> setToolbar)");

        setCity(); // Выбираем город
        Log.d("My", "Finish MainActivity ->setCity()");

        // Создаем и устанавливаем фрагмент панели навигации NavigationDrawer
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        Log.d("My", "Finish MainActivity -> OnCreate()");
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

        if (id == R.id.add) {
            Intent addActivity = new Intent(getApplicationContext(), AddEventsActivity.class);
            startActivity(addActivity);
        }

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
                cityName = getResources().getStringArray(R.array.cities)[cityId - 1];
            }
        });

        // Диалог выбора городов, внутри которого находится ListView
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
        builder.setCancelable(false);
        builder.setTitle("Выберите город");
        builder.setView(lvCity);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Отпускает диалоговое окно
                Log.d("My", "Call category All Events ");
                displayView(0);
                getSupportActionBar().setSubtitle(cityName);
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    //Метод для заполнения списка с городами в адаптер
    private ArrayAdapter fillCitySpinner(){
        String[] cities = getResources().getStringArray(R.array.cities);
        cityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, cities);
        return cityAdapter;
    }

    // Обработка нажатий на пункты панели навигации
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    // Метод для наполнения фрагмента в MainActivity при выборе категории
    private void displayView(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentMain fragment = null;
        String category = getString(R.string.app_name);
        switch (position) {
            case 0:
                Log.d("My", "Touch Drawer Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(-1);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 1:
                Log.d("My", "Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 2:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 3:
                Log.d("My", "Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 4:
                Log.d("My", "Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 5:
                Log.d("My", "Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 6:
                Log.d("My", "Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 7:
                Log.d("My", "Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 8:
                Log.d("My", "Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 9:
                Log.d("My", "Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 10:
                Log.d("My", "Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 11:
                Log.d("My", "Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            case 12:
                Log.d("My", "Category: " + position);
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getResources().getStringArray(R.array.nav_rv)[position];
                break;
            default:
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_main, fragment)
                .commit();
        getSupportActionBar().setTitle(category);
    }

    @Override
    public void onBackPressed() {
        Log.d("My", "OnBackPressed");
        openQuitDialog();
    }

    // При нажатии назад
    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                MainActivity.this);
        quitDialog.setTitle("Выход: Вы уверены?");

        quitDialog.setPositiveButton("Да!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();
    }
}
