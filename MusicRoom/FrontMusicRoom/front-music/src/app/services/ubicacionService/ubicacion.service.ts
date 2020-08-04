import { Injectable } from '@angular/core';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';

@Injectable({
  providedIn: 'root'
})
export class UbicacionService {
  // Datos
  objetoFiltro: any;
  listaConsulta: any;
  editParam: any;
  listaCiudades: any;
  listaDepartamentos: any;
  listaPaises: any;

  constructor(public objectModelInitializer: ObjectModelInitializer) {
  }

  inicializar() {
  }

}
