import { Component, OnInit } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import { MessageService } from 'primeng/api';
import { RestService } from '../../services/rest.service';
import { DomSanitizer } from '@angular/platform-browser';
import { LocationService } from 'src/app/services/location.service';
import { MapsAPILoader, GoogleMapsAPIWrapper, AgmMap } from "@agm/core";
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { Enumerados } from 'src/app/config/Enumerados';
import { SesionService } from 'src/app/services/sesionService/sesion.service';
import { BandaIntegranteService } from 'src/app/services/bandaIntegranteService/bandaIntegrante.service';

declare var google: any;
declare var $: any;

@Component({
  selector: 'app-bandas-integrantes-edit',
  templateUrl: './bandasIntegrantesEdit.component.html',
  styleUrls: ['./bandasIntegrantes.component.scss'],
  providers: [RestService, MessageService]
})
export class BandasIntegrantesEditComponent implements OnInit {
  // Objetos de Sesion
  ACCESS_TOKEN: any;
  usuarioSesion: any;
  sesion: any;

  // Utilidades
  msg: any;
  const: any;
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

  // Mapa
  optionsMap: any;
  overlays: any[];
  dialogVisible: boolean;
  markerTitle: string;
  selectedPosition: any;
  infoWindow: any;
  draggable: boolean;
  API_GOOGLE_KEY_MAP = 'AIzaSyBaNBQN5zBRz7h5lUKB4GGZQHhakKrajSA';
  map: google.maps.Map;

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public locationService: LocationService, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService, public bandaIntegranteService: BandaIntegranteService, private sanitizer: DomSanitizer, private mapsAPILoader: MapsAPILoader) {
    this.optionsMap = null;
    this.mapsAPILoader.load().then(() => {
      this.locationService.getLocation().subscribe(resp => {
        // Tomar Geo Location del usuario
        this.optionsMap = {
          center: { lat: resp.coords.latitude, lng: resp.coords.longitude },
          zoom: 12
        };

        this.infoWindow = new google.maps.InfoWindow();
        this.initOverlays();
      });
    });
    this.usuarioSesion = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.objeto = this.objectModelInitializer.getDataSala();
    this.objeto.estado = this.const.estadoActivoNumString
    this.objeto.terceroTb = this.objectModelInitializer.getDataTercero();
    this.enums = this.enumerados.getEnumerados();
    this.ACCESS_TOKEN = this.sesionService.tokenSesion.token;
    this.acceptStr = "image/*";
  }

  // Cuando el mapa esté listo para usarse
  setMap(event) {

  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {

  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.sesionService.mensajeConfirmacion = undefined;
    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);
    this.listaTerceros = this.bandaIntegranteService.listaTerceros;
    this.enumFiltroTerceros = this.util.obtenerEnumeradoDeListaTercero(this.listaTerceros);

    this.phase = this.sesionService.phase;
    this.isDisabled = this.phase !== this.const.phaseAdd;

    if (typeof this.bandaIntegranteService.editParam !== 'undefined' && this.bandaIntegranteService.editParam !== null) {
      this.objeto = this.bandaIntegranteService.editParam;
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
      this.sanitizarUrlImgCargada(this.objeto.fotoPrincipalTb.valor, 0, this.objeto.fotoPrincipalTb.tipoArchivo);
      if (this.objeto.foto1Tb !== null) {
        this.sanitizarUrlImgCargada(this.objeto.foto1Tb.valor, 1, this.objeto.foto1Tb.tipoArchivo);
      } else {
        this.objeto.foto1Tb = this.dataGenericaArchivo();
        this.srcFoto1 = null;
      }
      if (this.objeto.foto2Tb !== null) {
        this.sanitizarUrlImgCargada(this.objeto.foto2Tb.valor, 2, this.objeto.foto2Tb.tipoArchivo);
      } else {
        this.objeto.foto2Tb = this.dataGenericaArchivo();
        this.srcFoto2 = null;
      }
      if (this.objeto.foto3Tb !== null) {
        this.sanitizarUrlImgCargada(this.objeto.foto3Tb.valor, 3, this.objeto.foto3Tb.tipoArchivo);
      } else {
        this.objeto.foto3Tb = this.dataGenericaArchivo();
        this.srcFoto3 = null;
      }
      if (this.objeto.foto4Tb !== null) {
        this.sanitizarUrlImgCargada(this.objeto.foto4Tb.valor, 4, this.objeto.foto4Tb.tipoArchivo);
      } else {
        this.objeto.foto4Tb = this.dataGenericaArchivo();
        this.srcFoto4 = null;
      }
    }
  }

  sanitizarUrlImgCargada(dato: any, i, tipoArchivo) {
    if (tipoArchivo === 'svg') {
      switch (i.toString()) {
        case '0':
          this.srcFotoPrincipal = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/svg+xml;base64,' + dato);
        case '1':
          this.srcFoto1 = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/svg+xml;base64,' + dato);
          break;
        case '2':
          this.srcFoto2 = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/svg+xml;base64,' + dato);
          break;
        case '3':
          this.srcFoto3 = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/svg+xml;base64,' + dato);
          break;
        case '4':
          this.srcFoto4 = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/svg+xml;base64,' + dato);
          break;
      }
    } else {
      tipoArchivo = tipoArchivo + ';base64,';
      switch (i.toString()) {
        case '0':
          this.srcFotoPrincipal = 'data:image/' + tipoArchivo + dato;
          break;
        case '1':
          this.srcFoto1 = 'data:image/' + tipoArchivo + dato;
          break;
        case '2':
          this.srcFoto2 = 'data:image/' + tipoArchivo + dato;
          break;
        case '3':
          this.srcFoto3 = 'data:image/' + tipoArchivo + dato;
          break;
        case '4':
          this.srcFoto4 = 'data:image/' + tipoArchivo + dato;
          break;
      }
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
    console.clear();
    this.messageService.clear();
  }

  irGuardar() {
    try {
      if (this.objeto.fotoPrincipalTb.valor !== null) {
        this.limpiarExcepcion();
        let url = this.const.urlRestService + this.const.urlControllerSala + (this.phase === this.const.phaseAdd ? 'crearSala' : 'modificarSala');
        this.ajustarCombos();
        let obj = this.objeto;

        this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
          .subscribe(resp => {
            this.data = resp;
            let mensajeConfirmacion = this.msg.lbl_detail_el_registro + this.data.idSala + this.msg.lbl_detail_fue + (this.phase === this.const.phaseAdd ? this.msg.lbl_detail_creado : this.msg.lbl_detail_actualizado) + this.msg.lbl_detail_satisfactoriamente;
            this.sesionService.mensajeConfirmacion = mensajeConfirmacion;
            this.irAtras();
          },
            error => {
              let mensaje = this.util.mostrarNotificacion(error.error);
              this.messageService.clear();
              this.messageService.add(mensaje);

              this.inicializarCombos();
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
    if (this.terceroSeleccionado !== null) {
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
    this.bandaIntegranteService.listaConsulta = undefined;
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
        },
          error => {
            let mensaje = this.util.mostrarNotificacion(error.error);
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
          });
    } catch (e) {
      console.log(e);
    }
  }

  handleMapClick(event) {
    this.dialogVisible = true;
    this.selectedPosition = event.latLng;
  }

  handleOverlayClick(event) {
    let isMarker = event.overlay.getTitle != undefined;

    if (isMarker) {
      let title = event.overlay.getTitle();
      this.infoWindow.setContent('' + title + '');
      this.infoWindow.open(event.map, event.overlay);
      event.map.setCenter(event.overlay.getPosition());

      this.messageService.add({ severity: 'info', summary: 'Marker Selected', detail: title });
    }
    else {
      this.messageService.add({ severity: 'info', summary: 'Shape Selected', detail: '' });
    }
  }

  addMarker() {
    this.overlays.push(new google.maps.Marker({ position: { lat: this.selectedPosition.lat(), lng: this.selectedPosition.lng() }, title: this.markerTitle, draggable: this.draggable }));
    this.markerTitle = null;
    this.dialogVisible = false;
  }

  handleDragEnd(event) {
    this.messageService.add({ severity: 'info', summary: 'Marker Dragged', detail: event.overlay.getTitle() });
  }

  initOverlays() {
    if (!this.overlays || !this.overlays.length) {
      this.overlays = [
        new google.maps.Marker({ position: { lat: 36.879466, lng: 30.667648 }, title: "Konyaalti" }),
        new google.maps.Marker({ position: { lat: 36.883707, lng: 30.689216 }, title: "Ataturk Park" }),
        new google.maps.Marker({ position: { lat: 36.885233, lng: 30.702323 }, title: "Oldtown" }),
        new google.maps.Polygon({
          paths: [
            { lat: 36.9177, lng: 30.7854 }, { lat: 36.8851, lng: 30.7802 }, { lat: 36.8829, lng: 30.8111 }, { lat: 36.9177, lng: 30.8159 }
          ], strokeOpacity: 0.5, strokeWeight: 1, fillColor: '#1976D2', fillOpacity: 0.35
        }),
        new google.maps.Circle({ center: { lat: 36.90707, lng: 30.56533 }, fillColor: '#1976D2', fillOpacity: 0.35, strokeWeight: 1, radius: 1500 }),
        new google.maps.Polyline({ path: [{ lat: 36.86149, lng: 30.63743 }, { lat: 36.86341, lng: 30.72463 }], geodesic: true, strokeColor: '#FF0000', strokeOpacity: 0.5, strokeWeight: 2 })
      ];
    }
  }

  zoomIn(map) {
    map.setZoom(map.getZoom() + 1);
  }

  zoomOut(map) {
    map.setZoom(map.getZoom() - 1);
  }

  clear() {
    this.overlays = [];
  }
}