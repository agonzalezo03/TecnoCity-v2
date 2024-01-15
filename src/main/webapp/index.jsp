<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <main class="pt-5 pb-5 ">
            <div id="carouselExampleAutoplaying" class="carousel slide m-auto w-75 rounded-2 overflow-hidden m-auto " style=" height: 300px;" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <div class="d-flex flex-column justify-content-center align-items-center">
                            <h1>Bienvenido a TecnoCity ${sessionScope.sesion.nombre}</h1>
                            <p>¡Bienvenido a nuestra tienda de informática! Explora las últimas innovaciones tecnológicas y encuentra soluciones perfectas para potenciar tu experiencia digital. Descubre productos de calidad, asesoramiento experto y todo lo que necesitas para estar a la vanguardia en el mundo tecnológico.</p>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <div class="d-flex flex-column justify-content-center align-items-center">
                            <h1>Mantente informado de las mejores ofertas</h1>
                            <form action="#">
                                <div class="input-group mb-3">
                                    <input class="form-control" type="text" placeholder="example@example.com" aria-label="Recipient's username" aria-describedby="button-addon2">
                                    <button class="btn btn-primary" id="button-addon2" type="button"><i class="fas fa-paper-plane"></i></button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <div class="d-flex flex-column justify-content-center align-items-center">
                            <h1>Siguenos en nuestras redes sociales</h1>
                            <ul class="list-unstyled text-white-50 contacto d-flex gap-5">
                                <li><a href="#"><i class="fa-brands fa-square-x-twitter"></i></a></li>
                                <li><a href="#"><i class="fa-brands fa-facebook"></i></a></li>
                                <li><a href="#"><i class="fa-brands fa-square-instagram"></i></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row justify-content-center align-items-center mt-5 w-100">
                <div class="row gap-3 justify-content-center align-items-center flex-wrap">

                    <c:forEach var="producto" items="${applicationScope.productos}" varStatus="loop">

                        <div class="product col-lg-2 col-md-4 col-10">

                            <div class="imgBox">
                                <img src="${appContext}IMG/productos/${producto.imagen}.jpg" alt="ImagenProducto" class="imgProduct">
                            </div>

                            <div class="contentBox">
                                <h3>${producto.nombre}</h3>
                                <h2 class="price"><fmt:formatNumber value="${producto.precio}" type="number" pattern="#,##0.00" /> €</h2>
                                <form action="FrontController" method="post">
                                    <button type="submit" name="detalle" value="${producto.idProducto}" class="buy">Comprar</button>
                                </form>
                            </div>         
                        </div>

                    </c:forEach>

                </div>
            </div>
        </main>
        <jsp:include page="${appContext}/INC/footer.jsp">
            <jsp:param name="imagen" value="imagen1" />
        </jsp:include>

    </body>

</html>
