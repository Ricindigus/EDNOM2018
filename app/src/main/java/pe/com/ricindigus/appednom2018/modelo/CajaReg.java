package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class CajaReg {
    private String _id;
    private String cod_barra_caja;
    private String ccdd;
    private String departamento;
    private int idnacional;
    private String idsede;
    private String nom_sede;
    private int idlocal;
    private String nom_local;
    private int tipo;
    private int nlado;
    private int acl;
    private String direccion;
    private int dia_entrada;
    private int mes_entrada;
    private int anio_entrada;
    private int hora_entrada;
    private int min_entrada;
    private int seg_entrada;
    private int estado_entrada;
    private int check_entrada;
    private int dia_salida;
    private int mes_salida;
    private int anio_salida;
    private int hora_salida;
    private int min_salida;
    private int seg_salida;
    private int estado_salida;
    private int check_salida;


    public CajaReg(String _id, String cod_barra_caja, String ccdd, String departamento, int idnacional, String idsede, String nom_sede, int idlocal, String nom_local, int tipo, int nlado, int acl, String direccion, int dia_entrada, int mes_entrada, int anio_entrada, int hora_entrada, int min_entrada, int seg_entrada, int estado_entrada, int check_entrada, int dia_salida, int mes_salida, int anio_salida, int hora_salida, int min_salida, int seg_salida, int estado_salida, int check_salida) {
        this._id = _id;
        this.cod_barra_caja = cod_barra_caja;
        this.ccdd = ccdd;
        this.departamento = departamento;
        this.idnacional = idnacional;
        this.idsede = idsede;
        this.nom_sede = nom_sede;
        this.idlocal = idlocal;
        this.nom_local = nom_local;
        this.tipo = tipo;
        this.nlado = nlado;
        this.acl = acl;
        this.direccion = direccion;
        this.dia_entrada = dia_entrada;
        this.mes_entrada = mes_entrada;
        this.anio_entrada = anio_entrada;
        this.hora_entrada = hora_entrada;
        this.min_entrada = min_entrada;
        this.seg_entrada = seg_entrada;
        this.estado_entrada = estado_entrada;
        this.check_entrada = check_entrada;
        this.dia_salida = dia_salida;
        this.mes_salida = mes_salida;
        this.anio_salida = anio_salida;
        this.hora_salida = hora_salida;
        this.min_salida = min_salida;
        this.seg_salida = seg_salida;
        this.estado_salida = estado_salida;
        this.check_salida = check_salida;
    }

    public CajaReg() {
        this.dia_entrada = 0;
        this.mes_entrada = 0;
        this.anio_entrada = 0;
        this.hora_entrada = 0;
        this.min_entrada = 0;
        this.seg_entrada = 0;
        this.estado_entrada = 0;
        this.dia_salida = 0;
        this.mes_salida = 0;
        this.anio_salida = 0;
        this.hora_salida = 0;
        this.min_salida = 0;
        this.seg_salida = 0;
        this.estado_salida = 0;
        this.check_entrada = 0;
        this.check_salida = 0;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCheck_entrada() {
        return check_entrada;
    }

    public void setCheck_entrada(int check_entrada) {
        this.check_entrada = check_entrada;
    }

    public int getCheck_salida() {
        return check_salida;
    }

    public void setCheck_salida(int check_salida) {
        this.check_salida = check_salida;
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

    public String getCcdd() {
        return ccdd;
    }

    public void setCcdd(String ccdd) {
        this.ccdd = ccdd;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getIdnacional() {
        return idnacional;
    }

    public void setIdnacional(int idnacional) {
        this.idnacional = idnacional;
    }

    public String getIdsede() {
        return idsede;
    }

    public void setIdsede(String idsede) {
        this.idsede = idsede;
    }

    public String getNom_sede() {
        return nom_sede;
    }

    public void setNom_sede(String nom_sede) {
        this.nom_sede = nom_sede;
    }

    public int getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(int idlocal) {
        this.idlocal = idlocal;
    }

    public String getNom_local() {
        return nom_local;
    }

    public void setNom_local(String nom_local) {
        this.nom_local = nom_local;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getNlado() {
        return nlado;
    }

    public void setNlado(int nlado) {
        this.nlado = nlado;
    }

    public int getAcl() {
        return acl;
    }

    public void setAcl(int acl) {
        this.acl = acl;
    }

    public int getDia_entrada() {
        return dia_entrada;
    }

    public void setDia_entrada(int dia_entrada) {
        this.dia_entrada = dia_entrada;
    }

    public int getMes_entrada() {
        return mes_entrada;
    }

    public void setMes_entrada(int mes_entrada) {
        this.mes_entrada = mes_entrada;
    }

    public int getAnio_entrada() {
        return anio_entrada;
    }

    public void setAnio_entrada(int anio_entrada) {
        this.anio_entrada = anio_entrada;
    }

    public int getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(int hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public int getMin_entrada() {
        return min_entrada;
    }

    public void setMin_entrada(int min_entrada) {
        this.min_entrada = min_entrada;
    }

    public int getSeg_entrada() {
        return seg_entrada;
    }

    public void setSeg_entrada(int seg_entrada) {
        this.seg_entrada = seg_entrada;
    }

    public int getEstado_entrada() {
        return estado_entrada;
    }

    public void setEstado_entrada(int estado_entrada) {
        this.estado_entrada = estado_entrada;
    }

    public int getDia_salida() {
        return dia_salida;
    }

    public void setDia_salida(int dia_salida) {
        this.dia_salida = dia_salida;
    }

    public int getMes_salida() {
        return mes_salida;
    }

    public void setMes_salida(int mes_salida) {
        this.mes_salida = mes_salida;
    }

    public int getAnio_salida() {
        return anio_salida;
    }

    public void setAnio_salida(int anio_salida) {
        this.anio_salida = anio_salida;
    }

    public int getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(int hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getMin_salida() {
        return min_salida;
    }

    public void setMin_salida(int min_salida) {
        this.min_salida = min_salida;
    }

    public int getSeg_salida() {
        return seg_salida;
    }

    public void setSeg_salida(int seg_salida) {
        this.seg_salida = seg_salida;
    }

    public int getEstado_salida() {
        return estado_salida;
    }

    public void setEstado_salida(int estado_salida) {
        this.estado_salida = estado_salida;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.cajasreg_id,_id);
        contentValues.put(SQLConstantes.cajasreg_cod_barra,cod_barra_caja);
        contentValues.put(SQLConstantes.cajasreg_ccdd,ccdd);
        contentValues.put(SQLConstantes.cajasreg_departamento,departamento);
        contentValues.put(SQLConstantes.cajasreg_idnacional,idnacional);
        contentValues.put(SQLConstantes.cajasreg_idsede,idsede);
        contentValues.put(SQLConstantes.cajasreg_nom_sede,nom_sede);
        contentValues.put(SQLConstantes.cajasreg_idlocal,idlocal);
        contentValues.put(SQLConstantes.cajasreg_nom_local,nom_local);
        contentValues.put(SQLConstantes.cajasreg_tipo,tipo);
        contentValues.put(SQLConstantes.cajasreg_nlado,nlado);
        contentValues.put(SQLConstantes.cajasreg_acl,acl);
        contentValues.put(SQLConstantes.cajasreg_direccion,direccion);
        contentValues.put(SQLConstantes.cajasreg_dia_entrada,dia_entrada);
        contentValues.put(SQLConstantes.cajasreg_mes_entrada,mes_entrada);
        contentValues.put(SQLConstantes.cajasreg_anio_entrada,anio_entrada);
        contentValues.put(SQLConstantes.cajasreg_hora_entrada,hora_entrada);
        contentValues.put(SQLConstantes.cajasreg_min_entrada,min_entrada);
        contentValues.put(SQLConstantes.cajasreg_seg_entrada,seg_entrada);
        contentValues.put(SQLConstantes.cajasreg_estado_entrada,estado_entrada);
        contentValues.put(SQLConstantes.cajasreg_dia_salida,dia_salida);
        contentValues.put(SQLConstantes.cajasreg_mes_salida,mes_salida);
        contentValues.put(SQLConstantes.cajasreg_anio_salida,anio_salida);
        contentValues.put(SQLConstantes.cajasreg_hora_salida,hora_salida);
        contentValues.put(SQLConstantes.cajasreg_min_salida,min_salida);
        contentValues.put(SQLConstantes.cajasreg_seg_salida,seg_salida);
        contentValues.put(SQLConstantes.cajasreg_estado_salida,estado_salida);
        return contentValues;
    }
}
