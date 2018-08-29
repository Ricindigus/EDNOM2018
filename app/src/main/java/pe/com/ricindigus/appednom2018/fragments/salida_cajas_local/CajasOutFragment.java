package pe.com.ricindigus.appednom2018.fragments.salida_cajas_local;


import android.annotation.SuppressLint;
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
import pe.com.ricindigus.appednom2018.modelo.CajaOut;
import pe.com.ricindigus.appednom2018.modelo.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class CajasOutFragment extends Fragment {

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

    public CajasOutFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CajasOutFragment(int numeroLocal, Context context) {
        this.numeroLocal = numeroLocal;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cajas_out, container, false);
        correctoTxtCodBarra = (TextView) rootView.findViewById(R.id.caja_correcta_txtCodBarra);
        correctoTxtAcl = (TextView) rootView.findViewById(R.id.caja_correcta_txtAcl);
        correctoTxtSede = (TextView) rootView.findViewById(R.id.caja_correcta_txtSede);
        correctoTxtLocal = (TextView) rootView.findViewById(R.id.caja_correcta_txtLocal);

        yaRegistradoTxtCodBarra = (TextView) rootView.findViewById(R.id.caja_duplicada_txtCodBarra);
        yaRegistradoTxtAcl = (TextView) rootView.findViewById(R.id.caja_duplicada_txtAcl);
        yaRegistradoTxtFechaHora = (TextView) rootView.findViewById(R.id.caja_duplicada_txtFechaHora);

        lytCorrecto = (LinearLayout) rootView.findViewById(R.id.cajas_salida_lytCorrecto);
        lytDuplicado = (LinearLayout) rootView.findViewById(R.id.cajas_salida_lytYaRegistrado);
        lytNoExiste = (LinearLayout) rootView.findViewById(R.id.cajas_salida_lytNoExiste);

        edtCodigo = (EditText) rootView.findViewById(R.id.salida_cajas_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.salida_cajas_btnBuscar);
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
        Caja caja = data.getCajaxCodigo(codigoBarra);
        data.close();
        if(caja == null){
            mostrarCodigoNoExiste();
        }else{
            registrarCaja(caja);
        }
        edtCodigo.setText("");
        edtCodigo.requestFocus();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void registrarCaja(Caja caja){
        if(numeroLocal == caja.getIdlocal()) {
            if(!existeRegistro(caja.getCod_barra_caja())){
                Data data = new Data(context);
                data.open();
                CajaOut cajaOut = new CajaOut();
                cajaOut.set_id(caja.getCod_barra_caja());
                cajaOut.setCod_barra_caja(caja.getCod_barra_caja());
                cajaOut.setIdsede(caja.getIdsede());
                cajaOut.setSede(caja.getSede());
                cajaOut.setIdlocal(caja.getIdlocal());
                cajaOut.setLocal(caja.getLocal());
                cajaOut.setAcl(caja.getAcl());
                cajaOut.setTipo(caja.getTipo());
                Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH)+1;
                int dd = calendario.get(Calendar.DAY_OF_MONTH);
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minuto = calendario.get(Calendar.MINUTE);
                int segundos = calendario.get(Calendar.SECOND);
                cajaOut.setDia(dd);
                cajaOut.setMes(mm);
                cajaOut.setAnio(yy);
                cajaOut.setHora(hora);
                cajaOut.setMin(minuto);
                cajaOut.setSeg(segundos);
                cajaOut.setSubido(0);
                data.insertarCajaOut(cajaOut);
                data.close();
                mostrarCorrecto(cajaOut.getCod_barra_caja(),cajaOut.getAcl(),cajaOut.getSede(),cajaOut.getLocal());
                WriteBatch batch = FirebaseFirestore.getInstance().batch();
                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("cajas").document(cajaOut.getCod_barra_caja());
                batch.update(documentReference, "check_reg_salida", 1);
                batch.update(documentReference, "fech_trans_salida", FieldValue.serverTimestamp());
                batch.update(documentReference, "fech_reg_salida", new Timestamp(new Date(cajaOut.getAnio()-1900,cajaOut.getMes()-1,cajaOut.getDia(),cajaOut.getHora(),cajaOut.getMin(),cajaOut.getSeg())));
                final String codigoBarra = cajaOut.getCod_barra_caja();
                batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Data data = new Data(context);
                        data.open();
                        data.actualizarCajaOutSubido(codigoBarra);
                        data.close();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }else{
            mostrarCodigoNoExiste();
        }
    }

    public boolean existeRegistro(String codigoBarra){
        boolean existe = false;
        Data d = new Data(context);
        d.open();
        CajaOut a = d.getCajaOut(codigoBarra);
        if(a != null){
            existe = true;
            mostrarDuplicado(a.getCod_barra_caja(),a.getAcl(),
                    checkDigito(a.getDia()) +"/"+ checkDigito(a.getMes()) +"/"+ a.getAnio() +
                            " " + checkDigito(a.getHora()) + ":" + checkDigito(a.getMin())+ ":" + checkDigito(a.getSeg()));
        }
        d.close();
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
