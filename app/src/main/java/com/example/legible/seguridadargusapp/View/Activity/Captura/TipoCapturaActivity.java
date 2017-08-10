package com.example.legible.seguridadargusapp.View.Activity.Captura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.View.Activity.Captura.AsistioActivity;
import com.example.legible.seguridadargusapp.View.Activity.Captura.CubreDescansoActivity;
import com.example.legible.seguridadargusapp.View.Activity.Captura.DobleTurnoActivity;
import com.example.legible.seguridadargusapp.View.Activity.Captura.HorasExtraActivity;
import com.example.legible.seguridadargusapp.View.Activity.Captura.NoAsistioActivity;

public class TipoCapturaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_captura);

    }

    public void capturaClick(View view){
        LinearLayout clickedButton = (LinearLayout) view;

        switch (clickedButton.getId()){
            case R.id.btnCapturaAsistencia:
                startActivity(new Intent(this, AsistioActivity.class));
            break;

            case R.id.btnCapturaInAsistencia:
                startActivity(new Intent(this, NoAsistioActivity.class));
                break;

            case R.id.btnCapturaDobleTurno:
                startActivity(new Intent(this, DobleTurnoActivity.class));
                break;

            case R.id.btnCapturaCubreDescanso:
                startActivity(new Intent(this, CubreDescansoActivity.class));
                break;

            case R.id.btnCapturaHorasExtra:
                startActivity(new Intent(this, HorasExtraActivity.class));
                break;

        }
    }
}
