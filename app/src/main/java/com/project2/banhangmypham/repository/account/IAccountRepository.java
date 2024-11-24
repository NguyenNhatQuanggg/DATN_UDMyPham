package com.project2.banhangmypham.repository.account;

import android.net.Uri;

import com.project2.banhangmypham.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface IAccountRepository {
    Single<User> logIn(String email, String password);
    Completable logOut();
    Completable signUp(User user);
    Single<String> uploadImage(Uri uri);
    Single<User> getInfoUserFromServer(String id);
    Completable changePassword(String newPassword);
}
