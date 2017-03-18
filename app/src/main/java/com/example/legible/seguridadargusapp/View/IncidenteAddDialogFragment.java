package com.example.legible.seguridadargusapp.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.legible.seguridadargusapp.Model.ObjectModel.DatePost;
import com.example.legible.seguridadargusapp.Model.ObjectModel.Incidente;
import com.example.legible.seguridadargusapp.Model.ObjectModel.Notificacion;
import com.example.legible.seguridadargusapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiosilva on 3/10/17.
 *
 */

public class IncidenteAddDialogFragment extends DialogFragment {

    private EditText editTextIncidenteObservacion;
    private Spinner spinner;
    private List<Incidente> incidenteList;
    //private Spinner spinnerIncidenteTipo;
    private DatabaseReference mrefIncidenteTipo =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("IncidenteTipo");

    private DatabaseReference mPushRef =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("Incidente");

    private DatabaseReference mNotificationRef =
            FirebaseDatabase.getInstance().getReference().child("Argus").child("Notificacion");



    public static IncidenteAddDialogFragment newInstance(String cliente, String supervisor){

        IncidenteAddDialogFragment fragment = new IncidenteAddDialogFragment();

        Bundle args = new Bundle();
        args.putString("supervisorNombre",supervisor);
        args.putString("clienteNombre",cliente);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Agregar un nuevo Incidente");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_fragment_incidente_add,null);

        // inflate the componentes
        editTextIncidenteObservacion = (EditText) view.findViewById(R.id.editTextIncidenteObservacion);
        mrefIncidenteTipo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> incidentes = new ArrayList<String>();

                for (DataSnapshot ds : dataSnapshot.getChildren()){

//                    //Get the object
//                    Incidente currentInciente = ds.getValue(Incidente.class);
//                    incidenteList.add(0,currentInciente);


                    String incidenteTipo = ds.child("incidenteTipo").getValue(String.class);
                    incidentes.add(incidenteTipo);

                }

                spinner = (Spinner) view.findViewById(R.id.spinnerIncidenteTipo);
                ArrayAdapter<String> incidentesAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,incidentes);
                incidentesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(incidentesAdapter);


                //String value = spinnner.getSelectedItem().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        builder.setView(view);

        builder.setPositiveButton("anadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Push Data
                pushData();
            }
        });

        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    public void pushData(){



        String incidenteTipo = spinner.getSelectedItem().toString();
        //Todo set incidenteTipo
        String incidenteGravedad="";
        // get the current incidente Gravedad

//        for (int i =0; i< incidenteList.size(); i++){
//
//            if (incidenteTipo.equals(incidenteList.get(i).getIncidenteTipo())){
//                incidenteGravedad = incidenteList.get(i).getIncidenteGravedad();
//            }
//        }

        String incidenteCliente = getArguments().getString("clienteNombre");

        DatePost datePost = new DatePost();
        String incidenteFecha = datePost.getDatePost();

        String incidenteObservacion = editTextIncidenteObservacion.getText().toString();

        //Todo set incidenteUsuario
        String incidenteUsuario = "supervisor";


        //set the current onject

        Incidente incidente = new Incidente(incidenteCliente,incidenteFecha,incidenteObservacion,incidenteTipo,incidenteUsuario,incidenteGravedad);

        //push in a new Incidente
        mPushRef.push().setValue(incidente);


        //push to notifiacion
        String descripcion="Hubo un "+ incidenteTipo + " en "+ incidenteCliente;
        Notificacion notificacion = new Notificacion("AI",descripcion, incidenteFecha );
        mNotificationRef.push().setValue(notificacion);




    }
}
