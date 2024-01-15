/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.tecnocity.models;

import es.tecnocity.beans.LineaPedido;
import es.tecnocity.beans.Pedido;
import es.tecnocity.dao.ILineasPedidosDAO;
import es.tecnocity.beans.Producto;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Clases
 */
public class Carrito {

    /**
     * Añade un producto al carrito de sesion y si existe le aumenta la cantidad
     *
     * @param producto
     * @param carrito
     * @param request
     * @return
     */
    public static LineaPedido addProducto(Producto producto, Pedido carrito, HttpServletRequest request) {
        boolean existe = false;
        ArrayList<LineaPedido> lineasPedido = null;
        LineaPedido lineaPedido = new LineaPedido();
        
        if (carrito != null) {
            lineasPedido = carrito.getLineasPedido();
            for (LineaPedido lpedido : lineasPedido) {
                Producto productoLinea = lpedido.getProducto();
                if (productoLinea.getIdProducto() == producto.getIdProducto()) {
                    short cantidad = (short) (lpedido.getCantidad() + 1);
                    lpedido.setCantidad(cantidad);
                    existe = true;
                    lineaPedido = lpedido;
                }
            }
            if (!existe) {
                lineaPedido.setProducto(producto);
                lineaPedido.setCantidad((short) 1);
                lineasPedido.add(lineaPedido);
            }
        } else {
            lineasPedido = new ArrayList();
            carrito = new Pedido();
            lineaPedido.setCantidad((short) 1);
            lineaPedido.setProducto(producto);
            lineasPedido.add(lineaPedido);
        }
        carrito.setLineasPedido(lineasPedido);
        request.getSession().setAttribute("carrito", carrito);
        return lineaPedido;
    }

    /**
     * Guarda el idProducto y la cantidad en la cookie y si existe le aumenta la
     * cantidad
     *
     * @param producto
     * @param cookies
     * @return
     * @throws UnsupportedEncodingException Si hay un problema con la codificación/decodificación UTF-8.
     */
    public static Cookie addCookie(Producto producto, Cookie[] cookies) throws UnsupportedEncodingException {
        if (cookies != null) {
            for (Cookie valorCookie : cookies) {
                String nombreCookie = valorCookie.getName();
                String idProductoStr = "idProducto#" + producto.getIdProducto();

                if (nombreCookie.startsWith(idProductoStr)) {
                    String cantidadActualSTR = URLDecoder.decode(valorCookie.getValue(), "UTF-8");
                    short cantidadActual = Short.parseShort(cantidadActualSTR);
                    cantidadActual = (short) (1 + cantidadActual);
                    valorCookie.setValue(URLEncoder.encode(String.valueOf(cantidadActual), "UTF-8"));
                    valorCookie.setMaxAge(172800);
                    return valorCookie;
                }
            }
        }

        String cantidad = "1";
        String idProducto = "idProducto#" + producto.getIdProducto();
        Cookie cookie = new Cookie(idProducto, URLEncoder.encode(cantidad, "UTF-8"));
        cookie.setMaxAge(172800);

        return cookie;
    }

    /**
     * Comprueba la accion que el usuario ha seleccionado y cambia la cantidad o
     * elimina el producto
     *
     * @param idProducto
     * @param carrito
     * @param accion
     * @return
     */
   public static Pedido accionesCarrito(short idProducto, Pedido carrito, String accion) {
    ArrayList<LineaPedido> lineasPedido = carrito.getLineasPedido();
    Iterator<LineaPedido> iterator = lineasPedido.iterator();

    while (iterator.hasNext()) {
        LineaPedido lpedido = iterator.next();
        Producto producto = lpedido.getProducto();

        if (producto.getIdProducto() == idProducto) {
            switch (accion) {
                case "mas":
                    lpedido.setCantidad((short) (lpedido.getCantidad() + 1));
                    break;
                case "menos":
                    lpedido.setCantidad((short) (lpedido.getCantidad() - 1));
                    break;
                case "eliminar":
                    iterator.remove();  // Utilizar el iterador para eliminar el elemento
                    break;
            }
        }
    }

    carrito.setLineasPedido(lineasPedido);
    return carrito;
}


    //Comprueba la accion que el usuario ha seleccionado y cambia la cantidad en linaPedido o elimina una lineaPedido
    public static void accionesDAO(LineaPedido lineaPedido, short idPedido, ILineasPedidosDAO lpdao, String accion) {
        Producto producto = lineaPedido.getProducto();
        System.out.println("valor producto" + producto.getIdProducto());
        switch (accion) {
            case "mas":

                lpdao.setCantidad(idPedido, lineaPedido);
                break;
            case "menos":

                lpdao.setCantidad(idPedido, lineaPedido);
                break;
            case "eliminar":
                
                lpdao.delLinea(idPedido, producto);
                break;
        }
    }

    /**
     * Comprueba la accion que el usuario ha seleccionado y cambia la cantidad o
     * elimina la cookie
     *
     * @param idProducto
     * @param cookies
     * @param accion
     * @return
     * @throws UnsupportedEncodingException Si hay un problema con la codificación/decodificación UTF-8.
     */
    public static Cookie accionesCookie(String idProducto, Cookie[] cookies, String accion)  {
        System.out.println("llega");
        for (Cookie valorCookie : cookies) {
            if (valorCookie.getName().startsWith("idProducto#" + idProducto)) {
                String datos = "";
                try {
                    datos = URLDecoder.decode(valorCookie.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Carrito.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("datosCookie" + accion);
                int cantidadActual = Integer.parseInt(datos);
                switch (accion) {
                    case "mas":
                        cantidadActual++;
                        break;
                    case "menos":
                        cantidadActual--;
                        break;
                    case "eliminar":
                        valorCookie.setMaxAge(0);
                        break;
                }
                System.out.println("Cantidad cookie" + cantidadActual);
                try {
                    valorCookie.setValue(URLEncoder.encode(String.valueOf(cantidadActual), "UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Carrito.class.getName()).log(Level.SEVERE, null, ex);
                }
                return valorCookie;
            }
        }

        return null;

    }

}
