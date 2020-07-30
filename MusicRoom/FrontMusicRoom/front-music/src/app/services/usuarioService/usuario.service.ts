import { Injectable } from '@angular/core';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  // Datos
  editParam: any;
  listaConsulta: any[];
  objetoFiltro: any;

  constructor(public objectModelInitializer: ObjectModelInitializer) {
  }

  inicializar() {
  }

}
