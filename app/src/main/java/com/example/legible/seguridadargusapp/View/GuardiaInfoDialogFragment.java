package com.example.legible.seguridadargusapp.View;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.Model.ObjectModel.guardias;
import com.example.legible.seguridadargusapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sergiosilva on 3/7/17.
 */

public class GuardiaInfoDialogFragment extends DialogFragment{

    private guardias mGuardia;
    private TextView disponible;
    private TextView domicillio;
    private TextView nombre;
    private TextView telefono;
    private TextView tipo;
    private TextView turno;

    //Firebase Database reference for the current Guardia
    private DatabaseReference mDatabaseReference =
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Argus")
                    .child("guardias");

    public GuardiaInfoDialogFragment(){}

    public static GuardiaInfoDialogFragment newInstance(String guardiaKeyRef){

        GuardiaInfoDialogFragment frag = new GuardiaInfoDialogFragment();
        Bundle args = new Bundle();
        args.putString("currentGuardia",guardiaKeyRef);
        frag.setArguments(args);

        return frag;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment_guardia_info,container,false);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        disponible = (TextView) view.findViewById(R.id.textViewUsuarioDisponible);
        domicillio = (TextView) view.findViewById(R.id.textViewUsuarioDomicilio);
        nombre  = (TextView) view.findViewById(R.id.textViewUsuarioNombre);
        telefono = (TextView) view.findViewById(R.id.textViewUsuarioTelefono);
        tipo = (TextView) view.findViewById(R.id.textViewUsuarioTipo);
        turno = (TextView) view.findViewById(R.id.textViewUsuarioTurno);

        DatabaseReference ref = mDatabaseReference.child(getArguments().getString("currentGuardia"));

        ref.addValueEventListener(new GuardiaReferenceEventListener());

    }



    class GuardiaReferenceEventListener implements ValueEventListener{

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {


            mGuardia  = dataSnapshot.getValue(guardias.class);
            disponible.setText(String.valueOf(mGuardia.isUsuarioDisponible()));
            domicillio.setText(mGuardia.getUsuarioDomicilio());
            nombre.setText(mGuardia.getUsuarioNombre());
            telefono.setText(String.valueOf(mGuardia.getUsuarioTelefono()));
            tipo.setText(mGuardia.getUsuarioTipo());
            turno.setText(mGuardia.getUsuarioTurno());


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }


}
