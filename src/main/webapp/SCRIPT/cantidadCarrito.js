let cantidadCarritoSpan = document.getElementById("cantidadCarrito");

cantidadCarrito();

function cantidadCarrito() {

    var xhr = new XMLHttpRequest();
    xhr.open("POST", //Por POST
            "HeaderAyax", //Nombre del Servlet
            true);

    xhr.setRequestHeader("Content-Type",
            "application/json");

    // Enviar datos al servidor como cadena JSON
    var datos = JSON.stringify({
        
        
    });
    xhr.send(datos);

//---------------------------------------------------------------

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Respuesta del servidor
                var respuesta = JSON.parse(xhr.responseText);
                console.log(respuesta);
                cantidadCarritoSpan.innerText = `${respuesta}`;
                


            } else {
                alert("Error en la solicitud AJAX");
            }
        }
    };
}


