package com.example.legible.seguridadargusapp.Model.ObjectModel;

/**
 * Created by SERGIO on 25/02/2017.
 */

public class guardias {

    private boolean usuarioDisponible;
    private String usuarioDomicilio;
    private String usuarioNombre;
    private long usuarioTelefono;
    private String usuarioTipo;
    private String usuarioTurno;
    //Really? Zona are you even using that bro...
    private String usuarioZona;
    private String usuarioCliente;
    private String key;

    public guardias(){}

    public guardias(boolean usuarioDisponible,String usuarioDomicilio, String usuarioNombre, String usuarioTipo,long usuarioTelefono,String usuarioTurno, String usuarioZona) {
        this.usuarioDisponible = usuarioDisponible;
        this.usuarioDomicilio = usuarioDomicilio;
        this.usuarioNombre = usuarioNombre;
        this.usuarioTelefono = usuarioTelefono;
        this.usuarioTipo = usuarioTipo;
        this.usuarioTurno = usuarioTurno;
        this.usuarioZona = usuarioZona;
        this.key=key;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getUsuarioDomicilio() {
        return usuarioDomicilio;
    }

    public void setUsuarioDomicilio(String usuarioDomicilio) {
        this.usuarioDomicilio = usuarioDomicilio;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public long getUsuarioTelefono() {
        return usuarioTelefono;
    }

    public void setUsuarioTelefono(long usuarioTelefono) {
        this.usuarioTelefono = usuarioTelefono;
    }

    public String getUsuarioTipo() {
        return usuarioTipo;
    }

    public void setUsuarioTipo(String usuarioTipo) {
        this.usuarioTipo = usuarioTipo;
    }

    public String getUsuarioTurno() {
        return usuarioTurno;
    }

    public void setUsuarioTurno(String usuarioTurno) {
        this.usuarioTurno = usuarioTurno;
    }

    public String getUsuarioZona() {
        return usuarioZona;
    }

    public void setUsuarioZona(String usuarioZona) {
        this.usuarioZona = usuarioZona;
    }

    public boolean isUsuarioDisponible() {
        return usuarioDisponible;
    }

    public void setUsuarioDisponible(boolean usuarioDisponible) {
        this.usuarioDisponible = usuarioDisponible;
    }

    public String getUsuarioCliente() {
        return usuarioCliente;
    }

    public void setUsuarioCliente(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;
    }
}
