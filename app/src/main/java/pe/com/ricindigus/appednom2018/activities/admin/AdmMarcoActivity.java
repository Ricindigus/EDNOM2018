package pe.com.ricindigus.appednom2018.activities.admin;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Data;

public class AdmMarcoActivity extends AppCompatActivity {
    Button btnCargarMarco;
    ProgressBar progressBar;
    TextView txtMensaje;
    String filename = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_marco);
        btnCargarMarco = (Button) findViewById(R.id.btnCargar);
        progressBar = (ProgressBar) findViewById(R.id.progreso_admin);
        txtMensaje = (TextView) findViewById(R.id.mensaje_admin);
        filename = getIntent().getExtras().getString("filename");
        new MyAsyncTask().execute();

    }

    public class MyAsyncTask extends AsyncTask<Integer,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            txtMensaje.setText("CARGANDO MARCO...");
        }

        @Override
        protected String doInBackground(Integer... integers) {
            try {
                Data data = new Data(AdmMarcoActivity.this,filename);
                data.open();
                data.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "LISTO";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            txtMensaje.setText(mensaje);
            progressBar.setVisibility(View.GONE);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 1000);
        }
    }
}
