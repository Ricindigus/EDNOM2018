package pe.com.ricindigus.appednom2018.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaReg;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.InventarioReg;

public class SplashActivity extends AppCompatActivity {

    int tiempoEspera = 3000;

    TextView txtTitulo;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtTitulo = (TextView) findViewById(R.id.txtTituloSplash);
        progressBar = (ProgressBar) findViewById(R.id.progreso_copia);
        Data data = new Data(SplashActivity.this);
        if(data.checkDataBase()){
            data.open();
            txtTitulo.setText(data.getNombreApp());
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, tiempoEspera);
        }else{
            new MyAsyncTask().execute(0);
        }
    }

    public class MyAsyncTask extends AsyncTask<Integer,Integer,String> {
        String nompreApp = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtTitulo.setText("INICIANDO APP...");
//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            String nombreApp = "";
            try {
                Data data = new Data(SplashActivity.this,1);
                data.open();
                nombreApp = data.getNombreApp();
                data.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return nombreApp;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            txtTitulo.setText(mensaje);
            progressBar.setVisibility(View.GONE);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 2000);
        }
    }
}
