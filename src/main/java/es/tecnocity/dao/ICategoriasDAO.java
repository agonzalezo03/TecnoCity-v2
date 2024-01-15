/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.tecnocity.dao;

import es.tecnocity.beans.Categoria;
import java.util.List;

/**
 *
 * @author Clases
 */
public interface ICategoriasDAO {
    public List<Categoria> getCategorias();
    public void closeConnection();
}
