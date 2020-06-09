/*ARCHIVO FUNCIONES JAVASCRIPT*/
//Método para Anadir manejadores de funciones en los media query e identificar si estamos en responsive o no
function cargarManejadorDeResolucion() {
    try {
        // Mediaquery destinado al responsive (Resolución máxima de 839px)
        var mediaqueryList = window.matchMedia("(max-width: 839px)");
        function manejador(EventoMediaQueryList) {
            if (EventoMediaQueryList.matches) {
                localStorage.setItem('responsive', true);
            } else {
                localStorage.setItem('responsive', false);
            }
        }

        // Asociamos el manejador de evento
        mediaqueryList.addListener(manejador);
    } catch (error) {
        console.log(error);
    }
}

// --------------------------------------------------------
// -- Funciones para carousel flickity --
function cargarCarousels() {
    try {
        var carouselFli = $('.carouselFL');
        if (typeof carouselFli !== 'undefined' && carouselFli !== 'null' && carouselFli !== null && carouselFli.length > 0) {
            $('.details > .p-grid').css("display", "block");
            $('.carouselFL').flickity({
                cellAlign: 'left',
                freeScroll: false,
                contain: true,
                prevNextButtons: true,
                pageDots: false,
                wrapAround: true
            });
            $('.carouselFL').flickity('resize');
        }
    } catch (error) {
        console.log(error);
    }
}
// -- Funciones para carousel flickity --
// --------------------------------------------------------