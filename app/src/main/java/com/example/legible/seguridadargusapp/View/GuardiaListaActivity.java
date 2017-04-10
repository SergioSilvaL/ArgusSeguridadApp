package com.example.legible.seguridadargusapp.View;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.legible.seguridadargusapp.Controller.ClienteRecyclerAdapter;
import com.example.legible.seguridadargusapp.Controller.GuardiaRecyclerAdapter;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.Controller.GuardiaListaRecyclerAdapter;

public class GuardiaListaActivity extends AppCompatActivity {

    private String clienteRef = ClienteRecyclerAdapter.myCliente;
    private String supervisorRef = ClienteRecyclerAdapter.mySupervisor;
    private android.support.v4.app.FragmentManager fm;
    private RecyclerView recyclerView;
    private GuardiaListaRecyclerAdapter mAdapter;

    public static int REQUEST_ASISTENCIA = 0;
    public static String EXTRA_ASISTENCIA = "EXTRA_ASISTENCIA";
    public static String EXTRA_ASISTENCIA_GUARDIA_CAPTURADO = "EXTRA_ASISTENCIA_GUARDIA_CAPTURADO";
    public static String EXTRA_DOBLE_ASISTENCIA = "EXTRA_DOBLE_ASISTENCIA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardia_lista);

        //Set our support actionBar

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_guardia_lista);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(clienteRef);

        mAdapter =  new GuardiaListaRecyclerAdapter(this, clienteRef,getSupportFragmentManager());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_guardia_lista);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

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

        GuardiaTemporalAddDialogFragment dialogFragment = GuardiaTemporalAddDialogFragment.newInstance(clienteRef,supervisorRef);
        fm = getSupportFragmentManager();
        dialogFragment.show(fm,"fragment_guarida_temporal_add");

    }

    public void addIncidente(){

        IncidenteAddDialogFragment df = IncidenteAddDialogFragment.newInstance(clienteRef,supervisorRef);
        fm = getSupportFragmentManager();
        df.show(fm,"dialog_fragment_incidente_add");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ASISTENCIA){
            if (resultCode == RESULT_OK){
                GuardiaListaRecyclerAdapter.myStatus = data.getStringExtra(EXTRA_ASISTENCIA);
                GuardiaListaRecyclerAdapter.myGuardiaCaptura =  data.getStringExtra(EXTRA_ASISTENCIA_GUARDIA_CAPTURADO);
                GuardiaListaRecyclerAdapter.myStatusExtra = data.getStringExtra(EXTRA_DOBLE_ASISTENCIA);

                recyclerView.setAdapter(mAdapter);
            }
        }
    }
}



































