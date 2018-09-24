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
import pe.com.ricindigus.appednom2018.activities.MainActivity;
import pe.com.ricindigus.appednom2018.modelo.Asistencia;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaReg;
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

    TextView txtRegistrados;
    TextView txtFaltan;
    TextView txtTotal;
    TextView txtTransferidos;

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

        txtTotal = (TextView) rootView.findViewById(R.id.asistencia_local_txtTotal);
//        txtFaltan = (TextView) rootView.findViewById(R.id.asistencia_local_txtFaltan);
        txtRegistrados = (TextView) rootView.findViewById(R.id.asistencia_local_txtRegistrados);
        txtTransferidos = (TextView) rootView.findViewById(R.id.asistencia_local_txtTransferidos);


        edtDni = (EditText) rootView.findViewById(R.id.asistencia_local_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.asistencia_local_btnBuscar);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data data =  new Data(context);
        data.open();
        txtTotal.setText("Total: " + data.getNumeroItemsAsistenciaReg());
//        txtFaltan.setText("Faltan: " + data.getNroAsistenciasLocalSinRegistro(nroLocal));
        txtRegistrados.setText("Leidos: " + data.getNroAsistenciasLocalLeidas(nroLocal));
        txtTransferidos.setText("Transferidos: " + data.getNroAsistenciasLocalTransferidos(nroLocal));
        data.close();
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
        AsistenciaReg asistenciaReg = data.getAsistenciaReg(dni);
        if(asistenciaReg == null){
            Asistencia asistenciaPadron = data.getAsistenciaxDni(dni);
            if (asistenciaPadron != null) mostrarErrorLocal(asistenciaPadron.getNom_sede(),asistenciaPadron.getNom_local(),asistenciaPadron.getDireccion(),""+asistenciaPadron.getNaula());
            else mostrarErrorDni();
        }else{
            if (asistenciaReg.getEstado_local() == 0) registrarAsistencia(asistenciaReg);
            else mostrarYaRegistrado(asistenciaReg.getDni(),
                    asistenciaReg.getNombres() + " " + asistenciaReg.getApe_paterno() + " " + asistenciaReg.getApe_materno(), asistenciaReg.getNaula(),
                    checkDigito(asistenciaReg.getDia_local()) +"/"+ checkDigito(asistenciaReg.getMes_local()) +"/"+ asistenciaReg.getAnio_local() +
                            " " + checkDigito(asistenciaReg.getHora_local()) + ":" + checkDigito(asistenciaReg.getMin_local()) + ":" + checkDigito(asistenciaReg.getSeg_local()));
        }
        edtDni.setText("");
        edtDni.requestFocus();
        data.close();
    }
    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void registrarAsistencia(AsistenciaReg asistenciaReg){
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
        contentValues.put(SQLConstantes.asistenciareg_dia_local,dd);
        contentValues.put(SQLConstantes.asistenciareg_mes_local,mm);
        contentValues.put(SQLConstantes.asistenciareg_anio_local,yy);
        contentValues.put(SQLConstantes.asistenciareg_hora_local,hora);
        contentValues.put(SQLConstantes.asistenciareg_min_local,minuto);
        contentValues.put(SQLConstantes.asistenciareg_seg_local,seg);
        contentValues.put(SQLConstantes.asistenciareg_estado_local,1);
        data.actualizarAsistenciaReg(asistenciaReg.getDni(),contentValues);
//        txtFaltan.setText("Faltan: " + data.getNroAsistenciasLocalSinRegistro(nroLocal));
        txtRegistrados.setText("Leidos: " + data.getNroAsistenciasLocalLeidas(nroLocal));

        AsistenciaReg asis = data.getAsistenciaReg(asistenciaReg.getDni());
        data.close();
        mostrarCorrecto(asis.getDni(),asis.getNombres() +" "+ asis.getApe_paterno() +" "+ asis.getApe_materno(),asis.getNom_sede(),asis.getNom_local(),asis.getNaula());
        final String c = asis.getDni();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("asistencia").document(asis.getDni());
        batch.update(documentReference, "check_registro_local", 1);
        batch.update(documentReference, "fecha_transferencia_local", FieldValue.serverTimestamp());
        batch.update(documentReference, "usuario_registro_local", usuario);
        batch.update(documentReference, "fecha_registro_local", new Timestamp(
                new Date(asis.getAnio_local()-1900,asis.getMes_local()-1,asis.getDia_local(),
                asis.getHora_local(),asis.getMin_local(),asis.getSeg_local())));
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data data = new Data(context);
                data.open();
                data.actualizarAsistenciaRegLocalSubido(c);
                txtTransferidos.setText("Transferidos: " + data.getNroAsistenciasLocalTransferidos(nroLocal));
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
