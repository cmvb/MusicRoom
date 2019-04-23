import { Component, OnInit, Input, forwardRef, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import 'rxjs/add/operator/map';
import { format } from 'url';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { DataObjects } from '../.././components/ObjectGeneric'
import { Util } from '../.././components/Util';
import { Observable } from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';
import { RestService } from '../.././services/rest.service';

declare var $: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [RestService]
})

export class HomeComponent implements OnInit {
  data: any;
  ex: any;
  usuario: any;
  logueado: boolean;
  util: any;
  msg: any;
  const: any;
  msgs = [];

  usuarioLogin: any;
  passwordLogin: any;

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util) {
    this.usuario = datasObject.getDataUsuario();
    this.ex = datasObject.getDataException();
    this.msg = datasObject.getProperties(datasObject.getConst().idiomaEn);
    this.const = datasObject.getConst();
    this.util = util;
  }

  ngOnInit() {
  }

  ngDoCheck() {
  }

  limpiarExcepcion() {
    this.ex = this.util.limpiarExcepcion;
    this.msgs = [];
  }

  login() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'login';
      let obj = this.usuario;

      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          // Procesamiento o Lógica Específica
          console.log(this.data);
          this.usuario = this.data;
        },
          error => {
            this.ex = error.error;
            let mensaje = this.util.mensaje;
            mensaje.severity = this.const.severity[2];
            mensaje.summary = "ADVERTENCIA: ";
            mensaje.detail = this.ex.mensaje;

            this.msgs.push(mensaje);
            console.log(error, "error");
          })
    } catch (e) {
      console.log(e);
    }
  }

  irDashboard() {
    this.router.navigate(['/dashboard']);
  }
}