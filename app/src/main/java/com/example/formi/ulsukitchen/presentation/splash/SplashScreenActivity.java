package com.example.formi.ulsukitchen.presentation.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.arellomobile.mvp.MvpAppCompatActivity;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.formi.ulsukitchen.presentation.gcontainer.ContainerActivity;

public class SplashScreenActivity extends MvpAppCompatActivity implements SplashScreenView {
    public static final String TAG = "SplashScreenActivity";
    @InjectPresenter
    SplashScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
        finish();
    }
}
