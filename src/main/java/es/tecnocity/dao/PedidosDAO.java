/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.tecnocity.dao;

import es.tecnocity.beans.Pedido;
import es.tecnocity.beans.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Clases
 */
public class PedidosDAO implements IPedidosDAO {

    /**
     * Añade un pedido
     * @param usr 
     */
    @Override
    public void addPedido(Usuario usr) {
        String sql = "INSERT INTO `tecnocity`.`pedidos` (IdPedido, IdUsuario) VALUES (LAST_INSERT_ID(), ?);";
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        try (PreparedStatement preparada = conexion.prepareStatement(sql);) {
            conexion.setAutoCommit(false);
            preparada.setShort(1, usr.getIdUsuario());

            preparada.executeUpdate();
            conexion.commit();
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
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
     * Obtiene el idPedido del carrito de un usuario
     * @param usr
     * @return 
     */
    @Override
    public short getIdPedido(Usuario usr) {
        String sql = "SELECT * FROM `tecnocity`.`pedidos` WHERE IdUsuario = ? AND Estado = ?";
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        short idPedido = 0;
        try (PreparedStatement preparada = conexion.prepareStatement(sql);) {
            preparada.setShort(1, usr.getIdUsuario());
            preparada.setString(2, "c");

            ResultSet resultado = preparada.executeQuery();
            while (resultado.next()) {
                idPedido = resultado.getShort("IdPedido");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.closeConnection();
        }

        return idPedido;
    }

    /**
     * Elimina un pedido
     * @param idPedido 
     */
    @Override
    public void delPedido(short idPedido) {
        String sql = "DELETE FROM `tecnocity`.`pedidos` WHERE IdPedido = ? ";
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);) {
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

    /**
     * Cambia el estado de un pedido a finalizado
     * @param idPedido 
     */
    @Override
    public void chageEstado(short idPedido) {
        String sql = "UPDATE `tecnocity`.`pedidos` SET `Estado` = 'f', Fecha = ? WHERE IdPedido = ? ";
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);) {
            conexion.setAutoCommit(false);
            Date fechaActual = new Date();
            Timestamp timestamp = new Timestamp(fechaActual.getTime());

            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setShort(2, idPedido);

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
     * Da valor al importe y al iva de un pedido
     * @param pedido 
     */
    @Override
    public void setImporeIva(Pedido pedido) {
        String sql = "UPDATE `tecnocity`.`pedidos` SET Importe = ?, Iva = ? WHERE IdPedido = ? ";
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql);) {
            conexion.setAutoCommit(false);
            preparedStatement.setFloat(1, pedido.getImporte());
            preparedStatement.setFloat(2, pedido.getIva());
            preparedStatement.setShort(3, pedido.getIdPedido());

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
     * Obtiene los pedidos finalizados
     * @param usuario
     * @return 
     */
    @Override
    public ArrayList<Pedido> getPedidos(Usuario usuario) {
        String sql = "SELECT * FROM `tecnocity`.`pedidos` WHERE IdUsuario = ? AND Estado = ? ORDER BY Fecha DESC";
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        ArrayList<Pedido> pedidos = new ArrayList();
        try (PreparedStatement preparada = conexion.prepareStatement(sql);) {
            preparada.setShort(1, usuario.getIdUsuario());
            preparada.setString(2, "f");

            ResultSet resultado = preparada.executeQuery();
            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(resultado.getShort("IdPedido"));
                pedido.setFecha(resultado.getDate("fecha"));
                pedido.setIdUsuario(resultado.getShort("Idusuario"));
                pedido.setImporte(resultado.getFloat("Importe"));
                pedido.setIva(resultado.getFloat("Iva"));
                pedidos.add(pedido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            this.closeConnection();
        }
        return pedidos;
    }

    @Override
    public void closeConnection() {
        ConnectionFactory.closeConexion();
    }

    

}
