package com.example.legible.seguridadargusapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class GuardiaListaActivity extends AppCompatActivity {

    private GuardiaListaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardia_lista);

        String ClienteRef = getIntent().getStringExtra("Cliente");

        mAdapter =  new GuardiaListaAdapter(this);


        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view_guardia_lista);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setHasFixedSize(true);
        view.setAdapter(mAdapter);

    }
}
