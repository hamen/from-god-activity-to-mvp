package com.ivanmorgillo.fromgodactivitytomvp.mvp.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ivanmorgillo.fromgodactivitytomvp.R;
import com.ivanmorgillo.fromgodactivitytomvp.api.StackOverflowApiManager;
import com.ivanmorgillo.fromgodactivitytomvp.api.models.Question;
import com.ivanmorgillo.fromgodactivitytomvp.api.models.SearchResponse;
import com.ivanmorgillo.fromgodactivitytomvp.god.GodActivity;
import com.ivanmorgillo.fromgodactivitytomvp.helpers.DateTimeSerializer;
import com.ivanmorgillo.fromgodactivitytomvp.ui.QuestionsAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MvpActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DateTimeSerializer dateSerializer = new DateTimeSerializer(ISODateTimeFormat.dateTimeParser().withZoneUTC());

    Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, dateSerializer).create();

    private StackOverflowApiManager apiManager;

    private QuestionsAdapter adapter;

    List<Question> questions = new ArrayList<>();

    private NavigationView navigationView;

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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MvpActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new QuestionsAdapter(questions);
        recyclerView.setAdapter(adapter);

        apiManager = new StackOverflowApiManager(gson, getCacheDir(), getString(R.string.server));

        new SearchAndroid().execute();
    }

    private class SearchAndroid extends AsyncTask<Void, Void, List<Question>> {

        @Override
        protected List<Question> doInBackground(Void... args) {
            Call<SearchResponse> posts = apiManager.doSearchForTitle("android");
            List<Question> questions = new ArrayList<>();
            try {
                questions = posts.execute().body().getQuestions();
            } catch (IOException e) {
                Log.e("GDG", e.getLocalizedMessage());
            }
            return questions;
        }

        @Override
        protected void onPostExecute(List<Question> questions) {
            adapter.setQuestions(questions);
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
        navigationView.getMenu().getItem(1).setChecked(true);
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
            startActivity(new Intent(this, GodActivity.class));
            finish();
        } else if (id == R.id.nav_mvp) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
