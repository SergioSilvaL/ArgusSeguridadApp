package com.example.legible.seguridadargusapp.View.Activity.Captura;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.Model.ObjectModel.DatePost;
import com.example.legible.seguridadargusapp.R;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by sergiosilva on 7/20/17.
 */

public class CapturaActivity extends CaptureBasectivity {

    protected SignaturePad signaturePad;
    protected Button btnClear;
    private String downloadUrlMediaString;


    protected void setSignaturePad() {

        if (signaturePad == null)
            signaturePad = (SignaturePad) findViewById(R.id.signPad);

//        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
//            @Override
//            public void onStartSigning() {
//
//            }
//
//            @Override
//            public void onSigned() {
//
//            }
//
//            @Override
//            public void onClear() {
//
//            }
//        });
    }

    protected void setClearButton(){
        if (btnClear == null)
            btnClear = (Button) findViewById(R.id.btnClear);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signaturePad == null)
                    signaturePad = (SignaturePad) findViewById(R.id.signPad);
                signaturePad.clear();
            }
        });
    }

    protected void uploadMediaContent(TipoCaptura tipoCaptura) {

        UploadTask uploadTask = setImageStorageRef(tipoCaptura).putBytes(getDataFromSignaturePadAsBytes());

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                downloadUrlMediaString = null;
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // Todo: get Image URL
                downloadUrlMediaString = taskSnapshot.getDownloadUrl().toString();
            }
        });
    }

    protected String getUploadMediaContentStringUrl ()
    {
        return downloadUrlMediaString;
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

    private StorageReference setImageStorageRef(TipoCaptura tipoCaptura) {

        String storageDir = "";

        switch (tipoCaptura){
            case Asistencia:
                storageDir = "image";
            break;

            case CubreDescanso:
            case HorasExtra:
            case DobleTurno:
                storageDir = "imageFirmaExtra";
                break;
        }

        // Create a storage reference from our app
        StorageReference storageRef =  FirebaseStorage.getInstance().getReference();

        // Create a child reference
        // imagesRef now points to "image"
        return  storageRef
                .child("Bitacora")
                .child(new DatePost().getDateKey())// Takes the current Date
                // Todo: set get GuardiaKey
                .child("123")
                .child(storageDir);

    }


    @Override
    protected void capturarContenido(String guardiaKey) {

    }
}
