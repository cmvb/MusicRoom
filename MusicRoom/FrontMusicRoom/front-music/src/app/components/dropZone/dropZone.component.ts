import { map } from 'rxjs/operators'
import { Util } from '../Util';
import { DataObjects } from '../ObjectGeneric';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Component, OnInit, Input, forwardRef, Inject, Output, EventEmitter, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { format } from 'url';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { Observable } from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';
import { RestService } from '../../services/rest.service';
import { DropzoneComponent, DropzoneDirective, DropzoneConfigInterface } from 'ngx-dropzone-wrapper';

declare var $: any;

@Component({
  selector: 'app-drop-zone',
  templateUrl: './dropZone.component.html',
  styleUrls: ['./dropZone.component.scss']
})

export class DropZoneComponent implements OnInit {
  idModal: any;
  claseModal: any;
  confirmModal: boolean[];
  tituloModal: any;
  cuerpoModal: any;
  ruteModal: any;
  closeModal: boolean[];

  objeto: any;
  mensaje: any;


  @Input() apiUrlInput: any;

  public type: string = 'component';
  public disabled: boolean = false;

  public config: DropzoneConfigInterface = {
    clickable: true,
    maxFiles: 1,
    autoReset: null,
    errorReset: null,
    cancelReset: null,
    headers: { 'Access-Control-Allow-Origin': 'http://localhost:4200/', 'Access-Control-Allow-Credentials': true, 'Access-Control-Allow-Methods': 'POST, GET, PUT, UPDATE, OPTIONS', 'Access-Control-Allow-Headers': 'Content-Type, Accept, X-Requested-With' }
  };

  @ViewChild(DropzoneComponent) componentRef: DropzoneComponent;
  @ViewChild(DropzoneDirective) directiveRef: DropzoneDirective;

  apiUrl: any;
  headers = new Headers({ 'Content-Type': 'application/json' });
  options = new RequestOptions({ headers: this.headers });

  constructor(private http: Http, private router: Router, datasObject: DataObjects) {
    this.idModal = [10, 20, 30, 40];
    this.claseModal = ['info', 'success', 'warning', 'danger'];
    this.confirmModal = [false];
    this.tituloModal = ['INFORMACIÓN', 'PROCESO EXITOSO', 'ADVERTENCIA', 'ERROR'];
    this.ruteModal = ['#'];
    this.cuerpoModal = ['Cargando Resultados', 'Proceso realizado Satisfactoriamente', 'XXX',];
    this.closeModal = [false, true];


    this.objeto = datasObject.getDataUsuario();
  }

  ngOnInit() {
    this.apiUrl = this.apiUrlInput;
  }

  public toggleType(): void {
    this.type = (this.type === 'component') ? 'directive' : 'component';
  }

  public toggleDisabled(): void {
    this.disabled = !this.disabled;
  }

  public toggleAutoReset(): void {
    this.config.autoReset = this.config.autoReset ? null : 5000;
    this.config.errorReset = this.config.errorReset ? null : 5000;
    this.config.cancelReset = this.config.cancelReset ? null : 5000;
  }

  public toggleMultiUpload(): void {
    this.config.maxFiles = this.config.maxFiles ? null : 1;
  }

  public toggleClickAction(): void {
    this.config.clickable = !this.config.clickable;
  }

  public resetDropzoneUploads(): void {
    if (this.type === 'directive') {
      this.directiveRef.reset();
    } else {
      this.componentRef.directiveRef.reset();
    }
  }

  public onUploadError(args: any): void {
    console.log('onUploadError:', args);
  }

  public onUploadSuccess(args: any): void {
    console.log('onUploadSuccess:', args);
  }

  importar(objetoFile): Observable<Response> {
    return this.http.post(this.apiUrl, objetoFile, this.options);
  }

  uploadFile(event) {
    let inputFileImage: any = document.getElementById("fileUp");
    let idModalInicial = 10;
    this.classToggleModalParam(this.idModal);

    let http = this.http;
    let apiUrl = this.apiUrl;
    let options = this.options;
    let objeto = this.objeto;
    let mensaje = this.mensaje;
    event.preventDefault();

    let file = event.dataTransfer.files[0];
    if (!$('#textoImage').hasClass('ocultar')) {
      $('#textoImage').addClass('ocultar');
    }

    var reader = new FileReader();//instanciamos FileReader
    reader.onloadend = (function (f) {//creamos la función que recogerá los datos
      return function (e) {
        var content = e.target.result.split(",", 2)[1];//obtenemos el contenido del archivo, estará codificado en Base64
        var decodedData = window.atob(content);
        objeto.file = content;
        objeto.filename = file.name;
        objeto.contentDecodeB64 = decodedData;
        let extension = file.name.split('.');
        objeto.mimeType = '.' + extension[extension.length - 1];

        let data: any = http.post(apiUrl, objeto, options);
        data.toPromise().then(data => {
          let temporal = data.json();
          if (temporal.statusId == 200) {
            if (!$('#errorImage').hasClass('ocultar')) {
              $('#errorImage').addClass('ocultar');
            }
            if ($('#responseImage').hasClass('ocultar')) {
              $('#responseImage').removeClass('ocultar');
            }

            $('#' + idModalInicial).toggleClass('show');
            $('#' + idModalInicial).toggleClass('modalVisible');

            let idModal = 20;
            $('#' + idModal).toggleClass('show');
            $('#' + idModal).toggleClass('modalVisible');
          }
          else {
            let salida = 'ERRORES' + '\n';

            let i = 1;
            temporal.errores.forEach(function (linea) {
              console.log(linea);
              salida = salida + linea + '\n';
              i++;
            });

            var element = document.createElement('a');
            element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(salida));
            element.setAttribute('download', 'erroresImportandoInsumos.csv');

            element.style.display = 'none';
            document.body.appendChild(element);

            element.click();

            document.body.removeChild(element);

            mensaje = temporal.statusMsg;
            if (!$('#responseImage').hasClass('ocultar')) {
              $('#responseImage').addClass('ocultar');
            }
            if ($('#errorImage').hasClass('ocultar')) {
              $('#errorImage').removeClass('ocultar');
            }

            $('#' + idModalInicial).toggleClass('show');
            $('#' + idModalInicial).toggleClass('modalVisible');

            let idModal = 30;
            let cuerpoModal = mensaje;
            $('#' + idModal + ' .replc').html(function (buscayreemplaza, reemplaza) {
              return reemplaza.replace('XXX', cuerpoModal);
            });

            $('#' + idModal).toggleClass('show');
            $('#' + idModal).toggleClass('modalVisible');
          }

          inputFileImage.form.reset()

          return true;
        }).catch(data => {
          mensaje = 'No hay Conexión a la Base de Datos';
          if (!$('#responseImage').hasClass('ocultar')) {
            $('#responseImage').addClass('ocultar');
          }
          if ($('#errorImage').hasClass('ocultar')) {
            $('#errorImage').removeClass('ocultar');
          }

          $('#' + idModalInicial).toggleClass('show');
          $('#' + idModalInicial).toggleClass('modalVisible');

          let idModal = 40;
          let cuerpoModal = mensaje;
          $('#' + idModal + ' .replc').html(function (buscayreemplaza, reemplaza) {
            return reemplaza.replace('XXX', cuerpoModal);
          });

          $('#' + idModal).toggleClass('show');
          $('#' + idModal).toggleClass('modalVisible');

          inputFileImage.form.reset()

          return false;
        });
      }
    })(event.dataTransfer.files[0]);
    reader.readAsDataURL(event.dataTransfer.files[0]);
  }

  dragOver(event) {
    event.preventDefault();
  }

  startFileUp() {
    $('#fileUp').click();
    return true;
  }

  uploadAjax() {
    let idModalInicial = 10;
    this.classToggleModalParam(this.idModal);

    let http = this.http;
    let apiUrl = this.apiUrl;
    let options = this.options;
    let objeto = this.objeto;
    let mensaje = this.mensaje;

    let inputFileImage: any = document.getElementById("fileUp");
    let file = inputFileImage.files[0];
    if (!$('#textoImage').hasClass('ocultar')) {
      $('#textoImage').addClass('ocultar');
    }

    var reader = new FileReader();//instanciamos FileReader
    reader.onloadend = (function (f) {//creamos la función que recogerá los datos
      return function (e) {
        var content = e.target.result.split(",", 2)[1];//obtenemos el contenido del archivo, estará codificado en Base64
        var decodedData = window.atob(content);
        objeto.file = content;
        objeto.filename = file.name;
        objeto.contentDecodeB64 = decodedData;
        let extension = file.name.split('.');
        objeto.mimeType = '.' + extension[extension.length - 1];

        let data: any = http.post(apiUrl, objeto, options);
        data.toPromise().then(data => {
          let temporal = data.json();
          if (temporal.statusId == 200) {
            if (!$('#errorImage').hasClass('ocultar')) {
              $('#errorImage').addClass('ocultar');
            }
            if ($('#responseImage').hasClass('ocultar')) {
              $('#responseImage').removeClass('ocultar');
            }

            $('#' + idModalInicial).toggleClass('show');
            $('#' + idModalInicial).toggleClass('modalVisible');

            let idModal = 20;

            $('#' + idModal).toggleClass('show');
            $('#' + idModal).toggleClass('modalVisible');
          }
          else {
            let salida = 'ERRORES' + '\n';

            let i = 1;
            temporal.errores.forEach(function (linea) {
              console.log(linea);
              salida = salida + linea + '\n';
              i++;
            });

            var element = document.createElement('a');
            element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(salida));
            element.setAttribute('download', 'erroresImportandoInsumos.csv');

            element.style.display = 'none';
            document.body.appendChild(element);

            element.click();

            document.body.removeChild(element);

            mensaje = temporal.statusMsg;
            if (!$('#responseImage').hasClass('ocultar')) {
              $('#responseImage').addClass('ocultar');
            }
            if ($('#errorImage').hasClass('ocultar')) {
              $('#errorImage').removeClass('ocultar');
            }

            $('#' + idModalInicial).toggleClass('show');
            $('#' + idModalInicial).toggleClass('modalVisible');

            let idModal = 30;
            let cuerpoModal = mensaje;
            $('#' + idModal + ' .replc').html(function (buscayreemplaza, reemplaza) {
              return reemplaza.replace('XXX', cuerpoModal);
            });

            $('#' + idModal).toggleClass('show');
            $('#' + idModal).toggleClass('modalVisible');
          }

          inputFileImage.form.reset();

          return true;
        }).catch(data => {
          mensaje = 'No hay Conexión a la Base de Datos';
          if (!$('#responseImage').hasClass('ocultar')) {
            $('#responseImage').addClass('ocultar');
          }
          if ($('#errorImage').hasClass('ocultar')) {
            $('#errorImage').removeClass('ocultar');
          }

          $('#' + idModalInicial).toggleClass('show');
          $('#' + idModalInicial).toggleClass('modalVisible');

          let idModal = 40;
          let cuerpoModal = mensaje;
          $('#' + idModal + ' .replc').html(function (buscayreemplaza, reemplaza) {
            return reemplaza.replace('XXX', cuerpoModal);
          });

          $('#' + idModal).toggleClass('show');
          $('#' + idModal).toggleClass('modalVisible');

          inputFileImage.form.reset();

          return false;
        });
      }
    })(inputFileImage.files[0]);
    reader.readAsDataURL(inputFileImage.files[0]);
  }

  classToggleModal() {
    $('#' + this.idModal).toggleClass('show');
    $('#' + this.idModal).toggleClass('modalVisible');

    return true;
  }

  classToggleModalParam(id) {
    $('#' + id).toggleClass('show');
    $('#' + id).toggleClass('modalVisible');

    return true;
  }

}