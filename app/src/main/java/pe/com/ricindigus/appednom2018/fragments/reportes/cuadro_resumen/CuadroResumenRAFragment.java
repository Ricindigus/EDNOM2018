package pe.com.ricindigus.appednom2018.fragments.reportes.cuadro_resumen;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class CuadroResumenRAFragment extends Fragment {


    Context context;
    int nrolocal;
    TextView txtAsistenteAdministrativo, txtCoordinadorSede, txtCoordinadorLiderLocal, txtMonitorNacional;
    TextView txtSupervisorInformatico, txtSupervisorNacional, txtInformaticoLocal;
    TextView txtCoordinadorLocal, txtAsistenteCoordinadorLocal, txtAplicador, txtOrientador, txtOperadorInformatico;
    TextView txtTotal;

    public CuadroResumenRAFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public CuadroResumenRAFragment(Context context, int nrolocal){
        this.context=context;
        this.nrolocal=nrolocal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cuadro_resumen_ra, container, false);
        txtAsistenteAdministrativo=(TextView) rootView.findViewById(R.id.resumen_ra_txtAsistenteAdministrativo);
        txtCoordinadorSede= (TextView) rootView.findViewById(R.id.resumen_ra_txtCoordinadorSede);
        txtCoordinadorLiderLocal= (TextView) rootView.findViewById(R.id.resumen_ra_txtCoordinadorLiderLocal);
        txtMonitorNacional = (TextView) rootView.findViewById(R.id.resumen_ra_txtMonitorNacional);
        txtSupervisorInformatico = (TextView) rootView.findViewById(R.id.resumen_ra_txtSupervisorInformatico);
        txtSupervisorNacional = (TextView) rootView.findViewById(R.id.resumen_ra_txtSupervisorNacional);
        txtInformaticoLocal = (TextView) rootView.findViewById(R.id.resumen_ra_txtInformaticoLocal);
        txtCoordinadorLocal = (TextView) rootView.findViewById(R.id.resumen_ra_txtCoordinadorLocal);
        txtAsistenteCoordinadorLocal = (TextView) rootView.findViewById(R.id.resumen_ra_txtAsistenteCoordinadorLocal);
        txtAplicador = (TextView) rootView.findViewById(R.id.resumen_ra_txtAplicador);
        txtOrientador = (TextView) rootView.findViewById(R.id.resumen_ra_txtOrientador);
        txtOperadorInformatico = (TextView) rootView.findViewById(R.id.resumen_ra_txtOperadorInformatico);
        txtTotal = (TextView) rootView.findViewById(R.id.resumen_RA_txtTotal);
        return rootView;


    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);
        Data data = new Data(context);
        data.open();
        int asisAdmi= data.getNroPersonalRA_RegistradasxCargo(nrolocal,101);
        int coordiSede= data.getNroPersonalRA_RegistradasxCargo(nrolocal,102);
        int coordiLiderLocal= data.getNroPersonalRA_RegistradasxCargo(nrolocal,104);
        int monitorN= data.getNroPersonalRA_RegistradasxCargo(nrolocal,605);
        int supervisorInfo= data.getNroPersonalRA_RegistradasxCargo(nrolocal,105);
        int supervisorNac= data.getNroPersonalRA_RegistradasxCargo(nrolocal,100);
        int informaticoLocal= data.getNroPersonalRA_RegistradasxCargo(nrolocal,202);
        int coordiLocal= data.getNroPersonalRA_RegistradasxCargo(nrolocal,201);
        int asisCoordiLocal= data.getNroPersonalRA_RegistradasxCargo(nrolocal,200);
        int aplicador= data.getNroPersonalRA_RegistradasxCargo(nrolocal,300);
        int orientador= data.getNroPersonalRA_RegistradasxCargo(nrolocal,302);
        int operador= data.getNroPersonalRA_RegistradasxCargo(nrolocal,301);

        int tAsisAdmi = data.getNroPersonalRATotalesxCargo(nrolocal,101);
        int tCoordiSede = data.getNroPersonalRATotalesxCargo(nrolocal,103);
        int tCoordiLiderLocal= data.getNroPersonalRATotalesxCargo(nrolocal,104);
        int tMonitorN = data.getNroPersonalRATotalesxCargo(nrolocal,605);
        int tSupervisorInfo = data.getNroPersonalRATotalesxCargo(nrolocal,105);
        int tSupervisorNac = data.getNroPersonalRATotalesxCargo(nrolocal,100);
        int tInformaticoLocal = data.getNroPersonalRATotalesxCargo(nrolocal,202);
        int tCoordiLocal= data.getNroPersonalRATotalesxCargo(nrolocal,201);
        int tAsisCoordiLocal = data.getNroPersonalRATotalesxCargo(nrolocal,200);
        int tAplicador = data.getNroPersonalRATotalesxCargo(nrolocal,300);
        int tOrientador = data.getNroPersonalRATotalesxCargo(nrolocal,302);
        int tOperador = data.getNroPersonalRATotalesxCargo(nrolocal,301);

        txtAsistenteAdministrativo.setText(asisAdmi + "/" +tAsisAdmi);
        txtCoordinadorSede.setText(coordiSede +"/"+ tCoordiSede);
        txtCoordinadorLiderLocal.setText(coordiLiderLocal + "/" + tCoordiLiderLocal);
        txtMonitorNacional.setText(monitorN+"/"+tMonitorN);
        txtSupervisorInformatico.setText(supervisorInfo+"/"+tSupervisorInfo);
        txtSupervisorNacional.setText(supervisorNac+"/"+tSupervisorNac);
        txtInformaticoLocal.setText(informaticoLocal+"/"+tInformaticoLocal);
        txtCoordinadorLocal.setText(coordiLocal+"/"+tCoordiLocal);
        txtAsistenteCoordinadorLocal.setText(asisCoordiLocal+"/"+tAsisCoordiLocal);
        txtAplicador.setText(aplicador+"/"+tAplicador);
        txtOrientador.setText(orientador+"/"+tOrientador);
        txtOperadorInformatico.setText(operador+"/"+tOperador);
        txtTotal.setText((asisAdmi+coordiSede+coordiLiderLocal+monitorN+supervisorInfo+supervisorNac+informaticoLocal+coordiLocal+asisCoordiLocal+aplicador+orientador+operador)+"/"+
                (tAsisAdmi+tCoordiSede+tCoordiLiderLocal+tMonitorN+tSupervisorInfo+tSupervisorNac+tInformaticoLocal+tCoordiLocal+tAsisCoordiLocal+tAplicador+tOrientador+tOperador));
        data.close();

    }


}
