import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import { MessageService } from 'primeng/api';
import 'rxjs/add/operator/map';
import { DataObjects } from '../.././components/ObjectGeneric';
import { Util } from '../.././components/Util';
import { RestService } from '../.././services/rest.service';
import { DomSanitizer } from '@angular/platform-browser';
import { bypassSanitizationTrustResourceUrl } from '@angular/core/src/sanitization/bypass';

declare var $: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [RestService, MessageService]
})
export class DashboardComponent implements OnInit {
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
  ex: any;
  objeto: any;
  msgs = [];
  data: any;

  // Utilidades
  util: any;
  msg: any;
  const: any;
  locale: any;
  maxDate = new Date();


  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util, private messageService: MessageService, private sanitization: DomSanitizer) {
    this.sesion = datasObject.getDataSesion();
    this.ex = datasObject.getDataException();
    this.msg = datasObject.getProperties(datasObject.getConst().idiomaEs);
    this.const = datasObject.getConst();
    this.util = util;
    this.locale = datasObject.getLocaleESForCalendar();
    this.util = util;
    this.srcImg = 'assets/images/icons/';
    this.selectedFiles = undefined;
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
    this.ex = this.util.limpiarExcepcion();
    this.msgs = [];
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

  selectFiles(e: any) {
    console.log(e.target.files);
    this.currentFileUpload = e.target.files[0];
    this.nameFile = e.target.files[0].name;
    this.selectedFiles = e.target.files;
  }

  upload() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerReporte + 'guardarArchivo';
      let obj = this.currentFileUpload;

      this.restService.postFileREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;

          this.selectedFiles = undefined;
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
