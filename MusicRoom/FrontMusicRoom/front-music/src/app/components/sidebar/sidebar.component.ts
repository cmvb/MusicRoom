import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestService } from '../.././services/rest.service';
import * as $ from 'jquery';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { Enumerados } from 'src/app/config/Enumerados';
import { SesionService } from 'src/app/services/sesionService/sesion.service';
import { MessageService } from 'primeng/api';

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
  usuario: any;
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
  msg: any;
  const: any;

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService) {
    this.usuario = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.idioma);
    this.const = this.objectModelInitializer.getConst();
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
    console.clear();
    this.messageService.clear();
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
    let usuarioSesion = this.sesionService.usuarioSesion;

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