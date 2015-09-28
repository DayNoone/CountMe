package com.mobile.countme.framework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mobile.countme.implementation.controllers.MainPages;
import com.mobile.countme.implementation.menus.BikingMenu;
import com.mobile.countme.implementation.menus.EnvironmentMenu;
import com.mobile.countme.implementation.menus.InformationMenu;
import com.mobile.countme.implementation.menus.StatisticsMenu;

/**
 * Created by Kristian on 16/09/2015.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter{


    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    /**
     * Different menus.
     */
    BikingMenu bikingMenu;
    EnvironmentMenu environmentMenu;
    StatisticsMenu statisticsMenu;
    InformationMenu informationMenu;


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public MainViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

        bikingMenu = new BikingMenu();
        environmentMenu = new EnvironmentMenu();
        statisticsMenu = new StatisticsMenu();
        informationMenu = new InformationMenu();


    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            return bikingMenu;
        }
        else if(position == 1)             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            return environmentMenu;
        }
        else if(position == 2)             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            return statisticsMenu;
        }
        else         // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
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

    public CharSequence[] getTitles() {
        return Titles;
    }

    public InformationMenu getInformationMenu() {
        return informationMenu;
    }

    public StatisticsMenu getStatisticsMenu() {
        return statisticsMenu;
    }

    public EnvironmentMenu getEnvironmentMenu() {
        return environmentMenu;
    }

    public BikingMenu getBikingMenu() {
        return bikingMenu;
    }
}

