package co.in.replete.komalindustries.beans.entity;

public class UserDetailsAssociatedTO {


  private int userDistributionListId;
  
  private String trackId;
  
  private String firstName;
  
  private String lastName;
  
  private String displayName;
	
  public UserDetailsAssociatedTO(){}

  public int getUserDistributionListId() {
	return userDistributionListId;
  }

  public void setUserDistributionListId(int userDistributionListId) {
	this.userDistributionListId = userDistributionListId;
  }

  public String getTrackId() {
	return trackId;
  }

  public void setTrackId(String trackId) {
	this.trackId = trackId;
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
