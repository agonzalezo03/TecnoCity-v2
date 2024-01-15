/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.tecnocity.controllers;

import es.tecnocity.dao.ILineasPedidosDAO;
import es.tecnocity.dao.IPedidosDAO;
import es.tecnocity.dao.IProductosDAO;
import es.tecnocity.dao.IUsuariosDAO;
import es.tecnocity.beans.LineaPedido;
import es.tecnocity.beans.Pedido;
import es.tecnocity.beans.Producto;
import es.tecnocity.beans.Usuario;
import es.tecnocity.daofactory.DAOFactory;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Clases
 */
@WebServlet(name = "FrontController", urlPatterns = {"/FrontController"})
public class FrontController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        DAOFactory daof = DAOFactory.getDAOFactory();
        String accion = request.getParameter("boton");
        String detalle = request.getParameter("detalle");
        String url = "index.jsp";
        Pedido pedido = null;
        IProductosDAO pdao = daof.getProductosDAO();
        IPedidosDAO pedao = daof.getPedidosDAO();
        ILineasPedidosDAO lpdao = daof.getLineasPedidoDAO();
        IUsuariosDAO udao = daof.getUsuariosDAO();
        Usuario usuario = new Usuario();
        ArrayList<LineaPedido> lineasPedidos = null;
        Producto producto = null;
        

        if (accion != null) {

            switch (accion) {
                //Va al formulario de registro
                case "registro":
                    url = "JSP/registro.jsp";
                    break;
                //Va a la página de inicio
                case "inicio":
                    url = "index.jsp";
                    break;
                //Va a la página de tienda
                case "tienda":
                    url = "JSP/tienda.jsp";
                    break;
                //va al formulario de iniciar sesión
                case "login":
                    url = "JSP/login.jsp";
                    break;
                //Cierra la sesión
                case "salir":
                    url = "index.jsp";
                    //Eliminamos la sesion del usuario y guardamos el ultimo acceso
                    usuario = (Usuario) request.getSession().getAttribute("sesion");
                    request.getSession().removeAttribute("sesion");
                    request.getSession().removeAttribute("carrito");
                    udao.setUltAcceso(usuario);
                    break;
                //Despues del aviso vuelve al index
                case "continuar":
                    url = "index.jsp";
                    break;
                //Va a formulario para editar el perfil
                case "perfil":
                    url = "JSP/login/editarPerfil.jsp";
                    break;
                //Va a la pagina para ver todos los pedidos
                case "pedidos":
                    //Guarda los pedidos que han finalizado
                    url = "JSP/login/verPedidos.jsp";
                    usuario = (Usuario) request.getSession().getAttribute("sesion");
                    //Obtengo los pedidos finalizados de un usuario
                    List<Pedido> pedidos = pedao.getPedidos(usuario);
                    ArrayList<Pedido> listaPedidos = new ArrayList();
                    lineasPedidos = new ArrayList();
                    //recorro los pedidos
                    for(Pedido p : pedidos){
                        //Obtengo las linea de pedido del pedido
                        lineasPedidos = lpdao.getLineasPedidos(p.getIdPedido());
                        //recorro las lineas de pedido obtenidas para darle los valores del Producto
                        for(LineaPedido lpedido : lineasPedidos){   
                            producto = lpedido.getProducto();
                            lpedido.setProducto(pdao.getProducto(String.valueOf(producto.getIdProducto())));
                            
                            p.setLineasPedido(lineasPedidos);
                        }
                        
                        listaPedidos.add(p);
                        
                    }
                    request.setAttribute("pedidos", listaPedidos);
                    break;
                //Muestro el carrito
                case "carrito":
                    //Guarda en sesion el carrito cargandolo desde la BD o las cookies
                    url = "JSP/carrito.jsp";
                    Cookie[] cookies = request.getCookies();
                    //Compruebo si se ha iniciado sesión
                    if (request.getSession().getAttribute("sesion") != null) {
                        
                        usuario = (Usuario) request.getSession().getAttribute("sesion");
                        //Obtego las lineas de pedido del usuario que esten en estado "c"
                        short idPedido = pedao.getIdPedido(usuario);
                        lineasPedidos = lpdao.getLineasPedidos(idPedido);
                        //Recorro las lineas y le doi el valor producto
                        for(LineaPedido lineaPedido : lineasPedidos){
                            producto = lineaPedido.getProducto();
                            lineaPedido.setProducto( pdao.getProducto(String.valueOf(producto.getIdProducto())));
                        }
                        //Guardo las lines pedido en un objeto pedido y las guardo en sesión
                        pedido = new Pedido();
                        pedido.setLineasPedido(lineasPedidos);
                        request.getSession().setAttribute("carrito", pedido);
                        
                        
                    } else {
                        //Si no esta logeado
                        //compruebo si existen cookies
                        if (cookies != null && cookies.length >= 0) {
                            lineasPedidos = new ArrayList();
                             pedido = new Pedido();
                             //Recorro las cookies
                            for (Cookie cookie : cookies) {
                                //Obtengo el nobre de la cookie y compruebo si es igual a idProducto#
                                String id = URLDecoder.decode(cookie.getName(), "UTF-8");
                                if (id.startsWith("idProducto#")) {
                                    //Obtengo el id del producto y guardo el producto en una lineaPedido
                                    String idProductoSTR = id.substring("idProducto#".length());
                                    short idProducto = Short.parseShort(idProductoSTR);
                                    LineaPedido lineaPedido = new LineaPedido();
                                    lineaPedido.setProducto(pdao.getProducto(idProductoSTR));
                                    lineasPedidos.add(lineaPedido);
                                    //Recorro las linesPedidos y asigno la cantidad a la linea
                                    for (LineaPedido linea : lineasPedidos) {
                                        producto = linea.getProducto();
                                        if (producto.getIdProducto() == idProducto) {
                                            String cantidadSTR = URLDecoder.decode(cookie.getValue(), "UTF-8");
                                            linea.setCantidad(Short.parseShort(cantidadSTR));
                                        }

                                    }
                                   
                                    
                                }
                            }
                            //guardo las lineas en un objeto pedido y lo guardo en sesión
                            pedido.setLineasPedido(lineasPedidos);
                            
                            request.getSession().setAttribute("carrito", pedido);
                        }//if
                    }
                    break;

            }
        }

        //Va a la página de detalle del producto
        if (detalle != null) {
            url = "JSP/detalle.jsp";
            request.setAttribute("producto", detalle);
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
