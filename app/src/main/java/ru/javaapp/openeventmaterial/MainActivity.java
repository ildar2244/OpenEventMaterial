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

        // Устанавливаем Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setCity(); // Выбираем город

        // Создаем и устанавливаем фрагмент панели навигации NavigationDrawer
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
                displayView(0);
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
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(-1);
                category = getString(R.string.title_section1);
                break;
            case 1:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section2);
                break;
            case 2:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section3);
                break;
            case 3:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section4);
                break;
            case 4:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section5);
                break;
            case 5:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section6);
                break;
            case 6:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section7);
                break;
            case 7:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section8);
                break;
            case 8:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section9);
                break;
            case 9:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section10);
                break;
            case 10:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section11);
                break;
            case 11:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section12);
                break;
            case 12:
                fragment = new FragmentMain();
                fragment.setCityId(cityId);
                fragment.setPositionCategory(position);
                category = getString(R.string.title_section13);
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
