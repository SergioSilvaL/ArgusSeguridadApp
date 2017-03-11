package com.example.legible.seguridadargusapp.View;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.Model.ObjectModel.Cliente;
import com.example.legible.seguridadargusapp.Model.ObjectModel.Incidente;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.Controller.GuardiaListaRecyclerAdapter;

public class GuardiaListaActivity extends AppCompatActivity {

    private String ClienteRef;
    private android.support.v4.app.FragmentManager fm;


    private GuardiaListaRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardia_lista);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ActionBar supportActionBar = getSupportActionBar();
//        if (supportActionBar != null) {
//            ClienteRef = getIntent().getStringExtra("Cliente");
//            supportActionBar.setTitle(ClienteRef);
//        }


        //Set our support actionBar

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_guardia_lista);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ClienteRef = getIntent().getStringExtra("Cliente");

        actionBar.setTitle(ClienteRef);






        mAdapter =  new GuardiaListaRecyclerAdapter(this, ClienteRef,getSupportFragmentManager());


        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view_guardia_lista);
        view.setLayoutManager(new GridLayoutManager(this,2));
        //view.setLayoutManager(new LinearLayoutManager(this));
        view.setHasFixedSize(true);
        view.setAdapter(mAdapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_guardias_lista,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                return true;

            case R.id.action_add_guardia:
                addGuardiaTemporal();
            return true;

            case R.id.action_add_incidente:
                //Do something
                addIncidente();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void addGuardiaTemporal(){


        //Todo getStringExtra supervisorNombre from bundle

//        Intent in = getIntent();
//        Bundle bundle  = getIntent().getExtras();
//        String supervisor = bundle.getString("SupervisorNombre");
        String supervisor = "";

        GuardiaTemporalAddDialogFragment dialogFragment = GuardiaTemporalAddDialogFragment.newInstance(ClienteRef,supervisor);
        fm = getSupportFragmentManager();
        dialogFragment.show(fm,"fragment_guarida_temporal_add");

    }

    public void addIncidente(){

        String supervisor="";
        IncidenteAddDialogFragment df = IncidenteAddDialogFragment.newInstance(ClienteRef,supervisor);
        fm = getSupportFragmentManager();
        df.show(fm,"dialog_fragment_incidente_add");
    }
}
