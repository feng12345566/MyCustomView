package com.fyc.admin.mycustomview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fyc.admin.adapter.MyFragmentAdapter;
import com.fyc.admin.fragment.HotOpenSourceFragment;
import com.fyc.admin.fragment.LastOpenSourceFragment;
import com.fyc.admin.fragment.MyViewFragment;
import com.fyc.admin.fragment.NewBlogFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("知识库");
        setSupportActionBar(toolbar);
        Fragment[] fragments = new Fragment[4];
        fragments[0] = new LastOpenSourceFragment();
        fragments[1] = new NewBlogFragment();
        fragments[2] = new HotOpenSourceFragment();
        fragments[3] = new MyViewFragment();
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments,
                new String[]{"最新项目", "最新博客", "热门框架", "自定义组件"});
        mainViewPager.setAdapter(adapter);
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
        return super.onCreateOptionsMenu(menu);
    }
}
