package com.example.formi.ulsukitchen.data.presentation.adding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.formi.ulsukitchen.App;
import com.example.formi.ulsukitchen.R;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;
import com.example.formi.ulsukitchen.other.Constants;
import com.example.formi.ulsukitchen.other.events.HideLoaderEvent;
import com.example.formi.ulsukitchen.other.events.ShowLoaderEvent;
import com.example.formi.ulsukitchen.other.events.ShowMessageEvent;

import org.greenrobot.eventbus.EventBus;

public class AddingDialogFragment extends DialogFragment implements AddingDialogView {
    public static final String TAG = "AddingDialogFragment";
    @InjectPresenter
    AddingDialogPresenter presenter;

    private ImageButton btnMinus, btnPlus;
    private Button btnAddToLoot, btnClose;
    private TextView txtCount, txtPrice;

    private int count = 1;

    private String title;
    private int singlePrice, totalPrice = 0;

    private Eat eat;

    public static AddingDialogFragment newInstance(Bundle args) {
        AddingDialogFragment fragment = new AddingDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle args = getArguments();

            eat = args.getParcelable(Constants.BundleKeys.EAT_KEY);

            title = eat.title;
            singlePrice = Integer.parseInt(eat.price);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_loot, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(title);

        txtCount = view.findViewById(R.id.txtEatCount);
        txtCount.setText(String.valueOf(count));

        txtPrice = view.findViewById(R.id.txtAddingEatPrice);
        txtPrice.setText(String.valueOf(singlePrice));

        btnMinus = view.findViewById(R.id.btnCountMinus);
        btnPlus = view.findViewById(R.id.btnCountPlus);

        btnAddToLoot = view.findViewById(R.id.btnAddToLoot);
        btnClose = view.findViewById(R.id.btnCloseDialog);

        btnClose.setOnClickListener((v) -> this.dismiss());
        btnAddToLoot.setOnClickListener((v) -> addToLoot(eat, count));

        btnMinus.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
        btnMinus.setClickable(false);

        btnPlus.setOnClickListener((v) -> onPlusClick());

        btnMinus.setOnClickListener((v) -> onMinusClick());
    }

    private void onPlusClick(){
        if (count > 8) {
            btnPlus.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
            btnPlus.setClickable(false);
            return;
        }

        if (count == 8) {
            count++;
            txtCount.setText(String.valueOf(count));
            totalPrice = singlePrice * count;
            txtPrice.setText(String.valueOf(totalPrice));

            btnPlus.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
            btnPlus.setClickable(true);
            return;
        }

        btnMinus.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
        btnMinus.setClickable(true);
        count++;
        txtCount.setText(String.valueOf(count));

        totalPrice = singlePrice * count;
        txtPrice.setText(String.valueOf(totalPrice));
    }

    private void onMinusClick(){
        if (count < 2) {
            btnMinus.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
            btnMinus.setClickable(false);
            return;
        }

        if (count == 2) {
            count--;
            txtCount.setText(String.valueOf(count));
            totalPrice = singlePrice * count;
            txtPrice.setText(String.valueOf(totalPrice));

            btnMinus.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
            btnMinus.setClickable(false);
            return;
        }

        btnPlus.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
        btnPlus.setClickable(true);
        count--;
        txtCount.setText(String.valueOf(count));
        totalPrice = singlePrice * count;
        txtPrice.setText(String.valueOf(totalPrice));
    }

    public void eatAdded(String title, String count) {
        dismiss();
        EventBus.getDefault().post(new ShowMessageEvent(title + " добавлен(а) в корзину (x" + count + ")"));
    }

    public void addToLoot(Eat eat, int count) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        eat.count = String.valueOf(count);
        App.getDBRepository().addEatToLoot(eat);
        eatAdded(eat.title, String.valueOf(count));
        EventBus.getDefault().post(new HideLoaderEvent());
    }
}
