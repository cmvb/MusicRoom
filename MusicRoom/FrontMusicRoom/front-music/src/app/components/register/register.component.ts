import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import { MenuItem, MessageService } from 'primeng/api';
import 'rxjs/add/operator/map';
import { DataObjects } from '../.././components/ObjectGeneric';
import { Util } from '../.././components/Util';
import { RestService } from '../.././services/rest.service';
import { debug } from 'util';

declare var $: any;

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [RestService, MessageService]
})
export class RegisterComponent implements OnInit {
  // Objetos de Sesion
  usuarioSesion: any;
  sesion: any;

  // Objetos de Datos
  ex: any;
  usuario: any;
  codigoVerificacion: any;
  nuevaPassword: any;
  msgs = [];
  logueado: boolean;
  step: any;
  isDisabled: any;
  dataUsuarios: any;
  listaUsuarios = [];

  // Steps
  items: MenuItem[];
  activeIndex: number = 0;
  indexTemp: number = 0;

  // Utilidades
  util: any;
  msg: any;
  const: any;
  locale: any;
  maxDate = new Date();

  // Enumerados
  enums: any;
  enumTipoUsuario = [];
  enumTipoDocumento = [];

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util, private messageService: MessageService) {
    this.usuario = datasObject.getDataUsuario();
    this.sesion = datasObject.getDataSesion();
    this.ex = datasObject.getDataException();
    this.msg = datasObject.getProperties(datasObject.getConst().idiomaEs);
    this.const = datasObject.getConst();
    this.util = util;
    this.step = 1;
    this.locale = datasObject.getLocaleESForCalendar();
    this.usuario.tipoUsuario = { value: 0, label: this.msg.lbl_enum_generico_valor_vacio };
    this.usuario.tipoDocumento = { value: 0, label: this.msg.lbl_enum_generico_valor_vacio };
    this.enums = datasObject.getEnumerados();
    this.isDisabled = true;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.consultarTodosUsuarios();
    this.enumTipoUsuario = this.util.getEnum(this.enums.tipoUsuario.cod);
    this.enumTipoDocumento = this.util.getEnum(this.enums.tipoDocumento.cod);

    this.items = [
      {
        label: 'PERSONAL',
        command: (event: any) => {
          this.activeIndex = 0;
          this.step = 1;
          this.isDisabled = true;
          this.messageService.clear();
          this.messageService.add({ severity: this.const.severity[0], summary: event.item.label, detail: this.msg.lbl_mtto_generico_step_1_registrar_usuario });
        }
      },
      {
        label: 'IDENTIFICACIÓN',
        command: (event: any) => {
          let nowActiveIndex = this.activeIndex;

          if (this.validarStep(1)) {
            this.indexTemp = 1;
            this.activeIndex = 1;
            this.step = 2;
            this.isDisabled = true;
            this.messageService.clear();
            this.messageService.add({ severity: this.const.severity[0], summary: event.item.label, detail: this.msg.lbl_mtto_generico_step_2_registrar_usuario });
          }
          else {
            this.activeIndex = this.indexTemp;
            this.messageService.clear();
            this.messageService.add({ severity: this.const.severity[2], summary: "ADVERTENCIA", detail: this.msg.lbl_mtto_generico_step_registrar_usuario_error });
          }
        }
      },
      {
        label: 'SEGURIDAD',
        command: (event: any) => {
          let nowActiveIndex = this.activeIndex;

          if (this.validarStep(2)) {
            this.indexTemp = 2;
            this.activeIndex = 2;
            this.step = 3;
            this.isDisabled = true;
            this.messageService.clear();
            this.messageService.add({ severity: this.const.severity[0], summary: event.item.label, detail: this.msg.lbl_mtto_generico_step_3_registrar_usuario });
          }
          else {
            this.activeIndex = this.indexTemp;
            this.messageService.clear();
            this.messageService.add({ severity: this.const.severity[2], summary: "ADVERTENCIA", detail: this.msg.lbl_mtto_generico_step_registrar_usuario_error });
          }
        }
      },
      {
        label: 'CONFIRMACIÓN',
        command: (event: any) => {
          let nowActiveIndex = this.activeIndex;

          if (this.validarStep(3)) {
            // Enviar Correo con el Código de Verificación
            this.enviarCorreoCodVerificacionReg();
            this.indexTemp = 3;
            this.activeIndex = 3;
            this.step = 4;
            this.isDisabled = false;
            this.messageService.clear();
            this.messageService.add({ severity: this.const.severity[0], summary: event.item.label, detail: this.msg.lbl_mtto_generico_step_4_registrar_usuario });
          }
          else {
            this.activeIndex = this.indexTemp;
            this.messageService.clear();
            this.messageService.add({ severity: this.const.severity[2], summary: "ADVERTENCIA", detail: this.msg.lbl_mtto_generico_step_registrar_usuario_error });
          }
        }
      }
    ];

    console.clear();
    this.messageService.clear();
    this.messageService.add({ severity: this.const.severity[0], summary: "Personal", detail: this.msg.lbl_mtto_generico_step_1_registrar_usuario });
  }


  consultarTodosUsuarios() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'consultarUsuariosRegister';

      this.restService.getREST(url)
        .subscribe(resp => {
          console.log(resp, "res");
          this.dataUsuarios = resp;
          this.listaUsuarios = this.dataUsuarios;
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

  enviarCorreoCodVerificacionReg() {
    try {
      this.limpiarExcepcion();
      let nombreCompleto = this.usuario.nombre + ' ' + this.usuario.apellido;
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'enviarCodigoVerificacion/' + this.codigoVerificacion + '/' + this.usuario.email + '/' + this.usuario.usuario + '/' + nombreCompleto;

      this.util.agregarSesionXItem([{ item: 'usuarioRegister', valor: this.usuario }]);

      this.restService.getREST(url)
        .subscribe(resp => {
          this.messageService.add({ severity: this.const.severity[0], summary: "INFORMACIÓN: ", detail: this.msg.lbl_mtto_generico_codigo_verificaicion_enviado_ok });
        },
          error => {
            this.ex = error.error;
            let mensaje = this.util.mostrarNotificacion(this.ex);
            this.messageService.clear();
            this.messageService.add(mensaje);

            console.log(error, "error");
          });

    } catch (e) {
      console.log(e);
    }
  }

  validarStep(paso) {
    let result = false;
    if (paso == 1) {
      let flagNombre = (this.usuario.nombre !== undefined && this.usuario.nombre !== null && this.usuario.nombre.length > 0);
      let flagApellido = (this.usuario.apellido !== undefined && this.usuario.apellido !== null && this.usuario.apellido.length > 0);
      let flagFechaNacimiento = (this.usuario.fechaNacimiento !== undefined && this.usuario.fechaNacimiento !== null);

      if (flagNombre && flagApellido && flagFechaNacimiento) {
        result = true;
      }
    }
    else if (paso == 2) {
      this.usuario.email = this.usuario.email.toLowerCase();
      let flagTipoDoc = (this.usuario.tipoDocumento !== undefined && this.usuario.tipoDocumento !== null);
      let flagNumDoc = (this.usuario.numeroDocumento !== undefined && this.usuario.numeroDocumento !== null && this.usuario.numeroDocumento.length > 0);
      let flagEmail = (this.usuario.email !== undefined && this.usuario.email !== null && this.usuario.email.length > 0);
      if (flagEmail) {
        flagEmail = this.util.validarEstructuraEmail(this.usuario.email);
      }
      let flagEmailNoRepetido = !this.util.emailInLista(this.usuario.email, this.listaUsuarios);
      let flagDocumentoNoRepetido = !this.util.documentoInLista(this.usuario.tipoDocumento, this.usuario.numeroDocumento, this.listaUsuarios);

      if (flagTipoDoc && flagNumDoc && flagEmail && flagEmailNoRepetido && flagDocumentoNoRepetido) {
        result = true;
      }
    }
    else if (paso == 3) {
      let flagUsuario = (this.usuario.usuario !== undefined && this.usuario.usuario !== null && this.usuario.usuario.length > 0);
      let flagPassword = (this.usuario.password !== undefined && this.usuario.password !== null && this.usuario.password.length > 0);
      let flagNuevaPassword = (this.nuevaPassword !== undefined && this.nuevaPassword !== null && this.nuevaPassword.length > 0);
      let flagPasswordIguales = this.nuevaPassword === this.usuario.password;
      let flagUsuarioNoRepetido = !this.util.usuarioInLista(this.usuario.usuario, this.listaUsuarios);

      if (flagUsuario && flagPassword && flagNuevaPassword && flagPasswordIguales && flagUsuarioNoRepetido) {
        result = true;
      }
    }

    return result;
  }

  limpiarExcepcion() {
    console.clear();
    this.ex = this.util.limpiarExcepcion;
    this.msgs = [];
  }

  registrarse() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'registrarse';
      let obj = this.util.copiarElemento(this.usuario, this.util.usuarioEjemplo);

      obj.tipoDocumento = this.usuario.tipoDocumento.value;
      obj.tipoUsuario = 1;
      obj.fotoTb = null;
      obj.codigoVerificacion = this.codigoVerificacion;

      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.sesion.usuarioTb = resp;

          // Procesamiento o Lógica Específica
          this.util.agregarSesionXItem([{ item: 'usuarioSesion', valor: this.sesion }]);
          this.irLogin();
        },
          error => {
            this.ex = error.error;
            this.msgs.push(this.util.mostrarNotificacion(this.ex));
            console.log(error, "error");
          })
    } catch (e) {
      console.log(e);
    }
  }

  irDashboard() {
    let audio = new Audio();
    audio.src = "assets/audio/guitarIntro.mp3";
    audio.load();
    audio.play();
    this.router.navigate(['/dashboard']);
  }

  irLogin() {
    let audio = new Audio();
    audio.src = "assets/audio/crash.mp3";
    audio.load();
    audio.play();
    this.router.navigate(['/home']);
  }

  guardaTeclaEnter(event) {
    if (event.which === 13) {
      this.siguientePaso();
    }
  }

  siguientePaso() {
    if ($('.ui-steps-number')[this.step] !== undefined) {
      $('.ui-steps-number')[this.step].click();
    }
  }
}
