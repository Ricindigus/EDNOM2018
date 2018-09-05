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

//    public Data(Context contexto,int flag) throws IOException {
//        this.contexto = contexto;
//        sqLiteOpenHelper = new DataBaseHelper(contexto);
//        createDataBase();
//    }

    public Data(Context contexto, String inputPath) throws IOException {
        this.contexto = contexto;
        sqLiteOpenHelper = new DataBaseHelper(contexto);
        createDataBase(inputPath);
    }


//    public void createDataBase() throws IOException {
//        boolean dbExist = checkDataBase();
//        if(!dbExist){
//            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
//            sqLiteDatabase.close();
//            try{
//                copyDataBase();
//                sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
//                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CAJAS_ENTRADA);
//                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CAJAS_SALIDA);
//                sqLiteDatabase.close();
//            }catch (IOException e){
//                throw new Error("Error: copiando base de datos");
//            }
//        }
//
//    }

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
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CAJAS_ENTRADA);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CAJAS_SALIDA);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIA_AULA);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIA_LOCAL);
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


    public Caja getCajaxCodigo(String codBarra){
        Caja caja = null;
        String[] whereArgs = new String[]{codBarra};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajas,
                    null,SQLConstantes.WHERE_CLAUSE_COD_BARRA,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                caja = new Caja();
                caja.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_id)));
                caja.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_cod_barra)));
                caja.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_idsede)));
                caja.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_nomsede)));
                caja.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_idlocal)));
                caja.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_nomlocal)));
                caja.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_tipo)));
                caja.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_nlado)));
                caja.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_acl)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return caja;
    }

    public ArrayList<CajaIn> getCopiaCajasInxLocal(int nroLocal){
        ArrayList<CajaIn> cajaIns = new ArrayList<CajaIn>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajas, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                CajaIn cajaIn = new CajaIn();
                cajaIn.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_id)));
                cajaIn.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_cod_barra)));
                cajaIn.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_idsede)));
                cajaIn.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomsede)));
                cajaIn.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idlocal)));
                cajaIn.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomlocal)));
                cajaIn.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_tipo)));
                cajaIn.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_acl)));
                cajaIn.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_nlado)));
                cajaIn.setDia(0);
                cajaIn.setMes(0);
                cajaIn.setAnio(0);
                cajaIn.setHora(0);
                cajaIn.setMin(0);
                cajaIn.setSeg(0);
                cajaIn.setCheck_reg(0);
                cajaIn.setEstado(0);
                cajaIns.add(cajaIn);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaIns;
    }

    public ArrayList<CajaOut> getCopiaCajasOutxLocal(int nroLocal){
        ArrayList<CajaOut> cajaOuts = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajas, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                CajaOut cajaOut = new CajaOut();
                cajaOut.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_id)));
                cajaOut.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_cod_barra)));
                cajaOut.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_idsede)));
                cajaOut.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomsede)));
                cajaOut.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idlocal)));
                cajaOut.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomlocal)));
                cajaOut.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_tipo)));
                cajaOut.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_acl)));
                cajaOut.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_nlado)));
                cajaOut.setDia(0);
                cajaOut.setMes(0);
                cajaOut.setAnio(0);
                cajaOut.setHora(0);
                cajaOut.setMin(0);
                cajaOut.setSeg(0);
                cajaOut.setCheck_reg(0);
                cajaOut.setEstado(0);
                cajaOuts.add(cajaOut);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaOuts;
    }

    public void insertarCajaIn(CajaIn cajaIn){
        ContentValues contentValues = cajaIn.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablacajasentrada,null,contentValues);
    }

    public void actualizarCajaIn(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablacajasentrada,valores,SQLConstantes.WHERE_CLAUSE_COD_BARRA,whereArgs);
    }

    public void actualizarCajaInSubido(String codigo){
        String[] whereArgs = new String[]{codigo};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.cajas_entrada_estado,3);
        sqLiteDatabase.update(SQLConstantes.tablacajasentrada,contentValues,SQLConstantes.WHERE_CLAUSE_COD_BARRA,whereArgs);
    }

    public CajaIn getCajaIn(String codBarra){
        CajaIn cajaIn = null;
        String[] whereArgs = new String[]{codBarra};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasentrada,
                    null,SQLConstantes.WHERE_CLAUSE_COD_BARRA,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                cajaIn = new CajaIn();
                cajaIn.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_id)));
                cajaIn.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_cod_barra)));
                cajaIn.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idsede)));
                cajaIn.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomsede)));
                cajaIn.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idlocal)));
                cajaIn.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomlocal)));
                cajaIn.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_acl)));
                cajaIn.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_tipo)));
                cajaIn.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_dia)));
                cajaIn.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_mes)));
                cajaIn.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_anio)));
                cajaIn.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_hora)));
                cajaIn.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_min)));
                cajaIn.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_seg)));
                cajaIn.setCheck_reg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_check_reg)));
                cajaIn.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nlado)));
                cajaIn.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_estado)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaIn;
    }

    public ArrayList<CajaIn> getAllCajaIn(int nroLocal){
        ArrayList<CajaIn> cajaIns = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasentrada, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ArrayList<CajaIn> cajas = new ArrayList<>();
                CajaIn cajaIn = new CajaIn();
                cajaIn.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_id)));
                cajaIn.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_cod_barra)));
                cajaIn.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idsede)));
                cajaIn.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomsede)));
                cajaIn.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idlocal)));
                cajaIn.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomlocal)));
                cajaIn.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_tipo)));
                cajaIn.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_acl)));
                cajaIn.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_dia)));
                cajaIn.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_mes)));
                cajaIn.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_anio)));
                cajaIn.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_hora)));
                cajaIn.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_min)));
                cajaIn.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_seg)));
                cajaIn.setCheck_reg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_check_reg)));
                cajaIn.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nlado)));
                cajaIn.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_estado)));
                cajas.add(cajaIn);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaIns;
    }

    public ArrayList<CajaIn> getAllCajaInListado(int nroLocal){
        ArrayList<CajaIn> cajaIns = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasentrada, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " + SQLConstantes.WHERE_CLAUSE_NRO_LADO,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                CajaIn cajaIn = new CajaIn();
                cajaIn.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_id)));
                cajaIn.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_cod_barra)));
                cajaIn.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idsede)));
                cajaIn.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomsede)));
                cajaIn.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idlocal)));
                cajaIn.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomlocal)));
                cajaIn.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_tipo)));
                cajaIn.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_acl)));
                cajaIn.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_dia)));
                cajaIn.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_mes)));
                cajaIn.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_anio)));
                cajaIn.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_hora)));
                cajaIn.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_min)));
                cajaIn.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_seg)));
                cajaIn.setCheck_reg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_check_reg)));
                cajaIn.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nlado)));
                cajaIn.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_estado)));
                cajaIns.add(cajaIn);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaIns;
    }


    public int getNroCajaInxTipo(int nroLocal, int tipo){
        int cantidad = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(tipo)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasentrada, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " + SQLConstantes.WHERE_CLAUSE_TIPO_CAJA,whereArgs,null,null,null);
            if(cursor.getCount() > 0) cantidad = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return cantidad;
    }

    public ArrayList<CajaIn> getAllCajasInCompletas(int nroLocal){
        ArrayList<CajaIn> cajaIns = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","2"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasentrada, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            while(cursor.moveToNext()){

                    CajaIn cajaIn = new CajaIn();
//                    cajaIn.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_id)));
                    cajaIn.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_cod_barra)));
//                    cajaIn.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idsede)));
//                    cajaIn.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomsede)));
//                    cajaIn.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idlocal)));
//                    cajaIn.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomlocal)));
//                    cajaIn.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_tipo)));
//                    cajaIn.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_acl)));
                    cajaIn.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_dia)));
                    cajaIn.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_mes)));
                    cajaIn.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_anio)));
                    cajaIn.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_hora)));
                    cajaIn.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_min)));
                    cajaIn.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_seg)));
//                    cajaIn.setCheck(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_check_reg)));
//                    cajaIn.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nlado)));
//                    cajaIn.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_estado)));
                    cajaIns.add(cajaIn);

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaIns;
    }

    public ArrayList<CajaIn> getAllCajasInTransferidos(int nroLocal){
        ArrayList<CajaIn> cajaIns = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","3"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasentrada, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            while(cursor.moveToNext()){

                    CajaIn cajaIn = new CajaIn();
//                    cajaIn.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_id)));
                    cajaIn.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_cod_barra)));
//                    cajaIn.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idsede)));
//                    cajaIn.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomsede)));
//                    cajaIn.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_idlocal)));
//                    cajaIn.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nomlocal)));
//                    cajaIn.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_tipo)));
//                    cajaIn.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_acl)));
                    cajaIn.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_dia)));
                    cajaIn.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_mes)));
                    cajaIn.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_anio)));
                    cajaIn.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_hora)));
                    cajaIn.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_min)));
                    cajaIn.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_fecha_reg_seg)));
//                    cajaIn.setCheck(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_check_reg)));
//                    cajaIn.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_nlado)));
//                    cajaIn.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_entrada_estado)));
                    cajaIns.add(cajaIn);

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaIns;
    }

    public void insertarCajaOut(CajaOut cajaOut){
        ContentValues contentValues = cajaOut.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablacajassalida,null,contentValues);
    }

    public void actualizarCajaOut(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablacajassalida,valores,SQLConstantes.WHERE_CLAUSE_COD_BARRA,whereArgs);
    }

    public void actualizarCajaOutSubido(String codigo){
        String[] whereArgs = new String[]{codigo};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.cajas_salida_estado,1);
        sqLiteDatabase.update(SQLConstantes.tablacajassalida,contentValues,SQLConstantes.WHERE_CLAUSE_COD_BARRA,whereArgs);
    }

    public CajaOut getCajaOut(String codBarra){
        CajaOut cajaOut = null;
        String[] whereArgs = new String[]{codBarra};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajassalida,
                    null,SQLConstantes.WHERE_CLAUSE_COD_BARRA,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                cajaOut = new CajaOut();
                cajaOut.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_id)));
                cajaOut.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_cod_barra)));
                cajaOut.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_idsede)));
                cajaOut.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_nomsede)));
                cajaOut.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_idlocal)));
                cajaOut.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_nomlocal)));
                cajaOut.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_tipo)));
                cajaOut.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_acl)));
                cajaOut.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_dia)));
                cajaOut.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_mes)));
                cajaOut.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_anio)));
                cajaOut.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_hora)));
                cajaOut.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_min)));
                cajaOut.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_seg)));
                cajaOut.setCheck_reg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_check_reg)));
                cajaOut.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_nlado)));
                cajaOut.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_estado)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaOut;
    }

    public int getNroCajaOutxTipo(int nroLocal, int tipo){
        int cantidad = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(tipo)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajassalida, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " + SQLConstantes.WHERE_CLAUSE_TIPO_CAJA,whereArgs,null,null,null);
            if(cursor.getCount() > 0) cantidad = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return cantidad;
    }

    public ArrayList<CajaOut> getAllCajaOutListado(int nroLocal){
        ArrayList<CajaOut> cajaOuts = new ArrayList<CajaOut>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajassalida, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " + SQLConstantes.WHERE_CLAUSE_NRO_LADO,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                CajaOut cajaOut = new CajaOut();
                cajaOut.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_id)));
                cajaOut.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_cod_barra)));
                cajaOut.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_idsede)));
                cajaOut.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_nomsede)));
                cajaOut.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_idlocal)));
                cajaOut.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_nomlocal)));
                cajaOut.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_tipo)));
                cajaOut.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_acl)));
                cajaOut.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_dia)));
                cajaOut.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_mes)));
                cajaOut.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_anio)));
                cajaOut.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_hora)));
                cajaOut.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_min)));
                cajaOut.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_seg)));
                cajaOut.setCheck_reg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_check_reg)));
                cajaOut.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_nlado)));
                cajaOut.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_estado)));
                cajaOuts.add(cajaOut);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaOuts;
    }

    public ArrayList<CajaOut> getAllCajasOutCompletos(int nroLocal){
        ArrayList<CajaOut> cajaOuts = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajassalida, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);while(cursor.moveToNext()){
                if(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_estado)) == 2){
                    CajaOut cajaOut = new CajaOut();
                    cajaOut.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_id)));
                    cajaOut.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_cod_barra)));
                    cajaOut.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_idsede)));
                    cajaOut.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_nomsede)));
                    cajaOut.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_idlocal)));
                    cajaOut.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_nomlocal)));
                    cajaOut.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_tipo)));
                    cajaOut.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_acl)));
                    cajaOut.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_dia)));
                    cajaOut.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_mes)));
                    cajaOut.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_anio)));
                    cajaOut.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_hora)));
                    cajaOut.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_min)));
                    cajaOut.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_seg)));
                    cajaOut.setCheck_reg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_check_reg)));
                    cajaOut.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_nlado)));
                    cajaOut.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_estado)));
                    cajaOuts.add(cajaOut);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaOuts;
    }

    public ArrayList<CajaOut> getAllCajasOutTransferidos(int nroLocal){
        ArrayList<CajaOut> cajaOuts = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajassalida, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);while(cursor.moveToNext()){
                if(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_estado)) == 3){
                    CajaOut cajaOut = new CajaOut();
                    cajaOut.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_id)));
                    cajaOut.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_cod_barra)));
                    cajaOut.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_idsede)));
                    cajaOut.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_nomsede)));
                    cajaOut.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_idlocal)));
                    cajaOut.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_salida_nomlocal)));
                    cajaOut.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_tipo)));
                    cajaOut.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_acl)));
                    cajaOut.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_dia)));
                    cajaOut.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_mes)));
                    cajaOut.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_anio)));
                    cajaOut.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_hora)));
                    cajaOut.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_min)));
                    cajaOut.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_fecha_reg_seg)));
                    cajaOut.setCheck_reg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_check_reg)));
                    cajaOut.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_nlado)));
                    cajaOut.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_salida_estado)));
                    cajaOuts.add(cajaOut);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaOuts;
    }

    public Asistencia getAsistenciaxDni(String dni){
        Asistencia asistencia = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia,
                    null,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                asistencia = new Asistencia();
                asistencia.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_id)));
                asistencia.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_dni)));
                asistencia.setIdnacional(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_idnacional)));
                asistencia.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ccdd)));
                asistencia.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_idsede)));
                asistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_sede)));
                asistencia.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_idlocal)));
                asistencia.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local)));
                asistencia.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_direccion)));
                asistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombres)));
                asistencia.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ape_materno)));
                asistencia.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ape_paterno)));
                asistencia.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_naula)));
                asistencia.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_discapacidad)));
                asistencia.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_prioridad)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistencia;
    }

//    public ArrayList<RegistroAsistencia> filtrarMarcoAsistencia(int idLocal){
//        ArrayList<RegistroAsistencia> registroAsistencias = new ArrayList<>();
//        String[] whereArgs = new String[]{String.valueOf(idLocal)};
//        Cursor cursor = null;
//        try{
//            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia,
//                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
//            while(cursor.moveToNext()){
//                RegistroAsistencia registroAsistencia = new RegistroAsistencia();
//                registroAsistencia.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_id)));
//                registroAsistencia.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_dni)));
//                registroAsistencia.setIdnacional(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_idnacional)));
//                registroAsistencia.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ccdd)));
//                registroAsistencia.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_idsede)));
//                registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_sede)));
//                registroAsistencia.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_idlocal)));
//                registroAsistencia.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_local)));
//                registroAsistencia.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_direccion)));
//                registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombres)));
//                registroAsistencia.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ape_materno)));
//                registroAsistencia.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ape_paterno)));
//                registroAsistencia.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_naula)));
//                registroAsistencia.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_discapacidad)));
//                registroAsistencia.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_prioridad)));
//                registroAsistencias.add(registroAsistencia);
//            }
//        }finally{
//            if(cursor != null) cursor.close();
//        }
//        return registroAsistencias;
//    }

    public void insertarAsistenciaLocal(AsistenciaLocal asistenciaLocal){
        ContentValues contentValues = asistenciaLocal.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablaasistencialocal,null,contentValues);
    }

    public AsistenciaLocal getAsistenciaLocal(String dni){
        AsistenciaLocal asistenciaLocal = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencialocal,
                    null,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                asistenciaLocal = new AsistenciaLocal();
                asistenciaLocal.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_id)));
                asistenciaLocal.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_dni)));
                asistenciaLocal.setIdnacional(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_idnacional)));
                asistenciaLocal.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_ccdd)));
                asistenciaLocal.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_idsede)));
                asistenciaLocal.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_sede)));
                asistenciaLocal.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_idlocal)));
                asistenciaLocal.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_local)));
                asistenciaLocal.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_direccion)));
                asistenciaLocal.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_nombres)));
                asistenciaLocal.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_ape_materno)));
                asistenciaLocal.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_ape_paterno)));
                asistenciaLocal.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_naula)));
                asistenciaLocal.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_discapacidad)));
                asistenciaLocal.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_prioridad)));
                asistenciaLocal.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_dia)));
                asistenciaLocal.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_mes)));
                asistenciaLocal.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_anio)));
                asistenciaLocal.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_hora)));
                asistenciaLocal.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_min)));
                asistenciaLocal.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_seg)));
                asistenciaLocal.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_estado)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaLocal;
    }

    public ArrayList<AsistenciaLocal> getAllAsistenciaLocal(int idLocal){
        ArrayList<AsistenciaLocal> asistenciaLocals = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencialocal,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                AsistenciaLocal asistenciaLocal = new AsistenciaLocal();
                asistenciaLocal.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_id)));
                asistenciaLocal.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_dni)));
                asistenciaLocal.setIdnacional(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_idnacional)));
                asistenciaLocal.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_ccdd)));
                asistenciaLocal.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_idsede)));
                asistenciaLocal.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_sede)));
                asistenciaLocal.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_idlocal)));
                asistenciaLocal.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_local)));
                asistenciaLocal.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_direccion)));
                asistenciaLocal.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_nombres)));
                asistenciaLocal.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_ape_materno)));
                asistenciaLocal.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_ape_paterno)));
                asistenciaLocal.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_naula)));
                asistenciaLocal.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_discapacidad)));
                asistenciaLocal.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_prioridad)));
                asistenciaLocal.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_dia)));
                asistenciaLocal.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_mes)));
                asistenciaLocal.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_anio)));
                asistenciaLocal.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_hora)));
                asistenciaLocal.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_min)));
                asistenciaLocal.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_seg)));
                asistenciaLocal.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_estado)));
                asistenciaLocals.add(asistenciaLocal);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaLocals;
    }

    public ArrayList<AsistenciaLocal> getAsistenciaLocalSinEnviar(int idLocal){
        ArrayList<AsistenciaLocal> asistenciaLocals = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal),"0"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencialocal,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND "+ SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                AsistenciaLocal asistenciaLocal = new AsistenciaLocal();
                asistenciaLocal.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_id)));
                asistenciaLocal.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_dni)));
                asistenciaLocal.setIdnacional(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_idnacional)));
                asistenciaLocal.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_ccdd)));
                asistenciaLocal.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_idsede)));
                asistenciaLocal.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_sede)));
                asistenciaLocal.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_idlocal)));
                asistenciaLocal.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_local)));
                asistenciaLocal.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_direccion)));
                asistenciaLocal.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_nombres)));
                asistenciaLocal.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_ape_materno)));
                asistenciaLocal.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_ape_paterno)));
                asistenciaLocal.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_naula)));
                asistenciaLocal.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_discapacidad)));
                asistenciaLocal.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_local_prioridad)));
                asistenciaLocal.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_dia)));
                asistenciaLocal.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_mes)));
                asistenciaLocal.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_anio)));
                asistenciaLocal.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_hora)));
                asistenciaLocal.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_min)));
                asistenciaLocal.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_fecha_seg)));
                asistenciaLocal.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_local_estado)));
                asistenciaLocals.add(asistenciaLocal);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaLocals;
    }

    public void insertarAsistenciaAula(AsistenciaAula asistenciaAula){
        ContentValues contentValues = asistenciaAula.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablaasistenciaaula,null,contentValues);
    }

    public AsistenciaAula getAsistenciaAula(String dni, int nroAula){
        AsistenciaAula asistenciaAula = null;
        String[] whereArgs = new String[]{dni, String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciaaula,
                    null,SQLConstantes.WHERE_CLAUSE_DNI +" AND "
                            + SQLConstantes.WHERE_CLAUSE_NRO_AULA,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                asistenciaAula = new AsistenciaAula();
                asistenciaAula.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_id)));
                asistenciaAula.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_dni)));
                asistenciaAula.setIdnacional(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_idnacional)));
                asistenciaAula.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_ccdd)));
                asistenciaAula.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_idsede)));
                asistenciaAula.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_sede)));
                asistenciaAula.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_idlocal)));
                asistenciaAula.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_local)));
                asistenciaAula.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_direccion)));
                asistenciaAula.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_nombres)));
                asistenciaAula.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_ape_materno)));
                asistenciaAula.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_ape_paterno)));
                asistenciaAula.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_naula)));
                asistenciaAula.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_discapacidad)));
                asistenciaAula.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_prioridad)));
                asistenciaAula.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_dia)));
                asistenciaAula.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_mes)));
                asistenciaAula.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_anio)));
                asistenciaAula.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_hora)));
                asistenciaAula.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_min)));
                asistenciaAula.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_seg)));
                asistenciaAula.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_estado)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaAula;
    }

    public ArrayList<AsistenciaAula> getAllAsistenciaAula(int idLocal, int nroAula){
        ArrayList<AsistenciaAula> asistenciaAulas = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal),String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciaaula,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND "
                            + SQLConstantes.WHERE_CLAUSE_NRO_AULA,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                AsistenciaAula asistenciaAula = new AsistenciaAula();
                asistenciaAula.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_id)));
                asistenciaAula.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_dni)));
                asistenciaAula.setIdnacional(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_idnacional)));
                asistenciaAula.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_ccdd)));
                asistenciaAula.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_idsede)));
                asistenciaAula.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_sede)));
                asistenciaAula.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_idlocal)));
                asistenciaAula.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_local)));
                asistenciaAula.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_direccion)));
                asistenciaAula.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_nombres)));
                asistenciaAula.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_ape_materno)));
                asistenciaAula.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_ape_paterno)));
                asistenciaAula.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_naula)));
                asistenciaAula.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_discapacidad)));
                asistenciaAula.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_prioridad)));
                asistenciaAula.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_dia)));
                asistenciaAula.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_mes)));
                asistenciaAula.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_anio)));
                asistenciaAula.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_hora)));
                asistenciaAula.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_min)));
                asistenciaAula.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_seg)));
                asistenciaAula.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_estado)));
                asistenciaAulas.add(asistenciaAula);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaAulas;
    }

    public ArrayList<AsistenciaAula> getAsistenciaAulaSinEnviar(int idLocal, int nroAula){
        ArrayList<AsistenciaAula> asistenciaAulas = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal),String.valueOf(nroAula),"0"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencialocal,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND "
                            + SQLConstantes.WHERE_CLAUSE_NRO_AULA + " AND "
                            + SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                AsistenciaAula asistenciaAula = new AsistenciaAula();
                asistenciaAula.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_id)));
                asistenciaAula.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_dni)));
                asistenciaAula.setIdnacional(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_idnacional)));
                asistenciaAula.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_ccdd)));
                asistenciaAula.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_idsede)));
                asistenciaAula.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_sede)));
                asistenciaAula.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_idlocal)));
                asistenciaAula.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_local)));
                asistenciaAula.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_direccion)));
                asistenciaAula.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_nombres)));
                asistenciaAula.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_ape_materno)));
                asistenciaAula.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_ape_paterno)));
                asistenciaAula.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_naula)));
                asistenciaAula.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_discapacidad)));
                asistenciaAula.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asis_aula_prioridad)));
                asistenciaAula.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_dia)));
                asistenciaAula.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_mes)));
                asistenciaAula.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_anio)));
                asistenciaAula.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_hora)));
                asistenciaAula.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_min)));
                asistenciaAula.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_fecha_seg)));
                asistenciaAula.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asis_aula_estado)));
                asistenciaAulas.add(asistenciaAula);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaAulas;
    }



    public void actualizarAsistenciaLocal(String dni, ContentValues valores){
        String[] whereArgs = new String[]{dni};
        sqLiteDatabase.update(SQLConstantes.tablaasistencialocal,valores,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs);
    }

    public void actualizarAsistenciaAula(String dni, ContentValues valores){
        String[] whereArgs = new String[]{dni};
        sqLiteDatabase.update(SQLConstantes.tablaasistenciaaula,valores,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs);
    }

    public void actualizarAsistenciaLocalSubido(String dni){
        String[] whereArgs = new String[]{dni};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.asis_local_estado,1);
        sqLiteDatabase.update(SQLConstantes.tablaasistencialocal,contentValues,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs);
    }
    public void actualizarAsistenciaAulaSubido(String dni){
        String[] whereArgs = new String[]{dni};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.asis_aula_estado,1);
        sqLiteDatabase.update(SQLConstantes.tablaasistenciaaula,contentValues,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs);
    }

    public ArrayList<String> getArrayAulas(int nroLocal){
        ArrayList<String> aulas = new ArrayList<>();
        String[] whereArgs = new String[]{Integer.toString(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaaulas,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL, whereArgs,null,null,"naula ASC");
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
            cursor = sqLiteDatabase.query(SQLConstantes.tablaaulas,
                    null, SQLConstantes.WHERE_CLAUSE_NOMBRE_AULA + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ID_LOCAL
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

    //----------------------------------------------------------------------------


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

    public void insertarListado(Listado listado){
        ContentValues contentValues = listado.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablalistados,null,contentValues);
    }

    public void actualizarListado(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablalistados,valores,SQLConstantes.WHERE_CLAUSE_CODIGO_PAGINA,whereArgs);
    }

    public void actualizarListadoSubido(String codigo){
        String[] whereArgs = new String[]{codigo};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.listado_subido,1);
        sqLiteDatabase.update(SQLConstantes.tablalistados,contentValues,SQLConstantes.WHERE_CLAUSE_CODIGO_PAGINA,whereArgs);
    }

    public Listado getListado(String codPagina){
        Listado listado = null;
        String[] whereArgs = new String[]{codPagina};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistados, null,
                    SQLConstantes.WHERE_CLAUSE_CODIGO_PAGINA
                    ,whereArgs,null,null,null);

            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                listado = new Listado();
                listado.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_id)));
                listado.setCodigo_pagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_codigo_pagina)));
                listado.setNro_postulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_nro_postulantes)));
                listado.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_id_local)));
                listado.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_nombre_local)));
                listado.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_sede)));
                listado.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_aula)));
                listado.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_dia)));
                listado.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_mes)));
                listado.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_anio)));
                listado.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_hora)));
                listado.setMinuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_minuto)));
                listado.setSubido(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_subido)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return listado;
    }

    public int getNroPostulantesListado(String codPagina){
        int nroPostulantes = 0;
        String[] whereArgs = new String[]{codPagina};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablanacional, null,
                    SQLConstantes.WHERE_CLAUSE_CODIGO_PAGINA
                    ,whereArgs,null,null,null);
            if(cursor!= null) nroPostulantes = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return nroPostulantes;
    }

    public ArrayList<Listado> getAllListados(int nroLocal, int nroAula){
        ArrayList<Listado> listados = new ArrayList<Listado>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistados, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA
                    ,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                Listado listado = new Listado();
                listado.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_id)));
                listado.setCodigo_pagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_codigo_pagina)));
                listado.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_id_local)));
                listado.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_nombre_local)));
                listado.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_sede)));
                listado.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_aula)));
                listado.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_dia)));
                listado.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_mes)));
                listado.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_anio)));
                listado.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_hora)));
                listado.setMinuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_minuto)));
                listado.setNro_postulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_nro_postulantes)));
                listado.setSubido(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_subido)));
                listados.add(listado);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return listados;
    }

    public ArrayList<Listado> getAllListadosSinEnviar(int nroLocal, int nroAula){
        ArrayList<Listado> listados = new ArrayList<Listado>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistados, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA
                    ,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                if (cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_subido)) == 0){
                    Listado listado = new Listado();
                    listado.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_id)));
                    listado.setCodigo_pagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_codigo_pagina)));
                    listado.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_id_local)));
                    listado.setLocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_nombre_local)));
                    listado.setNro_postulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_nro_postulantes)));
                    listado.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_sede)));
                    listado.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.listado_aula)));
                    listado.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_dia)));
                    listado.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_mes)));
                    listado.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_anio)));
                    listado.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_hora)));
                    listado.setMinuto(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_minuto)));
                    listado.setSubido(cursor.getInt(cursor.getColumnIndex(SQLConstantes.listado_subido)));
                    listados.add(listado);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return listados;
    }

    public void insertarResumenAsistencia(ResumenAsistencia resumenAsistencia){
        ContentValues contentValues = resumenAsistencia.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablaresumenasistencia,null,contentValues);
    }

    public void actualizarResumenAsistencia(int id_local,int id_aula, ContentValues valores){
        String[] whereArgs = new String[]{String.valueOf(id_local),String.valueOf(id_aula)};
        sqLiteDatabase.update(SQLConstantes.tablaresumenasistencia,valores,
                SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                SQLConstantes.WHERE_CLAUSE_ID_AULA,whereArgs);
    }

    public void actualizarResumenAsistenciaAula(int id_local,int id_aula){
        String[] whereArgs = new String[]{String.valueOf(id_local),String.valueOf(id_aula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaresumenasistencia, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ID_AULA
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                int id = cursor.getInt(cursor.getColumnIndex(SQLConstantes.resumen_asistencia_id));
                int numeroAsistenciasAula = cursor.getInt(cursor.getColumnIndex(SQLConstantes.resumen_asistencia_nro_asisaula));
                numeroAsistenciasAula++;
                ContentValues contentValues = new ContentValues();
                contentValues.put(SQLConstantes.resumen_asistencia_nro_asisaula,numeroAsistenciasAula);
                sqLiteDatabase.update(SQLConstantes.tablaresumenasistencia,contentValues,SQLConstantes.WHERE_CLAUSE_ID,new String[]{String.valueOf(id)});
            }
        }finally{
            if(cursor != null) cursor.close();
        }

    }

    public void actualizarResumenAsistenciaLocal(int id_local,int id_aula){
        String[] whereArgs = new String[]{String.valueOf(id_local),String.valueOf(id_aula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaresumenasistencia, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ID_AULA
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                int id = cursor.getInt(cursor.getColumnIndex(SQLConstantes.resumen_asistencia_id));
                int numeroAsistenciasLocal = cursor.getInt(cursor.getColumnIndex(SQLConstantes.resumen_asistencia_nro_asislocal));
                numeroAsistenciasLocal++;
                ContentValues contentValues = new ContentValues();
                contentValues.put(SQLConstantes.resumen_asistencia_nro_asislocal,numeroAsistenciasLocal);
                sqLiteDatabase.update(SQLConstantes.tablaresumenasistencia,contentValues,SQLConstantes.WHERE_CLAUSE_ID,new String[]{String.valueOf(id)});
            }
        }finally{
            if(cursor != null) cursor.close();
        }

    }

    public void insertarResumenInventario(ResumenInventario resumenInventario){
        ContentValues contentValues = resumenInventario.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablaresumeninventario,null,contentValues);
    }

    public void actualizarResumenInventario(int id_local,int id_aula, ContentValues valores){
        String[] whereArgs = new String[]{String.valueOf(id_local),String.valueOf(id_aula)};
        sqLiteDatabase.update(SQLConstantes.tablaresumeninventario,valores,
                SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                        SQLConstantes.WHERE_CLAUSE_ID_AULA,whereArgs);
    }

    public void actualizarResumenInventarioFicha(int id_local,int id_aula){
        String[] whereArgs = new String[]{String.valueOf(id_local),String.valueOf(id_aula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaresumeninventario, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ID_AULA
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                int id = cursor.getInt(cursor.getColumnIndex(SQLConstantes.resumen_inventario_id));
                int numeroInventarioFichas = cursor.getInt(cursor.getColumnIndex(SQLConstantes.resumen_inventario_nro_invfichas));
                numeroInventarioFichas++;
                ContentValues contentValues = new ContentValues();
                contentValues.put(SQLConstantes.resumen_inventario_nro_invfichas,numeroInventarioFichas);
                sqLiteDatabase.update(SQLConstantes.tablaresumeninventario,contentValues,SQLConstantes.WHERE_CLAUSE_ID,new String[]{String.valueOf(id)});
            }
        }finally{
            if(cursor != null) cursor.close();
        }

    }

    public void actualizarResumenInventarioCartilla(int id_local,int id_aula){
        String[] whereArgs = new String[]{String.valueOf(id_local),String.valueOf(id_aula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaresumeninventario, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ID_AULA
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                int id = cursor.getInt(cursor.getColumnIndex(SQLConstantes.resumen_inventario_id));
                int numeroInventarioCartillas = cursor.getInt(cursor.getColumnIndex(SQLConstantes.resumen_inventario_nro_invcartillas));
                numeroInventarioCartillas++;
                ContentValues contentValues = new ContentValues();
                contentValues.put(SQLConstantes.resumen_inventario_nro_invcartillas,numeroInventarioCartillas);
                sqLiteDatabase.update(SQLConstantes.tablaresumeninventario,contentValues,SQLConstantes.WHERE_CLAUSE_ID,new String[]{String.valueOf(id)});
            }
        }finally{
            if(cursor != null) cursor.close();
        }
    }

    public void actualizarResumenInventarioListados(int id_local,int id_aula){
        String[] whereArgs = new String[]{String.valueOf(id_local),String.valueOf(id_aula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaresumeninventario, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ID_AULA
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                int id = cursor.getInt(cursor.getColumnIndex(SQLConstantes.resumen_inventario_id));
                int numeroInventarioListados = cursor.getInt(cursor.getColumnIndex(SQLConstantes.resumen_inventario_nro_invlistados));
                numeroInventarioListados++;
                ContentValues contentValues = new ContentValues();
                contentValues.put(SQLConstantes.resumen_inventario_nro_invlistados,numeroInventarioListados);
                sqLiteDatabase.update(SQLConstantes.tablaresumeninventario,contentValues,SQLConstantes.WHERE_CLAUSE_ID,new String[]{String.valueOf(id)});
            }
        }finally{
            if(cursor != null) cursor.close();
        }
    }

    public void insertarResumenTotal(ResumenTotal resumenTotal){
        ContentValues contentValues = resumenTotal.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablaresumentotal,null,contentValues);
    }

    public void actualizarResumenTotal(int id, ContentValues valores){
        String[] whereArgs = new String[]{String.valueOf(id)};
        sqLiteDatabase.update(SQLConstantes.tablaresumentotal,valores, SQLConstantes.WHERE_CLAUSE_ID ,whereArgs);
    }

    public void actualizarCantidadResumenTotal(int idTotal){
        String[] whereArgs = new String[]{String.valueOf(idTotal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaresumentotal, null,
                    SQLConstantes.WHERE_CLAUSE_ID,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                int numeroResumenesTotal = cursor.getInt(cursor.getColumnIndex(SQLConstantes.resumen_total_cantidad));
                numeroResumenesTotal++;
                ContentValues contentValues = new ContentValues();
                contentValues.put(SQLConstantes.resumen_total_cantidad,numeroResumenesTotal);
                sqLiteDatabase.update(SQLConstantes.tablaresumeninventario,contentValues,SQLConstantes.WHERE_CLAUSE_ID,whereArgs);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
    }


    public void deleteAllElementosFromTabla(String nombreTabla){
        sqLiteDatabase.execSQL("delete from "+ nombreTabla);
    }

}
