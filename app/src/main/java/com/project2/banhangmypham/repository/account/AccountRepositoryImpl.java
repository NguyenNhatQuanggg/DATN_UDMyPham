package com.project2.banhangmypham.repository.account;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.project2.banhangmypham.model.User;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import java.util.Objects;
import java.util.UUID;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AccountRepositoryImpl implements IAccountRepository {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public static final String TAG = "AccountRepositoryImpl";
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    public final ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @SuppressLint("CheckResult")
    @Override
    public Single<User> logIn(String email, String password) {
        return Single.create(emitter -> {
            User user = new User();
            user.setUsername(email);
            user.setPassword(password);
            String jsonRequest = new Gson().toJson(user);
            Log.d(TAG, "logIn: ====> request = "+jsonRequest);
            apiClientService.makeRequest("http://10.0.2.2:5000/user/login", ApiMethod.POST, User.class,
                            RequestBody.create(jsonRequest,JSON)
                    ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
    @Override
    public Completable logOut() {
        return Completable.create(emitter -> {
           mAuth.signOut();
           emitter.onComplete();
        });
    }
    @SuppressLint("CheckResult")
    @Override
    public Completable signUp(User user) {
        String jsonRequest = new Gson().toJson(user);
        Log.d(TAG, "signUp: ==> jsonRequest =  "+ jsonRequest);
        return Completable.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/user/register", ApiMethod.POST, User.class,
                        RequestBody.create(jsonRequest,JSON)
                    ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if (user != null) {
                            emitter.onComplete();
                        }else {
                            emitter.onError(new Throwable("Object is null"));
                        }
                    }, emitter::onError);
        });
    }
    @Override
    public Single<String> uploadImage(Uri uri) {
        return Single.create(emitter -> {
            StorageReference reference = storageRef.child("images/"+ UUID.randomUUID().toString());
            reference.putFile(uri).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    emitter.onSuccess(uri.toString());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    emitter.onError(e);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }
    @Override
    public Single<User> getInfoUserFromServer(String id) {
        return Single.create(emitter -> {
            databaseReference.child("Users").child(id)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists() ){
                                User user = snapshot.getValue(User.class);
                                emitter.onSuccess(user);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            emitter.onError(new Throwable(error.getMessage()));
                        }
                    });
        });
    }

    @Override
    public Completable changePassword(String newPassword) {
        return Completable.create(emitter -> {

        });
    }
}
