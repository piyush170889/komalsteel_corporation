package com.replete.komalapp.rowitem;

import java.util.List;

/**
 * Created by Replete Android on 9/4/2016.
 */
public class CartProductNSubCategories {

    private int subcategoryId;
    private String subcategoryName;
    private List<CartProducts> cartProductsList;


    public CartProductNSubCategories(int subcategoryId, String subcategoryName, List<CartProducts> cartProductsList) {
        this.subcategoryId = subcategoryId;
        this.subcategoryName = subcategoryName;
        this.cartProductsList = cartProductsList;
    }

    public CartProductNSubCategories() {
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

    public List<CartProducts> getCartProductsList() {
        return cartProductsList;
    }

    public void setCartProductsList(List<CartProducts> cartProductsList) {
        this.cartProductsList = cartProductsList;
    }
}
