package pe.com.ricindigus.appednom2018.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Asistencia;

import java.util.ArrayList;

public class AsistenciaLocalAdapter extends RecyclerView.Adapter<AsistenciaLocalAdapter.ViewHolder>{
    ArrayList<Asistencia> asistencias;
    Context context;

    public AsistenciaLocalAdapter(ArrayList<Asistencia> asistencias, Context context) {
        this.asistencias = asistencias;
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
        Asistencia asistencia = asistencias.get(position);
        holder.txtDni.setText(asistencia.getDni());
        holder.txtNombres.setText(asistencia.getNombres() + " " + asistencia.getApepat() + " " +asistencia.getApemat());
        holder.txtAula.setText(asistencia.getAula());
        holder.txtFecha.setText(checkDigito(asistencia.getLocal_dia()) + "-"
                + checkDigito(asistencia.getAula_mes()) + "-" + checkDigito(asistencia.getLocal_anio()) + " "
                + checkDigito(asistencia.getLocal_hora()) + ":" + checkDigito(asistencia.getLocal_minuto()));

//        if(asistencia.getSubidoEntrada() == 1){
//            holder.cv.setCardBackgroundColor(Color.WHITE);
//        }else{
//            holder.cv.setCardBackgroundColor(Color.rgb(227,242,253));
//        }
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public int getItemCount() {
        return asistencias.size();
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
