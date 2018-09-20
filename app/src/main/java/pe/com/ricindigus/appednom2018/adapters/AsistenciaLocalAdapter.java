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
import pe.com.ricindigus.appednom2018.modelo.AsistenciaReg;

public class AsistenciaLocalAdapter extends RecyclerView.Adapter<AsistenciaLocalAdapter.ViewHolder>{
    ArrayList<AsistenciaReg> asistenciaLocals;
    Context context;

    public AsistenciaLocalAdapter(ArrayList<AsistenciaReg> asistenciaLocals, Context context) {
        this.asistenciaLocals = asistenciaLocals;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asistencia,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AsistenciaReg asistenciaLocal = asistenciaLocals.get(position);
        holder.txtDni.setText(asistenciaLocal.getDni());
        holder.txtNombres.setText(asistenciaLocal.getNombres() + " " + asistenciaLocal.getApe_paterno() + " " + asistenciaLocal.getApe_materno());
        holder.txtAula.setText(asistenciaLocal.getNaula()+"");
        holder.txtFecha.setText(checkDigito(asistenciaLocal.getDia_local()) + "-"
                + checkDigito(asistenciaLocal.getMes_local()) + "-" + checkDigito(asistenciaLocal.getAnio_local()) + " "
                + checkDigito(asistenciaLocal.getHora_local()) + ":" + checkDigito(asistenciaLocal.getMin_local())+ ":" + checkDigito(asistenciaLocal.getSeg_local()));
        if(asistenciaLocal.getEstado_local() == 1) holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.greenPrimaryLight));
        else holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.redPrimaryLight));
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public int getItemCount() {
        return asistenciaLocals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDni;
        TextView txtNombres;
        TextView txtAula;
        TextView txtFecha;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.item_asistencia_cv);
            txtDni = itemView.findViewById(R.id.item_asistencia_txtDni);
            txtNombres = itemView.findViewById(R.id.item_asistencia_txtNombres);
            txtAula = itemView.findViewById(R.id.item_asistencia_txtAula);
            txtFecha = itemView.findViewById(R.id.item_asistencia_txtFecha);
        }
    }
}
