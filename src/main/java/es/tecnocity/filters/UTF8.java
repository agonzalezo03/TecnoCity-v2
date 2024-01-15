/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.tecnocity.filters;

/**
 *
 * @author Clases
 */

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "UTF8", urlPatterns = {"/*"})
public class UTF8 implements Filter {

    private String encoding;

    /**
     *
     * @param fConfig Parámetro inicial
     * @throws ServletException Excepción
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        encoding = fConfig.getInitParameter("requestEncoding");
        if (encoding == null) {
            encoding = "UTF-8";
        }
    }

    /**
     *
     * @param request Petición
     * @param response Respuesta
     * @param chain Cambio
     * @throws IOException Excepción de entrada/salida
     * @throws ServletException Excepción
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    /**
     *
     */
    @Override
    public void destroy() {
    }
}
