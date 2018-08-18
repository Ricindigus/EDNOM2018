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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Calendar;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaAula;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaLocal;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Nacional;
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

    TextView correctoTxtDni;
    TextView correctoTxtNombre;
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


    public AsistAulaFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AsistAulaFragment(int nroLocal, Context context) {
        this.context = context;
        this.nroLocal = nroLocal;
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
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data data =  new Data(context);
        data.open();
        ArrayList<String> aulas =  data.getArrayAulas(nroLocal);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, aulas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAulas.setAdapter(dataAdapter);
        data.close();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarTeclado(edtDni);
                String dni = edtDni.getText().toString();
                Data data = new Data(context);
                data.open();
                Nacional nacional = data.getNacionalxDNI(dni);
                data.close();
                if(nacional == null){
                    mostrarErrorDni();
                }else{
                    registrarAsistencia(nacional);
                }
                edtDni.setText("");
            }
        });
    }
    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void registrarAsistencia(Nacional nacional){
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        Data da = new Data(context);
        da.open();
        nroAula = da.getNumeroAula(aula,nroLocal);
        da.close();

        if(nroLocal == nacional.getNro_local() && nroAula == nacional.getId_aula()){
            if(!existeAsistenciaAula(nacional.getIns_numdoc())){
                Data data = new Data(context);
                data.open();
                AsistenciaAula asis = new AsistenciaAula();
                asis.set_id(nacional.getIns_numdoc());
                asis.setDni(nacional.getIns_numdoc());
                asis.setNombres(nacional.getNombres());
                asis.setApepat(nacional.getApepat());
                asis.setApemat(nacional.getApemat());
                asis.setSede(nacional.getSede());
                asis.setId_local(nacional.getNro_local());
                asis.setLocal(nacional.getLocal_aplicacion());
                asis.setAula(nacional.getAula());
                Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH)+1;
                int dd = calendario.get(Calendar.DAY_OF_MONTH);
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minuto = calendario.get(Calendar.MINUTE);
                asis.setAula_dia(dd);
                asis.setAula_mes(mm);
                asis.setAula_anio(yy);
                asis.setAula_hora(hora);
                asis.setAula_minuto(minuto);
                asis.setSubido_aula(0);
                data.insertarAsistenciaAula(asis);
                data.close();
                mostrarCorrecto(asis.getDni(),asis.getNombres() +" "+ asis.getApepat() +" "+ asis.getApemat(),asis.getAula());
                final String c = asis.getDni();
                WriteBatch batch = FirebaseFirestore.getInstance().batch();
                DocumentReference documentReference = FirebaseFirestore.getInstance().collection(getResources().getString(R.string.nombre_coleccion_asistencia))
                        .document(asis.getDni());
                batch.update(documentReference, "aula_dia", dd);
                batch.update(documentReference, "aula_mes", mm);
                batch.update(documentReference, "aula_anio", yy);
                batch.update(documentReference, "aula_hora", hora);
                batch.update(documentReference, "aula_minuto", minuto);
                batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Data data = new Data(context);
                        data.open();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(SQLConstantes.asistencia_local_subido_local,1);
                        data.actualizarAsistenciaLocal(c,contentValues);
                        data.close();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else{
            mostrarErrorLocal(nacional.getSede(),nacional.getLocal_aplicacion(),nacional.getDireccion(),"Aula " + nacional.getAula());
        }
    }

    public boolean existeAsistenciaAula(String dni){
        boolean existe = false;
        Data d = new Data(context);
        d.open();
        AsistenciaAula a = d.getAsistenciaAula(dni);
        if(a != null){
            existe = true;
            mostrarYaRegistrado(a.getDni(),a.getNombres() + " " + a.getApepat() + " " + a.getApemat(),a.getAula(),
                    checkDigito(a.getAula_dia()) +"/"+ checkDigito(a.getAula_mes()) +"/"+ a.getAula_anio() +
                            " " + checkDigito(a.getAula_hora()) + ":" + checkDigito(a.getAula_minuto()));
        }
        return existe;
    }

    public void mostrarCorrecto(String dni, String nombre, String aula){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        correctoTxtDni.setText(dni);
        correctoTxtNombre.setText(nombre);
        correctoTxtAula.setText(aula);
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
    public void mostrarYaRegistrado(String dni, String nombre, String aula, String fecha){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.VISIBLE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        yaRegistradoTxtDni.setText(dni);
        yaRegistradoTxtNombre.setText(nombre);
        yaRegistradoTxtAula.setText(aula);
        yaRegistradoTxtFecha.setText(fecha);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
