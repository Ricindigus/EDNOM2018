package pe.com.ricindigus.appednom2018.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Caja;
import pe.com.ricindigus.appednom2018.modelo.CajaIn;

public class CajaIngresoAdapter extends RecyclerView.Adapter<CajaIngresoAdapter.ViewHolder>{
    ArrayList<CajaIn> cajas;
    Context context;

    public CajaIngresoAdapter(ArrayList<CajaIn> cajas, Context context) {
        this.cajas = cajas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_caja,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CajaIn caja = cajas.get(position);
        holder.txtCodBarra.setText(caja.getCod_barra_caja());
        holder.txtAcl.setText("ACL: " + caja.getAcl());
        holder.txtFecha.setText(checkDigito(caja.getDia()) + "-"
                + checkDigito(caja.getMes()) + "-" + checkDigito(caja.getAnio()) + " "
                + checkDigito(caja.getHora()) + ":" + checkDigito(caja.getMin())+ ":" + checkDigito(caja.getSeg()));

        if(caja.getSubido() == 1){
            holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.greenPrimaryLight));
        }else{
            holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.redPrimaryLight));
        }
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public int getItemCount() {
        return cajas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCodBarra;
        TextView txtAcl;
        TextView txtFecha;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.item_caja_cv);
            txtCodBarra = itemView.findViewById(R.id.item_caja_txtCodBarra);
            txtAcl = itemView.findViewById(R.id.item_caja_txtAcl);
            txtFecha = itemView.findViewById(R.id.item_caja_txtFecha);
        }
    }
}
