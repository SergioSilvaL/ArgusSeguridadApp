package com.example.legible.seguridadargusapp.View;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.Controller.BitacoraRegistroAdapter;
import com.example.legible.seguridadargusapp.Model.ObjectModel.BitacoraRegistro;
import com.example.legible.seguridadargusapp.R;

public class BitacoraRegistroActivity extends AppCompatActivity implements BitacoraRegistroAdapter.Callback{

    BitacoraRegistroAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEditDialog(null);
            }
        });

        mAdapter = new BitacoraRegistroAdapter(this);
        RecyclerView view = (RecyclerView) findViewById(R.id.recyclerViewBitacoraRegistro);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setHasFixedSize(true);
        view.setAdapter(mAdapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showAddEditDialog(final BitacoraRegistro bitacoraRegistro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(bitacoraRegistro == null ? "Agregar Registro" : "Editar Registro");
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null, false);
        builder.setView(view);
        final EditText observacionEditText = (EditText) view.findViewById(R.id.dialog_add_observacion_text);
        //final TextView horaEditText = (TextView) view.findViewById(R.id.dialog_add_hora_text);


        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (bitacoraRegistro == null) {
                    String observacion = observacionEditText.getText().toString();
                    //String hora = horaEditText.getText().toString();
                    mAdapter.add(new BitacoraRegistro(observacion, "", 0));
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        builder.create().show();
    }


    @Override
    public void onEdit(final BitacoraRegistro bitacoraRegistro) {
        showAddEditDialog(bitacoraRegistro);
    }
}


