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
import java.util.HashSet;

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
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIA_LOCAL);
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIA_AULA);
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_FICHAS);
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CUADERNILLOS);
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
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIA_LOCAL);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIA_AULA);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_FICHAS);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CUADERNILLOS);
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

    public Nacional getNacionalxFicha(String codigoFicha){
        Nacional nacional = null;
        String[] whereArgs = new String[]{codigoFicha};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablanacional,
                    null,SQLConstantes.WHERE_CLAUSE_CODIGO_FICHA,whereArgs,null,null,null);
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

    public Nacional getNacionalxCuadernillo(String codigoCuadernillo){
        Nacional nacional = null;
        String[] whereArgs = new String[]{codigoCuadernillo};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablanacional,
                    null,SQLConstantes.WHERE_CLAUSE_CODIGO_CUADERNILLO,whereArgs,null,null,null);
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

    public ArrayList<Nacional> getNacionalxNroLocal(int nroLocal){
        ArrayList<Nacional> nacionals = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablanacional,
                    null,SQLConstantes.WHERE_CLAUSE_NRO_LOCAL,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                Nacional nacional = new Nacional();
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
                nacionals.add(nacional);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return nacionals;
    }

    public ArrayList<Nacional> getListasDelLocal(int nroLocal){
        ArrayList<Nacional> nacionals = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablanacional,
                    null,SQLConstantes.WHERE_CLAUSE_NRO_LOCAL,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                Nacional nacional = new Nacional();
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
                nacionals.add(nacional);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        if(nacionals.size() > 0){
            HashSet hs = new HashSet();
            hs.addAll(nacionals);
            nacionals.clear();
            nacionals.addAll(hs);
        }
        return nacionals;
    }

    public void insertarAsistenciaLocal(AsistenciaLocal asistenciaLocal){
        ContentValues contentValues = asistenciaLocal.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablaasislocal,null,contentValues);
    }

    public void actualizarAsistenciaLocal(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablaasislocal,valores,SQLConstantes.WHERE_CLAUSE_DNI_ASISTENCIA,whereArgs);
    }

    public void actualizarAsistenciaLocalSubido(String codigo){
        String[] whereArgs = new String[]{codigo};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.asistencia_local_subido_local,1);
        sqLiteDatabase.update(SQLConstantes.tablaasislocal,contentValues,SQLConstantes.WHERE_CLAUSE_DNI_ASISTENCIA,whereArgs);
    }

    public AsistenciaLocal getAsistenciaLocal(String dni){
        AsistenciaLocal asistenciaLocal = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasislocal, null,
                    SQLConstantes.WHERE_CLAUSE_DNI_ASISTENCIA
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();

                    asistenciaLocal = new AsistenciaLocal();
                    asistenciaLocal.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_id)));
                    asistenciaLocal.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_dni)));
                    asistenciaLocal.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_id_local)));
                    asistenciaLocal.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_nombre_local)));
                    asistenciaLocal.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_sede)));
                    asistenciaLocal.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_aula)));
                    asistenciaLocal.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_nombres)));
                    asistenciaLocal.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_apepat)));
                    asistenciaLocal.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_apemat)));
                    asistenciaLocal.setLocal_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_dia)));
                    asistenciaLocal.setLocal_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_mes)));
                    asistenciaLocal.setLocal_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_anio)));
                    asistenciaLocal.setLocal_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_hora)));
                    asistenciaLocal.setLocal_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_minuto)));
                    asistenciaLocal.setSubido_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_subido_local)));

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaLocal;
    }

    public ArrayList<AsistenciaLocal> getAllAsistenciaLocal(int nroLocal){
        ArrayList<AsistenciaLocal> asistenciaLocals = new ArrayList<AsistenciaLocal>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasislocal, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while(cursor.moveToNext()){

                    AsistenciaLocal asistenciaLocal = new AsistenciaLocal();
                    asistenciaLocal.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_id)));
                    asistenciaLocal.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_dni)));
                    asistenciaLocal.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_id_local)));
                    asistenciaLocal.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_nombre_local)));
                    asistenciaLocal.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_sede)));
                    asistenciaLocal.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_aula)));
                    asistenciaLocal.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_nombres)));
                    asistenciaLocal.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_apepat)));
                    asistenciaLocal.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_apemat)));
                    asistenciaLocal.setLocal_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_dia)));
                    asistenciaLocal.setLocal_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_mes)));
                    asistenciaLocal.setLocal_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_anio)));
                    asistenciaLocal.setLocal_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_hora)));
                    asistenciaLocal.setLocal_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_minuto)));
                    asistenciaLocal.setSubido_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_subido_local)));
                    asistenciaLocals.add(asistenciaLocal);

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaLocals;
    }
    public ArrayList<AsistenciaLocal> getAllAsistenciaLocalSinEnviar(int nroLocal){
        ArrayList<AsistenciaLocal> asistenciaLocals = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasislocal, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);while(cursor.moveToNext()){
                if(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_subido_local)) == 0){
                    AsistenciaLocal asistenciaLocal = new AsistenciaLocal();
                    asistenciaLocal.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_id)));
                    asistenciaLocal.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_dni)));
                    asistenciaLocal.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_id_local)));
                    asistenciaLocal.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_nombre_local)));
                    asistenciaLocal.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_sede)));
                    asistenciaLocal.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_aula)));
                    asistenciaLocal.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_nombres)));
                    asistenciaLocal.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_apepat)));
                    asistenciaLocal.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local_apemat)));
                    asistenciaLocal.setLocal_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_dia)));
                    asistenciaLocal.setLocal_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_mes)));
                    asistenciaLocal.setLocal_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_anio)));
                    asistenciaLocal.setLocal_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_hora)));
                    asistenciaLocal.setLocal_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_local_minuto)));
                    asistenciaLocal.setSubido_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_local_subido_local)));
                    asistenciaLocals.add(asistenciaLocal);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaLocals;
    }

    public void insertarAsistenciaAula(AsistenciaAula asistenciaAula){
        ContentValues contentValues = asistenciaAula.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablaasisaula,null,contentValues);
    }

    public void actualizarAsistenciaAula(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablaasisaula,valores,SQLConstantes.WHERE_CLAUSE_DNI_ASISTENCIA,whereArgs);
    }

    public void actualizarAsistenciaAulaSubido(String codigo){
        String[] whereArgs = new String[]{codigo};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.asistencia_aula_subido_aula,1);
        sqLiteDatabase.update(SQLConstantes.tablaasisaula,contentValues,SQLConstantes.WHERE_CLAUSE_DNI_ASISTENCIA,whereArgs);
    }

    public AsistenciaAula getAsistenciaAula(String dni){
        AsistenciaAula asistenciaAula = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasisaula, null,
                    SQLConstantes.WHERE_CLAUSE_DNI_ASISTENCIA
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                    asistenciaAula = new AsistenciaAula();
                    asistenciaAula.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_id)));
                    asistenciaAula.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_dni)));
                    asistenciaAula.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_id_local)));
                    asistenciaAula.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_nombre_local)));
                    asistenciaAula.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_sede)));
                    asistenciaAula.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula)));
                    asistenciaAula.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_nombres)));
                    asistenciaAula.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_apepat)));
                    asistenciaAula.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_apemat)));
                    asistenciaAula.setAula_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_dia)));
                    asistenciaAula.setAula_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_mes)));
                    asistenciaAula.setAula_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_anio)));
                    asistenciaAula.setAula_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_hora)));
                    asistenciaAula.setAula_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_minuto)));
                    asistenciaAula.setSubido_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_subido_aula)));

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaAula;
    }

    public ArrayList<AsistenciaAula> getAllAsistenciaAula(int nroLocal, int nroAula){
        ArrayList<AsistenciaAula> asistenciaAulas = new ArrayList<AsistenciaAula>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasisaula, null,
                            SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA
                    ,whereArgs,null,null,null);
            while(cursor.moveToNext()){

                    AsistenciaAula asistenciaAula = new AsistenciaAula();
                    asistenciaAula.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_id)));
                    asistenciaAula.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_dni)));
                    asistenciaAula.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_id_local)));
                    asistenciaAula.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_nombre_local)));
                    asistenciaAula.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_sede)));
                    asistenciaAula.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula)));
                    asistenciaAula.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_nombres)));
                    asistenciaAula.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_apepat)));
                    asistenciaAula.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_apemat)));
                    asistenciaAula.setAula_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_dia)));
                    asistenciaAula.setAula_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_mes)));
                    asistenciaAula.setAula_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_anio)));
                    asistenciaAula.setAula_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_hora)));
                    asistenciaAula.setAula_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_minuto)));
                    asistenciaAula.setSubido_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_subido_aula)));
                    asistenciaAulas.add(asistenciaAula);

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaAulas;
    }
    public ArrayList<AsistenciaAula> getAllAsistenciaAulaSinEnviar(int nroLocal, int nroAula){
        ArrayList<AsistenciaAula> asistenciaAulas = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasisaula, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA
                    ,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                if(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_subido_aula)) == 0){
                    AsistenciaAula asistenciaAula = new AsistenciaAula();
                    asistenciaAula.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_id)));
                    asistenciaAula.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_dni)));
                    asistenciaAula.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_id_local)));
                    asistenciaAula.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_nombre_local)));
                    asistenciaAula.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_sede)));
                    asistenciaAula.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula)));
                    asistenciaAula.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_nombres)));
                    asistenciaAula.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_apepat)));
                    asistenciaAula.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_aula_apemat)));
                    asistenciaAula.setAula_dia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_dia)));
                    asistenciaAula.setAula_mes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_mes)));
                    asistenciaAula.setAula_anio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_anio)));
                    asistenciaAula.setAula_hora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_hora)));
                    asistenciaAula.setAula_minuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_aula_minuto)));
                    asistenciaAula.setSubido_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_aula_subido_aula)));
                    asistenciaAulas.add(asistenciaAula);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaAulas;
    }
//    public ArrayList<AsistenciaLocal> getAllRegistrados(String sede, int dia, int mes, int anio){
//        ArrayList<AsistenciaLocal> registroAsistencias = new ArrayList<>();
//        String[] whereArgs = new String[]{sede,Integer.toString(dia),Integer.toString(mes),Integer.toString(anio)};
//        Cursor cursor = null;
//        try{
//            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
//                    null,SQLConstantes.WHERE_CLAUSE_SEDE + " AND " +
//                    SQLConstantes.WHERE_CLAUSE_DIA + " AND " +
//                    SQLConstantes.WHERE_CLAUSE_MES + " AND " +
//                    SQLConstantes.WHERE_CLAUSE_ANIO, whereArgs,null,null,null);
//            while(cursor.moveToNext()){
//                AsistenciaLocal registroAsistencia = new AsistenciaLocal();
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

    public void insertarFicha(Ficha ficha){
        ContentValues contentValues = ficha.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablafichas,null,contentValues);
    }

    public void actualizarFicha(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablafichas,valores,SQLConstantes.WHERE_CLAUSE_CODIGO_FICHA,whereArgs);
    }

    public void actualizarFichaSubido(String codigo){
        String[] whereArgs = new String[]{codigo};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.ficha_subido,1);
        sqLiteDatabase.update(SQLConstantes.tablafichas,contentValues,SQLConstantes.WHERE_CLAUSE_CODIGO_FICHA,whereArgs);
    }

    public Ficha getFicha(String codigoFicha){
        Ficha ficha = null;
        String[] whereArgs = new String[]{codigoFicha};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichas, null,
                    SQLConstantes.WHERE_CLAUSE_CODIGO_FICHA
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                ficha = new Ficha();
                ficha.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_id)));
                ficha.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_dni)));
                ficha.setCodficha(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_codficha)));
                ficha.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_id_local)));
                ficha.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_nombre_local)));
                ficha.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_sede)));
                ficha.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_aula)));
                ficha.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_nombres)));
                ficha.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_apepat)));
                ficha.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_apemat)));
                ficha.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_dia)));
                ficha.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_mes)));
                ficha.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_anio)));
                ficha.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_hora)));
                ficha.setMinuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_minuto)));
                ficha.setSubido(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_subido)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return ficha;
    }

    public ArrayList<Ficha> getAllFichas(int nroLocal, int nroAula){
        ArrayList<Ficha> fichas = new ArrayList<Ficha>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichas, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA
                    ,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                Ficha ficha = new Ficha();
                ficha.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_id)));
                ficha.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_dni)));
                ficha.setCodficha(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_codficha)));
                ficha.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_id_local)));
                ficha.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_nombre_local)));
                ficha.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_sede)));
                ficha.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_aula)));
                ficha.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_nombres)));
                ficha.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_apepat)));
                ficha.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_apemat)));
                ficha.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_dia)));
                ficha.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_mes)));
                ficha.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_anio)));
                ficha.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_hora)));
                ficha.setMinuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_minuto)));
                ficha.setSubido(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_subido)));
                fichas.add(ficha);

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return fichas;
    }
    public ArrayList<Ficha> getAllFichasSinEnviar(int nroLocal, int nroAula){
        ArrayList<Ficha> fichas = new ArrayList<Ficha>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichas, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA
                    ,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                if(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_subido)) == 0){
                    Ficha ficha = new Ficha();
                    ficha.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_id)));
                    ficha.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_dni)));
                    ficha.setCodficha(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_codficha)));
                    ficha.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_id_local)));
                    ficha.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_nombre_local)));
                    ficha.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_sede)));
                    ficha.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_aula)));
                    ficha.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_nombres)));
                    ficha.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_apepat)));
                    ficha.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.ficha_apemat)));
                    ficha.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_dia)));
                    ficha.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_mes)));
                    ficha.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_anio)));
                    ficha.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_hora)));
                    ficha.setMinuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_minuto)));
                    ficha.setSubido(cursor.getInt(cursor.getColumnIndex(SQLConstantes.ficha_subido)));
                    fichas.add(ficha);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return fichas;
    }

    public void insertarCuadernillo(Cuadernillo cuadernillo){
        ContentValues contentValues = cuadernillo.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablacuadernillos,null,contentValues);
    }

    public void actualizarCuadernillo(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablacuadernillos,valores,SQLConstantes.WHERE_CLAUSE_CODIGO_CUADERNILLO,whereArgs);
    }

    public void actualizarCuadernilloSubido(String codigo){
        String[] whereArgs = new String[]{codigo};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.cuadernillo_subido,1);
        sqLiteDatabase.update(SQLConstantes.tablacuadernillos,contentValues,SQLConstantes.WHERE_CLAUSE_CODIGO_CUADERNILLO,whereArgs);
    }

    public Cuadernillo getCuadernillo(String codigoCuadernillo){
        Cuadernillo cuadernillo = null;
        String[] whereArgs = new String[]{codigoCuadernillo};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillos, null,
                    SQLConstantes.WHERE_CLAUSE_CODIGO_CUADERNILLO
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                cuadernillo = new Cuadernillo();
                cuadernillo.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_id)));
                cuadernillo.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_dni)));
                cuadernillo.setCodcartilla(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_codcartilla)));
                cuadernillo.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_id_local)));
                cuadernillo.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_nombre_local)));
                cuadernillo.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_sede)));
                cuadernillo.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_aula)));
                cuadernillo.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_nombres)));
                cuadernillo.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_apepat)));
                cuadernillo.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_apemat)));
                cuadernillo.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_dia)));
                cuadernillo.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_mes)));
                cuadernillo.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_anio)));
                cuadernillo.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_hora)));
                cuadernillo.setMinuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_minuto)));
                cuadernillo.setSubido(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_subido)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cuadernillo;
    }

    public ArrayList<Cuadernillo> getAllCuadernillos(int nroLocal, int nroAula){
        ArrayList<Cuadernillo> cuadernillos = new ArrayList<Cuadernillo>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillos, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA
                    ,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                Cuadernillo cuadernillo = new Cuadernillo();
                cuadernillo.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_id)));
                cuadernillo.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_dni)));
                cuadernillo.setCodcartilla(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_codcartilla)));
                cuadernillo.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_id_local)));
                cuadernillo.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_nombre_local)));
                cuadernillo.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_sede)));
                cuadernillo.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_aula)));
                cuadernillo.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_nombres)));
                cuadernillo.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_apepat)));
                cuadernillo.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_apemat)));
                cuadernillo.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_dia)));
                cuadernillo.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_mes)));
                cuadernillo.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_anio)));
                cuadernillo.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_hora)));
                cuadernillo.setMinuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_minuto)));
                cuadernillo.setSubido(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_subido)));
                cuadernillos.add(cuadernillo);

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cuadernillos;
    }

    public ArrayList<Cuadernillo> getAllCuadernillosSinEnviar(int nroLocal, int nroAula){
        ArrayList<Cuadernillo> cuadernillos = new ArrayList<Cuadernillo>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillos, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA
                    ,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                if (cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_subido)) == 0){
                    Cuadernillo cuadernillo = new Cuadernillo();
                    cuadernillo.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_id)));
                    cuadernillo.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_dni)));
                    cuadernillo.setCodcartilla(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_codcartilla)));
                    cuadernillo.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_id_local)));
                    cuadernillo.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_nombre_local)));
                    cuadernillo.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_sede)));
                    cuadernillo.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_aula)));
                    cuadernillo.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_nombres)));
                    cuadernillo.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_apepat)));
                    cuadernillo.setApemat(cursor.getString(cursor.getColumnIndex(SQLConstantes.cuadernillo_apemat)));
                    cuadernillo.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_dia)));
                    cuadernillo.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_mes)));
                    cuadernillo.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_anio)));
                    cuadernillo.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_hora)));
                    cuadernillo.setMinuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_minuto)));
                    cuadernillo.setSubido(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cuadernillo_subido)));
                    cuadernillos.add(cuadernillo);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cuadernillos;
    }

    public void deleteAllElementosFromTabla(String nombreTabla){
        sqLiteDatabase.execSQL("delete from "+ nombreTabla);
    }

}
