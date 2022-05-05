package com.example.careshipapp.user_management;

public class User {
    public String email, password, postAddress;
    Integer postCode;

    public User(){
    }

    public User(String email,String password,String postAddress,Integer postCode){
        this.email = email;
        this.password = password;
        this.postAddress = postAddress;
        this.postCode = postCode;
    }
}
