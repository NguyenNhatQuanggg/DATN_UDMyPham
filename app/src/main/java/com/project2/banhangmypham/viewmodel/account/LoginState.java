package com.project2.banhangmypham.viewmodel.account;

import com.project2.banhangmypham.model.User;

public class LoginState {
    private User user;
    private boolean isSuccess ;
    private String error ;

    public LoginState(User id, boolean isSuccess, String error) {
        this.user = id;
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getError() {
        return error;
    }
}
