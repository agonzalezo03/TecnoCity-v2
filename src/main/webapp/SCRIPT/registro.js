const email = document.getElementById("email");
const password = document.getElementById("password");
const rePassword = document.getElementById("rePassword");
const boton = document.getElementById("boton");
const codigoPostal = document.getElementById("codigoPostal");

let nif = document.getElementById("nif");

let passwordError = true;
let rePasswordError = true;
let nifError = true;
let emailError = true;
let codigoPostalError = true;

password.addEventListener("blur", validarPassword);
rePassword.addEventListener("blur", validarRePassword);
email.addEventListener("blur", validarCorreo);
nif.addEventListener("blur", asignarLetraNIF);
codigoPostal.addEventListener("input", validarCodigoPostal);

validarCorreo();
asignarLetraNIF();
validarCodigoPostal();

validarFormulario();
function validarFormulario() {
    if (!passwordError && !rePasswordError && !nifError && !emailError && !codigoPostalError) {
        boton.classList.remove("notAvailable");
        boton.removeAttribute("disabled");
    } else {
        boton.classList.add("notAvailable");
        boton.setAttribute("disabled", "disabled");
    }
}


function validarPassword() {
    var nombreRegex = /^[a-zA-Z0-9\s]{1,100}$/;
    if (!password.value.match(nombreRegex)) {
        password.classList.add("error");
        password.classList.remove("bien");
        passwordError = true;
    } else {
        password.classList.remove("error");
        password.classList.add("bien");
        passwordError = false;
    }
    validarFormulario();
}

function validarRePassword() {
    if (password.value !== rePassword.value) {
        rePassword.classList.add("error");
        rePassword.classList.remove("bien");
        rePasswordError = true;
        mensajeError("Las contraseñas no coinciden");
    } else {
        rePassword.classList.remove("error");
        rePassword.classList.add("bien");
        rePasswordError = false;
    }
    validarPassword();
    validarFormulario();
}

function asignarLetraNIF() {
    let numero = nif.value;
    const regexNIF = /^[0-9]{8}/;
    const regexNIFCompleto = /^[0-9]{8}[A-Z]$/i;
    const letrasNIF = "TRWAGMYFPDXBNJZSQVHLCKE";
    console.log(numero.length);
    if (regexNIF.test(numero) && numero.length === 8) {
        console.log("hola");
        nif.classList.remove("error");
        nif.classList.add("bien");
        nifError = false;
        validarNIF();
    } else {
        if (!regexNIFCompleto.test(numero)) {
            nif.classList.add("error");
            nif.classList.remove("bien");
            nifError = true;
        } else {
            nif.classList.remove("error");
            nif.classList.add("bien");
            nifError = false;
        }


    }

    validarFormulario();


}

function validarCodigoPostal() {
    var regex = /^(?:0[1-9]|[1-4]\d|5[0-2])\d{3}$/;

    if (regex.test(codigoPostal.value)) {
        codigoPostal.classList.remove("error");
        codigoPostal.classList.add("bien");
        codigoPostalError = false;
    } else {
        codigoPostal.classList.add("error");
        codigoPostal.classList.remove("bien");
        codigoPostalError = true;
    }
    validarFormulario();
}

function validarCorreo() {

    var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (regex.test(email.value)) {
        email.classList.remove("error");
        email.classList.add("bien");


        emailError = false;
        comprobarCorreo();
    } else {
        email.classList.add("error");
        email.classList.remove("bien");
        emailError = true;
    }
}

function comprobarCorreo() {


    var xhr = new XMLHttpRequest();
    xhr.open("POST", //Por POST
            "RegistroAyax", //Nombre del Servlet
            true);

    xhr.setRequestHeader("Content-Type",
            "application/json");

    // Enviar datos al servidor como cadena JSON
    var datos = JSON.stringify({
        accion: "correo",
        valor: `${email.value}`
    });
    xhr.send(datos);

//---------------------------------------------------------------

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {

                // Respuesta del servidor
                var respuesta = JSON.parse(xhr.responseText);
                console.log(respuesta);
                email.classList.remove("error");
                email.classList.add("bien");
                emailError = false;
                respuesta.forEach(function (usuario) {
                    // Acceder a las propiedades de cada objeto
                    console.log(usuario.email);
                    console.log(email.value);
                    if (usuario.email === email.value) {
                        emailError = true;
                        email.classList.add("error");
                        email.classList.remove("bien");
                        mensajeError("El correo ya existe");
                    }

                });


            } else {
                alert("Error en la solicitud AJAX");
            }
        }
    };
    validarFormulario();
}

function validarNIF() {

    console.log("adios");
    var xhr = new XMLHttpRequest();
    xhr.open("POST", //Por POST
            "RegistroAyax", //Nombre del Servlet
            true);

    xhr.setRequestHeader("Content-Type",
            "application/json");

    // Enviar datos al servidor como cadena JSON
    var datos = JSON.stringify({
        accion: "nif",
        valor: `${nif.value}`

    });
    xhr.send(datos);

//---------------------------------------------------------------

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log("adios");
                // Respuesta del servidor
                var respuesta = JSON.parse(xhr.responseText);
                console.log(respuesta);
                nif.value = respuesta;



            } else {
                alert("Error en la solicitud AJAX");
            }
        }
    };
    validarFormulario();
}

function mensajeError(mensaje) {
    let body = document.getElementsByTagName("body")[0];
    let span = document.createElement("span");
    span.classList.add('mensaje');
    span.innerText = `
    ${mensaje}
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

