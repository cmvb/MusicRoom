import { Component, OnInit } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import { MenuItem, MessageService } from 'primeng/api';
import 'rxjs/add/operator/map';
import { RestService } from '../../services/rest.service';
import { DataObjects } from '../ObjectGeneric';
import { Util } from '../Util';

declare var $: any;

@Component({
  selector: 'app-ubicaciones-edit',
  templateUrl: './ubicacionesEdit.component.html',
  styleUrls: ['./ubicaciones.component.css'],
  providers: [RestService, MessageService]
})
export class UbicacionesEditComponent implements OnInit {
  // Objetos de Sesion
  usuarioSesion: any;
  sesion: any;

  // Utilidades
  util: any;
  msg: any;
  const: any;
  ex: any;
  isDisabled: boolean;
  tabsMenu: MenuItem[];
  activeItem: MenuItem;
  mostrarP = true;
  mostrarD = false;
  mostrarC = false;

  // Objetos de Datos
  phase: any;
  data: any;
  dataP: any;
  dataD: any;
  dataC: any;
  listaConsulta = [];
  objeto: any;
  paisSeleccionado: any;
  departamentoSeleccionado: any;
  ciudadSeleccionada: any;
  codigoPaisP: any;
  nombrePaisP: any;
  paisSeleccionadoD: any;
  codigoDepartamentoD: any;
  nombreDepartamentoD: any;
  paisSeleccionadoC: any;
  departamentoSeleccionadoC: any;
  codigoCiudadC: any;
  nombreCiudadC: any;

  // Enumerados y Listas
  enums: any;
  enumSiNo = [];
  listaPaises = [];
  listaDepartamentos = [];
  listaCiudades = [];
  enumFiltroPaises = [];
  enumFiltroDepartamentos = [];
  enumFiltroCiudades = [];

  // Propiedades de las peticiones REST
  headers = new Headers({ 'Content-Type': 'application/json' });
  options = new RequestOptions({ headers: this.headers });

  // Constructor o Inicializador de Variables
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util, private messageService: MessageService) {
    this.usuarioSesion = datasObject.getDataUsuario();
    this.sesion = datasObject.getDataSesion();
    this.msg = datasObject.getProperties(datasObject.getConst().idiomaEs);
    this.const = datasObject.getConst();
    this.enums = datasObject.getEnumerados();
    this.util = util;
    this.objeto = datasObject.getDataUbicacion();
    this.objeto.estado = { value: 1, label: this.msg.lbl_enum_si };
    this.paisSeleccionado = null;
    this.departamentoSeleccionado = null;
    this.ciudadSeleccionada = null;
    this.paisSeleccionadoD = null;
    this.paisSeleccionadoC = null;
    this.departamentoSeleccionadoC = null;
    this.codigoPaisP = null;
    this.nombrePaisP = null;
    this.codigoDepartamentoD = null;
    this.nombreDepartamentoD = null;
    this.codigoCiudadC = null;
    this.nombreCiudadC = null;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.util.limpiarSesionXItem(['mensajeConfirmacion']);
    this.phase = this.util.getSesionXItem('phase');
    this.isDisabled = this.phase !== this.const.phaseAdd;
    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);

    // Paises
    this.listaPaises = this.util.getSesionXItem('listaPaises');
    this.enumFiltroPaises = this.util.obtenerEnumeradoDeListaUbicacion(this.listaPaises, 0);
    // Departamentos
    this.listaDepartamentos = this.util.getSesionXItem('listaDepartamentos');
    this.enumFiltroDepartamentos = this.util.obtenerEnumeradoDeListaUbicacion(this.listaDepartamentos, 1);
    // Ciudades
    this.listaCiudades = this.util.getSesionXItem('listaCiudades');
    this.enumFiltroCiudades = this.util.obtenerEnumeradoDeListaUbicacion(this.listaCiudades, 2);

    if (this.util.getSesionXItem('editParam') != null) {
      this.objeto = JSON.parse(localStorage.getItem('editParam'));
      this.inicializarCombos();
      this.colocarValoresObjetoEdit();
    }

    this.tabsMenu = [
      { id: '1', label: 'País', icon: 'flag' },
      { id: '2', label: 'Departamento/Región', icon: 'location_on' },
      { id: '3', label: 'Ciudad/Municipio', icon: 'location_city' }
    ];

    this.activeItem = this.tabsMenu[0];
  }

  cambiarPanelMostrar(item) {
    if (item.id == '1') {
      this.mostrarP = true;
      this.mostrarD = false;
      this.mostrarC = false;
    }
    else if (item.id == '2') {
      this.mostrarP = false;
      this.mostrarD = true;
      this.mostrarC = false;
    }
    else if (item.id == '3') {
      this.mostrarP = false;
      this.mostrarD = false;
      this.mostrarC = true;
    }
  }

  inicializarCombos() {
    this.objeto.estado = this.util.getValorEnumerado(this.enumSiNo, this.objeto.estado);
    this.paisSeleccionado = null;
    this.departamentoSeleccionado = null;
    this.ciudadSeleccionada = null;
    this.paisSeleccionadoD = null;
    this.paisSeleccionadoC = null;
    this.departamentoSeleccionadoC = null;
    this.codigoPaisP = null;
    this.nombrePaisP = null;
    this.codigoDepartamentoD = null;
    this.nombreDepartamentoD = null;
    this.codigoCiudadC = null;
    this.nombreCiudadC = null;
  }

  colocarValoresObjetoEdit() {
    this.objeto.estado = this.util.getValorEnumerado(this.enumSiNo, this.objeto.estado);
    this.paisSeleccionado = this.util.obtenerUbicacionPorCodigo(this.objeto.codigoPais, this.listaPaises, 0);
    this.departamentoSeleccionado = this.util.obtenerUbicacionPorCodigo(this.objeto.codigoDepartamento, this.listaDepartamentos, 1);
    this.ciudadSeleccionada = this.util.obtenerUbicacionPorCodigo(this.objeto.codigoCiudad, this.listaCiudades, 2);
  }

  ajustarCombos() {
    this.objeto.estado = this.objeto.estado.value;

    if (this.phase === this.const.phaseAdd) {
      if (this.mostrarP) {
        this.objeto.codigoPais = this.codigoPaisP;
        this.objeto.nombrePais = this.nombrePaisP;
        this.objeto.codigoDepartamento = null;
        this.objeto.nombreDepartamento = null;
        this.objeto.codigoCiudad = null;
        this.objeto.nombreCiudad = null;
        this.objeto.tipoUbicacion = 0;
      }
      else if (this.mostrarD) {
        this.objeto.codigoPais = this.paisSeleccionadoD.value.codigoPais;
        this.objeto.nombrePais = this.paisSeleccionadoD.value.nombrePais;
        this.objeto.codigoDepartamento = this.codigoDepartamentoD;
        this.objeto.nombreDepartamento = this.nombreDepartamentoD;
        this.objeto.codigoCiudad = null;
        this.objeto.nombreCiudad = null;
        this.objeto.tipoUbicacion = 1;
      }
      else if (this.mostrarC) {
        this.objeto.codigoPais = this.paisSeleccionadoC.value.codigoPais;
        this.objeto.nombrePais = this.paisSeleccionadoC.value.nombrePais;
        this.objeto.codigoDepartamento = this.departamentoSeleccionadoC.value.codigoPais;
        this.objeto.nombreDepartamento = this.departamentoSeleccionadoC.value.nombrePais;
        this.objeto.codigoCiudad = this.codigoCiudadC;
        this.objeto.nombreCiudad = this.nombreCiudadC;
        this.objeto.tipoUbicacion = 2;
      }
    }
    else {
      if (this.paisSeleccionado != null) {
        let pais = this.util.obtenerUbicacionDeEnum(this.paisSeleccionado.value.idUbicacion, this.listaPaises);
        this.objeto.codigoPais = pais.codigoPais;
      }
      else {
        this.objeto.codigoPais = null;
      }
      if (this.departamentoSeleccionado != null) {
        let departamento = this.util.obtenerUbicacionDeEnum(this.departamentoSeleccionado.value.idUbicacion, this.listaDepartamentos);
        this.objeto.codigoDepartamento = departamento.codigoDepartamento;
      }
      else {
        this.objeto.codigoDepartamento = null;
      }
      if (this.ciudadSeleccionada != null) {
        let ciudad = this.util.obtenerUbicacionDeEnum(this.ciudadSeleccionada.value.idUbicacion, this.listaCiudades);
        this.objeto.codigoCiudad = ciudad.codigoCiudad;
      }
      else {
        this.objeto.codigoCiudad = null;
      }
    }
  }

  limpiarExcepcion() {
    this.ex = this.util.limpiarExcepcion;
  }

  irGuardar() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUbicacion + (this.phase === this.const.phaseAdd ? 'crearUbicacion' : 'modificarUbicacion');
      this.ajustarCombos();
      let obj = this.objeto;
      debugger;
      this.restService.postREST(url, obj)
        .subscribe(resp => {
          console.log(resp, "res");
          this.data = resp;
          let mensajeConfirmacion = 'La Ubicación #' + this.data.idUbicacion + ' fue ' + (this.phase === this.const.phaseAdd ? 'creada' : 'actualizada') + ' satisfactoriamente.';
          this.util.agregarSesionXItem([{ item: 'mensajeConfirmacion', valor: mensajeConfirmacion }]);
          this.irAtras();
        },
          error => {
            this.ex = error.error;
            let mensaje = this.util.mostrarNotificacion(this.ex);
            this.messageService.clear();
            this.messageService.add(mensaje);

            this.colocarValoresObjetoEdit();

            console.log(error, "error");
          })
    } catch (e) {
      console.log(e);
    }
  }

  irAtras() {
    this.util.limpiarSesionXItem(['listaConsulta']);
    this.router.navigate(['/ubicacionQuery']);
  }

  guardaTeclaEnter(event) {
    if (event.which === 13) {
      this.irGuardar();
    }
  }

  cargarDepartamentosPorPais() {
    if (this.paisSeleccionado != null) {
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

  cargarCiudadesPorDepartamento() {
    if (this.departamentoSeleccionado != null) {
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
}
