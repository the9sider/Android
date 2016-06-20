package com.example.kgm.lgtwins;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.kgm.lgtwins.board.ArticleVO;
import com.example.kgm.lgtwins.board.BoardFragment;
import com.example.kgm.lgtwins.board.DetailFragment;
import com.example.kgm.lgtwins.board.SearchFragment;
import com.example.kgm.lgtwins.cheer.CheerFragment;
import com.example.kgm.lgtwins.food.FoodFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentReplaceable {

    private Fragment mainFragment;
    private Fragment cheerFragment;
    private Fragment boardFragment;
    private Fragment foodFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로딩화면
        startActivity(new Intent(this, LoadingActivity.class));

        mainFragment = new MainFragment();
        cheerFragment = new CheerFragment();
        boardFragment = new BoardFragment();
        foodFragment = new FoodFragment();

        // 기본화면 설정
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, mainFragment);
        transaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "cocomo.tistory.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // 검색 기능 활성화한다.
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        // 검색 버튼을 가져온다.
        MenuItem searchButton = menu.findItem(R.id.searchButton);

        // 검색버튼을 클릭했을 때 SearchView를 가져온다.
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchButton);

        // 검색 힌트를 설정한다.
        searchView.setQueryHint("검색어를 입력하세요");

        // SearchView를 검색 가능한 위젝으로 설정한다.
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );

        //
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /**
             * 검색 버튼을 클릭했을 때 동작하는 이벤트
             * @param s : 입력된 검색어
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String s) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, SearchFragment.newInstance(s));
                transaction.commit();

                return false;
            }

            /**
             * 검색어를 입력할 때 동작하는 이
             * @param s
             * @return
             */
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.searchButton) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cheer) {
            transaction.replace(R.id.container, cheerFragment);
        } else if(id == R.id.nav_board) {
            transaction.replace(R.id.container, boardFragment);
        } else if (id == R.id.nav_food) {
            transaction.replace(R.id.container, foodFragment);
        }

        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void replaceFragment(ArticleVO article) {

        Log.d("MAIN", article.getSubject());
        Log.d("MAIN", article.getAuthor());
        Log.d("MAIN", article.getHitCount() + "");
        Log.d("MAIN", article.getDescription());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, DetailFragment.newInstance(article));
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
