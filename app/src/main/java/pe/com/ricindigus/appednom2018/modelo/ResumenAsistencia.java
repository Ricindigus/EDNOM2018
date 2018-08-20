package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class ResumenAsistencia {
    private int _id;
    private int id_local;
    private int id_aula;
    private int nro_asislocal;
    private int nro_asisaula;

    public ResumenAsistencia(int id_local, int id_aula, int nro_asislocal, int nro_asisaula) {
        this._id = -1;
        this.id_local = id_local;
        this.id_aula = id_aula;
        this.nro_asislocal = nro_asislocal;
        this.nro_asisaula = nro_asisaula;
    }

    public ResumenAsistencia() {
        this._id = -1;
        this.id_local = 0;
        this.id_aula = 0;
        this.nro_asislocal = 0;
        this.nro_asisaula = 0;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public int getNro_asislocal() {
        return nro_asislocal;
    }

    public void setNro_asislocal(int nro_asislocal) {
        this.nro_asislocal = nro_asislocal;
    }

    public int getNro_asisaula() {
        return nro_asisaula;
    }

    public void setNro_asisaula(int nro_asisaula) {
        this.nro_asisaula = nro_asisaula;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.resumen_asistencia_id,_id);
        contentValues.put(SQLConstantes.resumen_asistencia_id_aula,id_aula);
        contentValues.put(SQLConstantes.resumen_asistencia_id_local,id_local);
        contentValues.put(SQLConstantes.resumen_asistencia_nro_asisaula,nro_asisaula);
        contentValues.put(SQLConstantes.resumen_asistencia_nro_asislocal,nro_asislocal);
        return contentValues;
    }
}
