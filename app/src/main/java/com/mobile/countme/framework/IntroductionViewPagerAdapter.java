package com.mobile.countme.framework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mobile.countme.implementation.views.introduction_pages.*;

/**
 * Created by Kristian on 11/09/2015.
 */
public class IntroductionViewPagerAdapter extends FragmentPagerAdapter {


    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public IntroductionViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Page1 page1 = new Page1();
            return page1;
        }
        else if(position == 1)             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Page2 page2 = new Page2();
            return page2;
        }
        else if(position == 2)             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            UserInformationTab userInformationTab = new UserInformationTab();
            return userInformationTab;
        }
        else         // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Page4 page4 = new Page4();
            return page4;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

}
