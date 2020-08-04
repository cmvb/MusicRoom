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
import { UbicacionService } from 'src/app/services/ubicacionService/ubicacion.service';

declare var $: any;

@Component({
  selector: 'app-ubicaciones-query',
  templateUrl: './ubicacionesQuery.component.html',
  styleUrls: ['./ubicaciones.component.scss'],
  providers: [RestService, MessageService]
})
export class UbicacionesQueryComponent implements OnInit {
  // Objetos de Sesion
  ACCESS_TOKEN: any;
  usuarioSesion: any;
  sesion: any;

  // Utilidades
  msg: any;
  const: any;

  // Objetos de Datos
  data: any;
  dataP: any;
  dataD: any;
  dataC: any;
  listaConsulta = [];
  objetoFiltro: any;

  // Enumerados
  enums: any;
  listaPaises = [];
  listaDepartamentos = [];
  listaCiudades = [];
  enumFiltroPaises = [];
  enumFiltroDepartamentos = [];
  enumFiltroCiudades = [];
  enumFiltroTipoUbicacion = [];
  paisSeleccionado: any;
  departamentoSeleccionado: any;
  ciudadSeleccionada: any;
  tipoUbicacionSeleccionada: any;

  // Opciones del Componente Consulta
  btnEditar = true;
  btnEliminar = true;
  listaCabeceras = [
    { 'campoLista': 'nombreCiudad', 'nombreCabecera': 'Ciudad/Municipio' },
    { 'campoLista': 'nombreDepartamento', 'nombreCabecera': 'Departamento/Región' },
    { 'campoLista': 'nombrePais', 'nombreCabecera': 'País' },
  ];

  // Propiedades de las peticiones REST
  headers = new Headers({ 'Content-Type': 'application/json' });
  options = new RequestOptions({ headers: this.headers });

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService, public ubicacionService: UbicacionService) {
    this.usuarioSesion = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.objServiceSesion.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.enums = this.enumerados.getEnumerados();
    this.objetoFiltro = this.objectModelInitializer.getDataUbicacion();
    this.paisSeleccionado = null;
    this.departamentoSeleccionado = null;
    this.ciudadSeleccionada = null;
    this.tipoUbicacionSeleccionada = null;
    this.ACCESS_TOKEN = this.sesionService.objServiceSesion.tokenSesion.token.access_token;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.enumFiltroTipoUbicacion = this.util.getEnum(this.enums.tipoUbicacion.cod);
    this.tipoUbicacionSeleccionada = this.enumFiltroTipoUbicacion[0];

    this.consultarUbicaciones();
    // Paises
    this.consultarPaises();
    // Departamentos
    this.consultarDepartamentos();
    // Ciudades
    this.consultarCiudades();
  }

  ngAfterViewInit() {
    if (typeof this.sesionService.objServiceSesion.mensajeConfirmacion !== 'undefined' && this.sesionService.objServiceSesion.mensajeConfirmacion !== null) {
      let mensajeConfirmacion = this.sesionService.objServiceSesion.mensajeConfirmacion;
      this.messageService.clear();
      this.messageService.add({ severity: this.const.severity[1], summary: this.msg.lbl_summary_success, detail: mensajeConfirmacion });
    }
  }

  limpiarExcepcion() {
    console.clear();
    this.messageService.clear();
  }

  consultarUbicaciones() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUbicacion + 'consultarPorFiltros';
      this.ajustarCombos();
      let obj = this.objetoFiltro;
      obj.estado = this.const.estadoActivoNumString
      obj.tipoUbicacion = this.tipoUbicacionSeleccionada.value;

      this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          this.listaConsulta = this.data;
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

  ajustarCombos() {
    if (this.paisSeleccionado !== null) {
      let pais = this.util.obtenerUbicacionDeEnum(this.paisSeleccionado.value.idUbicacion, this.listaPaises);
      this.objetoFiltro.codigoPais = pais.codigoPais;
    } else {
      this.objetoFiltro.codigoPais = null;
    }

    if (this.departamentoSeleccionado !== null) {
      let departamento = this.util.obtenerUbicacionDeEnum(this.departamentoSeleccionado.value.idUbicacion, this.listaDepartamentos);
      this.objetoFiltro.codigoDepartamento = departamento.codigoDepartamento;
    } else {
      this.objetoFiltro.codigoDepartamento = null;
    }

    if (this.ciudadSeleccionada !== null) {
      let ciudad = this.util.obtenerUbicacionDeEnum(this.ciudadSeleccionada.value.idUbicacion, this.listaCiudades);
      this.objetoFiltro.codigoCiudad = ciudad.codigoCiudad;
    } else {
      this.objetoFiltro.codigoCiudad = null;
    }
  }

  consultarPaises() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUbicacion + 'consultarPorTipo/0';

      this.restService.getSecureREST(url, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.dataP = resp;
          this.listaPaises = this.dataP;
          this.enumFiltroPaises = this.util.obtenerEnumeradoDeListaUbicacion(this.listaPaises, 0);
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

  consultarDepartamentos() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUbicacion + 'consultarPorTipo/1';

      this.restService.getSecureREST(url, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.dataD = resp;
          this.listaDepartamentos = this.dataD;
          this.enumFiltroDepartamentos = this.util.obtenerEnumeradoDeListaUbicacion(this.listaDepartamentos, 1);
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
          console.log(resp, "res");
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
    this.sesionService.objServiceSesion.phase = this.const.phaseEdit;
    this.ubicacionService.objetoFiltro = this.objetoFiltro;
    this.ubicacionService.listaConsulta = this.listaConsulta;
    this.ubicacionService.editParam = objetoEdit;
    this.ubicacionService.listaCiudades = this.listaCiudades;
    this.ubicacionService.listaDepartamentos = this.listaDepartamentos;
    this.ubicacionService.listaPaises = this.listaPaises;
    this.router.navigate(['/ubicacionEdit']);
    return true;
  }

  irCrear() {
    this.sesionService.objServiceSesion.phase = this.const.phaseAdd;
    this.ubicacionService.objetoFiltro = this.objetoFiltro;
    this.ubicacionService.listaConsulta = this.listaConsulta;
    this.ubicacionService.editParam = null;
    this.ubicacionService.listaCiudades = this.listaCiudades;
    this.ubicacionService.listaDepartamentos = this.listaDepartamentos;
    this.ubicacionService.listaPaises = this.listaPaises;
    this.router.navigate(['/ubicacionEdit']);
    return true;
  }

  eliminar(objetoEdit) {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUbicacion + 'eliminarUbicacion';

      this.restService.postSecureREST(url, objetoEdit, this.ACCESS_TOKEN)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          this.limpiar();
          this.consultarUbicaciones();
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

  cargarDepartamentosPorPais(event) {
    if (this.paisSeleccionado !== null) {
      let nuevaListaDepartamentos = [];
      let paisUbicacion = this.util.obtenerUbicacionDeEnum(this.paisSeleccionado.value.idUbicacion, this.listaPaises);

      for (let i in this.listaDepartamentos) {
        let dep = this.listaDepartamentos[i];
        if (dep.codigoPais == paisUbicacion.codigoPais) {
          nuevaListaDepartamentos.push(dep);
        }
      }

      this.enumFiltroDepartamentos = this.util.obtenerEnumeradoDeListaUbicacion(nuevaListaDepartamentos, 1);
    }
    else {
      this.departamentoSeleccionado = null;
      this.ciudadSeleccionada = null;
    }
  }

  cargarCiudadesPorDepartamento(event) {
    if (this.departamentoSeleccionado !== null) {
      let nuevaListaCiudades = [];
      let departamentoUbicacion = this.util.obtenerUbicacionDeEnum(this.departamentoSeleccionado.value.idUbicacion, this.listaDepartamentos);

      for (let i in this.listaCiudades) {
        let ciu = this.listaCiudades[i];
        if (ciu.codigoDepartamento == departamentoUbicacion.codigoDepartamento) {
          nuevaListaCiudades.push(ciu);
        }
      }

      this.enumFiltroCiudades = this.util.obtenerEnumeradoDeListaUbicacion(nuevaListaCiudades, 2);
    }
    else {
      this.ciudadSeleccionada = null;
    }
  }

  consultaTeclaEnter(event) {
    if (event.which === 13) {
      this.consultarUbicaciones();
    }
  }
}
