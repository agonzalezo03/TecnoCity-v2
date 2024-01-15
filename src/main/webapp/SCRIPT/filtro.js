const categorias = document.querySelectorAll(".categoria");
const productos = document.querySelectorAll(".product");
let productosObtenidos = [];
let categoriasSeleccionadas = [];
let divProductos = document.getElementById("mostrarProductos");

categorias.forEach(categoria => {
    categoria.addEventListener("click", () => {

        const categoriaSeleccionada = categoria.id;


        const indice = categoriasSeleccionadas.indexOf(categoriaSeleccionada);

        if (indice !== -1) {
            // Si está seleccionada, quitarla de las categorías seleccionadas
            categoriasSeleccionadas.splice(indice, 1);
            categoria.classList.remove("catSelec");
        } else {
            // Si no está seleccionada, agregarla a las categorías seleccionadas
            categoriasSeleccionadas.push(categoriaSeleccionada);
            categoria.classList.add("catSelec");
        }

        // Ocultar todos los productos
        productos.forEach(producto => {
            producto.style.display = "none";
        });

        console.log(categoriasSeleccionadas.length);
        

        
            obtenerProductosCategoria(categoriasSeleccionadas);
        
    });
});

function obtenerProductosCategoria(idCategoria) {


    var xhr = new XMLHttpRequest();
    xhr.open("POST", //Por POST
            "FiltrosTiendaAyax", //Nombre del Servlet
            true);

    xhr.setRequestHeader("Content-Type",
            "application/json");

    // Enviar datos al servidor como cadena JSON
    var datos = JSON.stringify({
        categorias: `${idCategoria}`
    });
    xhr.send(datos);

//---------------------------------------------------------------

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {

                // Respuesta del servidor
                var respuesta = JSON.parse(xhr.responseText);
                productosObtenidos = respuesta;
                mostrarProductos();


            } else {
                alert("Error en la solicitud AJAX");
            }
        }
    };
}

function mostrarProductos() {
    // Limpiar los productos anteriores
    var formatter = new Intl.NumberFormat('es-ES', {
        style: 'currency',
        currency: 'EUR',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
    divProductos.innerHTML = "";

    productosObtenidos.forEach(producto => {   
        let div = document.createElement("div");
        div.classList.add("product", "col-lg-3", "col-md-3", "col-sm-8", "col-8", `categoria${producto.idProducto}`);
        div.innerHTML = `
            <div class="imgBox">
                <img src="${appContext}IMG/productos/${producto.imagen}.jpg" alt="ImagenProducto" class="imgProduct">
            </div>
            <div class="contentBox">
                <h3>${producto.nombre}</h3>
                <h2 class="price">${formatter.format(producto.precio)}</h2>
                <form action="FrontController" method="post">
                    <button type="submit" name="detalle" value="${producto.idProducto}" class="buy">Comprar</button>
                </form>
            </div>`;
        divProductos.appendChild(div);
    });
}


function mensajeError() {
    let body = document.getElementsByTagName("body")[0];
    let span = document.createElement("span");
    span.classList.add('mensaje');
    span.innerText = `
    Esta categoria no tiene productos
  `;
    body.appendChild(span);

    setTimeout(() => {
        span.classList.add('mostrar');
    }, 10);
    setTimeout(() => {
        span.classList.remove('mostrar');
        setTimeout(() => {
            span.remove();
        }, 1000);
    }, 3000);
}



