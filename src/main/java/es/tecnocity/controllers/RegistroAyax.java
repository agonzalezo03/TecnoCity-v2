/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.tecnocity.controllers;

import es.tecnocity.dao.IUsuariosDAO;
import es.tecnocity.beans.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import com.google.gson.Gson;
import es.tecnocity.daofactory.DAOFactory;
import java.io.BufferedReader;
import java.util.stream.Collectors;

/**
 *
 * @author Clases
 */
@WebServlet(name = "RegistroAyax", urlPatterns = {"/RegistroAyax"})
public class RegistroAyax extends HttpServlet {

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

        BufferedReader reader = request.getReader();
        String json = reader.lines().collect(Collectors.joining(System.lineSeparator()));

        // Convertir la cadena JSON a un objeto JSON
        JSONObject jsonObject = new JSONObject(json);

        String datoForumulario = jsonObject.getString("accion");
        String inputFormulario = jsonObject.getString("valor");
        Gson gson = new Gson();
        String resultadoJSON;
        System.out.println("valor nif: " + datoForumulario);
        switch (datoForumulario) {
            //Calcula la letra del NIF
            case "nif":
                String letrasNIF = "TRWAGMYFPDXBNJZSQVHLCKE";
                int posicionLetra = Integer.parseInt(inputFormulario) % 23;

                char letra = letrasNIF.charAt(posicionLetra);
                inputFormulario = inputFormulario + String.valueOf(letra);
                
                resultadoJSON = gson.toJson(inputFormulario);
                response.getWriter().write(resultadoJSON);
                
                break;
                //Obtiene todos los correos
            case "correo":
                List<Usuario> lista = new ArrayList<>();
                IUsuariosDAO udao = daof.getUsuariosDAO();
                lista = udao.getCorreos();
                resultadoJSON = gson.toJson(lista);
                response.getWriter().write(resultadoJSON);
                break;
        }
        

        
        

        // Configurar la respuesta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

            
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
