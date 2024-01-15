package es.tecnocity.events;

import es.tecnocity.dao.ICategoriasDAO;
import es.tecnocity.dao.IProductosDAO;
import es.tecnocity.beans.Categoria;
import es.tecnocity.beans.Producto;
import es.tecnocity.daofactory.DAOFactory;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Alberto
 */
@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Cuando se inicia la aplicación
        DAOFactory daof = DAOFactory.getDAOFactory();
        List<Producto> lista = null;
        IProductosDAO pdao = daof.getProductosDAO();
        List<Categoria> categorias = null;
        ICategoriasDAO cdao = daof.getCategoriasDAO();

        //Cargo los producto y las categorias en el contextode la aplicación
        lista = pdao.getProductos();
        categorias = cdao.getCategorias();

        sce.getServletContext().setAttribute("productos", lista);
        sce.getServletContext().setAttribute("categorias", categorias);

        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cuando se para la aplicación
        System.out.println("Se ha invocado contextDestroyed ya que se ha detenido la aplicación");
    }


    private String getTime() {
        return new Date(System.currentTimeMillis()).toString();
    }
}
