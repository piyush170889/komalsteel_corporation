package com.replete.komalapp.rowitem;

/**
 * Created by MR JOSHI on 26-Apr-16.
 */
public class SubCategoryOfCategory {
    private String categoryIdOfSubcategory;

    private String subCategoryId;

    private String subCategoryName;

    public SubCategoryOfCategory() {
    }

    public SubCategoryOfCategory(String categoryIdOfSubcategory, String subCategoryId, String subCategoryName) {
        this.categoryIdOfSubcategory = categoryIdOfSubcategory;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
    }


    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getCategoryIdOfSubcategory() {
        return categoryIdOfSubcategory;
    }

    public void setCategoryIdOfSubcategory(String categoryIdOfSubcategory) {
        this.categoryIdOfSubcategory = categoryIdOfSubcategory;
    }
}
