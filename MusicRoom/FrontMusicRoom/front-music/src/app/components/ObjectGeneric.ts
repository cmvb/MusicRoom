import { Injectable } from '@angular/core';
export var url = 'http://localhost:8080/';

@Injectable()
export class DataObjects {

  getLocaleESForCalendar() {
    return {
      firstDayOfWeek: 1,
      dayNames: ["domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"],
      dayNamesShort: ["dom", "lun", "mar", "mié", "jue", "vie", "sáb"],
      dayNamesMin: ["D", "L", "M", "X", "J", "V", "S"],
      monthNames: ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"],
      monthNamesShort: ["ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"],
      today: 'Hoy',
      clear: 'Borrar'
    }
  };

  getConst() {
    return {
      urlRestService: 'http://localhost:9002/music-room/',
      urlControllerUsuario: 'usuario/',
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
        cod: 3, valores: [
          { value: 0, label: properties.lbl_enum_generico_valor_vacio },
          { value: 1, label: properties.lbl_enum_tipo_usuario_valor_cliente },
          { value: 2, label: properties.lbl_enum_tipo_usuario_valor_empleado },
          { value: 3, label: properties.lbl_enum_tipo_usuario_valor_administrador }
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
      lbl_menu_usuario: idioma == constant.idiomaEs ? 'Usuarios' : 'Users',

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
      lbl_tip_anterior: idioma == constant.idiomaEs ? '[Clic] para Regresas' : 'Click to go back',
      lbl_tip_actualizar: idioma == constant.idiomaEs ? '[Clic] para Actualizar' : 'Click to Update',
      lbl_tip_guardar: idioma == constant.idiomaEs ? '[Clic] para Guardar' : 'Click to Save',

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

      // Módulo Usuario
      lbl_mtto_usuario_title: idioma == constant.idiomaEs ? 'Configuración de Usuarios' : 'Users Settings',
      lbl_mtto_usuario_nombre: idioma == constant.idiomaEs ? 'Nombre' : 'First Name',
      lbl_mtto_usuario_apellido: idioma == constant.idiomaEs ? 'Apellido' : 'Last Name',
      lbl_mtto_usuario_usuario: idioma == constant.idiomaEs ? 'Usuario' : 'User',
      lbl_mtto_usuario_email: idioma == constant.idiomaEs ? 'Email' : 'Email',
      lbl_mtto_usuario_fecha_nacimiento: idioma == constant.idiomaEs ? 'Fecha Nacimiento' : 'Birth Date',
      lbl_mtto_usuario_estado: idioma == constant.idiomaEs ? 'Estado' : 'State',
      lbl_mtto_usuario_tipo_usuario: idioma == constant.idiomaEs ? 'Tipo Usuario' : 'User Type',
      lbl_mtto_usuario_sw_activo: idioma == constant.idiomaEs ? 'Activo' : 'Active',
    }
  };
}
