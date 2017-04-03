package com.example.legible.seguridadargusapp.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.Controller.ClienteRecyclerAdapter;
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

    private Spinner spinner;

    private DatabaseReference mRefCliente =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("Clientes");

    private DatabaseReference mRefNotification =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("Notificacion");

    public static GuardiaMoveDialogFragment newInstance(String cliente,String supervisor,String guardiaKey,String guardiaNombre){
        GuardiaMoveDialogFragment fragment = new GuardiaMoveDialogFragment();

        Bundle args = new Bundle();
        args.putString("supervisorNombre",supervisor);
        args.putString("clienteNombre",cliente);
        args.putString("guardiaKey",guardiaKey);
        args.putString("guardiaNombre",guardiaNombre);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Mover Guardia");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_fragment_guardia_move,null);

        mRefCliente.addValueEventListener(new ValueEventListener() {
            final List<String> clientes = new ArrayList<String>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String Cliente = ds.child("clienteNombre").getValue(String.class);
                    if (!Cliente.equals(ClienteRecyclerAdapter.myCliente))
                    clientes.add(Cliente);

                }

                spinner = (Spinner) view.findViewById(R.id.spinnerCliente);
                ArrayAdapter<String> clientesAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,clientes);
                clientesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(clientesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        builder.setView(view);

        builder.setPositiveButton("Anadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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


        GuardiaMoveBasicInfo guardia = new GuardiaMoveBasicInfo();
        guardia.setGuardiaKey(getArguments().getString("guardiaKey"));
        guardia.setGuardiaClienteActual(getArguments().getString("clienteNombre"));
        guardia.setGuardiaClienteNuevo(spinner.getSelectedItem().toString());

        //Get Action
        String accion = "MG";
        //Get Current Date
        DatePost date = new DatePost();
        String currentDate = date.getDatePost();

        //GetCurrent Supervisor
        //Todo set the correct SuperVisor
        String supervisor = getArguments().getString("supervisorNombre");

        //setDescription
        //Todo set the guardia that was moved
        String guardiaNombre = getArguments().getString("guardiaNombre");
        String description = supervisor + " movio a "+ guardiaNombre+"(Guardia)";

        Notificacion notificacion = new Notificacion(accion,description,currentDate);



        String key = mRefNotification.push().getKey();

        mRefNotification.child(key).setValue(notificacion);
        mRefNotification.child(key).child("informacion").setValue(guardia);

        Toast.makeText(getContext(), "Su solicitud se ha enviado con exito",Toast.LENGTH_LONG).show();





    }
}
