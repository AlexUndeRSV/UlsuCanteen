package com.lynx.formi.ulsucanteen.presentation.bucket;


import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;
import com.lynx.formi.ulsucanteen.other.Screen;
import com.lynx.formi.ulsucanteen.other.events.ClearLootEvent;
import com.lynx.formi.ulsucanteen.other.events.HideLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.HideToolbarIcon;
import com.lynx.formi.ulsucanteen.other.events.BucketSetChangedEvent;
import com.lynx.formi.ulsucanteen.other.events.SelectItemEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowClearLootItemEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.TitleEvent;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class BucketPresenter extends MvpPresenter<BucketView> {
    private final Router router;

    // TODO Костыль - мокси запускает последний метод в IView при пересоздании
    private BucketFragment view;

    public BucketPresenter(Router router){
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getLootList();
    }

    public void onCreate(final BucketFragment view){
        this.view = view;
    }

    public void goToPay() {
        router.navigateTo(Screen.PAY.name());
    }

    private void getLootList() {
        EventBus.getDefault().post(new ShowLoaderEvent());
        getViewState().setLootList(App.getDBRepository().getBucketFoodList());
        EventBus.getDefault().post(new HideLoaderEvent());
    }

    public void deleteItem(final int position, final Food food) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        App.getDBRepository().deleteFromBucket(food.id);
//        getViewState().notifyItemDeleted(food.title, position);
        view.notifyItemDeleted(food.title, position);
        EventBus.getDefault().post(new HideLoaderEvent());
    }


    public void onStart() {
        EventBus.getDefault().register(this);
        // TODO Костыль - не работает через EventBus :(
        getViewState().setLootList(App.getDBRepository().getBucketFoodList());
    }

    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onNotifyLoot(BucketSetChangedEvent event) {
        getViewState().setLootList(App.getDBRepository().getBucketFoodList());
    }

    @Subscribe
    public void onClearLoot(ClearLootEvent event){
//        getViewState().requestForClear();
        view.requestForClear();
    }

    public void clearLoot(){
        EventBus.getDefault().post(new ShowLoaderEvent());
        App.getDBRepository().clearLootTable();
        getViewState().notifyLootCleared();
    }

    public void showBNV() {
        EventBus.getDefault().post(new ShowBottomNavigationEvent());
    }

    public void setTitle(String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }

    public void navigateToEat() {
        EventBus.getDefault().post(new SelectItemEvent(R.id.item_menu));
    }

    public void hideToolbarIcon() {
        EventBus.getDefault().post(new HideToolbarIcon());
    }

    public int getTotalPrice() {
        return App.getDBRepository().getTotalBucketPrice();
    }

    public void showClearLootItem() {
        EventBus.getDefault().post(new ShowClearLootItemEvent());
    }

    public void incrementDatabaseCount(final String id) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        App.getDBRepository().incrementCount(id);
        EventBus.getDefault().post(new HideLoaderEvent());
    }

    public void decrementDatabaseCount(final String id) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        App.getDBRepository().decrementCount(id);
        EventBus.getDefault().post(new HideLoaderEvent());
    }
}
