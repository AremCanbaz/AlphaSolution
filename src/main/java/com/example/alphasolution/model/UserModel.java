package com.example.alphasolution.model;

public class UserModel {
    private int userid;
    private String username;
    private String password;
    private String email;

    public UserModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public UserModel(){

    }
    public int getId(){
        return userid;
    }
    public void setId(int id){
        this.userid = id;
    }
    public String getUsername(){
        return username;

    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

}
