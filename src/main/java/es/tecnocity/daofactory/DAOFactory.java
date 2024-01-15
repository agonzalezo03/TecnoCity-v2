/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.tecnocity.daofactory;

import es.tecnocity.dao.ICategoriasDAO;
import es.tecnocity.dao.ILineasPedidosDAO;
import es.tecnocity.dao.IPedidosDAO;
import es.tecnocity.dao.IProductosDAO;
import es.tecnocity.dao.IUsuariosDAO;


public abstract class DAOFactory {

    /**
     * Objeto DAO de Usuario
     * @return interface de dicho objeto DAO
     */
    public abstract IUsuariosDAO getUsuariosDAO();
    
    /**
     * Objeto DAO de Productos
     * @return interface de dicho objeto DAO
     */
    public abstract IProductosDAO getProductosDAO();
    
    /**
     * Objeto DAO de Productos
     * @return interface de dicho objeto DAO
     */
    public abstract ICategoriasDAO getCategoriasDAO();
    
    /**
     * Objeto DAO de Pedidos
     * @return interface de dicho objeto DAO
     */
    public abstract IPedidosDAO getPedidosDAO();
    
    /**
     * Objeto DAO de Pedidos
     * @return interface de dicho objeto DAO
     */
    public abstract ILineasPedidosDAO getLineasPedidoDAO();
    
    /**
     * Obtiene la fábrica concreta a la fuente de datos
     * @return la fábrica concreta
     */
    public static DAOFactory getDAOFactory() {
        
        DAOFactory daof = null;

        daof = new MySQLDAOFactory();

        return daof;
    }

}
