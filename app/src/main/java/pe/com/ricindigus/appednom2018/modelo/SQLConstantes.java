package pe.com.ricindigus.appednom2018.modelo;

public class SQLConstantes {
    public static String DB_PATH = "/data/data/pe.com.ricindigus.appednom2018/databases/";
    public static String DB_NAME = "mydatabase.sqlite";

    public static String tablausuariolocal = "usuario_local";
    public static String tablacajas = "cajas";
    public static String tablaasistencia = "asistencia";
    public static String tablaaulas = "aulas";
    public static String tablacajasentrada = "cajas_entrada";
    public static String tablacajassalida = "cajas_salida";
    public static String tablaasistenciaaula = "asistencia_aula";
    public static String tablaasistencialocal = "asistencia_local";


    public static String tablafichas = "fichas";
    public static String tablacuadernillos = "cuadernillos";
    public static String tablalistados = "listados";
    public static String tablanacional = "nacional";



    //TABLA USUARIO LOCAL
    public static String usuario_local_id = "_id";
    public static String usuario_local_usuario = "usuario";
    public static String usuario_local_clave = "clave";
    public static String usuario_local_rol = "rol";
    public static String usuario_local_nro_local = "nro_local";
    public static String usuario_local_nombreLocal = "nombreLocal";
    public static String usuario_local_naulas = "naulas";
    public static String usuario_local_ncontingencia = "ncontingencia";
    public static String usuario_local_codsede = "codsede";
    public static String usuario_local_sede = "sede";
    public static String usuario_local_nombre = "nombre";


    //TABLA CAJAS
    public static String cajas_id = "_id";
    public static String cajas_cod_barra = "cod_barra_caja";
    public static String cajas_idsede = "idsede";
    public static String cajas_nomsede = "sede";
    public static String cajas_idlocal = "idlocal";
    public static String cajas_nomlocal = "local";
    public static String cajas_tipo = "tipo";
    public static String cajas_acl = "acl";
    public static String cajas_nlado = "nlado";


    //TABLA ASISTENCIA
    public static String asistencia_id = "_id";
    public static String asistencia_dni = "dni";
    public static String asistencia_idnacional = "idnacional";
    public static String asistencia_ccdd = "ccdd";
    public static String asistencia_idsede = "idsede";
    public static String asistencia_sede = "sede";
    public static String asistencia_idlocal = "idlocal";
    public static String asistencia_local = "local";
    public static String asistencia_direccion = "direccion";
    public static String asistencia_nombres = "nombres";
    public static String asistencia_ape_paterno = "ape_paterno";
    public static String asistencia_ape_materno = "ape_materno";
    public static String asistencia_naula = "naula";
    public static String asistencia_discapacidad = "discapacidad";
    public static String asistencia_prioridad = "prioridad";

    //TABLA AULA
    public static String aulas_id = "_id";
    public static String aulas_nro_local = "idlocal";
    public static String aulas_naula = "naula";
    public static String aulas_nombre = "nombre";



    //TABLA CAJAS ENTRADA
    public static String cajas_entrada_id = "_id";
    public static String cajas_entrada_cod_barra = "cod_barra_caja";
    public static String cajas_entrada_idsede = "idsede";
    public static String cajas_entrada_nomsede = "sede";
    public static String cajas_entrada_idlocal = "idlocal";
    public static String cajas_entrada_nomlocal = "local";
    public static String cajas_entrada_tipo = "tipo";
    public static String cajas_entrada_acl = "acl";
    public static String cajas_entrada_fecha_reg_dia = "dia";
    public static String cajas_entrada_fecha_reg_mes = "mes";
    public static String cajas_entrada_fecha_reg_anio = "anio";
    public static String cajas_entrada_fecha_reg_hora = "hora";
    public static String cajas_entrada_fecha_reg_min = "min";
    public static String cajas_entrada_fecha_reg_seg = "seg";
    public static String cajas_entrada_check_reg = "check_reg";
    public static String cajas_entrada_estado = "estado";
    public static String cajas_entrada_nlado = "nlado";



    //TABLA CAJAS SALIDA
    public static String cajas_salida_id = "_id";
    public static String cajas_salida_cod_barra = "cod_barra_caja";
    public static String cajas_salida_idsede = "idsede";
    public static String cajas_salida_nomsede = "sede";
    public static String cajas_salida_idlocal = "idlocal";
    public static String cajas_salida_nomlocal = "local";
    public static String cajas_salida_tipo = "tipo";
    public static String cajas_salida_acl = "acl";
    public static String cajas_salida_fecha_reg_dia = "dia";
    public static String cajas_salida_fecha_reg_mes = "mes";
    public static String cajas_salida_fecha_reg_anio = "anio";
    public static String cajas_salida_fecha_reg_hora = "hora";
    public static String cajas_salida_fecha_reg_min = "min";
    public static String cajas_salida_fecha_reg_seg = "seg";
    public static String cajas_salida_check_reg = "check_reg";
    public static String cajas_salida_estado = "estado";
    public static String cajas_salida_nlado = "nlado";

    //TABLA ASISTENCIA LOCAL
    public static String asis_local_id = "_id";
    public static String asis_local_dni = "dni";
    public static String asis_local_idnacional = "idnacional";
    public static String asis_local_ccdd = "ccdd";
    public static String asis_local_idsede = "idsede";
    public static String asis_local_sede = "sede";
    public static String asis_local_idlocal = "idlocal";
    public static String asis_local_local = "local";
    public static String asis_local_direccion = "direccion";
    public static String asis_local_nombres = "nombres";
    public static String asis_local_ape_paterno = "ape_paterno";
    public static String asis_local_ape_materno = "ape_materno";
    public static String asis_local_naula = "naula";
    public static String asis_local_discapacidad = "discapacidad";
    public static String asis_local_prioridad = "prioridad";
    public static String asis_local_fecha_dia = "dia";
    public static String asis_local_fecha_mes = "mes";
    public static String asis_local_fecha_anio = "anio";
    public static String asis_local_fecha_hora = "hora";
    public static String asis_local_fecha_min = "min";
    public static String asis_local_fecha_seg = "seg";
    public static String asis_local_estado = "estado";


    //TABLA ASISTENCIA AULA
    public static String asis_aula_id = "_id";
    public static String asis_aula_dni = "dni";
    public static String asis_aula_idnacional = "idnacional";
    public static String asis_aula_ccdd = "ccdd";
    public static String asis_aula_idsede = "idsede";
    public static String asis_aula_sede = "sede";
    public static String asis_aula_idlocal = "idlocal";
    public static String asis_aula_local = "local";
    public static String asis_aula_direccion = "direccion";
    public static String asis_aula_nombres = "nombres";
    public static String asis_aula_ape_paterno = "ape_paterno";
    public static String asis_aula_ape_materno = "ape_materno";
    public static String asis_aula_naula = "naula";
    public static String asis_aula_discapacidad = "discapacidad";
    public static String asis_aula_prioridad = "prioridad";
    public static String asis_aula_fecha_dia = "dia";
    public static String asis_aula_fecha_mes = "mes";
    public static String asis_aula_fecha_anio = "anio";
    public static String asis_aula_fecha_hora = "hora";
    public static String asis_aula_fecha_min = "min";
    public static String asis_aula_fecha_seg = "seg";
    public static String asis_aula_estado = "estado";




    public static final String SQL_CREATE_TABLA_CAJAS_ENTRADA =
            "CREATE TABLE " + tablacajasentrada + "(" +
                    cajas_entrada_id + " TEXT PRIMARY KEY," +
                    cajas_entrada_cod_barra + " TEXT," +
                    cajas_entrada_idsede + " INTEGER," +
                    cajas_entrada_nomsede + " TEXT," +
                    cajas_entrada_idlocal + " INTEGER," +
                    cajas_entrada_nomlocal + " TEXT," +
                    cajas_entrada_tipo + " INTEGER," +
                    cajas_entrada_acl + " INTEGER," +
                    cajas_entrada_fecha_reg_dia + " INTEGER," +
                    cajas_entrada_fecha_reg_mes + " INTEGER," +
                    cajas_entrada_fecha_reg_anio + " INTEGER," +
                    cajas_entrada_fecha_reg_hora + " INTEGER," +
                    cajas_entrada_fecha_reg_min + " INTEGER," +
                    cajas_entrada_fecha_reg_seg + " INTEGER," +
                    cajas_entrada_check_reg + " INTEGER," +
                    cajas_entrada_nlado + " INTEGER," +
                    cajas_entrada_estado + " INTEGER" + ");"
            ;

    public static final String SQL_CREATE_TABLA_CAJAS_SALIDA =
            "CREATE TABLE " + tablacajassalida + "(" +
                    cajas_salida_id + " TEXT PRIMARY KEY," +
                    cajas_salida_cod_barra + " TEXT," +
                    cajas_salida_idsede + " INTEGER," +
                    cajas_salida_nomsede + " TEXT," +
                    cajas_salida_idlocal + " INTEGER," +
                    cajas_salida_nomlocal + " TEXT," +
                    cajas_salida_tipo + " INTEGER," +
                    cajas_salida_acl + " INTEGER," +
                    cajas_salida_fecha_reg_dia + " INTEGER," +
                    cajas_salida_fecha_reg_mes + " INTEGER," +
                    cajas_salida_fecha_reg_anio + " INTEGER," +
                    cajas_salida_fecha_reg_hora + " INTEGER," +
                    cajas_salida_fecha_reg_min + " INTEGER," +
                    cajas_salida_fecha_reg_seg + " INTEGER," +
                    cajas_salida_check_reg + " INTEGER," +
                    cajas_salida_nlado + " INTEGER," +
                    cajas_salida_estado + " INTEGER" + ");"
            ;

    public static final String SQL_CREATE_TABLA_ASISTENCIA_AULA =
            "CREATE TABLE " + tablaasistenciaaula + "(" +
                    asis_aula_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    asis_aula_dni + " TEXT," +
                    asis_aula_idnacional + " TEXT," +
                    asis_aula_ccdd + " TEXT," +
                    asis_aula_idsede + " TEXT," +
                    asis_aula_sede + " TEXT," +
                    asis_aula_idlocal + " INTEGER," +
                    asis_aula_local + " TEXT," +
                    asis_aula_direccion + " TEXT," +
                    asis_aula_nombres + " TEXT," +
                    asis_aula_ape_paterno + " TEXT," +
                    asis_aula_ape_materno + " TEXT," +
                    asis_aula_naula + " INTEGER," +
                    asis_aula_discapacidad+ " TEXT," +
                    asis_aula_prioridad + " TEXT," +
                    asis_aula_fecha_dia + " INTEGER," +
                    asis_aula_fecha_mes + " INTEGER," +
                    asis_aula_fecha_anio + " INTEGER," +
                    asis_aula_fecha_hora + " INTEGER," +
                    asis_aula_fecha_min + " INTEGER," +
                    asis_aula_fecha_seg + " INTEGER," +
                    asis_aula_estado + " INTEGER" + ");"
            ;

    public static final String SQL_CREATE_TABLA_ASISTENCIA_LOCAL =
            "CREATE TABLE " + tablaasistencialocal + "(" +
                    asis_local_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    asis_local_dni + " TEXT," +
                    asis_local_idnacional + " TEXT," +
                    asis_local_ccdd + " TEXT," +
                    asis_local_idsede + " TEXT," +
                    asis_local_sede + " TEXT," +
                    asis_local_idlocal + " INTEGER," +
                    asis_local_local + " TEXT," +
                    asis_local_direccion + " TEXT," +
                    asis_local_nombres + " TEXT," +
                    asis_local_ape_paterno + " TEXT," +
                    asis_local_ape_materno + " TEXT," +
                    asis_local_naula + " INTEGER," +
                    asis_local_discapacidad+ " TEXT," +
                    asis_local_prioridad + " TEXT," +
                    asis_local_fecha_dia + " INTEGER," +
                    asis_local_fecha_mes + " INTEGER," +
                    asis_local_fecha_anio + " INTEGER," +
                    asis_local_fecha_hora + " INTEGER," +
                    asis_local_fecha_min + " INTEGER," +
                    asis_local_fecha_seg + " INTEGER," +
                    asis_local_estado + " INTEGER" + ");"
            ;

    public static final String WHERE_CLAUSE_ID = "_id=?";
    public static final String WHERE_CLAUSE_CLAVE = "clave=?";
    public static final String WHERE_CLAUSE_NRO_LOCAL = "nro_local=?";
    public static final String WHERE_CLAUSE_COD_BARRA = "cod_barra_caja=?";
    public static final String WHERE_CLAUSE_ID_LOCAL = "idlocal=?";
    public static final String WHERE_CLAUSE_TIPO_CAJA = "tipo=?";
    public static final String WHERE_CLAUSE_NRO_LADO = "nlado=?";
    public static final String WHERE_CLAUSE_ESTADO = "estado=?";
    public static final String WHERE_CLAUSE_DNI = "dni=?";
    public static final String WHERE_CLAUSE_NRO_AULA = "naula=?";








    public static final String WHERE_CLAUSE_LOCAL = "local=?";
    public static final String WHERE_CLAUSE_ID_AULA = "id_aula=?";
    public static final String WHERE_CLAUSE_CODIGO_FICHA = "codficha=?";
    public static final String WHERE_CLAUSE_CODIGO_CUADERNILLO = "codcartilla=?";
    public static final String WHERE_CLAUSE_CODIGO_PAGINA = "codigo_pagina=?";
    public static final String WHERE_CLAUSE_NOMBRE_AULA = "nombre=?";
    public static final String WHERE_CLAUSE_CODIGO = "codigo=?";
    public static final String WHERE_CLAUSE_SEDE = "sede=?";
    public static final String WHERE_CLAUSE_DIA = "dia=?";
    public static final String WHERE_CLAUSE_MES = "mes=?";
    public static final String WHERE_CLAUSE_ANIO = "anio=?";
    public static final String WHERE_CLAUSE_ESTADO_ENTRADA = "estado_entrada=?";
    public static final String WHERE_CLAUSE_ESTADO_SALIDA = "estado_salida=?";


    //DELETE
    public static final String SQL_DELETE_CAJAS_ENTRADA = "DROP TABLE " + tablacajasentrada;
    public static final String SQL_DELETE_CAJAS_SALIDA = "DROP TABLE " + tablacajassalida;











//--------------------------------------------------------------------





    public static String tablaaulalocal = "aulas_local";


    public static String tablaresumenasistencia = "resumen_asistencia";
    public static String tablaresumeninventario = "resumen_inventario";
    public static String tablaresumentotal = "resumen_total";





    //TABLA NACIONAL
    public static String nacional_id = "_id";
    public static String nacional_sede = "sede";
    public static String nacional_nro_local = "nro_local";
    public static String nacional_local_aplicacion = "local_aplicacion";
    public static String nacional_aula = "aula";
    public static String nacional_codigo_pagina = "codigo_pagina";
    public static String nacional_ins_numdoc = "ins_numdoc";
    public static String nacional_apepat = "apepat";
    public static String nacional_apemat = "apemat";
    public static String nacional_nombres = "nombres";
    public static String nacional_estatus = "estatus";
    public static String nacional_s_aula = "s_aula";
    public static String nacional_s_ficha = "s_ficha";
    public static String nacional_s_cartilla = "s_cartilla";
    public static String nacional_s_listaasistencia = "s_listaasistencia";
    public static String nacional_id_local = "id_local";
    public static String nacional_id_aula = "id_aula";
    public static String nacional_direccion = "direccion";
    public static String nacional_codficha = "codficha";
    public static String nacional_codcartilla = "codcartilla";
    public static String nacional_new_aula = "new_aula";
    public static String nacional_new_local = "new_local";
    public static String nacional_tipo = "tipo";
    public static String nacional_discapacidad = "discapacidad";
    public static String nacional_version = "version";
    public static String nacional_tipo_concurso = "tipo_concurso";
    public static String nacional_estatus2 = "estatus2";
    public static String nacional_estatus3 = "estatus3";
    public static String nacional_new_aula_ficha = "new_aula_ficha";
    public static String nacional_new_aula_cartilla = "new_aula_cartilla";






    //TABLA FICHAS
    public static String ficha_id = "_id";
    public static String ficha_codficha = "codficha";
    public static String ficha_dni = "dni";
    public static String ficha_nombres = "nombres";
    public static String ficha_apepat = "apepat";
    public static String ficha_apemat = "apemat";
    public static String ficha_sede = "sede";
    public static String ficha_id_local = "id_local";
    public static String ficha_nombre_local = "local";
    public static String ficha_aula = "aula";
    public static String ficha_dia = "dia";
    public static String ficha_mes = "mes";
    public static String ficha_anio = "anio";
    public static String ficha_hora = "hora";
    public static String ficha_minuto = "minuto";
    public static String ficha_subido = "subido";

    //TABLA CUADERNILLOS
    public static String cuadernillo_id = "_id";
    public static String cuadernillo_codcartilla = "codcartilla";
    public static String cuadernillo_dni = "dni";
    public static String cuadernillo_nombres = "nombres";
    public static String cuadernillo_apepat = "apepat";
    public static String cuadernillo_apemat = "apemat";
    public static String cuadernillo_sede = "sede";
    public static String cuadernillo_id_local = "id_local";
    public static String cuadernillo_nombre_local = "local";
    public static String cuadernillo_aula = "aula";
    public static String cuadernillo_dia = "dia";
    public static String cuadernillo_mes = "mes";
    public static String cuadernillo_anio = "anio";
    public static String cuadernillo_hora = "hora";
    public static String cuadernillo_minuto = "minuto";
    public static String cuadernillo_subido = "subido";

    //TABLA LISTAS
    public static String listado_id = "_id";
    public static String listado_codigo_pagina = "codigo_pagina";
    public static String listado_sede = "sede";
    public static String listado_id_local = "id_local";
    public static String listado_nombre_local = "local";
    public static String listado_aula = "aula";
    public static String listado_nro_postulantes = "nro_postulantes";
    public static String listado_dia = "dia";
    public static String listado_mes = "mes";
    public static String listado_anio = "anio";
    public static String listado_hora = "hora";
    public static String listado_minuto = "minuto";
    public static String listado_subido = "subido";

    //TABLA RESUMEN ASISTENCIA
    public static String resumen_asistencia_id = "_id";
    public static String resumen_asistencia_id_local = "id_local";
    public static String resumen_asistencia_id_aula = "id_aula";
    public static String resumen_asistencia_nro_asislocal = "nro_asislocal";
    public static String resumen_asistencia_nro_asisaula = "nro_asisaula";

    //TABLA RESUMEN INVENTARIO
    public static String resumen_inventario_id = "_id";
    public static String resumen_inventario_id_local = "id_local";
    public static String resumen_inventario_id_aula = "id_aula";
    public static String resumen_inventario_nro_invfichas = "nro_invfichas";
    public static String resumen_inventario_nro_invcartillas = "nro_invcartillas";
    public static String resumen_inventario_nro_invlistados = "nro_invlistados";

    //TABLA RESUMEN TOTALES
    public static String resumen_total_id = "_id";
    public static String resumen_total_cantidad = "cantidad";


    public static final String SQL_CREATE_TABLA_FICHAS =
            "CREATE TABLE " + tablafichas + "(" +
                    ficha_id + " TEXT PRIMARY KEY," +
                    ficha_codficha + " TEXT," +
                    ficha_dni + " TEXT," +
                    ficha_nombres + " TEXT," +
                    ficha_apepat + " TEXT," +
                    ficha_apemat + " TEXT," +
                    ficha_sede + " TEXT," +
                    ficha_id_local + " TEXT," +
                    ficha_nombre_local + " TEXT," +
                    ficha_aula + " TEXT," +
                    ficha_dia + " INTEGER," +
                    ficha_mes + " INTEGER," +
                    ficha_anio + " INTEGER," +
                    ficha_hora + " INTEGER," +
                    ficha_minuto + " INTEGER," +
                    ficha_subido + " INTEGER" + ");"
            ;

    public static final String SQL_CREATE_TABLA_CUADERNILLOS =
            "CREATE TABLE " + tablacuadernillos + "(" +
                    cuadernillo_id + " TEXT PRIMARY KEY," +
                    cuadernillo_codcartilla + " TEXT," +
                    cuadernillo_dni + " TEXT," +
                    cuadernillo_nombres + " TEXT," +
                    cuadernillo_apepat + " TEXT," +
                    cuadernillo_apemat + " TEXT," +
                    cuadernillo_sede + " TEXT," +
                    cuadernillo_id_local + " TEXT," +
                    cuadernillo_nombre_local + " TEXT," +
                    cuadernillo_aula + " TEXT," +
                    cuadernillo_dia + " INTEGER," +
                    cuadernillo_mes + " INTEGER," +
                    cuadernillo_anio + " INTEGER," +
                    cuadernillo_hora + " INTEGER," +
                    cuadernillo_minuto + " INTEGER," +
                    cuadernillo_subido + " INTEGER" + ");"
            ;

    public static final String SQL_CREATE_TABLA_LISTADOS =
            "CREATE TABLE " + tablalistados + "(" +
                    listado_id + " TEXT PRIMARY KEY," +
                    listado_codigo_pagina + " TEXT," +
                    listado_sede + " TEXT," +
                    listado_id_local + " TEXT," +
                    listado_nombre_local + " TEXT," +
                    listado_aula + " TEXT," +
                    listado_nro_postulantes + " INTEGER," +
                    listado_dia + " INTEGER," +
                    listado_mes + " INTEGER," +
                    listado_anio + " INTEGER," +
                    listado_hora + " INTEGER," +
                    listado_minuto + " INTEGER," +
                    listado_subido + " INTEGER" + ");"
            ;

    public static final String SQL_CREATE_TABLA_RESUMEN_ASISTENCIA =
            "CREATE TABLE " + tablaresumenasistencia + "(" +
                    resumen_asistencia_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    resumen_asistencia_id_local + " INTEGER," +
                    resumen_asistencia_id_aula + " INTEGER," +
                    resumen_asistencia_nro_asislocal + " INTEGER," +
                    resumen_asistencia_nro_asisaula + " INTEGER" + ");"
            ;

    public static final String SQL_CREATE_TABLA_RESUMEN_INVENTARIO =
            "CREATE TABLE " + tablaresumeninventario + "(" +
                    resumen_inventario_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    resumen_inventario_id_aula + " INTEGER," +
                    resumen_inventario_id_local + " INTEGER," +
                    resumen_inventario_nro_invfichas + " INTEGER," +
                    resumen_inventario_nro_invcartillas + " INTEGER," +
                    resumen_inventario_nro_invlistados + " INTEGER" + ");"
            ;

    public static final String SQL_CREATE_TABLA_RESUMEN_TOTAL =
            "CREATE TABLE " + tablaresumentotal + "(" +
                    resumen_total_id + " INTEGER PRIMARY KEY," +
                    resumen_total_cantidad + " INTEGER" + ");"
            ;





//    public static final String[] COLUMNAS_NACIONAL = {
//            nacional_codigo,nacional_apepat,nacional_aplicacion,
//            nacional_aula, nacional_discapacidad, nacional_sede
//    };



}
