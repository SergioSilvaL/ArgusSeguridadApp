package com.example.legible.seguridadargusapp.ObjectModel;

/**
 * Created by SERGIO on 20/02/2017.
 */

public class Cliente {


    private String clienteNombre;


    Cliente(){}


    public Cliente(String cliente_Nombre){
        this.clienteNombre = cliente_Nombre;
    }

    public void setClienteNombre(String clienteNombre){
        this.clienteNombre = clienteNombre;
    }
    public String getClienteNombre(){
        return clienteNombre;
    }

}
