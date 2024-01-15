/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.tecnocity.dao;

import es.tecnocity.beans.Producto;
import es.tecnocity.beans.Usuario;

import java.util.List;

public interface IProductosDAO {
    List<Producto> getProductos();
    List<Producto> getProductosPorCategoria(String categorias);
    Producto getProducto(String id);
    void closeConnection();
}

