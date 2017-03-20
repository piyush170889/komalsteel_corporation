package com.replete.komalapp.StaggeredGridView;

public class Item {
    public String url;
    public String categoryId;
    public String categoryName;

    public Item(String url, String categoryId, String categoryName) {
        this.url = url;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
