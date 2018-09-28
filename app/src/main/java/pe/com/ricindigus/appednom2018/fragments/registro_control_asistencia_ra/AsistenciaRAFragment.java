package pe.com.ricindigus.appednom2018.fragments.registro_control_asistencia_ra;


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
import pe.com.ricindigus.appednom2018.modelo.Asistencia;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaRa;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaRaReg;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaReg;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;
import pe.com.ricindigus.appednom2018.util.ActividadInterfaz;
import pe.com.ricindigus.appednom2018.util.TipoFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsistenciaRAFragment extends Fragment {

    TextView correctoTxtDni;
    TextView correctoTxtNombre;
    TextView correctoTxtSede;
    TextView correctoTxtLocal;
    TextView correctoTxtCargo;


    TextView errorLocalTxtSede;
    TextView errorLocalTxtLocal;
    TextView errorLocalTxtAula;
    //TextView errorLocalTxtDireccion;


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

    EditText edtDni;
    ImageView btnBuscar;
    ImageView btnReporte;

    int nroLocal;
    Context context;
    String usuario;
    String nombreColeccion;

    public AsistenciaRAFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AsistenciaRAFragment(int nroLocal, Context context, String usuario) {
        this.nroLocal = nroLocal;
        this.context = context;
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_asistencia_ra, container, false);
        correctoTxtDni = (TextView) rootView.findViewById(R.id.dni_correcto_txtDni);
        correctoTxtNombre = (TextView) rootView.findViewById(R.id.dni_correcto_txtNombre);
        correctoTxtSede = (TextView) rootView.findViewById(R.id.dni_correcto_txtSede);
        correctoTxtLocal = (TextView) rootView.findViewById(R.id.dni_correcto_txtLocal);
        correctoTxtCargo = (TextView) rootView.findViewById(R.id.dni_correcto_txtCargo);

        errorLocalTxtSede = (TextView) rootView.findViewById(R.id.error_local_txtSede);
        errorLocalTxtLocal = (TextView) rootView.findViewById(R.id.error_local_txtLocal);
        errorLocalTxtAula = (TextView) rootView.findViewById(R.id.error_local_txtAula);
        //errorLocalTxtDireccion = (TextView) rootView.findViewById(R.id.error_local_txtDireccion);

        yaRegistradoTxtDni = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtDni);
        yaRegistradoTxtNombre = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtNombre);
        yaRegistradoTxtAula = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtAula);
        yaRegistradoTxtFecha = (TextView) rootView.findViewById(R.id.error_yaregistrado_txtFechaHora);

        lytCorrecto = (LinearLayout) rootView.findViewById(R.id.asistencia_ra_lytCorrecto);
        lytErrorLocal = (LinearLayout) rootView.findViewById(R.id.asistencia_ra_lytErrorLocal);
        lytYaRegistrado = (LinearLayout) rootView.findViewById(R.id.asistencia_ra_lytYaRegistrado);
        lytErrorDni = (LinearLayout) rootView.findViewById(R.id.asistencia_ra_ErrorDni);

        txtRegistrados = (TextView) rootView.findViewById(R.id.asistencia_ra_txtRegistrados);
        txtTransferidos = (TextView) rootView.findViewById(R.id.asistencia_ra_txtTransferidos);


        edtDni = (EditText) rootView.findViewById(R.id.asistencia_ra_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.asistencia_ra_btnBuscar);
        btnReporte = (ImageView) rootView.findViewById(R.id.asistencia_ra_btnReporte);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data data =  new Data(context);
        data.open();
        nombreColeccion = data.getNombreColeccionAsistenciaRA();
//        txtTotal.setText("Total: " + data.getNroAsistenciasRa(nroLocal));
//        txtFaltan.setText("Faltan: " + data.getNroAsistenciasLocalSinRegistro(nroLocal));
        txtRegistrados.setText("Registrados: " + data.getNroAsistenciasRALeidas(nroLocal) +"/"+data.getNroAsistenciasRa(nroLocal));
        txtTransferidos.setText("Transferidos: " + data.getNroAsistenciasRATransferidos(nroLocal)+"/"+data.getNroAsistenciasRa(nroLocal));
        data.close();
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
                actividadInterfaz.irReporte(TipoFragment.REPORTE_LISTADO_ASISTENCIA_RA);
                ocultarTeclado(btnReporte);
            }
        });
    }

    public void clickBoton(){
        ocultarTeclado(edtDni);
        String dni = edtDni.getText().toString();
        Data data = new Data(context);
        data.open();
        AsistenciaRaReg asistenciaReg = data.getAsistenciaRaReg(dni);
        if(asistenciaReg == null){
            AsistenciaRa asistenciaPadron = data.getAsistenciaRaxDni(dni);
            if (asistenciaPadron != null) mostrarErrorLocal(asistenciaPadron.getNom_sede(),asistenciaPadron.getNom_local());
            else mostrarErrorDni();
        }else{
            if (asistenciaReg.getEstado() == 0) registrarAsistencia(asistenciaReg);
            else mostrarYaRegistrado(asistenciaReg.getDni(),
                    asistenciaReg.getNombres_completos() ,
                    checkDigito(asistenciaReg.getDia()) +"/"+ checkDigito(asistenciaReg.getMes()) +"/"+ asistenciaReg.getAnio() +
                            " " + checkDigito(asistenciaReg.getHora()) + ":" + checkDigito(asistenciaReg.getMin()) + ":" + checkDigito(asistenciaReg.getSeg()));
        }
        edtDni.setText("");
        edtDni.requestFocus();
        data.close();
    }
    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void registrarAsistencia(AsistenciaRaReg asistenciaReg){
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
        contentValues.put(SQLConstantes.asistenciareg_ra_dia,dd);
        contentValues.put(SQLConstantes.asistenciareg_ra_mes,mm);
        contentValues.put(SQLConstantes.asistenciareg_ra_anio,yy);
        contentValues.put(SQLConstantes.asistenciareg_ra_hora,hora);
        contentValues.put(SQLConstantes.asistenciareg_ra_min,minuto);
        contentValues.put(SQLConstantes.asistenciareg_ra_seg,seg);
        contentValues.put(SQLConstantes.asistenciareg_ra_estado,1);
        contentValues.put(SQLConstantes.asistenciareg_ra_leida_orden,hora*60*60+minuto*60+seg);
        data.actualizarAsistenciaRaReg(asistenciaReg.getDni(),contentValues);
        txtRegistrados.setText("Registrados: " + data.getNroAsistenciasRALeidas(nroLocal) +"/"+data.getNroAsistenciasRa(nroLocal));
        AsistenciaRaReg asis = data.getAsistenciaRaReg(asistenciaReg.getDni());
        data.close();
        mostrarCorrecto(asis.getDni(),asis.getNombres_completos(),asis.getNom_sede(),asis.getNom_local(),asis.getNombre_cargo());
        final String c = asis.getDni();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(nombreColeccion).document(asis.getDni());
        batch.update(documentReference, "check_registro", 1);
        batch.update(documentReference, "fecha_transferencia", FieldValue.serverTimestamp());
        batch.update(documentReference, "usuario_registro", usuario);
        batch.update(documentReference, "fecha_registro", new Timestamp(
                new Date(asis.getAnio()-1900,asis.getMes()-1,asis.getDia(),
                        asis.getHora(),asis.getMin(),asis.getSeg())));
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data data = new Data(context);
                data.open();
                data.actualizarAsistenciaRaRegSubido(c);
                txtTransferidos.setText("Transferidos: " + data.getNroAsistenciasRATransferidos(nroLocal)+"/"+data.getNroAsistenciasRa(nroLocal));
                data.close();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostrarCorrecto(String dni, String nombre, String sede, String local,String cargo){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        correctoTxtDni.setText(dni);
        correctoTxtNombre.setText(nombre);
        correctoTxtSede.setText(sede);
        correctoTxtLocal.setText(local);
        correctoTxtCargo.setText(cargo);

    }
    public void mostrarErrorDni(){
        lytErrorDni.setVisibility(View.VISIBLE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
    }
    public void mostrarErrorLocal(String sede, String local){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.VISIBLE);
        lytCorrecto.setVisibility(View.GONE);
        errorLocalTxtSede.setText(sede);
        errorLocalTxtLocal.setText(local);

    }
    public void mostrarYaRegistrado(String dni, String nombre, String fecha){
        lytErrorDni.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.VISIBLE);
        lytErrorLocal.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        yaRegistradoTxtDni.setText(dni);
        yaRegistradoTxtNombre.setText(nombre);
        yaRegistradoTxtFecha.setText(fecha);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
