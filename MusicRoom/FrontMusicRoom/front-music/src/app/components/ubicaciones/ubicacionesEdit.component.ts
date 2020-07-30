import { Component, OnInit } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { RestService } from '../../services/rest.service';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { Enumerados } from 'src/app/config/Enumerados';
import { SesionService } from 'src/app/services/sesionService/sesion.service';
import { UbicacionService } from 'src/app/services/ubicacionService/ubicacion.service';

declare var $: any;

@Component({
  selector: 'app-ubicaciones-edit',
  templateUrl: './ubicacionesEdit.component.html',
  styleUrls: ['./ubicaciones.component.scss'],
  providers: [RestService, MessageService]
})
export class UbicacionesEditComponent implements OnInit {
  // Objetos de Sesion
  ACCESS_TOKEN: any;
  usuarioSesion: any;
  sesion: any;

  // Utilidades
  msg: any;
  const: any;
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
  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public util: Util, public objectModelInitializer: ObjectModelInitializer, public enumerados: Enumerados, public sesionService: SesionService, private messageService: MessageService, public ubicacionService: UbicacionService) {
    this.usuarioSesion = this.objectModelInitializer.getDataUsuario();
    this.sesion = this.objectModelInitializer.getDataSesion();
    this.msg = this.textProperties.getProperties(this.sesionService.idioma);
    this.const = this.objectModelInitializer.getConst();
    this.enums = this.enumerados.getEnumerados();
    this.objeto = this.objectModelInitializer.getDataUbicacion();
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
    this.ACCESS_TOKEN = this.sesionService.tokenSesion.token;
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
    this.inicializarCombos();
    this.sesionService.mensajeConfirmacion = undefined;
    this.phase = this.sesionService.phase;
    this.isDisabled = this.phase !== this.const.phaseAdd;
    this.enumSiNo = this.util.getEnum(this.enums.sino.cod);

    // Paises
    this.listaPaises = this.ubicacionService.listaPaises;
    this.enumFiltroPaises = this.util.obtenerEnumeradoDeListaUbicacion(this.listaPaises, 0);
    // Departamentos
    this.listaDepartamentos = this.ubicacionService.listaDepartamentos;
    this.enumFiltroDepartamentos = this.util.obtenerEnumeradoDeListaUbicacion(this.listaDepartamentos, 1);
    // Ciudades
    this.listaCiudades = this.ubicacionService.listaCiudades;
    this.enumFiltroCiudades = this.util.obtenerEnumeradoDeListaUbicacion(this.listaCiudades, 2);

    if (typeof this.ubicacionService.editParam !== 'undefined' && this.ubicacionService.editParam !== null) {
      this.objeto = this.ubicacionService.editParam;
      this.colocarValoresObjetoEdit();
      if (this.phase !== this.const.phaseAdd) {
        if (this.objeto.tipoUbicacion === 0) {
          this.codigoPaisP = this.paisSeleccionado.value.codigoPais;
          this.nombrePaisP = this.paisSeleccionado.value.nombrePais;
        }
        if (this.objeto.tipoUbicacion === 1) {
          this.codigoDepartamentoD = this.departamentoSeleccionado.value.codigoDepartamento;
          this.nombreDepartamentoD = this.departamentoSeleccionado.value.nombreDepartamento;
        }
        if (this.objeto.tipoUbicacion === 2) {
          this.codigoCiudadC = this.ciudadSeleccionada.value.codigoCiudad;
          this.nombreCiudadC = this.ciudadSeleccionada.value.nombreCiudad;
        }
      }
    }

    this.tabsMenu = [
      { id: '1', label: this.msg.lbl_enum_tipo_ubicacion_valor_pais, icon: 'flag' },
      { id: '2', label: this.msg.lbl_enum_tipo_ubicacion_valor_departamento, icon: 'location_on' },
      { id: '3', label: this.msg.lbl_enum_tipo_ubicacion_valor_ciudad, icon: 'location_city' }
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
    this.objeto.estado = this.objeto.estado === undefined ? null : this.objeto.estado.value;

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
        this.objeto.codigoDepartamento = this.departamentoSeleccionadoC.value.codigoDepartamento;
        this.objeto.nombreDepartamento = this.departamentoSeleccionadoC.value.nombreDepartamento;
        this.objeto.codigoCiudad = this.codigoCiudadC;
        this.objeto.nombreCiudad = this.nombreCiudadC;
        this.objeto.tipoUbicacion = 2;
      }
    }
    else {
      if (this.paisSeleccionado !== null && typeof this.paisSeleccionado.value != 'undefined') {
        let pais = this.util.obtenerUbicacionDeEnum(this.paisSeleccionado.value.idUbicacion, this.listaPaises);
        this.objeto.codigoPais = pais.codigoPais;
      }
      else {
        this.objeto.codigoPais = null;
      }
      if (this.departamentoSeleccionado !== null && typeof this.departamentoSeleccionado.value != 'undefined') {
        let departamento = this.util.obtenerUbicacionDeEnum(this.departamentoSeleccionado.value.idUbicacion, this.listaDepartamentos);
        this.objeto.codigoDepartamento = departamento.codigoDepartamento;
      }
      else {
        this.objeto.codigoDepartamento = null;
      }
      if (this.ciudadSeleccionada !== null && typeof this.ciudadSeleccionada.value != 'undefined') {
        let ciudad = this.util.obtenerUbicacionDeEnum(this.ciudadSeleccionada.value.idUbicacion, this.listaCiudades);
        this.objeto.codigoCiudad = ciudad.codigoCiudad;
      }
      else {
        this.objeto.codigoCiudad = null;
      }
    }
  }

  limpiarExcepcion() {
    console.clear();
    this.messageService.clear();
  }

  irGuardar() {
    try {
      this.limpiarExcepcion();
      let url = this.const.urlRestService + this.const.urlControllerUbicacion + (this.phase === this.const.phaseAdd ? 'crearUbicacion' : 'modificarUbicacion');
      this.ajustarCombos();
      let obj = this.objeto;

      if (this.objeto.tipoUbicacion === 0) {
        obj.codigoPais = this.codigoPaisP;
        obj.nombrePais = this.nombrePaisP;
      } else if (this.objeto.tipoUbicacion === 1) {
        obj.codigoDepartamento = this.codigoDepartamentoD;
        obj.nombreDepartamento = this.nombreDepartamentoD;
      } else if (this.objeto.tipoUbicacion === 2) {
        obj.codigoCiudad = this.codigoCiudadC;
        obj.nombreCiudad = this.nombreCiudadC;
      }

      this.restService.postSecureREST(url, obj, this.ACCESS_TOKEN)
        .subscribe(resp => {
          this.data = resp;
          let mensajeConfirmacion = this.msg.lbl_detail_el_registro + this.data.idUbicacion + this.msg.lbl_detail_fue + (this.phase === this.const.phaseAdd ? this.msg.lbl_detail_creado : this.msg.lbl_detail_actualizado) + this.msg.lbl_detail_satisfactoriamente;
          this.sesionService.mensajeConfirmacion = mensajeConfirmacion;
          this.irAtras();
        },
          error => {
            let mensaje = this.util.mostrarNotificacion(error.error);
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
    this.ubicacionService.listaConsulta;
    this.router.navigate(['/ubicacionQuery']);
  }

  guardaTeclaEnter(event) {
    if (event.which === 13) {
      this.irGuardar();
    }
  }

  cargarDepartamentosPorPais() {
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

  cargarCiudadesPorDepartamento() {
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
}
