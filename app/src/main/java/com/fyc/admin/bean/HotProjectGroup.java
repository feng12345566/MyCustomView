package com.fyc.admin.bean;

import java.util.List;

/**
 * Created by Admin on 2017/3/6.
 */

public class HotProjectGroup {
    private String groupName;
    private List<HotProject> hotProjectList;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<HotProject> getHotProjectList() {
        return hotProjectList;
    }

    public void setHotProjectList(List<HotProject> hotProjectList) {
        this.hotProjectList = hotProjectList;
    }
}
