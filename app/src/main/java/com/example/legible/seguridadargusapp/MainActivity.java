package com.example.legible.seguridadargusapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabaseReference;
    public String ZonaSupervisorRef;



    private Adapter_ViewPagerMain mAdapter_viewPagerMain;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    //UI Elements
    //TextView mUserEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create costume TabLayour for our main view.
        setTabLayoutMain();

        //Create Costum Adapter for our View Pager in Main.
        setViewPagerMain();

        String userID = "no se recibio un correo valido";

        if(user.getUid()!= null){
            userID = user.getUid();
        }

        Log.v(TAG, "User ID: " + userID);


        //Get Firebase Reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mSupervisorRef = mDatabaseReference
                .child("Argus")
                .child("supervisores");

        mSupervisorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    supervisores supervisor = data.getValue(supervisores.class);

//                    Log.v(TAG,user.getEmail());
//                    Log.v(TAG,supervisor.getUsuarioEmail());
//                    Log.v(TAG,supervisor.getUsuarioZona());

                    if(user.getEmail().equals(supervisor.getUsuarioEmail())){
                        Log.v(TAG, supervisor.getUsuarioZona());

                        ZonaSupervisorRef = supervisor.getUsuarioZona();


                    }


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Save the Zona Database Reference
        Bundle bundle = new Bundle();
        bundle.putString("ZonaSupervisorRef",ZonaSupervisorRef);

        // set Fragmentclass Arguments
        ClienteFragment fragobj = new ClienteFragment();
        fragobj.setArguments(bundle);


    }

    public String getInfo(){return ZonaSupervisorRef;}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);


        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,SignInActivity.class));
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
