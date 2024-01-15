/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.tecnocity.dao;

import es.tecnocity.beans.Categoria;
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
public class CategoriasDAO implements ICategoriasDAO{

    /**
     * Obtiene todas las categorias
     * @return lista
     */
    @Override
    public List<Categoria> getCategorias() {
        
         List<Categoria> lista = null;
        String consulta = "SELECT * FROM tecnocity.categorias";
        try {
            Statement sentencia = ConnectionFactory.getConnection().createStatement();
            ResultSet resultado = sentencia.executeQuery(consulta);
            lista = new ArrayList();
            while (resultado.next()) {
                Categoria categoria = new Categoria();

                categoria.setIdCategoria(resultado.getByte("IdCategoria"));
                categoria.setNombre(resultado.getString("Nombre"));
                
                byte[] bytesImagen = resultado.getBytes("Imagen");
                categoria.setImagen(Base64.getEncoder().encodeToString(bytesImagen));
                byte[] decodeBytes = Base64.getDecoder().decode(categoria.getImagen());
                String decodeS = new String(decodeBytes);
                categoria.setImagen(decodeS);
         
                lista.add(categoria);
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

    @Override
    public void closeConnection() {
        ConnectionFactory.closeConexion();
    }
    
}
