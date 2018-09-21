package pe.com.ricindigus.appednom2018.fragments.reportes.cuadro_resumen;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class CuadroResumenCajasInFragment extends Fragment {

    Context context;
    int nroLocal;
    TextView txtAplicacion;
    TextView txtAdicionales;
    TextView txtCandado;
    TextView txtTotal;



    public CuadroResumenCajasInFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CuadroResumenCajasInFragment(Context context, int nroLocal) {
        this.context = context;
        this.nroLocal = nroLocal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cuadro_resumen_cajas_in, container, false);
        txtAplicacion = (TextView) rootView.findViewById(R.id.resumen_cajas_txtAplicacion);
        txtAdicionales = (TextView) rootView.findViewById(R.id.resumen_cajas_txtAdicional);
        txtCandado = (TextView) rootView.findViewById(R.id.resumen_cajas_txtCandado);
        txtTotal = (TextView) rootView.findViewById(R.id.resumen_cajas_txtTotal);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data data =  new Data(context);
        data.open();
        int ap = data.getNroCajasEntradaLeidasxTipo(nroLocal,1);
        int ad = data.getNroCajasEntradaLeidasxTipo(nroLocal,2);
        int cand = data.getNroCajasEntradaLeidasxTipo(nroLocal,3);
        txtAplicacion.setText(ap+"");
        txtAdicionales.setText(ad+"");
        txtCandado.setText(cand+"");
        txtTotal.setText((ap+ad+cand)+"");
        data.close();
    }
}
