package com.example.legible.seguridadargusapp;

import com.google.firebase.database.Exclude;

/**
 * Created by SERGIO on 24/02/2017.
 */

public class supervisores {

    private String usuarioContrasena;
    private String usuarioDomicilio;
    private String usuarioEmail;
    private String usuarioNombre;
    private String usuarioTipo;
    private String usuarioTurno;
    private String usuarioZona;
    private String key;

    public supervisores(){}

    public supervisores(String usuarioContrasena, String usuarioDomicilio, String usuarioEmail, String usuarioNombre, String usuarioTipo, String usuarioTurno, String usuarioZona) {
        this.usuarioContrasena = usuarioContrasena;
        this.usuarioDomicilio = usuarioDomicilio;
        this.usuarioEmail = usuarioEmail;
        this.usuarioNombre = usuarioNombre;
        this.usuarioTipo = usuarioTipo;
        this.usuarioTurno = usuarioTurno;
        this.usuarioZona = usuarioZona;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsuarioContrasena() {
        return usuarioContrasena;
    }

    public void setUsuarioContrasena(String usuarioContrasena) {
        this.usuarioContrasena = usuarioContrasena;
    }

    public String getUsuarioDomicilio() {
        return usuarioDomicilio;
    }

    public void setUsuarioDomicilio(String usuarioDomicilio) {
        this.usuarioDomicilio = usuarioDomicilio;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
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
