package com.fyc.admin.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.fyc.admin.bean.ApkInfo;
import com.fyc.admin.mycustomview.R;
import com.fyc.admin.utils.common.PackageUtil;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Admin on 2017/3/5.
 */

public class WebViewFragment extends Fragment {
    private WebView webView;
    private CircularProgressBar progressDialog;
    private Observable<String> observable;
    private Subscriber<String> subscriber;
    private ArrayList<String> map = new ArrayList<String>();

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        webView = (WebView) view.findViewById(R.id.webview);
        progressDialog = (CircularProgressBar) view.findViewById(R.id.circularProgressBar);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new FromJs(), "fyc");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                System.out.println(consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress > 99) {
                    progressDialog.setVisibility(View.GONE);
                    loadApp();
                }
            }
        });
        webView.loadUrl("http://www.iygdy.com:13312/app/installedpackages.html");
        return view;
    }

    /**
     * 方法上一定要加上注解@JavascriptInterface，否则无法调用
     */
    public class FromJs {
        @JavascriptInterface
        public void openApp(final String packageName) {
            try {
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void loadApp() {
        subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                if (!map.contains(s)) {
                    map.add(s);
                    webView.loadUrl(s);
                }
            }
        };
        observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                ArrayList<ApkInfo> apks = PackageUtil.getInstalledPackages(getActivity());
                for (int i = 0; i < apks.size(); i++) {
                    subscriber.onNext(String.format("javascript:listpackages('%s')", JSON.toJSONString(apks.get(i))));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
