import { Component, OnInit } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import { MessageService } from 'primeng/api';
import 'rxjs/add/operator/map';
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

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util, private messageService: MessageService, private sanitizer: DomSanitizer) {
    this.usuarioSesion = datasObject.getDataUsuario();
    this.sesion = datasObject.getDataSesion();
    this.msg = datasObject.getProperties(datasObject.getConst().idiomaEs);
    this.const = datasObject.getConst();
    this.util = util;
    this.objeto = datasObject.getDataSala();
    this.objeto.estado = { value: 1, label: this.msg.lbl_enum_si };
    this.objeto.terceroTb = datasObject.getDataTercero();
    this.enums = datasObject.getEnumerados();
    this.ACCESS_TOKEN = JSON.parse(sessionStorage.getItem(this.const.tokenNameAUTH)).access_token;
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
      this.terceroSeleccionado = this.objeto.terceroTb;
    }

    this.objeto.fotoPrincipalTb.valor = null;
    this.objeto.foto1Tb.valor = null;
    this.objeto.foto2Tb.valor = null;
    this.objeto.foto3Tb.valor = null;
    this.objeto.foto4Tb.valor = null;
  }

  dataGenericaArchivo() {
    return {
      idArchivo: '',
      nombreArchivo: '',
      tipoArchivo: '',
      valor: '',
    };
  }

  clickFileUpPrincipal() {
    $('#fileUpPrincipal').click();
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
            if (this.validarArchivo(file, event.target.accept)) {
              // Si el tipo de archivo es aceptado
              let flag = false;
              switch (i.toString()) {
                case '0':
                  this.objeto.fotoPrincipalTb = this.dataGenericaArchivo();
                  this.objeto.fotoPrincipalTb.nombreArchivo = file.name;
                  this.objeto.fotoPrincipalTb.tipoArchivo = file.name.split('.')[1];
                  flag = true;

                  break;
                case '1':
                  this.objeto.foto1Tb = this.dataGenericaArchivo();
                  this.objeto.foto1Tb.nombreArchivo = file.name;
                  this.objeto.foto1Tb.tipoArchivo = file.name.split('.')[1];
                  flag = true;

                  break;
                case '2':
                  this.objeto.foto2Tb = this.dataGenericaArchivo();
                  this.objeto.foto2Tb.nombreArchivo = file.name;
                  this.objeto.foto2Tb.tipoArchivo = file.name.split('.')[1];
                  flag = true;

                  break;
                case '3':
                  this.objeto.foto3Tb = this.dataGenericaArchivo();
                  this.objeto.foto3Tb.nombreArchivo = file.name;
                  this.objeto.foto3Tb.tipoArchivo = file.name.split('.')[1];
                  flag = true;

                  break;
                case '4':
                  this.objeto.foto4Tb = this.dataGenericaArchivo();
                  this.objeto.foto4Tb.nombreArchivo = file.name;
                  this.objeto.foto4Tb.tipoArchivo = file.name.split('.')[1];
                  flag = true;

                  break;
              }

              if (flag) {
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

  validarArchivo(archivo: any, acceptStr) {
    let valido = true;
    try {
      this.messageService.clear();
      let flagRegex = acceptStr.includes('*');
      let flagRegexOpc1 = flagRegex && archivo.type.toUpperCase().includes(acceptStr.toUpperCase().replace('*', ''));
      let flagRegexOpc2 = !flagRegex && archivo.type.toUpperCase() === acceptStr.toUpperCase();

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
        let flag = false;
        switch (i.toString()) {
          case '0':
            this.objeto.fotoPrincipalTb.valor = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
            break;
          case '1':
            this.objeto.foto1Tb.valor = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
            break;
          case '2':
            this.objeto.foto2Tb.valor = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
            break;
          case '3':
            this.objeto.foto3Tb.valor = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
            break;
          case '4':
            this.objeto.foto4Tb.valor = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
            break;
        }

        this.messageService.clear();
        this.messageService.add({ severity: this.const.severity[1], summary: this.msg.lbl_summary_success, detail: this.msg.lbl_mensaje_archivo_subido });
        localStorage.setItem('fileUploadTo', null);
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
          })
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
    this.objeto.fotoPrincipalTb.tipoArchivo = this.objeto.fotoPrincipalTb.tipoArchivo.value;
    this.objeto.foto1Tb.tipoArchivo = this.objeto.foto1Tb.tipoArchivo.value;
    this.objeto.foto2Tb.tipoArchivo = this.objeto.foto2Tb.tipoArchivo.value;
    this.objeto.foto3Tb.tipoArchivo = this.objeto.foto3Tb.tipoArchivo.value;
    this.objeto.foto4Tb.tipoArchivo = this.objeto.foto4Tb.tipoArchivo.value;
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
}