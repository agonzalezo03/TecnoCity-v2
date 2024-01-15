<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="es">
    <head>
        <title>Registro</title>
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
            <jsp:param name="mostar" value="false" />
        </jsp:include>
        <main>
            <form action="Registro" method="post" class="registro" enctype="multipart/form-data">
                <h1 style="border-bottom: black solid 1px;">Formulario Registro</h1>
                <p>${requestScope.mensaje}</p>
                
                <label for="">Correo*</label>
                <input type="email" name="email" id="email" value="${param.email}">

                <label for="">Contraseña*</label>
                <input type="password" name="password" id="password">

                <label for="">Repite la contraseña*</label>
                <input type="password" name="rePassword" id="rePassword">

                <label for="">Nombre*</label>
                <input type="text" name="nombre" value="${param.nombre}">

                <label for="">Apellidos*</label>
                <input type="text" name="apellidos" value="${param.apellidos}">

                <label for="">NIF*</label>
                <input type="text" name="nif" id="nif" maxlength="8" value="${param.nif}">

                <label for="">Telefono</label>
                <input type="text" name="telefono" value="${param.telefono}">

                <label for="">Dirección*</label>
                <input type="text" name="direccion" value="${param.direccion}">

                <label for="">Código postal*</label>
                <input type="text" name="codigoPostal" maxlength="5" id="codigoPostal" value="${param.codigoPostal}">

                <label for="">Localidad*</label>
                <input type="text" name="localidad" value="${param.localidad}">

                <label for="">Provincia*</label>
                <input type="text" name="provincia" value="${param.povincia}">

                <label for="">Foto</label>
                <input type="file" name="avatar">

                <input type="submit" name="boton" value="Registrarse" id="boton">
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

