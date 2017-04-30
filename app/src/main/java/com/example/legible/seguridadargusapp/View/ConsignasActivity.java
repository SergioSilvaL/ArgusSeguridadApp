package com.example.legible.seguridadargusapp.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.legible.seguridadargusapp.Controller.ConsignasAdapter;
import com.example.legible.seguridadargusapp.Model.ObjectModel.Consigna;
import com.example.legible.seguridadargusapp.R;

import java.util.ArrayList;
import java.util.List;

public class ConsignasActivity extends AppCompatActivity implements ConsignasAdapter.Callback{

    ConsignasAdapter consignasAdapter;

    EditText editTextConsigna;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set views
        setContentView(R.layout.activity_consignas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextConsigna = (EditText) findViewById(R.id.editTextConsigna);

        RecyclerView recyclerViewConsignas = (RecyclerView) findViewById(R.id.recyclerViewConsignas);
        recyclerViewConsignas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewConsignas.setHasFixedSize(true);
        consignasAdapter = new ConsignasAdapter(this);
        recyclerViewConsignas.setAdapter(consignasAdapter);

        //Fab Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggregarConsigna();
            }
        });
        //Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void aggregarConsigna() {
        //Codigo para agregar Consgina
        consignasAdapter.add(new Consigna(editTextConsigna.getText().toString()));
    }

    @Override
    public void onEdit(final Consigna consigna) {
        showConsignaDialogFragment(consigna);
    }

    private void showConsignaDialogFragment(final Consigna consigna) {

        FragmentManager fm =getSupportFragmentManager();
        ConsignaDialogFragment cdf = ConsignaDialogFragment.newInstance(consigna);
        cdf.show(fm, "fragment_consigna");

    }

}
