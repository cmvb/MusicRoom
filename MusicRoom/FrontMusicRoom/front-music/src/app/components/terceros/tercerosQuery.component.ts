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
  selector: 'app-terceros-query',
  templateUrl: './tercerosQuery.component.html',
  styleUrls: ['./terceros.component.scss'],
  providers: [RestService, MessageService]
})
export class TercerosQueryComponent implements OnInit {
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
  listaCiudades = [];
  enumFiltroCiudades = [];
  ciudadSeleccionada: any;

  // Opciones del Componente Consulta
  btnEditar = true;
  btnEliminar = true;
  listaCabeceras = [
    { 'campoLista': 'nit', 'nombreCabecera': 'NIT' },
    { 'campoLista': 'razonSocial', 'nombreCabecera': 'Razón Social' },
    { 'campoLista': 'ubicacionTabla', 'nombreCabecera': 'Ubicación' },
    { 'campoLista': 'direccion', 'nombreCabecera': 'Dirección' },
  ];

  // Propiedades de las peticiones REST
  headers = new Headers({ 'Content-Type': 'application/json' });
  options = new RequestOptions({ headers: this.headers });

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService, public terceroService: TerceroService) {
    this.usuarioSesion = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.objetoFiltro = this.objectModelInitializer.getDataTercero();
    this.ACCESS_TOKEN = this.sesionService.tokenSesion.token;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.consultarTerceros();
    // Ciudades
    this.consultarCiudades();
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

  consultarTerceros() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerTercero + 'consultarPorFiltros';
      this.ajustarCombos();
      let obj = this.objetoFiltro;
      obj.estado = this.const.estadoActivoNumString

      this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
        .subscribe(resp => {
          this.data = resp;
          this.listaConsulta = this.data;

          for (let i in this.listaConsulta) {
            this.listaConsulta[i].ubicacionTabla = this.listaConsulta[i].ubicacionTb.nombreCiudad + " (" + this.listaConsulta[i].ubicacionTb.nombreDepartamento + ")";
          }
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

  consultarCiudades() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUbicacion + 'consultarPorTipo/2';

      this.restService.getSecureREST(url, this.ACCESS_TOKEN)
        .subscribe(resp => {
          this.dataC = resp;
          this.listaCiudades = this.dataC;
          this.enumFiltroCiudades = this.util.obtenerEnumeradoDeListaUbicacion(this.listaCiudades, 2);
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

  limpiar() {
    this.objetoFiltro = {};
    this.listaConsulta = [];
    return true;
  }

  editar(objetoEdit) {
    this.sesionService.phase = this.const.phaseEdit;
    this.terceroService.objetoFiltro = this.objetoFiltro;
    this.terceroService.listaConsulta = this.listaConsulta;
    this.terceroService.editParam = objetoEdit;
    this.terceroService.listaCiudades = this.listaCiudades;
    this.router.navigate(['/terceroEdit']);
    return true;
  }

  irCrear() {
    this.sesionService.phase = this.const.phaseAdd;
    this.terceroService.objetoFiltro = this.objetoFiltro;
    this.terceroService.listaConsulta = this.listaConsulta;
    this.terceroService.editParam = null;
    this.terceroService.listaCiudades = this.listaCiudades;
    this.router.navigate(['/terceroEdit']);
    return true;
  }

  eliminar(objetoEdit) {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerTercero + 'eliminarTercero';

      this.restService.postSecureREST(url, objetoEdit, this.ACCESS_TOKEN)
        .subscribe(resp => {
          this.data = resp;
          this.limpiar();
          this.consultarTerceros();
          this.messageService.clear();
          this.messageService.add({ severity: this.const.severity[1], summary: this.msg.lbl_summary_success, detail: this.msg.lbl_detail_el_registro_eliminado });
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

  consultaTeclaEnter(event) {
    if (event.which === 13) {
      this.consultarTerceros();
    }
  }

  ajustarCombos() {
    if (this.ciudadSeleccionada !== null) {
      let ciudad = this.util.obtenerUbicacionDeEnum(this.ciudadSeleccionada.value.idUbicacion, this.listaCiudades);
      Object.assign(this.objetoFiltro.ubicacionTb, ciudad);
    }
    else {
      this.objetoFiltro.ubicacionTb = null;
    }
  }
}
