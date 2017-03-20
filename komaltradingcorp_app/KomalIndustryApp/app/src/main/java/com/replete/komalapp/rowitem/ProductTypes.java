package com.replete.komalapp.rowitem;

import android.graphics.Bitmap;

/**
 * Created by MR JOSHI on 05-Apr-16.
 */
public class ProductTypes {
    private int productID;
    private String productName;
    private String productSize;
    private String productMasterCartonQtyRange;
    private String productMasterCartonQtyIncVal;

    public ProductTypes(int productID, String productName, String productSize, String productMasterCartonQtyRange, String productMasterCartonQtyIncVal) {
        this.productID = productID;
        this.productName = productName;
        this.productSize = productSize;
        this.productMasterCartonQtyRange = productMasterCartonQtyRange;
        this.productMasterCartonQtyIncVal = productMasterCartonQtyIncVal;
    }


    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductMasterCartonQtyRange() {
        return productMasterCartonQtyRange;
    }

    public void setProductMasterCartonQtyRange(String productMasterCartonQtyRange) {
        this.productMasterCartonQtyRange = productMasterCartonQtyRange;
    }

    public String getProductMasterCartonQtyIncVal() {
        return productMasterCartonQtyIncVal;
    }

    public void setProductMasterCartonQtyIncVal(String productMasterCartonQtyIncVal) {
        this.productMasterCartonQtyIncVal = productMasterCartonQtyIncVal;
    }
}