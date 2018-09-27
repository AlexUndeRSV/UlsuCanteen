package com.example.formi.ulsukitchen.presentation.pay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.formi.ulsukitchen.presentation.pay.PayView;
import com.example.formi.ulsukitchen.presentation.pay.PayPresenter;

import com.arellomobile.mvp.MvpAppCompatFragment;

import com.example.formi.ulsukitchen.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

public class PayFragment extends MvpAppCompatFragment implements PayView {
    public static final String TAG = "PayFragment";
    private final String TITLE = "Оплата";
    @InjectPresenter
    PayPresenter presenter;


    public static PayFragment newInstance(Bundle args) {
        PayFragment fragment = new PayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pay, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setTitle(TITLE);
    }
}
