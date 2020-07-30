import { Component, OnInit } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { RestService } from '../../services/rest.service';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { Enumerados } from 'src/app/config/Enumerados';
import { SesionService } from 'src/app/services/sesionService/sesion.service';
import { BandaIntegranteService } from 'src/app/services/bandaIntegranteService/bandaIntegrante.service';

declare var $: any;

@Component({
  selector: 'app-bandas-integrantes-query',
  templateUrl: './bandasIntegrantesQuery.component.html',
  styleUrls: ['./bandasIntegrantes.component.scss'],
  providers: [RestService, MessageService]
})
export class BandasIntegrantesQueryComponent implements OnInit {
  // Objetos de Sesion
  ACCESS_TOKEN: any;
  usuarioSesion: any;
  sesion: any;

  // Utilidades
  msg: any;
  const: any;

  // Objetos de Datos
  data: any;
  dataC: any;
  listaConsulta = [];
  objetoFiltro: any;

  // Enumerados
  enums: any;
  listaTerceros = [];
  enumFiltroTerceros = [];
  terceroSeleccionado: any;

  // Opciones del Componente Consulta
  btnEditar = true;
  btnEliminar = true;
  listaCabeceras = [
    { 'campoLista': 'nombreSala', 'nombreCabecera': 'Sala' },
    { 'campoLista': 'infoAdicional', 'nombreCabecera': 'Información' },
    { 'campoLista': 'fotoPrincipalTb', 'nombreCabecera': 'Foto Principal' },
  ];

  // Propiedades de las peticiones REST
  headers = new Headers({ 'Content-Type': 'application/json' });
  options = new RequestOptions({ headers: this.headers });

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService, public bandaIntegranteService: BandaIntegranteService) {
    this.usuarioSesion = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.objetoFiltro = this.objectModelInitializer.getDataSala();
    this.ACCESS_TOKEN = this.sesionService.tokenSesion.token.access_token;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.consultarSalas();
    // Terceros
    this.consultarTerceros();
  }

  ngAfterViewInit() {
    if (typeof this.sesionService.mensajeConfirmacion !== 'undefined' && this.sesionService.mensajeConfirmacion !== null) {
      let mensajeConfirmacion = this.sesionService.mensajeConfirmacion;
      this.messageService.clear();
      this.messageService.add({ severity: this.const.severity[1], summary: this.msg.lbl_summary_success, detail: mensajeConfirmacion });
    }
  }

  limpiarExcepcion() {
    console.clear();
    this.messageService.clear();
  }

  consultarSalas() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerSala + 'consultarPorFiltros';
      this.ajustarCombos();
      let obj = this.objetoFiltro;
      obj.estado = this.const.estadoActivoNumString

      this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
        .subscribe(resp => {
          this.data = resp;
          this.listaConsulta = this.data;
        },
          error => {
            let mensaje = this.util.mostrarNotificacion(error.error);
            this.messageService.clear();
            this.messageService.add(mensaje);
          })
    } catch (e) {
      console.log(e);
    }
  }

  consultarTerceros() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerTercero;

      this.restService.getSecureREST(url, this.ACCESS_TOKEN)
        .subscribe(resp => {
          this.dataC = resp;
          this.listaTerceros = this.dataC;
          this.enumFiltroTerceros = this.util.obtenerEnumeradoDeListaTercero(this.listaTerceros);
        },
          error => {
            let mensaje = this.util.mostrarNotificacion(error.error);
            this.messageService.clear();
            this.messageService.add(mensaje);
          })
    } catch (e) {
      console.log(e);
    }
  }

  limpiar() {
    this.objetoFiltro = {};
    this.listaConsulta = [];

    return true;
  }

  editar(objetoEdit) {
    this.sesionService.phase = this.const.phaseEdit;
    this.bandaIntegranteService.objetoFiltro = this.objetoFiltro;
    this.bandaIntegranteService.listaConsulta = this.listaConsulta;
    this.bandaIntegranteService.editParam = null;
    this.bandaIntegranteService.listaTerceros = this.listaTerceros;
    this.router.navigate(['/salaEdit']);
    return true;
  }

  irCrear() {
    this.sesionService.phase = this.const.phaseAdd;
    this.bandaIntegranteService.objetoFiltro = this.objetoFiltro;
    this.bandaIntegranteService.listaConsulta = this.listaConsulta;
    this.bandaIntegranteService.editParam = null;
    this.bandaIntegranteService.listaTerceros = this.listaTerceros;
    this.router.navigate(['/salaEdit']);
    return true;
  }

  eliminar(objetoEdit) {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerSala + 'eliminarSala';

      this.restService.postSecureREST(url, objetoEdit, this.ACCESS_TOKEN)
        .subscribe(resp => {
          this.data = resp;
          this.limpiar();
          this.consultarSalas();
          this.messageService.clear();
          this.messageService.add({ severity: this.const.severity[1], summary: this.msg.lbl_summary_success, detail: this.msg.lbl_detail_el_registro_eliminado });
        },
          error => {
            let mensaje = this.util.mostrarNotificacion(error.error);
            this.messageService.clear();
            this.messageService.add(mensaje);
          })
    } catch (e) {
      console.log(e);
    }
  }

  consultaTeclaEnter(event) {
    if (event.which === 13) {
      this.consultarSalas();
    }
  }

  ajustarCombos() {
    if (this.terceroSeleccionado !== null) {
      let tercero = this.util.obtenerTerceroDeEnum(this.terceroSeleccionado.value.idTercero, this.listaTerceros);
      Object.assign(this.objetoFiltro.terceroTb, tercero);
    }
    else {
      this.objetoFiltro.terceroTb = null;
    }
  }
}
