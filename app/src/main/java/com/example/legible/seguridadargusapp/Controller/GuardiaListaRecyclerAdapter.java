package com.example.legible.seguridadargusapp.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.Model.ObjectModel.DatePost;
import com.example.legible.seguridadargusapp.Model.ObjectModel.guardias;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.View.DialogFragment.GuardiaInfoDialogFragment;
import com.example.legible.seguridadargusapp.View.Activity.GuardiaListaActivity;
import com.example.legible.seguridadargusapp.View.DialogFragment.GuardiaMoveDialogFragment;
import com.example.legible.seguridadargusapp.View.Activity.GuardiaSignatureActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SERGIO on 25/02/2017.
 */

public class GuardiaListaRecyclerAdapter extends RecyclerView.Adapter<GuardiaListaRecyclerAdapter.ViewHolder>{

    //Quick Fix
    public static DatabaseReference ClienteGuardiasRef;

    //private List<guardias> mGuardiasList;
    public static List<guardias> mGuardiasList;
    private Context mContext;
    private DatabaseReference guardiaListaRef;
    private String clienteActual;
    private android.support.v4.app.FragmentManager fm;

    public static String myGuardiaCaptura;



    public static String isMyStatusAsistio;
    public static String isMyStatusCubreDescanso;
    public static String isMyStatusDobleTurno;
    public static String myHorasExtra;
    boolean asistio, cubreDescanso, dobleTurno;
    long horasExtra;



    public GuardiaListaRecyclerAdapter(Context context, String clienteActual, android.support.v4.app.FragmentManager fm){

        mGuardiasList =  new ArrayList<>();
        this.clienteActual = clienteActual;
        mContext = context;
        this.fm = fm;


        //Database Reference Setup

        guardiaListaRef = FirebaseDatabase.getInstance().getReference()
                .child("Argus")
                .child("Clientes")
                .child(clienteActual)
                .child("clienteGuardias");



        ClienteGuardiasRef = guardiaListaRef;

        guardiaListaRef.addChildEventListener(new GuardiaListChildEventListener());


    }

    class GuardiaListChildEventListener implements ChildEventListener{


        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            //TODO Change reference from GuardiaName to Guardia Key;


            guardias guardia = dataSnapshot.getValue(guardias.class);
            guardia.setUsuarioKey(dataSnapshot.getKey());


            boolean bandera = false;

            if (mGuardiasList.size()>0){

                for (guardias currentGuardia : mGuardiasList){

                    if (currentGuardia.getUsuarioNombre().equals(guardia.getUsuarioNombre())){
                        bandera = true;
                    }
                }
            }

            if (bandera == false){
                mGuardiasList.add(0,guardia);
            }

            notifyDataSetChanged();


        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {


//            guardias guardia = dataSnapshot.getValue(guardias.class);
//
//            if (guardia.getUsuarioAsistenciaDelDia().equals("No Asistió")&& guardia.equals("")){
//                if (guardia.getUsuarioNombre().equals(myGuardiaCaptura)){
//                    myStatusExtra="";
//                    myStatus="";
//                }
//            }

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

            guardias guardiaRemoved = dataSnapshot.getValue(guardias.class);

            int i = 0;
            int position = 0;
            final List<guardias> mGuardiasRemovalList = mGuardiasList;


            for (guardias currentGuardia : mGuardiasRemovalList){
                if (mGuardiasRemovalList.get(i).getUsuarioNombre().equals(guardiaRemoved.getUsuarioNombre())){
                    position = i;
                }
                i++;
            }

            mGuardiasList.remove(position);

            notifyItemRemoved(position);

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lista_guardia,parent,false);

        return new ViewHolder(view);
    }

    public static void updateGuardiaList(){

        mGuardiasList.clear();

        ClienteGuardiasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                guardias guardia = dataSnapshot.getValue(guardias.class);
                guardia.setUsuarioKey(dataSnapshot.getKey());
                mGuardiasList.add(0,guardia);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        asistio = Boolean.valueOf(isMyStatusAsistio);
        cubreDescanso = Boolean.valueOf(isMyStatusCubreDescanso);
        dobleTurno = Boolean.valueOf(isMyStatusDobleTurno);

        if (myHorasExtra==null){
            horasExtra = 0;
        }else {
            horasExtra = Long.valueOf(myHorasExtra);
        }

        Collections.sort(mGuardiasList, new CompareGuard());

        final guardias guardia = mGuardiasList.get(position);



        holder.nameTxt.setText(guardia.getUsuarioNombre());


            if (guardia.getUsuarioNombre().equals(myGuardiaCaptura) || (guardia.isUsuarioAsistio()||guardia.isUsuarioDobleTurno()||guardia.isUsuarioCubreTurno())) {


                int imageDoble = 0;


                /**
                 *
                 * CUBRE DESCANSO
                 *
                 * */

                imageDoble = 0;

                if ((guardia.getUsuarioNombre().equals(myGuardiaCaptura) && cubreDescanso)|| guardia.isUsuarioCubreTurno()){
                    imageDoble = android.R.drawable.presence_online;
                }

                if (guardia.getUsuarioAsistenciaFecha()!= null) {

                    if (!guardia.getUsuarioAsistenciaFecha().equals(new DatePost().getDate())) {
                        imageDoble = 0;
                    }
                }

                holder.asistenciaCubreDescansoView.setBackgroundResource(imageDoble);


                /**
                 *
                 * DOBLE TURNO
                 *
                 * */

                imageDoble = 0;

                if ((guardia.getUsuarioNombre().equals(myGuardiaCaptura) && dobleTurno)|| guardia.isUsuarioDobleTurno()){
                    imageDoble = android.R.drawable.presence_online;
                }

                if (guardia.getUsuarioAsistenciaFecha()!= null) {

                    if (!guardia.getUsuarioAsistenciaFecha().equals(new DatePost().getDate())) {
                        imageDoble = 0;
                    }
                }

                holder.asistenciaDobleTurno.setBackgroundResource(imageDoble);

                /**
                 *
                 * Horas Extra
                 *
                 * */

                imageDoble = 0;

                if ((guardia.getUsuarioNombre().equals(myGuardiaCaptura) && (horasExtra>0))|| guardia.getUsuarioHorasExtra()>0){
                    imageDoble = android.R.drawable.presence_online;
                }

                if (guardia.getUsuarioAsistenciaFecha()!= null) {

                    if (!guardia.getUsuarioAsistenciaFecha().equals(new DatePost().getDate())) {
                        imageDoble = 0;
                    }
                }

                //Todo Bind Hour View

                holder.asistenciaHorasExtra.setBackgroundResource(imageDoble);


                /**
                 *
                 * ASISTIO
                 *
                 * */

                imageDoble = 0;

                if ((guardia.getUsuarioNombre().equals(myGuardiaCaptura) && asistio)|| guardia.isUsuarioAsistio()){
                    imageDoble = android.R.drawable.presence_online;
                }

                if (guardia.getUsuarioAsistenciaFecha()!= null) {

                    if (!guardia.getUsuarioAsistenciaFecha().equals(new DatePost().getDate())) {
                        imageDoble = 0;
                    }
                }

                if ((guardia.getUsuarioNombre().equals(myGuardiaCaptura) && !asistio)|| !guardia.isUsuarioAsistio()){
                    imageDoble = android.R.drawable.presence_busy;

                    // set the rest of the views to empty
                    holder.asistenciaDobleTurno.setBackgroundResource(0);
                    holder.asistenciaCubreDescansoView.setBackgroundResource(0);
                }

                holder.asistenciaView.setBackgroundResource(imageDoble);


            }


        holder.viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, GuardiaSignatureActivity.class);
                intent.putExtra("guardiaKey",guardia.getUsuarioKey());
                intent.putExtra("guardiaNombre",guardia.getUsuarioNombre());
                intent.putExtra("guardiaAsistenciaStatus",false);

                ((Activity) mContext).startActivityForResult(intent,GuardiaListaActivity.REQUEST_ASISTENCIA);

            }
        });

        holder.optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGuardiaOptionsMenu(guardia.getUsuarioKey(),guardia.getUsuarioNombre());
            }
        });
    }



    public void showGuardiaOptionsMenu(final String key,final String guardiaNombre){

        final String options[] = {"Detalles", "Mover"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("Opciones");
        dialog.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0 :
                        // Detallles;
                        showGuardiaInfoDialogFragment(key);
                        break;

                    case 1 :
                        //Mover
                        showMoveGuardiaDialogFragment(key,guardiaNombre);

                        break;
                    default:
                        dialog.dismiss();
                }
            }
        });

        dialog.show();

    }

    public void showGuardiaInfoDialogFragment(String guardiaRef){

        GuardiaInfoDialogFragment df = GuardiaInfoDialogFragment.newInstance(guardiaRef);
        df.show(fm,"fragment_guardia_info");
    }

    public void showMoveGuardiaDialogFragment(String key,String guardiaNombre){
        GuardiaMoveDialogFragment df = GuardiaMoveDialogFragment.newInstance(clienteActual,ClienteRecyclerAdapter.mySupervisor,key,guardiaNombre);
        df.show(fm,"fragment_guardia_move");
    }

    @Override
    public int getItemCount() {
        return mGuardiasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;
        ImageView optionMenu, asistenciaView, asistenciaCubreDescansoView,asistenciaDobleTurno,asistenciaHorasExtra;
        ViewGroup viewGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTxt = (TextView) itemView.findViewById(R.id.nameTxt);
            optionMenu = (ImageView) itemView.findViewById(R.id.imageViewOption);
            viewGroup = (ViewGroup) itemView.findViewById(R.id.cardview_image);
            asistenciaView = (ImageView) itemView.findViewById(R.id.asistenciaView);
            asistenciaDobleTurno = (ImageView) itemView.findViewById(R.id.asistenciaDobleTurno);
            asistenciaCubreDescansoView = (ImageView) itemView.findViewById(R.id.asistenciaCubreDescansoView);
            asistenciaHorasExtra = (ImageView) itemView.findViewById(R.id.asistenciaHorasExtra);

        }
    }


}
