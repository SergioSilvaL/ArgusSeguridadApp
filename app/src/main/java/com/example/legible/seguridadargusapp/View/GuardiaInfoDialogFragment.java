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
    //Firebase Database reference for the current Guardia
    private DatabaseReference mDatabaseReference =
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Argus")
                    .child("guardias");

    public GuardiaInfoDialogFragment(){}

    public static GuardiaInfoDialogFragment newInstance(String guardiaNombreRef){

        GuardiaInfoDialogFragment frag = new GuardiaInfoDialogFragment();
        Bundle args = new Bundle();
        args.putString("currentGuardia",guardiaNombreRef);
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

        TextView nombre  = (TextView) view.findViewById(R.id.textViewUsuarioNombre);


        mDatabaseReference.addValueEventListener(new GuardiaReferenceEventListener());


            //Todo: Change this, bug it's a very bad practice due to the fact it depends on chance

            //Set Stuff up

            if (mGuardia!=null){
                nombre.setText(mGuardia.getUsuarioNombre());
            }





    }

    class GuardiaReferenceEventListener implements ValueEventListener{

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String guardiaNombreRef = getArguments().getString("currentGuardia","sinNombre");

            for (DataSnapshot data: dataSnapshot.getChildren()){

                guardias guardia = data.getValue(guardias.class);

                if (guardiaNombreRef.equals(guardia.getUsuarioNombre())){

                    mGuardia = guardia;
                    return;

                }


            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
