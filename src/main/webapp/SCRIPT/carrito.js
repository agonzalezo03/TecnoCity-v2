

const botones = document.querySelectorAll(".accion");
const cards = document.querySelectorAll(".producto");
const total = document.getElementById("total");
const iva = document.getElementById("iva");
const totalIva = document.getElementById("totalIva");
const pagarbtn = document.getElementById("pagarBtn");

disabled();

function disabled() {
    cards.forEach(producto => {
        let cantidad = producto.querySelector(".cantidad");
        let menosBtn = producto.querySelector("#menos");

        if (menosBtn) {
            if (parseInt(cantidad.innerText) === 1) {
                menosBtn.setAttribute("disabled", "true");

            } else {
                menosBtn.removeAttribute("disabled");
            }
        } else {
            console.error("No se encontró el botón menos dentro del producto con id " + producto.id);
        }
    });
}

botones.forEach(event => {
    console.log(event.innerText);
    event.addEventListener("click", () => {
        accionesCarrito(event.id, event.value);
        console.log(event.value);
        var div;
        let cantidad;
        let totalProducto;
        let precioProducto;
        switch (event.id) {
            case"mas":
                div = document.getElementById(`${event.value}`);
                cantidad = div.querySelector(`.cantidad`);
                totalProducto = div.querySelector(`.totalProducto`);
                precioProducto = div.querySelector(`.precioProducto`);
                console.log(cantidad);
                cantidad.innerText = `${parseInt(cantidad.innerText) + 1}`;
                totalProducto.innerText = `${formatter.format(parseInt(precioProducto.innerText) * parseInt(cantidad.innerText))}`;
                console.log(parseInt(precioProducto.innerText) * parseInt(cantidad.innerText));

                break;
            case"menos":
                div = document.getElementById(`${event.value}`);
                cantidad = div.querySelector(`.cantidad`);
                totalProducto = div.querySelector(`.totalProducto`);
                precioProducto = div.querySelector(`.precioProducto`);
                console.log(cantidad);
                cantidad.innerText = `${parseInt(cantidad.innerText) - 1}`;
                totalProducto.innerText = `${formatter.format(parseInt(precioProducto.innerText) * parseInt(cantidad.innerText))}`;
                break;
            case"eliminar":
                div = document.getElementById(`${event.value}`);
                div.style.display = "none";
                
                let cantidadActual = parseInt(cantidadCarritoSpan.innerText);
                cantidadActual = Math.max(0, cantidadActual - 1); 
                console.log(cantidadActual);
                cantidadCarritoSpan.innerText = cantidadActual.toString();
                
                break;
        }
        disabled();






    });
});

function accionesCarrito(accion, id) {

    console.log("llega" + id);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", //Por POST
            "CarritoAyax", //Nombre del Servlet
            true);

    xhr.setRequestHeader("Content-Type",
            "application/json");

    // Enviar datos al servidor como cadena JSON
    var datos = JSON.stringify({
        accion: `${accion}`,
        idProducto: `${id}`

    });
    xhr.send(datos);

//---------------------------------------------------------------

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Respuesta del servidor
                var respuesta = JSON.parse(xhr.responseText);
                console.log(respuesta);
                total.innerText = `${formatter.format(respuesta.importe)}`;
                iva.innerText = `${formatter.format(respuesta.iva)}`;
                totalIva.innerText = `${formatter.format(respuesta.importe + respuesta.iva)}`;
                if (respuesta.importe === 0) {
                    pagarbtn.setAttribute("disabled", "true");
                }



            } else {
                alert("Error en la solicitud AJAX");
            }
        }
    };
}




var formatter = new Intl.NumberFormat('es-ES', {
    style: 'currency',
    currency: 'EUR',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
});





