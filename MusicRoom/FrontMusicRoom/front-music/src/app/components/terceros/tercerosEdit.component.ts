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
import { TerceroService } from 'src/app/services/terceroService/tercero.service';

declare var $: any;

@Component({
  selector: 'app-terceros-edit',
  templateUrl: './tercerosEdit.component.html',
  styleUrls: ['./terceros.component.scss'],
  providers: [RestService, MessageService]
})
export class TercerosEditComponent implements OnInit {
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
  listaCiudades = [];
  enumFiltroCiudades = [];
  ciudadSeleccionada: any;

  // Propiedades de las peticiones REST
  headers = new Headers({ 'Content-Type': 'application/json' });
  options = new RequestOptions({ headers: this.headers });

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService, public terceroService: TerceroService) {
    this.usuarioSesion = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.objeto = this.objectModelInitializer.getDataTercero();
    this.objeto.estado = { value: 1, label: this.msg.lbl_enum_si };
    this.objeto.ubicacionTb = this.objectModelInitializer.getDataUbicacion();
    this.enums = this.enumerados.getEnumerados();
    this.ACCESS_TOKEN = this.sesionService.tokenSesion.token;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.sesionService.mensajeConfirmacion = undefined;
    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);
    this.listaCiudades = this.terceroService.listaCiudades;
    this.enumFiltroCiudades = this.util.obtenerEnumeradoDeListaUbicacion(this.listaCiudades, 2);

    this.phase = this.sesionService.phase;
    this.isDisabled = this.phase !== this.const.phaseAdd;

    if (typeof this.terceroService.editParam !== 'undefined' && this.terceroService.editParam !== null) {
      this.objeto = this.terceroService.editParam;
      this.inicializarCombos();
      this.ciudadSeleccionada = this.util.obtenerUbicacionPorCodigo(this.objeto.ubicacionTb.codigoCiudad, this.listaCiudades, 2);
    }
  }

  limpiarExcepcion() {
    console.clear();
    this.messageService.clear();
  }

  irGuardar() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerTercero + (this.phase === this.const.phaseAdd ? 'crearTercero' : 'modificarTercero');
      this.ajustarCombos();
      let obj = this.objeto;

      this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
        .subscribe(resp => {
          this.data = resp;
          let mensajeConfirmacion = this.msg.lbl_detail_el_registro + this.data.idTercero + this.msg.lbl_detail_fue + (this.phase === this.const.phaseAdd ? this.msg.lbl_detail_creado : this.msg.lbl_detail_actualizado) + this.msg.lbl_detail_satisfactoriamente;
          this.sesionService.mensajeConfirmacion = mensajeConfirmacion;
          this.irAtras();
        },
          error => {
            let mensaje = this.util.mostrarNotificacion(error.error);
            this.messageService.clear();
            this.messageService.add(mensaje);

            this.inicializarCombos();
          })
    } catch (e) {
      console.log(e);
    }
  }

  ajustarCombos() {
    this.objeto.estado = this.objeto.estado === undefined ? null : this.objeto.estado.value;

    if (this.ciudadSeleccionada !== null) {
      let ciudad = this.util.obtenerUbicacionDeEnum(this.ciudadSeleccionada.value.idUbicacion, this.listaCiudades);
      Object.assign(this.objeto.ubicacionTb, ciudad);
    }
    else {
      this.objeto.ubicacionTb = null;
    }
  }

  inicializarCombos() {
    this.objeto.estado = this.util.getValorEnumerado(this.enumSiNo, this.objeto.estado);
    this.ciudadSeleccionada = null;
  }

  irAtras() {
    this.terceroService.listaConsulta;
    this.router.navigate(['/terceroQuery']);
  }

  guardaTeclaEnter(event) {
    if (event.which === 13) {
      this.irGuardar();
    }
  }
}
