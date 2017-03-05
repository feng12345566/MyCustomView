package com.fyc.admin.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.fyc.admin.mycustomview.R;
import com.fyc.admin.utils.common.PackageUtil;

/**
 * Created by Admin on 2017/3/5.
 */

public class WebViewFragment extends Fragment {
    private WebView webView;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        webView = (WebView) view.findViewById(R.id.webview);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new FromJs(), "fyc");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String script = String.format("javascript:listpackages('%s')", JSON.toJSONString(PackageUtil.getInstalledPackages(getActivity())));
                view.loadUrl(script);
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


}
