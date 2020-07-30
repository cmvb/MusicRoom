import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import { MenuItem, MessageService } from 'primeng/api';
import { RestService } from '../.././services/rest.service';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { Enumerados } from 'src/app/config/Enumerados';
import { SesionService } from 'src/app/services/sesionService/sesion.service';


declare var $: any;

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  providers: [RestService, MessageService]
})
export class RegisterComponent implements OnInit {
  // Objetos de Sesion
  usuarioSesion: any;
  sesion: any;

  // Objetos de Datos
  usuario: any;
  codigoVerificacion: any;
  nuevaPassword: any;
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
  msg: any;
  locale: any;
  maxDate = new Date();

  // Enumerados
  enumTipoUsuario = [];
  enumTipoDocumento = [];

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService) {
    this.usuario = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.idioma);
    this.step = 1;
    this.locale = this.sesionService.idioma === this.objectModelInitializer.getConst().idiomaEs ? this.objectModelInitializer.getLocaleESForCalendar() : this.objectModelInitializer.getLocaleENForCalendar();
    this.usuario.tipoUsuario = { value: 0, label: this.msg.lbl_enum_generico_valor_vacio };
    this.usuario.tipoDocumento = { value: 0, label: this.msg.lbl_enum_generico_valor_vacio };
    this.isDisabled = true;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.consultarTodosUsuarios();
    this.enumTipoUsuario = this.util.getEnum(this.enums.getEnumerados().tipoUsuario.cod);
    this.enumTipoDocumento = this.util.getEnum(this.enums.getEnumerados().tipoDocumento.cod);

    this.items = [
      {
        label: 'PERSONAL',
        command: (event: any) => {
          this.activeIndex = 0;
          this.step = 1;
          this.isDisabled = true;
          this.messageService.clear();
          this.messageService.add({ severity: this.objectModelInitializer.getConst().severity[0], summary: event.item.label, detail: this.msg.lbl_mtto_generico_step_1_registrar_usuario });
        }
      },
      {
        label: 'IDENTIFICACIÓN',
        command: (event: any) => {
          if (this.validarStep(1)) {
            this.indexTemp = 1;
            this.activeIndex = 1;
            this.step = 2;
            this.isDisabled = true;
            this.messageService.clear();
            this.messageService.add({ severity: this.objectModelInitializer.getConst().severity[0], summary: event.item.label, detail: this.msg.lbl_mtto_generico_step_2_registrar_usuario });
          }
          else {
            this.activeIndex = this.indexTemp;
            this.messageService.clear();
            this.messageService.add({ severity: this.objectModelInitializer.getConst().severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mtto_generico_step_registrar_usuario_error });
          }
        }
      },
      {
        label: 'SEGURIDAD',
        command: (event: any) => {
          if (this.validarStep(2)) {
            this.indexTemp = 2;
            this.activeIndex = 2;
            this.step = 3;
            this.isDisabled = true;
            this.messageService.clear();
            this.messageService.add({ severity: this.objectModelInitializer.getConst().severity[0], summary: event.item.label, detail: this.msg.lbl_mtto_generico_step_3_registrar_usuario });
          }
          else {
            this.activeIndex = this.indexTemp;
            this.messageService.clear();
            this.messageService.add({ severity: this.objectModelInitializer.getConst().severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mtto_generico_step_registrar_usuario_error });
          }
        }
      },
      {
        label: 'CONFIRMACIÓN',
        command: (event: any) => {
          if (this.validarStep(3)) {
            // Enviar Correo con el Código de Verificación
            this.enviarCorreoCodVerificacionReg();
            this.indexTemp = 3;
            this.activeIndex = 3;
            this.step = 4;
            this.isDisabled = false;
            this.messageService.clear();
            this.messageService.add({ severity: this.objectModelInitializer.getConst().severity[0], summary: event.item.label, detail: this.msg.lbl_mtto_generico_step_4_registrar_usuario });
          }
          else {
            this.activeIndex = this.indexTemp;
            this.messageService.clear();
            this.messageService.add({ severity: this.objectModelInitializer.getConst().severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mtto_generico_step_registrar_usuario_error });
          }
        }
      }
    ];

    console.clear();
    this.messageService.clear();
    this.messageService.add({ severity: this.objectModelInitializer.getConst().severity[0], summary: "Personal", detail: this.msg.lbl_mtto_generico_step_1_registrar_usuario });
  }


  consultarTodosUsuarios() {
    try {
      this.limpiarExcepcion();
      let url = this.objectModelInitializer.getConst().urlRestService + this.objectModelInitializer.getConst().urlControllerUsuario + 'consultarUsuariosRegister';

      this.restService.getREST(url)
        .subscribe(resp => {
          console.log(resp, "res");
          this.dataUsuarios = resp;
          this.listaUsuarios = this.dataUsuarios;
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

  enviarCorreoCodVerificacionReg() {
    try {
      this.limpiarExcepcion();
      let nombreCompleto = this.usuario.nombre + ' ' + this.usuario.apellido;
      let url = this.objectModelInitializer.getConst().urlRestService + this.objectModelInitializer.getConst().urlControllerUsuario + 'enviarCodigoVerificacion/' + this.codigoVerificacion + '/' + this.usuario.email + '/' + this.usuario.usuario + '/' + nombreCompleto;
      this.sesionService.usuarioRegister = this.usuario;

      this.restService.getREST(url)
        .subscribe(resp => {
          this.messageService.add({ severity: this.objectModelInitializer.getConst().severity[0], summary: this.msg.lbl_summary_info, detail: this.msg.lbl_mtto_generico_codigo_verificaicion_enviado_ok });
        },
          error => {
            let mensaje = this.util.mostrarNotificacion(error.error);
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
    this.messageService.clear();
  }

  registrarse() {
    try {
      this.limpiarExcepcion();
      let url = this.objectModelInitializer.getConst().urlRestService + this.objectModelInitializer.getConst().urlControllerUsuario + 'registrarse';
      let obj = this.util.copiarElemento(this.usuario, this.util.usuarioEjemplo);

      obj.tipoDocumento = this.usuario.tipoDocumento.value;
      obj.tipoUsuario = 1;
      obj.fotoTb = null;
      obj.codigoVerificacion = this.codigoVerificacion;

      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.sesion.usuarioTb = resp;
          this.sesionService.usuarioSesion = this.sesion;
          this.irLogin();
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
