package co.wisne.matrimonyapp.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ProfilePagerAdapter extends FragmentPagerAdapter {

    String[] tabTitle = {"Notifications","Bookmarks","Profile"};

    public ProfilePagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
        if(position == 2){
            return new ProfileFragment();
        }
        if(position == 0){
            return new NotificationsFragment();
        }
        Fragment fragment = new DemoFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(DemoFragment.ARG_OBJECT, position + 1);
        fragment.setArguments(args);
        return fragment;

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
