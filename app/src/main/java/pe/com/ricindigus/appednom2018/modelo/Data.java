package pe.com.ricindigus.appednom2018.modelo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_USUARIO_ACTUAL);
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_HISTORIAL_USUARIOS);
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CAJAS_REGISTRADAS);
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIAS_REGISTRADAS);
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_FICHAS_REGISTRADAS);
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_LISTADOS_REGISTRADOS);
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CUADERNILLOS_REGISTRADOS);
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIAS_RA_REGISTRADAS);

                sqLiteDatabase.close();
            }catch (IOException e){
                throw new Error("Error: copiando base de datos");
            }
        }

    }


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
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_USUARIO_ACTUAL);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_HISTORIAL_USUARIOS);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CAJAS_REGISTRADAS);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIAS_REGISTRADAS);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_FICHAS_REGISTRADAS);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_LISTADOS_REGISTRADOS);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CUADERNILLOS_REGISTRADOS);
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_ASISTENCIAS_RA_REGISTRADAS);
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
                usuario.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.usuario_local_idlocal)));
                usuario.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_local_nom_local)));
                usuario.setNaulas(cursor.getInt(cursor.getColumnIndex(SQLConstantes.usuario_local_naulas)));
                usuario.setIdsede(cursor.getInt(cursor.getColumnIndex(SQLConstantes.usuario_local_idsede)));
                usuario.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_local_nom_sede)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return usuario;
    }


    public void guardarClave(String clave){
        long numero = DatabaseUtils.queryNumEntries(sqLiteDatabase, SQLConstantes.tablausuarioactual);
        if(numero == 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put(SQLConstantes.usuario_actual_id,1);
            contentValues.put(SQLConstantes.usuario_actual_clave,clave);
            sqLiteDatabase.insert(SQLConstantes.tablausuarioactual,null,contentValues);
        }else{
            ContentValues contentValues = new ContentValues();
            contentValues.put(SQLConstantes.usuario_actual_clave,clave);
            String[] whereArgs = new String[]{"1"};
            sqLiteDatabase.update(SQLConstantes.tablausuarioactual,contentValues,SQLConstantes.WHERE_CLAUSE_ID,whereArgs);
        }
    }


    public UsuarioActual getUsuarioActual(){
        UsuarioActual usuario = null;
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablausuarioactual,
                    null,SQLConstantes.WHERE_CLAUSE_ID,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                usuario = new UsuarioActual();
                usuario.setClave(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_actual_clave)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return usuario;
    }

    public void insertarHistorialUsuario(String clave){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.historial_usuario_clave,clave);
        sqLiteDatabase.insert(SQLConstantes.tablahistorialusuarios,null,contentValues);
    }

    public boolean existeUsuario(String clave){
        boolean existe = false;
        String[] whereArgs = new String[]{clave};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablahistorialusuarios,
                    null,SQLConstantes.WHERE_CLAUSE_CLAVE,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                existe = true;
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return existe;
    }

    public Version getVersion(){
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        Version version = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaversion, null,"_id=?",whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                version = new Version();
                version.setNumero(cursor.getInt(cursor.getColumnIndex(SQLConstantes.version_numero)));
                version.setNombre(cursor.getString(cursor.getColumnIndex(SQLConstantes.version_nombre)));
                version.setColeccion_asistencia(cursor.getString(cursor.getColumnIndex(SQLConstantes.version_coleccion_asistencia)));
                version.setColeccion_cajas(cursor.getString(cursor.getColumnIndex(SQLConstantes.version_coleccion_cajas)));
                version.setColeccion_inventario(cursor.getString(cursor.getColumnIndex(SQLConstantes.version_coleccion_inventario)));
                version.setAsis_hora_inicio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.version_asis_hora_inicio)));
                version.setAsis_min_inicio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.version_asis_min_inicio)));
                version.setAsis_hora_fin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.version_asis_hora_fin)));
                version.setAsis_min_fin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.version_asis_min_fin)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return version;
    }

    public void actualizarHoraAsisDocenteVersion(int hi, int mi, int hf, int mf){
        String[] whereArgs = new String[]{"1"};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.version_asis_hora_inicio,hi);
        contentValues.put(SQLConstantes.version_asis_hora_fin,hf);
        contentValues.put(SQLConstantes.version_asis_min_inicio,mi);
        contentValues.put(SQLConstantes.version_asis_min_fin,mf);
        contentValues.put(SQLConstantes.version_asis_min_fin,mf);
        contentValues.put(SQLConstantes.version_restriccion,1);
        sqLiteDatabase.update(SQLConstantes.tablaversion,contentValues,"_id=?",whereArgs);
    }

    public void actualizarRestriccionHoraVersion(){
        String[] whereArgs = new String[]{"1"};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.version_restriccion,0);
        sqLiteDatabase.update(SQLConstantes.tablaversion,contentValues,"_id=?",whereArgs);
    }


    public String getNombreApp(){
        String nombre = "";
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query("version", null,"_id=?",whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return nombre;
    }

    public String getNombreColeccionCajas(){
        String nombre = "";
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query("version", null,"_id=?",whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                nombre = cursor.getString(cursor.getColumnIndex("coleccion_cajas"));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return nombre;
    }

    public String getNombreColeccionInventario(){
        String nombre = "";
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query("version", null,"_id=?",whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                nombre = cursor.getString(cursor.getColumnIndex("coleccion_inventario"));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return nombre;
    }

    public String getNombreColeccionAsistencia(){
        String nombre = "";
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query("version", null,"_id=?",whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                nombre = cursor.getString(cursor.getColumnIndex("coleccion_asistencia"));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return nombre;
    }

    public String getNombreColeccionAsistenciaRA(){
        String nombre = "";
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query("version", null,"_id=?",whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                nombre = cursor.getString(cursor.getColumnIndex("coleccion_asistencia_ra"));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return nombre;
    }

    public int getNumeroApp(){
        int numero = 0;
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query("version", null,"_id=?",whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                numero = cursor.getInt(cursor.getColumnIndex("numero"));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public ArrayList<String> getArrayAulasRegistro(int nroLocal){
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

    public ArrayList<String> getArrayAulasListado(int nroLocal){
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

    /**
     * --------------------------------QUERYS CAJAS-----------------------------------
     * */

    public Caja getCaja(String codBarra){
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
                caja.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_ccdd)));
                caja.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_departamento)));
                caja.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_idnacional)));
                caja.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_idsede)));
                caja.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_nom_sede)));
                caja.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_idlocal)));
                caja.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_nom_local)));
                caja.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_tipo)));
                caja.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_nlado)));
                caja.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajas_acl)));
                caja.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajas_direccion)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return caja;
    }

    public long getNumeroItemsCajasReg(){
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, SQLConstantes.tablacajasreg);
    }

    public void insertarCajaReg(CajaReg cajaReg){
        ContentValues contentValues = cajaReg.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablacajasreg,null,contentValues);
    }

    public void actualizarCajaReg(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablacajasreg,valores,SQLConstantes.WHERE_CLAUSE_COD_BARRA,whereArgs);
    }

    public void actualizarEntradaLadoCajaLeida(String codigo){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.cajasreg_check_entrada,1);
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablacajasreg,contentValues,SQLConstantes.WHERE_CLAUSE_COD_BARRA,whereArgs);
    }

    public void actualizarCajaRegSubidoEntrada(String codigo){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.cajasreg_estado_entrada,3);
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablacajasreg,contentValues,SQLConstantes.WHERE_CLAUSE_COD_BARRA,whereArgs);
    }

    public void actualizarCajaRegSubidoSalida(String codigo){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.cajasreg_estado_salida,3);
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablacajasreg,contentValues,SQLConstantes.WHERE_CLAUSE_COD_BARRA,whereArgs);
    }

    public CajaReg getCajaReg(String codBarra,int idlocal){
        CajaReg cajaReg = null;
        String[] whereArgs = new String[]{codBarra, String.valueOf(idlocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg,
                    null,SQLConstantes.WHERE_CLAUSE_COD_BARRA + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                cajaReg = new CajaReg();
                cajaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_id)));
                cajaReg.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_cod_barra)));
                cajaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_ccdd)));
                cajaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_departamento)));
                cajaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idnacional)));
                cajaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_idsede)));
                cajaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_sede)));
                cajaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idlocal)));
                cajaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_local)));
                cajaReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_tipo)));
                cajaReg.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_nlado)));
                cajaReg.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_acl)));
                cajaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_direccion)));
                cajaReg.setDia_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_dia_entrada)));
                cajaReg.setMes_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_mes_entrada )));
                cajaReg.setAnio_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_anio_entrada )));
                cajaReg.setHora_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_hora_entrada)));
                cajaReg.setMin_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_min_entrada )));
                cajaReg.setSeg_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_seg_entrada )));
                cajaReg.setEstado_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_estado_entrada)));
                cajaReg.setDia_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_dia_salida)));
                cajaReg.setMes_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_mes_salida)));
                cajaReg.setAnio_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_anio_salida )));
                cajaReg.setHora_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_hora_salida)));
                cajaReg.setMin_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_min_salida)));
                cajaReg.setSeg_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_seg_salida)));
                cajaReg.setEstado_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_estado_salida)));
                cajaReg.setCheck_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_check_entrada)));
                cajaReg.setCheck_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_check_salida)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaReg;
    }

    public ArrayList<CajaReg> filtrarMarcoCajas(int nroLocal){
        ArrayList<CajaReg> cajaRegs = new ArrayList<CajaReg>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajas, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                CajaReg cajaReg = new CajaReg();
                cajaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_id)));
                cajaReg.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_cod_barra)));
                cajaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_ccdd)));
                cajaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_departamento)));
                cajaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idnacional)));
                cajaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_idsede)));
                cajaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_sede)));
                cajaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idlocal)));
                cajaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_local)));
                cajaReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_tipo)));
                cajaReg.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_nlado)));
                cajaReg.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_acl)));
                cajaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_direccion)));
                cajaRegs.add(cajaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaRegs;
    }

    public ArrayList<CajaReg> getListadoCajasEntrada(int nroLocal){
        ArrayList<CajaReg> cajaRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " + SQLConstantes.WHERE_CLAUSE_NRO_LADO
                    ,whereArgs,null,null,"estado_entrada ASC");
            while (cursor.moveToNext()){
                CajaReg cajaReg = new CajaReg();
                cajaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_id)));
                cajaReg.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_cod_barra)));
                cajaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_ccdd)));
                cajaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_departamento)));
                cajaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idnacional)));
                cajaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_idsede)));
                cajaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_sede)));
                cajaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idlocal)));
                cajaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_local)));
                cajaReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_tipo)));
                cajaReg.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_nlado)));
                cajaReg.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_acl)));
                cajaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_direccion)));
                cajaReg.setDia_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_dia_entrada)));
                cajaReg.setMes_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_mes_entrada)));
                cajaReg.setAnio_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_anio_entrada)));
                cajaReg.setHora_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_hora_entrada)));
                cajaReg.setMin_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_min_entrada)));
                cajaReg.setSeg_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_seg_entrada)));
                cajaReg.setEstado_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_estado_entrada)));
                cajaReg.setCheck_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_check_entrada)));
                cajaRegs.add(cajaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaRegs;
    }



    public ArrayList<CajaReg> getListadoCajasSalida(int nroLocal){
        ArrayList<CajaReg> cajaRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " + SQLConstantes.WHERE_CLAUSE_NRO_LADO
                    ,whereArgs,null,null,"estado_salida ASC");
            while (cursor.moveToNext()){
                CajaReg cajaReg = new CajaReg();
                cajaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_id)));
                cajaReg.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_cod_barra)));
                cajaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_ccdd)));
                cajaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_departamento)));
                cajaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idnacional)));
                cajaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_idsede)));
                cajaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_sede)));
                cajaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idlocal)));
                cajaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_local)));
                cajaReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_tipo)));
                cajaReg.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_nlado)));
                cajaReg.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_acl)));
                cajaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_direccion)));
                cajaReg.setDia_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_dia_salida)));
                cajaReg.setMes_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_mes_salida)));
                cajaReg.setAnio_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_anio_salida)));
                cajaReg.setHora_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_hora_salida)));
                cajaReg.setMin_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_min_salida)));
                cajaReg.setSeg_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_seg_salida)));
                cajaReg.setEstado_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_estado_salida)));
                cajaReg.setCheck_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_check_salida)));
                cajaRegs.add(cajaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaRegs;
    }

    public ArrayList<CajaReg> getListCajasEntradaCompletas(int nroLocal){
        ArrayList<CajaReg> cajaRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","2"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_ENTRADA,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                CajaReg cajaReg = new CajaReg();
                cajaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_id)));
                cajaReg.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_cod_barra)));
                cajaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_ccdd)));
                cajaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_departamento)));
                cajaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idnacional)));
                cajaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_idsede)));
                cajaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_sede)));
                cajaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idlocal)));
                cajaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_local)));
                cajaReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_tipo)));
                cajaReg.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_nlado)));
                cajaReg.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_acl)));
                cajaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_direccion)));
                cajaReg.setDia_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_dia_entrada)));
                cajaReg.setMes_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_mes_entrada)));
                cajaReg.setAnio_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_anio_entrada)));
                cajaReg.setHora_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_hora_entrada)));
                cajaReg.setMin_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_min_entrada)));
                cajaReg.setSeg_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_seg_entrada)));
                cajaReg.setEstado_entrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_estado_entrada)));
                cajaRegs.add(cajaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaRegs;
    }

    public ArrayList<CajaReg> getListCajasSalidaCompletas(int nroLocal){
        ArrayList<CajaReg> cajaRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","2"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_SALIDA,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                CajaReg cajaReg = new CajaReg();
                cajaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_id)));
                cajaReg.setCod_barra_caja(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_cod_barra)));
                cajaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_ccdd)));
                cajaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_departamento)));
                cajaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idnacional)));
                cajaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_idsede)));
                cajaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_sede)));
                cajaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_idlocal)));
                cajaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_nom_local)));
                cajaReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_tipo)));
                cajaReg.setNlado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_nlado)));
                cajaReg.setAcl(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_acl)));
                cajaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.cajasreg_direccion)));
                cajaReg.setDia_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_dia_salida)));
                cajaReg.setMes_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_mes_salida)));
                cajaReg.setAnio_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_anio_salida)));
                cajaReg.setHora_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_hora_salida)));
                cajaReg.setMin_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_min_salida)));
                cajaReg.setSeg_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_seg_salida)));
                cajaReg.setEstado_salida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.cajasreg_estado_salida)));
                cajaRegs.add(cajaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return cajaRegs;
    }

    public int getNroCajasTotales(int nroLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " + SQLConstantes.WHERE_CLAUSE_NRO_LADO
                    ,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }


    public int getNroCajasEntradaSinRegistrar(int nroLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","0"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_ENTRADA,whereArgs,null,null,null);

            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroCajasSalidaSinRegistrar(int nroLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","0"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_SALIDA,whereArgs,null,null,null);

            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroCajasEntradaIncompletas(int nroLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_ENTRADA,whereArgs,null,null,null);

            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroCajasSalidaIncompletas(int nroLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_SALIDA,whereArgs,null,null,null);

            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroCajasEntradaCompletas(int nroLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","2"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_ENTRADA,whereArgs,null,null,null);

            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }
    public int getNroCajasSalidaCompletas(int nroLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","2"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_SALIDA,whereArgs,null,null,null);

            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroCajasEntradaLeidas(int nroLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            "estado_entrada>?",whereArgs,null,null,null);

            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroCajasSalidaLeidas(int nroLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            "estado_salida>?",whereArgs,null,null,null);

            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroCajasEntradaTransferidos(int nroLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","3"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_ENTRADA,whereArgs,null,null,null);

            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }
    public int getNroCajasSalidaTransferidos(int nroLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal),"1","3"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +" AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_LADO+" AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_SALIDA,whereArgs,null,null,null);

            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroCajasEntradaLeidasxTipo(int nroLocal, int tipo){
        int cantidad = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(tipo),"1","1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +
                            " AND " + SQLConstantes.WHERE_CLAUSE_TIPO_CAJA +
                            " AND " + SQLConstantes.WHERE_CLAUSE_NRO_LADO +
                            " AND " + "estado_entrada>?",whereArgs,null,null,null);
            if(cursor.getCount() > 0) cantidad = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return cantidad;
    }
    public int getNroCajasSalidaLeidasxTipo(int nroLocal, int tipo){
        int cantidad = 0;
        String[] whereArgs = new String[]{String.valueOf(nroLocal), String.valueOf(tipo),"1","1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacajasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL +
                            " AND " + SQLConstantes.WHERE_CLAUSE_TIPO_CAJA +
                            " AND " + SQLConstantes.WHERE_CLAUSE_NRO_LADO +
                            " AND " + "estado_salida>?",whereArgs,null,null,null);
            if(cursor.getCount() > 0) cantidad = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return cantidad;
    }
    /**
     * --------------------------------FIN QUERYS CAJAS -----------------------------------
     * */


    /**
     * --------------------------------QUERYS ASISTENCIA -----------------------------------
     * */
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
                asistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nombres)));
                asistencia.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ape_materno)));
                asistencia.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ape_paterno)));
                asistencia.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_naula)));
                asistencia.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_discapacidad)));
                asistencia.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_prioridad)));
                asistencia.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_idnacional)));
                asistencia.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_idsede)));
                asistencia.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nom_sede)));
                asistencia.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ccdd)));
                asistencia.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_departamento)));
                asistencia.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_idlocal)));
                asistencia.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_nom_local)));
                asistencia.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_direccion)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistencia;
    }

    public AsistenciaRa getAsistenciaRaxDni(String dni){
        AsistenciaRa asistenciaRa = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia_ra,
                    null,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                asistenciaRa = new AsistenciaRa();
                asistenciaRa.setId(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_ra_id)));
                asistenciaRa.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_ccdd)));
                asistenciaRa.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_departamento)));
                asistenciaRa.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_idsede)));
                asistenciaRa.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_nom_sede)));
                asistenciaRa.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_ra_idnacional)));
                asistenciaRa.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_ra_idlocal)));
                asistenciaRa.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_nom_local)));
                asistenciaRa.setRed(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_ra_red)));
                asistenciaRa.setTipo_cargo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_ra_tipo_cargo)));
                asistenciaRa.setNombre_cargo(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_nombre_cargo)));
                asistenciaRa.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_dni)));
                asistenciaRa.setNombres_completos(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_nombres_completos)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaRa;
    }


    public int getNroAsistenciasIdLocal(int idLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            if(cursor!= null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroAsistenciasRaIdLocal(int idLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia_ra,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            if(cursor!= null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public long getNumeroItemsAsistenciaReg(){
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, SQLConstantes.tablaasistenciasreg);
    }

    public long getNumeroItemsAsistenciaRaReg(){
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, SQLConstantes.tablaasistencias_ra_reg);
    }

    public void insertarAsistenciaReg(AsistenciaReg asistenciaReg){
        ContentValues contentValues = asistenciaReg.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablaasistenciasreg,null,contentValues);
    }
    public void actualizarAsistenciaReg(String dni, ContentValues valores){
        String[] whereArgs = new String[]{dni};
        sqLiteDatabase.update(SQLConstantes.tablaasistenciasreg,valores,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs);
    }

    public void insertarAsistenciaRaReg(AsistenciaRaReg asistenciaRaReg){
        ContentValues contentValues = asistenciaRaReg.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablaasistencias_ra_reg,null,contentValues);
    }
    public void actualizarAsistenciaRaReg(String dni, ContentValues valores){
        String[] whereArgs = new String[]{dni};
        sqLiteDatabase.update(SQLConstantes.tablaasistencias_ra_reg,valores,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs);
    }

    public void actualizarAsistenciaRaRegSubido(String dni){
        String[] whereArgs = new String[]{dni};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.asistenciareg_ra_estado,2);
        sqLiteDatabase.update(SQLConstantes.tablaasistencias_ra_reg,contentValues,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs);
    }

    public void actualizarAsistenciaRegLocalSubido(String dni){
        String[] whereArgs = new String[]{dni};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.asistenciareg_estado_local,2);
        sqLiteDatabase.update(SQLConstantes.tablaasistenciasreg,contentValues,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs);
    }

    public void actualizarAsistenciaRegAulaSubido(String dni){
        String[] whereArgs = new String[]{dni};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.asistenciareg_estado_aula,2);
        sqLiteDatabase.update(SQLConstantes.tablaasistenciasreg,contentValues,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs);
    }
    public AsistenciaReg getAsistenciaReg(String dni){
        AsistenciaReg asistenciaReg = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                asistenciaReg = new AsistenciaReg();
                asistenciaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_id)));
                asistenciaReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_dni)));
                asistenciaReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nombres)));
                asistenciaReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_materno)));
                asistenciaReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_paterno)));
                asistenciaReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_naula)));
                asistenciaReg.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_discapacidad)));
                asistenciaReg.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_prioridad)));
                asistenciaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idnacional)));
                asistenciaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_idsede)));
                asistenciaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_sede)));
                asistenciaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ccdd)));
                asistenciaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_departamento)));
                asistenciaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idlocal)));
                asistenciaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_local)));
                asistenciaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_direccion)));
                asistenciaReg.setDia_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_dia_local)));
                asistenciaReg.setMes_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_mes_local)));
                asistenciaReg.setAnio_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_anio_local)));
                asistenciaReg.setHora_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_hora_local)));
                asistenciaReg.setMin_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_min_local)));
                asistenciaReg.setSeg_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_seg_local)));
                asistenciaReg.setEstado_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_estado_local)));
                asistenciaReg.setDia_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_dia_aula)));
                asistenciaReg.setMes_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_mes_aula)));
                asistenciaReg.setAnio_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_anio_aula)));
                asistenciaReg.setHora_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_hora_aula)));
                asistenciaReg.setMin_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_min_aula)));
                asistenciaReg.setSeg_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_seg_aula)));
                asistenciaReg.setEstado_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_estado_aula)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaReg;
    }

    public AsistenciaRaReg getAsistenciaRaReg(String dni){
        AsistenciaRaReg asistenciaRaReg = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencias_ra_reg,
                    null,SQLConstantes.WHERE_CLAUSE_DNI,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                asistenciaRaReg = new AsistenciaRaReg();
                asistenciaRaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_ccdd)));
                asistenciaRaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_departamento)));
                asistenciaRaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_idsede)));
                asistenciaRaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nom_sede)));
                asistenciaRaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_idnacional)));
                asistenciaRaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_idlocal)));
                asistenciaRaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nom_local)));
                asistenciaRaReg.setRed(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_red)));
                asistenciaRaReg.setTipo_cargo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_tipo_cargo)));
                asistenciaRaReg.setNombre_cargo(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nombre_cargo)));
                asistenciaRaReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_dni)));
                asistenciaRaReg.setNombres_completos(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nombres_completos)));
                asistenciaRaReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_dia)));
                asistenciaRaReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_mes)));
                asistenciaRaReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_anio)));
                asistenciaRaReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_hora)));
                asistenciaRaReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_min)));
                asistenciaRaReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_seg)));
                asistenciaRaReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_estado)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaRaReg;
    }

    public ArrayList<AsistenciaReg> filtrarMarcoAsistencia(int idLocal){
        ArrayList<AsistenciaReg> asistenciaRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                AsistenciaReg asistenciaReg = new AsistenciaReg();
                asistenciaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_id)));
                asistenciaReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_dni)));
                asistenciaReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nombres)));
                asistenciaReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_materno)));
                asistenciaReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_paterno)));
                asistenciaReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_naula)));
                asistenciaReg.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_discapacidad)));
                asistenciaReg.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_prioridad)));
                asistenciaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idnacional)));
                asistenciaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_idsede)));
                asistenciaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_sede)));
                asistenciaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ccdd)));
                asistenciaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_departamento)));
                asistenciaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idlocal)));
                asistenciaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_local)));
                asistenciaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_direccion)));
                asistenciaRegs.add(asistenciaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaRegs;
    }

    public ArrayList<AsistenciaRaReg> filtrarMarcoAsistenciaRA(int idLocal){
        ArrayList<AsistenciaRaReg> asistenciaRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencia_ra,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                AsistenciaRaReg asistenciaRaReg = new AsistenciaRaReg();
                asistenciaRaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_ccdd)));
                asistenciaRaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_departamento)));
                asistenciaRaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_idsede)));
                asistenciaRaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_nom_sede)));
                asistenciaRaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_ra_idnacional)));
                asistenciaRaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_ra_idlocal)));
                asistenciaRaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_nom_local)));
                asistenciaRaReg.setRed(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_ra_red)));
                asistenciaRaReg.setTipo_cargo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistencia_ra_tipo_cargo)));
                asistenciaRaReg.setNombre_cargo(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_nombre_cargo)));
                asistenciaRaReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_dni)));
                asistenciaRaReg.setNombres_completos(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistencia_ra_nombres_completos)));
                asistenciaRegs.add(asistenciaRaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaRegs;
    }

    public ArrayList<AsistenciaReg> getListadoAsistenciaLocal(int idLocal){
        ArrayList<AsistenciaReg> asistenciaRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal),"0"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND "
                    + "estado_local>?",whereArgs,null,null,"estado_local ASC");
            while(cursor.moveToNext()){
                AsistenciaReg asistenciaReg = new AsistenciaReg();
                asistenciaReg = new AsistenciaReg();
                asistenciaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_id)));
                asistenciaReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_dni)));
                asistenciaReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nombres)));
                asistenciaReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_materno)));
                asistenciaReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_paterno)));
                asistenciaReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_naula)));
                asistenciaReg.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_discapacidad)));
                asistenciaReg.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_prioridad)));
                asistenciaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idnacional)));
                asistenciaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_idsede)));
                asistenciaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_sede)));
                asistenciaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ccdd)));
                asistenciaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_departamento)));
                asistenciaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idlocal)));
                asistenciaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_local)));
                asistenciaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_direccion)));
                asistenciaReg.setDia_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_dia_local)));
                asistenciaReg.setMes_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_mes_local)));
                asistenciaReg.setAnio_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_anio_local)));
                asistenciaReg.setHora_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_hora_local)));
                asistenciaReg.setMin_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_min_local)));
                asistenciaReg.setSeg_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_seg_local)));
                asistenciaReg.setEstado_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_estado_local)));
                asistenciaRegs.add(asistenciaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaRegs;
    }

    public ArrayList<AsistenciaRaReg> getListadoAsistenciaRA(int idLocal){
        ArrayList<AsistenciaRaReg> asistenciaRaRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencias_ra_reg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,"estado ASC");
            while(cursor.moveToNext()){
                AsistenciaRaReg asistenciaRaReg = new AsistenciaRaReg();
                asistenciaRaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_ccdd)));
                asistenciaRaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_departamento)));
                asistenciaRaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_idsede)));
                asistenciaRaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nom_sede)));
                asistenciaRaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_idnacional)));
                asistenciaRaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_idlocal)));
                asistenciaRaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nom_local)));
                asistenciaRaReg.setRed(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_red)));
                asistenciaRaReg.setTipo_cargo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_tipo_cargo)));
                asistenciaRaReg.setNombre_cargo(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nombre_cargo)));
                asistenciaRaReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_dni)));
                asistenciaRaReg.setNombres_completos(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nombres_completos)));
                asistenciaRaReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_dia)));
                asistenciaRaReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_mes)));
                asistenciaRaReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_anio)));
                asistenciaRaReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_hora)));
                asistenciaRaReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_min)));
                asistenciaRaReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_seg)));
                asistenciaRaReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_estado)));
                asistenciaRaRegs.add(asistenciaRaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaRaRegs;
    }

    public ArrayList<AsistenciaReg> getListadoAsistenciaAula(int idLocal, int nroAula){
        ArrayList<AsistenciaReg> asistenciaRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal),String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA,whereArgs,null,null,"estado_aula ASC");
            while(cursor.moveToNext()){
                AsistenciaReg asistenciaReg = new AsistenciaReg();
                asistenciaReg = new AsistenciaReg();
                asistenciaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_id)));
                asistenciaReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_dni)));
                asistenciaReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nombres)));
                asistenciaReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_materno)));
                asistenciaReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_paterno)));
                asistenciaReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_naula)));
                asistenciaReg.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_discapacidad)));
                asistenciaReg.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_prioridad)));
                asistenciaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idnacional)));
                asistenciaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_idsede)));
                asistenciaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_sede)));
                asistenciaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ccdd)));
                asistenciaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_departamento)));
                asistenciaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idlocal)));
                asistenciaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_local)));
                asistenciaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_direccion)));
                asistenciaReg.setDia_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_dia_aula)));
                asistenciaReg.setMes_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_mes_aula)));
                asistenciaReg.setAnio_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_anio_aula)));
                asistenciaReg.setHora_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_hora_aula)));
                asistenciaReg.setMin_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_min_aula)));
                asistenciaReg.setSeg_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_seg_aula)));
                asistenciaReg.setEstado_aula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_estado_aula)));
                asistenciaRegs.add(asistenciaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaRegs;
    }
    public ArrayList<AsistenciaReg> getAsistenciasLocalSinEnviar(int idLocal){
        ArrayList<AsistenciaReg> asistenciaRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal),"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_LOCAL,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                AsistenciaReg asistenciaReg = new AsistenciaReg();
                asistenciaReg = new AsistenciaReg();
                asistenciaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_id)));
                asistenciaReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_dni)));
                asistenciaReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nombres)));
                asistenciaReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_materno)));
                asistenciaReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_paterno)));
                asistenciaReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_naula)));
                asistenciaReg.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_discapacidad)));
                asistenciaReg.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_prioridad)));
                asistenciaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idnacional)));
                asistenciaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_idsede)));
                asistenciaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_sede)));
                asistenciaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ccdd)));
                asistenciaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_departamento)));
                asistenciaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idlocal)));
                asistenciaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_local)));
                asistenciaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_direccion)));
                asistenciaReg.setDia_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_dia_local)));
                asistenciaReg.setMes_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_mes_local)));
                asistenciaReg.setAnio_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_anio_local)));
                asistenciaReg.setHora_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_hora_local)));
                asistenciaReg.setMin_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_min_local)));
                asistenciaReg.setSeg_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_seg_local)));
                asistenciaReg.setEstado_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_estado_local)));
                asistenciaRegs.add(asistenciaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaRegs;
    }

    public ArrayList<AsistenciaRaReg> getAsistenciasRASinEnviar(int idLocal){
        ArrayList<AsistenciaRaReg> asistenciaRARegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal),"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencias_ra_reg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                AsistenciaRaReg asistenciaRaReg = new AsistenciaRaReg();
                asistenciaRaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_ccdd)));
                asistenciaRaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_departamento)));
                asistenciaRaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_idsede)));
                asistenciaRaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nom_sede)));
                asistenciaRaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_idnacional)));
                asistenciaRaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_idlocal)));
                asistenciaRaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nom_local)));
                asistenciaRaReg.setRed(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_red)));
                asistenciaRaReg.setTipo_cargo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_tipo_cargo)));
                asistenciaRaReg.setNombre_cargo(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nombre_cargo)));
                asistenciaRaReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_dni)));
                asistenciaRaReg.setNombres_completos(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_nombres_completos)));
                asistenciaRaReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_dia)));
                asistenciaRaReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_mes)));
                asistenciaRaReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_anio)));
                asistenciaRaReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_hora)));
                asistenciaRaReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_min)));
                asistenciaRaReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_seg)));
                asistenciaRaReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_ra_estado)));
                asistenciaRARegs.add(asistenciaRaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaRARegs;
    }

    public int getNroAsistenciasLocalRegistradas(int idLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal),"0"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            "estado_local>?",whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroAsistenciasLocalLeidas(int idLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal),"0"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            "estado_local>?",whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroAsistenciasRALeidas(int idLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal),"0"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencias_ra_reg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            "estado>?",whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }


    public ArrayList<AsistenciaReg> getAsistenciasAulaSinEnviar(int idLocal,int nroAula){
        ArrayList<AsistenciaReg> asistenciaRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal),String.valueOf(nroAula),"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_AULA,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                AsistenciaReg asistenciaReg = new AsistenciaReg();
                asistenciaReg = new AsistenciaReg();
                asistenciaReg.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_id)));
                asistenciaReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_dni)));
                asistenciaReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nombres)));
                asistenciaReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_materno)));
                asistenciaReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ape_paterno)));
                asistenciaReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_naula)));
                asistenciaReg.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_discapacidad)));
                asistenciaReg.setPrioridad(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_prioridad)));
                asistenciaReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idnacional)));
                asistenciaReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_idsede)));
                asistenciaReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_sede)));
                asistenciaReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_ccdd)));
                asistenciaReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_departamento)));
                asistenciaReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_idlocal)));
                asistenciaReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_nom_local)));
                asistenciaReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.asistenciareg_direccion)));
                asistenciaReg.setDia_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_dia_local)));
                asistenciaReg.setMes_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_mes_local)));
                asistenciaReg.setAnio_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_anio_local)));
                asistenciaReg.setHora_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_hora_local)));
                asistenciaReg.setMin_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_min_local)));
                asistenciaReg.setSeg_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_seg_local)));
                asistenciaReg.setEstado_local(cursor.getInt(cursor.getColumnIndex(SQLConstantes.asistenciareg_estado_local)));
                asistenciaRegs.add(asistenciaReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return asistenciaRegs;
    }

    public int getNroAsistenciasAulaRegistradas(int idLocal,int nroAula){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal),String.valueOf(nroAula),"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_AULA,whereArgs,null,null,null);
            if (cursor != null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroAsistenciasAulaLeidas(int idLocal,int nroAula){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal),String.valueOf(nroAula),"0"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA + " AND " +
                            "estado_aula>?",whereArgs,null,null,null);
            if (cursor != null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroAsistenciasAulaTotales(int idLocal,int nroAula){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal),String.valueOf(nroAula)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA ,whereArgs,null,null,null);
            if (cursor != null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroAsistenciasLocalSinRegistro(int idLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal),"0"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_LOCAL ,whereArgs,null,null,null);
            if (cursor != null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroAsistenciasAulaSinRegistro(int idLocal,int nroAula){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal),String.valueOf(nroAula),"0"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_AULA,whereArgs,null,null,null);
            if (cursor != null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroAsistenciasLocalTransferidos(int idLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal),"2"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_LOCAL ,whereArgs,null,null,null);
            if (cursor != null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroAsistenciasRATransferidos(int idLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal),"2"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistencias_ra_reg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO ,whereArgs,null,null,null);
            if (cursor != null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroAsistenciasAulaTransferidos(int idLocal,int nroAula){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal),String.valueOf(nroAula),"2"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg,
                    null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO_AULA,whereArgs,null,null,null);
            if (cursor != null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }



    /**
     * --------------------------------FIN QUERYS ASISTENCIA -----------------------------------
     * */


    /**
     * --------------------------------QUERYS INVENTARIO: FICHAS, CUADERNILLOS, LISTADOS -----------------------------------
     * */

    //BUSCAR FICHAS, CUADERNILLOS, LISTADOS EN MARCO TOTAL
    public Inventario getFicha(String codMaterial){
        Inventario inventario = null;
        String[] whereArgs = new String[]{codMaterial};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichas, null, SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                inventario = new Inventario();
                inventario.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_id)));
                inventario.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codigo)));
                inventario.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_tipo)));
                inventario.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ccdd)));
                inventario.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_departamento)));
                inventario.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idnacional)));
                inventario.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_idsede)));
                inventario.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_sede)));
                inventario.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idlocal)));
                inventario.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_local)));
                inventario.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_dni)));
                inventario.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_paterno)));
                inventario.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_materno)));
                inventario.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nombres)));
                inventario.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_naula)));
                inventario.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codpagina)));
                inventario.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_direccion)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventario;
    }
    public Inventario getCuadernillo(String codMaterial){
        Inventario inventario = null;
        String[] whereArgs = new String[]{codMaterial};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillos, null, SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                inventario = new Inventario();
                inventario.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_id)));
                inventario.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codigo)));
                inventario.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_tipo)));
                inventario.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ccdd)));
                inventario.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_departamento)));
                inventario.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idnacional)));
                inventario.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_idsede)));
                inventario.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_sede)));
                inventario.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idlocal)));
                inventario.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_local)));
                inventario.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_dni)));
                inventario.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_paterno)));
                inventario.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_materno)));
                inventario.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nombres)));
                inventario.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_naula)));
                inventario.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codpagina)));
                inventario.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_direccion)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventario;
    }
    public Inventario getListado(String codMaterial){
        Inventario inventario = null;
        String[] whereArgs = new String[]{codMaterial};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistados, null, SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                inventario = new Inventario();
                inventario.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_id)));
                inventario.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codigo)));
                inventario.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_tipo)));
                inventario.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ccdd)));
                inventario.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_departamento)));
                inventario.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idnacional)));
                inventario.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_idsede)));
                inventario.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_sede)));
                inventario.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idlocal)));
                inventario.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_local)));
                inventario.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_dni)));
                inventario.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_paterno)));
                inventario.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_materno)));
                inventario.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nombres)));
                inventario.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_naula)));
                inventario.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codpagina)));
                inventario.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_direccion)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventario;
    }

    //NUMERO DE FICHAS , CUADERNILLOS, LISTADOS POR EL LOCAL EN EL MARCO TOTAL
    public int getNroFichasIdLocal(int idLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichas, null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            if(cursor!= null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }
    public int getNroCuadernillosIdLocal(int idLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillos, null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            if(cursor!= null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }
    public int getNroListadosIdLocal(int idLocal){
        int numero = 0;
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistados, null,SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            if(cursor!= null) numero =  cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    //NUMERO DE ITEMS EN LAS TABLAS DE REGISTRO DE FICHAS, CUADERNILLOS Y LISTADOS
    public long getNumeroItemsFichasReg(){
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, SQLConstantes.tablafichasreg);
    }
    public long getNumeroItemsCuadernillosReg(){
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, SQLConstantes.tablacuadernillosreg);
    }
    public long getNumeroItemsListadosReg(){
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, SQLConstantes.tablalistadosreg);
    }

    //INSERT PARA LAS TABLAS DE REGISTRO DE FICHAS, CUADERNILLOS Y LISTADOS
    public void insertarFichaReg(InventarioReg inventarioReg){
        ContentValues contentValues = inventarioReg.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablafichasreg,null,contentValues);
    }
    public void insertarCuadernilloReg(InventarioReg inventarioReg){
        ContentValues contentValues = inventarioReg.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablacuadernillosreg,null,contentValues);
    }
    public void insertarListadoReg(InventarioReg inventarioReg){
        ContentValues contentValues = inventarioReg.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablalistadosreg,null,contentValues);
    }

    //ACTUALIZAR PARA LAS TABLAS DE REGISTRO DE FICHAS, CUADERNILLOS Y LISTADOS
    public void actualizarFichaReg(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablafichasreg,valores,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs);
    }
    public void actualizarCuadernilloReg(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablacuadernillosreg,valores,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs);
    }
    public void actualizarListadoReg(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablalistadosreg,valores,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs);
    }


    //ACTUALIZAR TRANSFERIDO PARA LAS TABLAS DE REGISTRO DE FICHAS, CUADERNILLOS Y LISTADOS
    public void actualizarFichaRegSubido(String codigo){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.inventarioreg_estado,2);
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablafichasreg,contentValues,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs);
    }
    public void actualizarCuadernilloRegSubido(String codigo){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.inventarioreg_estado,2);
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablacuadernillosreg,contentValues,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs);
    }
    public void actualizarListadoRegSubido(String codigo){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.inventarioreg_estado,2);
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablalistadosreg,contentValues,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs);
    }

    //OBTENER FICHAS, CUADERNILLOS Y LISTADOS REGISTRADOS
    public InventarioReg getFichaReg(String codMaterial){
        InventarioReg inventarioReg = null;
        String[] whereArgs = new String[]{codMaterial};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichasreg, null,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_direccion)));
                inventarioReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_dia)));
                inventarioReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_mes)));
                inventarioReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_anio)));
                inventarioReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_hora)));
                inventarioReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_min)));
                inventarioReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_seg)));
                inventarioReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_estado)));
                inventarioReg.setNpostulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_npostulantes)));

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioReg;
    }

    public InventarioReg getCuadernilloReg(String codMaterial){
        InventarioReg inventarioReg = null;
        String[] whereArgs = new String[]{codMaterial};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillosreg, null,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_direccion)));
                inventarioReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_dia)));
                inventarioReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_mes)));
                inventarioReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_anio)));
                inventarioReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_hora)));
                inventarioReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_min)));
                inventarioReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_seg)));
                inventarioReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_estado)));
                inventarioReg.setNpostulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_npostulantes)));

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioReg;
    }

    public InventarioReg getListadoReg(String codMaterial){
        InventarioReg inventarioReg = null;
        String[] whereArgs = new String[]{codMaterial};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistadosreg, null,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_direccion)));
                inventarioReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_dia)));
                inventarioReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_mes)));
                inventarioReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_anio)));
                inventarioReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_hora)));
                inventarioReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_min)));
                inventarioReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_seg)));
                inventarioReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_estado)));
                inventarioReg.setNpostulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_npostulantes)));

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioReg;
    }

    //FILTRAR MARCO INVENTARIO PARA FICHAS, CUADERNILLOS Y LISTADOS
    
    public ArrayList<InventarioReg> filtrarMarcoInventarioFichas(int idLocal){
        ArrayList<InventarioReg> inventarioRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichas, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                InventarioReg inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_id)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_direccion)));
                inventarioRegs.add(inventarioReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioRegs;
    }

    public ArrayList<InventarioReg> filtrarMarcoInventarioCuadernillos(int idLocal){
        ArrayList<InventarioReg> inventarioRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillos, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                InventarioReg inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_id)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_direccion)));
                inventarioRegs.add(inventarioReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioRegs;
    }

    public ArrayList<InventarioReg> filtrarMarcoInventarioListados(int idLocal){
        ArrayList<InventarioReg> inventarioRegs = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idLocal)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistados, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                InventarioReg inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_id)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventario_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventario_direccion)));
                inventarioRegs.add(inventarioReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioRegs;
    }

    //LISTADO DE INVENTARIO REGISTRADO: FICHAS, CUADERNILLOS Y LISTADOS

    public ArrayList<InventarioReg> getListadoInventarioFichas(int idLocal, int nroAula){
        ArrayList<InventarioReg> inventarioRegs = new ArrayList<InventarioReg>();
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula)};
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA,whereArgs,null,null,"estado ASC");
            while(cursor.moveToNext()){
                InventarioReg inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_direccion)));
                inventarioReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_dia)));
                inventarioReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_mes)));
                inventarioReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_anio)));
                inventarioReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_hora)));
                inventarioReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_min)));
                inventarioReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_seg)));
                inventarioReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_estado)));
                inventarioReg.setNpostulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_npostulantes)));
                inventarioRegs.add(inventarioReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioRegs;
    }



    public ArrayList<InventarioReg> getListadoInventarioCuadernillo(int idLocal, int nroAula){
        ArrayList<InventarioReg> inventarioRegs = new ArrayList<InventarioReg>();
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula)};
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA,whereArgs,null,null,"estado ASC");
            while(cursor.moveToNext()){
                InventarioReg inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_direccion)));
                inventarioReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_dia)));
                inventarioReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_mes)));
                inventarioReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_anio)));
                inventarioReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_hora)));
                inventarioReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_min)));
                inventarioReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_seg)));
                inventarioReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_estado)));
                inventarioReg.setNpostulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_npostulantes)));
                inventarioRegs.add(inventarioReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioRegs;
    }

    public ArrayList<InventarioReg> getListadoInventarioLista(int idLocal, int nroAula){
        ArrayList<InventarioReg> inventarioRegs = new ArrayList<InventarioReg>();
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula)};
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistadosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA,whereArgs,null,null,"estado ASC");
            while(cursor.moveToNext()){
                InventarioReg inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_direccion)));
                inventarioReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_dia)));
                inventarioReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_mes)));
                inventarioReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_anio)));
                inventarioReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_hora)));
                inventarioReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_min)));
                inventarioReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_seg)));
                inventarioReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_estado)));
                inventarioReg.setNpostulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_npostulantes)));
                inventarioRegs.add(inventarioReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioRegs;
    }

    //NUMERO INVENTARIOS TOTALES: FICHAS,CUADERNILLOS Y LISTADOS

    public int getNroFichasTotales(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula)};
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }
    public int getNroCuadernillosTotales(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula)};
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }
    public int getNroListasTotales(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula)};
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistadosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    ////NUMERO INVENTARIOS TOTALES REGISTRADOS: FICHAS,CUADERNILLOS Y LISTADOS

    public int getNroFichasRegistradas(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"1"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroCuadernillosRegistrados(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"1"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroListasRegistradas(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"1"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistadosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    ////NUMERO INVENTARIOS TOTALES FALTAN: FICHAS,CUADERNILLOS Y LISTADOS

    public int getNroFichasFaltan(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"0"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroCuadernillosFaltan(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"0"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroListasFaltan(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"0"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistadosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    ////NUMERO INVENTARIOS TOTALES TRANSFERIDOS: FICHAS,CUADERNILLOS Y LISTADOS

    public int getNroFichasTransferidas(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"2"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroCuadernillosTransferidos(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"2"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    public int getNroListasTransferidas(int idLocal, int nroAula){
        int numero = 0;
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"2"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistadosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            if (cursor != null) numero = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return numero;
    }

    ////INVENTARIOS TOTALES SIN ENVIAR: FICHAS,CUADERNILLOS Y LISTADOS

    public ArrayList<InventarioReg> getInventarioFichasSinEnviar(int idLocal, int nroAula){
        ArrayList<InventarioReg> inventarioRegs = new ArrayList<InventarioReg>();
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"1"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablafichasreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                InventarioReg inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_direccion)));
                inventarioReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_dia)));
                inventarioReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_mes)));
                inventarioReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_anio)));
                inventarioReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_hora)));
                inventarioReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_min)));
                inventarioReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_seg)));
                inventarioReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_estado)));
                inventarioReg.setNpostulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_npostulantes)));
                inventarioRegs.add(inventarioReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioRegs;
    }

    public ArrayList<InventarioReg> getInventarioCuadernillosSinEnviar(int idLocal, int nroAula){
        ArrayList<InventarioReg> inventarioRegs = new ArrayList<InventarioReg>();
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"1"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablacuadernillosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                InventarioReg inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_direccion)));
                inventarioReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_dia)));
                inventarioReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_mes)));
                inventarioReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_anio)));
                inventarioReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_hora)));
                inventarioReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_min)));
                inventarioReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_seg)));
                inventarioReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_estado)));
                inventarioReg.setNpostulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_npostulantes)));
                inventarioRegs.add(inventarioReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioRegs;
    }

    public ArrayList<InventarioReg> getInventarioListasSinEnviar(int idLocal, int nroAula){
        ArrayList<InventarioReg> inventarioRegs = new ArrayList<InventarioReg>();
        Cursor cursor = null;
        try{
            String[] whereArgs = new String[]{String.valueOf(idLocal), String.valueOf(nroAula),"1"};
            cursor = sqLiteDatabase.query(SQLConstantes.tablalistadosreg, null,
                    SQLConstantes.WHERE_CLAUSE_ID_LOCAL + " AND " +
                            SQLConstantes.WHERE_CLAUSE_NRO_AULA+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_ESTADO,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                InventarioReg inventarioReg = new InventarioReg();
                inventarioReg.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codigo)));
                inventarioReg.setTipo(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_tipo)));
                inventarioReg.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ccdd)));
                inventarioReg.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_departamento)));
                inventarioReg.setIdnacional(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idnacional)));
                inventarioReg.setIdsede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_idsede)));
                inventarioReg.setNom_sede(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_sede)));
                inventarioReg.setIdlocal(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_idlocal)));
                inventarioReg.setNom_local(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nom_local)));
                inventarioReg.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_dni)));
                inventarioReg.setApe_paterno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_paterno)));
                inventarioReg.setApe_materno(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_ape_materno)));
                inventarioReg.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_nombres)));
                inventarioReg.setNaula(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_naula)));
                inventarioReg.setCodpagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_codpagina)));
                inventarioReg.setDireccion(cursor.getString(cursor.getColumnIndex(SQLConstantes.inventarioreg_direccion)));
                inventarioReg.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_dia)));
                inventarioReg.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_mes)));
                inventarioReg.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_anio)));
                inventarioReg.setHora(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_hora)));
                inventarioReg.setMin(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_min)));
                inventarioReg.setSeg(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_seg)));
                inventarioReg.setEstado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_estado)));
                inventarioReg.setNpostulantes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.inventarioreg_npostulantes)));
                inventarioRegs.add(inventarioReg);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return inventarioRegs;
    }


    public int getNroPostulantesListado(String codPagina){
        int nroPostulantes = 0;
        String[] whereArgs = new String[]{codPagina};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaasistenciasreg, null, SQLConstantes.WHERE_CLAUSE_COD_PAGINA,whereArgs,null,null,null);
            if(cursor!= null) nroPostulantes = cursor.getCount();
        }finally{
            if(cursor != null) cursor.close();
        }
        return nroPostulantes;
    }

    public void deleteAllElementosFromTabla(String nombreTabla){
        sqLiteDatabase.execSQL("delete from "+ nombreTabla);
    }

}
