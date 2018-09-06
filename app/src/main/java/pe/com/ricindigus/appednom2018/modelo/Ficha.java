package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class Ficha {
    private String id;
    private String codigo;
    private int tipo;
    private int idnacional;
    private String ccdd;
    private String idsede;
    private String sede;
    private int idlocal;
    private String local;
    private String dni;
    private String nombres;
    private String ape_paterno;
    private String ape_materno;
    private int naula;
    private String codpagina;
    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int min;
    private int seg;
    private int estado;

    public Ficha(String id, String codigo, int tipo, int idnacional, String ccdd, String idsede, String sede, int idlocal, String local, String dni, String nombres, String ape_paterno, String aper_materno, int naula, String codpagina, int dia, int mes, int anio, int hora, int min, int seg, int estado) {
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.idnacional = idnacional;
        this.ccdd = ccdd;
        this.idsede = idsede;
        this.sede = sede;
        this.idlocal = idlocal;
        this.local = local;
        this.dni = dni;
        this.nombres = nombres;
        this.ape_paterno = ape_paterno;
        this.ape_materno = aper_materno;
        this.naula = naula;
        this.codpagina = codpagina;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.min = min;
        this.seg = seg;
        this.estado = estado;
    }

    public Ficha() {
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

    public String getApe_paterno() {
        return ape_paterno;
    }

    public void setApe_paterno(String ape_paterno) {
        this.ape_paterno = ape_paterno;
    }

    public String getApe_materno() {
        return ape_materno;
    }

    public void setApe_materno(String ape_materno) {
        this.ape_materno = ape_materno;
    }

    public int getNaula() {
        return naula;
    }

    public void setNaula(int naula) {
        this.naula = naula;
    }

    public String getCodpagina() {
        return codpagina;
    }

    public void setCodpagina(String codpagina) {
        this.codpagina = codpagina;
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
        contentValues.put(SQLConstantes.ficha_codigo,codigo);
        contentValues.put(SQLConstantes.ficha_tipo,tipo);
        contentValues.put(SQLConstantes.ficha_idnacional,idnacional);
        contentValues.put(SQLConstantes.ficha_ccdd,ccdd);
        contentValues.put(SQLConstantes.ficha_idsede,idsede);
        contentValues.put(SQLConstantes.ficha_sede,sede);
        contentValues.put(SQLConstantes.ficha_idlocal,idlocal);
        contentValues.put(SQLConstantes.ficha_local,local);
        contentValues.put(SQLConstantes.ficha_dni,dni);
        contentValues.put(SQLConstantes.ficha_nombres,nombres);
        contentValues.put(SQLConstantes.ficha_ape_paterno,ape_paterno);
        contentValues.put(SQLConstantes.ficha_ape_materno,ape_materno);
        contentValues.put(SQLConstantes.ficha_naula,naula);
        contentValues.put(SQLConstantes.ficha_codpagina,codpagina);
        contentValues.put(SQLConstantes.ficha_dia,dia);
        contentValues.put(SQLConstantes.ficha_mes,mes);
        contentValues.put(SQLConstantes.ficha_anio,anio);
        contentValues.put(SQLConstantes.ficha_hora,hora);
        contentValues.put(SQLConstantes.ficha_min,min);
        contentValues.put(SQLConstantes.ficha_seg,seg);
        contentValues.put(SQLConstantes.ficha_estado,estado);
        return contentValues;
    }
}
