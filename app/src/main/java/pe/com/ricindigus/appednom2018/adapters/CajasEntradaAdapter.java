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
import pe.com.ricindigus.appednom2018.modelo.CajaReg;

public class CajasEntradaAdapter extends RecyclerView.Adapter<CajasEntradaAdapter.ViewHolder>{
    ArrayList<CajaReg> cajaRegs;
    Context context;

    public CajasEntradaAdapter(ArrayList<CajaReg> cajaRegs, Context context) {
        this.cajaRegs = cajaRegs;
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
        CajaReg cajaReg = cajaRegs.get(position);
        holder.txtCodBarra.setText(cajaReg.getCod_barra_caja());
        holder.txtAcl.setText("ACL: " + cajaReg.getAcl());
        if(cajaReg.getEstado_entrada() == 3){
            holder.txtFecha.setText(checkDigito(cajaReg.getDia_entrada_final()) + "-"
                    + checkDigito(cajaReg.getMes_entrada_final()) + "-" + checkDigito(cajaReg.getAnio_entrada_final()) + " "
                    + checkDigito(cajaReg.getHora_entrada_final()) + ":" + checkDigito(cajaReg.getMin_entrada_final())+ ":"
                    + checkDigito(cajaReg.getSeg_entrada_final()));
            holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.greenPrimaryLight));
        }else if(cajaReg.getEstado_entrada() == 2){
            holder.txtFecha.setText(checkDigito(cajaReg.getDia_entrada()) + "-"
                    + checkDigito(cajaReg.getMes_entrada_final()) + "-" + checkDigito(cajaReg.getAnio_entrada_final()) + " "
                    + checkDigito(cajaReg.getHora_entrada_final()) + ":" + checkDigito(cajaReg.getMin_entrada_final())+ ":"
                    + checkDigito(cajaReg.getSeg_entrada_final()));
            holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.amberPrimaryLight));
        }else if(cajaReg.getEstado_entrada() == 1){
            holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bluePrimaryLight));
        }else{
            holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.redPrimaryLight));
        }
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public int getItemCount() {
        return cajaRegs.size();
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
