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
  selector: 'app-usuario-query',
  templateUrl: './usuarioQuery.component.html',
  styleUrls: ['./usuarios.component.css'],
  providers: [RestService]
})

export class UsuarioQueryComponent implements OnInit {
  // Objetos de Sesion
  usuarioSesion: any;
  sesion: any;
  // Utilidades
  util: any;
  msg: any;
  const: any;

  // Objetos de Datos
  data: any;
  listaConsulta = [];
  objetoFiltro: any;

  // Opciones del Componente Consulta
  btnEditar = true;
  btnEliminar = true;
  listaCabeceras = [
    { 'campoLista': 'usuario', 'nombreCabecera': 'User' },
    { 'campoLista': 'nombre', 'nombreCabecera': 'First Name' },
    { 'campoLista': 'apellido', 'nombreCabecera': 'Last Name' },
  ];

  // Propiedades de las peticiones REST
  headers = new Headers({ 'Content-Type': 'application/json' });
  options = new RequestOptions({ headers: this.headers });

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util) {
    this.usuarioSesion = datasObject.getDataUsuario();
    this.sesion = datasObject.getDataSesion();
    this.msg = datasObject.getProperties(datasObject.getConst().idiomaEn);
    this.const = datasObject.getConst();
    this.util = util;
    this.objetoFiltro = datasObject.getDataUsuario();
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    let filtroTemporal = this.util.getSesionXItem('objetoFiltro');
    if (filtroTemporal != null) {
      this.objetoFiltro = filtroTemporal;
    }

    let listaTemporal = this.util.getSesionXItem('listaConsulta');
    if (listaTemporal != null) {
      this.listaConsulta = listaTemporal;
    }
    else {
      this.consultarUsuarios();
    }
  }

  consultarUsuarios() {
    try {
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'consultarPorFiltros';
      let obj = this.objetoFiltro;

      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          console.log(this.data);
          this.listaConsulta = this.data;
        },
          error => {
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

  irEditar(objetoEdit) {
    this.util.agregarSesionXItem([{ item: 'phase', valor: this.const.phaseEdit }, { item: 'objetoFiltro', valor: this.objetoFiltro }, { item: 'listaConsulta', valor: this.listaConsulta }, { item: 'editParam', valor: objetoEdit }]);
    this.router.navigate(['/usuarioEdit']);
    return true;
  }

  irCrear() {
    this.util.agregarSesionXItem([{ item: 'phase', valor: this.const.phaseAdd }, { item: 'objetoFiltro', valor: this.objetoFiltro }, { item: 'listaConsulta', valor: this.listaConsulta }, { item: 'editParam', valor: null }]);
    this.router.navigate(['/usuarioEdit']);
    return true;
  }

  eliminar(obj) {

  }

  consultaTeclaEnter(event) {
    if (event.which === 13) {
      this.consultarUsuarios();
    }
  }
}