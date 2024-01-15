/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.tecnocity.controllers;

import es.tecnocity.dao.IPedidosDAO;
import es.tecnocity.beans.Pedido;
import es.tecnocity.daofactory.DAOFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Clases
 */
@WebServlet(name = "FinCompra", urlPatterns = {"/FinCompra"})
public class FinCompra extends HttpServlet {

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
        Pedido pedido = (Pedido) request.getSession().getAttribute("carrito");
        switch(boton){
            case"pagar":
                //Finaliza la compra, cambia el estado a f y añade la fecha actual al pedido
                pdao.chageEstado(pedido.getIdPedido());
                url = "JSP/aviso.jsp";
                request.setAttribute("aviso", "Se ha realizado el pedido con exito");
                break;
            case"volver":
                url = "JSP/carrito.jsp";
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
