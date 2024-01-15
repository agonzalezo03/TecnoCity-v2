const productos2 = document.querySelectorAll(".product");
let buscador = document.getElementById("buscador");

buscador.addEventListener("input", event =>{
    
    var busqueda =+ event.data || event.target.value;
    console.log(busqueda);
    productos2.forEach(producto => {
      producto.style.display = "none";
    });
    
    productos2.forEach(producto => {
 
        let nombreProducto = producto.querySelector("h3");
        
        if (String(nombreProducto.textContent).toLowerCase().includes(String(busqueda))) {
            console.log(busqueda);
            producto.style.display = "block";
        }
    });
});


