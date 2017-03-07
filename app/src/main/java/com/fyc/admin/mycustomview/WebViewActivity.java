package com.fyc.admin.mycustomview;

import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Admin on 2017/3/2.
 */

public class WebViewActivity extends AppCompatActivity {

    @InjectView(R.id.webview)
    WebView webview;

    private Toolbar toolbar;
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.inject(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        //必须在setSupportActionBar之后才有效果
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String url = getIntent().getStringExtra("url");
        mUrl = url;
        WebSettings setting = webview.getSettings();
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setting.setUseWideViewPort(true);
        setting.setBuiltInZoomControls(true);
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
        setting.setSupportZoom(true);
        webview.setInitialScale(10);
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
        }
        return true;
    }


    private void openBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(mUrl);
        intent.setData(content_url);
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        startActivity(intent);
    }

    private void copyLink() {
        ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cmb.setText(mUrl);
    }
}
