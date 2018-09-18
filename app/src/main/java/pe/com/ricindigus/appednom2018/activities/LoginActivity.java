package pe.com.ricindigus.appednom2018.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import pe.com.ricindigus.appednom2018.modelo.Asistencia;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaAula;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaLocal;
import pe.com.ricindigus.appednom2018.modelo.CajaIn;
import pe.com.ricindigus.appednom2018.modelo.CajaOut;
import pe.com.ricindigus.appednom2018.modelo.UsuarioActual;
import pe.com.ricindigus.appednom2018.util.FileChooser;
import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.UsuarioLocal;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtClave;
    TextView txtAquiMarco;
    Button btnIngresar;
    String temaApp;
    UsuarioLocal usuarioLocal;
    int maximo = 0;

    TextView txtCarga;
    ProgressBar progressBar;
    String clave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtClave = (TextInputEditText) findViewById(R.id.login_edtClave);
        btnIngresar = (Button) findViewById(R.id.login_btnIngresar);
        txtAquiMarco = (TextView) findViewById(R.id.login_txtAquiMarco);
        txtCarga = (TextView) findViewById(R.id.login_mensaje_carga);
        progressBar = (ProgressBar) findViewById(R.id.login_progreso);
        TextView txtTitulo = (TextView) findViewById(R.id.login_titulo_encuesta);
        Data data = new Data(LoginActivity.this);
        data.open();
        temaApp = data.getNombreApp();
        data.close();
        txtTitulo.setText(temaApp);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clave = edtClave.getText().toString();
                ingresar(clave);
            }
        });

        txtAquiMarco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarMarco();
            }
        });
    }

    public void ingresar(String clave){
        Data data = null;
        data = new Data(LoginActivity.this);
        data.open();
        usuarioLocal = data.getUsuarioLocal(clave);
        if (usuarioLocal != null){
            if(data.existeUsuario(clave)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                switch (usuarioLocal.getRol()){
                    case 2:
                        maximo = data.getNroAsistenciasIdLocal(usuarioLocal.getNro_local());
                        progressBar.setMax(maximo*2);
                        new MyAsyncTask().execute(0);
                        break;
                    case 3:
                        filtrarMarcoCajas(usuarioLocal.getNro_local(),clave);
                        break;
                }
                data.guardarClave(clave);
                data.close();
            }
        }else {
            Toast.makeText(this, "CLAVE INCORRECTA", Toast.LENGTH_SHORT).show();
        }



    }

    public void filtrarMarcoCajas(int nroLocal, String clave){

        Data data = new Data(LoginActivity.this);
        data.open();
        if (data.getNumeroItemsCajasIn() == 0){
            ArrayList<CajaIn> cajaIns = data.getCopiaCajasInxLocal(nroLocal);
            ArrayList<CajaOut> cajaOuts = data.getCopiaCajasOutxLocal(nroLocal);

            for (CajaIn caja : cajaIns){
                data.insertarCajaIn(caja);
            }
            for (CajaOut caja : cajaOuts){
                data.insertarCajaOut(caja);
            }
            data.insertarHistorialUsuario(clave);
        }
        data.close();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void cargarMarco(){
        FileChooser fileChooser = new FileChooser(LoginActivity.this);
        fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(File file) {
                String filename = file.getAbsolutePath();
                Log.d("File", filename);
                Toast.makeText(LoginActivity.this, "Cargando..." + filename, Toast.LENGTH_SHORT).show();
                try {
                    Data data = new Data(LoginActivity.this,filename);
                    data.open();
                    data.close();
                    Intent intent = new Intent(LoginActivity.this,SplashActivity.class);
                    startActivity(intent);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        fileChooser.showDialog();
    }

    public class MyAsyncTask extends AsyncTask<Integer,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            String mensaje = "";
            int i = 1;
            Data data = new Data(LoginActivity.this);
            data.open();
            ArrayList<AsistenciaLocal> asistenciaLocals = data.filtrarMarcoAsistenciaLocal(usuarioLocal.getNro_local());
            ArrayList<AsistenciaAula> asistenciaAulas = data.filtrarMarcoAsistenciaAula(usuarioLocal.getNro_local());

            for (AsistenciaLocal asistenciaLocal : asistenciaLocals) {
                try {
                    data.insertarAsistenciaLocal(asistenciaLocal);
                }catch (SQLiteException e){
                    e.printStackTrace();
                }
                publishProgress(i,(int)Math.floor((i*100)/(maximo*2)));
                i++;
            }

            for (AsistenciaAula asistenciaAula : asistenciaAulas) {
                try {
                    data.insertarAsistenciaAula(asistenciaAula);
                }catch (SQLiteException e){
                    e.printStackTrace();
                }
                publishProgress(i,(int)Math.floor((i*100)/(maximo*2)));
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
            String texto = "CARGANDO MARCO " + contador +"%";
            txtCarga.setText(texto);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            txtCarga.setText(mensaje);
            progressBar.setVisibility(View.GONE);
            Data data = new Data(LoginActivity.this);
            data.open();
            data.insertarHistorialUsuario(clave);
            data.close();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
