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
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Ficha;
import pe.com.ricindigus.appednom2018.modelo.Listado;
import pe.com.ricindigus.appednom2018.modelo.Nacional;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvListAsisFragment extends Fragment {
    Spinner spAulas;
    EditText edtLista;
    ImageView btnBuscar;
    Context context;
    int nroLocal;

    TextView correctoTxtCodLista;
    TextView correctoTxtNroPostulantes;

    TextView errorListaAulaTxtCodLista;
    TextView errorListaAulaTxtNroPostulantes;
    TextView errorListaAulaTxtAula;

    TextView yaRegistradoTxtCodLista;
    TextView yaRegistradoTxtNroPostulantes;
    TextView yaRegistradoTxtAula;

    TextView errorCodigoListaTxtCodLista;

    LinearLayout lytCorrecto;
    LinearLayout lytErrorListaAula;
    LinearLayout lytYaRegistrado;
    LinearLayout lytErrorLista;

    public InvListAsisFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InvListAsisFragment(int nroLocal, Context context) {
        this.context = context;
        this.nroLocal = nroLocal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inv_list_asis, container, false);
        spAulas = (Spinner) rootView.findViewById(R.id.inventario_lista_spAula);
        edtLista = (EditText) rootView.findViewById(R.id.inventario_lista_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.inventario_lista_btnBuscar);

        correctoTxtCodLista = (TextView) rootView.findViewById(R.id.lista_correcta_txtCodLista);
        correctoTxtNroPostulantes = (TextView) rootView.findViewById(R.id.lista_correcta_txtNroPostulantes);

        errorListaAulaTxtCodLista = (TextView) rootView.findViewById(R.id.error_lista_txtCodLista);
        errorListaAulaTxtAula = (TextView) rootView.findViewById(R.id.error_lista_txtAula);
        errorListaAulaTxtNroPostulantes = (TextView) rootView.findViewById(R.id.error_lista_txtNroPostulantes);

        yaRegistradoTxtCodLista = (TextView) rootView.findViewById(R.id.error_lista_yaregistrada_txtCodLista);
        yaRegistradoTxtNroPostulantes = (TextView) rootView.findViewById(R.id.error_lista_yaregistrada_txtNroPostulantes);
        yaRegistradoTxtAula = (TextView) rootView.findViewById(R.id.error_lista_yaregistrada_txtAula);


        errorCodigoListaTxtCodLista = (TextView) rootView.findViewById(R.id.error_codigo_lista_txtLista);

        lytCorrecto = (LinearLayout) rootView.findViewById(R.id.inventario_lista_lytCorrecto);
        lytErrorListaAula = (LinearLayout) rootView.findViewById(R.id.inventario_lista_lytErrorAula);
        lytYaRegistrado = (LinearLayout) rootView.findViewById(R.id.inventario_lista_lytYaRegistrado);
        lytErrorLista = (LinearLayout) rootView.findViewById(R.id.inventario_lista_lytErrorLista);
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

        edtLista.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 12) clickBoton();
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
//        ocultarTeclado(edtLista);
//        String codListado = edtLista.getText().toString();
//        Data data = new Data(context);
//        data.open();
//        Nacional nacional = data.getNacionalxCodPagina(codListado);
//        data.close();
//        if(nacional == null){
//            mostrarErrorDni(codListado);
//        }else{
//            registrarListado(nacional);
//        }
//        edtLista.setText("");
//        edtLista.requestFocus();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void registrarListado(Nacional nacional){
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        Data da = new Data(context);
        da.open();
        nroAula = da.getNumeroAula(aula,nroLocal);
        da.close();

        if(nroLocal == nacional.getNro_local() && nroAula == nacional.getId_aula()){
            if(!existeListado(nacional.getCodigo_pagina())){
                Data data = new Data(context);
                data.open();
                Listado listado = new Listado();
                listado.set_id(nacional.getCodigo_pagina());
                listado.setCodigo_pagina(nacional.getCodigo_pagina());
                listado.setSede(nacional.getSede());
                listado.setId_local(nacional.getId_local());
                listado.setLocal(nacional.getLocal_aplicacion());
                listado.setAula(nacional.getAula());
                Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH)+1;
                int dd = calendario.get(Calendar.DAY_OF_MONTH);
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minuto = calendario.get(Calendar.MINUTE);
                listado.setNro_postulantes(data.getNroPostulantesListado(nacional.getCodigo_pagina()));
                listado.setDia(dd);
                listado.setMes(mm);
                listado.setAnio(yy);
                listado.setHora(hora);
                listado.setMinuto(minuto);
                listado.setSubido(0);
                data.insertarListado(listado);
                data.close();
                mostrarCorrecto(listado.getCodigo_pagina(),listado.getNro_postulantes());
                final String c = listado.getCodigo_pagina();
                FirebaseFirestore.getInstance().collection("inventario_listado").document(listado.getCodigo_pagina())
                        .set(listado.toMap())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Data data = new Data(context);
                                data.open();
                                data.actualizarListadoSubido(c);
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
            Data dat = new Data(context);
            dat.open();
            mostrarErrorAula(nacional.getCodigo_pagina(),dat.getNroPostulantesListado(nacional.getCodigo_pagina()),"Aula " + nacional.getAula());
            dat.close();
        }
    }

    public boolean existeListado(String codigoLista){
        boolean existe = false;
        Data d = new Data(context);
        d.open();
        Listado listado = d.getListado(codigoLista);
        if(listado != null){
            existe = true;
            mostrarYaRegistrado(listado.getCodigo_pagina(),listado.getNro_postulantes(),listado.getAula());
        }
        d.close();
        return existe;
    }

    public void mostrarCorrecto(String codLista, int nroPostulantes){
        lytErrorListaAula.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLista.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        correctoTxtCodLista.setText(codLista);
        correctoTxtNroPostulantes.setText("NRO DE POSTULANTES: "+nroPostulantes);
    }
    public void mostrarErrorDni(String codigoLista){
        lytErrorLista.setVisibility(View.VISIBLE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorListaAula.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        errorCodigoListaTxtCodLista.setText(codigoLista);

    }
    public void mostrarErrorAula(String codLista, int nroPostulantes,String aula){
        lytErrorLista.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorListaAula.setVisibility(View.VISIBLE);
        lytCorrecto.setVisibility(View.GONE);
        errorListaAulaTxtCodLista.setText(codLista);
        errorListaAulaTxtNroPostulantes.setText("NRO POSTULANTES: "+nroPostulantes);
        errorListaAulaTxtAula.setText(aula);
    }
    public void mostrarYaRegistrado(String codLista, int nroPostulantes, String aula){
        lytErrorLista.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.VISIBLE);
        lytErrorListaAula.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        yaRegistradoTxtCodLista.setText(codLista);
        yaRegistradoTxtNroPostulantes.setText("NRO POSTULANTES: "+nroPostulantes);
        yaRegistradoTxtAula.setText(aula);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
