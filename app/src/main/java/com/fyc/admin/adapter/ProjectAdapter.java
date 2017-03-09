package com.fyc.admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fyc.admin.bean.UrlCollection;
import com.fyc.admin.mycustomview.R;
import com.fyc.admin.mycustomview.WebViewActivity;


import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Admin on 2017/3/2.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private JSONArray jsonArray;
    private LayoutInflater inflater;
    private Context context;

    public ProjectAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.jsonArray = jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_project, parent, false);
        //view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            final JSONObject object = jsonArray.getJSONObject(position);
            holder.desc.setText(object.getString("description"));
            holder.title.setText(object.getString("name"));
            holder.main_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url", object.getString("source"));
                    UrlCollection urlCollection = new UrlCollection();
                    urlCollection.setTitle(object.getString("name"));
                    urlCollection.setDesc(object.getString("description"));
                    urlCollection.setUrl(object.getString("source"));
                    urlCollection.setImg("");
                    intent.putExtra("urlCollection", JSON.toJSONString(urlCollection));
                    context.startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return jsonArray == null ? 0 : jsonArray.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        LinearLayout main_container;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);
            main_container = (LinearLayout) itemView.findViewById(R.id.main_container);
        }
    }
}
