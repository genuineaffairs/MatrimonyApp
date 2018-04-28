package co.wisne.matrimonyapp.ui.main.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import co.wisne.matrimonyapp.ui.main.BookmarksFragment;
import co.wisne.matrimonyapp.ui.main.NotificationsFragment;
import co.wisne.matrimonyapp.ui.main.ProfileFragment;

public class ProfilePagerAdapter extends FragmentPagerAdapter {

    String[] tabTitle = {"Notifications","Bookmarks","Profile"};

    ProfileFragment profileFragment;
    BookmarksFragment bookmarksFragment;
    NotificationsFragment notificationsFragment;


    public ProfilePagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
        if(position == 2){
            if(profileFragment == null){
                profileFragment = new ProfileFragment();
            }
            return profileFragment;
        }
        if(position == 1){
            if(bookmarksFragment == null){
                bookmarksFragment = new BookmarksFragment();
            }
            return bookmarksFragment;
        }
        if(position == 0){
            if(notificationsFragment == null){
                notificationsFragment = new NotificationsFragment();
            }
            return notificationsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
