package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class AsistenciaAula {
    private String _id;
    private String dni;
    private String idnacional;
    private String ccdd;
    private String idsede;
    private String sede;
    private int idlocal;
    private String local;
    private String direccion;
    private String nombres;
    private String ape_paterno;
    private String ape_materno;
    private int naula;
    private String discapacidad;
    private String prioridad;
    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int min;
    private int seg;
    private int estado;

    public AsistenciaAula() {
    }

    public AsistenciaAula(String _id, String dni, String idnacional, String ccdd, String idsede, String sede, int idlocal, String local, String direccion, String nombres, String ape_paterno, String ape_materno, int naula, String discapacidad, String prioridad, int dia, int mes, int anio, int hora, int min, int seg, int estado) {
        this._id = _id;
        this.dni = dni;
        this.idnacional = idnacional;
        this.ccdd = ccdd;
        this.idsede = idsede;
        this.sede = sede;
        this.idlocal = idlocal;
        this.local = local;
        this.direccion = direccion;
        this.nombres = nombres;
        this.ape_paterno = ape_paterno;
        this.ape_materno = ape_materno;
        this.naula = naula;
        this.discapacidad = discapacidad;
        this.prioridad = prioridad;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.min = min;
        this.seg = seg;
        this.estado = estado;
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

    public String getIdnacional() {
        return idnacional;
    }

    public void setIdnacional(String idnacional) {
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
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
        contentValues.put(SQLConstantes.asis_aula_id,_id);
        contentValues.put(SQLConstantes.asis_aula_dni,dni);
        contentValues.put(SQLConstantes.asis_aula_idnacional,idnacional);
        contentValues.put(SQLConstantes.asis_aula_ccdd,ccdd);
        contentValues.put(SQLConstantes.asis_aula_idsede,idsede);
        contentValues.put(SQLConstantes.asis_aula_sede,sede);
        contentValues.put(SQLConstantes.asis_aula_idlocal,idlocal);
        contentValues.put(SQLConstantes.asis_aula_local,local);
        contentValues.put(SQLConstantes.asis_aula_direccion,direccion);
        contentValues.put(SQLConstantes.asis_aula_nombres,nombres);
        contentValues.put(SQLConstantes.asis_aula_ape_paterno,ape_paterno);
        contentValues.put(SQLConstantes.asis_aula_ape_materno,ape_materno);
        contentValues.put(SQLConstantes.asis_aula_naula,naula);
        contentValues.put(SQLConstantes.asis_aula_discapacidad,discapacidad);
        contentValues.put(SQLConstantes.asis_aula_prioridad,prioridad);
        contentValues.put(SQLConstantes.asis_aula_fecha_dia,dia);
        contentValues.put(SQLConstantes.asis_aula_fecha_mes,mes);
        contentValues.put(SQLConstantes.asis_aula_fecha_anio,anio);
        contentValues.put(SQLConstantes.asis_aula_fecha_hora,hora);
        contentValues.put(SQLConstantes.asis_aula_fecha_min,min);
        contentValues.put(SQLConstantes.asis_aula_fecha_seg,seg);
        contentValues.put(SQLConstantes.asis_aula_estado,estado);
        return contentValues;
    }
}
