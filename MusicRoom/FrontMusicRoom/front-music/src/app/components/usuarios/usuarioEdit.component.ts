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

  // Objetos de Datos
  phase: any;
  data: any;
  listaConsulta = [];
  objetoFiltro: any;
  objetoEdit: any;

  // Enumerados
  enums: any;
  enumSiNo = [];
  enumEstadoUsuario = [];
  enumRolUsuario = [];

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
    this.enums = datasObject.getEnumerados();
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    if (this.util.getSesionXItem('editParam') != null) {
      this.objetoEdit = JSON.parse(localStorage.getItem('editParam'));
    }

    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);
    this.enumEstadoUsuario = this.util.getEnum(this.enums.estadoUsuario.cod);
    this.enumRolUsuario = this.util.getEnum(this.enums.rolUsuario.cod);
    this.phase = this.util.getSesionXItem('phase');
  }

  crearActualizar() {

  }

  sendBack() {
    this.util.limpiarSesionXItem(['listaConsulta']);
    this.router.navigate(['/usuarioQuery']);
  }
}