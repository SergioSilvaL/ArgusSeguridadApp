package com.example.legible.seguridadargusapp.View.Fragment;

/**
 * Created by sergiosilva on 5/6/17.
 */
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.legible.seguridadargusapp.Controller.BitacoraRegistroAdapter;
import com.example.legible.seguridadargusapp.Controller.ClienteRecyclerAdapter;
import com.example.legible.seguridadargusapp.Model.ObjectModel.BitacoraRegistro;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.View.Activity.BitacoraRegistroActivity;


public class BitacoraRegistroFragment extends Fragment implements BitacoraRegistroAdapter.Callback{



    BitacoraRegistroAdapter mAdapter;
    private static final String TAG = BitacoraRegistroActivity.class.getSimpleName();

    public BitacoraRegistroFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_bitacora_registro,container,false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEditDialog(null);
            }
        });

        mAdapter = new BitacoraRegistroAdapter(this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewBitacoraRegistro);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        return view;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(bitacoraRegistro == null ? R.string.dialog_bitacora_registro_add_title : R.string.dialog_bitacora_registro_edit_title);
        //Todo: Rename dialog
        View view = getLayoutInflater(this.getArguments()).inflate(R.layout.dialog_add, null, false);
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
    public void onAddEdit(final BitacoraRegistro bitacoraRegistro) {
        showAddEditDialog(bitacoraRegistro);
    }

}
