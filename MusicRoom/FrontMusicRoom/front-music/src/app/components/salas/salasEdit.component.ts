import { Component, OnInit } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import { MessageService } from 'primeng/api';
import { map } from 'rxjs/operators'
import { RestService } from '../../services/rest.service';
import { DataObjects } from '../ObjectGeneric';
import { Util } from '../Util';
import { DomSanitizer } from '@angular/platform-browser';

declare var $: any;
declare function cargarCarousels(): any;

@Component({
  selector: 'app-salas-edit',
  templateUrl: './salasEdit.component.html',
  styleUrls: ['./salas.component.scss'],
  providers: [RestService, MessageService]
})
export class SalasEditComponent implements OnInit {
  // Objetos de Sesion
  ACCESS_TOKEN: any;
  usuarioSesion: any;
  sesion: any;

  // Utilidades
  util: any;
  msg: any;
  const: any;
  ex: any;
  isDisabled: boolean;

  // Objetos de Datos
  phase: any;
  data: any;
  listaConsulta = [];
  objeto: any;

  // Enumerados
  enums: any;
  enumSiNo = [];
  listaTerceros = [];
  enumFiltroTerceros = [];
  terceroSeleccionado: any;

  // Propiedades de las peticiones REST
  headers = new Headers({ 'Content-Type': 'application/json' });
  options = new RequestOptions({ headers: this.headers });

  // Archivos
  imagenData: any;
  labelEscogido: any;
  acceptStr: any;
  srcFotoPrincipal: any;
  srcFoto1: any;
  srcFoto2: any;
  srcFoto3: any;
  srcFoto4: any;
  currentFileUploadPrincipal: File;
  currentFileUpload1: File;
  currentFileUpload2: File;
  currentFileUpload3: File;
  currentFileUpload4: File;

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util, private messageService: MessageService, private sanitizer: DomSanitizer) {
    this.usuarioSesion = datasObject.getDataUsuario();
    this.sesion = datasObject.getDataSesion();
    this.msg = datasObject.getProperties(datasObject.getConst().idiomaEs);
    this.const = datasObject.getConst();
    this.util = util;
    this.objeto = datasObject.getDataSala();
    this.objeto.estado = 1;
    this.objeto.terceroTb = datasObject.getDataTercero();
    this.enums = datasObject.getEnumerados();
    this.ACCESS_TOKEN = JSON.parse(sessionStorage.getItem(this.const.tokenNameAUTH)).access_token;
    this.acceptStr = "image/*";
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.util.limpiarSesionXItem(['mensajeConfirmacion']);
    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);
    this.listaTerceros = JSON.parse(localStorage.getItem('listaTerceros'));
    this.enumFiltroTerceros = this.util.obtenerEnumeradoDeListaTercero(this.listaTerceros);

    this.phase = this.util.getSesionXItem('phase');
    this.isDisabled = this.phase !== this.const.phaseAdd;

    if (this.util.getSesionXItem('editParam') != null) {
      this.objeto = JSON.parse(localStorage.getItem('editParam'));
      this.terceroSeleccionado = { value: this.objeto.terceroTb, label: this.objeto.terceroTb.razonSocial };
    }

    this.inicializarFotos();
    this.inicializarCombos();
    this.activarFoto(0);
  }

  inicializarFotos() {
    if (this.phase === this.const.phaseAdd) {
      this.objeto.fotoPrincipalTb = this.dataGenericaArchivo();
      this.objeto.foto1Tb = this.dataGenericaArchivo();
      this.objeto.foto2Tb = this.dataGenericaArchivo();
      this.objeto.foto3Tb = this.dataGenericaArchivo();
      this.objeto.foto4Tb = this.dataGenericaArchivo();
      this.srcFotoPrincipal = null;
      this.srcFoto1 = null;
      this.srcFoto2 = null;
      this.srcFoto3 = null;
      this.srcFoto4 = null;
    } else {
      this.sanitizarUrlImgCargada(this.objeto.fotoPrincipalTb.valor, 0);
      if (this.objeto.foto1Tb !== null) {
        this.sanitizarUrlImgCargada(this.objeto.foto1Tb.valor, 1);
      } else {
        this.objeto.foto1Tb = this.dataGenericaArchivo();
        this.srcFoto1 = null;
      }
      if (this.objeto.foto2Tb !== null) {
        this.sanitizarUrlImgCargada(this.objeto.foto2Tb.valor, 2);
      } else {
        this.objeto.foto2Tb = this.dataGenericaArchivo();
        this.srcFoto2 = null;
      }
      if (this.objeto.foto3Tb !== null) {
        this.sanitizarUrlImgCargada(this.objeto.foto3Tb.valor, 3);
      } else {
        this.objeto.foto3Tb = this.dataGenericaArchivo();
        this.srcFoto3 = null;
      }
      if (this.objeto.foto4Tb !== null) {
        this.sanitizarUrlImgCargada(this.objeto.foto4Tb.valor, 4);
      } else {
        this.objeto.foto4Tb = this.dataGenericaArchivo();
        this.srcFoto4 = null;
      }
    }
  }

  cargarImagen(data: any, i) {
    let reader = new FileReader();


    var binary = '';
    var bytes = new Uint8Array(data);
    var len = bytes.byteLength;
    for (let k = 0; k < len; k++) {
      binary += String.fromCharCode(bytes[k]);
    }
    let base64String = btoa(String.fromCharCode.apply(null, new Uint8Array(data)));


    reader.readAsDataURL(data);
    reader.onloadend = () => {
      let dato = reader.result;
      this.sanitizarUrlImgCargada(dato, i);
    }
  }
  
  sanitizarUrlImgCargada(dato: any, i) {
    switch (i.toString()) {
      case '0':
        this.srcFotoPrincipal = 'data:image/png;base64,' + dato;
        break;
      case '1':
        this.srcFoto1 = 'data:image/png;base64,' + dato;
        break;
      case '2':
        this.srcFoto2 = 'data:image/png;base64,' + dato;
        break;
      case '3':
        this.srcFoto3 = 'data:image/png;base64,' + dato;
        break;
      case '4':
        this.srcFoto4 = 'data:image/png;base64,' + dato;
        break;
    }
  }

  activarFoto(i) {
    $('.seccionFoto').hide();
    $('.card-af').removeClass('bg-c-orange');
    switch (i.toString()) {
      case '0':
        $('#idActivarFotoPrincipal').addClass('bg-c-orange');
        $('#sectionFotoPrincipal').show();
        break;
      case '1':
        $('#idActivarFoto1').addClass('bg-c-orange');
        $('#sectionFoto1').show();
        break;
      case '2':
        $('#idActivarFoto2').addClass('bg-c-orange');
        $('#sectionFoto2').show();
        break;
      case '3':
        $('#idActivarFoto3').addClass('bg-c-orange');
        $('#sectionFoto3').show();
        break;
      case '4':
        $('#idActivarFoto4').addClass('bg-c-orange');
        $('#sectionFoto4').show();
        break;
    }
  }

  dataGenericaArchivo() {
    return {
      idArchivo: '',
      nombreArchivo: '',
      tipoArchivo: '',
      rutaArchivo: '',
      valor: '',

      //Abstract
      estado: '',
      usuarioCreacion: '',
      fechaCreacion: '',
      usuarioActualiza: '',
      fechaActualiza: ''
    }
  }

  clickFileUp(id) {
    $('#' + id).click();
  }

  onUpload(event, i) {
    try {
      let flagFileUpload = typeof event !== 'undefined' && typeof event.files !== 'undefined' && event.files !== 'null' && event.files !== null;
      let flagPathUpload = typeof event.path[0] !== 'undefined' && typeof event.path[0].files !== 'undefined' && event.path[0].files !== 'null' && event.path[0].files !== null;
      let flagDragAndDrop = typeof event.dataTransfer !== 'undefined' && typeof event.dataTransfer.files !== 'undefined' && event.dataTransfer.files !== 'null' && event.dataTransfer.files !== null;

      if (flagFileUpload || flagDragAndDrop || flagPathUpload) {
        let files = flagFileUpload ? event.files : (flagDragAndDrop ? event.dataTransfer.files : (flagPathUpload ? event.path[0].files : null));
        if (files !== null && files.length === 1) {
          let file = files[0];
          if (file !== null) {
            // Validaciones de archivo
            debugger;
            if (this.validarArchivo(file)) {
              // Si el tipo de archivo es aceptado
              let flag = false;
              switch (i.toString()) {
                case '0':
                  this.objeto.fotoPrincipalTb.nombreArchivo = file.name;
                  this.currentFileUploadPrincipal = file;
                  flag = true;

                  break;
                case '1':
                  this.objeto.foto1Tb.nombreArchivo = file.name;
                  this.currentFileUpload1 = file;
                  flag = true;

                  break;
                case '2':
                  this.objeto.foto2Tb.nombreArchivo = file.name;
                  this.currentFileUpload2 = file;
                  flag = true;

                  break;
                case '3':
                  this.objeto.foto3Tb.nombreArchivo = file.name;
                  this.currentFileUpload3 = file;
                  flag = true;

                  break;
                case '4':
                  this.objeto.foto4Tb.nombreArchivo = file.name;
                  this.currentFileUpload4 = file;
                  flag = true;

                  break;
              }

              if (flag) {
                if (flagDragAndDrop) {
                  event.preventDefault();
                }
                this.sanitizarUrl(file, i, event);
              }
            }
          } else {
            this.messageService.clear();
            this.messageService.add({ severity: this.const.severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mensaje_archivo_no_subido });
          }
        } else if (files.length > 1) {
          this.messageService.clear();
          this.messageService.add({ severity: this.const.severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mensaje_cant_archivos_permitidos });
        } else if (files.length === 0) {
          this.messageService.clear();
          this.messageService.add({ severity: this.const.severity[0], summary: this.msg.lbl_summary_info, detail: this.msg.lbl_mensaje_seleccione_archivo_para_subir });
        }
      } else {
        this.messageService.clear();
        this.messageService.add({ severity: this.const.severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mensaje_archivo_no_subido });
      }
    } catch (error) {
      this.messageService.clear();
      this.messageService.add({ severity: this.const.severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mensaje_archivo_no_subido });
      event.preventDefault();
    }
  }

  validarArchivo(archivo: any) {
    let valido = true;
    try {
      this.messageService.clear();
      let flagRegex = this.acceptStr.includes('*');
      let flagRegexOpc1 = flagRegex && archivo.type.toUpperCase().includes(this.acceptStr.toUpperCase().replace('*', ''));
      let flagRegexOpc2 = !flagRegex && archivo.type.toUpperCase() === this.acceptStr.toUpperCase();

      // Validar Tipo
      if (!flagRegexOpc1 && !flagRegexOpc2) {
        valido = false;
        this.messageService.add({ severity: this.const.severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mensaje_tipo_archivos_permitidos_generico });
      }

      // Validar Tamaño
      if (archivo.size > 1000000) {
        valido = false;
        this.messageService.add({ severity: this.const.severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mensaje_size_archivos_permitidos_detalle + archivo.size + ' bytes.' });
      }
    } catch (e) {
      valido = false;
      console.log(e);
    }

    return valido;
  }

  sanitizarUrl(data: any, i, event) {
    try {
      let subirArchivoA = localStorage.getItem('fileUploadTo');
      let reader = new FileReader();
      event.preventDefault();
      reader.readAsDataURL(data);
      reader.onloadend = () => {
        let dato = reader.result;
        this.guardarArchivoServidor(i, dato);
      }
    } catch (error) { console.log(error) }
  }

  dragOver(event) {
    event.preventDefault();
  }

  limpiarExcepcion() {
    this.ex = this.util.limpiarExcepcion();
  }

  irGuardar() {
    try {
      if (this.objeto.fotoPrincipalTb.valor != null) {
        this.limpiarExcepcion();
        let url = this.const.urlRestService + this.const.urlControllerSala + (this.phase === this.const.phaseAdd ? 'crearSala' : 'modificarSala');
        this.ajustarCombos();
        let obj = this.objeto;

        this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
          .subscribe(resp => {
            console.log(resp, "res");
            this.data = resp;
            let mensajeConfirmacion = 'La Sala #' + this.data.idSala + ' fue ' + (this.phase === this.const.phaseAdd ? 'creada' : 'actualizada') + ' satisfactoriamente.';
            this.util.agregarSesionXItem([{ item: 'mensajeConfirmacion', valor: mensajeConfirmacion }]);
            this.irAtras();
          },
            error => {
              this.ex = error.error;
              let mensaje = this.util.mostrarNotificacion(this.ex);
              this.messageService.clear();
              this.messageService.add(mensaje);

              this.inicializarCombos();
              console.log(error, "error");
            });
      } else {
        this.messageService.clear();
        this.messageService.add({ severity: this.const.severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mensaje_seleccione_archivo_para_subir });
      }
    } catch (e) {
      console.log(e);
    }
  }

  ajustarCombos() {
    this.objeto.estado = this.objeto.estado === undefined ? null : this.objeto.estado.value;
    if (this.terceroSeleccionado != null) {
      let tercero = this.util.obtenerTerceroDeEnum(this.terceroSeleccionado.value.idTercero, this.listaTerceros);
      Object.assign(this.objeto.terceroTb, tercero);
    }
    else {
      this.objeto.terceroTb = {};
    }
  }

  inicializarCombos() {
    this.objeto.estado = this.util.getValorEnumerado(this.enumSiNo, this.objeto.estado);
  }

  irAtras() {
    this.util.limpiarSesionXItem(['listaConsulta']);
    this.router.navigate(['/salaQuery']);
  }

  guardaTeclaEnter(event) {
    if (event.which === 13) {
      this.irGuardar();
    }
  }

  guardarArchivoServidor(i, dato) {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerReporte + 'guardarArchivo';
      let obj: File;
      switch (i.toString()) {
        case '0':
          this.srcFotoPrincipal = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          obj = this.currentFileUploadPrincipal;

          break;
        case '1':
          this.srcFoto1 = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          obj = this.currentFileUpload1;

          break;
        case '2':
          this.srcFoto2 = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          obj = this.currentFileUpload2;

          break;
        case '3':
          this.srcFoto3 = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          obj = this.currentFileUpload3;

          break;
        case '4':
          this.srcFoto4 = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          obj = this.currentFileUpload4;

          break;
      }

      this.restService.postSecureFileREST(url, obj, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;

          switch (i.toString()) {
            case '0':
              this.objeto.fotoPrincipalTb = this.data;
              break;
            case '1':
              this.objeto.foto1Tb = this.data;
              break;
            case '2':
              this.objeto.foto2Tb = this.data;
              break;
            case '3':
              this.objeto.foto3Tb = this.data;
              break;
            case '4':
              this.objeto.foto4Tb = this.data;
              break;
          }

          this.messageService.clear();
          this.messageService.add({ severity: this.const.severity[0], summary: this.msg.lbl_summary_info, detail: this.msg.lbl_mensaje_archivo_subido });
          localStorage.setItem('fileUploadTo', null);
        },
          error => {
            this.ex = error.error;
            let mensaje = this.util.mostrarNotificacion(this.ex);
            this.messageService.clear();
            this.messageService.add(mensaje);

            switch (i.toString()) {
              case '0':
                this.objeto.fotoPrincipalTb = this.dataGenericaArchivo();
                this.srcFotoPrincipal = null;

                break;
              case '1':
                this.objeto.foto1Tb = this.dataGenericaArchivo();
                this.srcFoto1 = null;

                break;
              case '2':
                this.objeto.foto2Tb = this.dataGenericaArchivo();
                this.srcFoto2 = null;

                break;
              case '3':
                this.objeto.foto3Tb = this.dataGenericaArchivo();
                this.srcFoto3 = null;

                break;
              case '4':
                this.objeto.foto4Tb = this.dataGenericaArchivo();
                this.srcFoto4 = null;

                break;
            }

            console.log(error, "error");
          });
    } catch (e) {
      console.log(e);
    }
  }
}