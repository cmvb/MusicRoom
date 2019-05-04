import { Component, OnInit } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import 'rxjs/add/operator/map';
import { RestService } from '../../services/rest.service';
import { DataObjects } from '../ObjectGeneric';
import { Util } from '../Util';
import * as $ from 'jquery';

declare var $: any;

@Component({
  selector: 'app-usuario-query',
  templateUrl: './usuarioQuery.component.html',
  styleUrls: ['./usuarios.component.css'],
  providers: [RestService, MessageService]
})

export class UsuarioQueryComponent implements OnInit {
  // Objetos de Sesion
  usuarioSesion: any;
  sesion: any;

  // Utilidades
  util: any;
  msg: any;
  ex: any;
  const: any;

  // Objetos de Datos
  data: any;
  listaConsulta = [];
  objetoFiltro: any;

  // Opciones del Componente Consulta
  btnEditar = true;
  btnEliminar = true;
  listaCabeceras = [
    { 'campoLista': 'usuario', 'nombreCabecera': 'Usuario' },
    { 'campoLista': 'nombre', 'nombreCabecera': 'Nombre' },
    { 'campoLista': 'apellido', 'nombreCabecera': 'Apellido' },
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
    this.objetoFiltro = datasObject.getDataUsuario();
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.consultarUsuarios();
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
    this.ex = this.util.limpiarExcepcion;
  }

  consultarUsuarios() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'consultarPorFiltros';
      let obj = this.objetoFiltro;
      obj.estado = "1";

      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          this.listaConsulta = this.data;
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
    this.util.agregarSesionXItem([{ item: 'phase', valor: this.const.phaseEdit }, { item: 'objetoFiltro', valor: this.objetoFiltro }, { item: 'listaConsulta', valor: this.listaConsulta }, { item: 'editParam', valor: objetoEdit }]);
    this.router.navigate(['/usuarioEdit']);
    return true;
  }

  irCrear() {
    this.util.agregarSesionXItem([{ item: 'phase', valor: this.const.phaseAdd }, { item: 'objetoFiltro', valor: this.objetoFiltro }, { item: 'listaConsulta', valor: this.listaConsulta }, { item: 'editParam', valor: null }]);
    this.router.navigate(['/usuarioEdit']);
    return true;
  }

  eliminar(objetoEdit) {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'eliminarUsuario';

      this.restService.postREST(url, objetoEdit)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          this.limpiar();
          this.consultarUsuarios();
          this.messageService.clear();
          this.messageService.add({ severity: this.const.severity[1], summary: 'CONFIRMACIÓN: ', detail: 'El Usuario fue eliminado satisfactoriamente.' });
          this.ex.mensaje = 'El Usuario fue eliminado satisfactoriamente.';
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
      this.consultarUsuarios();
    }
  }
}