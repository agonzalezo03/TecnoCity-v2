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
                        <div class="col">
                            <div class="card">


                                <div class="card-body p-4">

                                    <div class="row">

                                        <div class="col-lg-7">
                                            <div class="row">
                                                <c:set var="total" value="0"/>
                                                <c:forEach var="lpedido" items="${sessionScope.carrito.lineasPedido}" varStatus="loop">

                                                    <div class="card mb-3 producto" id="${lpedido.producto.idProducto}">
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
                                        <div class="col-lg-5">
                                            <div class="card rounded-3 formCarrito">
                                                <div class="card-body rounded-3 formCarrito">
                                                    <div class="d-flex justify-content-between align-items-center mb-4">
                                                        <h5 class="mb-0" style="color: white">
                                                            Detalles compra
                                                        </h5>
                                                    </div>

                                                    <c:if test="${not empty sesion}">
                                                        <form class="mt-4">
                                                            <div class="form-outline mb-4">
                                                                <input type="text" id="typeName" class="form-control form-control-lg" siez="17"
                                                                       placeholder="${sessionScope.sesion.direccion}" disabled />
                                                                <label class="form-label" for="typeName">Direccion</label>
                                                            </div>

                                                            <div class="form-outline form-white mb-4">
                                                                <input type="text" id="typeText" class="form-control form-control-lg" siez="17"
                                                                       placeholder="${sessionScope.sesion.nombre}"disabled  minlength="19" maxlength="19" />
                                                                <label class="form-label" for="typeText">Nombre</label>
                                                            </div>

                                                            <div class="row mb-4">
                                                                <div class="col-md-6">
                                                                    <div class="form-outline form-white">
                                                                        <input type="text" id="typeExp" class="form-control form-control-lg" placeholder="${sessionScope.sesion.codigoPostal}"
                                                                               size="7" id="exp" minlength="7" maxlength="7" disabled />
                                                                        <label class="form-label" for="typeExp">codigo postal</label>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-outline form-white">
                                                                        <input type="text" id="typeText" class="form-control form-control-lg"
                                                                               placeholder="${sessionScope.sesion.telefono}" disabled size="1" minlength="3" maxlength="3" />
                                                                        <label class="form-label" for="typeText">Numero telefono</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </form>
                                                    </c:if>
                                                    <c:if test="${empty sesion}">
                                                        <form action="FrontController" method="post">
                                                            <button type="submit" name="boton" value="registro" class="pagar">
                                                                Registrarse
                                                            </button>
                                                            <button type="submit" name="boton" value="login" class="pagar">
                                                                Login
                                                            </button>
                                                        </form>
                                                    </c:if>

                                                    <hr class="my-4" />

                                                    <div class="d-flex justify-content-between fila">
                                                        <p class="mb-2">Total</p>
                                                        <p class="mb-2" id="total"><fmt:formatNumber value="${total}" type="number" pattern="#,##0.00" /> €</p>
                                                    </div>

                                                    <div class="d-flex justify-content-between fila">
                                                        <p class="mb-2">Iva 21%</p>

                                                        <p class="mb-2" id="iva"><fmt:formatNumber value="${total * 0.21}" type="number" pattern="#,##0.00" /> €</p>
                                                    </div>
                                                    <form action="FinCompra" method="post">
                                                        <div class="d-flex justify-content-between mb-4 fila">
                                                            <p class="mb-2">Total(+IVA)</p>
                                                            <c:set var="total" value="${total + (total * 0.21)}"/>
                                                            <p class="mb-2" id="totalIva"><fmt:formatNumber value="${total}" type="number" pattern="#,##0.00" /> €</p>
                                                        </div>
                                                        <c:set var="disabled" value=""/>
                                                        <c:if test="${empty sesion || fn:length(sessionScope.carrito.lineasPedido) <= 0}">
                                                            <c:set var="disabled" value="disabled"/>
                                                        </c:if>

                                                    
                                                        <button type="submit" class="pagar" name="boton" value="volver" id="pagarBtn" ${disabled}>
                                                            <i class="fas fa-long-arrow-alt-left ms-2"></i>
                                                            Volver

                                                        </button>
                                                        <button type="submit" class="pagar" name="boton" value="pagar" id="pagarBtn" ${disabled}>
                                                            Finalizar Compra
                                                            <i class="fas fa-long-arrow-alt-right ms-2"></i>
                                                        </button>
                                                    </form>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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