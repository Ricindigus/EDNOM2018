package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

import java.util.HashMap;
import java.util.Map;

public class Cuadernillo {
    private String _id;
    private String codcartilla;
    private String dni;
    private String nombres;
    private String apepat;
    private String apemat;
    private String sede;
    private int id_local;
    private String local;
    private String aula;
    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int minuto;
    private int subido;

    public Cuadernillo(String _id, String codcartilla, String dni, String nombres, String apepat, String apemat, String sede, int id_local, String local, String aula, int dia, int mes, int anio, int hora, int minuto, int subido) {
        this._id = _id;
        this.codcartilla = codcartilla;
        this.dni = dni;
        this.nombres = nombres;
        this.apepat = apepat;
        this.apemat = apemat;
        this.sede = sede;
        this.id_local = id_local;
        this.local = local;
        this.aula = aula;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minuto = minuto;
        this.subido = subido;
    }

    public Cuadernillo() {
        this._id = "";
        this.codcartilla = "";
        this.dni = "";
        this.nombres = "";
        this.apepat = "";
        this.apemat = "";
        this.sede = "";
        this.id_local = 0;
        this.local = "";
        this.aula = "";
        this.dia = 0;
        this.mes = 0;
        this.anio = 0;
        this.hora = 0;
        this.minuto = 0;
        this.subido = 0;
    }


    public String getCodcartilla() {
        return codcartilla;
    }

    public void setCodcartilla(String codcartilla) {
        this.codcartilla = codcartilla;
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

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public int getId_local() {
        return id_local;
    }

    public void setId_local(int id_local) {
        this.id_local = id_local;
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

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getSubido() {
        return subido;
    }

    public void setSubido(int subido) {
        this.subido = subido;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.cuadernillo_id,_id);
        contentValues.put(SQLConstantes.cuadernillo_codcartilla,codcartilla);
        contentValues.put(SQLConstantes.cuadernillo_dni,dni);
        contentValues.put(SQLConstantes.cuadernillo_nombres,nombres);
        contentValues.put(SQLConstantes.cuadernillo_apepat,apepat);
        contentValues.put(SQLConstantes.cuadernillo_apemat,apemat);
        contentValues.put(SQLConstantes.cuadernillo_sede,sede);
        contentValues.put(SQLConstantes.cuadernillo_id_local,id_local);
        contentValues.put(SQLConstantes.cuadernillo_nombre_local,local);
        contentValues.put(SQLConstantes.cuadernillo_aula,aula);
        contentValues.put(SQLConstantes.cuadernillo_dia,dia);
        contentValues.put(SQLConstantes.cuadernillo_mes,mes);
        contentValues.put(SQLConstantes.cuadernillo_anio,anio);
        contentValues.put(SQLConstantes.cuadernillo_hora,hora);
        contentValues.put(SQLConstantes.cuadernillo_minuto,minuto);
        contentValues.put(SQLConstantes.cuadernillo_subido,subido);
        return contentValues;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> asistencia = new HashMap<>();
        asistencia.put("codcartilla", codcartilla);
        asistencia.put("dni", dni);
        asistencia.put("nombres", nombres);
        asistencia.put("apepat", apepat);
        asistencia.put("apemat", apemat);
        asistencia.put("sede", sede);
        asistencia.put("id_local", id_local);
        asistencia.put("local", local);
        asistencia.put("aula", aula);
        asistencia.put("dia", dia);
        asistencia.put("mes", mes);
        asistencia.put("anio", anio);
        asistencia.put("hora", hora);
        asistencia.put("minuto", minuto);
        return asistencia;
    }
}
