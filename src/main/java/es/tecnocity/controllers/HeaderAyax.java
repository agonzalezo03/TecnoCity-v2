/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.tecnocity.controllers;

import com.google.gson.Gson;
import es.tecnocity.beans.LineaPedido;
import es.tecnocity.beans.Pedido;
import es.tecnocity.beans.Usuario;
import es.tecnocity.dao.ILineasPedidosDAO;
import es.tecnocity.dao.IPedidosDAO;
import es.tecnocity.daofactory.DAOFactory;
import java.io.IOException;
import java.net.URLDecoder;
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
@WebServlet(name = "HeaderAyax", urlPatterns = {"/HeaderAyax"})
public class HeaderAyax extends HttpServlet {

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
        IPedidosDAO pdao = daof.getPedidosDAO();
        ILineasPedidosDAO lpdao = daof.getLineasPedidoDAO();
        ArrayList<LineaPedido> lineasPedido = null;

        Pedido carrito = null;
        
        int cantidadProductos = 0;
        //Compruebo si esta logeado
        if (request.getSession().getAttribute("sesion") != null) {
            Usuario usuario = (Usuario) request.getSession().getAttribute("sesion");
            carrito = new Pedido();
            //Obtengo las lineas de pedido de un usuario con estado "c"
            carrito.setIdPedido(pdao.getIdPedido(usuario));
            lineasPedido = lpdao.getLineasPedidos(carrito.getIdPedido());
            //Sumo mas uno a la cantidad por cada iteración
            for (LineaPedido lpedido : lineasPedido) {
                cantidadProductos++;
            }
        } else {
            //Obtengo las cookies y las recorro
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length >= 0) {
                for (Cookie cookie : cookies) {
                    String id = URLDecoder.decode(cookie.getName(), "UTF-8");
                    //Sumo mas uno a la cantidad por cada iteración, si el valor empieza por idProducto#
                    if (id.startsWith("idProducto#")) {
                        cantidadProductos = cantidadProductos + 1;
                    }
                }

            }//if

        }
        Gson gson = new Gson();
        String resultadoJSON = gson.toJson(cantidadProductos);

        // Configurar la respuesta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Escribir la respuesta en el flujo de salida
        response.getWriter().write(resultadoJSON);
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
