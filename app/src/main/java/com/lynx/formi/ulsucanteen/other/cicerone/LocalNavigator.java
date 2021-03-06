package com.lynx.formi.ulsucanteen.other.cicerone;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.other.Screen;

import java.util.LinkedList;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

public class LocalNavigator implements Navigator {

    private final Activity activity;
    private final FragmentManager fragmentManager;
    private final int containerId;
    private LinkedList<String> localStackCopy;

    public LocalNavigator(final Activity activity, FragmentManager fragmentManager, int containerId) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    @Override
    public void applyCommand(final Command command) {
        fragmentManager.executePendingTransactions();

        copyStackToLocal();

        if (command instanceof Forward) {
            forwardCommand((Forward) command);
        } else if (command instanceof Replace) {
            replaceCommand((Replace) command);
        } else if (command instanceof BackTo) {
            backTo((BackTo) command);
        } else if (command instanceof Back) {
            fragmentBack();
        }
    }

    private void backTo(final BackTo command) {
        final String key = command.getScreenKey();

        if (key == null) {
            backToRoot();
        } else {
            int index = localStackCopy.indexOf(key);
            int size = localStackCopy.size();

            if (index != -1) {
                for (int i = 1; i < size - index; i++) {
                    localStackCopy.removeLast();
                }
                fragmentManager.popBackStack(key, 0);
            }
        }
    }

    private void backToRoot() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        localStackCopy.clear();
    }

    private void replaceCommand(final Replace command) {

        final Fragment fragment = createFragment(command.getScreenKey(), command.getTransitionData());

        if (localStackCopy.size() > 0) {
            fragmentManager.popBackStack();
            localStackCopy.removeLast();

            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                    .replace(containerId, fragment)
                    .addToBackStack(command.getScreenKey())
                    .commit();
            localStackCopy.add(command.getScreenKey());

        } else {
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction
                    .replace(containerId, fragment)
                    .commit();
        }
    }

    private void fragmentBack() {
        if (localStackCopy.size() > 0) {
            fragmentManager.popBackStack();
            localStackCopy.removeLast();
        }
    }

    private void forwardCommand(final Forward forward) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                .replace(containerId, createFragment(forward.getScreenKey(), forward.getTransitionData()))
                .addToBackStack(forward.getScreenKey())
                .commit();
        localStackCopy.add(forward.getScreenKey());
    }

    private void copyStackToLocal() {
        localStackCopy = new LinkedList<>();

        final int stackSize = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < stackSize; i++) {
            localStackCopy.add(fragmentManager.getBackStackEntryAt(i).getName());
        }
    }

    protected Fragment createFragment(final String screenKey, final Object data){
        return Screen.valueOf(screenKey).create((Bundle) data);
    }

}
