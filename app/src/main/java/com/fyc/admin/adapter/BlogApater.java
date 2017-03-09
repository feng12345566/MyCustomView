package com.fyc.admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fyc.admin.bean.Blog;
import com.fyc.admin.mycustomview.R;
import com.fyc.admin.mycustomview.WebViewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Admin on 2017/3/6.
 */

public class BlogApater extends RecyclerView.Adapter<BlogApater.ViewHolder> {
    private Context context;
    private List<Blog> blogs;
    private LayoutInflater inflater;


    public BlogApater(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.layout_blog_withoutpic, parent, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.layout_blog_withpic, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Blog blog = blogs.get(position);
        holder.title.setText(blog.getArt_title().replaceAll("&quot;", " "));
        holder.desc.setText(blog.getArt_desc().replaceAll("&quot;", " "));
        holder.linkView.setText(blog.getArt_view() + "");
        holder.time.setText(blog.getArt_time());
        holder.source.setText("来源:" + blog.getArt_source().toUpperCase());
        if (holder.pic != null && !TextUtils.isEmpty(blogs.get(position).getArt_pic())) {
            ImageLoader.getInstance().displayImage(blog.getArt_pic(), holder.pic);
        }
        holder.blogContainer.setTag(blog.getArt_url());
        holder.blogContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = (String) v.getTag();
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogs == null ? 0 : blogs.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(blogs.get(position).getArt_pic())) {
            return 1;
        } else {
            return 2;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        TextView linkView;
        TextView source;
        TextView time;
        ImageView pic;
        LinearLayout blogContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);
            linkView = (TextView) itemView.findViewById(R.id.linkview);
            time = (TextView) itemView.findViewById(R.id.time);
            source = (TextView) itemView.findViewById(R.id.source);
            pic = (ImageView) itemView.findViewById(R.id.blog_pic);
            blogContainer = (LinearLayout) itemView.findViewById(R.id.blog_container);
        }
    }
}
