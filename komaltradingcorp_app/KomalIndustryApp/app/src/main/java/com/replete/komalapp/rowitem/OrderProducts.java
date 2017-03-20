package com.replete.komalapp.rowitem;

/**
 * Created by MR JOSHI on 12-Apr-16.
 */
public class OrderProducts {

    private String ProductTitle;
    private String packageQuantity;
    private String productPrice;
    private String productType;



    public OrderProducts(String productTitle, String packageQuantity, String productPrice, String productType) {
        ProductTitle = productTitle;
        this.packageQuantity = packageQuantity;
        this.productPrice = productPrice;
        this.productType = productType;
    }

    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public String getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(String packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
