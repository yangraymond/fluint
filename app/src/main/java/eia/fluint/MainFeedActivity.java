package eia.fluint;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainFeedActivity extends AppCompatActivity {

    private static final String NAV_ITEM_ID = "navItemId";

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mNavItemId;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);

        toolbar = (Toolbar) findViewById(R.id.toolBarFeed);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            mNavItemId = R.id.navForSale;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        int navValue = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            navValue = extras.getInt("nav", 0);
        }

        mTitle = getTitle().toString();
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }

                mNavItemId = menuItem.getItemId();
                drawerLayout.closeDrawers();

                Fragment fragment;
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                switch (mNavItemId) {
                    case R.id.navBuyRequest:
                        toolbar.setTitle("Requests");
                        toolbar.setTitleTextColor(getResources().getColor(R.color.whiteColor));

                        fragment = new BuyRequestFeedFragment();
                        fragmentTransaction.replace(R.id.feedFragmentContainer, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navProfile:
                        toolbar.setTitle("Profile");
                        toolbar.setTitleTextColor(getResources().getColor(R.color.whiteColor));
                        Toast.makeText(getApplicationContext(), "Profile selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navSettings:
                        toolbar.setTitle("Settings");
                        toolbar.setTitleTextColor(getResources().getColor(R.color.whiteColor));

                        fragment = new SettingsFragment();
                        fragmentTransaction.replace(R.id.feedFragmentContainer, fragment);
                        fragmentTransaction.commit();
                        return true;
                    default:
                        toolbar.setTitle("For Sale");
                        toolbar.setTitleTextColor(getResources().getColor(R.color.whiteColor));

                        fragment = new ForSaleFeedFragment();
                        fragmentTransaction.replace(R.id.feedFragmentContainer, fragment);
                        fragmentTransaction.commit();
                        return true;
                }
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.setDrawerListener(mDrawerToggle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (navValue) {
            case 1:
                toolbar.setTitle("Requests");
                BuyRequestFeedFragment buyRequestFeedFragment = new BuyRequestFeedFragment();
                ft.replace(R.id.feedFragmentContainer, buyRequestFeedFragment).commit();
                break;
            default:
                toolbar.setTitle("For Sale");
                ForSaleFeedFragment forSaleFeedFragment = new ForSaleFeedFragment();
                ft.replace(R.id.feedFragmentContainer, forSaleFeedFragment).commit();
                break;
        }

        toolbar.setTitle("For Sale");
        ForSaleFeedFragment buyFeedFragment = new ForSaleFeedFragment();
        getFragmentManager().beginTransaction().add(R.id.feedFragmentContainer, buyFeedFragment).commit();



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }

}
