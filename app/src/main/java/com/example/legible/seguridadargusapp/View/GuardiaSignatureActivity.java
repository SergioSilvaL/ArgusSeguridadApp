package com.example.legible.seguridadargusapp.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.Controller.ClienteRecyclerAdapter;
import com.example.legible.seguridadargusapp.Controller.GuardiaListaRecyclerAdapter;
import com.example.legible.seguridadargusapp.Model.ObjectModel.BitacoraGuardia;
import com.example.legible.seguridadargusapp.Model.ObjectModel.DatePost;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.Date;

public class GuardiaSignatureActivity extends AppCompatActivity {


    SignaturePad signaturePad;
    Button saveButton, clearButton;
    EditText editTextObservacion;
    String status="Asisitio";
    String observacion;
    String cliente;
    String turno;
    String guardiaNombre;
    String guardiaKey;
    String guardiaFirma;

    //set fecha in datekey
    DatabaseReference mRefBitacora =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("Bitacora");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set Views
        setContentView(R.layout.activity_guardia_signature);
        editTextObservacion = (EditText) findViewById(R.id.editTextObservacion);
        signaturePad = (SignaturePad)findViewById(R.id.signaturePad);
        saveButton = (Button)findViewById(R.id.saveButton);
        clearButton = (Button)findViewById(R.id.clearButton);


        //disable both buttons at start
        saveButton.setEnabled(false);
        clearButton.setEnabled(false);

        //change screen orientation to landscape mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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


                observacion = editTextObservacion.getText().toString();


                FirebaseStorage storage = FirebaseStorage.getInstance();

                // Create a storage reference from our app
                StorageReference storageRef =  storage.getReference();

                //GetCurrent dataSpecialFormat
                String fecha = new DatePost().getDateKey();
                // Create a child reference
                // imagesRef now points to "images"


                StorageReference imagesRef = storageRef.child("Bitacora").child(fecha).child(getIntent().getStringExtra("guardiaKey")).child("image");

                //Get the data from Signature Pad as bytes
                signaturePad.setDrawingCacheEnabled(true);
                signaturePad.buildDrawingCache();
                Bitmap signaturePadDrawingCache = signaturePad.getDrawingCache();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                signaturePadDrawingCache.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] data = baos.toByteArray();

                //Begin Upload Task
                UploadTask uploadTask = imagesRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Handle unsuccesful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //taskSnapshot.getMetadata() contains file metadata such as size, content -type, and download URL.

                        // uploadData();

                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.v("downloadUrl",downloadUrl.toString());

                        //Push Data to Firebase if Image uploadad succesfull

                        guardiaFirma = downloadUrl.toString();

                        guardiaKey = getIntent().getStringExtra("guardiaKey");

                        //TODO Turno BUG

                        //Todo UnDO this Bad Practice
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Argus").child("guardias").child(guardiaKey).child("usuarioTurno");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                turno = dataSnapshot.getValue(String.class);
                                guardiaNombre = getIntent().getStringExtra("guardiaNombre");

                                pushData();

                                //startActivity(new Intent(GuardiaSignatureActivity.this, GuardiaListaActivity.class));

                                //Todo Intent Code
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra(GuardiaListaActivity.EXTRA_ASISTENCIA, status);
                                resultIntent.putExtra(GuardiaListaActivity.EXTRA_ASISTENCIA_GUARDIA_CAPTURADO, guardiaNombre);
                                setResult(RESULT_OK, resultIntent);
                                //Change to Intent
                                //GuardiaListaRecyclerAdapter.myGuardiaCaptura = guardiaNombre;]

                                //Update guardiasArrayAdapter;
                                GuardiaListaRecyclerAdapter.updateGuardiaList();


                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });




                    }
                });


                // end of upload intent

                //write code for saving the signature here
                Toast.makeText(GuardiaSignatureActivity.this, "Signature Saved", Toast.LENGTH_SHORT).show();

                //startActivity(new Intent(GuardiaSignatureActivity.this, GuardiaListaActivity.class));

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });



    }

    private void pushData(){

        //Push Data

        DatePost datePost = new DatePost();

        String dateKey = datePost.getDateKey();


        String currentDate = datePost.getDatePost();

        String cliente = ClienteRecyclerAdapter.myCliente;
        String zona = ClienteRecyclerAdapter.myZona;

        BitacoraGuardia bc = new BitacoraGuardia(status,guardiaFirma,observacion,cliente,zona,turno,guardiaNombre,currentDate);


        //Todo set datekey with fecha



        //mRefBitacora.child(dateKey).child(guardiaKey).setValue(bc);
        String fecha = "20170316";

        mRefBitacora.child(dateKey).child(guardiaKey).setValue(bc);
        //mRefBitacora.child(dateKey).setValue(fecha);


        //Todo add the current date

        DatabaseReference reference = GuardiaListaRecyclerAdapter.ClienteGuardiasRef;
        reference.child(guardiaKey).setValue(new guardias(guardiaKey,guardiaNombre,status, new DatePost().getDate()));

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonAsistio:
                if (checked)
                    status = "Asistió";
                break;
            case R.id.radioButtonNoAsistio:
                if (checked)
                    status = "No Asistió";
                break;

            case R.id.radioButtonLlegoTarde:
                if (checked)
                    status = "Llego Tarde";
                break;
        }
    }
}