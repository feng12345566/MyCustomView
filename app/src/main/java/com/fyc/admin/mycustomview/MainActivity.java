package com.fyc.admin.mycustomview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyc.admin.adapter.MyFragmentAdapter;
import com.fyc.admin.fragment.HotOpenSourceFragment;
import com.fyc.admin.fragment.LastOpenSourceFragment;
import com.fyc.admin.fragment.MyViewFragment;
import com.fyc.admin.fragment.NewBlogFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.appbar)
    AppBarLayout appbar;
    @InjectView(R.id.mainViewPager)
    ViewPager mainViewPager;
    @InjectView(R.id.main_content)
    CoordinatorLayout mainContent;

    @InjectView(R.id.dl_left)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.search_view)
    com.miguelcatalan.materialsearchview.MaterialSearchView searchView;


    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("知识库");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        Fragment[] fragments = new Fragment[4];
        fragments[0] = new LastOpenSourceFragment();
        fragments[1] = new NewBlogFragment();
        fragments[2] = new HotOpenSourceFragment();
        fragments[3] = new MyViewFragment();
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments,
                new String[]{"最新项目", "最新博客", "热门框架", "程序开发"});
        mainViewPager.setAdapter(adapter);
        mainViewPager.setOffscreenPageLimit(4);
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#EE9A00"));
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setTabTextColors(Color.parseColor("#F0F0F0"), Color.parseColor("#EE9A00"));
        tabs.setupWithViewPager(mainViewPager);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }


}
