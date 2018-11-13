package com.lynx.formi.ulsucanteen.presentation.pay;


import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.domain.dataclass.Order;
import com.lynx.formi.ulsucanteen.other.events.BucketSetChangedEvent;
import com.lynx.formi.ulsucanteen.other.events.HideBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.HideClearLootItemEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowMessageEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowToolbarIcon;
import com.lynx.formi.ulsucanteen.other.events.TitleEvent;
import com.lynx.formi.ulsucanteen.other.utils.RandomUtils;

import org.greenrobot.eventbus.EventBus;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class PayPresenter extends MvpPresenter<PayView> {

    private final Router router;

    private final DatabaseReference dbReference;

    public PayPresenter(final Router router) {
        this.router = router;
        dbReference = FirebaseDatabase.getInstance().getReference();
    }

    public void setTitle(final String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }

    public void onBackPressed() {
        router.exit();
    }

    public void showToolbarIcon() {
        EventBus.getDefault().post(new ShowToolbarIcon());
    }

    public void hideClearLootItem() {
        EventBus.getDefault().post(new HideClearLootItemEvent());
    }

    public void hideBNV() {
        EventBus.getDefault().post(new HideBottomNavigationEvent());
    }

    public void payRequest() {

        EventBus.getDefault().post(new ShowLoaderEvent());
        final Order order = new Order();
        order.setFoodList(App.getDBRepository().getBucketFoodList());

        dbReference.child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                final String generatedRandomKey = RandomUtils.generateRandomKey();

                if (!dataSnapshot.child(generatedRandomKey).exists()) {
                    dbReference.child("orders").child(generatedRandomKey).setValue(order);
                    App.getDBRepository().deleteBucket();
                    getViewState().notifyOrderAdded(generatedRandomKey);
                } else {
                    onDataChange(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {
                // TODO обработать ошибку
                EventBus.getDefault().post(new ShowMessageEvent(databaseError.getMessage()));
            }
        });
    }
}
