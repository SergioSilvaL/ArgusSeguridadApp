package com.example.legible.seguridadargusapp.View.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.legible.seguridadargusapp.Controller.ConsignasAdapter;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.View.DialogFragment.ConsignaDialogFragment;

public class ConsignasActivity extends AppCompatActivity implements ConsignasAdapter.Callback{

    ConsignasAdapter consignasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set views
        setContentView(R.layout.activity_consignas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
                showAddDialog();
            }
        });
        //Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showAddDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setTitle("Agregar Consigna");
        alert.setView(edittext);

        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = edittext.getText().toString();
                consignasAdapter.add(text);
            }
        });

        alert.setNegativeButton(android.R.string.cancel, null);

        alert.show();

    }

    @Override
    public void onEdit(final String consigna) {
        showConsignaDialogFragment(consigna);
    }

    private void showConsignaDialogFragment(final String consigna) {

        FragmentManager fm =getSupportFragmentManager();
        ConsignaDialogFragment cdf = ConsignaDialogFragment.newInstance(consigna);
        cdf.show(fm, "fragment_consigna");

    }

}
