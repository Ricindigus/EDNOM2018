package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class CajaOut {
    private String _id;
    private String cod_barra_caja;
    private int idsede;
    private String sede;
    private int idlocal;
    private String local;
    private int tipo;
    private int acl;
    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int min;
    private int seg;
    private int subido;

    public CajaOut(String _id, String cod_barra_caja, int idsede, String sede, int idlocal, String local, int tipo, int acl, int dia, int mes, int anio, int hora, int min, int seg, int subido) {
        this._id = _id;
        this.cod_barra_caja = cod_barra_caja;
        this.idsede = idsede;
        this.sede = sede;
        this.idlocal = idlocal;
        this.local = local;
        this.tipo = tipo;
        this.acl = acl;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.min = min;
        this.seg = seg;
        this.subido = subido;
    }

    public CajaOut() {
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCod_barra_caja() {
        return cod_barra_caja;
    }

    public void setCod_barra_caja(String cod_barra_caja) {
        this.cod_barra_caja = cod_barra_caja;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
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

    public int getAcl() {
        return acl;
    }

    public void setAcl(int acl) {
        this.acl = acl;
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

    public int getSubido() {
        return subido;
    }

    public void setSubido(int subido) {
        this.subido = subido;
    }
    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.cajas_salida_id,_id);
        contentValues.put(SQLConstantes.cajas_salida_cod_barra,cod_barra_caja);
        contentValues.put(SQLConstantes.cajas_salida_idsede,idsede);
        contentValues.put(SQLConstantes.cajas_salida_nomsede,sede);
        contentValues.put(SQLConstantes.cajas_salida_idlocal,idlocal);
        contentValues.put(SQLConstantes.cajas_salida_nomlocal,local);
        contentValues.put(SQLConstantes.cajas_salida_tipo,tipo);
        contentValues.put(SQLConstantes.cajas_salida_acl,acl);
        contentValues.put(SQLConstantes.cajas_salida_fecha_reg_dia,dia);
        contentValues.put(SQLConstantes.cajas_salida_fecha_reg_mes,mes);
        contentValues.put(SQLConstantes.cajas_salida_fecha_reg_anio,anio);
        contentValues.put(SQLConstantes.cajas_salida_fecha_reg_hora,hora);
        contentValues.put(SQLConstantes.cajas_salida_fecha_reg_min,min);
        contentValues.put(SQLConstantes.cajas_salida_fecha_reg_seg,seg);
        contentValues.put(SQLConstantes.cajas_salida_subido,subido);
        return contentValues;
    }

}
