package pe.com.ricindigus.appednom2018.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Data;

public class SplashActivity extends AppCompatActivity {

    int tiempoEspera = 3000;
    String nombreApp;
    TextView txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtTitulo = (TextView) findViewById(R.id.txtTituloSplash);

        Data data = null;
        try {
            data = new Data(SplashActivity.this,1);
            data.open();
            nombreApp = data.getNombreApp();
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtTitulo.setText(nombreApp);

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
    }
}
