package com.mobile.countme.framework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mobile.countme.implementation.views.BikingTab;
import com.mobile.countme.implementation.views.EnvironmentTab;
import com.mobile.countme.implementation.views.InformationTab;
import com.mobile.countme.implementation.views.StatisticsTab;

/**
 * Created by Kristian on 16/09/2015.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter{


    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    /**
     * Different menus.
     */
    private BikingTab bikingTab;
    private EnvironmentTab environmentTab;
    private StatisticsTab statisticsTab;
    private InformationTab informationTab;


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public MainViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

        bikingTab = new BikingTab();
        environmentTab = new EnvironmentTab();
        statisticsTab = new StatisticsTab();
        informationTab = new InformationTab();


    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            return bikingTab;
        }
        else if(position == 1)             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            return environmentTab;
        }
        else if(position == 2)             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            return statisticsTab;
        }
        else         // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            return informationTab;
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

    public InformationTab getInformationTab() {
        return informationTab;
    }

    public StatisticsTab getStatisticsTab() {
        return statisticsTab;
    }

    public EnvironmentTab getEnvironmentTab() {
        return environmentTab;
    }

    public BikingTab getBikingTab() {
        return bikingTab;
    }
}

