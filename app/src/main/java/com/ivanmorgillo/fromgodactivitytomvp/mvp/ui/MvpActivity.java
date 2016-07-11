package com.ivanmorgillo.fromgodactivitytomvp.mvp.ui;

import com.ivanmorgillo.fromgodactivitytomvp.MainApplication;
import com.ivanmorgillo.fromgodactivitytomvp.R;
import com.ivanmorgillo.fromgodactivitytomvp.api.models.Question;
import com.ivanmorgillo.fromgodactivitytomvp.god.GodActivity;
import com.ivanmorgillo.fromgodactivitytomvp.ui.QuestionsAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MvpActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IMvpView {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Inject
    MvpPresenter presenter;

    private QuestionsAdapter adapter;

    List<Question> questions = new ArrayList<>();

    private NavigationView navigationView;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.component().inject(this);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        presenter.setView(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MvpActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new QuestionsAdapter(questions);
        recyclerView.setAdapter(adapter);

        presenter.loadQuestions();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void updateList(List<Question> questions) {
        adapter.setQuestions(questions);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
