/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.tecnocity.dao;

import es.tecnocity.beans.LineaPedido;
import es.tecnocity.beans.Producto;
import es.tecnocity.beans.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Clases
 */
public interface ILineasPedidosDAO {
    public void addLinea(short idPedido, LineaPedido lineaPedido);
    public ArrayList<LineaPedido> getLineasPedidos(short ipPedido);
    public boolean comprobarLinea(short idPedido, Producto producto);
    public void setCantidad(short idPedido, LineaPedido lineaPedido);
    public void delLinea(short idPedido, Producto producto);
    public void delLineas(short idPedido);
    public void closeConnection();
}
