/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.tecnocity.controllers;

import es.tecnocity.beans.LineaPedido;
import es.tecnocity.dao.ILineasPedidosDAO;
import es.tecnocity.dao.IPedidosDAO;
import es.tecnocity.beans.Pedido;
import es.tecnocity.beans.Producto;
import es.tecnocity.beans.Usuario;
import es.tecnocity.daofactory.DAOFactory;
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
@WebServlet(name = "CarritoController", urlPatterns = {"/CarritoController"})
public class CarritoController extends HttpServlet {

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
        String boton = request.getParameter("boton");
        String url = "index.jsp";
        IPedidosDAO pdao = daof.getPedidosDAO();
        ILineasPedidosDAO lpdao = daof.getLineasPedidoDAO();
        Pedido carrito =  (Pedido) request.getSession().getAttribute("carrito");
        float total = 0;

        
        switch(boton) {
            case"Eliminar":
                url = "JSP/confirmarEliminar.jsp";
                break;
            case"cancelar":
                url = "JSP/carrito.jsp";
                break;
            case"confirmar":
            //Elimina el carrito entero
            request.getSession().removeAttribute("carrito");
            if (request.getSession().getAttribute("sesion") != null) {
                short idPedido = pdao.getIdPedido((Usuario) request.getSession().getAttribute("sesion"));
                lpdao.delLineas(idPedido);
            } else {
                Cookie[] cookies = request.getCookies();

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (!cookie.getName().equals("JSESSIONID")) {
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }
            }
            break;
        case"pagar":
            //Va a la página para finalizar la compra y guarda el pedido en sesion
            url = "JSP/finCompra.jsp";
            ArrayList<LineaPedido> lineasPedido = carrito.getLineasPedido();
            for(LineaPedido lpedido : lineasPedido){
                Producto producto = lpedido.getProducto();
                total = total + (producto.getPrecio() * lpedido.getCantidad());
            }
            carrito.setImporte(total);
            carrito.setIva((float) (total * 0.21));
             if (request.getSession().getAttribute("sesion") != null) {
                carrito.setIdPedido(pdao.getIdPedido((Usuario) request.getSession().getAttribute("sesion")));
                request.getSession().setAttribute("carrito", carrito);
                pdao.setImporeIva(carrito);
            }else{
                 url = "JSP/login.jsp";
                 request.setAttribute("mensaje", "Debe iniciar sesión para finalizar la compra");
            }
            break;
            
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
