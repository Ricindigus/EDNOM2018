package pe.com.ricindigus.appednom2018.modelo;

public class SQLConstantes {
    public static String DB_PATH = "/data/data/pe.com.ricindigus.appednom2018/databases/";
    public static String DB_NAME = "mydatabase.sqlite";

    public static String tablausuariolocal = "usuario_local";
    public static String tablausuarioactual = "usuario_actual";
    public static String tablacajas = "cajas";
    public static String tablaasistencia = "asistencia";
    public static String tablaaulas = "aulas";
    public static String tablacajasentrada = "cajas_entrada";
    public static String tablacajassalida = "cajas_salida";
    public static String tablaasistenciaaula = "asistencia_aula";
    public static String tablaasistencialocal = "asistencia_local";
    public static String tablamaterial = "inventario";
    public static String tablafichas = "fichas";
    public static String tablacuadernillos= "cuadernillos";
    public static String tablalistados = "listados";


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

    //TABLA USUARIO ACTUAL
    public static String usuario_actual_id = "_id";
    public static String usuario_actual_usuario = "usuario";
    public static String usuario_actual_clave = "clave";
    public static String usuario_actual_rol = "rol";
    public static String usuario_actual_nro_local = "nro_local";
    public static String usuario_actual_nombreLocal = "nombreLocal";
    public static String usuario_actual_naulas = "naulas";
    public static String usuario_actual_ncontingencia = "ncontingencia";
    public static String usuario_actual_codsede = "codsede";
    public static String usuario_actual_sede = "sede";
    public static String usuario_actual_nombre = "nombre";


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


    //TABLA MATERIAL
    public static String material_id = "_id";
    public static String material_codigo = "codigo";
    public static String material_tipo = "tipo";
    public static String material_idnacional = "idnacional";
    public static String material_ccdd = "ccdd";
    public static String material_idsede = "idsede";
    public static String material_sede = "sede";
    public static String material_idlocal = "idlocal";
    public static String material_local = "local";
    public static String material_dni = "dni";
    public static String material_nombres = "nombres";
    public static String material_ape_paterno = "ape_paterno";
    public static String material_ape_materno = "ape_materno";
    public static String material_naula = "naula";
    public static String material_codpagina = "codpagina";


    //TABLA FICHA
    public static String ficha_id = "_id";
    public static String ficha_codigo = "codigo";
    public static String ficha_tipo = "tipo";
    public static String ficha_idnacional = "idnacional";
    public static String ficha_ccdd = "ccdd";
    public static String ficha_idsede = "idsede";
    public static String ficha_sede = "sede";
    public static String ficha_idlocal = "idlocal";
    public static String ficha_local = "local";
    public static String ficha_dni = "dni";
    public static String ficha_nombres = "nombres";
    public static String ficha_ape_paterno = "ape_paterno";
    public static String ficha_ape_materno = "ape_materno";
    public static String ficha_naula = "naula";
    public static String ficha_codpagina = "codpagina";
    public static String ficha_dia = "dia";
    public static String ficha_mes = "mes";
    public static String ficha_anio = "anio";
    public static String ficha_hora = "hora";
    public static String ficha_min = "min";
    public static String ficha_seg = "seg";
    public static String ficha_estado = "estado";



    //TABLA CUADERNILLO
    public static String cuadernillo_id = "_id";
    public static String cuadernillo_codigo = "codigo";
    public static String cuadernillo_tipo = "tipo";
    public static String cuadernillo_idnacional = "idnacional";
    public static String cuadernillo_ccdd = "ccdd";
    public static String cuadernillo_idsede = "idsede";
    public static String cuadernillo_sede = "sede";
    public static String cuadernillo_idlocal = "idlocal";
    public static String cuadernillo_local = "local";
    public static String cuadernillo_dni = "dni";
    public static String cuadernillo_nombres = "nombres";
    public static String cuadernillo_ape_paterno = "ape_paterno";
    public static String cuadernillo_ape_materno = "ape_materno";
    public static String cuadernillo_naula = "naula";
    public static String cuadernillo_codpagina = "codpagina";
    public static String cuadernillo_dia = "dia";
    public static String cuadernillo_mes = "mes";
    public static String cuadernillo_anio = "anio";
    public static String cuadernillo_hora = "hora";
    public static String cuadernillo_min = "min";
    public static String cuadernillo_seg = "seg";
    public static String cuadernillo_estado = "estado";

    //TABLA LISTADO
    public static String listado_id = "_id";
    public static String listado_codigo = "codigo";
    public static String listado_tipo = "tipo";
    public static String listado_idnacional = "idnacional";
    public static String listado_ccdd = "ccdd";
    public static String listado_idsede = "idsede";
    public static String listado_sede = "sede";
    public static String listado_idlocal = "idlocal";
    public static String listado_local = "local";
    public static String listado_naula = "naula";
    public static String listado_npostulantes = "npostulantes";
    public static String listado_dia = "dia";
    public static String listado_mes = "mes";
    public static String listado_anio = "anio";
    public static String listado_hora = "hora";
    public static String listado_min = "min";
    public static String listado_seg = "seg";
    public static String listado_estado = "estado";



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

    //TABLA RESUMEN CAJAS TOTALES
    public static String resumen_total_id = "_id";
    public static String resumen_total_cantidad = "cantidad";

    public static final String SQL_CREATE_TABLA_USUARIO_ACTUAL =
            "CREATE TABLE " + tablausuarioactual + "(" +
                    usuario_actual_id + " INTEGER PRIMARY KEY," +
                    usuario_actual_usuario + " TEXT," +
                    usuario_actual_clave + " TEXT," +
                    usuario_actual_rol + " INTEGER," +
                    usuario_actual_nro_local + " INTEGER," +
                    usuario_actual_nombreLocal + " TEXT," +
                    usuario_actual_naulas + " INTEGER," +
                    usuario_actual_ncontingencia + " INTEGER," +
                    usuario_actual_codsede + " INTEGER," +
                    usuario_actual_sede + " TEXT," +
                    usuario_actual_nombre + " TEXT" + ");"
            ;

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

    public static final String SQL_CREATE_TABLA_FICHAS=
            "CREATE TABLE " + tablafichas + "(" +
                    ficha_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ficha_codigo + " TEXT," +
                    ficha_tipo + " INTEGER," +
                    ficha_idnacional + " INTEGER," +
                    ficha_ccdd + " TEXT," +
                    ficha_idsede + " TEXT," +
                    ficha_sede + " TEXT," +
                    ficha_idlocal + " INTEGER," +
                    ficha_local + " TEXT," +
                    ficha_dni + " TEXT," +
                    ficha_nombres + " TEXT," +
                    ficha_ape_paterno + " TEXT," +
                    ficha_ape_materno + " TEXT," +
                    ficha_naula + " INTEGER," +
                    ficha_codpagina + " TEXT," +
                    ficha_dia + " INTEGER," +
                    ficha_mes + " INTEGER," +
                    ficha_anio + " INTEGER," +
                    ficha_hora + " INTEGER," +
                    ficha_min + " INTEGER," +
                    ficha_seg + " INTEGER," +
                    ficha_estado + " INTEGER" + ");"
            ;
    public static final String SQL_CREATE_TABLA_CUADERNILLOS=
            "CREATE TABLE " + tablacuadernillos + "(" +
                    cuadernillo_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    cuadernillo_codigo + " TEXT," +
                    cuadernillo_tipo + " INTEGER," +
                    cuadernillo_idnacional + " INTEGER," +
                    cuadernillo_ccdd + " TEXT," +
                    cuadernillo_idsede + " TEXT," +
                    cuadernillo_sede + " TEXT," +
                    cuadernillo_idlocal + " INTEGER," +
                    cuadernillo_local + " TEXT," +
                    cuadernillo_dni + " TEXT," +
                    cuadernillo_nombres + " TEXT," +
                    cuadernillo_ape_paterno + " TEXT," +
                    cuadernillo_ape_materno + " TEXT," +
                    cuadernillo_naula + " INTEGER," +
                    cuadernillo_codpagina + " TEXT," +
                    cuadernillo_dia + " INTEGER," +
                    cuadernillo_mes + " INTEGER," +
                    cuadernillo_anio + " INTEGER," +
                    cuadernillo_hora + " INTEGER," +
                    cuadernillo_min + " INTEGER," +
                    cuadernillo_seg + " INTEGER," +
                    cuadernillo_estado + " INTEGER" + ");"
            ;
    public static final String SQL_CREATE_TABLA_LISTADOS=
            "CREATE TABLE " + tablalistados + "(" +
                    listado_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    listado_codigo + " TEXT," +
                    listado_tipo + " INTEGER," +
                    listado_idnacional + " INTEGER," +
                    listado_ccdd + " TEXT," +
                    listado_idsede + " TEXT," +
                    listado_sede + " TEXT," +
                    listado_idlocal + " INTEGER," +
                    listado_local + " TEXT," +
                    listado_naula + " INTEGER," +
                    listado_npostulantes + " INTEGER," +
                    listado_dia + " INTEGER," +
                    listado_mes + " INTEGER," +
                    listado_anio + " INTEGER," +
                    listado_hora + " INTEGER," +
                    listado_min + " INTEGER," +
                    listado_seg + " INTEGER," +
                    listado_estado + " INTEGER" + ");"
            ;

    public static final String WHERE_CLAUSE_ID = "_id=?";
    public static final String WHERE_CLAUSE_CLAVE = "clave=?";
    public static final String WHERE_CLAUSE_NRO_LOCAL = "nro_local=?";
    public static final String WHERE_CLAUSE_COD_BARRA = "cod_barra_caja=?";
    public static final String WHERE_CLAUSE_ID_LOCAL = "idlocal=?";
    public static final String WHERE_CLAUSE_TIPO_CAJA = "tipo=?";
    public static final String WHERE_CLAUSE_TIPO_MATERIAL = "tipo=?";
    public static final String WHERE_CLAUSE_NRO_LADO = "nlado=?";
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
    public static final String WHERE_CLAUSE_ESTADO_ENTRADA = "estado_entrada=?";
    public static final String WHERE_CLAUSE_ESTADO_SALIDA = "estado_salida=?";


    //DELETE
    public static final String SQL_DELETE_CAJAS_ENTRADA = "DROP TABLE " + tablacajasentrada;
    public static final String SQL_DELETE_CAJAS_SALIDA = "DROP TABLE " + tablacajassalida;











//--------------------------------------------------------------------


    public static String tablaresumenasistencia = "resumen_asistencia";
    public static String tablaresumeninventario = "resumen_inventario";
    public static String tablaresumentotal = "resumen_total";

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
