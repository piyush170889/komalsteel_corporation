package com.replete.komalapp.rowitem;

public class SubCategory {

    private String subCategoryId;
    private String subCategoryName;
    private String subCategoryCatDesc;
    private String subCategoryParantId;
    private String subCategoryUrl;
    private String categoryId;

    public SubCategory(String subCategoryId, String subCategoryName, String subCategoryCatDesc, String subCategoryParantId, String subCategoryUrl, String categoryId) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.subCategoryCatDesc = subCategoryCatDesc;
        this.subCategoryParantId = subCategoryParantId;
        this.subCategoryUrl = subCategoryUrl;
        this.categoryId = categoryId;
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

    public String getSubCategoryCatDesc() {
        return subCategoryCatDesc;
    }

    public void setSubCategoryCatDesc(String subCategoryCatDesc) {
        this.subCategoryCatDesc = subCategoryCatDesc;
    }

    public String getSubCategoryParantId() {
        return subCategoryParantId;
    }

    public void setSubCategoryParantId(String subCategoryParantId) {
        this.subCategoryParantId = subCategoryParantId;
    }

    public String getSubCategoryUrl() {
        return subCategoryUrl;
    }

    public void setSubCategoryUrl(String subCategoryUrl) {
        this.subCategoryUrl = subCategoryUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
