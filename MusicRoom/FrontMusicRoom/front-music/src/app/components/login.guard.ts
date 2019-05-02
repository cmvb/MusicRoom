import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

declare var $: any;

@Injectable()
export class LoginGuard implements CanActivate {
  usuario: any;
  constructor(private router: Router) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    let URLactual = window.location.href;

    let sesionOK = true;
    if (!URLactual.includes("home") && URLactual !== 'http://localhost:4200/') {
      let usuarioSesion = localStorage.getItem('usuarioSesion') === null ? null : JSON.parse(localStorage.getItem('usuarioSesion').toString());
      sesionOK = false;

      if (usuarioSesion !== null) {
        sesionOK = true;
        /*if (usuarioSesion.tbSesion.tokenSesion != null && usuarioSesion.tbSesion.tokenSesion.length > 0) {
          if (usuarioSesion.tbSesion.activo != null && usuarioSesion.tbSesion.activo === 1) {
            if (usuarioSesion.tbSesion.mensajeError === null || usuarioSesion.tbSesion.mensajeError.length === 0) {
              sesionOK = true;
            }
          }
        }*/
      }

      if (!sesionOK) {
        this.router.navigate(['/home']);
      }
    }

    return sesionOK;
  }
}