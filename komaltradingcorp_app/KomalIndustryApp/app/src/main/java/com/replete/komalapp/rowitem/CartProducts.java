package com.replete.komalapp.rowitem;

import android.graphics.Bitmap;

/**
 * Created by MR JOSHI on 17-Mar-16.
 */
public class CartProducts {
    private String productId;
    private String productName;
    private String productType;
    private String productQuantity;
    private int subCategoryId;
    private int productQuantityRangeStart;
    private int productQuantityRangeEnd;
    private int productQuantityIncValue;


    public CartProducts() {
    }

    public CartProducts(String productId, String productName, String productType, String productQuantity, int subCategoryId, int productQuantityRangeStart, int productQuantityRangeEnd, int productQuantityIncValue) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productQuantity = productQuantity;
        this.subCategoryId = subCategoryId;
        this.productQuantityRangeStart = productQuantityRangeStart;
        this.productQuantityRangeEnd = productQuantityRangeEnd;
        this.productQuantityIncValue = productQuantityIncValue;
    }

   /* public CartProducts(String productId, String productName, String productType, String productQuantity, int subCategoryId) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productQuantity = productQuantity;
        this.subCategoryId = subCategoryId;
    }*/




    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }


    public int getProductQuantityIncValue() {
        return productQuantityIncValue;
    }

    public void setProductQuantityIncValue(int productQuantityIncValue) {
        this.productQuantityIncValue = productQuantityIncValue;
    }

    public int getProductQuantityRangeStart() {
        return productQuantityRangeStart;
    }

    public void setProductQuantityRangeStart(int productQuantityRangeStart) {
        this.productQuantityRangeStart = productQuantityRangeStart;
    }

    public int getProductQuantityRangeEnd() {
        return productQuantityRangeEnd;
    }

    public void setProductQuantityRangeEnd(int productQuantityRangeEnd) {
        this.productQuantityRangeEnd = productQuantityRangeEnd;
    }





    /*@Override
    public boolean equals(Object obj) {

        return (this.productId.equals(((CartProducts) obj).productId)
                && this.productName.equals(((CartProducts) obj).productName)
        );
    }*/
}
