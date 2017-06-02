package com.example.legible.seguridadargusapp.View.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.Controller.ClienteRecyclerAdapter;
import com.example.legible.seguridadargusapp.Controller.GuardiaRecyclerAdapter;
import com.example.legible.seguridadargusapp.Model.ObjectModel.DatePost;
import com.example.legible.seguridadargusapp.Model.ObjectModel.GuardiaMoveBasicInfo;
import com.example.legible.seguridadargusapp.Model.ObjectModel.Notificacion;
import com.example.legible.seguridadargusapp.Model.ObjectModel.guardias;
import com.example.legible.seguridadargusapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiosilva on 3/11/17.
 */

public class GuardiaMoveDialogFragment extends DialogFragment{

    private static final String TAG = GuardiaMoveDialogFragment.class.getSimpleName();

    private Spinner spinner;

    private static final String NOTIFICATION_ACTION = "MG";

    private guardias currentGuardia;

    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Argus");


    // Firebase Reference(s)

    // Clients inside database reference
    private DatabaseReference mRefCliente =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("Zonas").child(ClienteRecyclerAdapter.myZona).child("zonaClientes");

    // Notification database reference
    private DatabaseReference mRefNotification =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("Notificacion");


    // Tempory Notification database reference
    private DatabaseReference mRefTmpNofication =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("NotificacionTmp");

    // Bitacora Registro datbase referece
    // Used for creating one of the events, that tracks the progress of the current Supervisor
    private DatabaseReference mBitacoraRegistroRef =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("BitacoraRegistro")
                    .child(new DatePost().getDateKey())
                    .child(ClienteRecyclerAdapter.mySupervisorKey);


    // Since Fragments need an empty constructor, if you wish to add parameters, you'll need to use newInstance

    public static GuardiaMoveDialogFragment newInstance(
            String clienteActual, String supervisor,String guardiaKey,String guardiaNombre){

        // Instanciate the Object
        GuardiaMoveDialogFragment fragment = new GuardiaMoveDialogFragment();

        // Create a new Bundle
        Bundle args = new Bundle();

        // Save the components for later use
        args.putString("supervisorNombre",supervisor);
        args.putString("clienteNombre",clienteActual);
        args.putString("guardiaKey",guardiaKey);
        args.putString("guardiaNombre",guardiaNombre);

        // Set the arguments
        fragment.setArguments(args);

        // Return Fragment
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Builds the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Mover Guardia");

        // Inflate the view(s)
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_fragment_guardia_move,null);
        spinner = (Spinner) view.findViewById(R.id.spinnerCliente);

        // Set the spinner with all the clients inside the database
        setSpinner();

        // Set the view inside the dialog
        builder.setView(view);

        // data is uploaded when the 'anadir' button is clicked.
        builder.setPositiveButton(R.string.mover_guardia, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Uploads the details handeled on the fragment
                uploadData();

                // Move the current Guard to the new Client
                moveGuardiaToNewClient();

            }
        });

        // Current Dialog gets removed when 'Cancelar' Button is clicked.
        builder.setNegativeButton(R.string.button_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    // TODO: Move Guardia first then eliminate him

    private void moveGuardiaToNewClient() {

        Log.v(TAG, "Current Guardia Key is " + getArguments().getString("guardiaKey"));


        // TODO Extract the object Info

        /*
          1.1 Extraer Informacion del Cliente
        * 1. Si el usuario fue movido dentro del contexto de un cliente se extrai
        * 2. Si el usuario fue movido dentro del fragmento de Guardias se extrai desde otro Base de datos
        * 3. Sabemos que si pasa null como cliente significa que no tiene cliente asignado
        * 4. Ya que extraemos el guardia y sus datos respectivmos procedemos a eliminarlos
        * */


        // No tiene Cliente Asignado
        if (getArguments().getString("clienteNombre")== null){

            mRef = mRef.child("guardias")
                    .child(getArguments().getString("guardiaKey"));

            mRef.addListenerForSingleValueEvent(new mRefListener());

        }else{// Cliente asignado

            mRef = mRef.child("Clientes")
                    .child(getArguments().getString("clienteNombre"))
                    .child("clienteGuardias")
                    .child(getArguments().getString("guardiaKey"));

            mRef.addListenerForSingleValueEvent(new mRefListener());
        }

    }

    private void uploadData(){

        // Creates an object which will store the values for later use
        GuardiaMoveBasicInfo guardiaMoveBasicInfo = new GuardiaMoveBasicInfo(
                        getArguments().getString("guardiaKey"), // Guardia Key
                        getArguments().getString("clienteNombre"), // Clientes Name
                        spinner.getSelectedItem().toString()); // Client selected on spinner


        // Creates and saves a random key for reference
        String key = mRefNotification.push().getKey();

        // Creates an object which will store the values for later use
        Notificacion notificacion = new Notificacion(
                // Action which describes notification
                NOTIFICATION_ACTION,
                // Description
                String.format("%s movio  %s a %s",
                        ClienteRecyclerAdapter.mySupervisor, // Gets the current Supervisor
                        getArguments().getString("guardiaNombre"), // Get the current Guardia
                        spinner.getSelectedItem().toString() // Gets the new client
                        ),
                new DatePost().getDatePost()); // gets the current Date

        // Notification Value set
        mRefNotification.child(key).setValue(notificacion);
        mRefNotification.child(key).child("informacion").setValue(guardiaMoveBasicInfo);

        // Tempory Notification value set
        mRefTmpNofication.child(key).setValue(notificacion);
        mRefTmpNofication.child(key).child("informacion").setValue(guardiaMoveBasicInfo);

        // TODO: Add FullTime key to Object

        // Bitacora Registro value set

        // Send to level 3

        // TODO: Move to no Resuelto

        String timeCompletetKey = new DatePost().getTimeCompletetKey();

        DatabaseReference mBitacoraRegistroNRRef =
                FirebaseDatabase.getInstance().getReference().child("Argus").child("BitacoraRegistroNoResuelto")
                        .child(ClienteRecyclerAdapter.mySupervisorKey);


        notificacion.setObservacionKey(timeCompletetKey);
        notificacion.setHora(new DatePost().get24HourFormat());

        mBitacoraRegistroNRRef.child(timeCompletetKey).setValue(notificacion);
        mBitacoraRegistroNRRef.child(timeCompletetKey).child("informacion").setValue(guardiaMoveBasicInfo);

        // Demonstrates that the data uploaded was succes.
        //Toast.makeText(getContext(), R.string.solicitud_enviada,Toast.LENGTH_LONG).show();


    }

    private void setSpinner(){

        // Value Event Listener used for collecting all the current clients inside datebase
        mRefCliente.addValueEventListener(new ValueEventListener() {

            // Creates an empty list of Clients
            final List<String> clientes = new ArrayList<String>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Iterates through all clientes from database
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    // Gets the property(s) of the client, in this case the clients name.
                    String Cliente = ds.getKey();

                    //  Makes sure the current client doesn't get included inside the list.
                    //if (!Cliente.equals(getArguments().getString("clienteNombre"))) {

                        // Adds the Client inside list of Clientes.
                        clientes.add(Cliente);

                    //}

                }

                // Creates a String Array in which collects all the clients
                ArrayAdapter<String> clientesAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,clientes);

                // Set the spinner adapter
                clientesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(clientesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Firebase Error
                Log.e(TAG, databaseError.toString());
                showFirebaseErrorToast(String.valueOf(R.string.GuardiaMoveDialog_Error_Loading_Clients));

            }
        });
    }

    private void showFirebaseErrorToast(String message){

        // Displays a toast which will notify the user there was a firebas network error
        Toast.makeText(
                GuardiaMoveDialogFragment.this.getContext(),
                message,
                Toast.LENGTH_LONG)
                .show();
    }

    private class mRefListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            // Get User Info
            currentGuardia = dataSnapshot.getValue(guardias.class);

            // If it doesn't have a client asisgned it doesn't get removed
            if (getArguments().getString("clienteNombre")!= null) {
                remove(dataSnapshot);
            }else{
                // Updates the current status to not available inside Current Guard and updates it
                currentGuardia.setUsuarioDisponible(false);
                mRef.setValue(currentGuardia);
            }

            add();

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    private void add(){

        DatabaseReference mClienteRef =
                FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Argus")
                .child("Clientes")
                .child(spinner.getSelectedItem().toString())// Replace with new cliente
                .child("clienteGuardias");

        mClienteRef.child(getArguments().getString("guardiaKey")).setValue(currentGuardia);


    }

    private void remove(DataSnapshot dataSnapshot){
        dataSnapshot.getRef().removeValue();
    }
}
