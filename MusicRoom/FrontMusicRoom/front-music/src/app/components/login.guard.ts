import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { DataObjects } from './ObjectGeneric';
import { JwtHelperService } from '@auth0/angular-jwt';

declare var $: any;

@Injectable()
export class LoginGuard implements CanActivate {
  usuario: any;
  const: any;

  constructor(private router: Router, datasObject: DataObjects) {
    this.const = datasObject.getConst();
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    let URLactual = window.location.href;
    const helper = new JwtHelperService();

    let sesionOK = true;
    if (!URLactual.includes("music-room/home") && !URLactual.includes("music-room/error") && URLactual !== this.const.urlDomain) {
      let usuarioSesion = localStorage.getItem('usuarioSesion') === null ? null : JSON.parse(localStorage.getItem('usuarioSesion').toString());
      let ACCESS_TOKEN = sessionStorage.getItem(this.const.tokenNameAUTH) === null ? null : JSON.parse(sessionStorage.getItem(this.const.tokenNameAUTH));

      if (usuarioSesion !== null && ACCESS_TOKEN !== null) {
        const decodedToken = helper.decodeToken(ACCESS_TOKEN.access_token);
        const expirationDate = helper.getTokenExpirationDate(ACCESS_TOKEN.access_token);
        const isExpired = helper.isTokenExpired(ACCESS_TOKEN.access_token);

        if (!isExpired) {
          let tienePermisos = false;

          if (usuarioSesion.listaRoles !== undefined && usuarioSesion.listaRoles !== null) {
            for (let i in usuarioSesion.listaRoles) {
              let rolUsuario = usuarioSesion.listaRoles[i];

              if (URLactual.includes(rolUsuario.path) || rolUsuario.codigo === this.const.codigoADMIN) {
                tienePermisos = true;
                break;
              }
            }
          }
          
          if (!tienePermisos) {
            localStorage.setItem('decodedToken', JSON.stringify(decodedToken));
            localStorage.setItem('expirationDate', JSON.stringify(expirationDate));
            localStorage.setItem('mensajeError403', 'Está intentando ingresar a la ruta: ' + URLactual + '. No cuenta con permisos para visualizar su contenido.');
            this.router.navigate(['/music-room/error403']);
          }
        }
        else {
          localStorage.setItem('decodedToken', JSON.stringify(decodedToken));
          localStorage.setItem('expirationDate', JSON.stringify(expirationDate));
          localStorage.setItem('mensajeError403', 'Su sesión ha expirado. Debe ingresar de nuevo a la aplicación.');
          this.router.navigate(['/music-room/error403']);
        }
      }
      else {
        localStorage.setItem('mensajeError500', 'No tiene una Sesión Iniciada.');
        this.router.navigate(['/music-room/error500']);
      }
    }

    return true;
  }
}