import { Injectable } from '@angular/core';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';

@Injectable({
  providedIn: 'root'
})
export class SalaService {
  // Datos
  objetoFiltro: any;
  listaConsulta: any;
  editParam: any;
  listaTerceros: any;

  constructor(public objectModelInitializer: ObjectModelInitializer) {
  }

  inicializar() {
  }

}
