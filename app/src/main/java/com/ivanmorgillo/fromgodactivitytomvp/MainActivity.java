package com.ivanmorgillo.fromgodactivitytomvp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ivanmorgillo.fromgodactivitytomvp.api.StackOverflowApiManager;
import com.ivanmorgillo.fromgodactivitytomvp.api.models.Question;
import com.ivanmorgillo.fromgodactivitytomvp.api.models.SearchResponse;
import com.ivanmorgillo.fromgodactivitytomvp.helpers.DateTimeSerializer;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DateTimeSerializer dateSerializer = new DateTimeSerializer(ISODateTimeFormat.dateTimeParser().withZoneUTC());
        Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, dateSerializer).create();

        StackOverflowApiManager apiManager = new StackOverflowApiManager(gson, getCacheDir(), getString(R.string.server));

        Call<SearchResponse> posts = apiManager.doSearchForTitle("android");
        try {
        List<Question> questions = posts.execute().body().getQuestions();
            for (Question question : questions) {
                Log.d("GDG", question.toString());
            }
        } catch (IOException e) {
            Log.e("GDG", e.getLocalizedMessage());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_god) {
        } else if (id == R.id.nav_mvp) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
