import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import 'rxjs/add/operator/map';
import { DataObjects } from '../.././components/ObjectGeneric';
import { Util } from '../.././components/Util';
import { RestService } from '../.././services/rest.service';

declare var $: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [RestService]
})

export class HomeComponent implements OnInit {
  // Objetos de Sesion
  usuarioSesion: any;
  sesion: any;

  // Objetos de Datos
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
    console.clear();
    this.util.limpiarSesion();
  }

  limpiarExcepcion() {
    console.clear();
    this.ex = this.util.limpiarExcepcion;
    this.msgs = [];
  }

  login() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'login';
      let urlAuth = this.const.urlRestOauth;
      let obj = this.usuario;
      
      this.restService.postOauthREST(urlAuth, this.usuario.usuario, this.usuario.password)
      .subscribe(resp => {
        if (resp) {
          let token = JSON.stringify(resp);
          sessionStorage.setItem(this.const.tokenNameAUTH, token);
        }
      }, 
      error => {
        this.ex = error.error;
        this.msgs.push(this.util.mostrarNotificacion(this.ex));
        console.log(error, "error");
      });

      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.sesion = resp;

          // Procesamiento o Lógica Específica
          this.util.agregarSesionXItem([{ item: 'usuarioSesion', valor: this.sesion }]);
          this.irDashboard();
        }, 
          error => {
            this.msgs = [];
            this.ex = error.error;
            this.msgs.push(this.util.mostrarNotificacion(this.ex));
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
    this.router.navigate(['/music-room/dashboard']);
  }

  irRegistrar() {
    let audio = new Audio();
    audio.src = "assets/audio/crash.mp3";
    audio.load();
    audio.play();
    this.router.navigate(['/music-room/register']);
  }
}