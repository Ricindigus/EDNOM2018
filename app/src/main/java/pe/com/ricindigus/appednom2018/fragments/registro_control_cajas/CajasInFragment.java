package pe.com.ricindigus.appednom2018.fragments.registro_control_cajas;


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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.activities.MainActivity;
import pe.com.ricindigus.appednom2018.modelo.Caja;
import pe.com.ricindigus.appednom2018.modelo.CajaReg;
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

    TextView txtTotal;
    TextView txtRegistrados;
    TextView txtTransferidos;


    TextView yaRegistradoTxtCodBarra;
    TextView yaRegistradoTxtAcl;
    TextView yaRegistradoTxtFechaHora;

    TextView errorLocalTxtCodBarra;
    TextView errorLocalTxtDireccion;
    TextView errorLocalTxtSede;
    TextView errorLocalTxtLocal;

    LinearLayout lytCorrecto;
    LinearLayout lytDuplicado;
    LinearLayout lytNoExiste;
    LinearLayout lytErrorLocal;


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

        errorLocalTxtCodBarra = (TextView) rootView.findViewById(R.id.caja_errorlocal_txtCodBarra);
        errorLocalTxtDireccion = (TextView) rootView.findViewById(R.id.caja_errorlocal_txtDireccion);
        errorLocalTxtSede = (TextView) rootView.findViewById(R.id.caja_errorlocal_txtSede);
        errorLocalTxtLocal = (TextView) rootView.findViewById(R.id.caja_errorlocal_txtLocal);

        lytCorrecto = (LinearLayout) rootView.findViewById(R.id.cajas_ingreso_lytCorrecto);
        lytDuplicado = (LinearLayout) rootView.findViewById(R.id.cajas_ingreso_lytYaRegistrado);
        lytNoExiste = (LinearLayout) rootView.findViewById(R.id.cajas_ingreso_lytNoExiste);
        lytErrorLocal = (LinearLayout) rootView.findViewById(R.id.cajas_ingreso_lytErrorLocal);

        edtCodigo = (EditText) rootView.findViewById(R.id.ingreso_cajas_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.ingreso_cajas_btnBuscar);

        txtTotal = (TextView) rootView.findViewById(R.id.ingreso_cajas_txtTotales);
        txtRegistrados = (TextView) rootView.findViewById(R.id.ingreso_cajas_txtRegistrados);
        txtTransferidos = (TextView) rootView.findViewById(R.id.ingreso_cajas_txtTransferidos);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data data =  new Data(context);
        data.open();
        txtRegistrados.setText("Total: " +data.getNroCajasTotales(numeroLocal));
        txtRegistrados.setText("Registrados: " + data.getNroCajasEntradaLeidas(numeroLocal));
        txtTransferidos.setText("Transferidos: " + data.getNroCajasEntradaTransferidos(numeroLocal));

        data.close();
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
        CajaReg cajaIn = data.getCajaReg(codigoBarra,numeroLocal);

        if(cajaIn == null){
            Caja caja = data.getCaja(codigoBarra);
            if (caja == null){
                mostrarCodigoNoExiste();
            }else{
                mostrarErrorLocal(codigoBarra,caja.getNom_sede(),caja.getNom_local(),caja.getDireccion());
            }
        }else{
            registrarCaja(cajaIn);
        }
        edtCodigo.setText("");
        edtCodigo.requestFocus();
        data.close();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void registrarCaja(CajaReg cajaIn){
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
            contentValues.put(SQLConstantes.cajasreg_dia_entrada,dd);
            contentValues.put(SQLConstantes.cajasreg_mes_entrada,mm);
            contentValues.put(SQLConstantes.cajasreg_anio_entrada,yy);
            contentValues.put(SQLConstantes.cajasreg_hora_entrada,hora);
            contentValues.put(SQLConstantes.cajasreg_min_entrada,minuto);
            contentValues.put(SQLConstantes.cajasreg_seg_entrada,segundos);
            contentValues.put(SQLConstantes.cajasreg_check_entrada,1);
            if (cajaIn.getNlado() == 1){
                if (cajaIn.getTipo() == 3) contentValues.put(SQLConstantes.cajasreg_estado_entrada,2);
                else contentValues.put(SQLConstantes.cajasreg_estado_entrada,cajaIn.getEstado_entrada() + 1);
                data.actualizarCajaReg(codigoBarra,contentValues);
                txtRegistrados.setText("Registrados: " + data.getNroCajasEntradaLeidas(numeroLocal));
            }

            //Si es codigo "20" debe guardar en el otro codigo "10"
            if(cajaIn.getNlado() == 2){
                CajaReg cajaIn1 = data.getCajaReg(getCodigoAux(codigoBarra),numeroLocal);
                contentValues = new ContentValues();
                contentValues.put(SQLConstantes.cajasreg_estado_entrada,cajaIn1.getEstado_entrada() + 1);
                data.actualizarCajaReg(cajaIn1.getCod_barra_caja(),contentValues);
                txtRegistrados.setText("Registrados: " + data.getNroCajasEntradaLeidas(numeroLocal));
            }
            data.close();
            mostrarCorrecto(cajaIn.getCod_barra_caja(), cajaIn.getAcl(), cajaIn.getNom_sede(), cajaIn.getNom_local());
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
        CajaReg a = d.getCajaReg(codigoBarra,numeroLocal);
        if(a.getCheck_entrada() == 1){
            existe = true;
            mostrarDuplicado(a.getCod_barra_caja(),a.getAcl(),
                    checkDigito(a.getDia_entrada()) +"/"+ checkDigito(a.getMes_entrada()) +"/"+ a.getAnio_entrada() +
                            " " + checkDigito(a.getHora_entrada()) + ":" + checkDigito(a.getMin_entrada())+ ":" + checkDigito(a.getSeg_entrada()));
        }
        return existe;
    }

    public void mostrarCorrecto(String codigoBarra, int acl, String sede, String local){
        lytDuplicado.setVisibility(View.GONE);
        lytNoExiste.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.VISIBLE);
        lytErrorLocal.setVisibility(View.GONE);
        correctoTxtCodBarra.setText(codigoBarra);
        correctoTxtAcl.setText("ACL: " + acl);
        correctoTxtSede.setText(sede);
        correctoTxtLocal.setText(local);
    }

    public void mostrarErrorLocal(String codigoBarra,String sede, String local,String direccion){
        lytDuplicado.setVisibility(View.GONE);
        lytNoExiste.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.VISIBLE);
        errorLocalTxtCodBarra.setText(codigoBarra);
        errorLocalTxtSede.setText(sede);
        errorLocalTxtLocal.setText(local);
        errorLocalTxtDireccion.setText(direccion);
    }
    public void mostrarCodigoNoExiste(){
        lytNoExiste.setVisibility(View.VISIBLE);
        lytDuplicado.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.GONE);
    }

    public void mostrarDuplicado(String codBarra, int acl, String fecha){
        lytCorrecto.setVisibility(View.GONE);
        lytDuplicado.setVisibility(View.VISIBLE);
        lytNoExiste.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.GONE);
        yaRegistradoTxtCodBarra.setText(codBarra);
        yaRegistradoTxtAcl.setText("ACL: " + acl);
        yaRegistradoTxtFechaHora.setText(fecha);
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
