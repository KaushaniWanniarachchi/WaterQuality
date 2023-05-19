package com.cy.smartsecure;

public class ReadWriteUserDetails {

    public String firstName, lastName  ,nicNumber ,mobileNumber ;


    public String Turbidity;
    public String WaterQuality;
    public String Tds;

    public ReadWriteUserDetails(){};

    public ReadWriteUserDetails(String firstName, String lastName, String nicNumber ,String mobileNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.nicNumber = nicNumber;
        this.mobileNumber = mobileNumber;

    }



}
