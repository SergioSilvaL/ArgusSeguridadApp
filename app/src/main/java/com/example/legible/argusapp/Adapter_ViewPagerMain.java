package com.example.legible.argusapp;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Legible on 17/02/2017.
 */

public class Adapter_ViewPagerMain extends FragmentPagerAdapter {

    int mNumberOfTabs;
    private Context mContext;

    public Adapter_ViewPagerMain(FragmentManager fm, int numberOfTabs, Context mContext) {
        super(fm);
        this.mNumberOfTabs = numberOfTabs;
        this.mContext = mContext;
    }



    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {

        /**
         * Get the position from the parameter
         * thanks to the position, we can send the correspondent Fragment to the Section.
         * */
         switch (position) {

            case 0: // I'll always begin in 0.
                return new ZonaFragment();

            case 1:
                return new GuardiaFragment();

            // If the position we receive doesn't correspond to any section.
            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {

        /**
         * Get the position from the parameter
         * thanks to the position, we can send the correspondent tab title
         * */
        switch (position) {

            case 0: // The first tab will always initialize at 0.
                return mContext.getString(R.string.Zona);
            case 1:
                return mContext.getString(R.string.Guardia);

            // If the position we receive doesn't correspond to any section.
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
