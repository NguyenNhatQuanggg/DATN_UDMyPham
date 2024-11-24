package com.project2.banhangmypham.common_screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DatabaseReference;
import com.project2.banhangmypham.animation.LoadingDialog;
import com.project2.banhangmypham.databinding.ActivityRegisterBinding;
import com.project2.banhangmypham.model.User;
import com.project2.banhangmypham.repository.account.AccountRepositoryImpl;
import com.project2.banhangmypham.viewmodel.account.AccountViewModel;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference mData;
    int REQUEST_CODE_IMAGE = 1;
    LoadingDialog loadingDialog;
    private AccountViewModel accountViewModel ;
    int image = 0;
    String uriImage;
    private ActivityRegisterBinding binding ;

    private final ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    binding.cardviewAvatar.setImageURI(uri);
                    accountViewModel.uploadImage(uri);
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.setAccountRepository(new AccountRepositoryImpl());
        binding.cardviewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                        .build());
            }
        });

        accountViewModel.getUploadImageLiveData().observe(this, result ->{
            if (result != null) {
                image = 1 ;
                uriImage = result;
                Toast.makeText(this, "Upload image successfully", Toast.LENGTH_SHORT).show();
            }
        });
        accountViewModel.getSignUpLiveData().observe(this, signUpState -> {
            if (signUpState.isSuccess()){
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.registerUser.getText().toString().isEmpty() ||
                        binding.registerPass.getText().toString().isEmpty() ||
                        binding.registerTen.getText().toString().isEmpty() ||
                        binding.registerDiachi.getText().toString().isEmpty() ||
                        image == 0) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng thêm đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // call dang kii
                        User user = new User();
                        user.setUsername(binding.registerUser.getText().toString());
                        user.setFull_name(binding.registerTen.getText().toString());
                        user.setPhone(binding.registerPhone.getText().toString());
                        user.setPassword(binding.registerPass.getText().toString().trim());
                        user.setBanned(false);
                        user.setIs_admin(false);
                        if (user.isValid()){
                            accountViewModel.signUp(user);
                        }else {
                            Toast.makeText(RegisterActivity.this, "Tai khoan hoac mat khau khong dung", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });
    }

}
