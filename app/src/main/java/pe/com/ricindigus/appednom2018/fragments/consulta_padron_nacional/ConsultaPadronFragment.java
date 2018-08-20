package pe.com.ricindigus.appednom2018.fragments.consulta_padron_nacional;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaLocal;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Nacional;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultaPadronFragment extends Fragment {
    TextView correctoTxtDni;
    TextView correctoTxtNombre;
    TextView correctoTxtSede;
    TextView correctoTxtLocal;
    TextView correctoTxtAula;
    LinearLayout lytCorrecto;
    LinearLayout lytErrorDni;


    EditText edtDni;
    ImageView btnBuscar;

    int numeroLocal;
    Context context;

    public ConsultaPadronFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_consulta_padron, container, false);
        correctoTxtDni = (TextView) rootView.findViewById(R.id.dni_correcto_txtDni);
        correctoTxtNombre = (TextView) rootView.findViewById(R.id.dni_correcto_txtNombre);
        correctoTxtSede = (TextView) rootView.findViewById(R.id.dni_correcto_txtSede);
        correctoTxtLocal = (TextView) rootView.findViewById(R.id.dni_correcto_txtLocal);
        correctoTxtAula = (TextView) rootView.findViewById(R.id.dni_correcto_txtAula);
        edtDni = (EditText) rootView.findViewById(R.id.asistencia_local_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.asistencia_local_btnBuscar);
        lytCorrecto = (LinearLayout) rootView.findViewById(R.id.asistencia_local_lytCorrecto);
        lytErrorDni = (LinearLayout) rootView.findViewById(R.id.asistencia_local_ErrorDni);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtDni.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 8) clickBoton();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBoton();
            }
        });


    }

    public void clickBoton(){
        ocultarTeclado(edtDni);
        String dni = edtDni.getText().toString();
        Data data = new Data(context);
        data.open();
        Nacional nacional = data.getNacionalxDNI(dni);
        data.close();
        if(nacional == null){
            mostrarErrorDni();
        }else{
            mostrarCorrecto(nacional.getIns_numdoc(),nacional.getNombres() +" "+ nacional.getApepat() +" "+ nacional.getApemat(),nacional.getSede(),nacional.getLocal_aplicacion(),nacional.getId_aula()+"");
        }
        edtDni.setText("");
        edtDni.requestFocus();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void mostrarCorrecto(String dni, String nombre, String sede, String local, String aula){
        lytCorrecto.setVisibility(View.VISIBLE);
        lytErrorDni.setVisibility(View.GONE);
        correctoTxtDni.setText(dni);
        correctoTxtNombre.setText(nombre);
        correctoTxtSede.setText(sede);
        correctoTxtLocal.setText(local);
        correctoTxtAula.setText(aula);
    }
    public void mostrarErrorDni(){
        lytErrorDni.setVisibility(View.VISIBLE);
        lytCorrecto.setVisibility(View.GONE);
    }
}
