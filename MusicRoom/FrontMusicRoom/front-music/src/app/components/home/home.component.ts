import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestService } from '../.././services/rest.service';
import { MessageService } from 'primeng/api';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { Enumerados } from 'src/app/config/Enumerados';
import { SesionService } from 'src/app/services/sesionService/sesion.service';

declare var $: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  providers: [RestService, MessageService]
})

export class HomeComponent implements OnInit {
  // Objetos de Sesion
  usuarioSesion: any;
  sesion: any;

  // Objetos de Datos
  usuario: any;
  logueado: boolean;

  // Utilidades
  msg: any;

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService) {
    this.usuario = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.idioma);
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.util.limpiarConsolaStorage();
    this.sesionService.inicializar();
  }

  limpiarExcepcion() {
    console.clear();
    this.messageService.clear();
  }

  login() {
    try {
      this.limpiarExcepcion();
      let url = this.objectModelInitializer.getConst().urlRestService + this.objectModelInitializer.getConst().urlControllerUsuario + 'login';
      let urlAuth = this.objectModelInitializer.getConst().urlRestOauth;
      let obj = this.usuario;

      this.restService.postOauthREST(urlAuth, this.usuario.usuario, this.usuario.password)
        .subscribe(resp => {
          if (resp) {
            let tokenSesion = this.objectModelInitializer.getTokenSesion();
            let token = JSON.stringify(resp);
            tokenSesion.name = this.objectModelInitializer.getConst().tokenNameAUTH;
            tokenSesion.token = token;
            this.sesionService.tokenSesion = tokenSesion;

            this.loguear(url, obj);
          }
        },
          error => {
            if (error.error.error === 'invalid_grant' && error.error.error_description === 'Bad credentials') {
              this.messageService.add({ severity: this.objectModelInitializer.getConst().severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mensaje_login_invalido });
            } else {
              this.messageService.add({ severity: this.objectModelInitializer.getConst().severity[3], summary: this.msg.lbl_summary_danger, detail: this.msg.lbl_mensaje_no_conexion_servidor });
            }

            console.log(error, "error");
          });
    } catch (e) {
      console.log(e);
    }
  }

  loguear(url, obj) {
    try {
      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.sesion = resp;
          this.sesionService.usuarioSesion = this.sesion;
          this.irDashboard();
        },
          error => {
            let mensaje = this.util.mostrarNotificacion(error.error);
            this.messageService.add(mensaje);

            console.log(error, "error");
          });
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

  irRegistrar() {
    let audio = new Audio();
    audio.src = "assets/audio/crash.mp3";
    audio.load();
    audio.play();
    this.router.navigate(['/register']);
  }

  irRecordarClave() {
    let audio = new Audio();
    audio.src = "assets/audio/crash.mp3";
    audio.load();
    audio.play();
    this.router.navigate(['/register']);
  }
}