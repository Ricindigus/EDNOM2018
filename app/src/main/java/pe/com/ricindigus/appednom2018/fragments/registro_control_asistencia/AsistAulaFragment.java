package pe.com.ricindigus.appednom2018.fragments.registro_control_asistencia;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import pe.com.ricindigus.appednom2018.modelo.AsistenciaReg;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;
import pe.com.ricindigus.appednom2018.util.ActividadInterfaz;
import pe.com.ricindigus.appednom2018.util.TipoFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsistAulaFragment extends Fragment {

    Spinner spAulas;
    EditText edtDni;
    ImageView btnBuscar;
    ImageView btnReporte;

    Context context;
    int nroLocal;
    String usuario;
    String nombreColeccion;

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

    TextView txtRegistrados;
    TextView txtTransferidos;



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
        btnReporte = (ImageView) rootView.findViewById(R.id.asistencia_aula_btnReporte);


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


        txtRegistrados = (TextView) rootView.findViewById(R.id.asistencia_aula_txtRegistrados);
        txtTransferidos = (TextView) rootView.findViewById(R.id.asistencia_aula_txtTransferidos);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (context != null){
            Data data =  new Data(context);
            data.open();
            nombreColeccion = data.getNombreColeccionAsistencia();
            ArrayList<String> aulas =  data.getArrayAulasRegistro(nroLocal);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, aulas);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spAulas.setAdapter(dataAdapter);
            data.close();
        }

        spAulas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Data data =  new Data(context);
                data.open();
                String aula = spAulas.getSelectedItem().toString();
                int nroAula = data.getNumeroAula(aula,nroLocal);
//                txtTotal.setText("Total: " +data.getNroAsistenciasAulaTotales(nroLocal,nroAula));
//                txtFaltan.setText("Faltan: " + data.getNroAsistenciasAulaSinRegistro(nroLocal,nroAula));
                txtRegistrados.setText("Registrados: " + data.getNroAsistenciasAulaLeidas(nroLocal,nroAula) + "/" + data.getNroAsistenciasAulaSinRegistro(nroLocal,nroAula));
                txtTransferidos.setText("Transferidos: " + data.getNroAsistenciasAulaTransferidos(nroLocal,nroAula)+"/"+data.getNroAsistenciasAulaSinRegistro(nroLocal,nroAula));
                lytErrorDni.setVisibility(View.GONE);
                lytYaRegistrado.setVisibility(View.GONE);
                lytErrorLocal.setVisibility(View.GONE);
                lytCorrecto.setVisibility(View.GONE);
                data.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBoton();
            }
        });

        btnReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActividadInterfaz actividadInterfaz = (ActividadInterfaz) getActivity();
                actividadInterfaz.irReporte(TipoFragment.REPORTES_LISTADO_ASISTENCIA_AULA);
            }
        });
    }

    public void clickBoton(){
        ocultarTeclado(edtDni);
        String dni = edtDni.getText().toString();
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        Data data = new Data(context);
        data.open();
        nroAula = data.getNumeroAula(aula,nroLocal);
        AsistenciaReg asistenciaReg = data.getAsistenciaReg(dni);
        if(asistenciaReg == null) {
            Asistencia asistenciaPadron = data.getAsistenciaxDni(dni);
            if (asistenciaPadron == null) mostrarErrorDni();
            else mostrarErrorAula(asistenciaPadron.getDni(),asistenciaPadron.getNom_sede(),asistenciaPadron.getNom_local(),asistenciaPadron.getDireccion(),asistenciaPadron.getNaula());
        }else{
            if(asistenciaReg.getNaula() == nroAula){
                if(asistenciaReg.getEstado_aula() == 0) registrarAsistencia(asistenciaReg);
                else mostrarYaRegistrado(asistenciaReg.getDni(),asistenciaReg.getNombres() + " " + asistenciaReg.getApe_paterno() + " " + asistenciaReg.getApe_materno(),asistenciaReg.getNaula(),
                        checkDigito(asistenciaReg.getDia_aula()) +"/"+ checkDigito(asistenciaReg.getMes_aula()) +"/"+ asistenciaReg.getAnio_aula() +
                                " " + checkDigito(asistenciaReg.getHora_aula()) + ":" + checkDigito(asistenciaReg.getMin_aula()) + ":" + checkDigito(asistenciaReg.getSeg_aula()));
            }
            else mostrarErrorAula(asistenciaReg.getDni(),asistenciaReg.getNom_sede(),asistenciaReg.getNom_local(),asistenciaReg.getDireccion(),asistenciaReg.getNaula());
        }
        edtDni.setText("");
        edtDni.requestFocus();
        data.close();
    }
    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void registrarAsistencia(final AsistenciaReg asistenciaReg){
        Data data = new Data(context);
        data.open();
        Calendar calendario = Calendar.getInstance();
        int yy = calendario.get(Calendar.YEAR);
        int mm = calendario.get(Calendar.MONTH)+1;
        int dd = calendario.get(Calendar.DAY_OF_MONTH);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);
        int seg = calendario.get(Calendar.SECOND);
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.asistenciareg_dia_aula,dd);
        contentValues.put(SQLConstantes.asistenciareg_mes_aula,mm);
        contentValues.put(SQLConstantes.asistenciareg_anio_aula,yy);
        contentValues.put(SQLConstantes.asistenciareg_hora_aula,hora);
        contentValues.put(SQLConstantes.asistenciareg_min_aula,minuto);
        contentValues.put(SQLConstantes.asistenciareg_seg_aula,seg);
        contentValues.put(SQLConstantes.asistenciareg_estado_aula,1);
        data.actualizarAsistenciaReg(asistenciaReg.getDni(),contentValues);
        txtRegistrados.setText("Registrados: " + data.getNroAsistenciasAulaLeidas(nroLocal,asistenciaReg.getNaula()) + "/" + data.getNroAsistenciasAulaSinRegistro(nroLocal,asistenciaReg.getNaula()));
        AsistenciaReg asis = data.getAsistenciaReg(asistenciaReg.getDni());
        data.close();
        mostrarCorrecto(asis.getDni(),asis.getNombres() +" "+ asis.getApe_paterno() +" "+ asis.getApe_materno());
        final String c = asis.getDni();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(nombreColeccion).document(asis.getDni());
        batch.update(documentReference, "check_registro_aula", 1);
        batch.update(documentReference, "fecha_transferencia_aula", FieldValue.serverTimestamp());
        batch.update(documentReference, "usuario_registro_aula", usuario);
        batch.update(documentReference, "fecha_registro_aula",
                new Timestamp(new Date(asis.getAnio_aula()-1900,asis.getMes_aula()-1,asis.getDia_aula(),
                        asis.getHora_aula(),asis.getMin_aula(),asis.getSeg_aula())));
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data data = new Data(context);
                data.open();
                data.actualizarAsistenciaRegAulaSubido(c);
                txtTransferidos.setText("Transferidos: " + data.getNroAsistenciasAulaTransferidos(nroLocal,asistenciaReg.getNaula())+"/"+data.getNroAsistenciasAulaSinRegistro(nroLocal,asistenciaReg.getNaula()));

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
