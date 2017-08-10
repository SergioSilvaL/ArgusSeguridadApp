package com.example.legible.seguridadargusapp.View.Activity.Captura;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.R;

import java.util.HashMap;
import java.util.Map;

public class HorasExtraActivity extends CapturaActivity {

    private TextView txtHora;
    private Button btnRestarHora;
    private Button btnSumarHora;
    private long horas = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Todo nombre del guardia
        setContentView("Despacito");

        btnCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo set Guardia Key
                capturarContenido("despzci");
            }
        });

    }

    private void setContentView(String header){

        setContentView(R.layout.activity_horas_extra);
        setHeading(header);
        setBtnCancelar();

        setBtnCapturar();
        setProgressDialog(this);
        setSignaturePad();
        setClearButton();

        txtHora = (TextView) findViewById(R.id.txtHour);
        btnRestarHora = (Button) findViewById(R.id.btnHourRestar);
        btnSumarHora = (Button) findViewById(R.id.btnHourSumar);


    }

    /**
     * 1. Get total hours
     * 2. Update textview
     * */
    public void btnClick(View view){
        Button clickedButton = (Button)view;


        switch (clickedButton.getId()){
            case R.id.btnHourSumar:
                // Code
                if (horas<12){
                    horas++;
                }else{
                    Toast.makeText(HorasExtraActivity.this,"Solo se pueden agregar 12 como max",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnHourRestar:
                // Code
                if (horas>1){
                    horas--;
                }else{
                    Toast.makeText(HorasExtraActivity.this,"Solo se puede agregar 1 como min",Toast.LENGTH_SHORT).show();
                }
                break;

        }

        txtHora.setText(String.valueOf(horas));

    }


    @Override
    protected void capturarContenido(String guardiaKey) {
        setBitacoraDatabaseReferenceBasicValues(null, null, null, null, guardiaKey);
        uploadMediaContent(TipoCaptura.HorasExtra);
        capturarHorasExtra(getUploadMediaContentStringUrl(), horas, guardiaKey);
    }

    private void capturarHorasExtra(String url, long horas, String key){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/firma", url);
        childUpdates.put("/horasExtra", horas);

        BitacoraDatabaseReference(key).updateChildren(childUpdates);
    }




}