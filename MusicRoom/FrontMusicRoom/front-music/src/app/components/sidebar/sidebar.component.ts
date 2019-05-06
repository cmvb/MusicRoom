import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import 'rxjs/add/operator/map';
import { DataObjects } from '../.././components/ObjectGeneric';
import { Util } from '../.././components/Util';
import { RestService } from '../.././services/rest.service';
import * as $ from 'jquery';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  // Objetos de Sesion
  usuarioSesion: any;
  sesion: any;

  // Objetos de Datos
  data: any;
  ex: any;
  usuario: any;
  msgs = [];

  logueado: boolean;

  // Utilidades
  util: any;
  msg: any;
  const: any;

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util) {
    this.usuario = datasObject.getDataUsuario();
    this.sesion = datasObject.getDataSesion();
    this.ex = datasObject.getDataException();
    this.msg = datasObject.getProperties(datasObject.getConst().idiomaEs);
    this.const = datasObject.getConst();
    this.util = util;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
  }

  limpiarExcepcion() {
    this.ex = this.util.limpiarExcepcion;
    this.msgs = [];
  }

  irMenu(menu) {
    this.util.limpiarVariableSesion();
    this.router.navigate(['/' + menu]);
  }

  desplegarMenu(id) {
    $('#' + id).children('ul').toggle('display');
    $('#' + id).children('span').toggleClass('backMenuOpen');
  }
}