/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.tecnocity.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Clases
 */
public class Pedido implements Serializable{
    private short idPedido;
    private Date fecha;
    private String estado;
    private short idUsuario;
    private float importe;
    private float iva;
    private ArrayList<LineaPedido> lineasPedido;

    public short getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(short idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public short getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(short idUsuario) {
        this.idUsuario = idUsuario;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public ArrayList<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(ArrayList<LineaPedido> LineasPedido) {
        this.lineasPedido = LineasPedido;
    }
    
    
    
    
    
}
