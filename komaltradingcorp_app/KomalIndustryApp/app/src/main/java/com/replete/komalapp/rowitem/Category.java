package com.replete.komalapp.rowitem;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

public class Category {

    private String itemId;
    private String itemName;
    //    private List<SubCategory> mSubCategories;

    public Category(String itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
