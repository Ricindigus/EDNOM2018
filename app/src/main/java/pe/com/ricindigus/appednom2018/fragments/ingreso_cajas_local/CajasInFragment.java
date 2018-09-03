package pe.com.ricindigus.appednom2018.fragments.ingreso_cajas_local;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import pe.com.ricindigus.appednom2018.modelo.Caja;
import pe.com.ricindigus.appednom2018.modelo.CajaIn;
import pe.com.ricindigus.appednom2018.modelo.CajaOut;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class CajasInFragment extends Fragment {

    TextView correctoTxtCodBarra;
    TextView correctoTxtAcl;
    TextView correctoTxtSede;
    TextView correctoTxtLocal;

    TextView yaRegistradoTxtCodBarra;
    TextView yaRegistradoTxtAcl;
    TextView yaRegistradoTxtFechaHora;

    LinearLayout lytCorrecto;
    LinearLayout lytDuplicado;
    LinearLayout lytNoExiste;

    EditText edtCodigo;
    ImageView btnBuscar;

    int numeroLocal;
    Context context;

    public CajasInFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CajasInFragment(int numeroLocal, Context context) {
        this.numeroLocal = numeroLocal;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cajas_in, container, false);
        correctoTxtCodBarra = (TextView) rootView.findViewById(R.id.caja_correcta_txtCodBarra);
        correctoTxtAcl = (TextView) rootView.findViewById(R.id.caja_correcta_txtAcl);
        correctoTxtSede = (TextView) rootView.findViewById(R.id.caja_correcta_txtSede);
        correctoTxtLocal = (TextView) rootView.findViewById(R.id.caja_correcta_txtLocal);

        yaRegistradoTxtCodBarra = (TextView) rootView.findViewById(R.id.caja_duplicada_txtCodBarra);
        yaRegistradoTxtAcl = (TextView) rootView.findViewById(R.id.caja_duplicada_txtAcl);
        yaRegistradoTxtFechaHora = (TextView) rootView.findViewById(R.id.caja_duplicada_txtFechaHora);

        lytCorrecto = (LinearLayout) rootView.findViewById(R.id.cajas_ingreso_lytCorrecto);
        lytDuplicado = (LinearLayout) rootView.findViewById(R.id.cajas_ingreso_lytYaRegistrado);
        lytNoExiste = (LinearLayout) rootView.findViewById(R.id.cajas_ingreso_lytNoExiste);

        edtCodigo = (EditText) rootView.findViewById(R.id.ingreso_cajas_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.ingreso_cajas_btnBuscar);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtCodigo.addTextChangedListener(new TextWatcher() {
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
        ocultarTeclado(edtCodigo);
        String codigoBarra = edtCodigo.getText().toString();
        Data data = new Data(context);
        data.open();
        CajaIn cajaIn = data.getCajaIn(codigoBarra);
        data.close();
        if(cajaIn == null){
            mostrarCodigoNoExiste();
        }else{
            registrarCaja(cajaIn);
        }
        edtCodigo.setText("");
        edtCodigo.requestFocus();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void registrarCaja(CajaIn cajaIn){
        String codigoBarra = cajaIn.getCod_barra_caja();
        if (!existeRegistro(cajaIn.getCod_barra_caja())) {
            Data data = new Data(context);
            data.open();
            Calendar calendario = Calendar.getInstance();
            int yy = calendario.get(Calendar.YEAR);
            int mm = calendario.get(Calendar.MONTH) + 1;
            int dd = calendario.get(Calendar.DAY_OF_MONTH);
            int hora = calendario.get(Calendar.HOUR_OF_DAY);
            int minuto = calendario.get(Calendar.MINUTE);
            int segundos = calendario.get(Calendar.SECOND);
            ContentValues contentValues = new ContentValues();
            contentValues.put(SQLConstantes.cajas_entrada_fecha_reg_dia,dd);
            contentValues.put(SQLConstantes.cajas_entrada_fecha_reg_mes,mm);
            contentValues.put(SQLConstantes.cajas_entrada_fecha_reg_anio,yy);
            contentValues.put(SQLConstantes.cajas_entrada_fecha_reg_hora,hora);
            contentValues.put(SQLConstantes.cajas_entrada_fecha_reg_min,minuto);
            contentValues.put(SQLConstantes.cajas_entrada_fecha_reg_seg,segundos);
            contentValues.put(SQLConstantes.cajas_entrada_check_reg,1);
            if (cajaIn.getNlado() == 1){
                if (cajaIn.getTipo() == 3) contentValues.put(SQLConstantes.cajas_entrada_estado,2);
                else contentValues.put(SQLConstantes.cajas_entrada_estado,cajaIn.getEstado() + 1);
            }
            data.actualizarCajaIn(codigoBarra,contentValues);
            //Si es codigo "20" debe guardar en el otro codigo "10"
            if(cajaIn.getNlado() == 2){
                CajaIn cajaIn1 = data.getCajaIn(getCodigoAux(codigoBarra));
                contentValues = new ContentValues();
                contentValues.put(SQLConstantes.cajas_entrada_estado,cajaIn1.getEstado() + 1);
                data.actualizarCajaIn(cajaIn1.getCod_barra_caja(),contentValues);
            }
            data.close();
            mostrarCorrecto(cajaIn.getCod_barra_caja(), cajaIn.getAcl(), cajaIn.getSede(), cajaIn.getLocal());
        }
    }

    public String getCodigoAux(String cod2){
        String cod1 = cod2.substring(0,cod2.length()-2) + "10";
        return cod1;
    }

    public boolean existeRegistro(String codigoBarra){
        boolean existe = false;
        Data d = new Data(context);
        d.open();
        CajaIn a = d.getCajaIn(codigoBarra);
        if(a.getCheck_reg() == 1){
            existe = true;
            mostrarDuplicado(a.getCod_barra_caja(),a.getAcl(),
                    checkDigito(a.getDia()) +"/"+ checkDigito(a.getMes()) +"/"+ a.getAnio() +
                            " " + checkDigito(a.getHora()) + ":" + checkDigito(a.getMin())+ ":" + checkDigito(a.getSeg()));
        }
        return existe;
    }

    public void mostrarCorrecto(String codigoBarra, int acl, String sede, String local){
        lytDuplicado.setVisibility(View.GONE);
        lytNoExiste.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        correctoTxtCodBarra.setText(codigoBarra);
        correctoTxtAcl.setText("ACL: " + acl);
        correctoTxtSede.setText(sede);
        correctoTxtLocal.setText(local);
    }
    public void mostrarCodigoNoExiste(){
        lytNoExiste.setVisibility(View.VISIBLE);
        lytDuplicado.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
    }

    public void mostrarDuplicado(String codBarra, int acl, String fecha){
        lytCorrecto.setVisibility(View.GONE);
        lytDuplicado.setVisibility(View.VISIBLE);
        lytNoExiste.setVisibility(View.GONE);
        yaRegistradoTxtCodBarra.setText(codBarra);
        yaRegistradoTxtAcl.setText("ACL: " + acl);
        yaRegistradoTxtFechaHora.setText(fecha);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
