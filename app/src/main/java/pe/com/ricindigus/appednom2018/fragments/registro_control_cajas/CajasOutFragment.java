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
import pe.com.ricindigus.appednom2018.modelo.Caja;
import pe.com.ricindigus.appednom2018.modelo.CajaReg;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;
import pe.com.ricindigus.appednom2018.util.ActividadInterfaz;
import pe.com.ricindigus.appednom2018.util.TipoFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CajasOutFragment extends Fragment {

    TextView correctoTxtCodBarra;
    TextView correctoTxtAcl;
    TextView correctoTxtSede;
    TextView correctoTxtLocal;

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
    ImageView btnReporte;


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

        errorLocalTxtCodBarra = (TextView) rootView.findViewById(R.id.caja_errorlocal_txtCodBarra);
        errorLocalTxtDireccion = (TextView) rootView.findViewById(R.id.caja_errorlocal_txtDireccion);
        errorLocalTxtSede = (TextView) rootView.findViewById(R.id.caja_errorlocal_txtSede);
        errorLocalTxtLocal = (TextView) rootView.findViewById(R.id.caja_errorlocal_txtLocal);

        lytCorrecto = (LinearLayout) rootView.findViewById(R.id.cajas_salida_lytCorrecto);
        lytDuplicado = (LinearLayout) rootView.findViewById(R.id.cajas_salida_lytYaRegistrado);
        lytNoExiste = (LinearLayout) rootView.findViewById(R.id.cajas_salida_lytNoExiste);
        lytErrorLocal = (LinearLayout) rootView.findViewById(R.id.cajas_salida_lytErrorLocal);


        edtCodigo = (EditText) rootView.findViewById(R.id.salida_cajas_edtCodigo);
        btnBuscar = (ImageView) rootView.findViewById(R.id.salida_cajas_btnBuscar);
        btnReporte = (ImageView) rootView.findViewById(R.id.salida_cajas_btnReporte);

        txtRegistrados = (TextView) rootView.findViewById(R.id.salida_cajas_txtRegistrados);
        txtTransferidos = (TextView) rootView.findViewById(R.id.salida_cajas_txtTransferidos);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data data =  new Data(context);
        data.open();
//        txtTotal.setText("Total: " + data.getNroCajasTotales(numeroLocal));
        txtRegistrados.setText("Registrados: " + data.getNroCajasSalidaLeidas(numeroLocal)+"/"+ data.getNroCajasTotales(numeroLocal));
        txtTransferidos.setText("Transferidos: " + data.getNroCajasSalidaTransferidos(numeroLocal)+"/"+ data.getNroCajasTotales(numeroLocal));
        data.close();
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBoton();
            }
        });
        btnReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActividadInterfaz actividadInterfaz = (ActividadInterfaz) getActivity();
                actividadInterfaz.irReporte(TipoFragment.REPORTES_LISTADO_SALIDA_CAJAS);
                ocultarTeclado(btnReporte);
            }
        });
    }

    public void clickBoton(){
        ocultarTeclado(edtCodigo);
        String codigoBarra = edtCodigo.getText().toString();
        Data data = new Data(context);
        data.open();
        CajaReg cajaOut = data.getCajaReg(codigoBarra,numeroLocal);
        if(cajaOut == null){
            Caja caja = data.getCaja(codigoBarra);
            if (caja == null){
                mostrarCodigoNoExiste();
            }else{
                mostrarErrorLocal(codigoBarra,caja.getNom_sede(),caja.getNom_local(),caja.getDireccion());
            }
        }else{
            registrarCaja(cajaOut);
        }
        data.close();
        edtCodigo.setText("");
        edtCodigo.requestFocus();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void registrarCaja(CajaReg cajaOut){
        String codigoBarra = cajaOut.getCod_barra_caja();
        if (!existeRegistro(cajaOut.getCod_barra_caja())) {
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
            contentValues.put(SQLConstantes.cajasreg_dia_salida,dd);
            contentValues.put(SQLConstantes.cajasreg_mes_salida,mm);
            contentValues.put(SQLConstantes.cajasreg_anio_salida,yy);
            contentValues.put(SQLConstantes.cajasreg_hora_salida,hora);
            contentValues.put(SQLConstantes.cajasreg_min_salida,minuto);
            contentValues.put(SQLConstantes.cajasreg_seg_salida,segundos);
            contentValues.put(SQLConstantes.cajasreg_check_salida,1);
            data.actualizarCajaReg(codigoBarra,contentValues);
            if (cajaOut.getNlado() == 1){
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put(SQLConstantes.cajasreg_dia_salida_final,dd);
                contentValues1.put(SQLConstantes.cajasreg_mes_salida_final,mm);
                contentValues1.put(SQLConstantes.cajasreg_anio_salida_final,yy);
                contentValues1.put(SQLConstantes.cajasreg_hora_salida_final,hora);
                contentValues1.put(SQLConstantes.cajasreg_min_salida_final,minuto);
                contentValues1.put(SQLConstantes.cajasreg_seg_salida_final,segundos);
                contentValues1.put(SQLConstantes.cajasreg_leido_orden_salida,hora*60*60+minuto*60+segundos);
                if (cajaOut.getTipo() == 3) contentValues1.put(SQLConstantes.cajasreg_estado_salida,2);
                else contentValues1.put(SQLConstantes.cajasreg_estado_salida,cajaOut.getEstado_salida() + 1);
                data.actualizarCajaReg(codigoBarra,contentValues1);
                txtRegistrados.setText("Registrados: " + data.getNroCajasSalidaLeidas(numeroLocal)+"/"+ data.getNroCajasTotales(numeroLocal));
            }

            //Si es codigo "20" debe guardar en el otro codigo "10"
            if(cajaOut.getNlado() == 2){
                CajaReg cajaOut1 = data.getCajaReg(getCodigoAux(codigoBarra),numeroLocal);
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put(SQLConstantes.cajasreg_estado_salida,cajaOut1.getEstado_salida() + 1);
                contentValues2.put(SQLConstantes.cajasreg_dia_salida_final,dd);
                contentValues2.put(SQLConstantes.cajasreg_mes_salida_final,mm);
                contentValues2.put(SQLConstantes.cajasreg_anio_salida_final,yy);
                contentValues2.put(SQLConstantes.cajasreg_hora_salida_final,hora);
                contentValues2.put(SQLConstantes.cajasreg_min_salida_final,minuto);
                contentValues2.put(SQLConstantes.cajasreg_seg_salida_final,segundos);
                contentValues2.put(SQLConstantes.cajasreg_leido_orden_salida,hora*60*60+minuto*60+segundos);
                data.actualizarCajaReg(cajaOut1.getCod_barra_caja(),contentValues2);
                txtRegistrados.setText("Registrados: " + data.getNroCajasSalidaLeidas(numeroLocal)+"/"+ data.getNroCajasTotales(numeroLocal));
            }
            data.close();
            mostrarCorrecto(cajaOut.getCod_barra_caja(), cajaOut.getAcl(), cajaOut.getNom_sede(), cajaOut.getNom_local());
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
        if(a.getCheck_salida() == 1){
            existe = true;
            mostrarDuplicado(a.getCod_barra_caja(),a.getAcl(),
                    checkDigito(a.getDia_salida()) +"/"+ checkDigito(a.getMes_salida()) +"/"+ a.getAnio_salida() +
                            " " + checkDigito(a.getHora_salida()) + ":" + checkDigito(a.getMin_salida())+ ":" + checkDigito(a.getSeg_salida()));
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
    public void mostrarCodigoNoExiste(){
        lytNoExiste.setVisibility(View.VISIBLE);
        lytDuplicado.setVisibility(View.GONE);
        lytCorrecto.setVisibility(View.GONE);
        lytErrorLocal.setVisibility(View.GONE);
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

