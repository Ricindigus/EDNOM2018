package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class InventarioReg {
    private String _id;
    private String codigo;
    private int tipo;
    private String ccdd;
    private String departamento;
    private int idnacional;
    private String idsede;
    private String nom_sede;
    private int idlocal;
    private String nom_local;
    private String dni;
    private String ape_paterno;
    private String ape_materno;
    private String nombres;
    private int naula;
    private String codpagina;
    private String direccion;
    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int min;
    private int seg;
    private int estado;
    private int npostulantes;

    public InventarioReg(){
        this.dia = 0;
        this.mes = 0;
        this.anio = 0;
        this.hora = 0;
        this.min = 0;
        this.seg = 0;
        this.estado = 0;
        this.npostulantes = 0;
    }

    public InventarioReg(String _id, String codigo, int tipo, String ccdd, String departamento, int idnacional, String idsede, String nom_sede, int idlocal, String nom_local, String dni, String ape_paterno, String ape_materno, String nombres, int naula, String codpagina, String direccion, int dia, int mes, int anio, int hora, int min, int seg, int estado, int npostulantes) {
        this._id = _id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.ccdd = ccdd;
        this.departamento = departamento;
        this.idnacional = idnacional;
        this.idsede = idsede;
        this.nom_sede = nom_sede;
        this.idlocal = idlocal;
        this.nom_local = nom_local;
        this.dni = dni;
        this.ape_paterno = ape_paterno;
        this.ape_materno = ape_materno;
        this.nombres = nombres;
        this.naula = naula;
        this.codpagina = codpagina;
        this.direccion = direccion;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.min = min;
        this.seg = seg;
        this.estado = estado;
        this.npostulantes = npostulantes;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
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

    public int getNpostulantes() {
        return npostulantes;
    }

    public void setNpostulantes(int npostulantes) {
        this.npostulantes = npostulantes;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.inventarioreg_id,_id);
        contentValues.put(SQLConstantes.inventarioreg_codigo,codigo);
        contentValues.put(SQLConstantes.inventarioreg_tipo,tipo);
        contentValues.put(SQLConstantes.inventarioreg_ccdd,ccdd);
        contentValues.put(SQLConstantes.inventarioreg_departamento,departamento);
        contentValues.put(SQLConstantes.inventarioreg_idnacional,idnacional);
        contentValues.put(SQLConstantes.inventarioreg_idsede,idsede);
        contentValues.put(SQLConstantes.inventarioreg_nom_sede,nom_sede);
        contentValues.put(SQLConstantes.inventarioreg_idlocal,idlocal);
        contentValues.put(SQLConstantes.inventarioreg_nom_local,nom_local);
        contentValues.put(SQLConstantes.inventarioreg_dni,dni);
        contentValues.put(SQLConstantes.inventarioreg_ape_paterno,ape_paterno);
        contentValues.put(SQLConstantes.inventarioreg_ape_materno,ape_materno);
        contentValues.put(SQLConstantes.inventarioreg_nombres,nombres);
        contentValues.put(SQLConstantes.inventarioreg_naula,naula);
        contentValues.put(SQLConstantes.inventarioreg_codpagina,codpagina);
        contentValues.put(SQLConstantes.inventarioreg_direccion,direccion);
        contentValues.put(SQLConstantes.inventarioreg_dia,dia);
        contentValues.put(SQLConstantes.inventarioreg_mes,mes);
        contentValues.put(SQLConstantes.inventarioreg_anio,anio);
        contentValues.put(SQLConstantes.inventarioreg_hora,hora);
        contentValues.put(SQLConstantes.inventarioreg_min,min);
        contentValues.put(SQLConstantes.inventarioreg_seg,seg);
        contentValues.put(SQLConstantes.inventarioreg_estado,estado);
        contentValues.put(SQLConstantes.inventarioreg_npostulantes,npostulantes);
        return contentValues;
    }
}
