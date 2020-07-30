import { Component, OnInit } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';
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
  selector: 'app-usuario-edit',
  templateUrl: './usuarioEdit.component.html',
  styleUrls: ['./usuarios.component.scss'],
  providers: [RestService, MessageService]
})

export class UsuarioEditComponent implements OnInit {
  // Objetos de Sesion
  ACCESS_TOKEN: any;
  usuarioSesion: any;
  sesion: any;

  // Utilidades
  msg: any;
  const: any;
  locale: any;
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
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService, public usuarioService: UsuarioService) {
    this.usuarioSesion = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.objeto = this.objectModelInitializer.getDataUsuario();
    this.objeto.estado = { value: 1, label: this.msg.lbl_enum_si };
    this.objeto.tipoUsuario = { value: 0, label: this.msg.lbl_enum_generico_valor_vacio };
    this.objeto.tipoDocumento = { value: 0, label: this.msg.lbl_enum_generico_valor_vacio };
    this.enums = this.enumerados.getEnumerados();
    this.locale = this.sesionService.idioma === this.objectModelInitializer.getConst().idiomaEs ? this.objectModelInitializer.getLocaleESForCalendar() : this.objectModelInitializer.getLocaleENForCalendar();
    this.ACCESS_TOKEN = this.sesionService.tokenSesion.token.access_token;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.sesionService.mensajeConfirmacion = undefined;
    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);
    this.enumTipoUsuario = this.util.getEnum(this.enums.tipoUsuario.cod);
    this.enumTipoDocumento = this.util.getEnum(this.enums.tipoDocumento.cod);
    this.phase = this.sesionService.phase;
    this.isDisabled = this.phase !== this.const.phaseAdd;

    if (typeof this.usuarioService.editParam !== 'undefined' && this.usuarioService.editParam !== null) {
      this.objeto = this.usuarioService.editParam
      this.objeto.fechaNacimiento = new Date(this.objeto.fechaNacimiento);
      this.inicializarCombos();
    }
  }

  limpiarExcepcion() {
    console.clear();
    this.messageService.clear();
  }

  irGuardar() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + (this.phase === this.const.phaseAdd ? 'crearUsuario' : 'modificarUsuario');
      this.ajustarCombos();
      let obj = this.objeto;

      this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          let mensajeConfirmacion = this.msg.lbl_detail_el_registro + this.data.usuario + this.msg.lbl_detail_fue + (this.phase === this.const.phaseAdd ? this.msg.lbl_detail_creado : this.msg.lbl_detail_actualizado) + this.msg.lbl_detail_satisfactoriamente;
          this.sesionService.mensajeConfirmacion = mensajeConfirmacion;
          this.irAtras();
        },
          error => {
            let mensaje = this.util.mostrarNotificacion(error.error);
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
    this.objeto.tipoUsuario = this.objeto.tipoUsuario === undefined ? null : this.objeto.tipoUsuario.value;
    this.objeto.tipoDocumento = this.objeto.tipoDocumento === undefined ? null : this.objeto.tipoDocumento.value;
  }

  inicializarCombos() {
    this.objeto.estado = this.util.getValorEnumerado(this.enumSiNo, this.objeto.estado);
    this.objeto.tipoDocumento = this.util.getValorEnumerado(this.enumTipoDocumento, this.objeto.tipoDocumento);
    this.objeto.tipoUsuario = this.util.getValorEnumerado(this.enumTipoUsuario, this.objeto.tipoUsuario);
  }

  irAtras() {
    this.usuarioService.listaConsulta = undefined;
    this.router.navigate(['/usuarioQuery']);
  }

  guardaTeclaEnter(event) {
    if (event.which === 13) {
      this.irGuardar();
    }
  }
}