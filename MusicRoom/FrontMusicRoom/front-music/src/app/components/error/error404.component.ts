import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import { MenuItem, MessageService } from 'primeng/api';
import 'rxjs/add/operator/map';
import { DataObjects } from '../.././components/ObjectGeneric';
import { Util } from '../.././components/Util';
import { RestService } from '../.././services/rest.service';
import { debug } from 'util';

@Component({
  selector: 'app-error404',
  templateUrl: './error404.component.html',
  styleUrls: ['./error.component.scss'],
  providers: [RestService, MessageService]
})
export class Error404Component implements OnInit {
  // Objetos de Datos
  mensajeError: any;
  ex: any;
  msgs = [];

  // Utilidades
  util: any;
  msg: any;
  const: any;

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util, private messageService: MessageService) {
    this.ex = datasObject.getDataException();
    this.msg = datasObject.getProperties(datasObject.getConst().idiomaEs);
    this.const = datasObject.getConst();
    this.util = util;
    this.mensajeError = this.msg.lbl_mensaje_error_404;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
  }

  // Procesos que se ejecutan cuando el DOM se carga
  ngAfterViewInit() {
  }

  irHome() {
    let audio = new Audio();
    audio.src = "assets/audio/crash.mp3";
    audio.load();
    audio.play();
    this.router.navigate(['/home']);
  }
}
