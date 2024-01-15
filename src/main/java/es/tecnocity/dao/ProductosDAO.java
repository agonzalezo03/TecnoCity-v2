/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.tecnocity.dao;

import es.tecnocity.beans.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Clases
 */
public class ProductosDAO implements IProductosDAO {

    /**
     * Obtiene todos los productos
     * @return 
     */
    @Override
    public List<Producto> getProductos() {
        List<Producto> lista = null;
        String consulta = "SELECT * FROM tecnocity.productos";
        try {
            Statement sentencia = ConnectionFactory.getConnection().createStatement();
            ResultSet resultado = sentencia.executeQuery(consulta);
            lista = new ArrayList();
            while (resultado.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(resultado.getShort("IdProducto"));
                producto.setIdCategoria(resultado.getByte("IdCategoria"));
                producto.setNombre(resultado.getString("Nombre"));
                producto.setDescripcion(resultado.getString("Descripcion"));
                producto.setPrecio(resultado.getFloat("Precio"));
                producto.setMarca(resultado.getString("Marca"));

                byte[] bytesImagen = resultado.getBytes("Imagen");
                producto.setImagen(Base64.getEncoder().encodeToString(bytesImagen));
                byte[] decodeBytes = Base64.getDecoder().decode(producto.getImagen());
                String decodeS = new String(decodeBytes);
                producto.setImagen(decodeS);

                lista.add(producto);
            }
            resultado.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeConnection();
        }
        return lista;
    }

    /**
     * Obtiene un producto
     * @param id
     * @return 
     */
    @Override
    public Producto getProducto(String id) {
        String sql = "SELECT * FROM tecnocity.productos WHERE IdProducto = ?";
        Producto producto = new Producto();
        try (Connection conexion = ConnectionFactory.getConnection(); PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, id);

            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado.next()) {
                
                producto.setIdProducto(resultado.getShort("IdProducto"));
                producto.setIdCategoria(resultado.getByte("IdCategoria"));
                producto.setNombre(resultado.getString("Nombre"));
                producto.setDescripcion(resultado.getString("Descripcion"));
                producto.setPrecio(resultado.getFloat("Precio"));
                producto.setMarca(resultado.getString("Marca"));

                byte[] bytesImagen = resultado.getBytes("Imagen");
                producto.setImagen(Base64.getEncoder().encodeToString(bytesImagen));
                byte[] decodeBytes = Base64.getDecoder().decode(producto.getImagen());
                String decodeS = new String(decodeBytes);
                producto.setImagen(decodeS);

            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            this.closeConnection();
        }
        return producto;
    }
    
    /**
     * Obtiene un listado de productos de una o varias categorias
     * @param categorias
     * @return 
     */
    @Override
    public List<Producto> getProductosPorCategoria(String categorias) {
        String sql = "SELECT * FROM tecnocity.productos WHERE IdCategoria IN (" + categorias + ")";
        List<Producto> productos = null;
        
        try (Connection conexion = ConnectionFactory.getConnection(); PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            ResultSet resultado = preparedStatement.executeQuery();
            productos = new ArrayList();
            while (resultado.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(resultado.getShort("IdProducto"));
                producto.setIdCategoria(resultado.getByte("IdCategoria"));
                producto.setNombre(resultado.getString("Nombre"));
                producto.setDescripcion(resultado.getString("Descripcion"));
                producto.setPrecio(resultado.getFloat("Precio"));
                producto.setMarca(resultado.getString("Marca"));

                byte[] bytesImagen = resultado.getBytes("Imagen");
                producto.setImagen(Base64.getEncoder().encodeToString(bytesImagen));
                byte[] decodeBytes = Base64.getDecoder().decode(producto.getImagen());
                String decodeS = new String(decodeBytes);
                producto.setImagen(decodeS);
                
                productos.add(producto);
                
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            this.closeConnection();
        }
        return productos;
    }

    @Override
    public void closeConnection() {
        ConnectionFactory.closeConexion();
    }

    

  

}
