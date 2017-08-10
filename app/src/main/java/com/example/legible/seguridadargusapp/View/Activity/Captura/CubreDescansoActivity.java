package com.example.legible.seguridadargusapp.View.Activity.Captura;

import android.os.Bundle;
import android.view.View;
import com.example.legible.seguridadargusapp.R;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergiosilva on 7/24/17.
 */

public class CubreDescansoActivity extends CapturaActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Todo: set Guardia Name
        setContentView("sadf");

        btnCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo Set Guardia Key
                capturarContenido("asdf");
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
        setBitacoraDatabaseReferenceBasicValues(null, null, null, null, guardiaKey);
        uploadMediaContent(TipoCaptura.CubreDescanso);
        capturarCubreDescanso(getUploadMediaContentStringUrl(), true, guardiaKey);
    }

    private void capturarCubreDescanso(String url, boolean status, String key){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/firma", url);
        childUpdates.put("/cubreDescanso", status);

        BitacoraDatabaseReference(key).updateChildren(childUpdates);
    }
}
