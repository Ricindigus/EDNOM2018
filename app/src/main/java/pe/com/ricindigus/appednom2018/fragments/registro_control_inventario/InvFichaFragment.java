package pe.com.ricindigus.appednom2018.fragments.registro_control_inventario;


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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaAula;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Ficha;
import pe.com.ricindigus.appednom2018.modelo.Nacional;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvFichaFragment extends Fragment {
    Spinner spAulas;
    EditText edtFicha;
    ImageView btnBuscar;
    Context context;
    int nroLocal;

    TextView correctoTxtDni;
    TextView correctoTxtNombre;
    TextView correctoTxtFicha;

    TextView errorFichaAulaTxtDni;
    TextView errorFichaAulaTxtNombre;
    TextView errorFichaAulaTxtAula;

    TextView yaRegistradoTxtDni;
    TextView yaRegistradoTxtNombre;
    TextView yaRegistradoTxtAula;
    TextView yaRegistradoTxtFicha;

    TextView errorCodigoFichaTxtFicha;

    LinearLayout lytCorrecto;
    LinearLayout lytErrorFichaAula;
    LinearLayout lytYaRegistrado;
    LinearLayout lytErrorFicha;

    public InvFichaFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InvFichaFragment(int nroLocal, Context context) {
        this.context = context;
        this.nroLocal = nroLocal;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inv_ficha, container, false);
        spAulas = (Spinner) rootView.findViewById(R.id.inventario_ficha_spAula);
        edtFicha = (EditText) rootView.findViewById(R.id.inventario_ficha_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.inventario_ficha_btnBuscar);

        correctoTxtDni = (TextView) rootView.findViewById(R.id.ficha_correcta_txtDni);
        correctoTxtNombre = (TextView) rootView.findViewById(R.id.ficha_correcta_txtNombre);
        correctoTxtFicha = (TextView) rootView.findViewById(R.id.ficha_correcta_txtFicha);

        errorFichaAulaTxtDni = (TextView) rootView.findViewById(R.id.error_ficha_txtDni);
        errorFichaAulaTxtAula = (TextView) rootView.findViewById(R.id.error_ficha_txtAula);
        errorFichaAulaTxtNombre = (TextView) rootView.findViewById(R.id.error_ficha_txtNombre);

        yaRegistradoTxtDni = (TextView) rootView.findViewById(R.id.error_ficha_yaregistrada_txtDni);
        yaRegistradoTxtNombre = (TextView) rootView.findViewById(R.id.error_ficha_yaregistrada_txtNombre);
        yaRegistradoTxtAula = (TextView) rootView.findViewById(R.id.error_ficha_yaregistrada_txtAula);
        yaRegistradoTxtFicha = (TextView) rootView.findViewById(R.id.error_ficha_yaregistrada_txtFicha);

        errorCodigoFichaTxtFicha = (TextView) rootView.findViewById(R.id.error_codigo_ficha_txtFicha);

        lytCorrecto = (LinearLayout) rootView.findViewById(R.id.inventario_ficha_lytCorrecto);
        lytErrorFichaAula = (LinearLayout) rootView.findViewById(R.id.inventario_ficha_lytErrorAula);
        lytYaRegistrado = (LinearLayout) rootView.findViewById(R.id.inventario_ficha_lytYaRegistrado);
        lytErrorFicha = (LinearLayout) rootView.findViewById(R.id.inventario_ficha_lytErrorFicha);
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

        edtFicha.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 6) clickBoton();
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
        ocultarTeclado(edtFicha);
        String ficha = edtFicha.getText().toString();
        Data data = new Data(context);
        data.open();
        Nacional nacional = data.getNacionalxFicha(ficha);
        data.close();
        if(nacional == null){
            mostrarErrorDni(ficha);
        }else{
            registrarFicha(nacional);
        }
        edtFicha.setText("");
        edtFicha.requestFocus();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void registrarFicha(Nacional nacional){
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        Data da = new Data(context);
        da.open();
        nroAula = da.getNumeroAula(aula,nroLocal);
        da.close();

        if(nroLocal == nacional.getNro_local() && nroAula == nacional.getId_aula()){
            if(!existeFicha(nacional.getCodficha())){
                Data data = new Data(context);
                data.open();
                Ficha ficha = new Ficha();
                ficha.set_id(nacional.getCodficha());
                ficha.setCodficha(nacional.getCodficha());
                ficha.setDni(nacional.getIns_numdoc());
                ficha.setNombres(nacional.getNombres());
                ficha.setApepat(nacional.getApepat());
                ficha.setApemat(nacional.getApemat());
                ficha.setSede(nacional.getSede());
                ficha.setId_local(nacional.getId_local());
                ficha.setLocal(nacional.getLocal_aplicacion());
                ficha.setAula(nacional.getAula());
                Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH)+1;
                int dd = calendario.get(Calendar.DAY_OF_MONTH);
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minuto = calendario.get(Calendar.MINUTE);
                ficha.setDia(dd);
                ficha.setMes(mm);
                ficha.setAnio(yy);
                ficha.setHora(hora);
                ficha.setMinuto(minuto);
                ficha.setSubido(0);
                data.insertarFicha(ficha);
                data.close();
                mostrarCorrecto(ficha.getDni(),ficha.getNombres() +" "+ ficha.getApepat() +" "+ ficha.getApemat(),ficha.getCodficha());
                final String c = ficha.getCodficha();
                FirebaseFirestore.getInstance().collection("inventario_ficha").document(ficha.getCodficha())
                        .set(ficha.toMap())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Data data = new Data(context);
                                data.open();
                                data.actualizarFichaSubido(c);
                                data.close();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("FIRESTORE", "Error writing document", e);
                            }
                        });
//                WriteBatch batch = FirebaseFirestore.getInstance().batch();
//                DocumentReference documentReference = FirebaseFirestore.getInstance().collection(getResources().getString(R.string.nombre_coleccion_asistencia))
//                        .document(asis.getDni());
//                batch.update(documentReference, "aula_dia", dd);
//                batch.update(documentReference, "aula_mes", mm);
//                batch.update(documentReference, "aula_anio", yy);
//                batch.update(documentReference, "aula_hora", hora);
//                batch.update(documentReference, "aula_minuto", minuto);
//                batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Data data = new Data(context);
//                        data.open();
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put(SQLConstantes.asistencia_aula_subido_aula,1);
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
            mostrarErrorAula(nacional.getIns_numdoc(),nacional.getNombres() +" "+ nacional.getApepat() +" "+ nacional.getApemat(),"Aula " + nacional.getAula());
        }
    }

    public boolean existeFicha(String codigoFicha){
        boolean existe = false;
        Data d = new Data(context);
        d.open();
        Ficha a = d.getFicha(codigoFicha);
        if(a != null){
            existe = true;
            mostrarYaRegistrado(a.getDni(),a.getNombres() + " " + a.getApepat() + " " + a.getApemat(),a.getAula(), a.getCodficha());
        }
        return existe;
    }

    public void mostrarCorrecto(String dni, String nombre, String codigoFicha){
        lytErrorFichaAula.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorFicha.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        correctoTxtDni.setText(dni);
        correctoTxtNombre.setText(nombre);
        correctoTxtFicha.setText(codigoFicha);
    }
    public void mostrarErrorDni(String codigoFicha){
        lytErrorFicha.setVisibility(View.VISIBLE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorFichaAula.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        errorCodigoFichaTxtFicha.setText(codigoFicha);

    }
    public void mostrarErrorAula(String dni, String nombre,String aula){
        lytErrorFicha.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorFichaAula.setVisibility(View.VISIBLE);
        lytCorrecto.setVisibility(View.GONE);
        errorFichaAulaTxtNombre.setText(dni);
        errorFichaAulaTxtDni.setText(nombre);
        errorFichaAulaTxtAula.setText(aula);
    }
    public void mostrarYaRegistrado(String dni, String nombre, String aula, String codigoFicha){
        lytErrorFicha.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.VISIBLE);
        lytErrorFichaAula.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        yaRegistradoTxtDni.setText(dni);
        yaRegistradoTxtNombre.setText(nombre);
        yaRegistradoTxtAula.setText(aula);
        yaRegistradoTxtFicha.setText(codigoFicha);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
