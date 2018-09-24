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

    TextView txtTotal;
    TextView txtFaltan;
    TextView txtRegistrados;
    TextView txtTransferidos;

    String nombreColeccion;

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

        txtTotal = (TextView) rootView.findViewById(R.id.inventario_ficha_txtTotal);
        txtRegistrados = (TextView) rootView.findViewById(R.id.inventario_ficha_txtRegistrados);
        txtFaltan = (TextView) rootView.findViewById(R.id.inventario_ficha_txtFaltan);
        txtTransferidos = (TextView) rootView.findViewById(R.id.inventario_ficha_txtTransferidos);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (context != null){
            Data data =  new Data(context);
            data.open();
            nombreColeccion = data.getNombreColeccionInventario();
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
                txtTotal.setText("Total: " + data.getNroFichasTotales(nroLocal, nroAula));
                txtFaltan.setText("Faltan: " + data.getNroFichasFaltan(nroLocal,nroAula));
                txtRegistrados.setText("Registrados: " + data.getNroFichasRegistradas(nroLocal,nroAula));
                txtTransferidos.setText("Transferidos: " + data.getNroFichasTransferidas(nroLocal,nroAula));
                lytCorrecto.setVisibility(View.GONE);
                lytErrorFicha.setVisibility(View.GONE);
                lytYaRegistrado.setVisibility(View.GONE);
                lytErrorFichaAula.setVisibility(View.GONE);
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
        ocultarTeclado(edtFicha);
        String codigoInventario = edtFicha.getText().toString();
        Data data = new Data(context);
        data.open();
        InventarioReg inventarioReg = data.getFichaReg(codigoInventario);
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        nroAula = data.getNumeroAula(aula,nroLocal);
        if(inventarioReg == null){
            Inventario fichaPadron = data.getFicha(codigoInventario);
            if (fichaPadron == null) mostrarErrorCodigo(codigoInventario);
            else mostrarErrorAula(fichaPadron.getDni(),
                    fichaPadron.getNombres() +" "+ fichaPadron.getApe_paterno() +" "+ fichaPadron.getApe_materno(),
                    "" + fichaPadron.getNaula());
        }else{
            if(inventarioReg.getNaula() == nroAula){
                if(inventarioReg.getEstado() == 0) registrarInventario(inventarioReg.getCodigo());
                else mostrarYaRegistrado(inventarioReg.getDni(),inventarioReg.getNombres() + " " + inventarioReg.getApe_paterno() + " " + inventarioReg.getApe_materno(),inventarioReg.getNaula(), inventarioReg.getCodigo());
            }else{
                mostrarErrorAula(inventarioReg.getDni(),inventarioReg.getNombres() +" "+ inventarioReg.getApe_paterno() +" "+ inventarioReg.getApe_materno(), "" + inventarioReg.getNaula());
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
        data.actualizarFichaReg(codInventario,contentValues);
        InventarioReg inventarioReg = data.getFichaReg(codInventario);
        txtFaltan.setText("Faltan: " + data.getNroFichasFaltan(nroLocal,inventarioReg.getNaula()));
        txtRegistrados.setText("Registrados: " + data.getNroFichasRegistradas(nroLocal,inventarioReg.getNaula()));
        data.close();
        mostrarCorrecto(inventarioReg.getDni(),inventarioReg.getNombres() +" "+ inventarioReg.getApe_paterno() +" "+ inventarioReg.getApe_materno(),inventarioReg.getCodigo());
        final String c = inventarioReg.getCodigo();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(nombreColeccion).document(inventarioReg.getCodigo());
        batch.update(documentReference, "check_registro_ficha", 1);
        batch.update(documentReference, "fecha_transferencia_ficha", FieldValue.serverTimestamp());
        batch.update(documentReference, "usuario_registro_ficha", usuario);
        batch.update(documentReference, "fecha_registro_ficha",
                new Timestamp(new Date(inventarioReg.getAnio()-1900,inventarioReg.getMes()-1,inventarioReg.getDia(),
                        inventarioReg.getHora(),inventarioReg.getMin(),inventarioReg.getSeg())));
        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data data = new Data(context);
                data.open();
                data.actualizarFichaRegSubido(c);
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
        errorFichaAulaTxtNombre.setText(nombre);
        errorFichaAulaTxtDni.setText(dni);
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
