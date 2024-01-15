/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.tecnocity.dao;

import es.tecnocity.beans.Usuario;
import java.util.List;

/**
 *
 * @author Clases
 */
public interface IUsuariosDAO {
    public boolean addUsuario(Usuario usuario);
    public List<Usuario> getCorreos();
    public Usuario login(Usuario usuario);
    public void update(Usuario usuario);
    public void setUltAcceso(Usuario usuario);
    public short getLastInsert();
    public void closeConnection();
}
