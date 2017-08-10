package com.example.legible.seguridadargusapp.View.Activity.Captura;

import android.os.Bundle;
import android.view.View;

import com.example.legible.seguridadargusapp.R;

import java.util.HashMap;
import java.util.Map;

public class AsistioActivity extends CapturaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Todo: set Guardia Name
        setContentView("sadf");

        btnCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo Set Guardia Key
                capturarContenido("asdffffff");
            }
        });

    }

    private void setContentView(String header){

        setContentView(R.layout.activity_signature);
        setHeading(header);
        setBtnCancelar();
        setBtnCapturar();
        setProgressDialog(this);
        setSignaturePad();
        setBtnCancelar();

    }

    @Override
    protected void capturarContenido(String guardiaKey) {
        super.capturarContenido(guardiaKey);
        progressDialog.show();
        setBitacoraDatabaseReferenceBasicValues(null, null, null, null, guardiaKey);
        uploadMediaContent(TipoCaptura.Asistencia);
        capturarAsistencia(getUploadMediaContentStringUrl(), true, guardiaKey);
        progressDialog.dismiss();
    }

    private void capturarAsistencia(String url, boolean status, String key){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/firma", url);
        childUpdates.put("/asistio", status);

        BitacoraDatabaseReference(key).updateChildren(childUpdates);
    }

}
