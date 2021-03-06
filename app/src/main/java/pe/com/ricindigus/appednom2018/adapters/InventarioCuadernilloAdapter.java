package pe.com.ricindigus.appednom2018.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.InventarioReg;

public class InventarioCuadernilloAdapter extends RecyclerView.Adapter<InventarioCuadernilloAdapter.ViewHolder>{
    ArrayList<InventarioReg> cuadernillos;
    Context context;

    public InventarioCuadernilloAdapter(ArrayList<InventarioReg> cuadernillos, Context context) {
        this.cuadernillos = cuadernillos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventario,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        InventarioReg cuadernillo = cuadernillos.get(position);
        holder.txtDni.setText(cuadernillo.getDni());
        holder.txtNombres.setText(cuadernillo.getNombres() + " " + cuadernillo.getApe_paterno() + " " + cuadernillo.getApe_materno());
        holder.txtCodigo.setText(cuadernillo.getCodigo());
        holder.txtFecha.setText(checkDigito(cuadernillo.getDia()) + "-"
                + checkDigito(cuadernillo.getMes()) + "-" + checkDigito(cuadernillo.getAnio()) + " "
                + checkDigito(cuadernillo.getHora()) + ":" + checkDigito(cuadernillo.getMin())+ ":" + checkDigito(cuadernillo.getSeg()));

        if(cuadernillo.getEstado() == 2) holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.greenPrimaryLight));
        else if(cuadernillo.getEstado() == 1) holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.amberPrimaryLight));
        else holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.redPrimaryLight));
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public int getItemCount() {
        return cuadernillos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDni;
        TextView txtNombres;
        TextView txtCodigo;
        TextView txtFecha;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.item_inventario_cv);
            txtDni = itemView.findViewById(R.id.item_inventario_txtDni);
            txtNombres = itemView.findViewById(R.id.item_inventario_txtNombres);
            txtCodigo = itemView.findViewById(R.id.item_inventario_txtCodigo);
            txtFecha = itemView.findViewById(R.id.item_inventario_txtFecha);
        }
    }
}
