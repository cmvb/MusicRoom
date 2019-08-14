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
    let LOCALHOST = 'http://localhost:4200/';

    let URLactual = window.location.href;
    const helper = new JwtHelperService();

    let sesionOK = true;
    
    if (!URLactual.includes("register") && !URLactual.includes("home") && !URLactual.includes("error") && URLactual.toString() !== (this.const.urlDomain) && URLactual.toString() !== (LOCALHOST)) {
      let usuarioSesion = localStorage.getItem('usuarioSesion') === null ? null : JSON.parse(localStorage.getItem('usuarioSesion').toString());
      let ACCESS_TOKEN = sessionStorage.getItem(this.const.tokenNameAUTH) === null ? null : JSON.parse(sessionStorage.getItem(this.const.tokenNameAUTH));

      if (usuarioSesion !== null && ACCESS_TOKEN !== null) {
        const decodedToken = helper.decodeToken(ACCESS_TOKEN.access_token);
        const expirationDate = helper.getTokenExpirationDate(ACCESS_TOKEN.access_token);
        const isExpired = helper.isTokenExpired(ACCESS_TOKEN.access_token);

        if (!isExpired) {
          let tienePermisos = false;
          
          if (usuarioSesion.usuarioTb !== undefined && usuarioSesion.usuarioTb !== null && usuarioSesion.usuarioTb.listaRoles !== undefined && usuarioSesion.usuarioTb.listaRoles !== null) {
            for (let i in usuarioSesion.usuarioTb.listaRoles) {
              let rolUsuario = usuarioSesion.usuarioTb.listaRoles[i];

              if (!URLactual.includes("dashboard")) {
                if (URLactual.includes(rolUsuario.path) || rolUsuario.codigo === this.const.codigoADMIN) {
                  tienePermisos = true;
                  break;
                }
              }
              else {
                tienePermisos = true;
                break;
              }
            }
          }

          if (!tienePermisos) {
            localStorage.setItem('decodedToken', JSON.stringify(decodedToken));
            localStorage.setItem('expirationDate', JSON.stringify(expirationDate));
            localStorage.setItem('mensajeError403', 'Está intentando ingresar a la ruta: ' + URLactual + '. No cuenta con permisos para visualizar su contenido.');
            this.router.navigate(['/error403']);
          }
        }
        else {
          localStorage.setItem('decodedToken', JSON.stringify(decodedToken));
          localStorage.setItem('expirationDate', JSON.stringify(expirationDate));
          localStorage.setItem('mensajeError403', 'Su sesión ha expirado. Debe ingresar de nuevo a la aplicación.');
          this.router.navigate(['/error403']);
        }
      }
      else {
        localStorage.setItem('mensajeError500', 'No tiene una Sesión Iniciada.');
        this.router.navigate(['/error500']);
      }
    }

    return true;
  }
}