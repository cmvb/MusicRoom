import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs/operators'
import { DataObjects } from '../.././components/ObjectGeneric';
import { Util } from '../.././components/Util';
import { RestService } from '../.././services/rest.service';
import * as $ from 'jquery';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
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
  verMenuConfiguracion: boolean;
  verMenuAdministracion: boolean;
  verMenuInventario: boolean;
  verMenuAgenda: boolean;
  verMenuMovimientos: boolean;
  mapaMenu: any;
  mapaRolXMenuG: any;
  isAdmin: boolean;

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
    this.isAdmin = false;
    this.mapaRolXMenuG = new Map();
    this.mapaRolXMenuG.set("RMRMPA", this.const.menuConfiguracion);
    this.mapaRolXMenuG.set("RMRMSE", this.const.menuConfiguracion);
    this.mapaRolXMenuG.set("RMRMUS", this.const.menuAdministracion);
    this.mapaRolXMenuG.set("RMRMUB", this.const.menuAdministracion);
    this.mapaRolXMenuG.set("RMRMTE", this.const.menuAdministracion);
    this.mapaRolXMenuG.set("RMRMSA", this.const.menuAdministracion);
    this.mapaRolXMenuG.set("RMRMIN", this.const.menuAdministracion);
    this.mapaRolXMenuG.set("RMRMIV", this.const.menuInventario);
    this.mapaRolXMenuG.set("RMRMPR", this.const.menuInventario);
    this.mapaRolXMenuG.set("RMRMEN", this.const.menuAgenda);
    this.mapaRolXMenuG.set("RMRMFA", this.const.menuMovimientos);
    this.verMenuConfiguracion = false;
    this.verMenuAdministracion = false;
    this.verMenuInventario = false;
    this.verMenuAgenda = false;
    this.verMenuMovimientos = false;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.mapaMenu = this.construirMapaURLS();
  }

  limpiarExcepcion() {
    this.ex = this.util.limpiarExcepcion();
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

  construirMapaURLS() {
    let mapaUrls = new Map();
    let usuarioSesion = localStorage.getItem('usuarioSesion') === null ? null : JSON.parse(localStorage.getItem('usuarioSesion').toString());

    if (usuarioSesion !== undefined && usuarioSesion !== null && usuarioSesion.usuarioTb !== undefined && usuarioSesion.usuarioTb !== null && usuarioSesion.usuarioTb.listaRoles !== undefined && usuarioSesion.usuarioTb.listaRoles !== null) {
      for (let i in usuarioSesion.usuarioTb.listaRoles) {
        let rolUsuario = usuarioSesion.usuarioTb.listaRoles[i].codigo;
        if (rolUsuario === this.const.codigoADMIN) {
          this.isAdmin = true;
          this.verMenuConfiguracion = true;
          this.verMenuAdministracion = true;
          this.verMenuInventario = true;
          this.verMenuAgenda = true;
          this.verMenuMovimientos = true;
        }
        else {
          let rolGeneral = this.mapaRolXMenuG.get(rolUsuario);
          mapaUrls.set(rolUsuario, rolGeneral);

          switch (rolGeneral) {
            case "C":
              this.verMenuConfiguracion = true;
              break;
            case "A":
              this.verMenuAdministracion = true;
              break;
            case "I":
              this.verMenuInventario = true;
              break;
            case "G":
              this.verMenuAgenda = true;
              break;
            case "M":
              this.verMenuMovimientos = true;
              break;
          }
        }
      }
    }

    return mapaUrls;
  }

  verificarRolesMenu(rol) {
    let result = false;

    if (this.isAdmin) {
      result = true;
    }
    let menu = this.mapaMenu.get(rol);
    if (menu !== undefined) {
      result = true;
    }

    return result;
  }
}