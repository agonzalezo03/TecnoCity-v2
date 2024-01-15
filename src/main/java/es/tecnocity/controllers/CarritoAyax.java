/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.tecnocity.controllers;

import com.google.gson.Gson;
import es.tecnocity.beans.LineaPedido;
import es.tecnocity.dao.ILineasPedidosDAO;
import es.tecnocity.dao.IPedidosDAO;
import es.tecnocity.beans.Pedido;
import es.tecnocity.beans.Producto;
import es.tecnocity.beans.Usuario;
import es.tecnocity.daofactory.DAOFactory;
import es.tecnocity.models.Carrito;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author Clases
 */
@WebServlet(name = "CarritoAyax", urlPatterns = {"/CarritoAyax"})
public class CarritoAyax extends HttpServlet {

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
        Pedido carrito =  (Pedido) request.getSession().getAttribute("carrito");
        IPedidosDAO pdao = daof.getPedidosDAO();
        ILineasPedidosDAO lpdao = daof.getLineasPedidoDAO();
        Producto producto = null;
        ArrayList<LineaPedido> lineasPedido = null;
        LineaPedido lineaPedido = new LineaPedido();

        BufferedReader reader = request.getReader();
        String json = reader.lines().collect(Collectors.joining(System.lineSeparator()));

        // Convertir la cadena JSON a un objeto JSON
        JSONObject jsonObject = new JSONObject(json);

        // Obtener el valor del parámetro
        String accion = jsonObject.getString("accion");
        String idProducto = jsonObject.getString("idProducto");
        float total = 0;
        
        carrito = Carrito.accionesCarrito(Short.parseShort(idProducto), carrito, accion);
        //Compruebp si el usuario se ha logeado
        if (request.getSession().getAttribute("sesion") != null) {
            //Si el usuario esta logeado actualizo la lineaPedido del producto
            Usuario usuario = (Usuario) request.getSession().getAttribute("sesion");
            short idPedido = pdao.getIdPedido(usuario);
            producto = new Producto();
            producto.setIdProducto(Short.parseShort(idProducto));
            lineasPedido = carrito.getLineasPedido();
            for (LineaPedido lpedido : lineasPedido) {
                Producto productoLinea = lpedido.getProducto();
                if (productoLinea.getIdProducto() == producto.getIdProducto()) {                   
                    lineaPedido = lpedido;
                }
            }
            if(accion.equals("eliminar")){
                lineaPedido.setProducto(producto);
                Carrito.accionesDAO(lineaPedido, idPedido, lpdao, accion);
            }else{
                Carrito.accionesDAO(lineaPedido, idPedido, lpdao, accion);
            }
            

        } else {
            //Si no esta logeado actualizo la cookie
            Cookie[] cookies = request.getCookies();
            response.addCookie(Carrito.accionesCookie(idProducto, cookies, accion));
        }
        
        
        //Guardo el Importe y el Iva en pedido para pasarselo al js
        Pedido pedido = new Pedido();
        lineasPedido = carrito.getLineasPedido();
        for (LineaPedido lpedido : lineasPedido) {
            Producto productoLinea = lpedido.getProducto();
            total = total + (productoLinea.getPrecio() * lpedido.getCantidad());
        }
        pedido.setImporte(total);
        pedido.setIva((float) (total * 0.21));
        if (request.getSession().getAttribute("sesion") != null) {
            pedido.setIdPedido(pdao.getIdPedido((Usuario) request.getSession().getAttribute("sesion")));
        }
        total = 0;
        
        
        Gson gson = new Gson();
        String resultadoJSON = gson.toJson(pedido);

        // Configurar la respuesta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Escribir la respuesta en el flujo de salida
        response.getWriter().write(resultadoJSON);
        request.getSession().setAttribute("carrito", carrito);
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
