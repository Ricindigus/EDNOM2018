package pe.com.ricindigus.appednom2018.modelo;

public class SQLConstantes {
    public static String DB_PATH = "/data/data/pe.com.ricindigus.appednom2018/databases/";
    public static String DB_NAME = "mydatabase.sqlite";

    public static String tablanacional = "nacional";
    public static String tablausuariolocal = "usuario_local";
    public static String tablaaulalocal = "aulas_local";
    public static String tablaasislocal = "asis_local";
    public static String tablaasisaula = "asis_aula";

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

    public static final String WHERE_CLAUSE_CLAVE = "clave=?";
    public static final String WHERE_CLAUSE_DNI = "ins_numdoc=?";
    public static final String WHERE_CLAUSE_NRO_LOCAL = "nro_local=?";
    public static final String WHERE_CLAUSE_LOCAL = "local=?";
    public static final String WHERE_CLAUSE_ID_LOCAL = "id_local=?";
    public static final String WHERE_CLAUSE_NRO_AULA = "aula=?";
    public static final String WHERE_CLAUSE_DNI_ASISTENCIA = "dni=?";
    public static final String WHERE_CLAUSE_NOMBRE_AULA = "nombre=?";


    public static final String WHERE_CLAUSE_CODIGO = "codigo=?";


    public static final String WHERE_CLAUSE_SEDE = "sede=?";
    public static final String WHERE_CLAUSE_DIA = "dia=?";
    public static final String WHERE_CLAUSE_MES = "mes=?";
    public static final String WHERE_CLAUSE_ANIO = "anio=?";


    public static final String WHERE_CLAUSE_SUBIDO_ENTRADA = "subido_entrada=?";
    public static final String WHERE_CLAUSE_SUBIDO_SALIDA = "subido_salida=?";



//    public static final String[] COLUMNAS_NACIONAL = {
//            nacional_codigo,nacional_apepat,nacional_aplicacion,
//            nacional_aula, nacional_discapacidad, nacional_sede
//    };



}
