package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class AsistenciaReg {
    private String _id;
    private String dni;
    private String nombres;
    private String ape_paterno;
    private String ape_materno;
    private int naula;
    private String discapacidad;
    private String prioridad;
    private int idnacional;
    private String idsede;
    private String nom_sede;
    private String ccdd;
    private String departamento;
    private int idlocal;
    private String nom_local;
    private String direccion;
    private int dia_local;
    private int mes_local;
    private int anio_local;
    private int hora_local;
    private int min_local;
    private int seg_local;
    private int estado_local;
    private int dia_aula;
    private int mes_aula;
    private int anio_aula;
    private int hora_aula;
    private int min_aula;
    private int seg_aula;
    private int estado_aula;

    public AsistenciaReg(){
        this.dia_local = 0;
        this.mes_local = 0;
        this.anio_local = 0;
        this.hora_local = 0;
        this.min_local = 0;
        this.seg_local = 0;
        this.estado_local = 0;
        this.dia_aula = 0;
        this.mes_aula = 0;
        this.anio_aula = 0;
        this.hora_aula = 0;
        this.min_aula = 0;
        this.seg_aula = 0;
        this.estado_aula = 0;
    }

    public AsistenciaReg(String _id, String dni, String nombres, String ape_paterno, String ape_materno, int naula, String discapacidad, String prioridad, int idnacional, String idsede, String nom_sede, String ccdd, String departamento, int idlocal, String nom_local, String direccion, int dia_local, int mes_local, int anio_local, int hora_local, int min_local, int seg_local, int estado_local, int dia_aula, int mes_aula, int anio_aula, int hora_aula, int min_aula, int seg_aula, int estado_aula) {
        this._id = _id;
        this.dni = dni;
        this.nombres = nombres;
        this.ape_paterno = ape_paterno;
        this.ape_materno = ape_materno;
        this.naula = naula;
        this.discapacidad = discapacidad;
        this.prioridad = prioridad;
        this.idnacional = idnacional;
        this.idsede = idsede;
        this.nom_sede = nom_sede;
        this.ccdd = ccdd;
        this.departamento = departamento;
        this.idlocal = idlocal;
        this.nom_local = nom_local;
        this.direccion = direccion;
        this.dia_local = dia_local;
        this.mes_local = mes_local;
        this.anio_local = anio_local;
        this.hora_local = hora_local;
        this.min_local = min_local;
        this.seg_local = seg_local;
        this.estado_local = estado_local;
        this.dia_aula = dia_aula;
        this.mes_aula = mes_aula;
        this.anio_aula = anio_aula;
        this.hora_aula = hora_aula;
        this.min_aula = min_aula;
        this.seg_aula = seg_aula;
        this.estado_aula = estado_aula;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getDia_local() {
        return dia_local;
    }

    public void setDia_local(int dia_local) {
        this.dia_local = dia_local;
    }

    public int getMes_local() {
        return mes_local;
    }

    public void setMes_local(int mes_local) {
        this.mes_local = mes_local;
    }

    public int getAnio_local() {
        return anio_local;
    }

    public void setAnio_local(int anio_local) {
        this.anio_local = anio_local;
    }

    public int getHora_local() {
        return hora_local;
    }

    public void setHora_local(int hora_local) {
        this.hora_local = hora_local;
    }

    public int getMin_local() {
        return min_local;
    }

    public void setMin_local(int min_local) {
        this.min_local = min_local;
    }

    public int getSeg_local() {
        return seg_local;
    }

    public void setSeg_local(int seg_local) {
        this.seg_local = seg_local;
    }

    public int getEstado_local() {
        return estado_local;
    }

    public void setEstado_local(int estado_local) {
        this.estado_local = estado_local;
    }

    public int getDia_aula() {
        return dia_aula;
    }

    public void setDia_aula(int dia_aula) {
        this.dia_aula = dia_aula;
    }

    public int getMes_aula() {
        return mes_aula;
    }

    public void setMes_aula(int mes_aula) {
        this.mes_aula = mes_aula;
    }

    public int getAnio_aula() {
        return anio_aula;
    }

    public void setAnio_aula(int anio_aula) {
        this.anio_aula = anio_aula;
    }

    public int getHora_aula() {
        return hora_aula;
    }

    public void setHora_aula(int hora_aula) {
        this.hora_aula = hora_aula;
    }

    public int getMin_aula() {
        return min_aula;
    }

    public void setMin_aula(int min_aula) {
        this.min_aula = min_aula;
    }

    public int getSeg_aula() {
        return seg_aula;
    }

    public void setSeg_aula(int seg_aula) {
        this.seg_aula = seg_aula;
    }

    public int getEstado_aula() {
        return estado_aula;
    }

    public void setEstado_aula(int estado_aula) {
        this.estado_aula = estado_aula;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.asistenciareg_id,_id);
        contentValues.put(SQLConstantes.asistenciareg_dni,dni);
        contentValues.put(SQLConstantes.asistenciareg_nombres,nombres);
        contentValues.put(SQLConstantes.asistenciareg_ape_paterno,ape_paterno);
        contentValues.put(SQLConstantes.asistenciareg_ape_materno,ape_materno);
        contentValues.put(SQLConstantes.asistenciareg_naula,naula);
        contentValues.put(SQLConstantes.asistenciareg_discapacidad,discapacidad);
        contentValues.put(SQLConstantes.asistenciareg_prioridad,prioridad);
        contentValues.put(SQLConstantes.asistenciareg_idnacional,idnacional);
        contentValues.put(SQLConstantes.asistenciareg_idsede,idsede);
        contentValues.put(SQLConstantes.asistenciareg_nom_sede,nom_sede);
        contentValues.put(SQLConstantes.asistenciareg_ccdd,ccdd);
        contentValues.put(SQLConstantes.asistenciareg_departamento,departamento);
        contentValues.put(SQLConstantes.asistenciareg_idlocal,idlocal);
        contentValues.put(SQLConstantes.asistenciareg_nom_local,nom_local);
        contentValues.put(SQLConstantes.asistenciareg_direccion,direccion);
        contentValues.put(SQLConstantes.asistenciareg_dia_local,dia_local);
        contentValues.put(SQLConstantes.asistenciareg_mes_local,mes_local);
        contentValues.put(SQLConstantes.asistenciareg_anio_local,anio_local);
        contentValues.put(SQLConstantes.asistenciareg_hora_local,hora_local);
        contentValues.put(SQLConstantes.asistenciareg_min_local,min_local);
        contentValues.put(SQLConstantes.asistenciareg_seg_local,seg_local);
        contentValues.put(SQLConstantes.asistenciareg_estado_local,estado_local);
        contentValues.put(SQLConstantes.asistenciareg_dia_aula,dia_aula);
        contentValues.put(SQLConstantes.asistenciareg_mes_aula,mes_aula);
        contentValues.put(SQLConstantes.asistenciareg_anio_aula,anio_aula);
        contentValues.put(SQLConstantes.asistenciareg_hora_aula,hora_aula);
        contentValues.put(SQLConstantes.asistenciareg_min_aula,min_aula);
        contentValues.put(SQLConstantes.asistenciareg_seg_aula,seg_aula);
        contentValues.put(SQLConstantes.asistenciareg_estado_aula,estado_aula);
        return contentValues;
    }
}
