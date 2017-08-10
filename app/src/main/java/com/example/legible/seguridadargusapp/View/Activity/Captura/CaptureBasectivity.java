package com.example.legible.seguridadargusapp.View.Activity.Captura;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.Model.ObjectModel.DatePost;
import com.example.legible.seguridadargusapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public abstract class CaptureBasectivity extends Activity {

    protected TextView txtHeading;
    protected Button btnCapturar;
    protected Button btnCancelar;
    protected ProgressDialog progressDialog;
    protected enum TipoCaptura {Asistencia,NoAsistencia,CubreDescanso,DobleTurno,HorasExtra}

    protected void setHeading(String guardiaNombre) {
        if (txtHeading == null)
            txtHeading = (TextView) findViewById(R.id.txtHeading);
        if (txtHeading != null)
            txtHeading.setText(guardiaNombre);
    }

    protected void setBtnCapturar(){
        if (btnCapturar == null)
            btnCapturar = (Button) findViewById(R.id.btnCaptura);
    }

    protected void setBtnCancelar(){

        if (btnCancelar == null)
            btnCancelar = (Button) findViewById(R.id.btnCancelar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("CARGANDO...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }

    protected void setBitacoraDatabaseReferenceBasicValues
            (String cliente, String zona, String turno, String guardiaNombre, String guardiaKey){

        updateBasicValues(cliente, zona, turno, guardiaNombre, guardiaKey);
        updateFechaNode();

    }

    protected void setClienteGuardiaDatabaseReferenceValue(){

    }

    protected DatabaseReference BitacoraDatabaseReference(String guardiaKey){

        return FirebaseDatabase.getInstance().getReference()
                .child("Argus").child("Bitacora").child(new DatePost().getDate()).child(guardiaKey);
    }

    private DatabaseReference ClienteGuardiaDatabaseReference(String cliente, String guardiaKey){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Argus").child("Clientes").child(cliente).child("clienteGuardias").child(guardiaKey);

        return reference;
    }

    private void updateFechaNode(){

        // Database Reference
        DatabaseReference reference =
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Argus")
                        .child("Bitacora")
                        .child(new DatePost().getDateKey());


        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/fecha", new DatePost().getDateKey());
        reference.updateChildren(childUpdates);

    }

    private void updateBasicValues
            (String cliente, String zona, String turno, String nombre, String guardiaKey){

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/cliente", "dfsdf");
        childUpdates.put("/zona", "dsfsdf");
        childUpdates.put("/guardiaNombre", "dsfsdf");
        childUpdates.put("/turno", "dsf");
        childUpdates.put("/fecha", new DatePost().getDate());


        BitacoraDatabaseReference(guardiaKey).updateChildren(childUpdates);
    }

    protected abstract void capturarContenido(String guardiaKey);
}
