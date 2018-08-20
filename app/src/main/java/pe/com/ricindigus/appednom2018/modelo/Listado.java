package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

import java.util.HashMap;
import java.util.Map;

public class Listado {
    private String _id;
    private String codigo_pagina;
    private String sede;
    private int id_local;
    private String local;
    private String aula;
    private int nro_postulantes;
    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int minuto;
    private int subido;

    public Listado(String _id, String codigo_pagina, String sede, int id_local, String local, String aula, int nro_postulantes, int dia, int mes, int anio, int hora, int minuto, int subido) {
        this._id = _id;
        this.codigo_pagina = codigo_pagina;
        this.sede = sede;
        this.id_local = id_local;
        this.local = local;
        this.aula = aula;
        this.nro_postulantes = nro_postulantes;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minuto = minuto;
        this.subido = subido;
    }

    public Listado() {
        this._id = "";
        this.codigo_pagina = "";
        this.sede = "";
        this.id_local = 0;
        this.local = "";
        this.aula = "";
        this.nro_postulantes = 0;
        this.dia = 0;
        this.mes = 0;
        this.anio = 0;
        this.hora = 0;
        this.minuto = 0;
        this.subido = 0;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCodigo_pagina() {
        return codigo_pagina;
    }

    public void setCodigo_pagina(String codigo_pagina) {
        this.codigo_pagina = codigo_pagina;
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

    public int getNro_postulantes() {
        return nro_postulantes;
    }

    public void setNro_postulantes(int nro_postulantes) {
        this.nro_postulantes = nro_postulantes;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.listado_id,_id);
        contentValues.put(SQLConstantes.listado_codigo_pagina,codigo_pagina);
        contentValues.put(SQLConstantes.listado_sede,sede);
        contentValues.put(SQLConstantes.listado_id_local,id_local);
        contentValues.put(SQLConstantes.listado_nombre_local,local);
        contentValues.put(SQLConstantes.listado_aula,aula);
        contentValues.put(SQLConstantes.listado_nro_postulantes,nro_postulantes);
        contentValues.put(SQLConstantes.listado_dia,dia);
        contentValues.put(SQLConstantes.listado_mes,mes);
        contentValues.put(SQLConstantes.listado_anio,anio);
        contentValues.put(SQLConstantes.listado_hora,hora);
        contentValues.put(SQLConstantes.listado_minuto,minuto);
        contentValues.put(SQLConstantes.listado_subido,subido);
        return contentValues;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> asistencia = new HashMap<>();
        asistencia.put("codigo_pagina", codigo_pagina);
        asistencia.put("sede", sede);
        asistencia.put("id_local", id_local);
        asistencia.put("local", local);
        asistencia.put("aula", aula);
        asistencia.put("nro_postulantes", nro_postulantes);
        asistencia.put("dia", dia);
        asistencia.put("mes", mes);
        asistencia.put("anio", anio);
        asistencia.put("hora", hora);
        asistencia.put("minuto", minuto);
        return asistencia;
    }
}
