package com.fyc.admin.mycustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Admin on 2017/3/4.
 */

public class WidgetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Fragment fragment = null;
        Intent intent = getIntent();
        String title = intent.getStringExtra("name");
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String fragmentName = intent.getStringExtra("fragmentName");
        Class onwClass = null;
        try {
            onwClass = Class.forName(fragmentName);
            Object o = onwClass.newInstance();
            fragment = (Fragment) o;
            getSupportFragmentManager().beginTransaction().replace(R.id.widget_container, fragment).commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
