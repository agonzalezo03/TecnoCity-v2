<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<header>
    <nav class="navbar navbar-expand-lg">
        <div class="container justify-content-center align-content-center">
            <a class="col-lg-1 col-md-12 col-sm-12 text-center" href="#"><img src="${appContext}IMG/logo.png" class="" style="width: 75%;" alt=""></a>
            <div class="collapse navbar-collapse col-lg-2 col-md-2 col-sm-2" id="navbarSupportedContent">
                <form action="FrontController" method="post">
                    <ul class="navbar-nav mb-2 mb-lg-0">
                        <li class="nav-item">
                            <button class="nav-link active" type="submit" name="boton" value="inicio">Inicio</button>
                        </li>

                        <li class="nav-item">
                            <button class="nav-link active" type="submit" name="boton" value="tienda">Tienda</button>
                        </li>     
                    </ul>
                </form>
            </div>


            <div class="d-flex form-search rounded-4 me-3 col-lg-6 col-md-6 col-9">
                <c:if test="${param.mostrar eq 'true'}">
                    <label class="m-auto p-2 rounded-2 "><i class="fa-solid fa-magnifying-glass" style="color: white;"></i></label>
                    <input class="form-control rounded-0 border-warning " type="search" placeholder="" id="buscador" aria-label="Search">
                    <button class="btn text-white " type="button">Search </button>
                </c:if>
            </div>
            <button class="navbar-toggler col-md-3 col-2 m-4" style="width: 50px; border-color: white;" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <i class="fa-solid fa-bars" style="color:white;"></i>
            </button>
            <ul class="navbar-nav mb-0 gap-2 col-lg-2 col-md-2 col-11 justify-content-md-end flex-row justify-content-center ">

                <li class="nav-item dropdown">
                    <a class="nav-link" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <c:if test="${not empty sesion}">
                        <img class="fotoPerfil rounded-5" src="<c:url value='/IMG/USERS/${sesion.avatar}'/>" alt="${sesion.avatar}" title="${sesion.avatar}" style="width: 40px;"/>
                        </c:if>
                        <c:if test="${empty sesion}">
                            <img class="fotoPerfil rounded-5" src="<c:url value='${appContext}IMG/USERS/default-user.jpg'/>" alt="avatar"/>
                        </c:if>
                    </a>
                    <ul class="dropdown-menu opcionesUsuario" aria-labelledby="navbarDropdown">
                        <form action="FrontController" method="post">

                            <c:if test="${empty sesion}">
                                <li class="dropdown-item"><button class="dropdown-item" type="submit" name="boton" value="login">Login</button></li>
                                <li class="dropdown-item"><button class="dropdown-item" type="submit" name="boton" value="registro">Registrarse</button></li>
                                </c:if>

                            <c:if test="${not empty sesion}">
                                <c:set var="fecha" value="Hoy"/>
                                <c:if test="${not empty sesion.ultimoAcceso}">
                                    <c:set var="fecha" value="${sesion.ultimoAcceso}"/>
                                </c:if>
                                <li class="dropdown-item disabled">Bienvenido</li>
                                <li class="dropdown-item disabled">${sesion.nombre}</li>
                                <li class="dropdown-item disabled">Ultimo acceso</li>
                                <li class="dropdown-item disabled"><fmt:formatDate value="${sesion.ultimoAcceso}" pattern="d 'de' MMMM 'de' yyyy" /></li>
                                <li class="dropdown-item"><button class="dropdown-item" type="submit" name="boton" value="perfil">Perfil</button></li>
                                <li class="dropdown-item"><button class="dropdown-item" type="submit" name="boton" value="pedidos">Pedidos</button></li>
                                <li class="dropdown-item"><button class="dropdown-item" type="submit" name="boton" value="salir">Salir</button></li>
                                </c:if>
                        </form>
                    </ul>
                </li>
                <form action="FrontController" method="post">
                    <li class="nav-item">

                        <button class="nav-link " type="submit" name="boton" value="carrito">
                            <i class="fa-solid fa-cart-shopping"></i>
                            <span class="badge text-bg-success" id="cantidadCarrito">4</span>
                        </button>

                    </li>
                </form>
            </ul>


        </div>
    </nav>
</header>
