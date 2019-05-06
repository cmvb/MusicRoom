import { Injectable } from '@angular/core';
export var url = 'http://localhost:8080/';

@Injectable()
export class DataObjects {

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

  getConst() {
    return {
      urlRestService: 'http://localhost:9002/music-room/',
      urlControllerUsuario: 'usuario/',
      urlControllerUbicacion: 'ubicacion/',
      urlControllerTercero: 'tercero/',
      //Model rango de fechas para NGBDatePicker
      minDate: { year: 1000, month: 1, day: 1 },
      maxDate: new Date(),
      formatoFecha: 'dd/mm/yy',
      rangoYears: '1900:3000',

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
    }
  };

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
      fechaNacimiento: '',
      email: '',

      //Abstract
      estado: '',
      usuarioCreacion: '',
      fechaCreacion: '',
      usuarioActualiza: '',
      fechaActualiza: ''
    }
  };

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
      direccion: '',
      telefono1: '',
      telefono2: '',
      infoAdicional: '',
      ubicacionTb: this.getDataUbicacion(),

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

  getEnumerados() {
    let properties = this.getProperties(this.getConst().idiomaEs);

    return {
      sino: {
        cod: 1, valores: [
          { value: 1, label: properties.lbl_enum_si },
          { value: 0, label: properties.lbl_enum_no }
        ]
      },
      modulo: {
        cod: 2, valores: [
          { value: 1, label: properties.lbl_enum_modulo_tb_perfil },
          { value: 2, label: properties.lbl_enum_modulo_tb_usuario },
          { value: 3, label: properties.lbl_enum_modulo_tb_perfil_x_usuario }
        ]
      },
      sexo: {
        cod: 3, valores: [
          { value: 1, label: properties.lbl_enum_sexo_valor_masculino },
          { value: 2, label: properties.lbl_enum_sexo_valor_femenino },
          { value: 3, label: properties.lbl_enum_sexo_valor_ambos }
        ]
      },
      tipoUsuario: {
        cod: 4, valores: [
          { value: 0, label: properties.lbl_enum_generico_valor_vacio },
          { value: 1, label: properties.lbl_enum_tipo_usuario_valor_cliente },
          { value: 2, label: properties.lbl_enum_tipo_usuario_valor_empleado },
          { value: 3, label: properties.lbl_enum_tipo_usuario_valor_administrador }
        ]
      },
      tipoDocumento: {
        cod: 5, valores: [
          { value: 0, label: properties.lbl_enum_generico_valor_vacio },
          { value: 1, label: properties.lbl_enum_tipo_documento_valor_cc },
          { value: 2, label: properties.lbl_enum_tipo_documento_valor_ti },
          { value: 3, label: properties.lbl_enum_tipo_documento_valor_ce }
        ]
      },
      //valorIva: {cod: 25},
    }
  };

  getProperties(idioma) {
    let constant = this.getConst();
    return {
      // Generales
      lbl_info_sin_resultados: idioma == constant.idiomaEs ? 'Sin Resultados' : 'Without Results',
      lbl_info_fallo_conectar_base_datos: idioma == constant.idiomaEs ? 'No hay Conexión a la Base de Datos' : 'Without Conection to Data Base',
      lbl_info_cargando_resultados: idioma == constant.idiomaEs ? 'Cargando Resultados' : 'Loading Results',
      lbl_info_proceso_completo: idioma == constant.idiomaEs ? 'Proceso realizado Satisfactoriamente' : 'Process Complete',

      // Modales
      lbl_info_titulo_modal_error: idioma == constant.idiomaEs ? 'ERROR' : 'ERROR',
      lbl_info_titulo_modal_informacion: idioma == constant.idiomaEs ? 'INFROMACION' : 'INFORMATION',
      lbl_info_titulo_modal_advertencia: idioma == constant.idiomaEs ? 'ADVERTECNIA' : 'WARNING',
      lbl_info_titulo_modal_proceso_exitoso: idioma == constant.idiomaEs ? 'PROCESO EXITOSO' : 'PROCESS COMPLETE',

      // Menu
      lbl_menu_parametrizacion: idioma == constant.idiomaEs ? 'Parametrización' : 'Parameterization',
      lbl_menu_sesion: idioma == constant.idiomaEs ? 'Sesión' : 'Session',
      lbl_menu_usuario: idioma == constant.idiomaEs ? 'Usuarios' : 'Users',
      lbl_menu_tercero: idioma == constant.idiomaEs ? 'Empresas/Terceros' : 'Companies',
      lbl_menu_sala: idioma == constant.idiomaEs ? 'Salas de Ensayo' : 'Rehearsal Rooms',
      lbl_menu_ubicacion: idioma == constant.idiomaEs ? 'Ubicaciones' : 'Locations',
      lbl_menu_banda_integrante: idioma == constant.idiomaEs ? 'Bandas/Integrantes' : 'Bands/Members',
      lbl_menu_ensayo: idioma == constant.idiomaEs ? 'Agendar Ensayo' : 'Schedule Rehearsal',
      lbl_menu_factura_pago: idioma == constant.idiomaEs ? 'Facturas/Pagos' : 'Bills/Payments',
      lbl_menu_inventario: idioma == constant.idiomaEs ? 'Productos/Accesorios' : 'Products/Accesories',
      lbl_menu_prestamo: idioma == constant.idiomaEs ? 'Alquiler' : 'Rental',

      // Acciones
      lbl_btn_consultar: idioma == constant.idiomaEs ? 'Consultar' : 'Query',
      lbl_btn_crear: idioma == constant.idiomaEs ? 'Crear' : 'Create',
      lbl_btn_editar: idioma == constant.idiomaEs ? 'Editar' : 'Edit',
      lbl_btn_limpiar: idioma == constant.idiomaEs ? 'Limpiar' : 'Clean',
      lbl_btn_atras: idioma == constant.idiomaEs ? 'Atrás' : 'Back',
      lbl_btn_masivo: idioma == constant.idiomaEs ? 'Masivo' : 'Masive',
      lbl_btn_exportar: idioma == constant.idiomaEs ? 'Exportar' : 'Export',
      lbl_btn_importar: idioma == constant.idiomaEs ? 'Importar' : 'Import',
      lbl_btn_actualizar: idioma == constant.idiomaEs ? 'Actualizar' : 'Update',
      lbl_btn_guardar: idioma == constant.idiomaEs ? 'Guardar' : 'Save',
      lbl_btn_ite_remover: idioma == constant.idiomaEs ? 'Remover' : 'Remove',
      lbl_btn_ite_agregar: idioma == constant.idiomaEs ? 'Agregar' : 'Add',

      // Header
      lbl_header_usuario: idioma == constant.idiomaEs ? 'Usuario' : 'User',
      lbl_header_nombre: idioma == constant.idiomaEs ? 'Nombre' : 'Name',

      // Titles
      lbl_mtto_consulta: idioma == constant.idiomaEs ? 'Consulta' : 'Query',
      lbl_mtto_creacion_edicion: idioma == constant.idiomaEs ? 'Creación/Edición' : 'Create/Edit',

      // Tooltips
      lbl_tip_cerrar_sesion: idioma == constant.idiomaEs ? 'Cerrar Sesión' : 'End Session',
      lbl_tip_agregar: idioma == constant.idiomaEs ? '[Clic] para agregar un nuevo registro' : 'Click to add a new register',
      lbl_tip_editar: idioma == constant.idiomaEs ? '[Clic] para editar registro' : 'Click to edit the register selected',
      lbl_tip_eliminar: idioma == constant.idiomaEs ? '[Clic] para eliminar registro' : 'Click to delete the register selected',
      lbl_tip_buscar: idioma == constant.idiomaEs ? '[Clic] para buscar registros' : 'Click to search registers',
      lbl_tip_limpiar: idioma == constant.idiomaEs ? '[Clic] para limpiar' : 'Click to clean',
      lbl_tip_anterior: idioma == constant.idiomaEs ? '[Clic] para Regresar' : 'Click to go back',
      lbl_tip_actualizar: idioma == constant.idiomaEs ? '[Clic] para Actualizar' : 'Click to update',
      lbl_tip_guardar: idioma == constant.idiomaEs ? '[Clic] para Guardar' : 'Click to save',

      //Enums
      lbl_enum_generico_valor_vacio: idioma == constant.idiomaEs ? 'Selecciona una opción' : 'Select a Item',

      lbl_enum_si: idioma == constant.idiomaEs ? 'Si' : 'Yes',
      lbl_enum_no: idioma == constant.idiomaEs ? 'No' : 'No',

      lbl_enum_modulo_test: idioma == constant.idiomaEs ? 'Test' : 'Test',
      lbl_enum_modulo_tb_perfil: idioma == constant.idiomaEs ? 'Perfil' : 'Profile',
      lbl_enum_modulo_tb_usuario: idioma == constant.idiomaEs ? 'Usuario' : 'User',
      lbl_enum_modulo_tb_perfil_x_usuario: idioma == constant.idiomaEs ? 'Perfil x Usuario' : 'Profile x User',

      lbl_enum_sexo_valor_masculino: idioma == constant.idiomaEs ? 'Masculino' : 'Man',
      lbl_enum_sexo_valor_femenino: idioma == constant.idiomaEs ? 'Femenino' : 'Femenino',
      lbl_enum_sexo_valor_ambos: idioma == constant.idiomaEs ? 'Ambos' : 'Ambos',

      lbl_enum_tipo_usuario_valor_cliente: idioma == constant.idiomaEs ? 'Cliente' : 'Client',
      lbl_enum_tipo_usuario_valor_empleado: idioma == constant.idiomaEs ? 'Empleado' : 'Employed',
      lbl_enum_tipo_usuario_valor_administrador: idioma == constant.idiomaEs ? 'Administrador' : 'Admin',

      lbl_enum_tipo_documento_valor_cc: idioma == constant.idiomaEs ? 'CC' : 'CC',
      lbl_enum_tipo_documento_valor_ti: idioma == constant.idiomaEs ? 'TI' : 'TI',
      lbl_enum_tipo_documento_valor_ce: idioma == constant.idiomaEs ? 'CE' : 'CE',

      // Módulos Genéricos
      lbl_mtto_generico_activo: idioma == constant.idiomaEs ? 'Activo' : 'Active',

      // Módulo Usuario
      lbl_mtto_usuario_title: idioma == constant.idiomaEs ? 'Configuración de Usuarios' : 'Users Settings',
      lbl_mtto_usuario_nombre: idioma == constant.idiomaEs ? 'Nombre' : 'First Name',
      lbl_mtto_usuario_apellido: idioma == constant.idiomaEs ? 'Apellido' : 'Last Name',
      lbl_mtto_usuario_tipo_documento: idioma == constant.idiomaEs ? 'Tipo Documento' : 'Document Type',
      lbl_mtto_usuario_numero_documento: idioma == constant.idiomaEs ? 'Número Documento' : 'Document Number',
      lbl_mtto_usuario_usuario: idioma == constant.idiomaEs ? 'Usuario' : 'User',
      lbl_mtto_usuario_email: idioma == constant.idiomaEs ? 'Email' : 'Email',
      lbl_mtto_usuario_fecha_nacimiento: idioma == constant.idiomaEs ? 'Fecha Nacimiento' : 'Birth Date',
      lbl_mtto_usuario_tipo_usuario: idioma == constant.idiomaEs ? 'Tipo Usuario' : 'User Type',
      lbl_mtto_usuario_sw_activo: idioma == constant.idiomaEs ? 'Activo' : 'Active',

      lbl_mtto_ubicacion_title: idioma == constant.idiomaEs ? 'Configuración de Ubicaciones' : 'Locations Settings',
      lbl_mtto_ubicacion_pais: idioma == constant.idiomaEs ? 'País' : 'Country',
      lbl_mtto_ubicacion_departamento: idioma == constant.idiomaEs ? 'Departamento/Región' : 'Department/Region',
      lbl_mtto_ubicacion_ciudad: idioma == constant.idiomaEs ? 'Ciudad/Municipio' : 'City/Municipality',
      lbl_mtto_ubicacion_codigo: idioma == constant.idiomaEs ? 'Código' : 'Code',
      lbl_mtto_ubicacion_nombre: idioma == constant.idiomaEs ? 'Nombre' : 'Name',
    }
  };
}
