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
  selector: 'app-terceros-edit',
  templateUrl: './tercerosEdit.component.html',
  styleUrls: ['./terceros.component.css'],
  providers: [RestService, MessageService]
})
export class TercerosEditComponent implements OnInit {
  // Objetos de Sesion
  ACCESS_TOKEN: any;
  usuarioSesion: any;
  sesion: any;

  // Utilidades
  util: any;
  msg: any;
  const: any;
  ex: any;
  isDisabled: boolean;

  // Objetos de Datos
  phase: any;
  data: any;
  listaConsulta = [];
  objeto: any;

  // Enumerados
  enums: any;
  enumSiNo = [];
  listaCiudades = [];
  enumFiltroCiudades = [];
  ciudadSeleccionada: any;

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
    this.objeto = datasObject.getDataTercero();
    this.objeto.estado = { value: 1, label: this.msg.lbl_enum_si };
    this.objeto.ubicacionTb = datasObject.getDataUbicacion();
    this.enums = datasObject.getEnumerados();
    this.ACCESS_TOKEN = JSON.parse(sessionStorage.getItem(this.const.tokenNameAUTH)).access_token;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.util.limpiarSesionXItem(['mensajeConfirmacion']);
    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);
    this.listaCiudades = JSON.parse(localStorage.getItem('listaCiudades'));
    this.enumFiltroCiudades = this.util.obtenerEnumeradoDeListaUbicacion(this.listaCiudades, 2);

    this.phase = this.util.getSesionXItem('phase');
    this.isDisabled = this.phase !== this.const.phaseAdd;

    if (this.util.getSesionXItem('editParam') != null) {
      this.objeto = JSON.parse(localStorage.getItem('editParam'));
      this.ciudadSeleccionada = this.util.obtenerUbicacionPorCodigo(this.objeto.ubicacionTb.codigoCiudad, this.listaCiudades, 2);
    }
  }

  limpiarExcepcion() {
    this.ex = this.util.limpiarExcepcion;
  }

  irGuardar() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerTercero + (this.phase === this.const.phaseAdd ? 'crearTercero' : 'modificarTercero');
      this.ajustarCombos();
      let obj = this.objeto;

      this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          let mensajeConfirmacion = 'El Tercero #' + this.data.idTercero + ' fue ' + (this.phase === this.const.phaseAdd ? 'creado' : 'actualizado') + ' satisfactoriamente.';
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
    this.objeto.estado = this.objeto.estado === undefined ? null : this.objeto.estado.value;
    
    if (this.ciudadSeleccionada != null) {
      let ciudad = this.util.obtenerUbicacionDeEnum(this.ciudadSeleccionada.value.idUbicacion, this.listaCiudades);
      Object.assign(this.objeto.ubicacionTb, ciudad);
    }
    else {
      this.objeto.ubicacionTb = null;
    }
  }

  inicializarCombos() {
    this.objeto.estado = this.util.getValorEnumerado(this.enumSiNo, this.objeto.estado);
    this.ciudadSeleccionada = null;
  }

  irAtras() {
    this.util.limpiarSesionXItem(['listaConsulta']);
    this.router.navigate(['/terceroQuery']);
  }

  guardaTeclaEnter(event) {
    if (event.which === 13) {
      this.irGuardar();
    }
  }
}
