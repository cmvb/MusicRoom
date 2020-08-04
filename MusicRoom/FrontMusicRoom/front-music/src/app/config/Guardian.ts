import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { TextProperties } from './TextProperties';
import { JwtHelperService } from '@auth0/angular-jwt';
import { SesionService } from '../services/sesionService/sesion.service';
import { ObjectModelInitializer } from './ObjectModelInitializer';

declare var $: any;

@Injectable()
export class Guardian implements CanActivate {
  msg: any;

  constructor(private router: Router, public objectModelInitializer: ObjectModelInitializer, public textProperties: TextProperties, public sesionService: SesionService) {
    this.msg = this.textProperties.getProperties(this.sesionService.objServiceSesion.idioma);
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    let URLactual = window.location.href;
    const helper = new JwtHelperService();

    let sesionOK = true;
    if (!URLactual.includes("register") && !URLactual.includes("home") && !URLactual.includes("error") && URLactual.toString() !== (this.objectModelInitializer.getConst().urlDomain)) {
      if (typeof this.sesionService.objServiceSesion.tokenSesion !== 'undefined' && this.sesionService.objServiceSesion.tokenSesion !== null) {
        let ACCESS_TOKEN = this.sesionService.objServiceSesion.tokenSesion.token;
        if (this.sesionService.objServiceSesion.usuarioSesion !== null && ACCESS_TOKEN !== null) {
          const decodedToken = helper.decodeToken(ACCESS_TOKEN.access_token);
          const expirationDate = helper.getTokenExpirationDate(ACCESS_TOKEN.access_token);
          const isExpired = helper.isTokenExpired(ACCESS_TOKEN.access_token);

          if (!isExpired) {
            let tienePermisos = this.sesionService.tienePermisos(URLactual);

            if (!tienePermisos) {
              this.sesionService.objServiceSesion.decodedToken = JSON.stringify(decodedToken);
              this.sesionService.objServiceSesion.expirationDate = JSON.stringify(expirationDate);
              this.sesionService.objServiceSesion.mensajeError403 = this.msg.lbl_mensaje_error_403_ingresar_ruta + URLactual + '. ' + this.msg.lbl_mensaje_error_403_no_permisos;
              this.router.navigate(['/error403']);
            }
          }
          else {
            this.sesionService.objServiceSesion.decodedToken = JSON.stringify(decodedToken);
            this.sesionService.objServiceSesion.expirationDate = JSON.stringify(expirationDate);
            this.sesionService.objServiceSesion.mensajeError403 = this.msg.lbl_mensaje_error_403_sesion_expirada;
            this.router.navigate(['/error403']);
          }
        }
        else {
          this.sesionService.objServiceSesion.mensajeError500 = this.msg.lbl_mensaje_error_500_no_sesion;
          this.router.navigate(['/error500']);
        }
      }
    } else if (URLactual.includes("home")) {
      // TODO: Verificar si tiene sesión y enviar al dashboard
    }

    return true;
  }
}