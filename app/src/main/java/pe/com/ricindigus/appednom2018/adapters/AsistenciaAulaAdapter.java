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
import pe.com.ricindigus.appednom2018.modelo.AsistenciaAula;

public class AsistenciaAulaAdapter extends RecyclerView.Adapter<AsistenciaAulaAdapter.ViewHolder>{
    ArrayList<AsistenciaAula> asistenciaAulas;
    Context context;

    public AsistenciaAulaAdapter(ArrayList<AsistenciaAula> asistenciaAulas, Context context) {
        this.asistenciaAulas = asistenciaAulas;
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
        AsistenciaAula asistenciaAula = asistenciaAulas.get(position);
        holder.txtDni.setText(asistenciaAula.getDni());
        holder.txtNombres.setText(asistenciaAula.getNombres() + " " + asistenciaAula.getApe_paterno() + " " + asistenciaAula.getApe_materno());
        holder.txtAula.setText(asistenciaAula.getNaula()+"");
        holder.txtFecha.setText(checkDigito(asistenciaAula.getDia()) + "-"
                + checkDigito(asistenciaAula.getMes()) + "-" + checkDigito(asistenciaAula.getAnio()) + " "
                + checkDigito(asistenciaAula.getHora()) + ":" + checkDigito(asistenciaAula.getMin())+ ":" + checkDigito(asistenciaAula.getSeg()));
        if(asistenciaAula.getEstado() == 1) holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.greenPrimaryLight));
        else holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.redPrimaryLight));
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public int getItemCount() {
        return asistenciaAulas.size();
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
