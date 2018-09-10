package pe.com.ricindigus.appednom2018.fragments.registro_control_asistencia;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Asistencia;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaAula;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsistAulaFragment extends Fragment {

    Spinner spAulas;
    EditText edtDni;
    ImageView btnBuscar;
    Context context;
    int nroLocal;
    String usuario;

    TextView correctoTxtDni;
    TextView correctoTxtNombre;

    TextView errorLocalTxtSede;
    TextView errorLocalTxtLocal;
    TextView errorLocalTxtAula;
    TextView errorLocalTxtDireccion;
    TextView errorLocalTxtDni;

    TextView yaRegistradoTxtDni;
    TextView yaRegistradoTxtNombre;
    TextView yaRegistradoTxtAula;
    TextView yaRegistradoTxtFecha;

    LinearLayout lytCorrecto;
    LinearLayout lytErrorLocal;
    LinearLayout lytYaRegistrado;
    LinearLayout lytErrorDni;


    public AsistAulaFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AsistAulaFragment(int nroLocal, Context context, String usuario) {
        this.context = context;
        this.nroLocal = nroLocal;
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_asist_aula, container, false);
        spAulas = (Spinner) rootView.findViewById(R.id.asistencia_aula_spAula);
        edtDni = (EditText) rootView.findViewById(R.id.asistencia_aula_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.asistencia_aula_btnBuscar);

        correctoTxtDni = (TextView) rootView.findViewById(R.id.dni_correcto_txtDni);
        correctoTxtNombre = (TextView) rootView.findViewById(R.id.dni_correcto_txtNombre);

        errorLocalTxtSede = (TextView) rootView.findViewById(R.id.error_local_txtSede);
        errorLocalTxtLocal = (TextView) rootView.findViewById(R.id.error_local_txtLocal);
        errorLocalTxtAula = (TextView) rootView.findViewById(R.id.error_local_txtAula);
        errorLocalTxtDireccion = (TextView) rootView.findViewById(R.id.error_local_txtDireccion);
        errorLocalTxtDni = (TextView) rootView.findViewById(R.id.error_local_txtDni);

        yaRegistradoTxtDni = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtDni);
        yaRegistradoTxtNombre = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtNombre);
        yaRegistradoTxtAula = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtAula);
        yaRegistradoTxtFecha = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtFechaHora);

        lytCorrecto = (LinearLayout) rootView.findViewById(R.id.asistencia_local_lytCorrecto);
        lytErrorLocal = (LinearLayout) rootView.findViewById(R.id.asistencia_local_lytErrorLocal);
        lytYaRegistrado = (LinearLayout) rootView.findViewById(R.id.asistencia_local_lytYaRegistrado);
        lytErrorDni = (LinearLayout) rootView.findViewById(R.id.asistencia_local_ErrorDni);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (context != null){
            Data data =  new Data(context);
            data.open();
            ArrayList<String> aulas =  data.getArrayAulas(nroLocal);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, aulas);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spAulas.setAdapter(dataAdapter);
            data.close();
        }

//        edtDni.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.length() == 8) clickBoton();
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//
//            @Override
//            public void afterTextChanged(Editable s) { }
//        });

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
        Asistencia asistencia = data.getAsistenciaxDni(dni);
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        nroAula = data.getNumeroAula(aula,nroLocal);
        if(asistencia == null) mostrarErrorDni();
        else{
            if(asistencia.getNaula() == nroAula && asistencia.getIdlocal() == nroLocal){
                AsistenciaAula asistenciaAula = data.getAsistenciaAula(asistencia.getDni(),asistencia.getNaula());
                if(asistenciaAula == null) registrarAsistencia(asistencia);
                else mostrarYaRegistrado(asistenciaAula.getDni(),asistenciaAula.getNombres() + " " + asistenciaAula.getApe_paterno() + " " + asistenciaAula.getApe_materno(),asistenciaAula.getNaula(),
                        checkDigito(asistenciaAula.getDia()) +"/"+ checkDigito(asistenciaAula.getMes()) +"/"+ asistenciaAula.getAnio() +
                                " " + checkDigito(asistenciaAula.getHora()) + ":" + checkDigito(asistenciaAula.getMin()) + ":" + checkDigito(asistenciaAula.getSeg()));
            }
            else mostrarErrorAula(asistencia.getDni(),asistencia.getSede(),asistencia.getLocal(),asistencia.getDireccion(),asistencia.getNaula());
        }
        edtDni.setText("");
        edtDni.requestFocus();
        data.close();
    }
    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void registrarAsistencia(Asistencia asistencia){
        Data data = new Data(context);
        data.open();
        Calendar calendario = Calendar.getInstance();
        int yy = calendario.get(Calendar.YEAR);
        int mm = calendario.get(Calendar.MONTH)+1;
        int dd = calendario.get(Calendar.DAY_OF_MONTH);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);
        int seg = calendario.get(Calendar.SECOND);
        AsistenciaAula asistenciaAula = new AsistenciaAula();
        asistenciaAula.setDni(asistencia.getDni());
        asistenciaAula.setIdnacional(asistencia.getIdnacional());
        asistenciaAula.setCcdd(asistencia.getCcdd());
        asistenciaAula.setIdsede(asistencia.getIdsede());
        asistenciaAula.setSede(asistencia.getSede());
        asistenciaAula.setIdlocal(asistencia.getIdlocal());
        asistenciaAula.setLocal(asistencia.getLocal());
        asistenciaAula.setDireccion(asistencia.getDireccion());
        asistenciaAula.setNombres(asistencia.getNombres());
        asistenciaAula.setApe_materno(asistencia.getApe_materno());
        asistenciaAula.setApe_paterno(asistencia.getApe_paterno());
        asistenciaAula.setNaula(asistencia.getNaula());
        asistenciaAula.setDiscapacidad(asistencia.getDiscapacidad());
        asistenciaAula.setPrioridad(asistencia.getPrioridad());
        asistenciaAula.setDia(dd);
        asistenciaAula.setMes(mm);
        asistenciaAula.setAnio(yy);
        asistenciaAula.setHora(hora);
        asistenciaAula.setMin(minuto);
        asistenciaAula.setSeg(seg);
        asistenciaAula.setEstado(0);
        data.insertarAsistenciaAula(asistenciaAula);
        data.close();
        mostrarCorrecto(asistenciaAula.getDni(),asistenciaAula.getNombres() +" "+ asistenciaAula.getApe_paterno() +" "+ asistenciaAula.getApe_materno());

        final String c = asistenciaAula.getDni();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("asistencia").document(asistenciaAula.getDni());
        batch.update(documentReference, "check_registro_aula", 1);
        batch.update(documentReference, "fecha_transferencia_aula", FieldValue.serverTimestamp());
        batch.update(documentReference, "usuario_reg", usuario);
        batch.update(documentReference, "fecha_registro_aula",
                new Timestamp(new Date(asistenciaAula.getAnio()-1900,asistenciaAula.getMes()-1,asistenciaAula.getDia(),
                        asistenciaAula.getHora(),asistenciaAula.getMin(),asistenciaAula.getSeg())));
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data data = new Data(context);
                data.open();
                data.actualizarAsistenciaAulaSubido(c);
                data.close();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostrarCorrecto(String dni, String nombre){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        correctoTxtDni.setText(dni);
        correctoTxtNombre.setText(nombre);
    }
    public void mostrarErrorDni(){
        lytErrorDni.setVisibility(View.VISIBLE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
    }
    public void mostrarErrorAula(String dni, String sede, String local, String direccion, int aula){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.VISIBLE);
        lytCorrecto.setVisibility(View.GONE);
        errorLocalTxtDni.setText(dni);
        errorLocalTxtSede.setText(sede);
        errorLocalTxtLocal.setText(local);
        errorLocalTxtAula.setText(aula+"");
        errorLocalTxtDireccion.setText(direccion);
    }
    public void mostrarYaRegistrado(String dni, String nombre, int aula, String fecha){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.VISIBLE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        yaRegistradoTxtDni.setText(dni);
        yaRegistradoTxtNombre.setText(nombre);
        yaRegistradoTxtAula.setText(""+aula);
        yaRegistradoTxtFecha.setText(fecha);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
