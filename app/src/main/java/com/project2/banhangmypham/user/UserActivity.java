package com.project2.banhangmypham.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.project2.banhangmypham.databinding.ActivityUserBinding;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.repository.account.AccountRepositoryImpl;
import com.project2.banhangmypham.viewmodel.account.AccountViewModel;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding ;
    private AccountViewModel accountViewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.setAccountRepository(new AccountRepositoryImpl());
        accountViewModel.getUserInfoLiveData().observe(this, result ->{
            if (result != null) {
//                binding.userName.setText(result.getName());
//                binding.userEmail.setText(result.getEmail());
//                binding.userDate.setText(result.getDate());
//                Glide.with(requireActivity()).load(result.getImageAvatar())
//                        .placeholder(R.drawable.noimage).into(binding.userImg1);
                binding.textEmail.setText(result.getUsername());
                binding.textDiachi.setText(result.getAddress());
                binding.textDate.setText(result.getDate());
                Glide.with(this).load(result.getProfile_image()).placeholder(R.drawable.noimage).into(binding.avatar);
            }
        });
        loadData();

        binding.btnSaveChangePass.setOnClickListener(view ->{
            String oldPassword = binding.edtPassword.getText().toString().trim();
            String newPassword = binding.edtNewPassword.getText().toString().trim();
            String confirmPassword = binding.edtNewPasswordConfirm.getText().toString().trim();
            if (newPassword.length() > 6 && newPassword.equals(confirmPassword)) {
                accountViewModel.changePassword(newPassword);
            }else {

            }
        });
    }
    private void loadData() {
        accountViewModel.getInformationUser(FirebaseAuth.getInstance().getUid());
    }

}
