package com.example.formi.ulsukitchen.presentation.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.formi.ulsukitchen.presentation.gcontainer.ContainerActivity;

import com.arellomobile.mvp.MvpAppCompatFragment;

import com.example.formi.ulsukitchen.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

public class MainFragment extends MvpAppCompatFragment implements MainView {
    public static final String TAG = "MainFragment";
    @InjectPresenter
    MainPresenter presenter;


    public static MainFragment newInstance(Bundle args) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        ((ContainerActivity)getActivity()).setActionBarTitle("Главная");
    }
}
