<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html lang="es">

    <head>
        <c:set var="appContext" value="${request.getContextPath}" scope="page" />
        <title>TecnoCity</title>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="${appContext}CSS/style.css">
        <script src="${appContext}SCRIPT/cantidadCarrito.js" defer></script>
        <script src="${appContext}SCRIPT/carrito.js" defer></script>
        <script src="${appContext}SCRIPT/busqueda.js" defer></script>

        <script src="https://kit.fontawesome.com/c9f5871d83.js" crossorigin="anonymous"></script>
        <!-- Bootstrap CSS v5.2.1 -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">

    </head>

    <body>
        <jsp:include page="${appContext}/INC/header.jsp">
            <jsp:param name="logeado" value="${sessionScope.sesion}" />
            <jsp:param name="mostrar" value="false" />
        </jsp:include>
        <main class="pt-5 pb-5 w-100 d-flex ">
            <section class="h-100 w-100 h-custom">
                <div class="container py-5 h-100">
                    <div class="row d-flex justify-content-center align-items-center h-100">
                        <div class="card">

                            <div class="card-header">
                                <h5 class="mb-0">Mis Pedidos</h5>
                            </div>


                            <div class="card-body p-4">
                                <div class="row">

                                    <div class="col-lg-12">
                                        <div class="row">
                                            <c:set var="total" value="0"/>
                                            <c:forEach var="pedido" items="${requestScope.pedidos}" varStatus="loop">
                                                <div class="card row mb-3">
                                                    <div class="card-header">
                                                        <h5 class="mb-0">ID Pedido: ${pedido.idPedido} - Fecha: ${pedido.fecha}</h5>
                                                    </div>
                                                    <div class="card-body">
                                                        <div class="row">
                                                            <c:forEach var="lpedido" items="${pedido.lineasPedido}" varStatus="loop">
                                                                <div class="card mb-3 producto col-lg-6 col-md-12 col-sm-12" id="${lpedido.producto.idProducto}">
                                                                    <div class="card-body d-flex row text-center ">
                                                                        <img
                                                                            src="${appContext}IMG/productos/${lpedido.producto.imagen}.jpg"
                                                                            class="img-fluid rounded-3 col-12 col-md-3" alt="Shopping item" />
                                                                        <p class="card-title col-12 col-md-3">
                                                                            ${lpedido.producto.nombre}
                                                                        </p>
                                                                        <div class="col-6 col-md-3 ">
                                                                            <h6>Unds</h6>
                                                                            <h5 class="cantidad">${lpedido.cantidad}</h5>
                                                                        </div>

                                                                        <div class="col-6 col-md-3">
                                                                            <h6>Precio Und:</h6>
                                                                            <h5 class="precioProducto"><fmt:formatNumber value="${lpedido.producto.precio}" type="number" pattern="#,##0.00" /> €</h5>
                                                                        </div>
                                                                        <c:set var="total" value="${(lpedido.producto.precio * lpedido.cantidad) + total}"/>


                                                                        <div class="col-6 col-md-3">
                                                                            <h6>Total:</h6>
                                                                            <h5 class="totalProducto"><fmt:formatNumber value="${lpedido.producto.precio * lpedido.cantidad}" type="number" pattern="#,##0.00" /> €</h5>

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                    <div class="card-footer">
                                                        <h5 class="mb-0 text-end">Importe <fmt:formatNumber value="${pedido.importe}" type="number" pattern="#,##0.00" /> € - 
                                                            IVA(21%): <fmt:formatNumber value="${pedido.iva}" type="number" pattern="#,##0.00" /> € - 
                                                            TOTAL: <strong><fmt:formatNumber value="${pedido.importe + pedido.iva}" type="number" pattern="#,##0.00" /> €</strong></h5>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            </section>
                            </main>
                            <jsp:include page="${appContext}/INC/footer.jsp">
                                <jsp:param name="imagen" value="imagen1" />
                            </jsp:include>

                            </body>

                            </html>