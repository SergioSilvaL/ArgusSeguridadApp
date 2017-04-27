package com.example.legible.seguridadargusapp.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class ConsignasActivity extends AppCompatActivity {
    List<Consigna> mConsignas = new ArrayList<>();
    ConsignasAdapter consignasAdapter;
    EditText editTextConsigna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creacion de Vistas
        setContentView(R.layout.activity_consignas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextConsigna = (EditText) findViewById(R.id.editTextConsigna);

        RecyclerView recyclerViewConsignas = (RecyclerView) findViewById(R.id.recyclerViewConsignas);
        recyclerViewConsignas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewConsignas.setHasFixedSize(true);

        // ArrayList de Consginas


        for (int i = 0 ; i< 10; i++){

            Consigna consigna = new Consigna("consigna"+i);
            mConsignas.add(mConsignas.size(),consigna);


        }
        //Button Flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggregarConsigna();
            }
        });

        consignasAdapter = new ConsignasAdapter(mConsignas);
        recyclerViewConsignas.setAdapter(consignasAdapter);



        //Button de Regreso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void aggregarConsigna() {
        //Codigo para agregar Consgina

        consignasAdapter.addConsigna(new Consigna(editTextConsigna.getText().toString()));
    }

}
