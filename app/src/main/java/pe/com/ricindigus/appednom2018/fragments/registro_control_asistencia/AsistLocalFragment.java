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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.Calendar;
import java.util.Date;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Asistencia;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaAula;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaLocal;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsistLocalFragment extends Fragment {
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

    LinearLayout lytCorrecto;
    LinearLayout lytErrorLocal;
    LinearLayout lytYaRegistrado;
    LinearLayout lytErrorDni;

    EditText edtDni;
    ImageView btnBuscar;

    int nroLocal;
    Context context;
    String usuario;

    public AsistLocalFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AsistLocalFragment(int nroLocal, Context context, String usuario) {
        this.nroLocal = nroLocal;
        this.context = context;
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_asist_local, container, false);
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

        lytCorrecto = (LinearLayout) rootView.findViewById(R.id.asistencia_local_lytCorrecto);
        lytErrorLocal = (LinearLayout) rootView.findViewById(R.id.asistencia_local_lytErrorLocal);
        lytYaRegistrado = (LinearLayout) rootView.findViewById(R.id.asistencia_local_lytYaRegistrado);
        lytErrorDni = (LinearLayout) rootView.findViewById(R.id.asistencia_local_ErrorDni);


        edtDni = (EditText) rootView.findViewById(R.id.asistencia_local_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.asistencia_local_btnBuscar);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        if(asistencia == null){
            mostrarErrorDni();
        }else{
            if(asistencia.getIdlocal() == nroLocal){
                AsistenciaLocal asistenciaLocal = data.getAsistenciaLocal(asistencia.getDni());
                if (asistenciaLocal == null) registrarAsistencia(asistencia);
                else mostrarYaRegistrado(asistenciaLocal.getDni(),asistenciaLocal.getNombres() + " " + asistenciaLocal.getApe_paterno() + " " + asistenciaLocal.getApe_materno(),asistenciaLocal.getNaula(),
                        checkDigito(asistenciaLocal.getDia()) +"/"+ checkDigito(asistenciaLocal.getMes()) +"/"+ asistenciaLocal.getAnio() +
                                " " + checkDigito(asistenciaLocal.getHora()) + ":" + checkDigito(asistenciaLocal.getMin()) + ":" + checkDigito(asistenciaLocal.getSeg()));
            }
            else mostrarErrorLocal(asistencia.getSede(),asistencia.getLocal(),asistencia.getDireccion(),""+asistencia.getNaula());
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
        AsistenciaLocal asistenciaLocal = new AsistenciaLocal();
        asistenciaLocal.setDni(asistencia.getDni());
        asistenciaLocal.setIdnacional(asistencia.getIdnacional());
        asistenciaLocal.setCcdd(asistencia.getCcdd());
        asistenciaLocal.setIdsede(asistencia.getIdsede());
        asistenciaLocal.setSede(asistencia.getSede());
        asistenciaLocal.setIdlocal(asistencia.getIdlocal());
        asistenciaLocal.setLocal(asistencia.getLocal());
        asistenciaLocal.setDireccion(asistencia.getDireccion());
        asistenciaLocal.setNombres(asistencia.getNombres());
        asistenciaLocal.setApe_materno(asistencia.getApe_materno());
        asistenciaLocal.setApe_paterno(asistencia.getApe_paterno());
        asistenciaLocal.setNaula(asistencia.getNaula());
        asistenciaLocal.setDiscapacidad(asistencia.getDiscapacidad());
        asistenciaLocal.setPrioridad(asistencia.getPrioridad());
        asistenciaLocal.setDia(dd);
        asistenciaLocal.setMes(mm);
        asistenciaLocal.setAnio(yy);
        asistenciaLocal.setHora(hora);
        asistenciaLocal.setMin(minuto);
        asistenciaLocal.setSeg(seg);
        asistenciaLocal.setEstado(0);
        data.insertarAsistenciaLocal(asistenciaLocal);
        data.close();
        mostrarCorrecto(asistenciaLocal.getDni(),asistenciaLocal.getNombres() +" "+ asistenciaLocal.getApe_paterno() +" "+ asistenciaLocal.getApe_materno(),asistenciaLocal.getSede(),asistenciaLocal.getLocal(),asistenciaLocal.getNaula());
        final String c = asistenciaLocal.getDni();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("asistencia").document(asistenciaLocal.getDni());
        batch.update(documentReference, "check_registro_local", 1);
        batch.update(documentReference, "fecha_transferencia_local", FieldValue.serverTimestamp());
        batch.update(documentReference, "usuario_reg", usuario);
        batch.update(documentReference, "fecha_registro_local",
                new Timestamp(new Date(asistenciaLocal.getAnio()-1900,asistenciaLocal.getMes()-1,asistenciaLocal.getDia(),
                        asistenciaLocal.getHora(),asistenciaLocal.getMin(),asistenciaLocal.getSeg())));
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data data = new Data(context);
                data.open();
                data.actualizarAsistenciaLocalSubido(c);
                data.close();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostrarCorrecto(String dni, String nombre, String sede, String local, int aula){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        correctoTxtDni.setText(dni);
        correctoTxtNombre.setText(nombre);
        correctoTxtSede.setText(sede);
        correctoTxtLocal.setText(local);
        correctoTxtAula.setText(aula+"");
    }
    public void mostrarErrorDni(){
        lytErrorDni.setVisibility(View.VISIBLE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
    }
    public void mostrarErrorLocal(String sede, String local, String direccion, String aula){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.VISIBLE);
        lytCorrecto.setVisibility(View.GONE);
        errorLocalTxtSede.setText(sede);
        errorLocalTxtLocal.setText(local);
        errorLocalTxtAula.setText(aula);
        errorLocalTxtDireccion.setText(direccion);
    }
    public void mostrarYaRegistrado(String dni, String nombre, int aula, String fecha){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.VISIBLE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        yaRegistradoTxtDni.setText(dni);
        yaRegistradoTxtNombre.setText(nombre);
        yaRegistradoTxtAula.setText("" + aula);
        yaRegistradoTxtFecha.setText(fecha);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
