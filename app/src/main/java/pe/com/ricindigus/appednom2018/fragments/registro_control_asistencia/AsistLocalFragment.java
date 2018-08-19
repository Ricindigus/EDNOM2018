package pe.com.ricindigus.appednom2018.fragments.registro_control_asistencia;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.Calendar;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaLocal;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Nacional;
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

    int numeroLocal;
    Context context;

    public AsistLocalFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AsistLocalFragment(int numeroLocal, Context context) {
        this.numeroLocal = numeroLocal;
        this.context = context;
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
        if(numeroLocal == nacional.getNro_local()){
            if(!existeRegistro(nacional.getIns_numdoc())){
                Data data = new Data(context);
                data.open();
                AsistenciaLocal asis = new AsistenciaLocal();
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
                asis.setLocal_dia(dd);
                asis.setLocal_mes(mm);
                asis.setLocal_anio(yy);
                asis.setLocal_hora(hora);
                asis.setLocal_minuto(minuto);
                asis.setSubido_local(0);
                data.insertarAsistenciaLocal(asis);
                data.close();
                mostrarCorrecto(asis.getDni(),asis.getNombres() +" "+ asis.getApepat() +" "+ asis.getApemat(),asis.getSede(),asis.getLocal(),asis.getAula());
                final String c = asis.getDni();
                FirebaseFirestore.getInstance().collection("asistencia_local").document(asis.getDni())
                        .set(asis.toMap())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Data data = new Data(context);
                                data.open();
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(SQLConstantes.asistencia_local_subido_local,1);
                                data.actualizarAsistenciaLocal(c,contentValues);
                                data.close();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("FIRESTORE", "Error writing document", e);
                            }
                        });


//                    WriteBatch batch = FirebaseFirestore.getInstance().batch();
//                    DocumentReference documentReference = FirebaseFirestore.getInstance().collection(getResources().
//                            getString(R.string.nombre_coleccion_asistencia)).document(asis.getDni());
//                    batch.update(documentReference, "local_dia", asis.getLocal_dia());
//                    batch.update(documentReference, "local_mes", asis.getLocal_mes());
//                    batch.update(documentReference, "local_anio", asis.getLocal_anio());
//                    batch.update(documentReference, "local_hora", asis.getLocal_hora());
//                    batch.update(documentReference, "local_minuto", asis.getLocal_minuto());
//                    final String c = asis.getDni();
//                    batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Data data = new Data(context);
//                        data.open();
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put(SQLConstantes.asistencia_local_subido_local,1);
//                        data.actualizarAsistenciaLocal(c,contentValues);
//                        data.close();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
        }else{
            mostrarErrorLocal(nacional.getSede(),nacional.getLocal_aplicacion(),nacional.getDireccion(),"Aula" + nacional.getAula());
        }
    }

    public boolean existeRegistro(String dni){
        boolean existe = false;

            Data d = new Data(context);
            d.open();
            AsistenciaLocal a = d.getAsistenciaLocal(dni);
            if(a != null){
                existe = true;
                mostrarYaRegistrado(a.getDni(),a.getNombres() + " " + a.getApepat() + " " + a.getApemat(),a.getAula(),
                checkDigito(a.getLocal_dia()) +"/"+ checkDigito(a.getLocal_mes()) +"/"+ a.getLocal_anio() +
                        " " + checkDigito(a.getLocal_hora()) + ":" + checkDigito(a.getLocal_minuto()));
            }


        return existe;
    }

    public void mostrarCorrecto(String dni, String nombre, String sede, String local, String aula){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        correctoTxtDni.setText(dni);
        correctoTxtNombre.setText(nombre);
        correctoTxtSede.setText(sede);
        correctoTxtLocal.setText(local);
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
