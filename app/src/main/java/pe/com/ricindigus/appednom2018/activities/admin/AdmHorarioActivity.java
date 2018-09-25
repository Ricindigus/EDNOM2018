package pe.com.ricindigus.appednom2018.activities.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Version;

public class AdmHorarioActivity extends AppCompatActivity {

    Switch swRestriccion;
    NumberPicker npHoraInicio;
    NumberPicker npHoraFin;
    NumberPicker npMinutoInicio;
    NumberPicker npMinutoFin;
    Button btnActualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_horario);

        swRestriccion = (Switch) findViewById(R.id.switch_restriccion);
        npHoraInicio = (NumberPicker) findViewById(R.id.inicio_hora);
        npMinutoInicio = (NumberPicker) findViewById(R.id.inicio_minuto);
        npHoraFin = (NumberPicker) findViewById(R.id.fin_hora);
        npMinutoFin = (NumberPicker) findViewById(R.id.fin_minuto);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);

        npHoraFin.setMinValue(1);
        npHoraFin.setMaxValue(24);
        npHoraInicio.setMinValue(1);
        npHoraInicio.setMaxValue(24);

        npMinutoInicio.setMinValue(0);
        npMinutoInicio.setMaxValue(59);
        npMinutoFin.setMinValue(0);
        npMinutoFin.setMaxValue(59);

        Data data = new Data(AdmHorarioActivity.this);
        data.open();
        Version version = data.getVersion();
        npHoraInicio.setValue(version.getAsis_hora_inicio());
        npMinutoInicio.setValue(version.getAsis_min_inicio());
        npHoraFin.setValue(version.getAsis_hora_fin());
        npMinutoFin.setValue(version.getAsis_min_fin());
        data.close();

        swRestriccion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Data data = new Data(AdmHorarioActivity.this);
                    data.open();
                    data.actualizarRestriccionHoraVersion();
                    data.close();
                    npHoraInicio.setEnabled(false);
                    npHoraFin.setEnabled(false);
                    npMinutoInicio.setEnabled(false);
                    npMinutoFin.setEnabled(false);
                    btnActualizar.setEnabled(false);

                }else{
                    npHoraInicio.setEnabled(true);
                    npHoraFin.setEnabled(true);
                    npMinutoInicio.setEnabled(true);
                    npMinutoFin.setEnabled(true);
                    btnActualizar.setEnabled(true);
                }
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(horarioCorrecto(npHoraInicio.getValue(),npMinutoInicio.getValue(),npHoraFin.getValue(),npMinutoFin.getValue())){
                    Data data = new Data(AdmHorarioActivity.this);
                    data.open();
                    data.actualizarHoraAsisDocenteVersion(npHoraInicio.getValue(),npMinutoInicio.getValue()
                            ,npHoraFin.getValue(),npMinutoFin.getValue());
                    data.close();
                    finish();
                }else{
                    Toast.makeText(AdmHorarioActivity.this, "LA HORA DE FIN DEBE SER MAYOR A LA HORA INICIAL", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public boolean horarioCorrecto(int hi,int mi,int hf,int mf){
        boolean correcto = false;
        if(hi*60+mi < hf*60+mf) correcto = true;
        return correcto;
    }
}
