package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class ResumenInventario {
    private int _id;
    private int id_local;
    private int id_aula;
    private int nro_invfichas;
    private int nro_invcartillas;
    private int nro_invlistados;

    public ResumenInventario(int id_local, int id_aula, int nro_invfichas, int nro_invcartillas, int nro_invlistados) {
        this._id = -1;
        this.id_local = id_local;
        this.id_aula = id_aula;
        this.nro_invfichas = nro_invfichas;
        this.nro_invcartillas = nro_invcartillas;
        this.nro_invlistados = nro_invlistados;
    }

    public ResumenInventario() {
        this._id = -1;
        this.id_local = 0;
        this.id_aula = 0;
        this.nro_invfichas = 0;
        this.nro_invcartillas = 0;
        this.nro_invlistados = 0;
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

    public int getNro_invfichas() {
        return nro_invfichas;
    }

    public void setNro_invfichas(int nro_invfichas) {
        this.nro_invfichas = nro_invfichas;
    }

    public int getNro_invcartillas() {
        return nro_invcartillas;
    }

    public void setNro_invcartillas(int nro_invcartillas) {
        this.nro_invcartillas = nro_invcartillas;
    }

    public int getNro_invlistados() {
        return nro_invlistados;
    }

    public void setNro_invlistados(int nro_invlistados) {
        this.nro_invlistados = nro_invlistados;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.resumen_inventario_id,_id);
        contentValues.put(SQLConstantes.resumen_inventario_id_aula,id_aula);
        contentValues.put(SQLConstantes.resumen_inventario_id_local,id_local);
        contentValues.put(SQLConstantes.resumen_inventario_nro_invcartillas,nro_invcartillas);
        contentValues.put(SQLConstantes.resumen_inventario_nro_invfichas,nro_invfichas);
        contentValues.put(SQLConstantes.resumen_inventario_nro_invlistados,nro_invlistados);
        return contentValues;
    }
}
