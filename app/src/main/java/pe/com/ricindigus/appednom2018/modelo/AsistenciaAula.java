package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class AsistenciaAula {
    private String _id;
    private String dni;
    private String nombres;
    private String apepat;
    private String apemat;
    private String aula;
    private int aula_dia;
    private int aula_mes;
    private int aula_anio;
    private int aula_hora;
    private int aula_minuto;
    private int subido;

    public AsistenciaAula(String _id, String dni, String nombres, String apepat, String apemat, String aula, int aula_dia, int aula_mes, int aula_anio, int aula_hora, int aula_minuto, int subido) {
        this._id = _id;
        this.dni = dni;
        this.nombres = nombres;
        this.apepat = apepat;
        this.apemat = apemat;
        this.aula = aula;
        this.aula_dia = aula_dia;
        this.aula_mes = aula_mes;
        this.aula_anio = aula_anio;
        this.aula_hora = aula_hora;
        this.aula_minuto = aula_minuto;
        this.subido = subido;
    }

    public AsistenciaAula() {
        this._id = "";
        this.dni = "";
        this.nombres = "";
        this.apepat = "";
        this.apemat = "";
        this.aula = "";
        this.aula_dia = 0;
        this.aula_mes = 0;
        this.aula_anio = 0;
        this.aula_hora = 0;
        this.aula_minuto = 0;
        this.subido = 0;
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

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
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
        contentValues.put(SQLConstantes.asis_aula_id,_id);
        contentValues.put(SQLConstantes.asis_aula_dni,dni);
        contentValues.put(SQLConstantes.asis_aula_nombres,nombres);
        contentValues.put(SQLConstantes.asis_aula_apepat,apepat);
        contentValues.put(SQLConstantes.asis_aula_apemat,apemat);
        contentValues.put(SQLConstantes.asis_aula_aula,aula);
        contentValues.put(SQLConstantes.asis_aula_aula_dia,aula_dia);
        contentValues.put(SQLConstantes.asis_aula_aula_mes,aula_mes);
        contentValues.put(SQLConstantes.asis_aula_aula_anio,aula_anio);
        contentValues.put(SQLConstantes.asis_aula_aula_hora,aula_hora);
        contentValues.put(SQLConstantes.asis_aula_aula_minuto,aula_minuto);
        contentValues.put(SQLConstantes.asis_aula_subido,subido);
        return contentValues;
    }
}
