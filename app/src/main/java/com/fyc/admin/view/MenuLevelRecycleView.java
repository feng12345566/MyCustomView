package com.fyc.admin.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fyc.admin.mycustomview.R;

import java.util.ArrayList;

/**
 * Created by Admin on 2017/3/4.
 */

public class MenuLevelRecycleView extends RecyclerView {
    private ArrayList<String> menuList;
    private MenuLevelAdapter adapter;
    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MenuLevelRecycleView(Context context) {
        super(context);
    }

    public MenuLevelRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuLevelRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void init(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(linearLayoutManager);
        adapter = new MenuLevelAdapter(context);
        this.setAdapter(adapter);
    }


    public void setMenuList(ArrayList<String> menuList) {
        this.menuList = menuList;
        adapter.notifyDataSetChanged();
    }

    public class MenuLevelAdapter extends RecyclerView.Adapter<MenuLevelAdapter.ViewHolder> {

        private Context context;
        private LayoutInflater layoutInflater;

        public MenuLevelAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.item_menulevel, parent, false);
            view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.textView.setText(menuList.get(position));
            holder.container.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return menuList == null ? 0 : menuList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            private LinearLayout container;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.title);
                container = (LinearLayout) itemView.findViewById(R.id.menu_container);
            }
        }
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int postion);
    }
}
