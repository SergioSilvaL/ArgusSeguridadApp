package com.example.legible.seguridadargusapp.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.Controller.ClienteRecyclerAdapter;
import com.example.legible.seguridadargusapp.Controller.GuardiaListaRecyclerAdapter;
import com.example.legible.seguridadargusapp.Model.ObjectModel.BitacoraGuardia;
import com.example.legible.seguridadargusapp.Model.ObjectModel.DatePost;
import com.example.legible.seguridadargusapp.Model.ObjectModel.Notificacion;
import com.example.legible.seguridadargusapp.Model.ObjectModel.guardias;
import com.example.legible.seguridadargusapp.R;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class GuardiaSignatureActivity extends AppCompatActivity {

    FrameLayout viewSignaturePad;
    LinearLayout viewNoAsistioInput, viewHourController;
    CheckBox checkBoxCF;
    boolean isConfirmarInasistencia = false;
    SignaturePad signaturePad;
    Button saveButton, clearButton, cancelButton, restarHoraButtonController, sumarHoraButtonController;
    EditText editTextObservacion;
    TextView textViewCurrentGuardiaName, textViewCurrentHour;
    String status="Asistio";
    String statusExtra = "";
    String turno, guardiaNombre,guardiaKey,guardiaFirma;
    String observacion, dateKey, currentDate,cliente,zona ;
    String guardiaFirmaExtra;
    Boolean dobleTurno = false, cubreDescanso = false, asistio = true;
    private long hourStatus = 1;
    private final static String TAG = GuardiaListaActivity.class.getSimpleName();
    private guardias mGuardia;
    private BitacoraGuardia mOtherBitacoraGuardia;

    // Database References

    DatabaseReference mRefBitacora =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("Bitacora");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setXmlViews();

        //change screen orientation to landscape mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sumarHoraButtonController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hourStatus<12){

                    /**
                     * Update TextView
                     * */
                    // Set TextView to long
                    String hour = textViewCurrentHour.getText().toString();
                    //add Value to long
                    hourStatus = Long.parseLong(hour);
                    hourStatus ++;
                    //convert long to String -> textView
                    textViewCurrentHour.setText(String.valueOf(hourStatus));

                }else{
                    //Todo Add String reference
                    Toast.makeText(GuardiaSignatureActivity.this,"Solo se podran agreggar 11 como max",Toast.LENGTH_SHORT).show();
                }
                Log.v(TAG, Long.toString(hourStatus));
            }
        });

        restarHoraButtonController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hourStatus>1){

                    /**
                     * Update TextView
                     * */
                    // Set TextView to long
                    String hour = textViewCurrentHour.getText().toString();
                    //add Value to long
                    hourStatus = Long.parseLong(hour);
                    hourStatus --;
                    //convert long to String -> textView
                    textViewCurrentHour.setText(String.valueOf(hourStatus));

                }else{
                    //Todo Add String reference
                    Toast.makeText(GuardiaSignatureActivity.this,"Solo se podran agreggar 1 como min",Toast.LENGTH_SHORT).show();
                }

                Log.v(TAG, Long.toString(hourStatus));
            }
        });

        // Controlers
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               uploadContent();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Fetch Other Data from Firebase
        DatabaseReference mRefGuardia =
                FirebaseDatabase.getInstance().getReference().child("Argus").child("guardias").child(getIntent().getStringExtra("guardiaKey"));

        mRefGuardia.addValueEventListener(new GuardiaReferenceEventListener());

        DatabaseReference guardiaBitacora =
                FirebaseDatabase.getInstance().getReference().child("Argus").child("Bitacora").child(new DatePost().getDateKey()).child(getIntent().getStringExtra("guardiaKey"));

        guardiaBitacora.addValueEventListener(new otherBitaCoraEventListener());

    }

    private void setXmlViews() {

        //Set Views
        setContentView(R.layout.activity_guardia_signature);
        editTextObservacion = (EditText) findViewById(R.id.editTextObservacion);
        textViewCurrentGuardiaName = (TextView) findViewById(R.id.TextViewSignatureCurrentGuardiaName);
        textViewCurrentGuardiaName.setText(getIntent().getStringExtra("guardiaNombre"));
        signaturePad = (SignaturePad)findViewById(R.id.signaturePad);
        saveButton = (Button)findViewById(R.id.saveButton);
        clearButton = (Button)findViewById(R.id.clearButton);
        cancelButton = (Button) findViewById(R.id.CancelButton);
        checkBoxCF = (CheckBox) findViewById(R.id.checkBoxConfirmarAsistencia);
        viewSignaturePad = (FrameLayout) findViewById(R.id.viewSignaturePad);
        viewNoAsistioInput = (LinearLayout) findViewById(R.id.viewNoAsistioInput);
        viewHourController = (LinearLayout) findViewById(R.id.viewHourController);
        restarHoraButtonController = (Button) findViewById(R.id.buttonHourControllerRestar);
        sumarHoraButtonController = (Button) findViewById(R.id.buttonHourControllerSumar);
        textViewCurrentHour = (TextView) findViewById(R.id.textViewHourControllerIndicator);

        //Enable the default Views
        viewSignaturePad.setVisibility(View.VISIBLE);
        viewNoAsistioInput.setVisibility(View.GONE);
        viewHourController.setVisibility(View.GONE);


        //disable both buttons at start
        saveButton.setEnabled(false);
        clearButton.setEnabled(false);


    }

    private void uploadContent(){

        //Begin Upload Task
        UploadTask uploadTask = getImageRef().putBytes(getDataFromSignaturePadAsBytes());

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Handle unsuccesful uploads
                Log.v("GuadriaSignature","OnFailure" );
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //taskSnapshot.getMetadata() contains file metadata such as size, content -type, and download URL.


                //Push Data to Firebase if Image uploadad succesfull
                // sets the downloadUrl and asigns it to the current status on whether it's an assitance or extra assistance signature
                setDownLoadUrlOnGuardiaFirmaOrGuardiaFirmaExtra(taskSnapshot);

                pushData();

                Intent resultIntent = new Intent();

                resultIntent.putExtra(GuardiaListaActivity.EXTRA_ASISTENCIA, status);
                resultIntent.putExtra(GuardiaListaActivity.EXTRA_ASISTENCIA_GUARDIA_CAPTURADO, guardiaNombre);
                resultIntent.putExtra(GuardiaListaActivity.EXTRA_DOBLE_ASISTENCIA, statusExtra);

                setResult(RESULT_OK, resultIntent);

                //Update guardiasArrayAdapter;
                GuardiaListaRecyclerAdapter.updateGuardiaList();

                finish();

                Toast.makeText(GuardiaSignatureActivity.this, "Captura fue Enviado con Exito", Toast.LENGTH_SHORT).show();



            }
        });



    }

    private void pushData(){

        turno = mGuardia.getUsuarioTurno();

        guardiaKey = getIntent().getStringExtra("guardiaKey");

        guardiaNombre = getIntent().getStringExtra("guardiaNombre");

        observacion = editTextObservacion.getText().toString();

        dateKey = new DatePost().getDateKey();

        currentDate = new DatePost().getDatePost();

        cliente = ClienteRecyclerAdapter.myCliente;

        zona = ClienteRecyclerAdapter.myZona;



        if (mOtherBitacoraGuardia != null){

            if (mOtherBitacoraGuardia.isAsistio()){
                guardiaFirma = mOtherBitacoraGuardia.getFirma();
            }

            currentDate = new DatePost().getDatePost();

            cubreDescanso = mOtherBitacoraGuardia.isCubreDescanso();
            dobleTurno = mOtherBitacoraGuardia.isDobleTurno();

            if (status.equals("Cubre Descanso")){
                cubreDescanso = true;
                dobleTurno = false;
            } else if (status.equals("Doble Turno")){
                dobleTurno = true;
                cubreDescanso = false;
            }

        }

        if (asistio){
            status = "asistio";
        }




        BitacoraGuardia bc = new BitacoraGuardia(
                asistio,
                dobleTurno,
                cubreDescanso,
                guardiaFirmaExtra,
                guardiaFirma,
                observacion,
                cliente,
                zona,
                turno,
                guardiaNombre,
                currentDate);


        if (status!= "No Asistió") {

            mRefBitacora.child(dateKey).child(guardiaKey).setValue(bc);
            //mRefBitacora.child(dateKey).setValue(fecha);

            DatabaseReference reference = GuardiaListaRecyclerAdapter.ClienteGuardiasRef;
            reference.child(guardiaKey).setValue(new guardias(guardiaKey, guardiaNombre, status,statusExtra, new DatePost().getDate()));

        }else {

             //Todo modify it's vallues so only the un assistance does not show



            if (isConfirmarInasistencia) {
            //Send Aproval Notification
            Notificacion notificacion = new Notificacion("CF", guardiaNombre+" " + observacion, new DatePost().getDatePost(), new DatePost().getDateKey(),guardiaKey, cliente);

            DatabaseReference referenceNot =  FirebaseDatabase.getInstance().getReference()
                    .child("Argus");

            referenceNot.child("NotificacionTmp").push().setValue(notificacion);
            referenceNot.child("Notificacion").push().setValue(notificacion);

            }

            BitacoraGuardia bitacoraNoAsistio = new BitacoraGuardia(false,false,false,null,null," ",cliente,zona,turno,guardiaNombre,currentDate);

            mRefBitacora.child(dateKey).child(guardiaKey).setValue(bitacoraNoAsistio);//.setValue(bc);
            //mRefBitacora.child(dateKey).setValue(fecha);


            DatabaseReference reference = GuardiaListaRecyclerAdapter.ClienteGuardiasRef;
            reference.child(guardiaKey).setValue(new guardias(guardiaKey, guardiaNombre, status,statusExtra, new DatePost().getDate()));


        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonAsistio:
                if (checked)
                    status = "Asistió";
                    asistio = true;
                    viewSignaturePad.setVisibility(View.VISIBLE);
                    viewNoAsistioInput.setVisibility(View.GONE);
                    viewHourController.setVisibility(View.GONE);
                    saveButton.setEnabled(false);
                    clearButton.setVisibility(View.VISIBLE);

                break;
            case R.id.radioButtonNoAsistio:
                if (checked)
                    status = "No Asistió";
                    asistio= false;
                    viewNoAsistioInput.setVisibility(View.VISIBLE);
                    viewSignaturePad.setVisibility(View.GONE);
                    viewHourController.setVisibility(View.GONE);
                    saveButton.setEnabled(true);
                    clearButton.setVisibility(View.GONE);
                break;

            case R.id.radioButtonCubreDescanso:
                if (checked)
                    statusExtra = "Cubre Descanso";
                    status = "Cubre Descanso";
                    cubreDescanso = true;
                    asistio =false;
                    dobleTurno = false;
                    viewSignaturePad.setVisibility(View.VISIBLE);
                    viewNoAsistioInput.setVisibility(View.GONE);
                    viewHourController.setVisibility(View.GONE);
                    saveButton.setEnabled(false);
                    clearButton.setVisibility(View.VISIBLE);


                break;

            case R.id.radioButtonDobleTurno:
                if (checked)
                    statusExtra = "Doble Turno";
                    status = "Doble Turno";
                    dobleTurno = true;
                    cubreDescanso = false;
                    viewSignaturePad.setVisibility(View.VISIBLE);
                    viewNoAsistioInput.setVisibility(View.GONE);
                    viewHourController.setVisibility(View.GONE);
                    saveButton.setEnabled(false);
                    clearButton.setVisibility(View.VISIBLE);

                break;

            case R.id.radioButtonHorasExtra:
                if (checked)
                    statusExtra = "Horas Extra";
                    status = "Horas Extra";
                    dobleTurno = false;
                    cubreDescanso = false;
                    viewSignaturePad.setVisibility(View.VISIBLE);
                    viewNoAsistioInput.setVisibility(View.GONE);
                    viewHourController.setVisibility(View.VISIBLE);
                    saveButton.setEnabled(false);
                    clearButton.setVisibility(View.VISIBLE);



        }
    }

    public void onCheckboxClicked(View view){


        // Is the view now check?
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()){

            case R.id.checkBoxConfirmarAsistencia:
                if (checked)
                    //
                    isConfirmarInasistencia = true;
                else
                    isConfirmarInasistencia = false;

                break;
        }
    }

    private byte[] getDataFromSignaturePadAsBytes(){

        //Get the data from Signature Pad as bytes
        signaturePad.setDrawingCacheEnabled(true);
        signaturePad.buildDrawingCache();

        Bitmap signaturePadDrawingCache = signaturePad.getDrawingCache();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        signaturePadDrawingCache.compress(Bitmap.CompressFormat.JPEG,100,baos);

        return baos.toByteArray();
    }

    private StorageReference getImageRef() {

        // Create a storage reference from our app
        StorageReference storageRef =  FirebaseStorage.getInstance().getReference();

        // Defualt Image if only an asssistance was taken
        String image = "image";

        // If it's taken as a extra asistance a new image is created with the signature taken
        if (status.equals("Cubre Descanso") || status.equals("Doble Turno")){
            image = "imageFirmaExtra";
        }

        // Create a child reference
        // imagesRef now points to "image"
        StorageReference imagesRef = storageRef
                .child("Bitacora")
                .child(new DatePost().getDateKey())// Takes the de
                .child(getIntent().getStringExtra("guardiaKey"))
                .child(image);


        return  imagesRef;
    }

    private void setDownLoadUrlOnGuardiaFirmaOrGuardiaFirmaExtra(UploadTask.TaskSnapshot taskSnapshot) {

        // Download Url for Signature Image
        String downLoadUrl = taskSnapshot.getDownloadUrl().toString();

        if (status.equals("Asistio")) {
            guardiaFirma = downLoadUrl;
        } else if (status.equals("Cubre Descanso") || status.equals("Doble Turno")) {
            guardiaFirmaExtra = downLoadUrl;
        }


    }

    class GuardiaReferenceEventListener implements ValueEventListener{

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mGuardia = dataSnapshot.getValue(guardias.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    class otherBitaCoraEventListener implements ValueEventListener{

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mOtherBitacoraGuardia = dataSnapshot.getValue(BitacoraGuardia.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    }

}