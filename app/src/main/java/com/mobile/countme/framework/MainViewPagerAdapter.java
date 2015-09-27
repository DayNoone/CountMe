package com.mobile.countme.framework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mobile.countme.menu.BikingMenu;
import com.mobile.countme.menu.EnvironmentMenu;
import com.mobile.countme.menu.InformationMenu;
import com.mobile.countme.menu.StatisticsMenu;
import com.mobile.countme.menu.introduction_pages.Page1;
import com.mobile.countme.menu.introduction_pages.Page2;
import com.mobile.countme.menu.introduction_pages.Page3;
import com.mobile.countme.menu.introduction_pages.Page4;

/**
 * Created by Kristian on 16/09/2015.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter{


    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public MainViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            BikingMenu bikingMenu = new BikingMenu();
            return bikingMenu;
        }
        else if(position == 1)             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            EnvironmentMenu environmentMenu = new EnvironmentMenu();
            return environmentMenu;
        }
        else if(position == 2)             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            StatisticsMenu statisticsMenu = new StatisticsMenu();
            return statisticsMenu;
        }
        else         // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            InformationMenu informationMenu = new InformationMenu();
            return informationMenu;
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

