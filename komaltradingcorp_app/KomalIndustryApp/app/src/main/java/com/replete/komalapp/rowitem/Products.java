package com.replete.komalapp.rowitem;

/**
 * Created by MR JOSHI on 09-Jul-16.
 */
public class Products  {
    private int ID;
    private String name;

    public Products(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return name;
    }
}
