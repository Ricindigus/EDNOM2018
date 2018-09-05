package pe.com.ricindigus.appednom2018.fragments.registro_control_inventario;


import android.annotation.SuppressLint;
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
import pe.com.ricindigus.appednom2018.modelo.Cuadernillo;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Ficha;
import pe.com.ricindigus.appednom2018.modelo.Nacional;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvCuaderFragment extends Fragment {
    Spinner spAulas;
    EditText edtCuadernillo;
    ImageView btnBuscar;
    Context context;
    int nroLocal;

    TextView correctoTxtDni;
    TextView correctoTxtNombre;
    TextView correctoTxtCuadernillo;

    TextView errorCuadernilloAulaTxtDni;
    TextView errorCuadernilloAulaTxtNombre;
    TextView errorCuadernilloAulaTxtAula;

    TextView yaRegistradoTxtDni;
    TextView yaRegistradoTxtNombre;
    TextView yaRegistradoTxtAula;
    TextView yaRegistradoTxtCuadernillo;

    TextView errorCodigoCuadernilloTxtCuadernillo;

    LinearLayout lytCorrecto;
    LinearLayout lytErrorCuadernilloAula;
    LinearLayout lytYaRegistrado;
    LinearLayout lytErrorCuadernillo;

    public InvCuaderFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InvCuaderFragment(int nroLocal, Context context) {
        this.context = context;
        this.nroLocal = nroLocal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inv_cuader, container, false);
        spAulas = (Spinner) rootView.findViewById(R.id.inventario_cuadernillo_spAula);
        edtCuadernillo = (EditText) rootView.findViewById(R.id.inventario_cuadernillo_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.inventario_cuadernillo_btnBuscar);

        correctoTxtDni = (TextView) rootView.findViewById(R.id.cuadernillo_correcto_txtDni);
        correctoTxtNombre = (TextView) rootView.findViewById(R.id.cuadernillo_correcto_txtNombre);
        correctoTxtCuadernillo = (TextView) rootView.findViewById(R.id.cuadernillo_correcto_txtCuadernillo);

        errorCuadernilloAulaTxtDni = (TextView) rootView.findViewById(R.id.error_cuadernillo_txtDni);
        errorCuadernilloAulaTxtAula = (TextView) rootView.findViewById(R.id.error_cuadernillo_txtAula);
        errorCuadernilloAulaTxtNombre = (TextView) rootView.findViewById(R.id.error_cuadernillo_txtNombre);

        yaRegistradoTxtDni = (TextView) rootView.findViewById(R.id.error_cuadernillo_yaregistrada_txtDni);
        yaRegistradoTxtNombre = (TextView) rootView.findViewById(R.id.error_cuadernillo_yaregistrada_txtNombre);
        yaRegistradoTxtAula = (TextView) rootView.findViewById(R.id.error_cuadernillo_yaregistrada_txtAula);
        yaRegistradoTxtCuadernillo = (TextView) rootView.findViewById(R.id.error_cuadernillo_yaregistrada_txtCuadernillo);

        errorCodigoCuadernilloTxtCuadernillo = (TextView) rootView.findViewById(R.id.error_codigo_cuadernillo_txtCuadernillo);

        lytCorrecto = (LinearLayout) rootView.findViewById(R.id.inventario_cuadernillo_lytCorrecto);
        lytErrorCuadernilloAula = (LinearLayout) rootView.findViewById(R.id.inventario_cuadernillo_lytErrorAula);
        lytYaRegistrado = (LinearLayout) rootView.findViewById(R.id.inventario_cuadernillo_lytYaRegistrado);
        lytErrorCuadernillo = (LinearLayout) rootView.findViewById(R.id.inventario_cuadernillo_ErrorCodigo);
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

        edtCuadernillo.addTextChangedListener(new TextWatcher() {
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
//        ocultarTeclado(edtCuadernillo);
//        String cuadernillo = edtCuadernillo.getText().toString();
//        Data data = new Data(context);
//        data.open();
//        Nacional nacional = data.getNacionalxCuadernillo(cuadernillo);
//        data.close();
//        if(nacional == null){
//            mostrarErrorDni(cuadernillo);
//        }else{
//            registrarCuadernillo(nacional);
//        }
//        edtCuadernillo.setText("");
//        edtCuadernillo.requestFocus();
    }
    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void registrarCuadernillo(Nacional nacional){
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        Data da = new Data(context);
        da.open();
        nroAula = da.getNumeroAula(aula,nroLocal);
        da.close();

        if(nroLocal == nacional.getNro_local() && nroAula == nacional.getId_aula()){
            if(!existeCuadernillo(nacional.getCodcartilla())){
                Data data = new Data(context);
                data.open();
                Cuadernillo cuadernillo = new Cuadernillo();
                cuadernillo.set_id(nacional.getCodficha());
                cuadernillo.setCodcartilla(nacional.getCodcartilla());
                cuadernillo.setDni(nacional.getIns_numdoc());
                cuadernillo.setNombres(nacional.getNombres());
                cuadernillo.setApepat(nacional.getApepat());
                cuadernillo.setApemat(nacional.getApemat());
                cuadernillo.setSede(nacional.getSede());
                cuadernillo.setId_local(nacional.getId_local());
                cuadernillo.setLocal(nacional.getLocal_aplicacion());
                cuadernillo.setAula(nacional.getAula());
                Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH)+1;
                int dd = calendario.get(Calendar.DAY_OF_MONTH);
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minuto = calendario.get(Calendar.MINUTE);
                cuadernillo.setDia(dd);
                cuadernillo.setMes(mm);
                cuadernillo.setAnio(yy);
                cuadernillo.setHora(hora);
                cuadernillo.setMinuto(minuto);
                cuadernillo.setSubido(0);
                data.insertarCuadernillo(cuadernillo);
                data.close();
                mostrarCorrecto(cuadernillo.getDni(),cuadernillo.getNombres() +" "+ cuadernillo.getApepat() +" "+ cuadernillo.getApemat(),cuadernillo.getCodcartilla());
                final String c = cuadernillo.getCodcartilla();
                FirebaseFirestore.getInstance().collection("inventario_cuadernillo").document(cuadernillo.getCodcartilla())
                        .set(cuadernillo.toMap())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Data data = new Data(context);
                                data.open();
                                data.actualizarCuadernilloSubido(c);
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

    public boolean existeCuadernillo(String codigoCuadernillo){
        boolean existe = false;
        Data d = new Data(context);
        d.open();
        Cuadernillo a = d.getCuadernillo(codigoCuadernillo);
        if(a != null){
            existe = true;
            mostrarYaRegistrado(a.getDni(),a.getNombres() + " " + a.getApepat() + " " + a.getApemat(),a.getAula(), a.getCodcartilla());
        }
        return existe;
    }

    public void mostrarCorrecto(String dni, String nombre, String codigoFicha){
        lytErrorCuadernilloAula.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorCuadernillo.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        correctoTxtDni.setText(dni);
        correctoTxtNombre.setText(nombre);
        correctoTxtCuadernillo.setText(codigoFicha);
    }

    public void mostrarErrorDni(String codigoFicha){
        lytErrorCuadernillo.setVisibility(View.VISIBLE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorCuadernilloAula.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        errorCodigoCuadernilloTxtCuadernillo.setText(codigoFicha);
    }

    public void mostrarErrorAula(String dni, String nombre,String aula){
        lytErrorCuadernillo.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorCuadernilloAula.setVisibility(View.VISIBLE);
        lytCorrecto.setVisibility(View.GONE);
        errorCuadernilloAulaTxtNombre.setText(dni);
        errorCuadernilloAulaTxtDni.setText(nombre);
        errorCuadernilloAulaTxtAula.setText(aula);
    }
    public void mostrarYaRegistrado(String dni, String nombre, String aula, String codigoFicha){
        lytErrorCuadernillo.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.VISIBLE);
        lytErrorCuadernilloAula.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        yaRegistradoTxtDni.setText(dni);
        yaRegistradoTxtNombre.setText(nombre);
        yaRegistradoTxtAula.setText(aula);
        yaRegistradoTxtCuadernillo.setText(codigoFicha);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
