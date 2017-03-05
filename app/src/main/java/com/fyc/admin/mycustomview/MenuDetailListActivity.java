package com.fyc.admin.mycustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.fyc.admin.bean.MenuDetail;
import com.fyc.admin.view.MenuLevelRecycleView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Admin on 2017/3/4.
 */

public class MenuDetailListActivity extends AppCompatActivity {

    @InjectView(R.id.menu_detail_list)
    MenuLevelRecycleView menuDetailList;


    private ArrayList<MenuDetail> menuDetails = new ArrayList<>();

    ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menudetail);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        menuDetailList.init(this);
        Intent intent = getIntent();
        int index = intent.getIntExtra("position", 0);
        initData(index);
        menuDetailList.setMenuList(list);
        menuDetailList.setOnItemClickListener(new MenuLevelRecycleView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent1 = null;
                MenuDetail menuDetail = menuDetails.get(postion);
                switch (menuDetail.getPageType()) {
                    case MenuDetail.PageType.LINK:
                        intent1 = new Intent(MenuDetailListActivity.this, WebViewActivity.class);
                        intent1.putExtra("url", menuDetail.getUrl());
                        intent1.putExtra("name", menuDetail.getName());
                        break;
                    case MenuDetail.PageType.APK:
                        break;
                    case MenuDetail.PageType.ACTIVITY:
                        intent1 = new Intent(MenuDetailListActivity.this, WidgetActivity.class);
                        intent1.putExtra("fragmentName", menuDetail.getUrl());
                        intent1.putExtra("name", menuDetail.getName());
                        break;
                }
                startActivity(intent1);
            }
        });
        toolbar.setTitle(intent.getStringExtra("title"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initData(int index) {
        switch (index) {
            case 0:
                menuDetails.add(new MenuDetail("TextView", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.TextViewFragment"));
                menuDetails.add(new MenuDetail("EditText", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.EditTextFragment"));
                menuDetails.add(new MenuDetail("AutoComplateTextView", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.AutoComplateEditTextFragment"));
                menuDetails.add(new MenuDetail("Button", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.ButtonFragment"));
                menuDetails.add(new MenuDetail("CheckBox", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.CheckBoxFragment"));
                menuDetails.add(new MenuDetail("RadioButton", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.RadioButtonFragment"));
                menuDetails.add(new MenuDetail("ImageView", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.ImageFragment"));
                menuDetails.add(new MenuDetail("WebView", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("GridView", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("ListView", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                break;
            case 1:
                menuDetails.add(new MenuDetail("仪表盘", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("进度条", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("日历", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("通讯录", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("电子书阅读器", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                break;
            case 2:
                menuDetails.add(new MenuDetail("天气插件", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("新闻资讯插件", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("代办事项插件", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                break;
            case 3:
                menuDetails.add(new MenuDetail("音乐播放器", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("视频播放器", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("网络直播", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("短视频", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));

                break;
            case 4:
                menuDetails.add(new MenuDetail("辅助服务", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("短信", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("拨号", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("应用管理", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                break;
            case 5:
                menuDetails.add(new MenuDetail("蓝牙通讯", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("串口通讯", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("WIFI通讯", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("耳机接口通讯", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("网络通讯（文件上传下载）", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("网络通讯（socket）", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                break;
            case 6:
                menuDetails.add(new MenuDetail("数据库存储", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("文件存储", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("XML解析", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("图片压缩", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                break;
            case 7:
                menuDetails.add(new MenuDetail("GPS定位导航", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("加速度传感器", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("方向传感器（指南针）", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("录音", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("语音助手", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
                menuDetails.add(new MenuDetail("拍照截图", MenuDetail.PageType.ACTIVITY, "com.fyc.admin.fragment.WebViewFragment"));
        }
        for (int i = 0; i < menuDetails.size(); i++) {
            list.add(menuDetails.get(i).getName());
        }
    }

}
