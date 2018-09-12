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
import pe.com.ricindigus.appednom2018.modelo.Material;
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
    String usuario;

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
    public InvFichaFragment(int nroLocal, Context context, String usuario) {
        this.context = context;
        this.nroLocal = nroLocal;
        this.usuario = usuario;
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
        ocultarTeclado(edtFicha);
        String codigoFicha = edtFicha.getText().toString();
        Data data = new Data(context);
        data.open();
        Material material = data.getMaterial(codigoFicha,1);
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        nroAula = data.getNumeroAula(aula,nroLocal);
        if(material == null){
            mostrarErrorCodigo(codigoFicha);
        }else{
            if(material.getIdlocal() == nroLocal && material.getNaula() == nroAula){
                Ficha ficha = data.getFicha(codigoFicha);
                if(ficha == null) registrarFicha(material);
                else mostrarYaRegistrado(material.getDni(),material.getNombres() + " " + material.getApe_paterno() + " " + material.getApe_materno(),material.getNaula(), material.getCodigo());
            }else{
                mostrarErrorAula(material.getDni(),material.getNombres() +" "+ material.getApe_paterno() +" "+ material.getApe_materno(), "" + material.getNaula());
            }
        }
        edtFicha.setText("");
        edtFicha.requestFocus();
        data.close();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void registrarFicha(Material material){
        Data data = new Data(context);
        data.open();
        Calendar calendario = Calendar.getInstance();
        int yy = calendario.get(Calendar.YEAR);
        int mm = calendario.get(Calendar.MONTH)+1;
        int dd = calendario.get(Calendar.DAY_OF_MONTH);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);
        int seg = calendario.get(Calendar.SECOND);
        Ficha ficha = new Ficha();
        ficha.setCodigo(material.getCodigo());
        ficha.setTipo(material.getTipo());
        ficha.setIdnacional(material.getIdnacional());
        ficha.setCcdd(material.getCcdd());
        ficha.setIdsede(material.getIdsede());
        ficha.setSede(material.getSede());
        ficha.setIdlocal(material.getIdlocal());
        ficha.setLocal(material.getLocal());
        ficha.setDni(material.getDni());
        ficha.setNombres(material.getNombres());
        ficha.setApe_paterno(material.getApe_paterno());
        ficha.setApe_materno(material.getApe_materno());
        ficha.setNaula(material.getNaula());
        ficha.setCodpagina(material.getCodpagina());
        ficha.setDia(dd);
        ficha.setMes(mm);
        ficha.setAnio(yy);
        ficha.setHora(hora);
        ficha.setMin(minuto);
        ficha.setSeg(seg);
        ficha.setEstado(0);
        data.insertarFicha(ficha);
        long numfichas = data.getNumeroItemsFicha();
        data.close();
        mostrarCorrecto(ficha.getDni(),ficha.getNombres() +" "+ ficha.getApe_paterno() +" "+ ficha.getApe_materno(),ficha.getCodigo());
        final String c = ficha.getCodigo();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("inventario").document(ficha.getTipo()+""+ficha.getCodigo());
        batch.update(documentReference, "check_registro", 1);
        batch.update(documentReference, "fecha_transferencia", FieldValue.serverTimestamp());
        batch.update(documentReference, "usuario_reg", usuario);
        batch.update(documentReference, "fech_reg_ingreso",
                new Timestamp(new Date(ficha.getAnio()-1900,ficha.getMes()-1,ficha.getDia(),
                        ficha.getHora(),ficha.getMin(),ficha.getSeg())));
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data data = new Data(context);
                data.open();
                data.actualizarFichaSubido(c);
                data.close();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
            }
        });
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
    public void mostrarErrorCodigo(String codigoFicha){
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
    public void mostrarYaRegistrado(String dni, String nombre, int nAula, String codigoFicha){
        lytErrorFicha.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.VISIBLE);
        lytErrorFichaAula.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        yaRegistradoTxtDni.setText(dni);
        yaRegistradoTxtNombre.setText(nombre);
        yaRegistradoTxtAula.setText(nAula+"");
        yaRegistradoTxtFicha.setText(codigoFicha);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
