package com.project2.banhangmypham.model;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String full_name, profile_image, address, phone, uid, username, date, password;
    private boolean banned;
    private boolean is_admin;
    private Date created_date ;
    private Date modified_date ;

    public User() {
    }

    public User(String full_name, String profile_image, String address, String phone, String uid, String username, String date, String password, boolean banned, boolean is_admin) {
        this.full_name = full_name;
        this.profile_image = profile_image;
        this.address = address;
        this.phone = phone;
        this.uid = uid;
        this.username = username;
        this.date = date;
        this.password = password;
        this.banned = banned;
        this.is_admin = is_admin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }


    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getModified_date() {
        return modified_date;
    }

    public void setModified_date(Date modified_date) {
        this.modified_date = modified_date;
    }

    boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }
    public boolean isValid() {
        if (isEmailValid(this.username) && this.password.length() > 6 ){
            return true ;
        }
        return false;
    }
}
