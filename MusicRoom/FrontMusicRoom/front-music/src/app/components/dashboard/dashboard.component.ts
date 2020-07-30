import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { RestService } from '../.././services/rest.service';
import { DomSanitizer } from '@angular/platform-browser';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { Enumerados } from 'src/app/config/Enumerados';
import { SesionService } from 'src/app/services/sesionService/sesion.service';

declare var $: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [RestService, MessageService]
})
export class DashboardComponent implements OnInit {
  ACCESS_TOKEN: any;
  availableCars: any[];
  selectedCars: any[];
  draggedCar: any;
  srcImg: any;

  // Objetos de Sesion
  usuarioSesion: any;
  sesion: any;
  pdfSrc: string = '/assets/pdf/pago2.pdf';
  nameFile: any;
  selectedFiles: FileList;
  currentFileUpload: File;
  imagenData: any;
  imagenEstado = false;

  // Objetos de Datos
  objeto: any;
  data: any;

  // Utilidades
  msg: any;
  const: any;
  locale: any;
  maxDate = new Date();


  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService, private sanitization: DomSanitizer) {
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.locale = this.sesionService.idioma === this.objectModelInitializer.getConst().idiomaEs ? this.objectModelInitializer.getLocaleESForCalendar() : this.objectModelInitializer.getLocaleENForCalendar();
    this.srcImg = 'assets/images/icons/';
    this.selectedFiles = undefined;
    this.ACCESS_TOKEN =  this.sesionService.tokenSesion.token;
  }

  ngOnInit() {
    this.selectedCars = [];
    this.availableCars = [
      { id: 1, label: 'obj1', srcImg: this.srcImg + 'About.gif' },
      { id: 2, label: 'obj2', srcImg: this.srcImg + '200-add.gif' },
      { id: 3, label: 'obj3', srcImg: this.srcImg + '0001-monitor.gif' },
      { id: 4, label: 'obj4', srcImg: this.srcImg + '0025-search.gif' },
      { id: 5, label: 'obj5', srcImg: this.srcImg + 'books_016.gif' },
    ];

    this.leerArchivo();
  }

  dragStart(event, car: any) {
    this.draggedCar = car;
  }

  drop(event) {
    if (this.draggedCar) {
      let draggedCarIndex = this.findIndex(this.draggedCar);
      this.selectedCars = [...this.selectedCars, this.draggedCar];
      this.availableCars = this.availableCars.filter((val, i) => i != draggedCarIndex);
      this.draggedCar = null;
    }
  }

  dragEnd(event) {
    this.draggedCar = null;
  }

  findIndex(car: any) {
    let index = -1;
    for (let i = 0; i < this.availableCars.length; i++) {
      if (car.label === this.availableCars[i].label) {
        index = i;
        break;
      }
    }
    return index;
  }

  limpiarExcepcion() {
    console.clear();
    this.messageService.clear();
  }

  generarReporte() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerReporte + 'generarReporteEJM';

      this.restService.getFileREST(url)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          let reader = new FileReader();
          reader.onload = (e: any) => {
            console.log(e.target.result);
            this.pdfSrc = e.target.result;
          }
          reader.readAsArrayBuffer(this.data);
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

  descargarReporte() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerReporte + 'generarReporteEJM';

      this.restService.getFileREST(url)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;

          const url = window.URL.createObjectURL(this.data);
          const a = document.createElement('a');
          a.setAttribute('style', 'display:none');
          a.href = url;
          a.download = 'archivo.pdf';
          a.click();
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

  selectFiles(e: any) {
    this.currentFileUpload = e.target.files[0];
    this.nameFile = e.target.files[0].name;
    this.selectedFiles = e.target.files;
  }

  upload() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerReporte + 'guardarArchivo';
      let obj = this.currentFileUpload;

      this.restService.postSecureFileREST(url, this.currentFileUpload, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;

          this.selectedFiles = undefined;
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

  leerArchivo() {
    try {
      this.limpiarExcepcion();
      debugger;
      let url = this.const.urlRestService + this.const.urlControllerReporte + 'leerArchivo/1';

      this.restService.getFileREST(url)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;

          this.convertirArchivo(this.data);
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

  accionImagen(accion: String) {
    if (accion === "M") {
      this.imagenEstado = true;
    } else {
      this.imagenEstado = false;
    }
  }

  convertirArchivo(data: any) {
    let reader = new FileReader();
    reader.readAsDataURL(data);
    reader.onloadend = () => {
      let dato = reader.result;
      this.sanitizarUrl(dato);
    }
  }

  sanitizarUrl(dato: any) {
    this.imagenData = this.sanitization.bypassSecurityTrustResourceUrl(dato);
  }
}
