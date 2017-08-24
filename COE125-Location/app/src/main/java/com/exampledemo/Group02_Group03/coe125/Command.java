package com.exampledemo.Group02_Group03.coe125;

/**
 * Created by Jzon on 8/6/2017.
 */

public class Command {
    String userComm;
    String UserName;
    String Email;
    String Address;
    String Password;
    //String userName;

    public Command(String userComm,String UserName,String Email,String Address,String Password) {
        this.userComm = userComm;
        this.UserName=UserName;
        this.Email=Email;
        this.Address=Address;
        this.Password=Password;
        //this.userName = userName;
    }

    //public String getUserID() {
     //   return userName;
    //}
    public String getUserComm() {
        return userComm;


    }

}
