package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class AsistenciaRaReg {
    private int id;
    private String ccdd;
    private String departamento;
    private String idsede;
    private String nom_sede;
    private int idnacional;
    private int idlocal;
    private String nom_local;
    private int red;
    private int tipo_cargo;
    private String nombre_cargo;
    private String dni;
    private String nombres_completos;
    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int min;
    private int seg;
    private int estado;


    public AsistenciaRaReg(int id, String ccdd, String departamento, String idsede, String nom_sede, int idnacional, int idlocal, String nom_local, int red, int tipo_cargo, String nombre_cargo, String dni, String nombres_completos, int dia, int mes, int anio, int hora, int min, int seg, int estado) {
        this.id = id;
        this.ccdd = ccdd;
        this.departamento = departamento;
        this.idsede = idsede;
        this.nom_sede = nom_sede;
        this.idnacional = idnacional;
        this.idlocal = idlocal;
        this.nom_local = nom_local;
        this.red = red;
        this.tipo_cargo = tipo_cargo;
        this.nombre_cargo = nombre_cargo;
        this.dni = dni;
        this.nombres_completos = nombres_completos;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.min = min;
        this.seg = seg;
        this.estado = estado;
    }

    public AsistenciaRaReg() {
        this.dia = 0;
        this.mes = 0;
        this.anio = 0;
        this.hora = 0;
        this.min = 0;
        this.seg = 0;
        this.estado = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIdnacional() {
        return idnacional;
    }

    public void setIdnacional(int idnacional) {
        this.idnacional = idnacional;
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

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getTipo_cargo() {
        return tipo_cargo;
    }

    public void setTipo_cargo(int tipo_cargo) {
        this.tipo_cargo = tipo_cargo;
    }

    public String getNombre_cargo() {
        return nombre_cargo;
    }

    public void setNombre_cargo(String nombre_cargo) {
        this.nombre_cargo = nombre_cargo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres_completos() {
        return nombres_completos;
    }

    public void setNombres_completos(String nombres_completos) {
        this.nombres_completos = nombres_completos;
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
        contentValues.put(SQLConstantes.asistenciareg_ra_ccdd,ccdd);
        contentValues.put(SQLConstantes.asistenciareg_ra_departamento,departamento);
        contentValues.put(SQLConstantes.asistenciareg_ra_idsede,idsede);
        contentValues.put(SQLConstantes.asistenciareg_ra_nom_sede,nom_sede);
        contentValues.put(SQLConstantes.asistenciareg_ra_idnacional,idnacional);
        contentValues.put(SQLConstantes.asistenciareg_ra_idlocal,idlocal);
        contentValues.put(SQLConstantes.asistenciareg_ra_nom_local,nom_local);
        contentValues.put(SQLConstantes.asistenciareg_ra_red,red);
        contentValues.put(SQLConstantes.asistenciareg_ra_tipo_cargo,tipo_cargo);
        contentValues.put(SQLConstantes.asistenciareg_ra_nombre_cargo,nombre_cargo);
        contentValues.put(SQLConstantes.asistenciareg_ra_dni,dni);
        contentValues.put(SQLConstantes.asistenciareg_ra_nombres_completos,nombres_completos);
        contentValues.put(SQLConstantes.asistenciareg_ra_dia,dia);
        contentValues.put(SQLConstantes.asistenciareg_ra_mes,mes);
        contentValues.put(SQLConstantes.asistenciareg_ra_anio,anio);
        contentValues.put(SQLConstantes.asistenciareg_ra_hora,hora);
        contentValues.put(SQLConstantes.asistenciareg_ra_min,min);
        contentValues.put(SQLConstantes.asistenciareg_ra_seg,seg);
        contentValues.put(SQLConstantes.asistenciareg_ra_estado,estado);
        return contentValues;
    }
}
