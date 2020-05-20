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
  enumFotos = [];

  // Propiedades de las peticiones REST
  headers = new Headers({ 'Content-Type': 'application/json' });
  options = new RequestOptions({ headers: this.headers });

  // Archivos
  uploadedFiles: any[] = [];
  imagenData: any;
  fotos: any[] = [];
  displayModal: boolean = false;

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

  showDialog() {
    this.displayModal = true;
  }

  onUpload(event) {
    this.uploadedFiles = [];
    this.fotos = [];
    let i = 0;
    for (let file of event.files) {
      this.uploadedFiles.push(file);
      if (this.uploadedFiles.length == 1) {
        this.objeto.fotoPrincipalTb.nombreArchivo = file.name;
        this.objeto.fotoPrincipalTb.tipoArchivo = file.name.split('.');
      } else if (this.uploadedFiles.length == 2) {
        this.objeto.foto1Tb.nombreArchivo = file.name;
        this.objeto.foto1Tb.tipoArchivo = file.name.split('.');
      } else if (this.uploadedFiles.length == 3) {
        this.objeto.foto2Tb.nombreArchivo = file.name;
        this.objeto.foto2Tb.tipoArchivo = file.name.split('.');
      } else if (this.uploadedFiles.length == 4) {
        this.objeto.foto3Tb.nombreArchivo = file.name;
        this.objeto.foto3Tb.tipoArchivo = file.name.split('.');
      } else if (this.uploadedFiles.length == 5) {
        this.objeto.foto4Tb.nombreArchivo = file.name;
        this.objeto.foto4Tb.tipoArchivo = file.name.split('.');
      }

      this.sanitizarUrl(file, i);
      i++;
    }
  }

  sanitizarUrl(data: any, i) {
    debugger;
    let reader = new FileReader();
    reader.readAsDataURL(data);
    reader.onloadend = () => {
      let dato = reader.result;

      if (this.uploadedFiles.length > 0 && this.uploadedFiles.length <= 5) {
        if (i == 0) {
          this.objeto.fotoPrincipalTb.valor = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          this.fotos.push(this.objeto.fotoPrincipalTb);
        } else if (i == 1) {
          this.objeto.foto1Tb.valor = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          this.fotos.push(this.objeto.foto1Tb);
        } else if (i == 2) {
          this.objeto.foto2Tb.valor = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          this.fotos.push(this.objeto.foto2Tb);
        } else if (i == 3) {
          this.objeto.foto3Tb.valor = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          this.fotos.push(this.objeto.foto3Tb);
        } else if (i == 4) {
          this.objeto.foto4Tb.valor = this.sanitizer.bypassSecurityTrustResourceUrl(dato.toString());
          this.fotos.push(this.objeto.foto4Tb);
        }

        this.messageService.clear();
        this.messageService.add({ severity: this.const.severity[1], summary: this.msg.lbl_summary_success, detail: this.msg.lbl_mensaje_archivo_subido });
      } else {
        this.uploadedFiles = [];
        this.messageService.clear();
        this.messageService.add({ severity: this.const.severity[2], summary: this.msg.lbl_summary_warning, detail: this.msg.lbl_mensaje_cant_archivos_permitidos });
      }
    }
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.util.limpiarSesionXItem(['mensajeConfirmacion']);
    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);
    this.enumFotos = [
      { value: 0, label: this.msg.lbl_mtto_sala_foto_principal },
      { value: 1, label: this.msg.lbl_mtto_sala_foto + " " + this.msg.lbl_mtto_sala_1 },
      { value: 2, label: this.msg.lbl_mtto_sala_foto + " " + this.msg.lbl_mtto_sala_2 },
      { value: 3, label: this.msg.lbl_mtto_sala_foto + " " + this.msg.lbl_mtto_sala_3 },
      { value: 4, label: this.msg.lbl_mtto_sala_foto + " " + this.msg.lbl_mtto_sala_4 }
    ];
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

  limpiarExcepcion() {
    this.ex = this.util.limpiarExcepcion;
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
    if (this.terceroSeleccionado != null) {
      let tercero = this.util.obtenerTerceroDeEnum(this.terceroSeleccionado.value.idTercero, this.listaTerceros);
      Object.assign(this.objeto.terceroTb, tercero);
    }
    else {
      this.objeto.terceroTb = null;
    }
  }

  inicializarCombos() {
    this.objeto.estado = this.util.getValorEnumerado(this.enumSiNo, this.objeto.estado);
    this.terceroSeleccionado = null;
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