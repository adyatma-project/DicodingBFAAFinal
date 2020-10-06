package id.adyatma.dicodingbfaafinal.adapter;
/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import id.adyatma.dicodingbfaafinal.fragment.FollowersFragment;
import id.adyatma.dicodingbfaafinal.fragment.FollowingFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int number_tabs;

    public PagerAdapter(FragmentManager fm, int number_tabs) {
        super(fm);
        this.number_tabs = number_tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FollowersFragment();
            case 1:
                return new FollowingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return number_tabs;
    }
}