package com.example.legible.seguridadargusapp.View.Activity;

import android.app.ProgressDialog;
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
import com.example.legible.seguridadargusapp.Model.ObjectModel.BitacoraRegistro;
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
import java.util.HashMap;
import java.util.Map;

public class GuardiaSignatureActivity extends AppCompatActivity {

    private FrameLayout viewSignaturePad;
    private LinearLayout viewNoAsistioInput, viewHourController;
    private CheckBox checkBoxCF;
    private boolean isConfirmarInasistencia = false;
    private SignaturePad signaturePad;
    private Button saveButton, clearButton, cancelButton, restarHoraButtonController, sumarHoraButtonController;
    private EditText editTextObservacion;
    private TextView textViewCurrentGuardiaName, textViewCurrentHour;
    private ProgressDialog progressDialog;


    //Reference for Bitacora
    private Boolean dobleTurno = false;
    private Boolean cubreDescanso = false;
    private Boolean asistio = true;
    private Boolean horasExtra = false;
    private long hourTotalStatus = 0;


    String turno, guardiaNombre,guardiaKey,guardiaFirma;
    String observacion, dateKey, currentDate,cliente,zona ;
    String guardiaFirmaExtra;


    private final static String TAG = GuardiaSignatureActivity.class.getSimpleName();
    private guardias mGuardia;
    private BitacoraGuardia mOtherBitacoraGuardia;

    // Database References

    DatabaseReference mRefBitacora =
            FirebaseDatabase.getInstance().getReference()
                    .child("Argus")
                    .child("Bitacora");

    private DatabaseReference mBitacoraRegistroNRRef =
            FirebaseDatabase.getInstance().getReference()
                    .child("Argus")
                    .child("BitacoraRegistroNoResuelto")
                    .child(ClienteRecyclerAdapter.mySupervisorKey);

    private DatabaseReference mBitacoraFechaRef =
            FirebaseDatabase.getInstance().getReference()
                    .child("Argus")
                    .child("Bitacora")
                    .child(new DatePost().getDateKey());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setXmlViews();

        createProgressDialog();

        //change screen orientation to landscape mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sumarHoraButtonController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hourTotalStatus <12){

                    /**
                     * Update TextView
                     * */
                    // Set TextView to long
                    String hour = textViewCurrentHour.getText().toString();
                    //add Value to long
                    hourTotalStatus = Long.parseLong(hour);
                    hourTotalStatus++;
                    //convert long to String -> textView
                    textViewCurrentHour.setText(String.valueOf(hourTotalStatus));

                }else{
                    //Todo Add String reference
                    Toast.makeText(GuardiaSignatureActivity.this,"Solo se pueden agregar 12 como max",Toast.LENGTH_SHORT).show();
                }
                Log.v(TAG, Long.toString(hourTotalStatus));
            }
        });

        restarHoraButtonController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hourTotalStatus >1){

                    /**
                     * Update TextView
                     * */
                    // Set TextView to long
                    String hour = textViewCurrentHour.getText().toString();
                    //add Value to long
                    hourTotalStatus = Long.parseLong(hour);
                    hourTotalStatus--;
                    //convert long to String -> textView
                    textViewCurrentHour.setText(String.valueOf(hourTotalStatus));

                }else{
                    //Todo Add String reference
                    Toast.makeText(GuardiaSignatureActivity.this,"Solo se puede agregar 1 como min",Toast.LENGTH_SHORT).show();
                }

                Log.v(TAG, Long.toString(hourTotalStatus));
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
                progressDialog.show();
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

    private void updateFechaInfo(){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/fecha", new DatePost().getDateKey());
        mBitacoraFechaRef.updateChildren(childUpdates);
    }

    private void uploadContent(){

        //Begin Upload Task
        UploadTask uploadTask = getImageRef().putBytes(getDataFromSignaturePadAsBytes());

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Handle unsuccesful uploads
                progressDialog.dismiss();
                FirebaseErrorMessageShow(e.toString());
                Log.v(TAG,"OnFailure" + e.toString() );
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //taskSnapshot.getMetadata() contains file metadata such as size, content -type, and download URL.


                //Push Data to Firebase if Image uploadad succesfull
                // sets the downloadUrl and asigns it to the current status on whether it's an assitance or extra assistance signature
                setDownLoadUrlOnGuardiaFirmaOrGuardiaFirmaExtra(taskSnapshot);

                pushData();

                progressDialog.dismiss();

                Intent resultIntent = new Intent();

                resultIntent.putExtra(GuardiaListaActivity.EXTRA_ASISTENCIA_GUARDIA_CAPTURADO, guardiaNombre);



                resultIntent.putExtra(GuardiaListaActivity.EXTRA_ASISTIO,String.valueOf(asistio));
                resultIntent.putExtra(GuardiaListaActivity.EXTRA_DOBLE_TURNO,String.valueOf(dobleTurno));
                resultIntent.putExtra(GuardiaListaActivity.EXTRA_CUBRE_DESCANSO,String.valueOf(cubreDescanso));

                resultIntent.putExtra(GuardiaListaActivity.EXTRA_CUBRE_DESCANSO,String.valueOf(hourTotalStatus));

                setResult(RESULT_OK, resultIntent);

                //Update guardiasArrayAdapter;
                GuardiaListaRecyclerAdapter.updateGuardiaList();

                finish();

                Toast.makeText(GuardiaSignatureActivity.this, "Captura fue Enviado con Exito", Toast.LENGTH_SHORT).show();



            }
        });



    }

    private void pushData() {

        turno = mGuardia.getUsuarioTurno();

        guardiaKey = getIntent().getStringExtra("guardiaKey");

        guardiaNombre = getIntent().getStringExtra("guardiaNombre");

        observacion = editTextObservacion.getText().toString();

        dateKey = new DatePost().getDateKey();

        currentDate = new DatePost().getDatePost();

        cliente = ClienteRecyclerAdapter.myCliente;

        zona = ClienteRecyclerAdapter.myZona;


        if (asistio || dobleTurno || cubreDescanso || horasExtra) {
            if (mOtherBitacoraGuardia != null) {

                if (mOtherBitacoraGuardia.isAsistio()) {
                    guardiaFirma = mOtherBitacoraGuardia.getFirma();
                    asistio = mOtherBitacoraGuardia.isAsistio();
                }

                //Get Current Hours

                currentDate = new DatePost().getDatePost();


                if (cubreDescanso) {
                    dobleTurno = mOtherBitacoraGuardia.isDobleTurno();
                    hourTotalStatus = mOtherBitacoraGuardia.getHorasExtra();
                } else if (dobleTurno) {
                    cubreDescanso = mOtherBitacoraGuardia.isCubreDescanso();
                    hourTotalStatus = mOtherBitacoraGuardia.getHorasExtra();
                } else if (hourTotalStatus>0){
                    cubreDescanso = mOtherBitacoraGuardia.isCubreDescanso();
                    dobleTurno = mOtherBitacoraGuardia.isDobleTurno();
                }
                else {
                    cubreDescanso = mOtherBitacoraGuardia.isCubreDescanso();
                    dobleTurno = mOtherBitacoraGuardia.isDobleTurno();
                    hourTotalStatus = mOtherBitacoraGuardia.getHorasExtra();
                }

            }


            BitacoraGuardia bc = new BitacoraGuardia(
                    asistio,
                    dobleTurno,
                    cubreDescanso,
                    hourTotalStatus,
                    guardiaFirmaExtra,
                    guardiaFirma,
                    observacion,
                    cliente,
                    zona,
                    turno,
                    guardiaNombre,
                    currentDate);

            mRefBitacora.child(dateKey).child(guardiaKey).setValue(bc);
            updateFechaInfo();

            DatabaseReference reference = GuardiaListaRecyclerAdapter.ClienteGuardiasRef;
            reference.child(guardiaKey).setValue(new guardias(guardiaKey,guardiaNombre, asistio, cubreDescanso, dobleTurno, hourTotalStatus,new DatePost().getDate()));



        } else{

            if (isConfirmarInasistencia) {

                //Send Aproval Notification
                Notificacion notificacion =
                        new Notificacion("CF", guardiaNombre + " " + observacion, new DatePost().getDatePost(), new DatePost().getDateKey(), guardiaKey, cliente);

                DatabaseReference referenceNot =
                        FirebaseDatabase.getInstance().getReference().child("Argus");

                String notificationTmpKey = referenceNot.push().getKey();

                referenceNot.child("NotificacionTmp").child(notificationTmpKey).setValue(notificacion);
                referenceNot.child("Notificacion").push().setValue(notificacion);

                String bitacoraRegistroNRKey = new DatePost().getTimeCompletetKey();

                BitacoraRegistro bitacoraRegistro = new BitacoraRegistro("Confirmacion de Inasistencia : " + observacion, 3, ClienteRecyclerAdapter.mySupervisor, ClienteRecyclerAdapter.myZona, new DatePost().get24HourFormat());
                bitacoraRegistro.setObservacionKey(bitacoraRegistroNRKey);
                bitacoraRegistro.setHora(new DatePost().get24HourFormat());
                mBitacoraRegistroNRRef.child(bitacoraRegistroNRKey).setValue(bitacoraRegistro);

                // add BitacoraInformacion to Notificacion Tmp for web reference use
                // TODO: ADD Notification key
                referenceNot.child("NotificacionTmp")
                        .child(notificationTmpKey)
                        .child("bitacoraInformacion")
                        .setValue(new BitacoraRegistro
                                (new DatePost().getDateKey(),
                                        ClienteRecyclerAdapter.mySupervisorKey,
                                        bitacoraRegistroNRKey));


                updateBitacoraRegistroNRSupervisorInfo(ClienteRecyclerAdapter.mySupervisor);

            }

            BitacoraGuardia bitacoraNoAsistio =
                    new BitacoraGuardia(false, false, false, 0, null, null, " ", cliente, zona, turno, guardiaNombre, currentDate);

            mRefBitacora.child(dateKey).child(guardiaKey).setValue(bitacoraNoAsistio);
            updateFechaInfo();

            DatabaseReference reference =
                    GuardiaListaRecyclerAdapter.ClienteGuardiasRef;
            reference.child(guardiaKey).setValue(new guardias(guardiaKey,guardiaNombre, asistio, cubreDescanso, dobleTurno, hourTotalStatus,new DatePost().getDate()));

        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonAsistio:
                if (checked)
                    asistio = true;
                    cubreDescanso = false;
                    dobleTurno = false;
                    horasExtra = false;
                    hourTotalStatus = 0;
                    Toast.makeText(GuardiaSignatureActivity.this,"Asistencia",Toast.LENGTH_SHORT).show();
                    //View(s)
                    viewSignaturePad.setVisibility(View.VISIBLE);
                    viewNoAsistioInput.setVisibility(View.GONE);
                    viewHourController.setVisibility(View.GONE);
                    saveButton.setEnabled(false);
                    clearButton.setVisibility(View.VISIBLE);

                break;
            case R.id.radioButtonNoAsistio:
                if (checked)
                    asistio= false;
                    hourTotalStatus = 0;
                    Toast.makeText(GuardiaSignatureActivity.this,"Inasistencia",Toast.LENGTH_SHORT).show();
                    viewNoAsistioInput.setVisibility(View.VISIBLE);
                    viewSignaturePad.setVisibility(View.GONE);
                    viewHourController.setVisibility(View.GONE);
                    saveButton.setEnabled(true);
                    clearButton.setVisibility(View.GONE);
                break;

            case R.id.radioButtonCubreDescanso:
                if (checked)
                    cubreDescanso = true;
                    asistio =false;
                    dobleTurno = false;
                    horasExtra = false;
                    hourTotalStatus = 0;
                    Toast.makeText(GuardiaSignatureActivity.this,"Descanso Laborado",Toast.LENGTH_SHORT).show();
                    viewSignaturePad.setVisibility(View.VISIBLE);
                    viewNoAsistioInput.setVisibility(View.GONE);
                    viewHourController.setVisibility(View.GONE);
                    saveButton.setEnabled(false);
                    clearButton.setVisibility(View.VISIBLE);


                break;

            case R.id.radioButtonDobleTurno:
                if (checked)
                    dobleTurno = true;
                    cubreDescanso = false;
                    asistio = false;
                    horasExtra = false;
                    hourTotalStatus = 0;
                    Toast.makeText(GuardiaSignatureActivity.this,"Doble Turno",Toast.LENGTH_SHORT).show();
                    viewSignaturePad.setVisibility(View.VISIBLE);
                    viewNoAsistioInput.setVisibility(View.GONE);
                    viewHourController.setVisibility(View.GONE);
                    saveButton.setEnabled(false);
                    clearButton.setVisibility(View.VISIBLE);

                break;

            case R.id.radioButtonHorasExtra:
                if (checked)
                    horasExtra = true;
                    asistio = false;
                    dobleTurno = false;
                    cubreDescanso = false;
                    Toast.makeText(GuardiaSignatureActivity.this,"Horas Extra",Toast.LENGTH_SHORT).show();
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
                //
                isConfirmarInasistencia = checked;

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


        if (cubreDescanso||dobleTurno || horasExtra){
            image = "imageFirmaExtra";
        }

        // Create a child reference
        // imagesRef now points to "image"
        return  storageRef
                .child("Bitacora")
                .child(new DatePost().getDateKey())// Takes the de
                .child(getIntent().getStringExtra("guardiaKey"))
                .child(image);

    }

    private void setDownLoadUrlOnGuardiaFirmaOrGuardiaFirmaExtra(UploadTask.TaskSnapshot taskSnapshot) {

        // Download Url for Signature Image
        String downLoadUrl = taskSnapshot.getDownloadUrl().toString();

        if (asistio){
            guardiaFirma = downLoadUrl;
        }else if (cubreDescanso || dobleTurno || horasExtra){
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

            progressDialog.dismiss();
            FirebaseErrorMessageShow(databaseError.toString());
        }
    }

    class otherBitaCoraEventListener implements ValueEventListener{

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mOtherBitacoraGuardia = dataSnapshot.getValue(BitacoraGuardia.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

            progressDialog.dismiss();
            FirebaseErrorMessageShow(databaseError.toString());
        }
    }

    private void FirebaseErrorMessageShow(String e){
        Toast.makeText(this, e, Toast.LENGTH_LONG).show();
    }

    private void createProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("CARGANDO...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }

    private void updateBitacoraRegistroNRSupervisorInfo(String supervisor){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/supervisor", supervisor);
        mBitacoraRegistroNRRef.updateChildren(childUpdates);
    }

}