package com.example.legible.seguridadargusapp.Model.ObjectModel;

/**
 * Created by sergiosilva on 3/9/17.
 */

public class Notificacion {

    public String accion="";
    public String observacion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    private String Hora;
    public String descripcion;
    public String fecha;
    public String referenceKey;
    public String fechaCodigo;
    private long semaforo;
    public String observacionKey;


    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    private String dateCreation;

    public String getObservacionKey() {
        return observacionKey;
    }

    public void setObservacionKey(String observacionKey) {
        this.observacionKey = observacionKey;
    }

    public boolean getSupervisorResponsibility() {
        return supervisorResponsibility;
    }

    public void setSupervisorResponsibility(boolean supervisorResponsibility) {
        this.supervisorResponsibility = supervisorResponsibility;
    }

    private boolean supervisorResponsibility;

    public long getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(long semaforo) {
        this.semaforo = semaforo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    private String cliente;

    public String getFechaCodigo() {
        return fechaCodigo;
    }

    public void setFechaCodigo(String fechaCodigo) {
        this.fechaCodigo = fechaCodigo;
    }

    public Notificacion(String accion, String descripcion, String fecha, String referenceKey) {
        this.accion = accion;
        this.observacion = descripcion;
        this.fecha = fecha;
        this.referenceKey = referenceKey;
    }

    public Notificacion(String accion, String descripcion, String fecha,String fechaCodigo,String referenceKey, String cliente){
        this.accion = accion;
        this.observacion = descripcion;
        this.fecha = fecha;
        this.fechaCodigo = fechaCodigo;
        this.referenceKey = referenceKey;
        this.cliente = cliente;
    }

    public Notificacion(String accion, String descripcion, String fecha) {
        this.accion = accion;
        this.observacion = descripcion;
        this.fecha = fecha;
        this.semaforo = 3;
        this.supervisorResponsibility = true;
        this.descripcion = observacion;

    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getReferenceKey() {
        return referenceKey;
    }

    public void setReferenceKey(String referenceKey) {
        this.referenceKey = referenceKey;
    }


}
