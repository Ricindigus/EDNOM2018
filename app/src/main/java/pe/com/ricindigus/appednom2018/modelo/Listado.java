package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class Listado {
    private String id;
    private String codigo;
    private int tipo;
    private int idnacional;
    private String ccdd;
    private String idsede;
    private String sede;
    private int idlocal;
    private String local;
    private int naula;
    private int npostulantes;
    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int min;
    private int seg;;
    private int estado;

    public Listado(String id, String codigo, int tipo, int idnacional, String ccdd, String idsede, String sede, int idlocal, String local, int naula, int npostulantes, int dia, int mes, int anio, int hora, int min, int seg, int estado) {
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.idnacional = idnacional;
        this.ccdd = ccdd;
        this.idsede = idsede;
        this.sede = sede;
        this.idlocal = idlocal;
        this.local = local;
        this.naula = naula;
        this.npostulantes = npostulantes;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.min = min;
        this.seg = seg;
        this.estado = estado;
    }

    public Listado() {
    }

    public int getNpostulantes() {
        return npostulantes;
    }

    public void setNpostulantes(int npostulantes) {
        this.npostulantes = npostulantes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getIdnacional() {
        return idnacional;
    }

    public void setIdnacional(int idnacional) {
        this.idnacional = idnacional;
    }

    public String getCcdd() {
        return ccdd;
    }

    public void setCcdd(String ccdd) {
        this.ccdd = ccdd;
    }

    public String getIdsede() {
        return idsede;
    }

    public void setIdsede(String idsede) {
        this.idsede = idsede;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public int getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(int idlocal) {
        this.idlocal = idlocal;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getNaula() {
        return naula;
    }

    public void setNaula(int naula) {
        this.naula = naula;
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

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSeg() {
        return seg;
    }

    public void setSeg(int seg) {
        this.seg = seg;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.listado_codigo,codigo);
        contentValues.put(SQLConstantes.listado_tipo,tipo);
        contentValues.put(SQLConstantes.listado_idnacional,idnacional);
        contentValues.put(SQLConstantes.listado_ccdd,ccdd);
        contentValues.put(SQLConstantes.listado_idsede,idsede);
        contentValues.put(SQLConstantes.listado_sede,sede);
        contentValues.put(SQLConstantes.listado_idlocal,idlocal);
        contentValues.put(SQLConstantes.listado_local,local);
        contentValues.put(SQLConstantes.listado_naula,naula);
        contentValues.put(SQLConstantes.listado_npostulantes,npostulantes);
        contentValues.put(SQLConstantes.listado_dia,dia);
        contentValues.put(SQLConstantes.listado_mes,mes);
        contentValues.put(SQLConstantes.listado_anio,anio);
        contentValues.put(SQLConstantes.listado_hora,hora);
        contentValues.put(SQLConstantes.listado_min,min);
        contentValues.put(SQLConstantes.listado_seg,seg);
        contentValues.put(SQLConstantes.listado_estado,estado);
        return contentValues;
    }
}
