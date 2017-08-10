package com.example.legible.seguridadargusapp.View.Activity.Captura;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.legible.seguridadargusapp.R;

import java.util.HashMap;
import java.util.Map;

public class NoAsistioActivity extends CaptureBasectivity {


    private CheckBox checkBoxCF;
    private EditText editTxtObservacion;
    private boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView("Despacito");

        btnCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarContenido("des");
            }
        });

    }

    /*
    * Sets Activity Controllers
    * **/

    private void setContentView(String header){

        // set Basic Controllers
        setContentView(R.layout.activity_no_asistio);
        setHeading(header);
        setBtnCapturar();
        setBtnCancelar();
        setProgressDialog(this);

        // Set Specific Controllers
        checkBoxCF = (CheckBox) findViewById(R.id.CheckBoxCI);
        editTxtObservacion = (EditText) findViewById(R.id.editTxtObservacion);

    }

    /*
    * Sets checkbox on click listener
    * **/

    public void onCheckboxClicked(View view){

        // Is the view now check?
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()){

            case R.id.checkBoxConfirmarAsistencia:
                this.checked = checked;
                break;
        }
    }

    @Override
    protected void capturarContenido(String guardiaKey){
        setBitacoraDatabaseReferenceBasicValues(null, null, null, null, guardiaKey);
        capturarInasistencia(false,editTxtObservacion.getText().toString(),guardiaKey);

    }

    private void capturarInasistencia(boolean status, String observacion, String guardiaKey){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/asistio", status);
        childUpdates.put("/observacion", observacion);

        BitacoraDatabaseReference(guardiaKey).updateChildren(childUpdates);
    }


}
