import 'rxjs/add/operator/map';
import { Util } from '../Util';
import { DataObjects } from '../ObjectGeneric';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Component, OnInit, Input, forwardRef, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { format } from 'url';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { Observable } from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';
import { RestService } from '../../services/rest.service';

declare var $: any;

@Component({
  selector: 'app-usuario-edit',
  templateUrl: './usuarioEdit.component.html',
  styleUrls: ['./usuarios.component.css'],
  providers: [RestService]
})

export class UsuarioEditComponent implements OnInit {
  // Objetos de Sesion
  usuarioSesion: any;
  sesion: any;

  // Utilidades
  util: any;
  msg: any;
  const: any;
  locale: any;
  ex: any;
  msgs = [];

  // Objetos de Datos
  phase: any;
  data: any;
  listaConsulta = [];
  objetoFiltro: any;
  objeto: any;

  // Enumerados
  enums: any;
  enumSiNo = [];
  enumTipoUsuario = [];

  // Propiedades de las peticiones REST
  headers = new Headers({ 'Content-Type': 'application/json' });
  options = new RequestOptions({ headers: this.headers });

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util) {
    this.usuarioSesion = datasObject.getDataUsuario();
    this.sesion = datasObject.getDataSesion();
    this.msg = datasObject.getProperties(datasObject.getConst().idiomaEs);
    this.const = datasObject.getConst();
    this.util = util;
    this.objetoFiltro = datasObject.getDataUsuario();
    this.enums = datasObject.getEnumerados();
    this.locale = this.const.getLocaleESForCalendar();
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    if (this.util.getSesionXItem('editParam') != null) {
      this.objeto = JSON.parse(localStorage.getItem('editParam'));
    }

    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);
    this.enumTipoUsuario = this.util.getEnum(this.enums.tipoUsuario.cod);
    this.phase = this.util.getSesionXItem('phase');
  }

  limpiarExcepcion() {
    this.ex = this.util.limpiarExcepcion;
    this.msgs = [];
  }

  irGuardar() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'crearUsuario';
      let obj = this.objeto;

      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
        },
          error => {
            this.ex = error.error;
            this.msgs.push(this.util.mostrarNotificacion(this.ex));
            console.log(error, "error");
          })
    } catch (e) {
      console.log(e);
    }
  }

  irAtras() {
    this.util.limpiarSesionXItem(['listaConsulta']);
    this.router.navigate(['/usuarioQuery']);
  }

  guardaTeclaEnter(event) {
    if (event.which === 13) {
      this.irGuardar();
    }
  }
}