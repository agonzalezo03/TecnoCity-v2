/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.tecnocity.controllers;

import es.tecnocity.beans.LineaPedido;
import es.tecnocity.beans.Pedido;
import es.tecnocity.dao.ILineasPedidosDAO;
import es.tecnocity.dao.IPedidosDAO;
import es.tecnocity.dao.IProductosDAO;
import es.tecnocity.dao.IUsuariosDAO;

import es.tecnocity.beans.Producto;
import es.tecnocity.beans.Usuario;
import es.tecnocity.daofactory.DAOFactory;
import es.tecnocity.models.ComprobarFormulario;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Clases
 */
@WebServlet(name = "Registro", urlPatterns = {"/Registro"})
@MultipartConfig
public class Registro extends HttpServlet {

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
        IUsuariosDAO udao = daof.getUsuariosDAO();
        IPedidosDAO pdao = daof.getPedidosDAO();
        IProductosDAO prodao = daof.getProductosDAO();
        ILineasPedidosDAO lpdao = daof.getLineasPedidoDAO();
        String filePath = null;
        String dirImagen = request.getServletContext().getRealPath("/IMG/USERS/");
        String fileName = "";

        switch (boton) {
            case "Registrarse":
                url = "JSP/registro.jsp";
                //Compruebo si los campos estan rellenos y si las contraseñas coinciden
                if (ComprobarFormulario.CamposRellenos((HttpServletRequest) request)) {
                    //Compruebo si ha introducido la misma contraseña
                    if (request.getParameter("password").equals(request.getParameter("rePassword"))) {
                        Part filePart = request.getPart("avatar");
                        //Obtengo el nombre de la foto de perfil
                        if (filePart.getName().length() != 0) {
                                fileName = ComprobarFormulario.comprobarFoto(filePart, filePath, dirImagen);
                        }
                        //Si el nombre es null significa que la foto era muy grande
                        if(!fileName.equals("null")){
                        try {
                            Usuario usuario = new Usuario();                           
                            boolean exito;
                            //Cargo el usuario con los parametros
                            BeanUtils.populate(usuario, request.getParameterMap());
                            usuario.setAvatar(fileName);
                            //Añado el usuario a la base de datos
                            exito = udao.addUsuario(usuario);

                            

                            if (exito) {
                                url = "JSP/aviso.jsp";
                                request.setAttribute("aviso", "Se ha registrado correctamente");

                            } else {
                                request.setAttribute("mensaje", "No se ha podido registrar correctamente");
                            }

                        } catch (IllegalAccessException | InvocationTargetException ex) {
                            Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }else{
                            request.setAttribute("mensaje", "El archivo es demasiado grande");
                        }
                    } else {
                        request.setAttribute("mensaje", "Las contraseñas no coinciden");
                    }
                } else {
                    request.setAttribute("mensaje", "No has rellenado todos los campos");
                }
                break;
                
            case "Entrar":
                url = "JSP/login.jsp";
                //Compruebo si los campos estan rellenos
                if (ComprobarFormulario.CamposRellenos((HttpServletRequest) request)) {

                    try {
                        Usuario usuario = new Usuario();
                        //Cargo el usuario con los parametros
                        BeanUtils.populate(usuario, request.getParameterMap());
                        //Compruebo si existe un usuario con ese correo y esa password
                        usuario = udao.login(usuario);

                        //Si el usuario es null error
                        if (usuario != null) {
                            url = "index.jsp";
                            request.getSession().setAttribute("sesion", usuario);
                            Cookie[] cookies = request.getCookies();
                            //Elimino las cookies
                            if (cookies != null) {
                                for (Cookie cookie : cookies) {
                                    if (!cookie.getName().equals("JSESSIONID")) {
                                        cookie.setMaxAge(0);
                                        response.addCookie(cookie);
                                    }

                                }
                            }

                            //Comprueba si el usuario tenia un carrito en la base de datos, si no 
                            //Crea un pedido y mete lo que tenia en el carrito de sesion si existe
                            if (pdao.getIdPedido(usuario) != 0) {
                                Pedido pedido = new Pedido();
                                pedido.setIdPedido(pdao.getIdPedido(usuario));
                                ArrayList<LineaPedido> lineasPedido = lpdao.getLineasPedidos(pedido.getIdPedido());
                                for (LineaPedido lineaPedido : lineasPedido) {
                                    Producto producto = lineaPedido.getProducto();
                                    lineaPedido.setProducto(prodao.getProducto(String.valueOf(producto.getIdProducto())));
                                }
                                pedido.setLineasPedido(lineasPedido);
                                request.getSession().setAttribute("carrito", pedido);
                            } else {

                                if (request.getSession().getAttribute("carrito") != null) {
                                    Pedido carrito = (Pedido) request.getSession().getAttribute("carrito");
                                    pdao.addPedido(usuario);
                                    short idPedido = pdao.getIdPedido(usuario);
                                    ArrayList<LineaPedido> lineasPedido = carrito.getLineasPedido();
                                    for (LineaPedido lpedido : lineasPedido) {
                                        lpdao.addLinea(idPedido, lpedido);
                                    }
                                }
                            }

                        } else {
                            request.setAttribute("mensaje", "El correo o la contraseña estan mal");
                        }

                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    request.setAttribute("mensaje", "No has rellenado todos los campos");
                }
                break;

            case "Editar":
                //Edita un usuario
                url = "JSP/login/editarPerfil.jsp";
                if (ComprobarFormulario.CamposRellenos(request)) {

                    try {
                        Usuario usuario = new Usuario();
                        Usuario usuariobd = new Usuario();
                        BeanUtils.populate(usuario, request.getParameterMap());
                        usuariobd = udao.login(usuario);
                        Part filePart = request.getPart("avatar");
                        if (filePart.getName().length() != 0) {
                                fileName = ComprobarFormulario.comprobarFoto(filePart, filePath, dirImagen);
                        }
                        //Comprueba si la foto es demasiado grande
                        if(!fileName.equals("null")){
                            //comprueba si ha subido alguna foto
                            if(!fileName.equals("default-user.jpg")){
                                usuario.setAvatar(fileName);
                            }else{
                                usuario.setAvatar(usuariobd.getAvatar());
                            }
                            
                        if (usuariobd != null) {
                            //Comprueba si ha puesto algo en el campo nueva contraseña
                            if (request.getParameter("newPassword") != null && !request.getParameter("newPassword").isEmpty()) {
                                //Comprueba que sean iguales
                                if (request.getParameter("newPassword").equals(request.getParameter("newrePassword"))) {
                                    usuario.setPassword(request.getParameter("newPassword"));
                                    udao.update(usuario);
                                    url = "index.jsp";
                                    request.getSession().setAttribute("sesion", udao.login(usuario));
                                } else {
                                    request.setAttribute("mensaje", "Las contraseñas no coinciden");
                                }
                            } else {
                                udao.update(usuario);
                                url = "JSP/aviso.jsp";
                                request.setAttribute("aviso", "Se han realizado cambios en su perfil");
                                request.getSession().setAttribute("sesion", udao.login(usuario));
                            }

                        } else {
                            request.setAttribute("mensaje", "Las contraseña no es correcta");
                        }
                        }else{
                            request.setAttribute("mensaje", "El archivo es demasiado grande");
                        }

                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {

                    request.setAttribute("mensaje", "No has rellenado todos los campos");
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
