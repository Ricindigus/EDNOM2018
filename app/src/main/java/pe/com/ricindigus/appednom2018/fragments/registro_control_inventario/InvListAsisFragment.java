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
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Ficha;
import pe.com.ricindigus.appednom2018.modelo.Listado;
import pe.com.ricindigus.appednom2018.modelo.Material;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvListAsisFragment extends Fragment {
    Spinner spAulas;
    EditText edtLista;
    ImageView btnBuscar;
    Context context;
    int nroLocal;
    String usuario;

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
    public InvListAsisFragment(int nroLocal, Context context, String usuario) {
        this.context = context;
        this.nroLocal = nroLocal;
        this.usuario = usuario;
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
        if (context != null){
            Data data =  new Data(context);
            data.open();
            ArrayList<String> aulas =  data.getArrayAulas(nroLocal);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, aulas);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spAulas.setAdapter(dataAdapter);
            data.close();
        }

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBoton();
            }
        });
    }

    public void clickBoton(){
        ocultarTeclado(edtLista);
        String codigoListado = edtLista.getText().toString();
        Data data = new Data(context);
        data.open();
        Material material = data.getMaterial(codigoListado,3);
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        int nroPostulantes = 0;
        nroAula = data.getNumeroAula(aula,nroLocal);
        if(material == null){
            mostrarErrorCodigo(codigoListado);
        }else{
            nroPostulantes = data.getNroPostulantesListado(material.getCodigo());
            if(material.getIdlocal() == nroLocal && material.getNaula() == nroAula){
                Listado listado = data.getListado(codigoListado);
                if(listado == null) registrarListado(material);
                else mostrarYaRegistrado(material.getCodigo(),nroPostulantes,material.getNaula());
            }else{
                mostrarErrorAula(material.getDni(),nroPostulantes, material.getNaula());
            }
        }
        edtLista.setText("");
        edtLista.requestFocus();
        data.close();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void registrarListado(Material material){
        Data data = new Data(context);
        data.open();
        int nroPostulantes = 0;
        nroPostulantes = data.getNroPostulantesListado(material.getCodigo());
        Calendar calendario = Calendar.getInstance();
        int yy = calendario.get(Calendar.YEAR);
        int mm = calendario.get(Calendar.MONTH)+1;
        int dd = calendario.get(Calendar.DAY_OF_MONTH);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);
        int seg = calendario.get(Calendar.SECOND);
        Listado listado = new Listado();
        listado.setCodigo(material.getCodigo());
        listado.setTipo(material.getTipo());
        listado.setIdnacional(material.getIdnacional());
        listado.setCcdd(material.getCcdd());
        listado.setIdsede(material.getIdsede());
        listado.setSede(material.getSede());
        listado.setIdlocal(material.getIdlocal());
        listado.setLocal(material.getLocal());
        listado.setNaula(material.getNaula());
        listado.setNpostulantes(nroPostulantes);
        listado.setDia(dd);
        listado.setMes(mm);
        listado.setAnio(yy);
        listado.setHora(hora);
        listado.setMin(minuto);
        listado.setSeg(seg);
        listado.setEstado(0);
        data.insertarListado(listado);
        data.close();
        mostrarCorrecto(listado.getCodigo(),nroPostulantes);
        final String c = listado.getCodigo();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("inventario").document(listado.getCodigo());
        batch.update(documentReference, "check_registro", 1);
        batch.update(documentReference, "fecha_transferencia", FieldValue.serverTimestamp());
        batch.update(documentReference, "usuario_registro", usuario);
        batch.update(documentReference, "fecha_registro",
                new Timestamp(new Date(listado.getAnio()-1900,listado.getMes()-1,listado.getDia(),
                        listado.getHora(),listado.getMin(),listado.getSeg())));
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data data = new Data(context);
                data.open();
                data.actualizarListadoSubido(c);
                data.close();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostrarCorrecto(String codLista, int nroPostulantes){
        lytErrorListaAula.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorLista.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        correctoTxtCodLista.setText(codLista);
        correctoTxtNroPostulantes.setText(""+nroPostulantes);
    }
    public void mostrarErrorCodigo(String codigoLista){
        lytErrorLista.setVisibility(View.VISIBLE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorListaAula.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        errorCodigoListaTxtCodLista.setText(codigoLista);

    }
    public void mostrarErrorAula(String codLista, int nroPostulantes,int aula){
        lytErrorLista.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorListaAula.setVisibility(View.VISIBLE);
        lytCorrecto.setVisibility(View.GONE);
        errorListaAulaTxtCodLista.setText(codLista);
        errorListaAulaTxtNroPostulantes.setText(""+nroPostulantes);
        errorListaAulaTxtAula.setText(aula +"");
    }
    public void mostrarYaRegistrado(String codLista, int nroPostulantes, int aula){
        lytErrorLista.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.VISIBLE);
        lytErrorListaAula.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        yaRegistradoTxtCodLista.setText(codLista);
        yaRegistradoTxtNroPostulantes.setText(""+nroPostulantes);
        yaRegistradoTxtAula.setText(""+aula);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
