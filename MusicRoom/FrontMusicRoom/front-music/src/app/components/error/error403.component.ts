import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { RestService } from '../.././services/rest.service';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { Enumerados } from 'src/app/config/Enumerados';
import { SesionService } from 'src/app/services/sesionService/sesion.service';


@Component({
  selector: 'app-error403',
  templateUrl: './error403.component.html',
  styleUrls: ['./error.component.scss'],
  providers: [RestService, MessageService]
})
export class Error403Component implements OnInit {
  // Objetos de Datos
  mensajeError: any;
  decodedToken: any;
  expirationDate: any;

  // Utilidades
  msg: any;
  const: any;

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService) {
    this.msg = this.textProperties.getProperties(this.sesionService.objServiceSesion.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.mensajeError = this.sesionService.objServiceSesion.mensajeError403;
    this.decodedToken = this.sesionService.objServiceSesion.decodedToken;
    this.expirationDate = this.sesionService.objServiceSesion.expirationDate;
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
