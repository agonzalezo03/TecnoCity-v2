<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="es">
    <head>
        <title>Perfil</title>
        <!-- Required meta tags -->
        <meta charset="utf-8" />
        <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
        />
        <link rel="stylesheet" href="${appContext}CSS/style.css">
        <link rel="stylesheet" href="${appContext}CSS/formulario.css">
        <script src="${appContext}SCRIPT/cantidadCarrito.js" defer></script>
        <script src="${appContext}SCRIPT/registro.js" defer></script>
        
        <script src="https://kit.fontawesome.com/c9f5871d83.js" crossorigin="anonymous"></script>
        <!-- Bootstrap CSS v5.2.1 -->
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
            crossorigin="anonymous"
        />
    </head>

    <body>
        <jsp:include page="${appContext}/INC/header.jsp">
            <jsp:param name="logeado" value="${sessionScope.sesion}" />
            <jsp:param name="mostrar" value="false" />
        </jsp:include>
        <main>
            <form action="Registro" method="post" class="registro" enctype="multipart/form-data">
                <h1 style="border-bottom: black solid 1px;">Editar perfil</h1>
                <p>${requestScope.mensaje}</p>
                
                <label for="">Correo</label>
                <input type="email" name="email" id="email" value="${sessionScope.sesion.email}" readonly>

                <label for="">Contraseña antigua*</label>
                <input type="password" name="password" id="opassword">

                <label for="">Contraseña nueva</label>
                <input type="password" name="newPassword" id="Password">
                
                <label for="">Repite contraseña nueva</label>
                <input type="password" name="newrePassword" id="rePassword">

                <label for="">Nombre*</label>
                <input type="text" name="nombre" value="${sessionScope.sesion.nombre}">

                <label for="">Apellidos*</label>
                <input type="text" name="apellidos" value="${sessionScope.sesion.apellidos}">

                <label for="">NIF</label>
                <input type="text" name="nif" id="nif" maxlength="9" value="${sessionScope.sesion.nif}" readonly>

                <label for="">Telefono</label>
                <input type="text" name="telefono" value="${sessionScope.sesion.telefono}">

                <label for="">Dirección*</label>
                <input type="text" name="direccion" value="${sessionScope.sesion.direccion}">

                <label for="">Código postal*</label>
                <input type="text" name="codigoPostal" value="${sessionScope.sesion.codigoPostal}">

                <label for="">Localidad*</label>
                <input type="text" name="localidad" value="${sessionScope.sesion.localidad}">

                <label for="">Provincia*</label>
                <input type="text" name="provincia" value="${sessionScope.sesion.provincia}">

                <label for="">Foto</label>
                <input type="file" name="avatar">

                <input type="submit" name="boton" value="Editar" id="boton">
            </form>
        </main>
        <jsp:include page="${appContext}/INC/footer.jsp">
            <jsp:param name="imagen" value="imagen1" />
  </jsp:include>
        <!-- Bootstrap JavaScript Libraries -->
        <script
            src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"
        ></script>

        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
            integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
            crossorigin="anonymous"
        ></script>
    </body>
</html>
