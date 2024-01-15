package es.tecnocity.daofactory;

import es.tecnocity.dao.CategoriasDAO;
import es.tecnocity.dao.ICategoriasDAO;
import es.tecnocity.dao.ILineasPedidosDAO;
import es.tecnocity.dao.IPedidosDAO;
import es.tecnocity.dao.IProductosDAO;
import es.tecnocity.dao.IUsuariosDAO;
import es.tecnocity.dao.LineasPedidosDAO;
import es.tecnocity.dao.PedidosDAO;
import es.tecnocity.dao.ProductosDAO;
import es.tecnocity.dao.UsuariosDAO;



public class MySQLDAOFactory extends DAOFactory{

    
    @Override
    public IUsuariosDAO getUsuariosDAO() {
        return new UsuariosDAO();
    }

    @Override
    public IProductosDAO getProductosDAO() {
        return new ProductosDAO();
    }

    @Override
    public IPedidosDAO getPedidosDAO() {
        return new PedidosDAO();
    }

    @Override
    public ILineasPedidosDAO getLineasPedidoDAO() {
        return new LineasPedidosDAO();
    }

    @Override
    public ICategoriasDAO getCategoriasDAO() {
        return new CategoriasDAO();
    }



}