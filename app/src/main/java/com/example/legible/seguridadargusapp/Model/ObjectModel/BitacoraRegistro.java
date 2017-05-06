package com.example.legible.seguridadargusapp.Model.ObjectModel;

import com.google.firebase.database.Exclude;

/**
 * Created by sergiosilva on 5/1/17.
 */

public class BitacoraRegistro {
    private String hora;
    private String observacion;
    private long semaforo;
    private String key;
    private String supervisor;
    private String zona;

    public BitacoraRegistro(){}

    public BitacoraRegistro(String observacion, long semaforo, String supervisor, String zona) {
        this.observacion = observacion;
        this.semaforo = semaforo;
        this.supervisor = supervisor;
        this.zona = zona;
    }



    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public long getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(long semaforo) {
        this.semaforo = semaforo;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}
