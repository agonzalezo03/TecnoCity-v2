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
        <main class="pt-5 pb-5 w-100">

            <c:set var="id" value="${requestScope.producto}"/>
            <c:forEach var="producto" items="${applicationScope.productos}" varStatus="loop">
                <c:if test="${id eq producto.idProducto}">
                    <div class="detalle row w-100 p-5 ">
                        <img src="${appContext}IMG/productos/${producto.imagen}.jpg" class="col-10 col-lg-5">
                        <div class="contenido col-10 col-lg-5">
                            <h1>${producto.nombre}</h1>
                            <p>${producto.descripcion}</p>
                            <div class="precio row">
                                <h2 class="col-lg-5 col-md-5 col-12"><fmt:formatNumber value="${producto.precio}" type="number" pattern="#,##0.00" /> €</h2>
                                <form action="Comprar" method="post" class="col-lg-5 col-md-5 col-12">
                                    <button type="submit" name="id" value="${producto.idProducto}" class="comprar ">Añadir a la cesta</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>



            <p>${id}</p>

        </main>
        <jsp:include page="${appContext}/INC/footer.jsp">
            <jsp:param name="imagen" value="imagen1" />
        </jsp:include>

    </body>

</html>

