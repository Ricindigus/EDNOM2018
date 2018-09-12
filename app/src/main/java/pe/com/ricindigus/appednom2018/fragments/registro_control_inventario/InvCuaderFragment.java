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
import pe.com.ricindigus.appednom2018.modelo.Cuadernillo;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Ficha;
import pe.com.ricindigus.appednom2018.modelo.Material;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvCuaderFragment extends Fragment {
    Spinner spAulas;
    EditText edtCuadernillo;
    ImageView btnBuscar;
    Context context;
    int nroLocal;
    String usuario;

    TextView correctoTxtDni;
    TextView correctoTxtNombre;
    TextView correctoTxtCuadernillo;

    TextView errorCuadernilloAulaTxtDni;
    TextView errorCuadernilloAulaTxtNombre;
    TextView errorCuadernilloAulaTxtAula;

    TextView yaRegistradoTxtDni;
    TextView yaRegistradoTxtNombre;
    TextView yaRegistradoTxtAula;

    TextView errorCodigoCuadernilloTxtCuadernillo;

    LinearLayout lytCorrecto;
    LinearLayout lytErrorCuadernilloAula;
    LinearLayout lytYaRegistrado;
    LinearLayout lytErrorCuadernillo;

    public InvCuaderFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InvCuaderFragment(int nroLocal, Context context, String usuario) {
        this.context = context;
        this.nroLocal = nroLocal;
        this.usuario = usuario;
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
        ocultarTeclado(edtCuadernillo);
        String codigoCuadernillo = edtCuadernillo.getText().toString();
        Data data = new Data(context);
        data.open();
        Material material = data.getMaterial(codigoCuadernillo,2);
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        nroAula = data.getNumeroAula(aula,nroLocal);
        if(material == null){
            mostrarErrorCodigo(codigoCuadernillo);
        }else{
            if(material.getIdlocal() == nroLocal && material.getNaula() == nroAula){
                Cuadernillo cuadernillo = data.getCuadernillo(codigoCuadernillo);
                if(cuadernillo == null) registrarCuadernillo(material);
                else mostrarYaRegistrado(material.getDni(),material.getNombres() + " " + material.getApe_paterno() + " " + material.getApe_materno(),material.getNaula());
            }else{
                mostrarErrorAula(material.getDni(),material.getNombres() +" "+ material.getApe_paterno() +" "+ material.getApe_materno(), "" + material.getNaula());
            }
        }
        edtCuadernillo.setText("");
        edtCuadernillo.requestFocus();
        data.close();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void registrarCuadernillo(Material material){
        Data data = new Data(context);
        data.open();
        Calendar calendario = Calendar.getInstance();
        int yy = calendario.get(Calendar.YEAR);
        int mm = calendario.get(Calendar.MONTH)+1;
        int dd = calendario.get(Calendar.DAY_OF_MONTH);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);
        int seg = calendario.get(Calendar.SECOND);
        Cuadernillo cuadernillo = new Cuadernillo();
        cuadernillo.setCodigo(material.getCodigo());
        cuadernillo.setTipo(material.getTipo());
        cuadernillo.setIdnacional(material.getIdnacional());
        cuadernillo.setCcdd(material.getCcdd());
        cuadernillo.setIdsede(material.getIdsede());
        cuadernillo.setSede(material.getSede());
        cuadernillo.setIdlocal(material.getIdlocal());
        cuadernillo.setLocal(material.getLocal());
        cuadernillo.setDni(material.getDni());
        cuadernillo.setNombres(material.getNombres());
        cuadernillo.setApe_paterno(material.getApe_paterno());
        cuadernillo.setApe_materno(material.getApe_materno());
        cuadernillo.setNaula(material.getNaula());
        cuadernillo.setCodpagina(material.getCodpagina());
        cuadernillo.setDia(dd);
        cuadernillo.setMes(mm);
        cuadernillo.setAnio(yy);
        cuadernillo.setHora(hora);
        cuadernillo.setMin(minuto);
        cuadernillo.setSeg(seg);
        cuadernillo.setEstado(0);
        data.insertarCuadernillo(cuadernillo);
        data.close();
        mostrarCorrecto(cuadernillo.getDni(),cuadernillo.getNombres() +" "+ cuadernillo.getApe_paterno() +" "+ cuadernillo.getApe_materno(),cuadernillo.getCodigo());
        final String c = cuadernillo.getCodigo();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("inventario").document(cuadernillo.getTipo()+""+cuadernillo.getCodigo());
        batch.update(documentReference, "check_registro", 1);
        batch.update(documentReference, "fecha_transferencia", FieldValue.serverTimestamp());
        batch.update(documentReference, "usuario_registro", usuario);
        batch.update(documentReference, "fecha_registro",
                new Timestamp(new Date(cuadernillo.getAnio()-1900,cuadernillo.getMes()-1,cuadernillo.getDia(),
                        cuadernillo.getHora(),cuadernillo.getMin(),cuadernillo.getSeg())));

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data data = new Data(context);
                data.open();
                data.actualizarCuadernilloSubido(c);
                data.close();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostrarCorrecto(String dni, String nombre, String codigoCuadernillo){
        lytErrorCuadernilloAula.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorCuadernillo.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        correctoTxtDni.setText(dni);
        correctoTxtNombre.setText(nombre);
        correctoTxtCuadernillo.setText(codigoCuadernillo);
    }

    public void mostrarErrorCodigo(String codigo){
        lytErrorCuadernillo.setVisibility(View.VISIBLE);
        lytYaRegistrado.setVisibility(View.GONE);
        lytErrorCuadernilloAula.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        errorCodigoCuadernilloTxtCuadernillo.setText(codigo);
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
    public void mostrarYaRegistrado(String dni, String nombre, int aula){
        lytErrorCuadernillo.setVisibility(View.GONE);
        lytYaRegistrado.setVisibility(View.VISIBLE);
        lytErrorCuadernilloAula.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        yaRegistradoTxtDni.setText(dni);
        yaRegistradoTxtNombre.setText(nombre);
        yaRegistradoTxtAula.setText(aula + "");
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
