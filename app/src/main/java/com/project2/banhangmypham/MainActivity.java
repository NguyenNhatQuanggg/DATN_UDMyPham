package com.project2.banhangmypham;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.project2.banhangmypham.user.UserFragment;

public class MainActivity extends AppCompatActivity {
    private boolean doubleClick = false;
    BottomNavigationView bottomNav;
    int newPosition, startingPosition;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                fragment = null;
                newPosition = 0;

                int itemId = menuItem.getItemId();
                if (itemId == R.id.nav_trangchu) {
//                    fragment = new HomeFragment();
                    newPosition = 1;
                } else if (itemId == R.id.nav_danhmuc) {
//                    fragment = new ListFragment();
                    newPosition = 2;
                } else if (itemId == R.id.nav_tienich) {
//                    fragment = new TienIchFragment();
                    newPosition = 3;
                } else if (itemId == R.id.nav_thongbao) {
//                    fragment = new ThongBaoFragment();
                    newPosition = 4;
                } else if (itemId == R.id.nav_nguoidung) {
                    fragment = new UserFragment();
                    newPosition = 5;
                }
                return loadFragment(fragment, newPosition);
            }
        });

        Bundle getSelection = getIntent().getExtras();
        if (getSelection == null) {
//            loadFragment(new HomeFragment(), 1);
        } else {
//            loadFragment(new ListFragment(), 2);
            bottomNav.setSelectedItemId(R.id.nav_danhmuc);
        }
    }

    private boolean loadFragment(Fragment fragment, int newPosition) {
        if (fragment != null) {
            if (startingPosition > newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.commit();
            }
            if (startingPosition < newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.commit();
            }
            startingPosition = newPosition;
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (doubleClick)
            finish();
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show();
        doubleClick = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClick = false;
            }
        }, 2000);
    }
}
