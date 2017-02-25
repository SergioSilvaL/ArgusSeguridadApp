package com.example.legible.seguridadargusapp;

/**
 * Created by SERGIO on 25/02/2017.
 */

public class guardias {

    private String usuarioDisponible;
    private String usuarioDomicilio;
    private String usuarioNombre;
    private String usuarioTelefono;
    private String usuarioTipo;
    private String usuarioTurno;
    private String usuarioZona;

    public guardias(){}

    public guardias(String usuarioDisponible, String usuarioDomicilio, String usuarioNombre, String usuarioTelefono, String usuarioTipo, String usuarioTurno, String usuarioZona) {
        this.usuarioDisponible = usuarioDisponible;
        this.usuarioDomicilio = usuarioDomicilio;
        this.usuarioNombre = usuarioNombre;
        this.usuarioTelefono = usuarioTelefono;
        this.usuarioTipo = usuarioTipo;
        this.usuarioTurno = usuarioTurno;
        this.usuarioZona = usuarioZona;
    }

    public String getUsuarioDisponible() {
        return usuarioDisponible;
    }

    public void setUsuarioDisponible(String usuarioDisponible) {
        this.usuarioDisponible = usuarioDisponible;
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

    public String getUsuarioTelefono() {
        return usuarioTelefono;
    }

    public void setUsuarioTelefono(String usuarioTelefono) {
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
}
