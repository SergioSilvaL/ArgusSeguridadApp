package com.example.legible.seguridadargusapp.View.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.Controller.ClienteRecyclerAdapter;
import com.example.legible.seguridadargusapp.Model.ObjectModel.BitacoraRegistro;
import com.example.legible.seguridadargusapp.Model.ObjectModel.DatePost;
import com.example.legible.seguridadargusapp.Model.ObjectModel.Notificacion;
import com.example.legible.seguridadargusapp.Model.ObjectModel.guardias;
import com.example.legible.seguridadargusapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sergiosilva on 3/8/17.
 */

public class GuardiaTemporalAddDialogFragment extends DialogFragment{


    private static final String action = "AG";
    private String supervisorNombre;//Referenced from class

    //private Date date
    //private String Cliente//referenced from other class
    private EditText usuarioDomicilio;
    private EditText usuarioNombre;
    private EditText usuarioTelefono;
    private Button btnAddGuardia;
    private DatabaseReference mNotificationRef =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("Notificacion");

    private DatabaseReference mNotificationTmpRef =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("NotificacionTmp");

    private DatabaseReference mBitacoraRegistroRef =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("BitacoraRegistro").child(new DatePost().getDateKey()).child(ClienteRecyclerAdapter.mySupervisorKey);

    public GuardiaTemporalAddDialogFragment(){}


    public static GuardiaTemporalAddDialogFragment newInstance(String cliente,String supervisor){

        GuardiaTemporalAddDialogFragment fragment = new GuardiaTemporalAddDialogFragment();



        Bundle args = new Bundle();
        args.putString("supervisorNombre",supervisor);
        args.putString("clienteNombre",cliente);
        fragment.setArguments(args);

        return fragment;


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Agregar Guardia");

        //Edited: Overrriding  onCreateView is not necessary in your case
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_fragment_guadria_temporal_add,null);

        usuarioDomicilio = (EditText) view.findViewById(R.id.editTextGuardiaDomicillio);
        usuarioNombre = (EditText) view.findViewById(R.id.editTextGuardiaNombre);
        usuarioTelefono = (EditText) view.findViewById(R.id.editTextGuardiaTelefono);



        builder.setView(view);

        builder.setPositiveButton("anadair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // se agreaga el guardia
                pushData();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        return builder.create();
    }



    private void pushData(){

        //Get Action
        String accion = "AG";

        //Get Current Date
        DatePost datePost = new DatePost();
        String fecha = datePost.getDatePost();

        //Get Current SuperVisior
        supervisorNombre = getArguments().getString("supervisorNombre");

        //Set Descripcion
        String descripcion = supervisorNombre + " agrego a un nuevo guardia";


        // Get the current notificition
        Notificacion notificacion = new Notificacion(accion,descripcion,fecha);
        //Set the Guardia data model
        guardias guardia = new guardias();
        guardia.setUsuarioNombre(usuarioNombre.getText().toString());
        guardia.setUsuarioDomicilio(usuarioDomicilio.getText().toString());
        String telefono = usuarioTelefono.getText().toString();
        guardia.setUsuarioTelefono(Long.valueOf(telefono));
        guardia.setUsuarioClienteAsignado(ClienteRecyclerAdapter.myCliente);


        // Push notification with nested class
        String key = mNotificationRef.push().getKey();

        mNotificationRef.child(key).setValue(notificacion);
        mNotificationRef.child(key).child("informacion").setValue(guardia);

        mNotificationTmpRef.child(key).setValue(notificacion);
        mNotificationTmpRef.child(key).child("informacion").setValue(guardia);

        // Todo: Send info to Bitacora
        mBitacoraRegistroRef.child(key).setValue(new BitacoraRegistro(descripcion, 1, ClienteRecyclerAdapter.mySupervisor, ClienteRecyclerAdapter.myZona, new DatePost().get24HourFormat()));

        Toast.makeText(getContext(),"Se envio una solicitud con exito",Toast.LENGTH_LONG).show();

    }
}



















