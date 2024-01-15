/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.tecnocity.dao;

import es.tecnocity.beans.Pedido;
import es.tecnocity.beans.Producto;
import es.tecnocity.beans.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Clases
 */
public interface IPedidosDAO {
    public void addPedido(Usuario usuario);
    public short getIdPedido(Usuario usuario);
    public void delPedido(short idPedido);
    public void chageEstado(short idPedido);
    public void setImporeIva(Pedido pedido);
    public ArrayList<Pedido> getPedidos(Usuario usario);
    public void closeConnection();
}
