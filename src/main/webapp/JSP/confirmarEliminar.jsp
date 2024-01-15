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
                        <div class="card w-50 m-auto">
                            <div class="card-header">
                                <h5 class="mb-0">¿Estas seguro que desea elimar el carrito?</h5>
                            </div>
                            <div class="card-body">
                                <!-- Agregar el botón Continuar aquí -->
                                <form action="CarritoController" method="post">
                                    <button type="submit" name="boton" value="cancelar" class="btn btn-primary">Cancelar</button>
                                    <button type="submit" name="boton" value="confirmar" class="btn btn-primary">Confirmar</button>
                                </form>
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

