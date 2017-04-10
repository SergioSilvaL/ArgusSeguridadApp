package com.example.legible.seguridadargusapp.Model.ObjectModel;

/**
 * Created by sergiosilva on 3/11/17.
 */

public class BitacoraGuardia  {

    private String status;
    private String firma;
    private String Observacion;
    private String firmExtra;
    private String cliente;
    private String zona;
    private String turno;
    private String guardiaNombre;

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public boolean isDobleTurno() {
        return dobleTurno;
    }

    public void setDobleTurno(boolean dobleTurno) {
        this.dobleTurno = dobleTurno;
    }

    public boolean isCubreDescanso() {
        return cubreDescanso;
    }

    public void setCubreDescanso(boolean cubreDescanso) {
        this.cubreDescanso = cubreDescanso;
    }

    public String getFirmaExtra() {
        return firmaExtra;
    }

    public void setFirmaExtra(String firmaExtra) {
        this.firmaExtra = firmaExtra;
    }

    private boolean asistio = false;
    private boolean dobleTurno = false;
    private boolean cubreDescanso = false;
    private String firmaExtra;


    public String getFecha() {
        return fecha;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;

    }

    private String fecha;

    public BitacoraGuardia(){}

    public BitacoraGuardia(boolean asisitio, boolean dobleTurno, boolean cubreDescanso, String firmaExtra,String firma, String observacion, String cliente, String zona,String turno, String guardiaNombre,String fecha) {
        this.asistio = asisitio;
        this.dobleTurno = dobleTurno;
        this.cubreDescanso = cubreDescanso;
        this.firmaExtra = firmaExtra;
        this.firma = firma;
        Observacion = observacion;
        this.cliente = cliente;
        this.zona = zona;
        this.turno = turno;
        this.guardiaNombre = guardiaNombre;
        this.fecha = fecha;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }

    public String getFirmExtra() {
        return firmExtra;
    }

    public void setFirmExtra(String firmExtra) {
        this.firmExtra = firmExtra;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getGuardiaNombre() {
        return guardiaNombre;
    }

    public void setGuardiaNombre(String guardiaNombre) {
        this.guardiaNombre = guardiaNombre;
    }
}
