/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.tecnocity.models;

import es.tecnocity.beans.Usuario;
import es.tecnocity.dao.IUsuariosDAO;
import es.tecnocity.daofactory.DAOFactory;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author Clases
 */
public class ComprobarFormulario {

    /**
     * Comprueba que todos los campos obligatorios esten rellenos
     *
     * @param request
     * @return
     */
    public static boolean CamposRellenos(HttpServletRequest request) {
        boolean relleno = true;
        Enumeration<String> nombresParametros = request.getParameterNames();

        while (nombresParametros.hasMoreElements()) {
            String nombreParametro = nombresParametros.nextElement();
            String valorParametro = request.getParameter(nombreParametro);

            if (!nombreParametro.equals("avatar") && !nombreParametro.equals("telefono") && !nombreParametro.equals("newPassword")
                    && !nombreParametro.equals("newrePassword")) {
                if (valorParametro == null || valorParametro.isEmpty()) {
                    relleno = false;
                }
            }

        }
        return relleno;
    }

    public static String comprobarFoto(Part filePart, String filePath, String dirImagen) throws IOException {
        DAOFactory daof = DAOFactory.getDAOFactory();
        IUsuariosDAO udao = daof.getUsuariosDAO();
        StringBuilder nombreFichero = new StringBuilder();
        int lastInsert = udao.getLastInsert();
        lastInsert++;
        if ( filePart.getSize() > 0) {
            if(filePart.getSize() < 102400){
                
            
            String extension = ".jpg";
            if (filePart.getContentType().equals("image/png")) {
                extension = ".png";
            }
            // Obtenemos el nombre del fichero
            nombreFichero.append("usuario").append(String.valueOf(lastInsert)).append(extension);
            filePath = dirImagen + nombreFichero.toString();
            // Escribimos el fichero en el servidor
            
            filePart.write(filePath);
            }else{
                nombreFichero.append("null");
            }
        }else{
            nombreFichero.append("default-user.jpg");
        }
        
        return String.valueOf(nombreFichero);
    }
}
