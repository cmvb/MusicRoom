import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { SesionService } from 'src/app/services/sesionService/sesion.service';
import { RestService } from '../../.././services/rest.service';

declare var $: any;

@Component({
  selector: 'app-token',
  templateUrl: './token.component.html',
  styleUrls: ['./token.component.css'],
  providers: [RestService, MessageService]
})
export class TokenComponent implements OnInit {
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
  verificacionTB: any;
  objetoVCODE: any;

  // Steps
  items: MenuItem[];
  activeIndex: number = 0;
  indexTemp: number = 0;

  // Utilidades
  msg: any;
  const: any;
  locale: any;
  maxDate = new Date();

  // Enumerados
  enums: any;
  enumTipoUsuario = [];
  enumTipoDocumento = [];

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public objectModelInitializer: ObjectModelInitializer, public util: Util, public sesionService: SesionService, private messageService: MessageService) {
    this.usuario = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.step = 1;
    this.locale = this.sesionService.idioma === this.objectModelInitializer.getConst().idiomaEs ? this.objectModelInitializer.getLocaleESForCalendar() : this.objectModelInitializer.getLocaleENForCalendar();
    this.usuario.tipoUsuario = { value: 0, label: this.msg.lbl_enum_generico_valor_vacio };
    this.usuario.tipoDocumento = { value: 0, label: this.msg.lbl_enum_generico_valor_vacio };
    this.enums = this.enumerados.getEnumerados();
    this.isDisabled = true;
    this.verificacionTB = this.objectModelInitializer.getDataVCode();
    this.objetoVCODE = this.objectModelInitializer.getDataVCode();

    let URLactual = window.location.href;
    this.codigoVerificacion = URLactual.replace(this.const.urlVCode, '');
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.consultarTodosUsuarios();
    this.enumTipoUsuario = this.util.getEnum(this.enums.tipoUsuario.cod);
    this.enumTipoDocumento = this.util.getEnum(this.enums.tipoDocumento.cod);
    this.consultarVCODE();

    if (typeof this.sesionService.usuarioRegister !== 'undefined' && this.sesionService.usuarioRegister !== null) {
      this.usuario = this.sesionService.usuarioRegister;
      this.usuario.fechaNacimiento = new Date(this.usuario.fechaNacimiento);
      this.nuevaPassword = this.usuario.password;
    }

    this.items = [
      {
        label: this.msg.lbl_info_titulo_step_personal,
        command: (event: any) => {
          this.activeIndex = 0;
          this.step = 1;
          this.isDisabled = true;
          this.messageService.clear();
          this.messageService.add({ severity: this.const.severity[0], summary: event.item.label, detail: this.msg.lbl_mtto_generico_step_1_registrar_usuario });
        }
      },
      {
        label: this.msg.lbl_info_titulo_step_identificacion,
        command: (event: any) => {
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
            this.messageService.add({ severity: this.const.severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mtto_generico_step_registrar_usuario_error });
          }
        }
      },
      {
        label: this.msg.lbl_info_titulo_step_seguridad,
        command: (event: any) => {
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
            this.messageService.add({ severity: this.const.severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mtto_generico_step_registrar_usuario_error });
          }
        }
      },
      {
        label: this.msg.lbl_info_titulo_step_confirmacion,
        command: (event: any) => {
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
            this.messageService.add({ severity: this.const.severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mtto_generico_step_registrar_usuario_error });
          }
        }
      }
    ];
  }

  consultarVCODE() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'consultarVCodePorCodigoVerificacion';
      let obj = this.objetoVCODE;
      obj.token = this.codigoVerificacion;
      obj.estado = this.const.estadoActivoNumString

      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.verificacionTB = resp;

          // Validar si el VCODE ya expiró. Si es así, redirigir a la pantalla de error con un mensaje informativo
          let vCodeValido = this.verificacionTB.token === this.codigoVerificacion;
          if (!vCodeValido) {
            this.sesionService.mensajeError500 = this.msg.lbl_vcode_expiro;
            //window.location.replace(this.const.urlDomain + 'error500');
            this.router.navigate(['/error500']);
          }
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

  ngAfterViewInit() {
    this.messageService.clear();
    this.messageService.add({ severity: this.const.severity[0], summary: this.msg.lbl_info_titulo_step_personal, detail: this.msg.lbl_mtto_generico_step_1_registrar_usuario });

    // Ubicar al usuario en el paso 3 donde envía el código de confirmación
    if ($('.ui-steps-number')[2] !== undefined) {
      $('.ui-steps-number')[2].click();
    }
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
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'enviarCodigoVerificacion/' + this.codigoVerificacion + '/' + this.usuario.email;

      this.restService.getREST(url)
        .subscribe(resp => {
          this.messageService.add({ severity: this.const.severity[0], summary: this.msg.lbl_summary_info, detail: this.msg.lbl_mtto_generico_codigo_verificaicion_enviado_ok });
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
          this.sesionService.usuarioSesion = this.sesion;

          this.irDashboard();
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

