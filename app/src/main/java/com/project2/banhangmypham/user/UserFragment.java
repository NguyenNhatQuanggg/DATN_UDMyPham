package com.project2.banhangmypham.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.project2.banhangmypham.common_screen.LoginActivity;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.databinding.FragmentUserBinding;
import com.project2.banhangmypham.repository.account.AccountRepositoryImpl;
import com.project2.banhangmypham.viewmodel.account.AccountViewModel;

public class UserFragment extends Fragment implements View.OnClickListener {

    DatabaseReference ref;
    Intent intent;
    private AccountViewModel accountViewModel ;
    private FragmentUserBinding binding ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        binding = FragmentUserBinding.inflate(getLayoutInflater());
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        accountViewModel.setAccountRepository(new AccountRepositoryImpl());
        accountViewModel.getUserInfoLiveData().observe((LifecycleOwner) requireContext(), result ->{
            if (result != null) {
                binding.userName.setText(result.getFull_name());
                binding.userEmail.setText(result.getUsername());
                binding.userDate.setText(result.getDate());
                Glide.with(requireActivity()).load(result.getProfile_image())
                        .placeholder(R.drawable.noimage).into(binding.userImg1);
            }
        });
        loadData();
        binding.btnUserGiohang.setOnClickListener(this);
        binding.userYeuthich.setOnClickListener(this);
        binding.userDangxuat.setOnClickListener(this);
        binding.userHotro.setOnClickListener(this);
        binding.userDiachi.setOnClickListener(this);
        binding.userCaidat.setOnClickListener(this);
        return binding.getRoot();
    }

    private void loadData() {
        accountViewModel.getInformationUser(FirebaseAuth.getInstance().getUid());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_user_giohang) {
           // intent = new Intent(requireActivity(), GioHangActivity.class);
            startActivity(intent);
        }  else if (id == R.id.user_yeuthich) {
           // intent = new Intent(requireActivity(), YeuThichActivity.class);
            startActivity(intent);
        }  else if (id == R.id.user_dangxuat) {
            accountViewModel.logOut();
            requireActivity().finish();
            startActivity(new Intent(requireActivity(), LoginActivity.class));
        } else if (id == R.id.user_diachi) {
            intent = new Intent(requireActivity(), UserActivity.class);
            startActivity(intent);
        } else if (id == R.id.user_hotro) {
            intent = new Intent(requireActivity(), InfoActivity.class);
            startActivity(intent);
        } else if (id == R.id.user_caidat) {
            intent = new Intent(requireActivity(), SettingsActivity.class);
            startActivity(intent);
        }
    }
}