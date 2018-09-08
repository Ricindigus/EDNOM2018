package pe.com.ricindigus.appednom2018.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Data;

public class SplashActivity extends AppCompatActivity {

    int tiempoEspera = 3000;
    int temaApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Data data = null;
        try {
            data = new Data(SplashActivity.this,1);
            data.open();
            temaApp = data.getTemaApp();
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (temaApp){
            case 1: setTheme(R.style.AppTheme1);setContentView(R.layout.activity_splash1);break;
            case 2: setTheme(R.style.AppTheme2);setContentView(R.layout.activity_splash2);break;
            case 3: setTheme(R.style.AppTheme3);setContentView(R.layout.activity_splash3);break;
            case 4: setTheme(R.style.AppTheme4);setContentView(R.layout.activity_splash4);break;
            case 5: setTheme(R.style.AppTheme5);setContentView(R.layout.activity_splash5);break;
        }
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra("tema",temaApp);
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, tiempoEspera);
    }
}
