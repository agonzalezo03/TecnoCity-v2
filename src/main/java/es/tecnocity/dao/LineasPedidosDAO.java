/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.tecnocity.dao;

import es.tecnocity.beans.LineaPedido;
import es.tecnocity.beans.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Clases
 */
public class LineasPedidosDAO implements ILineasPedidosDAO{

    /**
     * Añade una linea de pedido
     * @param idPedido
     * @param lineaPedido 
     */
    @Override
    public void addLinea(short idPedido, LineaPedido lineaPedido) {
        String sql = "INSERT INTO tecnocity.lineaspedidos ( IdPedido, IdProducto, Cantidad) VALUES ( ?, ?, ?);";
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        Producto producto = lineaPedido.getProducto();
        try(PreparedStatement preparada = conexion.prepareStatement(sql);){
            conexion.setAutoCommit(false);
            preparada.setShort(1, idPedido);
            preparada.setShort(2, producto.getIdProducto());
            preparada.setShort(3, lineaPedido.getCantidad());

            preparada.executeUpdate();
            conexion.commit();
        }catch(SQLException ex){
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            this.closeConnection();
        }
    }
    
    /**
     * Obtiene las lineas de pedido de un idPedido concreto
     * @param s
     * @return 
     */
    @Override
    public ArrayList<LineaPedido> getLineasPedidos(short s) {
        String sql = "SELECT * FROM tecnocity.lineaspedidos WHERE IdPedido = ?";
        LineaPedido lineaPedido = null;
        Producto producto = null;
        ArrayList<LineaPedido> pedidoC = new ArrayList();
        try (Connection conexion = ConnectionFactory.getConnection(); PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setShort(1, s);

            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado.next()) {
                lineaPedido = new LineaPedido();
                producto = new Producto();
                lineaPedido.setIdLinea(resultado.getShort("IdLinea"));
                lineaPedido.setIdPedido(resultado.getShort("IdPedido"));
                producto.setIdProducto(resultado.getShort("IdProducto"));
                lineaPedido.setProducto(producto);
                lineaPedido.setCantidad(resultado.getShort("Cantidad"));
                
                pedidoC.add(lineaPedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            this.closeConnection();
        }
        return pedidoC;
    }
    
    /**
     * Compueba si ya existe un producto en el pedido
     * @param id
     * @param producto
     * @return 
     */
    @Override
    public boolean comprobarLinea(short id, Producto producto) {
        String sql = "SELECT * FROM tecnocity.lineaspedidos WHERE IdPedido = ? AND IdProducto = ?";
        boolean existe = false;
        try (Connection conexion = ConnectionFactory.getConnection(); PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setShort(1, id);
            preparedStatement.setShort(2, producto.getIdProducto());

            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado.next()) {
                existe = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            this.closeConnection();
        }
        return existe;
    }
    
    /**
     * Cambia la cantidad de una lina de pedido
     * @param id
     * @param producto 
     */
    @Override
    public void setCantidad(short id, LineaPedido lineaPedido) {
        
        String sql = "UPDATE `tecnocity`.`lineaspedidos` SET `Cantidad` = ? WHERE IdPedido = ? AND IdProducto = ?";

        Producto producto = lineaPedido.getProducto();
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);){
            conexion.setAutoCommit(false);
            preparedStatement.setShort(1, lineaPedido.getCantidad());
            preparedStatement.setShort(2, id);
            preparedStatement.setShort(3, producto.getIdProducto());

            preparedStatement.executeUpdate();
            conexion.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } finally {
            this.closeConnection();
        }
    }
    
    
    /**
     * Elimina una lina pedido
     * @param idPedido
     * @param producto 
     */
    @Override
    public void delLinea(short idPedido, Producto producto) {
        String sql = "DELETE FROM `tecnocity`.`lineaspedidos` WHERE IdPedido = ? AND IdProducto = ?";
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);){
            conexion.setAutoCommit(false);
            preparedStatement.setShort(1, idPedido);
            preparedStatement.setShort(2, producto.getIdProducto());

            preparedStatement.executeUpdate();
            conexion.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } finally {
            this.closeConnection();
        }
    }
    
    /**
     * Elimina las lineas de un pedido
     * @param idPedido 
     */
    @Override
    public void delLineas(short idPedido) {
        String sql = "DELETE FROM `tecnocity`.`lineaspedidos` WHERE IdPedido = ? ";
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);){
            conexion.setAutoCommit(false);
            preparedStatement.setShort(1, idPedido);

            preparedStatement.executeUpdate();
            conexion.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } finally {
            this.closeConnection();
        }
    }
    
    @Override
    public void closeConnection() {
        ConnectionFactory.closeConexion();
    }

    

    

    

    

    


}
