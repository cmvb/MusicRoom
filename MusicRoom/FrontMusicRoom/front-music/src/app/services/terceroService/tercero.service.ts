import { Injectable } from '@angular/core';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';

@Injectable({
  providedIn: 'root'
})
export class TerceroService {
  // Datos
  objetoFiltro: any;
  listaConsulta: any;
  editParam: any;
  listaCiudades: any;

  constructor(public objectModelInitializer: ObjectModelInitializer) {
  }

  inicializar() {
  }

}
