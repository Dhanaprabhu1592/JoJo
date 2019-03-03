package com.whistledevelopers.jojo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.whistledevelopers.jojo.MyLocationUsingLocationAPI;
import com.whistledevelopers.jojo.model.Categories;
import com.whistledevelopers.jojo.fragments.HomeFragment;
import com.whistledevelopers.jojo.fragments.ProfileFragment;
import com.whistledevelopers.jojo.R;
import com.whistledevelopers.jojo.fragments.SettingsFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView_options;
    List<Categories> categoryList;
    Toolbar toolbar;
    public static final String SHARED_PREF_NAME = "Joju";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String status,location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitle(R.string.title_home);
        setSupportActionBar(toolbar);
        sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sp.edit();
        status = sp.getString("isLoggedin", "");
        location = sp.getString("isLocationGet", "");
        if(!status.equals("true")){
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(!location.equals("true")){
            Intent intent = new Intent(HomeActivity.this, MyLocationUsingLocationAPI.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }


        /*recyclerView_options=(RecyclerView)findViewById(R.id.recyclerview_options);
        recyclerView_options.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView_options.setHasFixedSize(true);
        */
        categoryList = new ArrayList<>();
        
       /*categoryList.add( new Categories("Electrician"));
       categoryList.add( new Categories("Plumber"));
       categoryList.add( new Categories("Carpenter"));
       categoryList.add( new Categories("Two Wheeler Mechanic"));
       categoryList.add( new Categories("Car Mechanic"));
       categoryList.add( new Categories("Painters"));
*/
        /*CategoryAdapter categoryAdapter=new CategoryAdapter(this,categoryList);
        recyclerView_options.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
*/

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.place_holder, homeFragment);
        fragmentTransaction.commit();


        //Recyclerview for Options


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            String message = "Do you want to exit from the apllication?";
            showDialog(message, "exit");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_partner) {
            startActivity(new Intent(HomeActivity.this,PartnerLogin.class));
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "It Will be activated once app published in playstore", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {

            showDialog("Do you want to Logout from the apllication?", "logout");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // HomeFragment homeFragment=new HomeFragment();
        fragmentTransaction.replace(R.id.place_holder, fragment);
        fragmentTransaction.commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(new HomeFragment());
                    toolbar.setTitle(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    showFragment(new ProfileFragment());
                    toolbar.setTitle(R.string.title_profile);

                    return true;
                case R.id.navigation_notifications:
                    toolbar.setTitle(R.string.title_support);
                    showFragment(new SettingsFragment());

                    return true;
            }
            return false;
        }
    };

    private void showDialog(String message, final String flag) {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle(getString(R.string.app_name));

        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (flag.equals("logout")) {
                            editor.putString("isLoggedin", "false   ");
                            editor.apply();

                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            // set the new task and clear flags
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else if (flag.equals("exit")) {
                            finishAffinity();
                        }

                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        AlertDialog alert = builder.create();
        alert.show();
    }

}
