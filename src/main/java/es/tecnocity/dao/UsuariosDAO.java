/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.tecnocity.dao;

import es.tecnocity.beans.Usuario;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Clases
 */
public class UsuariosDAO implements IUsuariosDAO{

    /**
     * Añade un usuario
     * @param usuario
     * @return 
     */
    @Override
    public boolean addUsuario(Usuario usuario) {    
        String sql = "INSERT INTO `tecnocity`.`usuarios` (idUsuario, Email, Password, Nombre, Apellidos, NIF, Telefono, Direccion, CodigoPostal, "
                + "Localidad, Provincia, Avatar) VALUES (LAST_INSERT_ID(), ?, MD5(?), ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        boolean exito = false;
        try(PreparedStatement preparada = conexion.prepareStatement(sql);){
            conexion.setAutoCommit(false);

            preparada.setString(1, usuario.getEmail());
            preparada.setString(2, usuario.getPassword());
            preparada.setString(3, usuario.getNombre());
            preparada.setString(4, usuario.getApellidos());
            preparada.setString(5, usuario.getNif());
            preparada.setString(6, usuario.getTelefono());
            preparada.setString(7, usuario.getDireccion());
            preparada.setString(8, usuario.getCodigoPostal());
            preparada.setString(9, usuario.getLocalidad());
            preparada.setString(10, usuario.getProvincia());
            preparada.setString(11, usuario.getAvatar());
            
            preparada.executeUpdate();
            conexion.commit();
            exito = true;
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
        

        return exito;
    }

    /**
     * Obtiene todos los correos
     * @return 
     */
    @Override
    public List<Usuario> getCorreos() {
        List<Usuario> lista = null;
        String consulta = "SELECT Email FROM tecnocity.usuarios";
        Statement sentencia;
        try {
            sentencia = ConnectionFactory.getConnection().createStatement();
            ResultSet resultado = sentencia.executeQuery(consulta);
            lista = new ArrayList();
            while(resultado.next()){
                Usuario usuario = new Usuario();
                usuario.setEmail(resultado.getString("Email"));
                lista.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.closeConnection();
        }
            
        return lista;
    }
    
    /**
     * Comprueba si existe un usuario con un correo y password especificos
     * @param usr
     * @return 
     */
    @Override
    public Usuario login(Usuario usr) {
        Usuario usuario = null;
        String sql = "SELECT * FROM tecnocity.usuarios WHERE Email = ? AND Password = MD5(?)";
        try (Connection conexion = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, usr.getEmail());
            preparedStatement.setString(2, usr.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()){
                usuario = new Usuario();
                usuario.setIdUsuario(resultSet.getShort("Idusuario"));
                usuario.setEmail(resultSet.getString("Email"));
                usuario.setPassword(resultSet.getString("Password"));
                usuario.setNombre(resultSet.getString("Nombre"));
                usuario.setApellidos(resultSet.getString("Apellidos"));
                usuario.setNif(resultSet.getString("NIF"));
                usuario.setTelefono(resultSet.getString("Telefono"));
                usuario.setDireccion(resultSet.getString("Direccion"));
                usuario.setCodigoPostal(resultSet.getString("CodigoPostal"));
                usuario.setLocalidad(resultSet.getString("Localidad"));
                usuario.setProvincia(resultSet.getString("Provincia"));
                usuario.setUltimoAcceso(resultSet.getDate("UltimoAcceso"));
                usuario.setAvatar(resultSet.getString("Avatar"));
            }
            

        } catch (SQLException e) {
            e.printStackTrace();
            
        }finally{
            this.closeConnection();
        }
        return usuario;
    }
    
    /**
     * Actualiza la información de un usuario
     * @param usuario 
     */
    @Override
    public void update(Usuario usuario) {
        String sql = "UPDATE `tecnocity`.`usuarios` SET  Password = MD5(?), Nombre = ?, Apellidos = ?, NIF = ?, Telefono = ?, Direccion = ?, "
            + "CodigoPostal = ?, Localidad = ?, Provincia = ?, Avatar = ? WHERE Email = ?";
        Connection conexion = (Connection) ConnectionFactory.getConnection();

        try(PreparedStatement preparada = conexion.prepareStatement(sql);){
            conexion.setAutoCommit(false);

            preparada.setString(1, usuario.getPassword());
            preparada.setString(2, usuario.getNombre());
            preparada.setString(3, usuario.getApellidos());
            preparada.setString(4, usuario.getNif());
            preparada.setString(5, usuario.getTelefono());
            preparada.setString(6, usuario.getDireccion());
            preparada.setString(7, usuario.getCodigoPostal());
            preparada.setString(8, usuario.getLocalidad());
            preparada.setString(9, usuario.getProvincia());
            preparada.setString(10, usuario.getAvatar());
            preparada.setString(11, usuario.getEmail());
            
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
     * Actualiza el ultimo acceso del usuario
     * @param usuario 
     */
    @Override
    public void setUltAcceso(Usuario usuario) {
        String sql = "UPDATE `tecnocity`.`usuarios` SET  UltimoAcceso = ? WHERE idUsuario = ?";
        Connection conexion = (Connection) ConnectionFactory.getConnection();
        try(PreparedStatement preparada = conexion.prepareStatement(sql);){
            conexion.setAutoCommit(false);

            Date fechaActual = new Date();
            Timestamp timestamp = new Timestamp(fechaActual.getTime());

            preparada.setTimestamp(1, timestamp);
            preparada.setShort(2, usuario.getIdUsuario());
            
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
    
    @Override
    public short getLastInsert() {
        String sql = "SELECT MAX(idUsuario) AS lastIndex FROM `tecnocity`.`usuarios`";
        Statement sentencia;
        short lastIndex = 0;
        try {
            sentencia = ConnectionFactory.getConnection().createStatement();
            ResultSet resultado = sentencia.executeQuery(sql);
            while(resultado.next()){
                lastIndex = resultado.getShort("lastIndex");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.closeConnection();
        }
        
        return lastIndex;
}        
    
    @Override
    public void closeConnection() {
        ConnectionFactory.closeConexion();
    }

    

    

    

    

    
    
}
