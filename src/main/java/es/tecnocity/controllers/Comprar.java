/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.tecnocity.controllers;

import es.tecnocity.beans.LineaPedido;
import es.tecnocity.beans.Pedido;
import es.tecnocity.dao.ILineasPedidosDAO;
import es.tecnocity.dao.IPedidosDAO;
import es.tecnocity.beans.Producto;
import es.tecnocity.beans.Usuario;
import es.tecnocity.daofactory.DAOFactory;
import es.tecnocity.models.Carrito;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "Comprar", urlPatterns = {"/Comprar"})
public class Comprar extends HttpServlet {

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
        ArrayList<Producto> productos = (ArrayList<Producto>) request.getServletContext().getAttribute("productos");
        String idProductoStr = request.getParameter("id");
        Pedido carrito =  (Pedido) request.getSession().getAttribute("carrito");
        IPedidosDAO pdao = daof.getPedidosDAO();
        ILineasPedidosDAO lpdao = daof.getLineasPedidoDAO();
        String url = "index.jsp";
        short idProducto = Short.parseShort(idProductoStr);
        Producto product = null;
        LineaPedido lineaPedido = null;

        //Añade un producto al carrito
        for (Producto producto : productos) {

            if (producto.getIdProducto() == idProducto) {
                lineaPedido = Carrito.addProducto(producto, carrito, request);
                url = "index.jsp";
                product = lineaPedido.getProducto();
            }
        }

        //Si el usuario esta logeado añade el producto en la BD, si no lo guarda en las cookies
        if (request.getSession().getAttribute("sesion") != null) {
            Usuario usuario = (Usuario) request.getSession().getAttribute("sesion");
            if(pdao.getIdPedido(usuario) != 0){
                short idPedido = pdao.getIdPedido(usuario);
                if(lpdao.comprobarLinea(idPedido, product)){
                    lpdao.setCantidad(idPedido, lineaPedido);
                }else{
                    lpdao.addLinea(idPedido, lineaPedido);
                }
            }else{
                pdao.addPedido(usuario);
                short idPedido = pdao.getIdPedido(usuario);
                lpdao.addLinea(idPedido, lineaPedido);
            }
        } else {
            Cookie[] cookies = request.getCookies();
            response.addCookie(Carrito.addCookie(product, cookies));
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
