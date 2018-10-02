package com.lynx.formi.ulsucanteen.presentation.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lynx.formi.ulsucanteen.other.utils.TitleProvider;
import com.lynx.formi.ulsucanteen.other.events.TitleEvent;

import com.arellomobile.mvp.MvpAppCompatFragment;

import com.lynx.formi.ulsucanteen.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.greenrobot.eventbus.EventBus;

public class MainFragment extends MvpAppCompatFragment implements MainView, TitleProvider {
    public static final String TAG = "MainFragment";
    private final String TITLE = "Главная";
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

        presenter.hideToolbarIcon();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setTitle(TITLE);
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
