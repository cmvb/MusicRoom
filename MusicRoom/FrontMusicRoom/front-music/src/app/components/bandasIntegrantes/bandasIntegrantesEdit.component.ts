import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import { MessageService } from 'primeng/api';
import { RestService } from '../../services/rest.service';
import { DomSanitizer } from '@angular/platform-browser';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { Enumerados } from 'src/app/config/Enumerados';
import { SesionService } from 'src/app/services/sesionService/sesion.service';
import { BandaIntegranteService } from 'src/app/services/bandaIntegranteService/bandaIntegrante.service';

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
  enumInstrumentosAccesorios = [];

  // Archivos
  imagenData: any;
  labelEscogido: any;
  acceptStr: any;
  srcFoto: any;
  srcLogo: any;
  currentFileUploadFoto: File;
  currentFileUploadLogo: File;

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService, public bandaIntegranteService: BandaIntegranteService, private sanitizer: DomSanitizer) {
    this.usuarioSesion = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.objServiceSesion.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.objeto = this.objectModelInitializer.getDataBandaIntegrante();
    this.objeto.estado = this.const.estadoActivoNumString
    this.enums = this.enumerados.getEnumerados();
    this.ACCESS_TOKEN = this.sesionService.objServiceSesion.tokenSesion.token.access_token;
    this.acceptStr = "image/*";
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {

  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.sesionService.objServiceSesion.mensajeConfirmacion = undefined;
    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);

    this.phase = this.sesionService.objServiceSesion.phase;
    this.isDisabled = this.phase !== this.const.phaseAdd;

    if (typeof this.bandaIntegranteService.editParam !== 'undefined' && this.bandaIntegranteService.editParam !== null) {
      this.objeto = this.bandaIntegranteService.editParam;
    }

    this.inicializarFotos();
    this.inicializarCombos();
    this.activarFoto(0);
  }

  inicializarFotos() {
    if (this.phase === this.const.phaseAdd) {
      this.objeto.fotoTb = this.dataGenericaArchivo();
      this.objeto.logoTb = this.dataGenericaArchivo();
      this.srcFoto = null;
      this.srcLogo = null;
    } else {
      this.sanitizarUrlImgCargada(this.objeto.fotoTb.valor, 0, this.objeto.fotoTb.tipoArchivo);
      this.sanitizarUrlImgCargada(this.objeto.logoTb.valor, 1, this.objeto.logoTb.tipoArchivo);
    }
  }

  sanitizarUrlImgCargada(dato: any, i, tipoArchivo) {
    if (tipoArchivo === 'svg') {
      switch (i.toString()) {
        case '0':
          this.srcFoto = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/svg+xml;base64,' + dato);
        case '1':
          this.srcLogo = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/svg+xml;base64,' + dato);
          break;
      }
    } else {
      tipoArchivo = tipoArchivo + ';base64,';
      switch (i.toString()) {
        case '0':
          this.srcFoto = 'data:image/' + tipoArchivo + dato;
          break;
        case '1':
          this.srcLogo = 'data:image/' + tipoArchivo + dato;
          break;
      }
    }
  }

  activarFoto(i) {
    $('.seccionFoto').hide();
    $('.card-af').removeClass('bg-c-orange');
    switch (i.toString()) {
      case '0':
        $('#idActivarFoto').addClass('bg-c-orange');
        $('#sectionFoto').show();
        break;
      case '1':
        $('#idActivarLogo').addClass('bg-c-orange');
        $('#sectionLogo').show();
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
                  this.objeto.fotoTb.nombreArchivo = file.name;
                  this.currentFileUploadFoto = file;
                  flag = true;

                  break;
                case '1':
                  this.objeto.logoTb.nombreArchivo = file.name;
                  this.currentFileUploadLogo = file;
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
      if (this.objeto.fotoTb.valor !== null && this.objeto.logoTb.valor !== null) {
        this.limpiarExcepcion();
        let url = this.const.urlRestService + this.const.urlControllerBandaIntegrante + (this.phase === this.const.phaseAdd ? 'crearSala' : 'modificarSala');
        this.ajustarCombos();
        let obj = this.objeto;

        this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
          .subscribe(resp => {
            this.data = resp;
            let mensajeConfirmacion = this.msg.lbl_detail_el_registro + this.data.bandaTb.idBanda + this.msg.lbl_detail_fue + (this.phase === this.const.phaseAdd ? this.msg.lbl_detail_creado : this.msg.lbl_detail_actualizado) + this.msg.lbl_detail_satisfactoriamente;
            this.sesionService.objServiceSesion.mensajeConfirmacion = mensajeConfirmacion;
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
  }

  inicializarCombos() {
    this.objeto.estado = this.util.getValorEnumerado(this.enumSiNo, this.objeto.estado);
  }

  irAtras() {
    this.bandaIntegranteService.listaConsulta = undefined;
    this.router.navigate(['/bandaIntegranteQuery']);
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
          this.srcFoto = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          obj = this.currentFileUploadFoto;

          break;
        case '1':
          this.srcLogo = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          obj = this.currentFileUploadLogo;

          break;
      }

      this.restService.postSecureFileREST(url, obj, this.ACCESS_TOKEN)
        .subscribe(resp => {
          this.data = resp;

          switch (i.toString()) {
            case '0':
              this.objeto.fotoTb = this.data;
              break;
            case '1':
              this.objeto.logoTb = this.data;
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
                this.objeto.fotoTb = this.dataGenericaArchivo();
                this.srcFoto = null;
                break;
              case '1':
                this.objeto.logoTb = this.dataGenericaArchivo();
                this.srcLogo = null;
                break;
            }
          });
    } catch (e) {
      console.log(e);
    }
  }

}