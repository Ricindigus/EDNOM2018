package pe.com.ricindigus.appednom2018.modelo;

public class SQLConstantes {
    public static String DB_PATH = "/data/data/pe.com.ricindigus.appednom2018/databases/";
    public static String DB_NAME = "mydatabase.sqlite";

    public static String tablausuariolocal = "usuario_local";
    public static String tablacajas = "cajas";
    public static String tablacajasentrada = "cajas_entrada";
    public static String tablacajassalida = "cajas_salida";




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
    public static String cajas_acl = "acl";


    //TABLA CAJAS ENTRADA
    public static String cajas_entrada_id = "_id";
    public static String cajas_entrada_cod_barra = "cod_barra_caja";
    public static String cajas_entrada_idsede = "idsede";
    public static String cajas_entrada_nomsede = "sede";
    public static String cajas_entrada_idlocal = "idlocal";
    public static String cajas_entrada_nomlocal = "local";
    public static String cajas_entrada_acl = "acl";
    public static String cajas_entrada_fecha_reg_dia = "dia";
    public static String cajas_entrada_fecha_reg_mes = "mes";
    public static String cajas_entrada_fecha_reg_anio = "anio";
    public static String cajas_entrada_fecha_reg_hora = "hora";
    public static String cajas_entrada_fecha_reg_min = "min";
    public static String cajas_entrada_fecha_reg_seg = "seg";
    public static String cajas_entrada_subido = "subido";


    //TABLA CAJAS SALIDA
    public static String cajas_salida_id = "_id";
    public static String cajas_salida_cod_barra = "cod_barra_caja";
    public static String cajas_salida_idsede = "idsede";
    public static String cajas_salida_nomsede = "sede";
    public static String cajas_salida_idlocal = "idlocal";
    public static String cajas_salida_nomlocal = "local";
    public static String cajas_salida_acl = "acl";
    public static String cajas_salida_fecha_reg_dia = "dia";
    public static String cajas_salida_fecha_reg_mes = "mes";
    public static String cajas_salida_fecha_reg_anio = "anio";
    public static String cajas_salida_fecha_reg_hora = "hora";
    public static String cajas_salida_fecha_reg_min = "min";
    public static String cajas_salida_fecha_reg_seg = "seg";
    public static String cajas_salida_subido = "subido";


    public static final String SQL_CREATE_TABLA_CAJAS_ENTRADA =
            "CREATE TABLE " + tablacajasentrada + "(" +
                    cajas_entrada_id + " TEXT PRIMARY KEY," +
                    cajas_entrada_cod_barra + " TEXT," +
                    cajas_entrada_idsede + " INTEGER," +
                    cajas_entrada_nomsede + " TEXT," +
                    cajas_entrada_idlocal + " INTEGER," +
                    cajas_entrada_nomlocal + " TEXT," +
                    cajas_entrada_acl + " INTEGER," +
                    cajas_entrada_fecha_reg_dia + " INTEGER," +
                    cajas_entrada_fecha_reg_mes + " INTEGER," +
                    cajas_entrada_fecha_reg_anio + " INTEGER," +
                    cajas_entrada_fecha_reg_hora + " INTEGER," +
                    cajas_entrada_fecha_reg_min + " INTEGER," +
                    cajas_entrada_fecha_reg_seg + " INTEGER," +
                    cajas_entrada_subido + " INTEGER" + ");"
            ;

    public static final String SQL_CREATE_TABLA_CAJAS_SALIDA =
            "CREATE TABLE " + tablacajassalida + "(" +
                    cajas_salida_id + " TEXT PRIMARY KEY," +
                    cajas_salida_cod_barra + " TEXT," +
                    cajas_salida_idsede + " INTEGER," +
                    cajas_salida_nomsede + " TEXT," +
                    cajas_salida_idlocal + " INTEGER," +
                    cajas_salida_nomlocal + " TEXT," +
                    cajas_salida_acl + " INTEGER," +
                    cajas_salida_fecha_reg_dia + " INTEGER," +
                    cajas_salida_fecha_reg_mes + " INTEGER," +
                    cajas_salida_fecha_reg_anio + " INTEGER," +
                    cajas_salida_fecha_reg_hora + " INTEGER," +
                    cajas_salida_fecha_reg_min + " INTEGER," +
                    cajas_salida_fecha_reg_seg + " INTEGER," +
                    cajas_salida_subido + " INTEGER" + ");"
            ;

    public static final String WHERE_CLAUSE_ID = "_id=?";
    public static final String WHERE_CLAUSE_CLAVE = "clave=?";
    public static final String WHERE_CLAUSE_COD_BARRA = "cod_barra_caja=?";
    public static final String WHERE_CLAUSE_DNI = "ins_numdoc=?";
    public static final String WHERE_CLAUSE_NRO_LOCAL = "nro_local=?";
    public static final String WHERE_CLAUSE_LOCAL = "local=?";
    public static final String WHERE_CLAUSE_ID_LOCAL = "id_local=?";
    public static final String WHERE_CLAUSE_ID_AULA = "id_aula=?";
    public static final String WHERE_CLAUSE_NRO_AULA = "aula=?";
    public static final String WHERE_CLAUSE_DNI_ASISTENCIA = "dni=?";
    public static final String WHERE_CLAUSE_CODIGO_FICHA = "codficha=?";
    public static final String WHERE_CLAUSE_CODIGO_CUADERNILLO = "codcartilla=?";
    public static final String WHERE_CLAUSE_CODIGO_PAGINA = "codigo_pagina=?";
    public static final String WHERE_CLAUSE_NOMBRE_AULA = "nombre=?";
    public static final String WHERE_CLAUSE_CODIGO = "codigo=?";
    public static final String WHERE_CLAUSE_SEDE = "sede=?";
    public static final String WHERE_CLAUSE_DIA = "dia=?";
    public static final String WHERE_CLAUSE_MES = "mes=?";
    public static final String WHERE_CLAUSE_ANIO = "anio=?";
    public static final String WHERE_CLAUSE_SUBIDO_ENTRADA = "subido_entrada=?";
    public static final String WHERE_CLAUSE_SUBIDO_SALIDA = "subido_salida=?";


















    public static String tablanacional = "nacional";
    public static String tablaaulalocal = "aulas_local";
    public static String tablaasislocal = "asis_local";
    public static String tablaasisaula = "asis_aula";
    public static String tablafichas = "fichas";
    public static String tablacuadernillos = "cuadernillos";
    public static String tablalistados = "listados";
    public static String tablaresumenasistencia = "resumen_asistencia";
    public static String tablaresumeninventario = "resumen_inventario";
    public static String tablaresumentotal = "resumen_total";



    //TABLA AULA
    public static String aulas_id = "_id";
    public static String aulas_codigo = "codigo";
    public static String aulas_nro_local = "nro_local";
    public static String aulas_naula = "naula";
    public static String aulas_tipo = "tipo";
    public static String aulas_nombre = "nombre";

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




    //TABLA ASISTENCIA LOCAL
    public static String asistencia_local_id = "_id";
    public static String asistencia_local_dni = "dni";
    public static String asistencia_local_nombres = "nombres";
    public static String asistencia_local_apepat = "apepat";
    public static String asistencia_local_apemat = "apemat";
    public static String asistencia_local_sede = "sede";
    public static String asistencia_local_id_local = "id_local";
    public static String asistencia_local_nombre_local = "local";
    public static String asistencia_local_aula = "aula";
    public static String asistencia_local_local_dia = "local_dia";
    public static String asistencia_local_local_mes = "local_mes";
    public static String asistencia_local_local_anio = "local_anio";
    public static String asistencia_local_local_hora = "local_hora";
    public static String asistencia_local_local_minuto = "local_minuto";
    public static String asistencia_local_subido_local = "subido_local";

    //TABLA ASISTENCIA AULA
    public static String asistencia_aula_id = "_id";
    public static String asistencia_aula_dni = "dni";
    public static String asistencia_aula_nombres = "nombres";
    public static String asistencia_aula_apepat = "apepat";
    public static String asistencia_aula_apemat = "apemat";
    public static String asistencia_aula_sede = "sede";
    public static String asistencia_aula_id_local = "id_local";
    public static String asistencia_aula_nombre_local = "local";
    public static String asistencia_aula_aula = "aula";
    public static String asistencia_aula_aula_dia = "aula_dia";
    public static String asistencia_aula_aula_mes = "aula_mes";
    public static String asistencia_aula_aula_anio = "aula_anio";
    public static String asistencia_aula_aula_hora = "aula_hora";
    public static String asistencia_aula_aula_minuto = "aula_minuto";
    public static String asistencia_aula_subido_aula = "subido_aula";

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



    public static final String SQL_CREATE_TABLA_ASISTENCIA_AULA =
            "CREATE TABLE " + tablaasisaula + "(" +
                    asistencia_aula_id + " TEXT PRIMARY KEY," +
                    asistencia_aula_dni + " TEXT," +
                    asistencia_aula_nombres + " TEXT," +
                    asistencia_aula_apepat + " TEXT," +
                    asistencia_aula_apemat + " TEXT," +
                    asistencia_aula_sede + " TEXT," +
                    asistencia_aula_id_local + " TEXT," +
                    asistencia_aula_nombre_local + " TEXT," +
                    asistencia_aula_aula + " TEXT," +
                    asistencia_aula_aula_dia + " INTEGER," +
                    asistencia_aula_aula_mes + " INTEGER," +
                    asistencia_aula_aula_anio + " INTEGER," +
                    asistencia_aula_aula_hora + " INTEGER," +
                    asistencia_aula_aula_minuto + " INTEGER," +
                    asistencia_aula_subido_aula + " INTEGER" + ");"
            ;


    public static final String SQL_CREATE_TABLA_ASISTENCIA_LOCAL =
            "CREATE TABLE " + tablaasislocal + "(" +
                    asistencia_local_id + " TEXT PRIMARY KEY," +
                    asistencia_local_dni + " TEXT," +
                    asistencia_local_nombres + " TEXT," +
                    asistencia_local_apepat + " TEXT," +
                    asistencia_local_apemat + " TEXT," +
                    asistencia_local_sede + " TEXT," +
                    asistencia_local_id_local + " TEXT," +
                    asistencia_local_nombre_local + " TEXT," +
                    asistencia_local_aula + " TEXT," +
                    asistencia_local_local_dia + " INTEGER," +
                    asistencia_local_local_mes + " INTEGER," +
                    asistencia_local_local_anio + " INTEGER," +
                    asistencia_local_local_hora+ " INTEGER," +
                    asistencia_local_local_minuto + " INTEGER," +
                    asistencia_local_subido_local + " INTEGER" + ");"
            ;

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
