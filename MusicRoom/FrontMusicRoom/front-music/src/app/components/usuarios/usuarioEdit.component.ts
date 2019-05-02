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
  maxDate = new Date();

  // Objetos de Datos
  phase: any;
  data: any;
  listaConsulta = [];
  objeto: any;

  // Enumerados
  enums: any;
  enumSiNo = [];
  enumTipoUsuario = [];
  enumTipoDocumento = [];

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
    this.objeto = datasObject.getDataUsuario();
    this.objeto.estado = { value: 1, label: this.msg.lbl_enum_si };
    this.objeto.tipoUsuario = { value: 0, label: this.msg.lbl_enum_generico_valor_vacio };
    this.objeto.tipoDocumento = { value: 0, label: this.msg.lbl_enum_generico_valor_vacio };
    this.enums = datasObject.getEnumerados();
    this.locale = datasObject.getLocaleESForCalendar();
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
    this.enumTipoDocumento = this.util.getEnum(this.enums.tipoDocumento.cod);
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
      this.ajustarCombos();
      let obj = this.objeto;

      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          this.irAtras();
        },
          error => {
            this.ex = error.error;
            this.msgs.push(this.util.mostrarNotificacion(this.ex));
            this.inicializarCombos();

            console.log(error, "error");
          })
    } catch (e) {
      console.log(e);
    }
  }

  ajustarCombos() {
    this.objeto.estado = this.objeto.estado.value;
    this.objeto.tipoUsuario = this.objeto.tipoUsuario.value;
    this.objeto.tipoDocumento = this.objeto.tipoDocumento.value;
  }

  inicializarCombos() {
    this.objeto.estado = this.util.getValorEnumerado(this.enumSiNo, this.objeto.estado);
    this.objeto.tipoDocumento = this.util.getValorEnumerado(this.enumTipoDocumento, this.objeto.tipoDocumento);
    this.objeto.tipoUsuario = this.util.getValorEnumerado(this.enumTipoUsuario, this.objeto.tipoUsuario);
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