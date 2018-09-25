package pe.com.ricindigus.appednom2018.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.activities.admin.AdmHorarioActivity;
import pe.com.ricindigus.appednom2018.activities.admin.AdmMarcoActivity;
import pe.com.ricindigus.appednom2018.util.FileChooser;

public class AdminActivity extends AppCompatActivity {

    Button btnCargarMarco;
    Button btnHorarioAsistencia;
    Button btnSalir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnCargarMarco = (Button) findViewById(R.id.btnCargarMarco);
        btnHorarioAsistencia = (Button) findViewById(R.id.btnHorarioAsistencia);
        btnSalir = (Button) findViewById(R.id.btnSalir);

        btnCargarMarco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser fileChooser = new FileChooser(AdminActivity.this);
                fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
                    @Override
                    public void fileSelected(File file) {
                        String filename = file.getAbsolutePath();
                        if(filename.substring(filename.length()-7,filename.length()).toLowerCase().equals(".sqlite")){
                            Intent intent = new Intent(AdminActivity.this, AdmMarcoActivity.class);
                            intent.putExtra("filename",filename);
                            startActivity(intent);
                        }else{
                            Toast.makeText(AdminActivity.this, "archivo de tipo incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                fileChooser.showDialog();

            }
        });

        btnHorarioAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdmHorarioActivity.class);
                startActivity(intent);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
