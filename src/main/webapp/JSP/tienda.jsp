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
        <script>
            var appContext = "${appContext}";
        </script>
        <script src="${appContext}SCRIPT/cantidadCarrito.js" defer></script>
        <script src="${appContext}SCRIPT/filtro.js" defer></script>
        <script src="${appContext}SCRIPT/busqueda.js" defer></script>
        
        <script src="https://kit.fontawesome.com/c9f5871d83.js" crossorigin="anonymous"></script>
        <!-- Bootstrap CSS v5.2.1 -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">

    </head>

    <body>
        <jsp:include page="${appContext}/INC/header.jsp">
            <jsp:param name="logeado" value="${sessionScope.sesion}" />
            <jsp:param name="mostrar" value="true" />
        </jsp:include>
        <main class="pt-5 pb-5 d-flex ">
            <div class="row w-100">
                <div class="filtros col-12 col-lg-2 col-md-2 ">
                    <h5 class="text-center" style="color: white;">Filtros</h5>
                    <ul class="filtros overflow-y-auto overflow-x-hidden">
                        <c:forEach var="categoria" items="${applicationScope.categorias}" varStatus="loop">
                            <li class="d-flex p-3 categoria" id="${categoria.idCategoria}"><img src="${appContext}IMG/categorias/${categoria.imagen}" alt="" width="25%"/>
                                ${categoria.nombre}</li>
                            </c:forEach>
                    </ul>
                </div> 
                <div class="row justify-content-center align-items-center mt-5 col-12 col-lg-10 col-md-10">
                    <div class="row gap-3 justify-content-center align-items-center" id="mostrarProductos">

                        <c:forEach var="producto" items="${applicationScope.productos}" varStatus="loop">
                            <div class="product col-lg-3 col-md-3 col-sm-8 col-8 categoria${producto.idCategoria}">

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
            </div>
        </main>
        <jsp:include page="${appContext}/INC/footer.jsp">
            <jsp:param name="imagen" value="imagen1" />
        </jsp:include>

    </body>

</html>
