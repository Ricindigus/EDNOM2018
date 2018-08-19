package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

import java.util.HashMap;
import java.util.Map;

public class AsistenciaLocal {
    private String _id;
    private String dni;
    private String nombres;
    private String apepat;
    private String apemat;
    private String sede;
    private int id_local;
    private String local;
    private String aula;
    private int local_dia;
    private int local_mes;
    private int local_anio;
    private int local_hora;
    private int local_minuto;
    private int subido_local;


    public AsistenciaLocal(String _id, String dni, String nombres, String apepat, String apemat, String sede, int id_local, String local, String aula, int local_dia, int local_mes, int local_anio, int local_hora, int local_minuto, int subido_local) {
        this._id = _id;
        this.dni = dni;
        this.nombres = nombres;
        this.apepat = apepat;
        this.apemat = apemat;
        this.sede = sede;
        this.id_local = id_local;
        this.local = local;
        this.aula = aula;
        this.local_dia = local_dia;
        this.local_mes = local_mes;
        this.local_anio = local_anio;
        this.local_hora = local_hora;
        this.local_minuto = local_minuto;
        this.subido_local = subido_local;
    }

    public AsistenciaLocal() {
        this._id = "";
        this.dni = "";
        this.nombres = "";
        this.apepat = "";
        this.apemat = "";
        this.sede = "";
        this.id_local = 0;
        this.local = "";
        this.aula = "";
        this.local_dia = 0;
        this.local_mes = 0;
        this.local_anio = 0;
        this.local_hora = 0;
        this.local_minuto = 0;
        this.subido_local = -1;
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

    public int getId_local() {
        return id_local;
    }

    public void setId_local(int id_local) {
        this.id_local = id_local;
    }

    public int getSubido_local() {
        return subido_local;
    }

    public void setSubido_local(int subido_local) {
        this.subido_local = subido_local;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.asistencia_local_id,_id);
        contentValues.put(SQLConstantes.asistencia_local_dni,dni);
        contentValues.put(SQLConstantes.asistencia_local_nombres,nombres);
        contentValues.put(SQLConstantes.asistencia_local_apepat,apepat);
        contentValues.put(SQLConstantes.asistencia_local_apemat,apemat);
        contentValues.put(SQLConstantes.asistencia_local_sede,sede);
        contentValues.put(SQLConstantes.asistencia_local_id_local,id_local);
        contentValues.put(SQLConstantes.asistencia_local_nombre_local,local);
        contentValues.put(SQLConstantes.asistencia_local_aula,aula);
        contentValues.put(SQLConstantes.asistencia_local_local_dia,local_dia);
        contentValues.put(SQLConstantes.asistencia_local_local_mes,local_mes);
        contentValues.put(SQLConstantes.asistencia_local_local_anio,local_anio);
        contentValues.put(SQLConstantes.asistencia_local_local_hora,local_hora);
        contentValues.put(SQLConstantes.asistencia_local_local_minuto,local_minuto);
        contentValues.put(SQLConstantes.asistencia_local_subido_local,subido_local);
        return contentValues;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> asistencia = new HashMap<>();
        asistencia.put("dni", dni);
        asistencia.put("nombres", nombres);
        asistencia.put("apepat", apepat);
        asistencia.put("apemat", apemat);
        asistencia.put("sede", sede);
        asistencia.put("id_local", id_local);
        asistencia.put("local", local);
        asistencia.put("aula", aula);
        asistencia.put("local_dia", local_dia);
        asistencia.put("local_mes", local_mes);
        asistencia.put("local_anio", local_anio);
        asistencia.put("local_hora", local_hora);
        asistencia.put("local_minuto", local_minuto);
        return asistencia;
    }
}
