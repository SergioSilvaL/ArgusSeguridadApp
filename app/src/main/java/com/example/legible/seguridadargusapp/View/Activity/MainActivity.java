package com.example.legible.seguridadargusapp.View.Activity;



import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.legible.seguridadargusapp.Controller.GuardiaRecyclerAdapter;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.Controller.Adapter_ViewPagerMain;
import com.example.legible.seguridadargusapp.View.DialogFragment.InternetConnectionDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Adapter_ViewPagerMain mAdapter_viewPagerMain;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Check to see if thier is an interntConnnection
        checkInternetConnection();
        
        //Create costume TabLayour for our main view.
        setTabLayoutMain();

        //Create Costum Adapter for our View Pager in Main.
        setViewPagerMain();

        Log.d("DEBUG", "Terminamos onCreate");
    }

    private void checkInternetConnection() {


        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            InternetConnectionDialogFragment df = new InternetConnectionDialogFragment();
            FragmentManager fm = getSupportFragmentManager();
            df.show(fm,"dialog_fragment_incidente_add");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_bitacora:
                openBitacora();
                return true;

            case R.id.action_sign_out:
                signOut();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        //TODO if finish() is better practice replace
        startActivity(new Intent(this,SignInActivity.class));
        finish();
    }

    public void openBitacora(){
        startActivity(new Intent(this, BitacoraRegistroActivity.class));
    }

    public void setTabLayoutMain(){

        // Instanciate a Tab Layout

        mTabLayout = (TabLayout) findViewById(R.id.tabLayoutMain);


        // We also asign a centered gravity.
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Add "Fixed Mode" so  all the tabs are the same size.
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);


        //Added two tabs to tabLayout
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());

    
    }

    public void setViewPagerMain(){

        //Instantiate a View Pager
        mViewPager = (ViewPager) findViewById(R.id.viewPagerMain);

        /**
         * Create Adapter
         * Pass the Fragment Gestor as an argument
         * add nº of tabs or Sections we have created
         * */

        mAdapter_viewPagerMain =
                new Adapter_ViewPagerMain(
                        getSupportFragmentManager(),
                        mTabLayout.getTabCount(),
                        this);

        //Bind View Pager to the Adapter we just created.

        mViewPager.setAdapter(mAdapter_viewPagerMain);

        //? Enables Swiping
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }


}
