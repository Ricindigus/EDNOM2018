package pe.com.ricindigus.appednom2018.fragments.ingreso_cajas_local;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class CajasInFragment extends Fragment {

    TextView correctoTxtDni;
    TextView correctoTxtNombre;
    TextView correctoTxtSede;
    TextView correctoTxtLocal;
    TextView correctoTxtAula;

    TextView errorLocalTxtSede;
    TextView errorLocalTxtLocal;
    TextView errorLocalTxtAula;
    TextView errorLocalTxtDireccion;


    TextView yaRegistradoTxtDni;
    TextView yaRegistradoTxtNombre;
    TextView yaRegistradoTxtAula;
    TextView yaRegistradoTxtFecha;

    int numeroLocal;
    Context context;


    public CajasInFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CajasInFragment(int numeroLocal, Context context) {
        this.numeroLocal = numeroLocal;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cajas_in, container, false);
        correctoTxtDni = (TextView) rootView.findViewById(R.id.dni_correcto_txtDni);
        correctoTxtNombre = (TextView) rootView.findViewById(R.id.dni_correcto_txtNombre);
        correctoTxtSede = (TextView) rootView.findViewById(R.id.dni_correcto_txtSede);
        correctoTxtLocal = (TextView) rootView.findViewById(R.id.dni_correcto_txtLocal);
        correctoTxtAula = (TextView) rootView.findViewById(R.id.dni_correcto_txtAula);

        errorLocalTxtSede = (TextView) rootView.findViewById(R.id.error_local_txtSede);
        errorLocalTxtLocal = (TextView) rootView.findViewById(R.id.error_local_txtLocal);
        errorLocalTxtAula = (TextView) rootView.findViewById(R.id.error_local_txtAula);
        errorLocalTxtDireccion = (TextView) rootView.findViewById(R.id.error_local_txtDireccion);

        yaRegistradoTxtDni = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtDni);
        yaRegistradoTxtNombre = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtNombre);
        yaRegistradoTxtAula = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtAula);
        yaRegistradoTxtFecha = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtFechaHora);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
