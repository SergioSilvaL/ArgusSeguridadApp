package com.example.legible.seguridadargusapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.legible.seguridadargusapp.adapter.GuardiaListaRecyclerAdapter;

public class GuardiaListaActivity extends AppCompatActivity {

    private GuardiaListaRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardia_lista);

        String ClienteRef = getIntent().getStringExtra("Cliente");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(ClienteRef);
        }

        mAdapter =  new GuardiaListaRecyclerAdapter(this, ClienteRef);


        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view_guardia_lista);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setHasFixedSize(true);
        view.setAdapter(mAdapter);

    }
}
