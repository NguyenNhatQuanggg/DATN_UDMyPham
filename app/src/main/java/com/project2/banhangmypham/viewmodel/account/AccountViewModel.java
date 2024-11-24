package com.project2.banhangmypham.viewmodel.account;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.model.User;
import com.project2.banhangmypham.repository.account.IAccountRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountViewModel extends ViewModel {
    public static final String TAG = "AccountViewModel";
    private IAccountRepository accountRepository ;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<LoginState> _loggedLiveData = new MutableLiveData<>();
    private LiveData<LoginState> loggedLiveData = _loggedLiveData;

    private final MutableLiveData<SignUpState> _signUpLiveData = new MutableLiveData<>();
    private LiveData<SignUpState> signUpLiveData = _signUpLiveData;

    private final MutableLiveData<Boolean> _logOutLiveData = new MutableLiveData<>();
    private LiveData<Boolean> logOutLiveData = _logOutLiveData;

    private final MutableLiveData<String> _uploadImageLiveData = new MutableLiveData<>();
    private LiveData<String> uploadImageLiveData = _uploadImageLiveData;

    private final MutableLiveData<User> _userInfoLiveData = new MutableLiveData<>();
    private LiveData<User> userInfoLiveData = _userInfoLiveData;

    public LiveData<User> getUserInfoLiveData() {
        return userInfoLiveData;
    }

    public LiveData<LoginState> getLoggedLiveData() {
        return loggedLiveData;
    }

    public LiveData<SignUpState> getSignUpLiveData() {
        return signUpLiveData;
    }

    public LiveData<String> getUploadImageLiveData() {
        return uploadImageLiveData;
    }

    public LiveData<Boolean> getLogOutLiveData() {
        return logOutLiveData;
    }

    public void setAccountRepository(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void getInformationUser(String uid ) {
        Disposable disposable = accountRepository.getInfoUserFromServer(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_userInfoLiveData::postValue,
                        error -> {
                            Log.d(TAG, "uploadImage: ===> error = " + error.getMessage());
                            _userInfoLiveData.postValue(null);
                        }
                );
        compositeDisposable.add(disposable);
    }

    public void uploadImage(Uri uri) {
        Disposable disposable = accountRepository.uploadImage(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_uploadImageLiveData::postValue,
                        error -> {
                            Log.d(TAG, "uploadImage: ===> error = " + error.getMessage());
                            _uploadImageLiveData.postValue(null);
                        }
                );
        compositeDisposable.add(disposable);
    }

    public void login(String password, String email) {
        Disposable disposable = accountRepository.logIn(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                            Log.d(TAG, "login: ====> result - "+ result);
                                _loggedLiveData.postValue(new LoginState(
                                        result,
                                        true,
                                        null
                                ));
                            },
                            error ->{
                                _loggedLiveData.postValue(new LoginState(
                                        null,
                                        false,
                                        error.getMessage()
                                ));
                            }
                        );

        compositeDisposable.add(disposable);
    }

    public void logOut() {
        Disposable disposable = accountRepository.logOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->{
                            _logOutLiveData.postValue(true);
                        },
                        error ->{
                            _logOutLiveData.postValue(false);
                        }
                );
        compositeDisposable.add(disposable);
    }
    public void signUp(User user){
        Disposable disposable = accountRepository.signUp(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->{
                            _signUpLiveData.postValue(new SignUpState(true, null));
                        },
                        error ->{
                            _signUpLiveData.postValue(new SignUpState(false, error.getMessage()));

                        }
                );
        compositeDisposable.add(disposable);
    }
    public void changePassword(String newPassword) {
        Disposable disposable = accountRepository.changePassword(newPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->{
                            _signUpLiveData.postValue(new SignUpState(true, null));
                        },
                        error ->{
                            _signUpLiveData.postValue(new SignUpState(false, error.getMessage()));

                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        compositeDisposable = null;
        super.onCleared();
    }


}
