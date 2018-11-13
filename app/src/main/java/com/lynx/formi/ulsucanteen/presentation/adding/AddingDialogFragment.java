package com.lynx.formi.ulsucanteen.presentation.adding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;
import com.lynx.formi.ulsucanteen.other.Constants;
import com.lynx.formi.ulsucanteen.other.events.HideLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.BucketSetChangedEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowMessageEvent;

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

    private Food food;

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

            food = args.getParcelable(Constants.BundleKeys.EAT_KEY);

            title = food.title;
            singlePrice = Integer.parseInt(food.price);
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
        btnAddToLoot.setOnClickListener((v) -> addToLoot(food, count));

//        btnMinus.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
        btnMinus.setClickable(false);

        btnPlus.setOnClickListener((v) -> onPlusClick());

        btnMinus.setOnClickListener((v) -> onMinusClick());
    }

    private void onPlusClick(){
        if (count > 8) {
            btnPlus.setClickable(false);
            return;
        }

        if (count == 8) {
            count++;
            txtCount.setText(String.valueOf(count));
            totalPrice = singlePrice * count;
            txtPrice.setText(String.valueOf(totalPrice));

            btnPlus.setClickable(true);
            return;
        }

        btnMinus.setClickable(true);
        count++;
        txtCount.setText(String.valueOf(count));

        totalPrice = singlePrice * count;
        txtPrice.setText(String.valueOf(totalPrice));
    }

    private void onMinusClick(){
        if (count < 2) {
            btnMinus.setClickable(false);
            return;
        }

        if (count == 2) {
            count--;
            txtCount.setText(String.valueOf(count));
            totalPrice = singlePrice * count;
            txtPrice.setText(String.valueOf(totalPrice));

            btnMinus.setClickable(false);
            return;
        }

        btnPlus.setClickable(true);
        count--;
        txtCount.setText(String.valueOf(count));
        totalPrice = singlePrice * count;
        txtPrice.setText(String.valueOf(totalPrice));
    }

    @Override
    public void notifyEatAdded(final String title, final boolean isMax) {
        dismiss();
        String message;
        if(isMax){
            message = title + " не умещается в корзину(";
        }else{
            message = title + " добавлен(а) в корзину";
        }
        EventBus.getDefault().post(new ShowMessageEvent(message));
    }

    // Presenter не успевает создаться (?) :(
    private void addToLoot(final Food food, final int count){
        EventBus.getDefault().post(new ShowLoaderEvent());

        food.count = String.valueOf(count);
        boolean isMax = App.getDBRepository().addFoodToBucket(food);
        EventBus.getDefault().post(new BucketSetChangedEvent());
        notifyEatAdded(food.title, isMax);

        EventBus.getDefault().post(new HideLoaderEvent());
    }
}
