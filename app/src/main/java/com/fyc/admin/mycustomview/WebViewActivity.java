package com.fyc.admin.mycustomview;

import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fyc.admin.bean.UrlCollection;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by Admin on 2017/3/2.
 */

public class WebViewActivity extends AppCompatActivity {

    @InjectView(R.id.webview)
    WebView webview;

    private Toolbar toolbar;
    private String mUrl;
    private Realm myRealm;
    private boolean collected = false;
    private UrlCollection urlCollection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.inject(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myRealm = Realm.getDefaultInstance();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        //必须在setSupportActionBar之后才有效果
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mUrl = url;
        if (intent.hasExtra("urlCollection")) {
            urlCollection = JSON.parseObject(intent.getStringExtra("urlCollection"), UrlCollection.class);
        }
        initMenu();
        WebSettings webSettings = webview.getSettings();
        //设置WebView缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mUrl = url;
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                toolbar.setTitle(title);
            }
        });
        webview.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.copy_link:
                copyLink();
                break;
            case R.id.open_web:
                openBrowser();
                break;
            case R.id.collect_web:
                collection(!collected);
                Toast.makeText(WebViewActivity.this, collected ? "取消收藏成功" : "收藏成功", Toast.LENGTH_SHORT).show();
                collected = !collected;
                break;
        }
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (urlCollection == null) {
            menu.getItem(2).setVisible(false);
        } else {
            menu.getItem(2).setVisible(true);
            menu.getItem(2).setTitle(collected ? "取消收藏" : "收藏");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void openBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(mUrl);
        intent.setData(content_url);
        startActivity(intent);
    }

    private void copyLink() {
        ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cmb.setText(mUrl);
    }


    private void initMenu() {
        UrlCollection co = myRealm.where(UrlCollection.class).equalTo("url", urlCollection.getUrl()).findFirstAsync();
        co.addChangeListener(new RealmChangeListener<UrlCollection>() {
            @Override
            public void onChange(UrlCollection element) {
                if (element == null) {
                    collected = false;
                } else {
                    collected = true;
                    urlCollection = element;
                }
            }
        });
    }


    private void collection(boolean insert) {
        if (insert) {
            myRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(urlCollection);
                }
            });
        } else {
            //delete不能异步执行
            final RealmResults<UrlCollection> cos = myRealm.where(UrlCollection.class).equalTo("url", urlCollection.getUrl()).findAll();
            myRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    cos.deleteAllFromRealm();
                }
            });
        }
    }
}
