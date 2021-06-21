package com.example.shopbuddy.services;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopbuddy.R;
import com.example.shopbuddy.models.CustomNavBar;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.example.shopbuddy.utils.CustomBackStack;

public class NavigationService {

    //Tabs values
    public final static int MAP_PAGE = 0;
    public final static int FOOD_WASTE_PAGE = 1;
    public final static int SHOP_LIST_PAGE = 2;
    public final static int ALARM_PAGE = 3;

    //From NavigationActivity
    private static NavigationActivity navActivity;
    private static CustomNavBar navbar;
    private static CustomBackStack customBackStack = new CustomBackStack();

    //Tab roots
    public static Fragment[] rootFragments;

    //Diverse
    private static String[] actionBarTitles;
    private static boolean firstFragmentUsed = false;

    public static void setNavigationActivity(NavigationActivity nav){
        navActivity = nav;
        actionBarTitles = new String[]{
                navActivity.getString(R.string.menu_button_1),
                navActivity.getString(R.string.menu_button_2),
                navActivity.getString(R.string.menu_button_3),
                navActivity.getString(R.string.menu_button_4)};
    }

    public static void setCustomNavBar(CustomNavBar cnb){
        navbar = cnb;
    }

    public static void setPredifinedTabRoots(Fragment[] frags){
        rootFragments = frags;
    }

    public static void changeToFragment(Fragment fragment, int navButton){
        FragmentManager fm = navActivity.getSupportFragmentManager();

        if(fm.findFragmentById(R.id.fragment_container) == fragment) return;

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);

        if(!firstFragmentUsed){
            firstFragmentUsed = true;
        }
        else{
            ft.addToBackStack(null);
        }
        ft.commit();

        customBackStack.addToBackStack(navButton);
        updateMenuButtons();
    }

    public static boolean canGoBack(){
        if(customBackStack.size() > 1){
            return true;
        }return false;
    }

    public static void goBack(){
        customBackStack.popCurrent();
        updateMenuButtons();
    }



    public static void changePage(int pagenumber){
        switch(pagenumber){
            case MAP_PAGE:
                changeToFragment(rootFragments[MAP_PAGE], MAP_PAGE);

                break;
            case FOOD_WASTE_PAGE:
                changeToFragment(rootFragments[FOOD_WASTE_PAGE], FOOD_WASTE_PAGE);

                break;
            case SHOP_LIST_PAGE:
                changeToFragment(rootFragments[SHOP_LIST_PAGE], SHOP_LIST_PAGE);

                break;
            case ALARM_PAGE:
                changeToFragment(rootFragments[ALARM_PAGE], ALARM_PAGE);

                break;

        }
    }

    public static void updateMenuButtons(){
        int navButton = customBackStack.getCurrent();
        navActivity.getActionBarTitle().setText(actionBarTitles[navButton]);
        navbar.updateMenuButtons(navButton);
    }


}
