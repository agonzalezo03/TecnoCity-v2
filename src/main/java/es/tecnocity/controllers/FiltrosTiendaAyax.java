/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.tecnocity.controllers;

import com.google.gson.Gson;
import es.tecnocity.dao.IProductosDAO;
import es.tecnocity.beans.Producto;
import es.tecnocity.daofactory.DAOFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author Clases
 */
@WebServlet(name = "FiltrosTiendaAyax", urlPatterns = {"/FiltrosTiendaAyax"})
public class FiltrosTiendaAyax extends HttpServlet {

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
    DAOFactory daof = DAOFactory.getDAOFactory();
    IProductosDAO pdao = daof.getProductosDAO();
    ArrayList<Producto> productos = new ArrayList();

    // Leer el JSON de la solicitud
    BufferedReader reader = request.getReader();
    String json = reader.lines().collect(Collectors.joining(System.lineSeparator()));

    // Convertir la cadena JSON a un objeto JSON
    JSONObject jsonObject = new JSONObject(json);

    // Verificar si "categorias" es un array o una cadena

        // Obtener una única categoría como cadena
        String categoria = jsonObject.getString("categorias");
        String[] categoriasArray = {categoria};

        if(categoria != null && !categoria.isEmpty()){
            
            String categoriasString = String.join(", ", categoriasArray);
        // Obtener productos según la única categoría

        productos = (ArrayList<Producto>) pdao.getProductosPorCategoria(categoriasString);

        }else{
            productos = (ArrayList<Producto>) request.getServletContext().getAttribute("productos");
        }
        
    
    
    // Convertir productos a formato JSON
    Gson gson = new Gson();
    String resultadoJSON = gson.toJson(productos);

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
