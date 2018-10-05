package com.example.smit.edutech;

public class User {
    private String name,email;

    public User(String Name, String Email){
        this.name = Name;
        this.email = Email;
    }

    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
