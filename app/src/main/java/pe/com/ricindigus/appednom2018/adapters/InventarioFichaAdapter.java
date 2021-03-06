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

public class InventarioFichaAdapter extends RecyclerView.Adapter<InventarioFichaAdapter.ViewHolder>{
    ArrayList<InventarioReg> fichas;
    Context context;

    public InventarioFichaAdapter(ArrayList<InventarioReg> fichas, Context context) {
        this.fichas = fichas;
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
        InventarioReg ficha = fichas.get(position);
        holder.txtDni.setText(ficha.getDni());
        holder.txtNombres.setText(ficha.getNombres() + " " + ficha.getApe_paterno() + " " + ficha.getApe_materno());
        holder.txtCodigo.setText(ficha.getCodigo());
        holder.txtFecha.setText(checkDigito(ficha.getDia()) + "-"
                + checkDigito(ficha.getMes()) + "-" + checkDigito(ficha.getAnio()) + " "
                + checkDigito(ficha.getHora()) + ":" + checkDigito(ficha.getMin())+ ":" + checkDigito(ficha.getSeg()));

        if(ficha.getEstado() == 2) holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.greenPrimaryLight));
        else if(ficha.getEstado() == 1) holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.amberPrimaryLight));
        else holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.redPrimaryLight));
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public int getItemCount() {
        return fichas.size();
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
