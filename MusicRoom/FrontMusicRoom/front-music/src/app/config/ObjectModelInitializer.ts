import { Injectable } from '@angular/core';

export var HOST = 'http://localhost:9002';
//export var HOST = 'http://10.176.56.211:9002';
//export var HOST = 'http://10.108.2.66:9002';
//export var HOST = 'http://192.168.1.15:9002';
//export var HOST = 'http://185.224.139.43:9002/music-room';

export var SYSTEM = 'http://localhost:4200';
//export var SYSTEM = 'http://10.176.56.211:7001';
//export var SYSTEM = 'http://10.108.2.66:4200';
//export var SYSTEM = 'http://192.168.1.15:4200';
//export var SYSTEM = 'http://185.224.139.43:8080/music-room';


@Injectable()
export class ObjectModelInitializer {

  constructor() {
  }

  getLocaleESForCalendar() {
    return {
      firstDayOfWeek: 1,
      dayNames: ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"],
      dayNamesShort: ["Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"],
      dayNamesMin: ["D", "L", "M", "X", "J", "V", "S"],
      monthNames: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"],
      monthNamesShort: ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"],
      today: 'Hoy',
      clear: 'Borrar'
    }
  };

  getLocaleENForCalendar() {
    return {
      firstDayOfWeek: 1,
      dayNames: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
      dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
      dayNamesMin: ["S", "M", "T", "W", "T", "F", "S"],
      monthNames: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
      monthNamesShort: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
      today: 'Today',
      clear: 'Clear'
    }
  };

  getConst() {
    return {
      // URL'S + Info del Sistema
      urlDomain: `${SYSTEM}/`,
      urlRestService: `${HOST}/`,
      urlRestOauth: `${HOST}/oauth/token`,
      urlVCode: `${SYSTEM}/vCode/`,
      urlControllerUsuario: 'music-room/usuario/',
      urlControllerUbicacion: 'music-room/ubicacion/',
      urlControllerTercero: 'music-room/tercero/',
      urlControllerReporte: 'music-room/reporte-archivo/',
      urlControllerSala: 'music-room/sala/',
      tokenUsernameAUTH: 'MusicRoomApp',
      tokenPasswordAUTH: 'musicroom2019codex',
      tokenNameAUTH: 'access_token',
      codigoADMIN: 'RMRADM',

      // Model rango de fechas para NGBDatePicker
      minDate: { year: 1000, month: 1, day: 1 },
      maxDate: new Date(),
      formatoFecha: 'dd/mm/yy',
      rangoYears: '1900:3000',

      // Otras Variables
      idiomaEs: 1,
      idiomaEn: 2,
      phaseAdd: 'add',
      phaseDelete: 'delete',
      phaseSearch: 'search',
      phaseEdit: 'edit',
      phasePlus: 'plus',
      tipoCampoTexto: 1,
      tipoCampoEnum: 2,
      disabled: 'disabled',
      readOnly: 'readOnly',
      severity: ['info', 'success', 'warn', 'error'],
      actionModal: { 'show': 1, 'hidde': 2 },
      collectionSize: 0,
      maxSize: 1,
      rotate: true,
      pageSize: 1,
      menuConfiguracion: "C",
      menuAdministracion: "A",
      menuInventario: "I",
      menuAgenda: "G",
      menuMovimientos: "M",
      estadoActivoNumString: 1,
      estadoInactivoNumString: 0
    }
  };

  getTokenSesion() {
    return {
      name: '',
      token: ''
    }
  }

  getDataModeloTablas() {
    return {
      // Campo de la tabla
      field: '',
      // Encabezado
      header: ''
    }
  };

  getDataMessage() {
    return {
      // info, success, warning, danger
      severity: '',
      // Title of MSG
      summary: '',
      // Description of MSG
      detail: ''
    }
  };

  getDataException() {
    return {
      fecha: '',
      mensaje: '',
      detalles: ''
    }
  };

  getDataUsuarioDTO() {
    return {
      //UsuarioDTO
      usuario: '',
      numeroDocumento: '',
      tipoDocumento: '',
      email: ''
    }
  };

  getDataMailDTO() {
    return {
      //UsuarioDTO
      from: '',
      to: '',
      subject: '',
      model: {}
    }
  };

  getDataUsuario() {
    return {
      //Usuario
      idUsuario: '',
      usuario: '',
      password: '',
      nombre: '',
      apellido: '',
      numeroDocumento: '',
      tipoDocumento: '',
      tipoUsuario: '',
      email: '',
      fechaNacimiento: '',
      fotoTb: this.getDataArchivo(),
      listaRoles: [this.getDataRol()],
      codigoVerificacion: '',

      //Abstract
      estado: '',
      usuarioCreacion: '',
      fechaCreacion: '',
      usuarioActualiza: '',
      fechaActualiza: ''
    }
  };

  getDataVCode() {
    return {
      idCodigoVerificacion: '',
      token: '',
      email: '',
      expiracion: '',

      //Abstract
      estado: '',
      usuarioCreacion: '',
      fechaCreacion: '',
      usuarioActualiza: '',
      fechaActualiza: ''
    }
  };

  getDataRol() {
    return {
      idRol: '',
      codigo: '',
      descripcion: '',
      path: '',
      subPath: '',

      //Abstract
      estado: '',
      usuarioCreacion: '',
      fechaCreacion: '',
      usuarioActualiza: '',
      fechaActualiza: ''
    }
  }

  getDataArchivo() {
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

  getDataUbicacion() {
    return {
      idUbicacion: '',
      codigoCiudad: '',
      nombreCiudad: '',
      codigoDepartamento: '',
      nombreDepartamento: '',
      codigoPais: '',
      nombrePais: '',
      tipoUbicacion: '',

      //Abstract
      estado: '',
      usuarioCreacion: '',
      fechaCreacion: '',
      usuarioActualiza: '',
      fechaActualiza: ''
    }
  };

  getDataTercero() {
    return {
      //Usuario
      idTercero: '',
      razonSocial: '',
      nit: '',
      direccion: '',
      telefono1: '',
      telefono2: '',
      infoAdicional: '',
      ubicacionTb: this.getDataUbicacion(),
      ubicacionTabla: '',

      //Abstract
      estado: '',
      usuarioCreacion: '',
      fechaCreacion: '',
      usuarioActualiza: '',
      fechaActualiza: ''
    }
  };

  getDataSesion() {
    return {
      idSesion: '',
      tokenSesion: '',
      usuarioTb: this.getDataUsuario(),

      //Abstract
      estado: '',
      usuarioCreacion: '',
      fechaCreacion: '',
      usuarioActualiza: '',
      fechaActualiza: ''
    }
  };

  getDataSala() {
    return {
      idSala: '',
      terceroTb: this.getDataTercero(),
      nombreSala: '',
      infoAdicional: '',
      fotoPrincipalTb: this.getDataArchivo(),
      foto1Tb: this.getDataArchivo(),
      foto2Tb: this.getDataArchivo(),
      foto3Tb: this.getDataArchivo(),
      foto4Tb: this.getDataArchivo(),

      //Abstract
      estado: '',
      usuarioCreacion: '',
      fechaCreacion: '',
      usuarioActualiza: '',
      fechaActualiza: ''
    }
  };

}