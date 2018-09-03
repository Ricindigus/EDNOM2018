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

    public ArrayList<CajaIn> getAllCajaInListadoCompletos(int nroLocal){
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

    //----------------------------------------------------------------------------





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

    public Nacional getNacionalxCodPagina(String codPagina){
        Nacional nacional = null;
        String[] whereArgs = new String[]{codPagina};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablanacional,
                    null,SQLConstantes.WHERE_CLAUSE_CODIGO_PAGINA,whereArgs,null,null,null);
            boolean obtenido = false;
            while (cursor.moveToNext() && !obtenido){
                nacional = new Nacional();
                nacional.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_sede)));
                nacional.setNro_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_nro_local)));
                nacional.setLocal_aplicacion(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_local_aplicacion)));
                nacional.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_aula)));
                nacional.setCodigo_pagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_codigo_pagina)));
                nacional.setId_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_id_local)));
                nacional.setId_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.nacional_id_aula)));
                nacional.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_direccion)));
                obtenido = true;
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
