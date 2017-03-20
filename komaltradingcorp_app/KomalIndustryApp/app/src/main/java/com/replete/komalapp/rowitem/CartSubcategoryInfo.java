package com.replete.komalapp.rowitem;

/**
 * Created by Replete Android on 9/4/2016.
 */
public class CartSubcategoryInfo {

    private int subcategoryId;
    private String subcategoryName;

    public CartSubcategoryInfo(int subcategoryId, String subcategoryName) {
        this.subcategoryId = subcategoryId;
        this.subcategoryName = subcategoryName;
    }


    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }


    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }
}
