package com.replete.komalapp.rowitem;

/**
 * Created by MR JOSHI on 30-Mar-16.
 */
public class AssociatedDistributor {
    String distributorId;
    String distributorFirstName;
    String distributorLastName;
    String distributorDisplayName;
    boolean isDeleted;

    public AssociatedDistributor(String distributorId, String distributorFirstName, String distributorLastName, String distributorDisplayName, boolean isDeleted) {
        this.distributorId = distributorId;
        this.distributorFirstName = distributorFirstName;
        this.distributorLastName = distributorLastName;
        this.distributorDisplayName = distributorDisplayName;
        this.isDeleted = isDeleted;
    }


    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorFirstName() {
        return distributorFirstName;
    }

    public void setDistributorFirstName(String distributorFirstName) {
        this.distributorFirstName = distributorFirstName;
    }

    public String getDistributorLastName() {
        return distributorLastName;
    }

    public void setDistributorLastName(String distributorLastName) {
        this.distributorLastName = distributorLastName;
    }

    public String getDistributorDisplayName() {
        return distributorDisplayName;
    }

    public void setDistributorDisplayName(String distributorDisplayName) {
        this.distributorDisplayName = distributorDisplayName;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
