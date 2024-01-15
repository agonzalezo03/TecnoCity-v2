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
public class Categoria implements Serializable{
    private short idCategoria;
    private String nombre;
    private String imagen;

    public short getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(short idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    
    
}
