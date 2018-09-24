package pe.com.ricindigus.appednom2018.fragments.registro_control_inventario;


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
import android.widget.AdapterView;
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
import pe.com.ricindigus.appednom2018.modelo.Inventario;
import pe.com.ricindigus.appednom2018.modelo.InventarioReg;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;

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

    TextView txtTotal;
    TextView txtFaltan;
    TextView txtRegistrados;
    TextView txtTransferidos;


    String nombreColeccion;

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

        txtTotal = (TextView) rootView.findViewById(R.id.inventario_cuadernillo_txtTotal);
        txtRegistrados = (TextView) rootView.findViewById(R.id.inventario_cuadernillo_txtRegistrados);
        txtFaltan = (TextView) rootView.findViewById(R.id.inventario_cuadernillo_txtFaltan);
        txtTransferidos = (TextView) rootView.findViewById(R.id.inventario_cuadernillo_txtTransferidos);

        return rootView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (context != null){
            Data data =  new Data(context);
            data.open();
            ArrayList<String> aulas =  data.getArrayAulasRegistro(nroLocal);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, aulas);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spAulas.setAdapter(dataAdapter);
            data.close();

        }

        spAulas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Data data =  new Data(context);
                data.open();
                String aula = spAulas.getSelectedItem().toString();
                int nroAula = data.getNumeroAula(aula,nroLocal);
                txtTotal.setText("Total: " + data.getNroCuadernillosTotales(nroLocal, nroAula));
                txtFaltan.setText("Faltan: " + data.getNroCuadernillosFaltan(nroLocal,nroAula));
                txtRegistrados.setText("Registrados: " + data.getNroCuadernillosRegistrados(nroLocal,nroAula));
                txtTransferidos.setText("Transferidos: " + data.getNroCuadernillosTransferidos(nroLocal,nroAula));
                lytCorrecto.setVisibility(View.GONE);
                lytErrorCuadernillo.setVisibility(View.GONE);
                lytYaRegistrado.setVisibility(View.GONE);
                lytErrorCuadernilloAula.setVisibility(View.GONE);
                data.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBoton();
            }
        });

    }

    public void clickBoton(){
        ocultarTeclado(edtCuadernillo);
        String codigoInventario = edtCuadernillo.getText().toString();
        Data data = new Data(context);
        data.open();
        nombreColeccion = data.getNombreColeccionInventario();
        InventarioReg inventarioReg = data.getCuadernilloReg(codigoInventario);
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        nroAula = data.getNumeroAula(aula,nroLocal);
        if(inventarioReg == null){
            Inventario cuadernilloPadron = data.getCuadernillo(codigoInventario);
            if (cuadernilloPadron == null) mostrarErrorCodigo(codigoInventario);
            else mostrarErrorAula(cuadernilloPadron.getDni(),
                    cuadernilloPadron.getNombres() +" "+ cuadernilloPadron.getApe_paterno() +" "+ cuadernilloPadron.getApe_materno(),
                    "" + cuadernilloPadron.getNaula());
        }else{
            if(inventarioReg.getNaula() == nroAula){
                if(inventarioReg.getEstado() == 0) registrarInventario(inventarioReg.getCodigo());
                else mostrarYaRegistrado(inventarioReg.getDni(),
                        inventarioReg.getNombres() + " " + inventarioReg.getApe_paterno() + " " + inventarioReg.getApe_materno(),
                        inventarioReg.getNaula());
            }else{
                mostrarErrorAula(inventarioReg.getDni(),inventarioReg.getNombres() +" "+ inventarioReg.getApe_paterno() +" "+ inventarioReg.getApe_materno(), "" + inventarioReg.getNaula());
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
    public void registrarInventario(String codInventario){
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
        contentValues.put(SQLConstantes.inventarioreg_dia,dd);
        contentValues.put(SQLConstantes.inventarioreg_mes,mm);
        contentValues.put(SQLConstantes.inventarioreg_anio,yy);
        contentValues.put(SQLConstantes.inventarioreg_hora,hora);
        contentValues.put(SQLConstantes.inventarioreg_min,minuto);
        contentValues.put(SQLConstantes.inventarioreg_seg,seg);
        contentValues.put(SQLConstantes.inventarioreg_estado,1);
        data.actualizarCuadernilloReg(codInventario,contentValues);
        InventarioReg inventarioReg = data.getCuadernilloReg(codInventario);
        txtRegistrados.setText("Registrados: " + data.getNroCuadernillosRegistrados(nroLocal,inventarioReg.getNaula())+"/"+data.getNroCuadernillosTotales(nroLocal,inventarioReg.getNaula()));
        data.close();
        mostrarCorrecto(inventarioReg.getDni(),inventarioReg.getNombres() +" "+ inventarioReg.getApe_paterno() +" "+ inventarioReg.getApe_materno(),inventarioReg.getCodigo());
        final String c = inventarioReg.getCodigo();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(nombreColeccion).document(inventarioReg.getCodigo());
        batch.update(documentReference, "check_registro_cuadernillo", 1);
        batch.update(documentReference, "fecha_transferencia_cuadernillo", FieldValue.serverTimestamp());
        batch.update(documentReference, "usuario_registro_cuadernillo", usuario);
        batch.update(documentReference, "fecha_registro_cuadernillo",
                new Timestamp(new Date(inventarioReg.getAnio()-1900,inventarioReg.getMes()-1,inventarioReg.getDia(),
                        inventarioReg.getHora(),inventarioReg.getMin(),inventarioReg.getSeg())));
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data data = new Data(context);
                data.open();
                data.actualizarCuadernilloRegSubido(c);
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
        errorCuadernilloAulaTxtNombre.setText(nombre);
        errorCuadernilloAulaTxtDni.setText(dni);
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
