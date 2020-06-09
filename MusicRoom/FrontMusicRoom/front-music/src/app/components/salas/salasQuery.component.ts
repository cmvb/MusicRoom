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
  selector: 'app-salas-query',
  templateUrl: './salasQuery.component.html',
  styleUrls: ['./salas.component.scss'],
  providers: [RestService, MessageService]
})
export class SalasQueryComponent implements OnInit {
  // Objetos de Sesion
  ACCESS_TOKEN: any;
  usuarioSesion: any;
  sesion: any;

  // Utilidades
  util: any;
  msg: any;
  ex: any;
  const: any;

  // Objetos de Datos
  data: any;
  dataC: any;
  listaConsulta = [];
  objetoFiltro: any;

  // Enumerados
  enums: any;
  listaTerceros = [];
  enumFiltroTerceros = [];
  terceroSeleccionado: any;

  // Opciones del Componente Consulta
  btnEditar = true;
  btnEliminar = true;
  listaCabeceras = [
    { 'campoLista': 'nombreSala', 'nombreCabecera': 'Sala' },
    { 'campoLista': 'infoAdicional', 'nombreCabecera': 'Información' }
  ];

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
    this.objetoFiltro = datasObject.getDataSala();
    this.ACCESS_TOKEN = JSON.parse(sessionStorage.getItem(this.const.tokenNameAUTH)).access_token;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.consultarSalas();
    // Terceros
    this.consultarTerceros();
  }

  ngAfterViewInit() {
    if (this.util.getSesionXItem('mensajeConfirmacion') != null) {
      let mensajeConfirmacion = JSON.parse(localStorage.getItem('mensajeConfirmacion'));
      this.messageService.clear();
      this.messageService.add({ severity: this.const.severity[1], summary: 'CONFIRMACIÓN: ', detail: mensajeConfirmacion });
      this.ex.mensaje = mensajeConfirmacion;
    }
  }

  limpiarExcepcion() {
    this.ex = this.util.limpiarExcepcion();
  }

  consultarSalas() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerSala + 'consultarPorFiltros';
      this.ajustarCombos();
      let obj = this.objetoFiltro;
      obj.estado = "1";

      this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          this.listaConsulta = this.data;

          /*for (let i in this.listaConsulta) {
            this.listaConsulta[i].ubicacionTabla = this.listaConsulta[i].ubicacionTb.nombreCiudad + " (" + this.listaConsulta[i].ubicacionTb.nombreDepartamento + ")";
          }*/
        },
          error => {
            this.ex = error.error;
            let mensaje = this.util.mostrarNotificacion(this.ex);
            this.messageService.clear();
            this.messageService.add(mensaje);

            console.log(error, "error");
          })
    } catch (e) {
      console.log(e);
    }
  }

  consultarTerceros() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerTercero;

      this.restService.getSecureREST(url, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.dataC = resp;
          this.listaTerceros = this.dataC;
          this.enumFiltroTerceros = this.util.obtenerEnumeradoDeListaTercero(this.listaTerceros);
        },
          error => {
            this.ex = error.error;
            let mensaje = this.util.mostrarNotificacion(this.ex);
            this.messageService.clear();
            this.messageService.add(mensaje);

            console.log(error, "error");
          })
    } catch (e) {
      console.log(e);
    }
  }

  limpiar() {
    this.objetoFiltro = {};
    this.listaConsulta = [];

    return true;
  }

  editar(objetoEdit) {
    this.util.agregarSesionXItem([{ item: 'phase', valor: this.const.phaseEdit }, { item: 'objetoFiltro', valor: this.objetoFiltro }, { item: 'listaConsulta', valor: this.listaConsulta }, { item: 'editParam', valor: objetoEdit }, { item: 'listaTerceros', valor: this.listaTerceros }]);
    this.router.navigate(['/salaEdit']);
    return true;
  }

  irCrear() {
    this.util.agregarSesionXItem([{ item: 'phase', valor: this.const.phaseAdd }, { item: 'objetoFiltro', valor: this.objetoFiltro }, { item: 'listaConsulta', valor: this.listaConsulta }, { item: 'editParam', valor: null }, { item: 'listaTerceros', valor: this.listaTerceros }]);
    this.router.navigate(['/salaEdit']);
    return true;
  }

  eliminar(objetoEdit) {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerSala + 'eliminarSala';

      this.restService.postSecureREST(url, objetoEdit, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          this.limpiar();
          this.consultarSalas();
          this.messageService.clear();
          this.messageService.add({ severity: this.const.severity[1], summary: 'CONFIRMACIÓN: ', detail: 'La Sala fue eliminada satisfactoriamente.' });
          this.ex.mensaje = 'La Sala fue eliminada satisfactoriamente.';
        },
          error => {
            this.ex = error.error;
            let mensaje = this.util.mostrarNotificacion(this.ex);
            this.messageService.clear();
            this.messageService.add(mensaje);

            console.log(error, "error");
          })
    } catch (e) {
      console.log(e);
    }
  }

  consultaTeclaEnter(event) {
    if (event.which === 13) {
      this.consultarSalas();
    }
  }

  ajustarCombos() {
    if (this.terceroSeleccionado != null) {
      let tercero = this.util.obtenerTerceroDeEnum(this.terceroSeleccionado.value.idTercero, this.listaTerceros);
      Object.assign(this.objetoFiltro.terceroTb, tercero);
    }
    else {
      this.objetoFiltro.terceroTb = null;
    }
  }
}
