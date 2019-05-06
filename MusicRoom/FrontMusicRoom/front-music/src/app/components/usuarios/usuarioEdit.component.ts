import { Component, OnInit } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import { MessageService } from 'primeng/api';
import 'rxjs/add/operator/map';
import { RestService } from '../../services/rest.service';
import { DataObjects } from '../ObjectGeneric';
import { Util } from '../Util';

declare var $: any;

@Component({
  selector: 'app-usuario-edit',
  templateUrl: './usuarioEdit.component.html',
  styleUrls: ['./usuarios.component.css'],
  providers: [RestService, MessageService]
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
  maxDate = new Date();
  isDisabled: boolean;

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
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util, private messageService: MessageService) {
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
    this.util.limpiarSesionXItem(['mensajeConfirmacion']);
    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);
    this.enumTipoUsuario = this.util.getEnum(this.enums.tipoUsuario.cod);
    this.enumTipoDocumento = this.util.getEnum(this.enums.tipoDocumento.cod);
    this.phase = this.util.getSesionXItem('phase');
    this.isDisabled = this.phase !== this.const.phaseAdd;

    if (this.util.getSesionXItem('editParam') != null) {
      this.objeto = JSON.parse(localStorage.getItem('editParam'));
      this.objeto.fechaNacimiento = new Date(this.objeto.fechaNacimiento);
      this.inicializarCombos();
    }
  }

  limpiarExcepcion() {
    this.ex = this.util.limpiarExcepcion;
  }

  irGuardar() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + (this.phase === this.const.phaseAdd ? 'crearUsuario' : 'modificarUsuario');
      this.ajustarCombos();
      let obj = this.objeto;

      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          let mensajeConfirmacion = 'El Usuario ' + this.data.usuario + ' fue ' + (this.phase === this.const.phaseAdd ? 'creado' : 'actualizado') + ' satisfactoriamente.';
          this.util.agregarSesionXItem([{ item: 'mensajeConfirmacion', valor: mensajeConfirmacion }]);
          this.irAtras();
        },
          error => {
            this.ex = error.error;
            let mensaje = this.util.mostrarNotificacion(this.ex);
            this.messageService.clear();
            this.messageService.add(mensaje);

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