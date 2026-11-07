package com.example.stockmanager;

import android.app.Activity;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationHelper {

    public static void setupBottomNavigation(Activity activity, int currentItemId) {
        BottomNavigationView bottomNav = activity.findViewById(R.id.bottomNavigation);
        if (bottomNav == null) return;

        bottomNav.setSelectedItemId(currentItemId);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == currentItemId) {
                return true;
            }

            Intent intent = null;
            if (itemId == R.id.nav_dashboard) {
                intent = new Intent(activity, MainActivity.class);
            } else if (itemId == R.id.nav_products) {
                intent = new Intent(activity, ProductsActivity.class);
            } else if (itemId == R.id.nav_analytics) {
                intent = new Intent(activity, AnalyticsActivity.class);
            } else if (itemId == R.id.nav_settings) {
                intent = new Intent(activity, SettingsActivity.class);
            }

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
}
