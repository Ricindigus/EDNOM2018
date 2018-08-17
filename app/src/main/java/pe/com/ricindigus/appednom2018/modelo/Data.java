package pe.com.ricindigus.appednom2018.modelo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Data {
    Context contexto;
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    public Data(Context contexto){
        this.contexto = contexto;
        sqLiteOpenHelper = new DataBaseHelper(contexto);
    }

    public Data(Context contexto,int flag) throws IOException {
        this.contexto = contexto;
        sqLiteOpenHelper = new DataBaseHelper(contexto);
        createDataBase();
    }

    public Data(Context contexto, String inputPath) throws IOException {
        this.contexto = contexto;
        sqLiteOpenHelper = new DataBaseHelper(contexto);
        createDataBase(inputPath);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(!dbExist){
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
            sqLiteDatabase.close();
            try{
                copyDataBase();
                sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIA);
                sqLiteDatabase.close();
            }catch (IOException e){
                throw new Error("Error: copiando base de datos");
            }
        }

    }

    @SuppressLint("NewApi")
    public void createDataBase(String inputPath) throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist){
            File dbFile = new File(SQLConstantes.DB_PATH + SQLConstantes.DB_NAME);
            SQLiteDatabase.deleteDatabase(dbFile);
        }
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase.close();
        try{
            copyDataBase(inputPath);
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIA);
            sqLiteDatabase.close();
        }catch (IOException e){
            throw new Error("Error: copiando base de datos");
        }

    }

    public void copyDataBase() throws IOException{
        InputStream myInput = contexto.getAssets().open(SQLConstantes.DB_NAME);
        String outFileName = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) != -1){
            if (length > 0){
                myOutput.write(buffer,0,length);
            }
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();

    }


    public void copyDataBase(String inputPath) throws IOException{
//        InputStream myInput = contexto.getAssets().open(SQLConstantes.DB_NAME);
        InputStream myInput = new FileInputStream(inputPath);
        String outFileName = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) != -1){
            if (length > 0){
                myOutput.write(buffer,0,length);
            }
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();

    }

    public void open() throws SQLException {
        String myPath = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close(){
        if(sqLiteDatabase != null){
            sqLiteDatabase.close();
        }
    }

    public boolean checkDataBase(){
        try{
            String myPath = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
            sqLiteDatabase = SQLiteDatabase.openDatabase(myPath,null, SQLiteDatabase.OPEN_READWRITE);
        }catch (Exception e){
            File dbFile = new File(SQLConstantes.DB_PATH + SQLConstantes.DB_NAME);
            return dbFile.exists();
        }
        if (sqLiteDatabase != null) sqLiteDatabase.close();

        return sqLiteDatabase != null ? true : false;
    }

    public UsuarioLocal getUsuarioLocal(String clave){
        UsuarioLocal usuario = null;
        String[] whereArgs = new String[]{clave};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablausuariolocal,
                    null,SQLConstantes.WHERE_CLAUSE_CLAVE,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                usuario = new UsuarioLocal();
                usuario.setUsuario(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_local_usuario)));
                usuario.setClave(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_local_clave)));
                usuario.setRol(cursor.getInt(cursor.getColumnIndex(SQLConstantes.usuario_local_rol)));
                usuario.setNro_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.usuario_local_nro_local)));
                usuario.setNombreLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_local_nombreLocal)));
                usuario.setNaulas(cursor.getInt(cursor.getColumnIndex(SQLConstantes.usuario_local_naulas)));
                usuario.setNcontingencia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.usuario_local_ncontingencia)));
                usuario.setCodsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.usuario_local_codsede)));
                usuario.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_local_sede)));
                usuario.setNombre(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_local_nombre)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return usuario;
    }

    public Nacional getNacionalxDNI(String dni){
        Nacional nacional = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablanacional,
                    null,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                nacional = new Nacional();
                nacional.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_sede)));
                nacional.setNro_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_nro_local)));
                nacional.setLocal_aplicacion(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_local_aplicacion)));
                nacional.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_aula)));
                nacional.setCodigo_pagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_codigo_pagina)));
                nacional.setIns_numdoc(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_ins_numdoc)));
                nacional.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_apepat)));
                nacional.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_apemat)));
                nacional.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_nombres)));
                nacional.setEstatus(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_estatus)));
                nacional.setS_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_s_aula)));
                nacional.setS_ficha(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_s_ficha)));
                nacional.setS_cartilla(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_s_cartilla)));
                nacional.setS_listaasistencia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_s_listaasistencia)));
                nacional.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_id_local)));
                nacional.setId_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_id_aula)));
                nacional.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_direccion)));
                nacional.setCodficha(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_codficha)));
                nacional.setCodcartilla(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_codcartilla)));
                nacional.setNew_aula(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_new_aula)));
                nacional.setNew_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_new_local)));
                nacional.setTipo(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_tipo)));
                nacional.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_discapacidad)));
                nacional.setVersion(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_version)));
                nacional.setTipo_concurso(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_tipo_concurso)));
                nacional.setEstatus2(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_estatus2)));
                nacional.setEstatus3(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_estatus3)));
                nacional.setNew_aula_ficha(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_new_aula_ficha)));
                nacional.setNew_aula_cartilla(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_new_aula_cartilla)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return nacional;
    }

    public void insertarAsistencia(Asistencia asistencia){
        ContentValues contentValues = asistencia.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablaasistencia,null,contentValues);
    }

    public void actualizarAsistencia(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablaasistencia,valores,SQLConstantes.WHERE_CLAUSE_DNI_ASISTENCIA,whereArgs);
    }

    public Asistencia getAsistenciaLocal(String dni){
        Asistencia asistencia = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia, null,
                    SQLConstantes.WHERE_CLAUSE_DNI_ASISTENCIA
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                if (cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_local)) != -1) {
                    asistencia = new Asistencia();
                    asistencia.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_id)));
                    asistencia.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_dni)));
                    asistencia.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_id_local)));
                    asistencia.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombre_local)));
                    asistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_sede)));
                    asistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula)));
                    asistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombres)));
                    asistencia.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apepat)));
                    asistencia.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apemat)));
                    asistencia.setLocal_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_dia)));
                    asistencia.setLocal_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_mes)));
                    asistencia.setLocal_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_anio)));
                    asistencia.setLocal_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_hora)));
                    asistencia.setLocal_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_minuto)));
                    asistencia.setAula_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_dia)));
                    asistencia.setAula_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_mes)));
                    asistencia.setAula_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_anio)));
                    asistencia.setAula_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_hora)));
                    asistencia.setAula_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_minuto)));
                    asistencia.setSubido_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_local)));
                    asistencia.setSubido_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_aula)));
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistencia;
    }

    public ArrayList<Asistencia> getAllAsistenciaLocal(int nroLocal){
        ArrayList<Asistencia> asistencias = new ArrayList<Asistencia>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                if (cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_local)) != -1) {
                    Asistencia asistencia = new Asistencia();
                    asistencia.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_id)));
                    asistencia.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_dni)));
                    asistencia.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_id_local)));
                    asistencia.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombre_local)));
                    asistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_sede)));
                    asistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula)));
                    asistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombres)));
                    asistencia.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apepat)));
                    asistencia.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apemat)));
                    asistencia.setLocal_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_dia)));
                    asistencia.setLocal_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_mes)));
                    asistencia.setLocal_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_anio)));
                    asistencia.setLocal_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_hora)));
                    asistencia.setLocal_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_minuto)));
                    asistencia.setAula_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_dia)));
                    asistencia.setAula_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_mes)));
                    asistencia.setAula_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_anio)));
                    asistencia.setAula_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_hora)));
                    asistencia.setAula_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_minuto)));
                    asistencia.setSubido_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_local)));
                    asistencia.setSubido_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_aula)));
                    asistencias.add(asistencia);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistencias;
    }
    public ArrayList<Asistencia> getAllAsistenciaLocalSinEnviar(int nroLocal){
        ArrayList<Asistencia> asistencias = null;
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);while(cursor.moveToNext()){
                if(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_local)) == 0){
                    Asistencia asistencia = new Asistencia();
                    asistencia.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_id)));
                    asistencia.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_dni)));
                    asistencia.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_id_local)));
                    asistencia.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombre_local)));
                    asistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_sede)));
                    asistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula)));
                    asistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombres)));
                    asistencia.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apepat)));
                    asistencia.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apemat)));
                    asistencia.setLocal_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_dia)));
                    asistencia.setLocal_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_mes)));
                    asistencia.setLocal_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_anio)));
                    asistencia.setLocal_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_hora)));
                    asistencia.setLocal_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_minuto)));
                    asistencia.setAula_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_dia)));
                    asistencia.setAula_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_mes)));
                    asistencia.setAula_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_anio)));
                    asistencia.setAula_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_hora)));
                    asistencia.setAula_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_minuto)));
                    asistencia.setSubido_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_local)));
                    asistencia.setSubido_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_aula)));
                    asistencias.add(asistencia);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistencias;
    }

    public Asistencia getAsistenciaAula(String dni){
        Asistencia asistencia = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia, null,
                    SQLConstantes.WHERE_CLAUSE_DNI_ASISTENCIA
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                if (cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_aula)) != -1) {
                    asistencia = new Asistencia();
                    asistencia.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_id)));
                    asistencia.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_dni)));
                    asistencia.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_id_local)));
                    asistencia.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombre_local)));
                    asistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_sede)));
                    asistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula)));
                    asistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombres)));
                    asistencia.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apepat)));
                    asistencia.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apemat)));
                    asistencia.setLocal_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_dia)));
                    asistencia.setLocal_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_mes)));
                    asistencia.setLocal_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_anio)));
                    asistencia.setLocal_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_hora)));
                    asistencia.setLocal_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_minuto)));
                    asistencia.setAula_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_dia)));
                    asistencia.setAula_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_mes)));
                    asistencia.setAula_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_anio)));
                    asistencia.setAula_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_hora)));
                    asistencia.setAula_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_minuto)));
                    asistencia.setSubido_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_local)));
                    asistencia.setSubido_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_aula)));
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistencia;
    }

    public ArrayList<Asistencia> getAllAsistenciaAula(int nroLocal, int nroAula){
        ArrayList<Asistencia> asistencias = new ArrayList<Asistencia>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia, null,
                            SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA
                    ,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                if (cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_aula)) != -1) {
                    Asistencia asistencia = new Asistencia();
                    asistencia.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_id)));
                    asistencia.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_dni)));
                    asistencia.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_id_local)));
                    asistencia.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombre_local)));
                    asistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_sede)));
                    asistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula)));
                    asistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombres)));
                    asistencia.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apepat)));
                    asistencia.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apemat)));
                    asistencia.setLocal_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_dia)));
                    asistencia.setLocal_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_mes)));
                    asistencia.setLocal_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_anio)));
                    asistencia.setLocal_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_hora)));
                    asistencia.setLocal_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_minuto)));
                    asistencia.setAula_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_dia)));
                    asistencia.setAula_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_mes)));
                    asistencia.setAula_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_anio)));
                    asistencia.setAula_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_hora)));
                    asistencia.setAula_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_minuto)));
                    asistencia.setSubido_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_local)));
                    asistencia.setSubido_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_aula)));
                    asistencias.add(asistencia);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistencias;
    }
    public ArrayList<Asistencia> getAllAsistenciaAulaSinEnviar(int nroLocal, int nroAula){
        ArrayList<Asistencia> asistencias = null;
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA
                    ,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                if(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_aula)) == 0){
                    Asistencia asistencia = new Asistencia();
                    asistencia.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_id)));
                    asistencia.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_dni)));
                    asistencia.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_id_local)));
                    asistencia.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombre_local)));
                    asistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_sede)));
                    asistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula)));
                    asistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombres)));
                    asistencia.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apepat)));
                    asistencia.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_apemat)));
                    asistencia.setLocal_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_dia)));
                    asistencia.setLocal_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_mes)));
                    asistencia.setLocal_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_anio)));
                    asistencia.setLocal_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_hora)));
                    asistencia.setLocal_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_minuto)));
                    asistencia.setAula_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_dia)));
                    asistencia.setAula_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_mes)));
                    asistencia.setAula_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_anio)));
                    asistencia.setAula_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_hora)));
                    asistencia.setAula_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_minuto)));
                    asistencia.setSubido_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_local)));
                    asistencia.setSubido_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_subido_aula)));
                    asistencias.add(asistencia);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistencias;
    }
//    public ArrayList<Asistencia> getAllRegistrados(String sede, int dia, int mes, int anio){
//        ArrayList<Asistencia> registroAsistencias = new ArrayList<>();
//        String[] whereArgs = new String[]{sede,Integer.toString(dia),Integer.toString(mes),Integer.toString(anio)};
//        Cursor cursor = null;
//        try{
//            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
//                    null,SQLConstantes.WHERE_CLAUSE_SEDE + " AND " +
//                    SQLConstantes.WHERE_CLAUSE_DIA + " AND " +
//                    SQLConstantes.WHERE_CLAUSE_MES + " AND " +
//                    SQLConstantes.WHERE_CLAUSE_ANIO, whereArgs,null,null,null);
//            while(cursor.moveToNext()){
//                Asistencia registroAsistencia = new Asistencia();
//                registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
//                registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
//                registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
//                registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
//                registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
//                registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
//                registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
//                registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
//                registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
//                registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
//                registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
//                registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
//                registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
//                registroAsistencias.add(registroAsistencia);
//            }
//        }finally{
//            if(cursor != null) cursor.close();
//        }
//        return registroAsistencias;
//    }

    public ArrayList<String> getArrayAulas(int nroLocal){
        ArrayList<String> aulas = new ArrayList<>();
        String[] whereArgs = new String[]{Integer.toString(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaaulalocal,
                    null,SQLConstantes.WHERE_CLAUSE_NRO_LOCAL, whereArgs,null,null,null);
            while(cursor.moveToNext()){
                String a = cursor.getString(cursor.getColumnIndex(SQLConstantes.aulas_nombre));
                aulas.add(a);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return aulas;
    }

    public int getNumeroAula(String aula, int nroLocal){
        int numeroAula = 0;
        String[] whereArgs = new String[]{aula, String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaaulalocal,
                    null, SQLConstantes.WHERE_CLAUSE_NOMBRE_AULA + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LOCAL
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                numeroAula = cursor.getInt(cursor.getColumnIndex(SQLConstantes.aulas_naula));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return numeroAula;
    }





//
//    public Asistencia getRegistro(String dni){
//        Asistencia registroAsistencia = null;
//        String[] whereArgs = new String[]{dni};
//        Cursor cursor = null;
//        try{
//            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
//                    null,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs,null,null,null);
//            if(cursor.getCount() == 1){
//                cursor.moveToFirst();
//                registroAsistencia = new Asistencia();
//                registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
//                registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
//                registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
//                registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
//                registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
//                registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
//                registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
//                registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
//                registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
//                registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
//                registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
//                registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
//                registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
//            }
//        }finally{
//            if(cursor != null) cursor.close();
//        }
//        return registroAsistencia;
//    }
//
//    public Asistencia getRegistro(String dni, int dia, int mes, int anio){
//        Asistencia registroAsistencia = null;
//        String[] whereArgs = new String[]{dni,Integer.toString(dia),Integer.toString(mes),Integer.toString(anio)};
//        Cursor cursor = null;
//        try{
//            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro, null,
//                    SQLConstantes.WHERE_CLAUSE_CODIGO+ " AND " +
//                            SQLConstantes.WHERE_CLAUSE_DIA + " AND " +
//                            SQLConstantes.WHERE_CLAUSE_MES + " AND " +
//                            SQLConstantes.WHERE_CLAUSE_ANIO
//                    ,whereArgs,null,null,null);
//            if(cursor.getCount() == 1){
//                cursor.moveToFirst();
//                registroAsistencia = new Asistencia();
//                registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
//                registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
//                registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
//                registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
//                registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
//                registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
//                registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
//                registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
//                registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
//                registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
//                registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
//                registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
//                registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
//            }
//        }finally{
//            if(cursor != null) cursor.close();
//        }
//        return registroAsistencia;
//    }
//
//    public ArrayList<Asistencia> getAllRegistrados(String sede, int dia, int mes, int anio){
//        ArrayList<Asistencia> registroAsistencias = new ArrayList<>();
//        String[] whereArgs = new String[]{sede,Integer.toString(dia),Integer.toString(mes),Integer.toString(anio)};
//        Cursor cursor = null;
//        try{
//            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
//                    null,SQLConstantes.WHERE_CLAUSE_SEDE + " AND " +
//                    SQLConstantes.WHERE_CLAUSE_DIA + " AND " +
//                    SQLConstantes.WHERE_CLAUSE_MES + " AND " +
//                    SQLConstantes.WHERE_CLAUSE_ANIO, whereArgs,null,null,null);
//            while(cursor.moveToNext()){
//                Asistencia registroAsistencia = new Asistencia();
//                registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
//                registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
//                registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
//                registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
//                registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
//                registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
//                registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
//                registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
//                registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
//                registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
//                registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
//                registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
//                registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
//                registroAsistencias.add(registroAsistencia);
//            }
//        }finally{
//            if(cursor != null) cursor.close();
//        }
//        return registroAsistencias;
//    }
//
//    public ArrayList<Asistencia> getAllSinEnviar(String sede, int dia, int mes, int anio){
//        ArrayList<Asistencia> registroAsistencias = new ArrayList<>();
//        String[] whereArgs = new String[]{sede,Integer.toString(dia),Integer.toString(mes),Integer.toString(anio)};
//        Cursor cursor = null;
//        try{
//            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
//                    null,SQLConstantes.WHERE_CLAUSE_SEDE + " AND " +
//                            SQLConstantes.WHERE_CLAUSE_DIA + " AND " +
//                            SQLConstantes.WHERE_CLAUSE_MES + " AND " +
//                            SQLConstantes.WHERE_CLAUSE_ANIO, whereArgs,null,null,null);
//            while(cursor.moveToNext()){
//                int subidoEntrada = cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada));
//                int subidoSalida = cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida));
//                if(subidoEntrada == 0 || subidoSalida == 0){
//                    Asistencia registroAsistencia = new Asistencia();
//                    registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
//                    registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
//                    registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
//                    registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
//                    registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
//                    registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
//                    registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
//                    registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
//                    registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
//                    registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
//                    registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
//                    registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
//                    registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
//                    registroAsistencias.add(registroAsistencia);
//                }
//            }
//        }finally{
//            if(cursor != null) cursor.close();
//        }
//        return registroAsistencias;
//    }
//
//    public ArrayList<Asistencia> getAllSalidaSinEnviar(String sede, int dia, int mes, int anio){
//        ArrayList<Asistencia> registroAsistencias = new ArrayList<>();
//        String[] whereArgs = new String[]{sede,Integer.toString(dia),Integer.toString(mes),Integer.toString(anio)};
//        Cursor cursor = null;
//        try{
//            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
//                    null,SQLConstantes.WHERE_CLAUSE_SEDE + " AND " +
//                            SQLConstantes.WHERE_CLAUSE_DIA + " AND " +
//                            SQLConstantes.WHERE_CLAUSE_MES + " AND " +
//                            SQLConstantes.WHERE_CLAUSE_ANIO, whereArgs,null,null,null);
//            while(cursor.moveToNext()){
//                int subidoEntrada = cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida));
//                if(subidoEntrada == 0){
//                    Asistencia registroAsistencia = new Asistencia();
//                    registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
//                    registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
//                    registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
//                    registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
//                    registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
//                    registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
//                    registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
//                    registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
//                    registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
//                    registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
//                    registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
//                    registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
//                    registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
//                    registroAsistencias.add(registroAsistencia);
//                }
//            }
//        }finally{
//            if(cursor != null) cursor.close();
//        }
//        return registroAsistencias;
//    }
//
//    public ArrayList<Asistencia> getAllRegistradosNube(){
//        ArrayList<Asistencia> registroAsistencias = new ArrayList<>();
//        String[] whereArgs = new String[]{"1"};
//        Cursor cursor = null;
//        try{
//            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
//                    null,SQLConstantes.WHERE_CLAUSE_SUBIDO_ENTRADA,whereArgs,null,null,null);
//            while(cursor.moveToNext()){
//                Asistencia registroAsistencia = new Asistencia();
//                registroAsistencia.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_id)));
//                registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
//                registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
//                registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
//                registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
//                registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
//                registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
//                registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
//                registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
//                registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
//                registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
//                registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
//                registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
//                registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
//                registroAsistencias.add(registroAsistencia);
//            }
//        }finally{
//            if(cursor != null) cursor.close();
//        }
//        return registroAsistencias;
//    }

    public void deleteAllElementosFromTabla(String nombreTabla){
        sqLiteDatabase.execSQL("delete from "+ nombreTabla);
    }

}
