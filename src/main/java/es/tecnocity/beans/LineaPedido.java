/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.tecnocity.beans;

import java.io.Serializable;

/**
 *
 * @author Clases
 */
public class LineaPedido implements Serializable{
    private short idLinea;
    private short idPedido;
    private Producto producto;
    private short cantidad;

    public short getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(short idLinea) {
        this.idLinea = idLinea;
    }

    public short getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(short idPedido) {
        this.idPedido = idPedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

   

    public short getCantidad() {
        return cantidad;
    }

    public void setCantidad(short cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
