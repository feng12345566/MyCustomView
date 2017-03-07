package com.fyc.admin.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyc.admin.bean.HotProject;
import com.fyc.admin.bean.HotProjectGroup;
import com.fyc.admin.mycustomview.R;

import java.util.List;

/**
 * Created by Admin on 2017/3/6.
 */

public class HotProjectApater extends BaseExpandableListAdapter {
    private Context context;
    private List<HotProjectGroup> hotProjectGroups;
    private LayoutInflater inflater;

    public HotProjectApater(Context context, List<HotProjectGroup> hotProjectGroups) {
        this.context = context;
        this.hotProjectGroups = hotProjectGroups;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return hotProjectGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return hotProjectGroups.get(groupPosition).getHotProjectList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return hotProjectGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return hotProjectGroups.get(groupPosition).getHotProjectList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition * 1000 + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_hotprj_group, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.group_name);
        name.setText(hotProjectGroups.get(groupPosition).getGroupName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        HotProject hotProject = (HotProject) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_hotprj, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.descb = (TextView) convertView.findViewById(R.id.descb);
            viewHolder.showpic = (ImageView) convertView.findViewById(R.id.showpic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(hotProject.getTitle());
        if (viewHolder.descb != null) {
            viewHolder.descb.setText(hotProject.getDesc());
        }
        if (TextUtils.isEmpty(hotProject.getGifUrl())) {
            viewHolder.showpic.setVisibility(View.GONE);
        } else {
            viewHolder.showpic.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class ViewHolder {
        ImageView showpic;
        TextView title;
        TextView descb;

    }
}
