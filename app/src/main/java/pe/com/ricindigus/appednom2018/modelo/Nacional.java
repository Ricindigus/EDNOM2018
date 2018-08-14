package pe.com.ricindigus.appednom2018.modelo;

public class Nacional {
    private String _id;
    private String sede;
    private int nro_local;
    private String local_aplicacion;
    private String aula;
    private String codigo_pagina;
    private String ins_numdoc;
    private String apepat;
    private String apemat;
    private String nombres;
    private int estatus;
    private int s_aula;
    private int s_ficha;
    private int s_cartilla;
    private int s_listaasistencia;
    private int id_local;
    private int id_aula;
    private String direccion;
    private String codficha;
    private String codcartilla;
    private String new_aula;
    private String new_local;
    private String tipo;
    private String discapacidad;
    private String version;
    private String tipo_concurso;
    private int estatus2;
    private int estatus3;
    private String new_aula_ficha;
    private String new_aula_cartilla;


    public Nacional(String _id, String sede, int nro_local, String local_aplicacion, String aula, String codigo_pagina, String ins_numdoc, String apepat, String apemat, String nombres, int estatus, int s_aula, int s_ficha, int s_cartilla, int s_listaasistencia, int id_local, int id_aula, String direccion, String codficha, String codcartilla, String new_aula, String new_local, String tipo, String discapacidad, String version, String tipo_concurso, int estatus2, int estatus3, String new_aula_ficha, String new_aula_cartilla) {
        this._id = _id;
        this.sede = sede;
        this.nro_local = nro_local;
        this.local_aplicacion = local_aplicacion;
        this.aula = aula;
        this.codigo_pagina = codigo_pagina;
        this.ins_numdoc = ins_numdoc;
        this.apepat = apepat;
        this.apemat = apemat;
        this.nombres = nombres;
        this.estatus = estatus;
        this.s_aula = s_aula;
        this.s_ficha = s_ficha;
        this.s_cartilla = s_cartilla;
        this.s_listaasistencia = s_listaasistencia;
        this.id_local = id_local;
        this.id_aula = id_aula;
        this.direccion = direccion;
        this.codficha = codficha;
        this.codcartilla = codcartilla;
        this.new_aula = new_aula;
        this.new_local = new_local;
        this.tipo = tipo;
        this.discapacidad = discapacidad;
        this.version = version;
        this.tipo_concurso = tipo_concurso;
        this.estatus2 = estatus2;
        this.estatus3 = estatus3;
        this.new_aula_ficha = new_aula_ficha;
        this.new_aula_cartilla = new_aula_cartilla;
    }

    public Nacional() {
        this._id = "";
        this.sede = "";
        this.nro_local = 0;
        this.local_aplicacion = "";
        this.aula = "";
        this.codigo_pagina = "";
        this.ins_numdoc = "";
        this.apepat = "";
        this.apemat = "";
        this.nombres = "";
        this.estatus = 0;
        this.s_aula = 0;
        this.s_ficha = 0;
        this.s_cartilla = 0;
        this.s_listaasistencia = 0;
        this.id_local = 0;
        this.id_aula = 0;
        this.direccion = "";
        this.codficha = "";
        this.codcartilla = "";
        this.new_aula = "";
        this.new_local = "";
        this.tipo = "";
        this.discapacidad = "";
        this.version = "";
        this.tipo_concurso = "";
        this.estatus2 = 0;
        this.estatus3 = 0;
        this.new_aula_ficha = "";
        this.new_aula_cartilla = "";
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public int getNro_local() {
        return nro_local;
    }

    public void setNro_local(int nro_local) {
        this.nro_local = nro_local;
    }

    public String getLocal_aplicacion() {
        return local_aplicacion;
    }

    public void setLocal_aplicacion(String local_aplicacion) {
        this.local_aplicacion = local_aplicacion;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getCodigo_pagina() {
        return codigo_pagina;
    }

    public void setCodigo_pagina(String codigo_pagina) {
        this.codigo_pagina = codigo_pagina;
    }

    public String getIns_numdoc() {
        return ins_numdoc;
    }

    public void setIns_numdoc(String ins_numdoc) {
        this.ins_numdoc = ins_numdoc;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public int getS_aula() {
        return s_aula;
    }

    public void setS_aula(int s_aula) {
        this.s_aula = s_aula;
    }

    public int getS_ficha() {
        return s_ficha;
    }

    public void setS_ficha(int s_ficha) {
        this.s_ficha = s_ficha;
    }

    public int getS_cartilla() {
        return s_cartilla;
    }

    public void setS_cartilla(int s_cartilla) {
        this.s_cartilla = s_cartilla;
    }

    public int getS_listaasistencia() {
        return s_listaasistencia;
    }

    public void setS_listaasistencia(int s_listaasistencia) {
        this.s_listaasistencia = s_listaasistencia;
    }

    public int getId_local() {
        return id_local;
    }

    public void setId_local(int id_local) {
        this.id_local = id_local;
    }

    public int getId_aula() {
        return id_aula;
    }

    public void setId_aula(int id_aula) {
        this.id_aula = id_aula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodficha() {
        return codficha;
    }

    public void setCodficha(String codficha) {
        this.codficha = codficha;
    }

    public String getCodcartilla() {
        return codcartilla;
    }

    public void setCodcartilla(String codcartilla) {
        this.codcartilla = codcartilla;
    }

    public String getNew_aula() {
        return new_aula;
    }

    public void setNew_aula(String new_aula) {
        this.new_aula = new_aula;
    }

    public String getNew_local() {
        return new_local;
    }

    public void setNew_local(String new_local) {
        this.new_local = new_local;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTipo_concurso() {
        return tipo_concurso;
    }

    public void setTipo_concurso(String tipo_concurso) {
        this.tipo_concurso = tipo_concurso;
    }

    public int getEstatus2() {
        return estatus2;
    }

    public void setEstatus2(int estatus2) {
        this.estatus2 = estatus2;
    }

    public int getEstatus3() {
        return estatus3;
    }

    public void setEstatus3(int estatus3) {
        this.estatus3 = estatus3;
    }

    public String getNew_aula_ficha() {
        return new_aula_ficha;
    }

    public void setNew_aula_ficha(String new_aula_ficha) {
        this.new_aula_ficha = new_aula_ficha;
    }

    public String getNew_aula_cartilla() {
        return new_aula_cartilla;
    }

    public void setNew_aula_cartilla(String new_aula_cartilla) {
        this.new_aula_cartilla = new_aula_cartilla;
    }
}
