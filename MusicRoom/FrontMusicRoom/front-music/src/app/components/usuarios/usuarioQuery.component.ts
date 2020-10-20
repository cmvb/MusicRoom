import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { RestService } from '../../services/rest.service';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { Enumerados } from 'src/app/config/Enumerados';
import { SesionService } from 'src/app/services/sesionService/sesion.service';
import { UsuarioService } from 'src/app/services/usuarioService/usuario.service';

declare var $: any;

@Component({
  selector: 'app-usuario-query',
  templateUrl: './usuarioQuery.component.html',
  styleUrls: ['./usuarios.component.scss'],
  providers: [RestService, MessageService]
})

export class UsuarioQueryComponent implements OnInit {
  // Objetos de Sesion
  ACCESS_TOKEN: any;
  usuarioSesion: any;
  sesion: any;

  // Utilidades
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
    { 'campoLista': 'usuario', 'nombreCabecera': 'Usuario' },
    { 'campoLista': 'nombre', 'nombreCabecera': 'Nombre' },
    { 'campoLista': 'apellido', 'nombreCabecera': 'Apellido' },
  ];

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService, public usuarioService: UsuarioService) {
    this.usuarioSesion = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.objServiceSesion.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.objetoFiltro = this.objectModelInitializer.getDataUsuario();
    this.ACCESS_TOKEN = this.sesionService.objServiceSesion.tokenSesion.token.access_token;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.consultarUsuarios();
  }

  ngAfterViewInit() {
    if (typeof this.sesionService.objServiceSesion.mensajeConfirmacion !== 'undefined' && this.sesionService.objServiceSesion.mensajeConfirmacion !== null) {
      let mensajeConfirmacion = this.sesionService.objServiceSesion.mensajeConfirmacion;
      this.messageService.clear();
      this.messageService.add({ severity: this.const.severity[1], summary: this.msg.lbl_summary_success, detail: mensajeConfirmacion });
    }
  }

  limpiarExcepcion() {
    console.clear();
    this.messageService.clear();
  }

  consultarUsuarios() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'consultarPorFiltros';
      let obj = this.objetoFiltro;
      obj.estado = this.const.estadoActivoNumString

      this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          this.listaConsulta = this.data;
        },
          error => {
            let mensaje = this.util.mostrarNotificacion(error.error);
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
    this.sesionService.objServiceSesion.phase = this.const.phaseEdit;
    this.usuarioService.objetoFiltro = this.objetoFiltro;
    this.usuarioService.listaConsulta = this.listaConsulta;
    this.usuarioService.editParam = objetoEdit;
    this.router.navigate(['/usuarioEdit']);
    return true;
  }

  irCrear() {
    this.sesionService.objServiceSesion.phase = this.const.phaseAdd;
    this.usuarioService.objetoFiltro = this.objetoFiltro;
    this.usuarioService.listaConsulta = this.listaConsulta;
    this.usuarioService.editParam = null;
    this.router.navigate(['/usuarioEdit']);
    return true;
  }

  eliminar(objetoEdit) {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'eliminarUsuario';

      this.restService.postSecureREST(url, objetoEdit, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          this.limpiar();
          this.consultarUsuarios();
          this.messageService.clear();
          this.messageService.add({ severity: this.const.severity[1], summary: this.msg.lbl_summary_success, detail: this.msg.lbl_detail_el_registro_eliminado });
        },
          error => {
            let mensaje = this.util.mostrarNotificacion(error.error);
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