package com.replete.komalapp.rowitem;

/**
 * Created by MR JOSHI on 31-Mar-16.
 */
public class AllDistributors {

    String id;
    String firstName;
    String lastName;
    String displayName;

    public AllDistributors(String id, String firstName, String lastName, String displayName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
    }

    public AllDistributors() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
