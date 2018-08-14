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

public class AsistenciaAdapter extends RecyclerView.Adapter<AsistenciaAdapter.ViewHolder>{
    ArrayList<Asistencia> asistencias;
    Context context;

    public AsistenciaAdapter(ArrayList<Asistencia> asistencias, Context context) {
        this.asistencias = asistencias;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registrado,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Asistencia asistencia = asistencias.get(position);
//        holder.txtDni.setText(asistencia.getCodigo());
//        holder.txtNombres.setText(asistencia.getNombres());
//        holder.txtSede.setText(asistencia.getSede());
//        holder.txtAula.setText(asistencia.getAula());
//        holder.txtFecha.setText(checkDigito(asistencia.getDia()) + "-" + checkDigito(asistencia.getMes()) + "-" + checkDigito(asistencia.getAnio()));
//        holder.txtEntrada.setText(checkDigito(asistencia.getHoraEntrada()) + ":" + checkDigito(asistencia.getMinutoEntrada()));
//        holder.txtSalida.setText(checkDigito(asistencia.getHoraSalida()) + ":" + checkDigito(asistencia.getMinutoSalida()));

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
        TextView txtSede;
        TextView txtAula;
        TextView txtFecha;
        TextView txtEntrada;
        TextView txtSalida;

        CardView cv;
        public ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.item_registrado_cv);
            txtDni = itemView.findViewById(R.id.item_registrado_txtDni);
            txtNombres = itemView.findViewById(R.id.item_registrado_txtNombres);
            txtSede = itemView.findViewById(R.id.item_registrado_txtSede);
            txtAula = itemView.findViewById(R.id.item_registrado_txtAula);
            txtFecha = itemView.findViewById(R.id.item_registrado_txtFecha);
            txtEntrada = itemView.findViewById(R.id.item_registrado_txtEntrada);
            txtSalida = itemView.findViewById(R.id.item_registrado_txtSalida);
        }
    }
}
