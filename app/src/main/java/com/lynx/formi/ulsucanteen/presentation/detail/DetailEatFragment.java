package com.lynx.formi.ulsucanteen.presentation.detail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;
import com.lynx.formi.ulsucanteen.other.Constants;
import com.lynx.formi.ulsucanteen.other.utils.BackButtonListener;
import com.lynx.formi.ulsucanteen.other.utils.RouterProvider;
import com.lynx.formi.ulsucanteen.presentation.adding.AddingDialogFragment;
import com.squareup.picasso.Picasso;

public class DetailEatFragment extends MvpAppCompatFragment implements DetailEatView, BackButtonListener {
    public static final String TAG = "DetailEatFragment";
    @InjectPresenter
    DetailEatPresenter presenter;

    @ProvidePresenter
    DetailEatPresenter provideDetailEatPresenter(){
        return new DetailEatPresenter(((RouterProvider)getParentFragment()).getRouter());
    }

    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private ImageView collapsingImage;
    private TextView txtDescription;

    private FloatingActionButton fab;

    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle args = getArguments();

            id = args.getString(Constants.BundleKeys.ID_KEY);
        }

        presenter.getDate(id);
    }

    public static DetailEatFragment newInstance(Bundle args) {
        DetailEatFragment fragment = new DetailEatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_eat, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.hideToolbarAndBNV();

        collapsingImage = view.findViewById(R.id.collapsingImage);
        collapsingToolbar = view.findViewById(R.id.collapsingToolbar);
        toolbar = view.findViewById(R.id.toolbar_collapsing);
        toolbar.setNavigationOnClickListener((v -> onBackPressed()));
        txtDescription = view.findViewById(R.id.txtDescription);

        fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener((v) -> goToAdd());
    }

    private void goToAdd() {
        final Bundle args = new Bundle();
        final Food food = App.getDBRepository().getFoodById(id);
        args.putParcelable(Constants.BundleKeys.EAT_KEY, food);

        final AddingDialogFragment addingDialogFragment = AddingDialogFragment.newInstance(args);
        addingDialogFragment.setCancelable(false);
        addingDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
    }

    @Override
    public void setTitle(String title) {
        collapsingToolbar.setTitle(title);
    }

    @Override
    public void setImage(String imageUrl) {
        Picasso.with(getActivity()).load(imageUrl).into(collapsingImage);
    }

    @Override
    public void setDescription(String description) {
        txtDescription.setText(description);
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}
