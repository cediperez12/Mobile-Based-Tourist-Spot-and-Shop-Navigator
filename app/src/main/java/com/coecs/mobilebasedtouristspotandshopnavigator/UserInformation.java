package com.coecs.mobilebasedtouristspotandshopnavigator;

public class UserInformation {

    //This class is the model for holding the data of General Users Information.

    private String firstName;
    private String lastName;

    private String profilePhotoPath;

    private String emailAddress;
    private String password;

    public UserInformation() {
    }

    public UserInformation(String firstName, String lastName, String profilePhotoPath, String emailAddress, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhotoPath = profilePhotoPath;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
