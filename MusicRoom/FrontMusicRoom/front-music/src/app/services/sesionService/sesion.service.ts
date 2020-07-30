import { Injectable } from '@angular/core';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';

@Injectable({
  providedIn: 'root'
})
export class SesionService {
  // Fases
  phase: any;

  // Datos
  usuarioSesion: any;
  usuarioRegister: any;
  tokenSesion: any;
  decodedToken: any;
  expirationDate: any;
  idioma: 1;

  // Excepciones
  mensajeError403: any;
  mensajeError404: any;
  mensajeError500: any;

  // Mensajes
  mensajeConfirmacion: any;

  constructor(public objectModelInitializer: ObjectModelInitializer) {
  }

  inicializar() {
    this.phase = undefined;
    this.usuarioSesion = undefined;
    this.usuarioRegister = undefined;
    this.tokenSesion = undefined;
    this.usuarioSesion = undefined;
    this.decodedToken = undefined;
    this.expirationDate = undefined;
    this.mensajeError403 = undefined;
    this.mensajeError404 = undefined;
    this.mensajeError500 = undefined;
    this.mensajeConfirmacion = undefined;
  }

  getUsuarioSesionActual() {
    let result = null;
    if (typeof this.usuarioSesion !== 'undefined' && this.usuarioSesion !== null && this.usuarioSesion !== 'null') {
      result = this.usuarioSesion;
    }
    return result;
  }

  isSesionActiva() {

  }

  tienePermisos(URLactual: String) {
    let resultTienePermisos = false;
    if (this.usuarioSesion.usuarioTb !== undefined && this.usuarioSesion.usuarioTb !== null && this.usuarioSesion.usuarioTb.listaRoles !== undefined && this.usuarioSesion.usuarioTb.listaRoles !== null) {
      for (let i in this.usuarioSesion.usuarioTb.listaRoles) {
        let rolUsuario = this.usuarioSesion.usuarioTb.listaRoles[i];

        if (!URLactual.includes("dashboard")) {
          if (URLactual.includes(rolUsuario.path) || rolUsuario.codigo === this.objectModelInitializer.getConst().codigoADMIN) {
            resultTienePermisos = true;
            break;
          }
        }
        else {
          resultTienePermisos = true;
          break;
        }
      }
    }

    return resultTienePermisos;
  }
}
