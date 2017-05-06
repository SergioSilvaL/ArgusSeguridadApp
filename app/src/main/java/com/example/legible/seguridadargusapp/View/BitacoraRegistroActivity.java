package com.example.legible.seguridadargusapp.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.legible.seguridadargusapp.Controller.BitacoraRegistroAdapter;
import com.example.legible.seguridadargusapp.Controller.ClienteRecyclerAdapter;
import com.example.legible.seguridadargusapp.Model.ObjectModel.BitacoraRegistro;
import com.example.legible.seguridadargusapp.R;

public class BitacoraRegistroActivity extends AppCompatActivity implements BitacoraRegistroAdapter.Callback{

    BitacoraRegistroAdapter mAdapter;
    private static final String TAG = BitacoraRegistroActivity.class.getSimpleName();

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

    private int semaforoStatus = 1;

    public void onRadioButtonClickedBitacoraRegistro(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was created
        switch (view.getId()){
            case R.id.radioButtonBitacoraRegistroStatusBAJA:
                if (checked)
                semaforoStatus = 1;
                break;

            case R.id.radioButtonBitacoraRegistroStatusMEDIA:
                if (checked) semaforoStatus = 2;
                break;

            case R.id.radioButtonBitacoraRegistroStatusALTA:
                semaforoStatus =3;
                break;
        }
        Log.v(TAG, String.valueOf(semaforoStatus));
    }

    private void showAddEditDialog(final BitacoraRegistro bitacoraRegistro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(bitacoraRegistro == null ? R.string.dialog_bitacora_registro_add_title : R.string.dialog_bitacora_registro_edit_title);
        //Todo: Rename dialog
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null, false);
        builder.setView(view);
        final EditText observacionEditText = (EditText) view.findViewById(R.id.dialog_add_observacion_text);
        semaforoStatus = 1;// Sets default semaforo Value
        // Todo: set Default radiobutton base on its current semaaforo

        if (bitacoraRegistro != null){
            // pre-populate
            observacionEditText.setText(bitacoraRegistro.getObservacion());
        }

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (bitacoraRegistro == null) {
                    String observacion = observacionEditText.getText().toString();
                    mAdapter.add(new BitacoraRegistro(observacion, semaforoStatus, ClienteRecyclerAdapter.mySupervisor, ClienteRecyclerAdapter.myZona));
                }else {
                    mAdapter.update(bitacoraRegistro, observacionEditText.getText().toString(), semaforoStatus);
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


