package com.example.legible.seguridadargusapp.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.legible.seguridadargusapp.Controller.ConsignasAdapter;
import com.example.legible.seguridadargusapp.Model.ObjectModel.Consigna;
import com.example.legible.seguridadargusapp.R;

import java.util.ArrayList;
import java.util.List;

public class ConsignasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creacion de Vistas
        setContentView(R.layout.activity_consignas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerViewConsignas = (RecyclerView) findViewById(R.id.recyclerViewConsignas);


        // ArrayList de Consginas
        List<Consigna> mConsignas = new ArrayList<>();

        for (int i = 0 ; i< 10; i++){

            Consigna consigna = new Consigna("consigna"+i);
            mConsignas.add(mConsignas.size(),consigna);


        }


        ConsignasAdapter consignasAdapter = new ConsignasAdapter(mConsignas);
        recyclerViewConsignas.setAdapter(consignasAdapter);

        //Button Flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Button de Regreso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
