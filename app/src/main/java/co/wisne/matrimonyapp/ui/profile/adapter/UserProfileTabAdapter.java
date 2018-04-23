package co.wisne.matrimonyapp.ui.profile.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import co.wisne.matrimonyapp.ui.profile.UserBasicProfileFragment;
import co.wisne.matrimonyapp.ui.profile.UserProfessionFragment;
import co.wisne.matrimonyapp.ui.profile.UserReligionFragment;

public class UserProfileTabAdapter extends FragmentPagerAdapter {

    String[] tabTitles = {"About","Religion","Profession"};

    public UserProfileTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new UserBasicProfileFragment();
        }
        if (position == 1){
            return  new UserReligionFragment();
        }

        return new UserProfessionFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
