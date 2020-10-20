import { Injectable } from '@angular/core';
import { ObjectModelInitializer } from './ObjectModelInitializer';


@Injectable()
export class TextProperties {

  constructor(public objectModelInitializer: ObjectModelInitializer) {
  }

  getProperties(idioma) {
    let constant = this.objectModelInitializer.getConst();
    return {
      // Mensajes
      lbl_summary_info: idioma == constant.idiomaEs ? '¡Información!' : 'Info!',
      lbl_summary_success: idioma == constant.idiomaEs ? '¡Exitoso!' : 'Success!',
      lbl_summary_warning: idioma == constant.idiomaEs ? '¡Advertencia!' : 'Warning!',
      lbl_summary_danger: idioma == constant.idiomaEs ? '¡Error!' : 'Error!',
      lbl_summary_unknown_danger: idioma == constant.idiomaEs ? '¡Error Desconocido!' : 'Unknown Error!',
      lbl_detail_fue: idioma == constant.idiomaEs ? ' fue ' : ' has ',
      lbl_detail_el_registro: idioma == constant.idiomaEs ? 'El elemento #' : 'The element #',
      lbl_detail_el_registro_eliminado: idioma == constant.idiomaEs ? 'El elemento fue eliminado satisfactoriamente.' : 'The element was deleted successfully.',
      lbl_detail_creado: idioma == constant.idiomaEs ? 'creado' : 'created',
      lbl_detail_actualizado: idioma == constant.idiomaEs ? 'actualizado' : 'updated',
      lbl_detail_satisfactoriamente: idioma == constant.idiomaEs ? ' satisfactoriamente.' : ' successfully.',

      // Generales
      lbl_info_sin_resultados: idioma == constant.idiomaEs ? 'Sin Resultados' : 'Without Results',
      lbl_info_fallo_conectar_base_datos: idioma == constant.idiomaEs ? 'No hay Conexión a la Base de Datos' : 'Without Conection to Data Base',
      lbl_info_cargando_resultados: idioma == constant.idiomaEs ? 'Cargando Resultados' : 'Loading Results',
      lbl_info_proceso_completo: idioma == constant.idiomaEs ? 'Proceso realizado Satisfactoriamente' : 'Process Complete',
      lbl_error_403: idioma == constant.idiomaEs ? '403' : '403',
      lbl_error_404: idioma == constant.idiomaEs ? '404' : '404',
      lbl_error_500: idioma == constant.idiomaEs ? '500' : '500',
      lbl_mensaje_error_404: idioma == constant.idiomaEs ? 'La página que busca no ha sido encontrada.' : 'Page Not Found.',
      lbl_oops: idioma == constant.idiomaEs ? '¡Oops!' : 'Oops!',
      lbl_vencimiento_token_sesion: idioma == constant.idiomaEs ? 'Fecha de Vencimiento del Token: ' : 'Token Expiration Date',
      lbl_vcode_expiro: idioma == constant.idiomaEs ? 'El Código de Verificación ya expiró.' : 'The Verification Code has expired.',
      lbl_ir_a: idioma == constant.idiomaEs ? 'Ir a: ' : 'Go to: ',
      lbl_ir_inicio: idioma == constant.idiomaEs ? 'Ir al Login' : 'Go to Login',
      lbl_btn_olvidaste_tu_clave: idioma == constant.idiomaEs ? '¿Olvidaste tu Clave?' : 'Do You forget your Password?',
      lbl_btn_no_ha_llegado_correo_vcode: idioma == constant.idiomaEs ? '¿No ha llegado ningún Correo? Presiona para Reintentar.' : 'No mail has arrived? Press to Retry.',
      lbl_drag_archivos: idioma == constant.idiomaEs ? 'Haga [Clic] o arrastre un archivo aquí' : 'Click or Drag a file here',
      lbl_cantidad_max_archivos: idioma == constant.idiomaEs ? 'Cantidad máxima: 5 archivos' : 'Only 5 Files',

      // Mensajes
      lbl_mensaje_archivo_subido: idioma == constant.idiomaEs ? 'Archivo(s) subido correctamente.' : 'File(s) uploaded successfully.',
      lbl_mensaje_cant_archivos_permitidos: idioma == constant.idiomaEs ? 'La cantidad de archivos por subir supera la cantidad permitida.' : 'The number of files to upload exceeds the allowed number.',
      lbl_mensaje_cant_archivos_permitidos_detalle: idioma == constant.idiomaEs ? 'Máximo {0} archivo(s).' : 'Limit is {0} at most.',
      lbl_mensaje_dropzone_principal: idioma == constant.idiomaEs ? 'Esta es una zona de carga de archivos' : 'This is a file upload area.',
      lbl_mensaje_dropzone_secundario: idioma == constant.idiomaEs ? 'Arrastre su archivo o dé [Clic] para subir uno desde el explorador de archivos' : 'Drag your file or Click to upload one from file explorer.',
      lbl_mensaje_size_archivos_permitidos: idioma == constant.idiomaEs ? 'Error con el tamaño del archivo: ' : 'File size error: ',
      lbl_mensaje_size_archivos_permitidos_detalle: idioma == constant.idiomaEs ? 'El tamaño del archivo excede lo permitido. El máximo es ' : 'The file size exceeds what is allowed. Maximum upload size is ',
      lbl_mensaje_tipo_archivos_permitidos_generico: idioma == constant.idiomaEs ? 'El tipo del archivo o elemento no es permitido.' : 'The file or element type is not allowed.',
      lbl_mensaje_tipo_archivos_permitidos: idioma == constant.idiomaEs ? 'Error con el tipo del archivo: ' : 'File type error: ',
      lbl_mensaje_tipo_archivos_permitidos_detalle: idioma == constant.idiomaEs ? 'El tipo del archivo no es permitido. Se permiten {0}.' : 'The file type is not allowed. Allowed file types {0}.',
      lbl_mensaje_archivo_no_subido: idioma == constant.idiomaEs ? 'El archivo no pudo ser procesado.' : 'The file could not be processed.',
      lbl_mensaje_seleccione_archivo_para_subir: idioma == constant.idiomaEs ? 'Seleccione un archivo.' : 'Select a file.',
      lbl_mensaje_no_conexion_servidor: idioma == constant.idiomaEs ? 'No se ha podido establecer la conexión con el Servidor en algun proceso interno' : 'The connection to the Server could not be established in some internal process',
      lbl_mensaje_login_invalido: idioma == constant.idiomaEs ? 'El Usuario y/o la Contraseña son incorrectos' : 'The User and/or Password are incorrect',
      lbl_mensaje_sin_detalles_error: idioma == constant.idiomaEs ? 'No hay detalles del error' : 'No error details',
      lbl_mensaje_error_403_ingresar_ruta: idioma == constant.idiomaEs ? 'Está intentando ingresar a la ruta: ' : '',
      lbl_mensaje_error_403_no_permisos: idioma == constant.idiomaEs ? 'No cuenta con permisos para visualizar el contenido.' : 'You do not have permissions to view its content.',
      lbl_mensaje_error_403_sesion_expirada: idioma == constant.idiomaEs ? 'Su sesión ha expirado. Debe ingresar de nuevo a la aplicación.' : 'Your session has expired. You must enter the application again.',
      lbl_mensaje_error_500_no_sesion: idioma == constant.idiomaEs ? 'No tiene una Sesión Iniciada.' : 'You dont have a session started.',

      // Modales
      lbl_info_titulo_modal_error: idioma == constant.idiomaEs ? 'ERROR' : 'ERROR',
      lbl_info_titulo_modal_informacion: idioma == constant.idiomaEs ? 'INFORMACION' : 'INFORMATION',
      lbl_info_titulo_modal_advertencia: idioma == constant.idiomaEs ? 'ADVERTECNIA' : 'WARNING',
      lbl_info_titulo_modal_proceso_exitoso: idioma == constant.idiomaEs ? 'PROCESO EXITOSO' : 'PROCESS COMPLETE',

      // Steps
      lbl_info_titulo_step_personal: idioma == constant.idiomaEs ? 'PERSONAL' : 'PERSONAL',
      lbl_info_titulo_step_identificacion: idioma == constant.idiomaEs ? 'IDENTIFICACIÓN' : 'IDENTIFICATION',
      lbl_info_titulo_step_seguridad: idioma == constant.idiomaEs ? 'SEGURIDAD' : 'SECURITY',
      lbl_info_titulo_step_confirmacion: idioma == constant.idiomaEs ? 'CONFIRMACION' : 'CONFIRMATION',

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
      lbl_btn_ingresar: idioma == constant.idiomaEs ? 'Ingresar' : 'Sign In',
      lbl_btn_registrarse: idioma == constant.idiomaEs ? 'Registrarse' : 'Register',
      lbl_btn_inicio: idioma == constant.idiomaEs ? 'Inicio' : 'Login',
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
      lbl_btn_siguiente: idioma == constant.idiomaEs ? 'Siguiente' : 'Next',
      lbl_btn_anterior: idioma == constant.idiomaEs ? 'Anterior' : 'Back',
      lbl_btn_cancelar: idioma == constant.idiomaEs ? 'Cancelar' : 'Cancel',
      lbl_btn_subir: idioma == constant.idiomaEs ? 'Subir' : 'Upload',
      lbl_btn_escoger: idioma == constant.idiomaEs ? 'Escoger' : 'Choose',
      lbl_btn_escoger_archivo: idioma == constant.idiomaEs ? 'Escoger Archivo' : 'Choose File',
      lbl_subir_archivos: idioma == constant.idiomaEs ? 'Subir Archivos' : 'Upload Files',

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
      lbl_tip_anterior: idioma == constant.idiomaEs ? '[Clic] para regresar' : 'Click to go back',
      lbl_tip_actualizar: idioma == constant.idiomaEs ? '[Clic] para actualizar' : 'Click to update',
      lbl_tip_guardar: idioma == constant.idiomaEs ? '[Clic] para guardar' : 'Click to save',
      lbl_tip_exportar_datos: idioma == constant.idiomaEs ? '[Clic] para exportar' : 'Click to export',
      lbl_tip_subir_archivos: idioma == constant.idiomaEs ? '[Clic] para subir archivos' : 'Click to upload files',
      lbl_tip_eliminar_archivo: idioma == constant.idiomaEs ? '[Clic] para eliminar archivo' : 'Click to delete file',
      lbl_tip_mostrar_ocultar_archivos: idioma == constant.idiomaEs ? '[Clic] para mostrar/ocultar archivos' : 'Click to show/hide files',

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

      lbl_enum_tipo_ubicacion_valor_pais: idioma == constant.idiomaEs ? 'País' : 'Country',
      lbl_enum_tipo_ubicacion_valor_departamento: idioma == constant.idiomaEs ? 'Departamento/Región/Estado' : 'Department/Region/State',
      lbl_enum_tipo_ubicacion_valor_ciudad: idioma == constant.idiomaEs ? 'Ciudad' : 'City',

      // Módulos Genéricos
      lbl_mtto_generico_activo: idioma == constant.idiomaEs ? 'Activo' : 'Active',
      lbl_mtto_generico_step_1_registrar_usuario: idioma == constant.idiomaEs ? 'Registre su Información Personal' : 'Register your Personal Information.',
      lbl_mtto_generico_step_2_registrar_usuario: idioma == constant.idiomaEs ? 'Registre su Información de Identificación' : 'Register your Identification Information.',
      lbl_mtto_generico_step_3_registrar_usuario: idioma == constant.idiomaEs ? 'Cree su nuevo Usuario' : 'Create your new user.',
      lbl_mtto_generico_step_4_registrar_usuario: idioma == constant.idiomaEs ? 'Ingrese el código de verificación que se ha enviado a su correo para Activar la Cuenta.' : 'Enter the verification code that sent your email to activate the account.',
      lbl_mtto_generico_step_registrar_usuario_error: idioma == constant.idiomaEs ? 'Los datos suministrados en este Paso son incorrectos. Verifique la información.' : 'The data provided in this step is incorrect. Check the information.',
      lbl_mtto_generico_codigo_verificaicion_enviado_ok: idioma == constant.idiomaEs ? 'Se ha enviado el Código de Verificación de Cuenta correctamente.' : 'The Account Verification Code has been sent correctly.',

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

      // Módulo Ubicación
      lbl_mtto_ubicacion_title: idioma == constant.idiomaEs ? 'Configuración de Ubicaciones' : 'Locations Settings',
      lbl_mtto_ubicacion_pais: idioma == constant.idiomaEs ? 'País' : 'Country',
      lbl_mtto_ubicacion_departamento: idioma == constant.idiomaEs ? 'Departamento/Región/Estado' : 'Department/Region/State',
      lbl_mtto_ubicacion_ciudad: idioma == constant.idiomaEs ? 'Ciudad/Municipio' : 'City/Municipality',
      lbl_mtto_ubicacion_codigo: idioma == constant.idiomaEs ? 'Código' : 'Code',
      lbl_mtto_ubicacion_nombre: idioma == constant.idiomaEs ? 'Nombre' : 'Name',
      lbl_mtto_ubicacion_tipo_ubicacion: idioma == constant.idiomaEs ? 'Tipo Ubicación' : 'Ubication Type',

      // Módulo Tercero
      lbl_mtto_tercero_title: idioma == constant.idiomaEs ? 'Configuración de Terceros/Empresas' : 'Companies Settings',
      lbl_mtto_tercero_nit: idioma == constant.idiomaEs ? 'NIT' : 'NIT',
      lbl_mtto_tercero_razon_social: idioma == constant.idiomaEs ? 'Razón Social' : 'Business Name',
      lbl_mtto_tercero_ubicacion: idioma == constant.idiomaEs ? 'Ubicación' : 'Location',
      lbl_mtto_tercero_telefono1: idioma == constant.idiomaEs ? 'Teléfono 1' : 'Telephone 1',
      lbl_mtto_tercero_telefono2: idioma == constant.idiomaEs ? 'Teléfono 2' : 'Telephone 2',
      lbl_mtto_tercero_direccion: idioma == constant.idiomaEs ? 'Dirección' : 'Address',
      lbl_mtto_tercero_info_adicional: idioma == constant.idiomaEs ? 'Información Adicional' : 'Other Info',

      // Módulo Sala
      lbl_mtto_sala_title: idioma == constant.idiomaEs ? 'Configuración de Salas de Ensayo' : 'Rehearsal Rooms Settings',
      lbl_mtto_sala_nombre: idioma == constant.idiomaEs ? 'Nombre' : 'Name',
      lbl_mtto_sala_tercero: idioma == constant.idiomaEs ? 'Tercero' : 'Company',
      lbl_mtto_sala_info_adicional: idioma == constant.idiomaEs ? 'Info. Adicional' : 'Additional Info.',
      lbl_mtto_sala_foto_principal: idioma == constant.idiomaEs ? 'Foto Principal' : 'Main Photo',
      lbl_mtto_sala_foto: idioma == constant.idiomaEs ? 'Foto' : 'Photo',
      lbl_mtto_sala_1: idioma == constant.idiomaEs ? '1' : '1',
      lbl_mtto_sala_2: idioma == constant.idiomaEs ? '2' : '2',
      lbl_mtto_sala_3: idioma == constant.idiomaEs ? '3' : '3',
      lbl_mtto_sala_4: idioma == constant.idiomaEs ? '4' : '4',
      lbl_mtto_sala_escoger_foto: idioma == constant.idiomaEs ? 'Escoger Foto' : 'Choose Photo',

      // Módulo Banda/Integrante
      lbl_mtto_banda_title: idioma == constant.idiomaEs ? 'Configuración de Banda/Grupo Musical' : 'Bands/Musical Group Settings',
      lbl_mtto_banda_nombre: idioma == constant.idiomaEs ? 'Nombre' : 'Name',
      lbl_mtto_banda_fecha_inicio: idioma == constant.idiomaEs ? 'Tercero' : 'Company',
      lbl_mtto_banda_genero: idioma == constant.idiomaEs ? 'Género' : 'Gender',
      lbl_mtto_banda_foto_principal: idioma == constant.idiomaEs ? 'Foto Principal' : 'Main Photo',
      lbl_mtto_banda_logo: idioma == constant.idiomaEs ? 'Logo' : 'Logo',
      lbl_mtto_banda_escoger_foto: idioma == constant.idiomaEs ? 'Escoger Foto' : 'Choose Photo',
    }
  };
}