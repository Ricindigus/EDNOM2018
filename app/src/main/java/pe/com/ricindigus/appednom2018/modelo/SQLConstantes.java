package pe.com.ricindigus.appednom2018.modelo;

public class SQLConstantes {
    public static String DB_PATH = "/data/data/pe.com.ricindigus.appednom2018/databases/";
    public static String DB_NAME = "mydatabase.sqlite";

    public static String tablanacional = "nacional";
    public static String tablausuariolocal = "usuario_local";
    public static String tablaaulalocal = "aula_local";
    public static String tablaregistro = "fecha_registro";




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
    public static String nacional_discapacidad = "discapacidad ";
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


    //TABLA REGISTRO
    public static String registro_id = "_id";
    public static String registro_codigo = "codigo";
    public static String registro_nombres = "nombres";
    public static String registro_sede = "sede";
    public static String registro_aula = "aula";
    public static String registro_dia = "dia";
    public static String registro_mes = "mes";
    public static String registro_anio = "anio";
    public static String registro_hora_entrada = "hora_entrada";
    public static String registro_minuto_entrada = "minuto_entrada";
    public static String registro_hora_salida = "hora_salida";
    public static String registro_minuto_salida = "minuto_salida";
    public static String registro_subido_entrada = "subido_entrada";
    public static String registro_subido_salida = "subido_salida";


    public static final String SQL_CREATE_TABLA_REGISTRO =
            "CREATE TABLE " + tablaregistro + "(" +
                    registro_id + " TEXT PRIMARY KEY," +
                    registro_codigo + " TEXT," +
                    registro_nombres + " TEXT," +
                    registro_sede + " TEXT," +
                    registro_aula + " TEXT," +
                    registro_dia + " INTEGER," +
                    registro_mes + " INTEGER," +
                    registro_anio + " INTEGER," +
                    registro_hora_entrada + " INTEGER," +
                    registro_minuto_entrada + " INTEGER," +
                    registro_hora_salida + " INTEGER," +
                    registro_minuto_salida + " INTEGER," +
                    registro_subido_entrada + " INTEGER," +
                    registro_subido_salida + " INTEGER" + ");"
            ;



    public static final String WHERE_CLAUSE_CLAVE = "clave=?";
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
