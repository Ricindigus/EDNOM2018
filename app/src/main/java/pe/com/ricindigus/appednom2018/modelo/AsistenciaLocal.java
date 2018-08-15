package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class AsistenciaLocal {
    private String _id;
    private String dni;
    private String nombres;
    private String apepat;
    private String apemat;
    private String sede;
    private String local;
    private String aula;
    private int local_dia;
    private int local_mes;
    private int local_anio;
    private int local_hora;
    private int local_minuto;
    private int aula_dia;
    private int aula_mes;
    private int aula_anio;
    private int aula_hora;
    private int aula_minuto;
    private int subido;


    public AsistenciaLocal(String _id, String dni, String nombres, String apepat, String apemat, String sede, String local, String aula, int local_dia, int local_mes, int local_anio, int local_hora, int local_minuto, int aula_dia, int aula_mes, int aula_anio, int aula_hora, int aula_minuto, int subido) {
        this._id = _id;
        this.dni = dni;
        this.nombres = nombres;
        this.apepat = apepat;
        this.apemat = apemat;
        this.sede = sede;
        this.local = local;
        this.aula = aula;
        this.local_dia = local_dia;
        this.local_mes = local_mes;
        this.local_anio = local_anio;
        this.local_hora = local_hora;
        this.local_minuto = local_minuto;
        this.aula_dia = aula_dia;
        this.aula_mes = aula_mes;
        this.aula_anio = aula_anio;
        this.aula_hora = aula_hora;
        this.aula_minuto = aula_minuto;
        this.subido = subido;
    }

    public AsistenciaLocal() {
        this._id = "";
        this.dni = "";
        this.nombres = "";
        this.apepat = "";
        this.apemat = "";
        this.sede = "";
        this.local = "";
        this.aula = "";
        this.local_dia = 0;
        this.local_mes = 0;
        this.local_anio = 0;
        this.local_hora = 0;
        this.local_minuto = 0;
        this.aula_dia = 0;
        this.aula_mes = 0;
        this.aula_anio = 0;
        this.aula_hora = 0;
        this.aula_minuto = 0;
        this.subido = -1;
    }


    public String getApepat() {
        return apepat;
    }

    public void setApepat(String apepat) {
        this.apepat = apepat;
    }

    public String getApemat() {
        return apemat;
    }

    public void setApemat(String apemat) {
        this.apemat = apemat;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public int getLocal_dia() {
        return local_dia;
    }

    public void setLocal_dia(int local_dia) {
        this.local_dia = local_dia;
    }

    public int getLocal_mes() {
        return local_mes;
    }

    public void setLocal_mes(int local_mes) {
        this.local_mes = local_mes;
    }

    public int getLocal_anio() {
        return local_anio;
    }

    public void setLocal_anio(int local_anio) {
        this.local_anio = local_anio;
    }

    public int getLocal_hora() {
        return local_hora;
    }

    public void setLocal_hora(int local_hora) {
        this.local_hora = local_hora;
    }

    public int getLocal_minuto() {
        return local_minuto;
    }

    public void setLocal_minuto(int local_minuto) {
        this.local_minuto = local_minuto;
    }

    public int getAula_dia() {
        return aula_dia;
    }

    public void setAula_dia(int aula_dia) {
        this.aula_dia = aula_dia;
    }

    public int getAula_mes() {
        return aula_mes;
    }

    public void setAula_mes(int aula_mes) {
        this.aula_mes = aula_mes;
    }

    public int getAula_anio() {
        return aula_anio;
    }

    public void setAula_anio(int aula_anio) {
        this.aula_anio = aula_anio;
    }

    public int getAula_hora() {
        return aula_hora;
    }

    public void setAula_hora(int aula_hora) {
        this.aula_hora = aula_hora;
    }

    public int getAula_minuto() {
        return aula_minuto;
    }

    public void setAula_minuto(int aula_minuto) {
        this.aula_minuto = aula_minuto;
    }

    public int getSubido() {
        return subido;
    }

    public void setSubido(int subido) {
        this.subido = subido;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.asistencia_id,_id);
        contentValues.put(SQLConstantes.asistencia_dni,dni);
        contentValues.put(SQLConstantes.asistencia_nombres,nombres);
        contentValues.put(SQLConstantes.asistencia_apepat,apepat);
        contentValues.put(SQLConstantes.asistencia_apemat,apemat);
        contentValues.put(SQLConstantes.asistencia_sede,sede);
        contentValues.put(SQLConstantes.asistencia_local,local);
        contentValues.put(SQLConstantes.asistencia_aula,aula);
        contentValues.put(SQLConstantes.asistencia_local_dia,local_dia);
        contentValues.put(SQLConstantes.asistencia_local_mes,local_mes);
        contentValues.put(SQLConstantes.asistencia_local_anio,local_anio);
        contentValues.put(SQLConstantes.asistencia_local_hora,local_hora);
        contentValues.put(SQLConstantes.asistencia_local_minuto,local_minuto);
        contentValues.put(SQLConstantes.asistencia_aula_dia,aula_dia);
        contentValues.put(SQLConstantes.asistencia_aula_mes,aula_mes);
        contentValues.put(SQLConstantes.asistencia_aula_anio,aula_anio);
        contentValues.put(SQLConstantes.asistencia_aula_hora,aula_hora);
        contentValues.put(SQLConstantes.asistencia_aula_minuto,aula_minuto);
        contentValues.put(SQLConstantes.asistencia_subido,subido);
        return contentValues;
    }
}
