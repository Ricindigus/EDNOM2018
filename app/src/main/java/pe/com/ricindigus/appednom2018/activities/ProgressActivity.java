package pe.com.ricindigus.appednom2018.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaRaReg;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaReg;
import pe.com.ricindigus.appednom2018.modelo.CajaReg;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.InventarioReg;
import pe.com.ricindigus.appednom2018.modelo.UsuarioLocal;

public class ProgressActivity extends AppCompatActivity {
    TextInputEditText edtClave;
    TextView txtAquiMarco;
    Button btnIngresar;
    String temaApp;
    UsuarioLocal usuarioLocal;
    int maximo = 0;
    int maximo1 = 0;
    int maximo2 = 0;

    TextView txtCarga;
    ProgressBar progressBar;
    String clave;
    int tipoFiltro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        txtCarga = (TextView) findViewById(R.id.progress_mensaje_carga);
        progressBar = (ProgressBar) findViewById(R.id.progress_progreso);
        clave = getIntent().getExtras().getString("clave");
        tipoFiltro = getIntent().getExtras().getInt("tipo_filtro");
        Data data = new Data(ProgressActivity.this);
        data.open();
        usuarioLocal = data.getUsuarioLocal(clave);
        data.close();
        if(tipoFiltro == 2) new MyAsyncTaskOperador().execute(0);
        else new MyAsyncTaskSupervisor().execute(0);

    }

    public class MyAsyncTaskOperador extends AsyncTask<Integer,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            String mensaje = "";
            Data data = new Data(ProgressActivity.this);
            data.open();
            ArrayList<AsistenciaReg> asistenciaRegs = data.filtrarMarcoAsistencia(usuarioLocal.getIdlocal());
            ArrayList<InventarioReg> fichasRegs = data.filtrarMarcoInventarioFichas(usuarioLocal.getIdlocal());
            ArrayList<InventarioReg> cuadernillosRegs = data.filtrarMarcoInventarioCuadernillos(usuarioLocal.getIdlocal());
            ArrayList<InventarioReg> listadosRegs = data.filtrarMarcoInventarioListados(usuarioLocal.getIdlocal());
            maximo1 = asistenciaRegs.size();
            maximo2 = fichasRegs.size()+cuadernillosRegs.size()+listadosRegs.size();
            maximo = maximo1 + maximo2;
            progressBar.setMax(maximo);
            int i = 1;
            for (AsistenciaReg asistenciaReg : asistenciaRegs) {
                try {
                    data.insertarAsistenciaReg(asistenciaReg);
                }catch (SQLiteException e){
                    e.printStackTrace();
                }
                publishProgress(i,(int)Math.floor((i*100)/maximo));
                i++;
            }
            for (InventarioReg inventarioReg : fichasRegs) {
                try {
                    data.insertarFichaReg(inventarioReg);
                }catch (SQLiteException e){
                    e.printStackTrace();
                }
                publishProgress(i,(int)Math.floor((i*100)/maximo));
                i++;
            }
            for (InventarioReg inventarioReg : cuadernillosRegs) {
                try {
                    data.insertarCuadernilloReg(inventarioReg);
                }catch (SQLiteException e){
                    e.printStackTrace();
                }
                publishProgress(i,(int)Math.floor((i*100)/maximo));
                i++;
            }

            for (InventarioReg inventarioReg : listadosRegs) {
                try {
                    data.insertarListadoReg(inventarioReg);
                }catch (SQLiteException e){
                    e.printStackTrace();
                }
                publishProgress(i,(int)Math.floor((i*100)/maximo));
                i++;
            }
            mensaje = "LISTO, BIENVENIDO";
            data.close();
            return mensaje;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int contador = values[1];
            String texto = "";
            texto = "CARGANDO MARCO ASISTENCIA E INVENTARIO " + contador +"%";
            txtCarga.setText(texto);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            txtCarga.setText(mensaje);
            progressBar.setVisibility(View.GONE);
            Data data = new Data(ProgressActivity.this);
            data.open();
            data.guardarClave(clave);
            data.insertarHistorialUsuario(clave);
            data.close();
            Intent intent = new Intent(ProgressActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public class MyAsyncTaskSupervisor extends AsyncTask<Integer,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            String mensaje = "";
            Data data = new Data(ProgressActivity.this);
            data.open();

            ArrayList<CajaReg> cajaRegs = data.filtrarMarcoCajas(usuarioLocal.getIdlocal());
            ArrayList<AsistenciaRaReg> asistenciaRaRegs = data.filtrarMarcoAsistenciaRA(usuarioLocal.getIdlocal());

            maximo1 = cajaRegs.size();
            maximo2 = asistenciaRaRegs.size();
            maximo = maximo1 + maximo2;
            progressBar.setMax(maximo);


            int i = 1;

            for (CajaReg cajaReg : cajaRegs){
                data.insertarCajaReg(cajaReg);
                publishProgress(i,(int)Math.floor((i*100)/maximo));
                i++;
            }


            for (AsistenciaRaReg asistenciaRaReg : asistenciaRaRegs){
                data.insertarAsistenciaRaReg(asistenciaRaReg);
                publishProgress(i,(int)Math.floor((i*100)/maximo));
                i++;
            }
            mensaje = "LISTO, BIENVENIDO";
            data.close();
            return mensaje;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int contador = values[1];
            String texto = "";
            texto = "CARGANDO MARCO CAJAS Y ASISTENCIA RA " + contador +"%";
            txtCarga.setText(texto);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            txtCarga.setText(mensaje);
            progressBar.setVisibility(View.GONE);
            Data data = new Data(ProgressActivity.this);
            data.open();
            data.guardarClave(clave);
            data.insertarHistorialUsuario(clave);
            data.close();
            Intent intent = new Intent(ProgressActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
