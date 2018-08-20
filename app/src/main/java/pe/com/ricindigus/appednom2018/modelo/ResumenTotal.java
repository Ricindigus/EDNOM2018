package pe.com.ricindigus.appednom2018.modelo;

import android.content.ContentValues;

public class ResumenTotal {
    private int _id;
    private int cantidad;

    public ResumenTotal(int _id, int cantidad) {
        this._id = _id;
        this.cantidad = cantidad;
    }

    public ResumenTotal() {
        this._id = -1;
        this.cantidad = 0;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.resumen_total_id,_id);
        contentValues.put(SQLConstantes.resumen_total_cantidad,cantidad);
        return contentValues;
    }
}
