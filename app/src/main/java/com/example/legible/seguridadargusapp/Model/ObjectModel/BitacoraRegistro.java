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
    private String dateCreation;
    private String dateCreationKey; // Todo see when creation key really needes to be set

    public BitacoraRegistro(){}

    public BitacoraRegistro(String observacion, long semaforo, String supervisor, String zona, String dateCreation) {
        this.observacion = observacion;
        this.semaforo = semaforo;
        this.supervisor = supervisor;
        this.zona = zona;
        this.dateCreation = dateCreation;
        dateCreationKey = new DatePost().getDateKey();
    }

    public BitacoraRegistro(String observacion, long semaforo, String supervisor, String zona, String hora, String dateCreation) {
        this.observacion = observacion;
        this.semaforo = semaforo;
        this.supervisor = supervisor;
        this.zona = zona;
        this.hora = hora;
        this.dateCreation = dateCreation;
        dateCreationKey = new DatePost().getDateKey();
    }

    public void setValues(BitacoraRegistro updatedBitacoraRegistro){
        observacion = updatedBitacoraRegistro.observacion;
        semaforo = updatedBitacoraRegistro.semaforo;
        supervisor = updatedBitacoraRegistro.supervisor;
        zona = updatedBitacoraRegistro.zona;
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

    public String getDateCreationKey() {
        return dateCreationKey;
    }

    public void setDateCreationKey(String dateCreationKey) {
        this.dateCreationKey = dateCreationKey;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
}
