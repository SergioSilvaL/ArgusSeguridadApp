package com.example.legible.seguridadargusapp;

import com.example.legible.seguridadargusapp.Model.ObjectModel.supervisores;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sergiosilva on 3/21/17.
 */

public class FirebaseReference {

//
//    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//    //Get Firebase Reference
//    private static DatabaseReference mDatabaseReference =
//            FirebaseDatabase.getInstance().getReference()
//                    .child("Argus")
//                    .child("supervisores");
//
//
//    public static String getZonaReference(){
//
//        String zona = "";
//
//        mDatabaseReference.addListenerForSingleValueEvent(new ZonaReferenceEventListener());
//
//        return zona;
//    }
//
//    static class ZonaReferenceEventListener implements ValueEventListener{
//
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//                        for(DataSnapshot data: dataSnapshot.getChildren()){
//
//                supervisores supervisor = data.getValue(supervisores.class);
//
//                if(user.getEmail().equals(supervisor.getUsuarioEmail())){
//
//
//
//                    //Todo set supervisorNombre through Bundle
//
//
//                    supervisorNombreRef = supervisor.getUsuarioNombre();
//
//                    zonaSupervisorRef = supervisor.getUsuarioZona();
//
//                    //Create the Adapter
//                    mAdapter = new ClienteRecyclerAdapter(ClienteFragment.this.getContext(), recyclerView, zonaSupervisorRef,supervisorNombreRef);
//
//
//                    //Binding
//                    recyclerView.setAdapter(mAdapter);
//
//
//                    Log.v(TAG,zonaSupervisorRef);
//                    return;
//
//
//
//
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    }
//
//

}