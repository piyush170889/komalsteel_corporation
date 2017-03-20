package com.replete.komalapp.helper;

/**
 * Created by MR JOSHI on 17-Mar-16.
 */
public class SubItem {

    private String subitemId;
    private String subItemName;

    public SubItem(String subitemId, String subItemName) {
        this.subitemId = subitemId;
        this.subItemName = subItemName;
    }

    public String getSubitemId() {
        return subitemId;
    }

    public void setSubitemId(String subitemId) {
        this.subitemId = subitemId;
    }

    public String getSubItemName() {
        return subItemName;
    }

    public void setSubItemName(String subItemName) {
        this.subItemName = subItemName;
    }
}
