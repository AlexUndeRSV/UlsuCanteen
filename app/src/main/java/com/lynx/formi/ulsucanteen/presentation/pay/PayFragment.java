package com.lynx.formi.ulsucanteen.presentation.pay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.lynx.formi.ulsucanteen.other.events.HideBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.utils.BackButtonListener;
import com.lynx.formi.ulsucanteen.other.utils.RouterProvider;
import com.lynx.formi.ulsucanteen.presentation.pay.PayView;
import com.lynx.formi.ulsucanteen.presentation.pay.PayPresenter;

import com.arellomobile.mvp.MvpAppCompatFragment;

import com.lynx.formi.ulsucanteen.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.greenrobot.eventbus.EventBus;

public class PayFragment extends MvpAppCompatFragment implements PayView, BackButtonListener {
    public static final String TAG = "PayFragment";
    private final String TITLE = "Оплата";
    @InjectPresenter
    PayPresenter presenter;

    @ProvidePresenter
    PayPresenter providePayPresenter(){
        return new PayPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

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

        EventBus.getDefault().post(new HideBottomNavigationEvent());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setTitle(TITLE);
        presenter.showToolbarIcon();
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}
