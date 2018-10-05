package pe.com.ricindigus.appednom2018.modelo;

public class SQLConstantes {
    public static String DB_PATH = "/data/data/pe.com.ricindigus.appednom2018/databases/";
    public static String DB_NAME = "mydatabase.sqlite";

    public static String tablausuariolocal = "usuario_local";
    public static String tablaaulas = "aulas";
    public static String tablacajas = "cajas";
    public static String tablaasistencia = "asistencia";
    public static String tablaasistencia_ra = "asistencia_ra";
    public static String tablafichas = "fichas";
    public static String tablacuadernillos = "cuadernillos";
    public static String tablalistados = "listados";
    public static String tablaversion = "version";



    public static String tablausuarioactual = "usuario_actual";
    public static String tablahistorialusuarios = "historial_usuario";
    public static String tablacajasreg = "cajas_reg";
    public static String tablaasistenciasreg = "asistencia_reg";
    public static String tablaasistencias_ra_reg = "asistencia_ra_reg";
    public static String tablafichasreg = "fichas_reg";
    public static String tablacuadernillosreg = "cuadernillos_reg";
    public static String tablalistadosreg = "listados_reg";



    /**
     * ----------------------------TABLAS INICIALES---------------------------
     * */

    //TABLA USUARIO LOCAL
    public static String usuario_local_id = "_id";
    public static String usuario_local_usuario = "usuario";
    public static String usuario_local_clave = "clave";
    public static String usuario_local_rol = "rol";
    public static String usuario_local_idlocal = "idlocal";
    public static String usuario_local_nom_local = "nom_local";
    public static String usuario_local_naulas = "naulas";
    public static String usuario_local_idsede = "idsede";
    public static String usuario_local_nom_sede = "nom_sede";

    //TABLA VERSION
    public static String version_id = "_id";
    public static String version_numero = "numero";
    public static String version_nombre = "nombre";
    public static String version_coleccion_cajas = "coleccion_cajas";
    public static String version_coleccion_inventario = "coleccion_inventario";
    public static String version_coleccion_asistencia = "coleccion_asistencia";
    public static String version_asis_hora_inicio = "asis_hora_inicio";
    public static String version_asis_hora_fin = "asis_hora_fin";
    public static String version_asis_min_inicio = "asis_min_inicio";
    public static String version_asis_min_fin = "asis_min_fin";
    public static String version_restriccion = "restriccion";


    //TABLA AULA
    public static String aulas_id = "_id";
    public static String aulas_nro_local = "idlocal";
    public static String aulas_naula = "naula";
    public static String aulas_nombre = "nombre";

    //TABLA CAJAS
    public static String cajas_id = "_id";
    public static String cajas_cod_barra = "cod_barra_caja";
    public static String cajas_ccdd = "ccdd";
    public static String cajas_departamento = "departamento";
    public static String cajas_idnacional = "idnacional";
    public static String cajas_idsede = "idsede";
    public static String cajas_nom_sede = "nom_sede";
    public static String cajas_idlocal = "idlocal";
    public static String cajas_nom_local = "nom_local";
    public static String cajas_tipo = "tipo";
    public static String cajas_nlado = "nlado";
    public static String cajas_acl = "acl";
    public static String cajas_direccion = "direccion";


    //TABLA ASISTENCIA
    public static String asistencia_id = "_id";
    public static String asistencia_dni = "dni";
    public static String asistencia_nombres = "nombres";
    public static String asistencia_ape_paterno = "ape_paterno";
    public static String asistencia_ape_materno = "ape_materno";
    public static String asistencia_naula = "naula";
    public static String asistencia_codpagina = "codpagina";
    public static String asistencia_discapacidad = "discapacidad";
    public static String asistencia_prioridad = "prioridad";
    public static String asistencia_idnacional = "idnacional";
    public static String asistencia_idsede = "idsede";
    public static String asistencia_nom_sede = "nom_sede";
    public static String asistencia_ccdd = "ccdd";
    public static String asistencia_departamento = "departamento";
    public static String asistencia_idlocal = "idlocal";
    public static String asistencia_nom_local = "nom_local";
    public static String asistencia_direccion = "direccion";

    //TABLA ASISTENCIA RA
    public static String asistencia_ra_id= "_id";
    public static String asistencia_ra_ccdd = "ccdd";
    public static String asistencia_ra_departamento = "departamento";
    public static String asistencia_ra_idsede = "idsede";
    public static String asistencia_ra_nom_sede = "nom_sede";
    public static String asistencia_ra_idnacional = "idnacional";
    public static String asistencia_ra_idlocal = "idlocal";
    public static String asistencia_ra_nom_local = "nom_local";
    public static String asistencia_ra_red = "red";
    public static String asistencia_ra_tipo_cargo = "tipo_cargo";
    public static String asistencia_ra_nombre_cargo = "nom_cargo";
    public static String asistencia_ra_dni = "dni";
    public static String asistencia_ra_nombres_completos = "nombres_completos";

    //TABLA FICHAS,CUADERNILLO Y LISTADOS
    public static String inventario_id = "_id";
    public static String inventario_codigo = "codigo";
    public static String inventario_tipo = "tipo";
    public static String inventario_ccdd = "ccdd";
    public static String inventario_departamento = "departamento";
    public static String inventario_idnacional = "idnacional";
    public static String inventario_idsede = "idsede";
    public static String inventario_nom_sede = "nom_sede";
    public static String inventario_idlocal = "idlocal";
    public static String inventario_nom_local = "nom_local";
    public static String inventario_dni = "dni";
    public static String inventario_ape_paterno = "ape_paterno";
    public static String inventario_ape_materno = "ape_materno";
    public static String inventario_nombres = "nombres";
    public static String inventario_naula = "naula";
    public static String inventario_codpagina = "codpagina";
    public static String inventario_direccion = "direccion";



    /**
     * ----------------------------FIN TABLAS INICIALES----------------------------
     * */


    /**
     * -----------------TABLAS GENERADAS PARA GUARDAR RESPUESTAS--------------------
     * */

    //TABLA USUARIO ACTUAL
    public static String usuario_actual_id = "_id";
    public static String usuario_actual_usuario = "usuario";
    public static String usuario_actual_clave = "clave";
    public static String usuario_actual_rol = "rol";
    public static String usuario_actual_idlocal = "idlocal";
    public static String usuario_actual_nom_local = "nom_local";
    public static String usuario_actual_naulas = "naulas";
    public static String usuario_actual_idsede = "idsede";
    public static String usuario_actual_nom_sede = "nom_sede";


    public static final String SQL_CREATE_TABLA_USUARIO_ACTUAL =
            "CREATE TABLE " + tablausuarioactual + "(" +
                    usuario_actual_id + " INTEGER PRIMARY KEY," +
                    usuario_actual_usuario + " TEXT," +
                    usuario_actual_clave + " TEXT," +
                    usuario_actual_rol + " INTEGER," +
                    usuario_actual_idlocal + " INTEGER," +
                    usuario_actual_nom_local + " TEXT," +
                    usuario_actual_naulas + " INTEGER," +
                    usuario_actual_idsede + " INTEGER," +
                    usuario_actual_nom_sede + " TEXT" + ");"
            ;

    //TABLA HISTORIAL DE USUARIOS
    public static String historial_usuario_id = "_id";
    public static String historial_usuario_clave = "clave";

    public static final String SQL_CREATE_TABLA_HISTORIAL_USUARIOS =
            "CREATE TABLE " + tablahistorialusuarios + "(" +
                    historial_usuario_id + " INTEGER PRIMARY KEY," +
                    historial_usuario_clave + " TEXT" + ");"
            ;

    //REGISTRO CAJAS REGISTRADAS
    public static String cajasreg_id = "_id";
    public static String cajasreg_cod_barra = "cod_barra_caja";
    public static String cajasreg_ccdd = "ccdd";
    public static String cajasreg_departamento = "departamento";
    public static String cajasreg_idnacional = "idnacional";
    public static String cajasreg_idsede = "idsede";
    public static String cajasreg_nom_sede = "nom_sede";
    public static String cajasreg_idlocal = "idlocal";
    public static String cajasreg_nom_local = "nom_local";
    public static String cajasreg_tipo = "tipo";
    public static String cajasreg_nlado = "nlado";
    public static String cajasreg_acl = "acl";
    public static String cajasreg_direccion = "direccion";
    public static String cajasreg_dia_entrada = "dia_entrada";
    public static String cajasreg_mes_entrada = "mes_entrada";
    public static String cajasreg_anio_entrada = "anio_entrada";
    public static String cajasreg_hora_entrada = "hora_entrada";
    public static String cajasreg_min_entrada = "min_entrada";
    public static String cajasreg_seg_entrada = "seg_entrada";
    public static String cajasreg_estado_entrada = "estado_entrada";
    public static String cajasreg_check_entrada = "check_entrada";
    public static String cajasreg_dia_salida = "dia_salida";
    public static String cajasreg_mes_salida = "mes_salida";
    public static String cajasreg_anio_salida = "anio_salida";
    public static String cajasreg_hora_salida = "hora_salida";
    public static String cajasreg_min_salida = "min_salida";
    public static String cajasreg_seg_salida = "seg_salida";
    public static String cajasreg_estado_salida = "estado_salida";
    public static String cajasreg_check_salida = "check_salida";
    public static String cajasreg_dia_entrada_final = "dia_entrada_final";
    public static String cajasreg_mes_entrada_final = "mes_entrada_final";
    public static String cajasreg_anio_entrada_final = "anio_entrada_final";
    public static String cajasreg_hora_entrada_final = "hora_entrada_final";
    public static String cajasreg_min_entrada_final = "min_entrada_final";
    public static String cajasreg_seg_entrada_final = "seg_entrada_final";
    public static String cajasreg_dia_salida_final = "dia_salida_final";
    public static String cajasreg_mes_salida_final = "mes_salida_final";
    public static String cajasreg_anio_salida_final = "anio_salida_final";
    public static String cajasreg_hora_salida_final = "hora_salida_final";
    public static String cajasreg_min_salida_final = "min_salida_final";
    public static String cajasreg_seg_salida_final = "seg_salida_final";
    public static String cajasreg_leido_orden_entrada = "leido_orden_entrada";
    public static String cajasreg_leido_orden_salida = "leido_orden_salida";
    public static String cajasreg_trans_orden_entrada = "trans_orden_entrada";
    public static String cajasreg_trans_orden_salida = "trans_orden_salida";





    public static final String SQL_CREATE_TABLA_CAJAS_REGISTRADAS =
            "CREATE TABLE " + tablacajasreg + "(" +
                    cajasreg_id + " TEXT PRIMARY KEY," +
                    cajasreg_cod_barra + " TEXT," +
                    cajasreg_ccdd + " TEXT," +
                    cajasreg_departamento + " TEXT," +
                    cajasreg_idnacional + " TEXT," +
                    cajasreg_idsede + " INTEGER," +
                    cajasreg_nom_sede + " TEXT," +
                    cajasreg_idlocal + " INTEGER," +
                    cajasreg_nom_local + " TEXT," +
                    cajasreg_tipo + " INTEGER," +
                    cajasreg_nlado + " INTEGER," +
                    cajasreg_acl + " INTEGER," +
                    cajasreg_direccion + " TEXT," +
                    cajasreg_dia_entrada + " INTEGER," +
                    cajasreg_mes_entrada + " INTEGER," +
                    cajasreg_anio_entrada + " INTEGER," +
                    cajasreg_hora_entrada + " INTEGER," +
                    cajasreg_min_entrada + " INTEGER," +
                    cajasreg_seg_entrada + " INTEGER," +
                    cajasreg_dia_entrada_final + " INTEGER," +
                    cajasreg_mes_entrada_final + " INTEGER," +
                    cajasreg_anio_entrada_final + " INTEGER," +
                    cajasreg_hora_entrada_final + " INTEGER," +
                    cajasreg_min_entrada_final + " INTEGER," +
                    cajasreg_seg_entrada_final + " INTEGER," +
                    cajasreg_estado_entrada + " INTEGER," +
                    cajasreg_dia_salida + " INTEGER," +
                    cajasreg_mes_salida + " INTEGER," +
                    cajasreg_anio_salida + " INTEGER," +
                    cajasreg_hora_salida + " INTEGER," +
                    cajasreg_min_salida + " INTEGER," +
                    cajasreg_seg_salida + " INTEGER," +
                    cajasreg_dia_salida_final + " INTEGER," +
                    cajasreg_mes_salida_final + " INTEGER," +
                    cajasreg_anio_salida_final + " INTEGER," +
                    cajasreg_hora_salida_final + " INTEGER," +
                    cajasreg_min_salida_final + " INTEGER," +
                    cajasreg_seg_salida_final + " INTEGER," +
                    cajasreg_estado_salida + " INTEGER," +
                    cajasreg_check_entrada + " INTEGER," +
                    cajasreg_check_salida + " INTEGER," +
                    cajasreg_leido_orden_entrada + " INTEGER," +
                    cajasreg_trans_orden_entrada + " INTEGER," +
                    cajasreg_leido_orden_salida + " INTEGER," +
                    cajasreg_trans_orden_salida + " INTEGER" + ");"
            ;


    //TABLA ASISTENCIA REGISTRADAS
    public static String asistenciareg_id = "_id";
    public static String asistenciareg_dni = "dni";
    public static String asistenciareg_nombres = "nombres";
    public static String asistenciareg_ape_paterno = "ape_paterno";
    public static String asistenciareg_ape_materno = "ape_materno";
    public static String asistenciareg_naula = "naula";
    public static String asistenciareg_codpagina = "codpagina";
    public static String asistenciareg_discapacidad = "discapacidad";
    public static String asistenciareg_prioridad = "prioridad";
    public static String asistenciareg_idnacional = "idnacional";
    public static String asistenciareg_idsede = "idsede";
    public static String asistenciareg_nom_sede = "nom_sede";
    public static String asistenciareg_ccdd = "ccdd";
    public static String asistenciareg_departamento = "departamento";
    public static String asistenciareg_idlocal = "idlocal";
    public static String asistenciareg_nom_local = "nom_local";
    public static String asistenciareg_direccion = "direccion";
    public static String asistenciareg_dia_local = "dia_local";
    public static String asistenciareg_mes_local = "mes_local";
    public static String asistenciareg_anio_local = "anio_local";
    public static String asistenciareg_hora_local = "hora_local";
    public static String asistenciareg_min_local = "min_local";
    public static String asistenciareg_seg_local = "seg_local";
    public static String asistenciareg_estado_local = "estado_local";
    public static String asistenciareg_dia_aula = "dia_aula";
    public static String asistenciareg_mes_aula = "mes_aula";
    public static String asistenciareg_anio_aula = "anio_aula";
    public static String asistenciareg_hora_aula = "hora_aula";
    public static String asistenciareg_min_aula = "min_aula";
    public static String asistenciareg_seg_aula = "seg_aula";
    public static String asistenciareg_estado_aula = "estado_aula";
    public static String asistenciareg_lei_orden_local = "lei_orden_local";
    public static String asistenciareg_lei_orden_aula = "lei_orden_aula";
    public static String asistenciareg_trans_orden_local = "trans_orden_local";
    public static String asistenciareg_trans_orden_aula = "trans_orden_aula";


    public static final String SQL_CREATE_TABLA_ASISTENCIAS_REGISTRADAS =
            "CREATE TABLE " + tablaasistenciasreg + "(" +
                    asistenciareg_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    asistenciareg_dni + " TEXT," +
                    asistenciareg_nombres + " TEXT," +
                    asistenciareg_ape_paterno + " TEXT," +
                    asistenciareg_ape_materno + " TEXT," +
                    asistenciareg_naula + " INTEGER," +
                    asistenciareg_codpagina + " TEXT," +
                    asistenciareg_discapacidad+ " TEXT," +
                    asistenciareg_prioridad + " TEXT," +
                    asistenciareg_idnacional + " TEXT," +
                    asistenciareg_idsede + " TEXT," +
                    asistenciareg_nom_sede + " TEXT," +
                    asistenciareg_ccdd + " TEXT," +
                    asistenciareg_departamento + " TEXT," +
                    asistenciareg_idlocal + " INTEGER," +
                    asistenciareg_nom_local + " TEXT," +
                    asistenciareg_direccion + " TEXT," +
                    asistenciareg_dia_local + " INTEGER," +
                    asistenciareg_mes_local + " INTEGER," +
                    asistenciareg_anio_local + " INTEGER," +
                    asistenciareg_hora_local + " INTEGER," +
                    asistenciareg_min_local + " INTEGER," +
                    asistenciareg_seg_local + " INTEGER," +
                    asistenciareg_estado_local + " INTEGER," +
                    asistenciareg_dia_aula + " INTEGER," +
                    asistenciareg_mes_aula + " INTEGER," +
                    asistenciareg_anio_aula + " INTEGER," +
                    asistenciareg_hora_aula + " INTEGER," +
                    asistenciareg_min_aula + " INTEGER," +
                    asistenciareg_seg_aula + " INTEGER," +
                    asistenciareg_estado_aula + " INTEGER," +
                    asistenciareg_lei_orden_aula + " INTEGER," +
                    asistenciareg_lei_orden_local + " INTEGER," +
                    asistenciareg_trans_orden_aula + " INTEGER," +
                    asistenciareg_trans_orden_local + " INTEGER" + ");"
            ;


    //TABLA ASISTENCIA RA REGISTRADOS
    public static String asistenciareg_ra_id= "_id";
    public static String asistenciareg_ra_ccdd = "ccdd";
    public static String asistenciareg_ra_departamento = "departamento";
    public static String asistenciareg_ra_idsede = "idsede";
    public static String asistenciareg_ra_nom_sede = "nom_sede";
    public static String asistenciareg_ra_idnacional = "idnacional";
    public static String asistenciareg_ra_idlocal = "idlocal";
    public static String asistenciareg_ra_nom_local = "nom_local";
    public static String asistenciareg_ra_red = "red";
    public static String asistenciareg_ra_tipo_cargo = "tipo_cargo";
    public static String asistenciareg_ra_nombre_cargo = "nombre_cargo";
    public static String asistenciareg_ra_dni = "dni";
    public static String asistenciareg_ra_nombres_completos = "nombres_completos";
    public static String asistenciareg_ra_dia = "dia";
    public static String asistenciareg_ra_mes = "mes";
    public static String asistenciareg_ra_anio = "anio";
    public static String asistenciareg_ra_hora = "hora";
    public static String asistenciareg_ra_min = "min";
    public static String asistenciareg_ra_seg = "seg";
    public static String asistenciareg_ra_estado = "estado";
    public static String asistenciareg_ra_leida_orden = "leida_orden";
    public static String asistenciareg_ra_trans_orden = "trans_orden";



    public static final String SQL_CREATE_TABLA_ASISTENCIAS_RA_REGISTRADAS =
            "CREATE TABLE " + tablaasistencias_ra_reg + "(" +
                    asistenciareg_ra_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    asistenciareg_ra_ccdd + " TEXT," +
                    asistenciareg_ra_departamento + " TEXT," +
                    asistenciareg_ra_idsede + " TEXT," +
                    asistenciareg_ra_nom_sede + " TEXT," +
                    asistenciareg_ra_idnacional + " INTEGER," +
                    asistenciareg_ra_idlocal + " INTEGER," +
                    asistenciareg_ra_nom_local + " TEXT," +
                    asistenciareg_ra_red + " INTEGER," +
                    asistenciareg_ra_tipo_cargo + " INTEGER," +
                    asistenciareg_ra_nombre_cargo + " TEXT," +
                    asistenciareg_ra_dni + " TEXT," +
                    asistenciareg_ra_nombres_completos + " TEXT," +
                    asistenciareg_ra_dia + " INTEGER," +
                    asistenciareg_ra_mes + " INTEGER," +
                    asistenciareg_ra_anio + " INTEGER," +
                    asistenciareg_ra_hora + " INTEGER," +
                    asistenciareg_ra_min + " INTEGER," +
                    asistenciareg_ra_seg + " INTEGER," +
                    asistenciareg_ra_estado + " INTEGER," +
                    asistenciareg_ra_leida_orden + " INTEGER," +
                    asistenciareg_ra_trans_orden + " INTEGER" + ");"
            ;


    //TABLA FICHAS, CUADERNILLOS Y LISTADOS REGISTRADOS
    public static String inventarioreg_id = "_id";
    public static String inventarioreg_codigo = "codigo";
    public static String inventarioreg_tipo = "tipo";
    public static String inventarioreg_ccdd = "ccdd";
    public static String inventarioreg_departamento = "departamento";
    public static String inventarioreg_idnacional = "idnacional";
    public static String inventarioreg_idsede = "idsede";
    public static String inventarioreg_nom_sede = "nom_sede";
    public static String inventarioreg_idlocal = "idlocal";
    public static String inventarioreg_nom_local = "nom_local";
    public static String inventarioreg_dni = "dni";
    public static String inventarioreg_ape_paterno = "ape_paterno";
    public static String inventarioreg_ape_materno = "ape_materno";
    public static String inventarioreg_nombres = "nombres";
    public static String inventarioreg_naula = "naula";
    public static String inventarioreg_codpagina = "codpagina";
    public static String inventarioreg_direccion = "direccion";
    public static String inventarioreg_dia = "dia";
    public static String inventarioreg_mes = "mes";
    public static String inventarioreg_anio = "anio";
    public static String inventarioreg_hora = "hora";
    public static String inventarioreg_min = "min";
    public static String inventarioreg_seg = "seg";
    public static String inventarioreg_estado = "estado";
    public static String inventarioreg_npostulantes = "npostulantes";
    public static String inventarioreg_leida_orden = "leida_orden";
    public static String inventarioreg_trans_orden = "trans_orden";

    public static final String SQL_CREATE_TABLA_FICHAS_REGISTRADAS=
            "CREATE TABLE " + tablafichasreg + "(" +
                    inventarioreg_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    inventarioreg_codigo + " TEXT," +
                    inventarioreg_tipo + " INTEGER," +
                    inventarioreg_ccdd + " TEXT," +
                    inventarioreg_departamento + " TEXT," +
                    inventarioreg_idnacional + " INTEGER," +
                    inventarioreg_idsede + " TEXT," +
                    inventarioreg_nom_sede + " TEXT," +
                    inventarioreg_idlocal + " INTEGER," +
                    inventarioreg_nom_local + " TEXT," +
                    inventarioreg_dni + " TEXT," +
                    inventarioreg_ape_paterno + " TEXT," +
                    inventarioreg_ape_materno + " TEXT," +
                    inventarioreg_nombres + " TEXT," +
                    inventarioreg_naula + " INTEGER," +
                    inventarioreg_codpagina + " TEXT," +
                    inventarioreg_direccion + " TEXT," +
                    inventarioreg_dia + " INTEGER," +
                    inventarioreg_mes + " INTEGER," +
                    inventarioreg_anio + " INTEGER," +
                    inventarioreg_hora + " INTEGER," +
                    inventarioreg_min + " INTEGER," +
                    inventarioreg_seg + " INTEGER," +
                    inventarioreg_estado + " INTEGER," +
                    inventarioreg_npostulantes + " INTEGER," +
                    inventarioreg_leida_orden + " INTEGER," +
                    inventarioreg_trans_orden + " INTEGER" + ");"
            ;




    public static final String SQL_CREATE_TABLA_CUADERNILLOS_REGISTRADOS=
            "CREATE TABLE " + tablacuadernillosreg + "(" +
                    inventarioreg_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    inventarioreg_codigo + " TEXT," +
                    inventarioreg_tipo + " INTEGER," +
                    inventarioreg_ccdd + " TEXT," +
                    inventarioreg_departamento + " TEXT," +
                    inventarioreg_idnacional + " INTEGER," +
                    inventarioreg_idsede + " TEXT," +
                    inventarioreg_nom_sede + " TEXT," +
                    inventarioreg_idlocal + " INTEGER," +
                    inventarioreg_nom_local + " TEXT," +
                    inventarioreg_dni + " TEXT," +
                    inventarioreg_ape_paterno + " TEXT," +
                    inventarioreg_ape_materno + " TEXT," +
                    inventarioreg_nombres + " TEXT," +
                    inventarioreg_naula + " INTEGER," +
                    inventarioreg_codpagina + " TEXT," +
                    inventarioreg_direccion + " TEXT," +
                    inventarioreg_dia + " INTEGER," +
                    inventarioreg_mes + " INTEGER," +
                    inventarioreg_anio + " INTEGER," +
                    inventarioreg_hora + " INTEGER," +
                    inventarioreg_min + " INTEGER," +
                    inventarioreg_seg + " INTEGER," +
                    inventarioreg_estado + " INTEGER," +
                    inventarioreg_npostulantes + " INTEGER," +
                    inventarioreg_leida_orden + " INTEGER," +
                    inventarioreg_trans_orden + " INTEGER" + ");"
            ;


    public static final String SQL_CREATE_TABLA_LISTADOS_REGISTRADOS=
            "CREATE TABLE " + tablalistadosreg + "(" +
                    inventarioreg_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    inventarioreg_codigo + " TEXT," +
                    inventarioreg_tipo + " INTEGER," +
                    inventarioreg_ccdd + " TEXT," +
                    inventarioreg_departamento + " TEXT," +
                    inventarioreg_idnacional + " INTEGER," +
                    inventarioreg_idsede + " TEXT," +
                    inventarioreg_nom_sede + " TEXT," +
                    inventarioreg_idlocal + " INTEGER," +
                    inventarioreg_nom_local + " TEXT," +
                    inventarioreg_dni + " TEXT," +
                    inventarioreg_ape_paterno + " TEXT," +
                    inventarioreg_ape_materno + " TEXT," +
                    inventarioreg_nombres + " TEXT," +
                    inventarioreg_naula + " INTEGER," +
                    inventarioreg_codpagina + " TEXT," +
                    inventarioreg_direccion + " TEXT," +
                    inventarioreg_dia + " INTEGER," +
                    inventarioreg_mes + " INTEGER," +
                    inventarioreg_anio + " INTEGER," +
                    inventarioreg_hora + " INTEGER," +
                    inventarioreg_min + " INTEGER," +
                    inventarioreg_seg + " INTEGER," +
                    inventarioreg_estado + " INTEGER," +
                    inventarioreg_npostulantes + " INTEGER," +
                    inventarioreg_leida_orden + " INTEGER," +
                    inventarioreg_trans_orden + " INTEGER" + ");"
            ;
    /**
     * -----------------FIN TABLAS GENERADAS PARA GUARDAR RESPUESTAS--------------------
     * */

    /**
     * -----------------CLAUSULAS WHERE--------------------
     * */
    public static final String WHERE_CLAUSE_ID = "_id=?";
    public static final String WHERE_CLAUSE_CLAVE = "clave=?";
    public static final String WHERE_CLAUSE_NRO_LOCAL = "nro_local=?";
    public static final String WHERE_CLAUSE_COD_BARRA = "cod_barra_caja=?";
    public static final String WHERE_CLAUSE_ID_LOCAL = "idlocal=?";
    public static final String WHERE_CLAUSE_TIPO_CAJA = "tipo=?";
    public static final String WHERE_CLAUSE_TIPO_MATERIAL = "tipo=?";
    public static final String WHERE_CLAUSE_NRO_LADO = "nlado=?";
    public static final String WHERE_CLAUSE_ESTADO_ENTRADA = "estado_entrada=?";
    public static final String WHERE_CLAUSE_ESTADO_SALIDA = "estado_salida=?";
    public static final String WHERE_CLAUSE_ESTADO_LOCAL = "estado_local=?";
    public static final String WHERE_CLAUSE_RA_TIPO_CARGO = "tipo_cargo=?";

    public static final String WHERE_CLAUSE_ESTADO_AULA = "estado_aula=?";
    public static final String WHERE_CLAUSE_ESTADO = "estado=?";
    public static final String WHERE_CLAUSE_DNI = "dni=?";
    public static final String WHERE_CLAUSE_NRO_AULA = "naula=?";
    public static final String WHERE_CLAUSE_CODIGO = "codigo=?";
    public static final String WHERE_CLAUSE_COD_PAGINA = "codpagina=?";
    public static final String WHERE_CLAUSE_LOCAL = "local=?";
    public static final String WHERE_CLAUSE_ID_AULA = "id_aula=?";
    public static final String WHERE_CLAUSE_CODIGO_FICHA = "codficha=?";
    public static final String WHERE_CLAUSE_CODIGO_CUADERNILLO = "codcartilla=?";
    public static final String WHERE_CLAUSE_NOMBRE_AULA = "nombre=?";
    public static final String WHERE_CLAUSE_SEDE = "sede=?";
    public static final String WHERE_CLAUSE_DIA = "dia=?";
    public static final String WHERE_CLAUSE_MES = "mes=?";
    public static final String WHERE_CLAUSE_ANIO = "anio=?";

    /**
     * -----------------FINAL CLAUSULAS WHERE--------------------
     * */




//    public static final String[] COLUMNAS_NACIONAL = {
//            nacional_codigo,nacional_apepat,nacional_aplicacion,
//            nacional_aula, nacional_discapacidad, nacional_sede
//    };
}
