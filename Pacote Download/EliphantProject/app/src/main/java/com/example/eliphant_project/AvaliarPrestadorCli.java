package com.example.eliphant_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AvaliarPrestadorCli extends AppCompatActivity {

    TextView TxtAvalPrestCliRecebeIdPedido,TxtAvaliarPrestCliNomePrest,TxtAvaliarPrestCliServPrest,TxtAvaliarPrestCliNumStars,TxtAvalPrestCliRecebeCPFCli;

    RatingBar RatingBarAvalPrestCli;

    ImageButton ImgBtnAvalPrestCliConfAval;

    Button BtnAvaliarPrestCliRecDen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliar_prestador_cli);

        BtnAvaliarPrestCliRecDen = findViewById(R.id. BtnAvaliarPrestCliRecDen);

        ImgBtnAvalPrestCliConfAval = findViewById(R.id. ImgBtnAvalPrestCliConfAval );

        RatingBarAvalPrestCli = findViewById(R.id.RatingBarAvalPrestCli);

        TxtAvalPrestCliRecebeCPFCli = findViewById(R.id.TxtAvalPrestCliRecebeCPFCli);
        TxtAvaliarPrestCliNumStars = findViewById(R.id.TxtAvaliarPrestCliNumStars);
        TxtAvaliarPrestCliServPrest = findViewById(R.id.TxtAvaliarPrestCliServPrest);
        TxtAvaliarPrestCliNomePrest = findViewById(R.id.TxtAvaliarPrestCliNomePrest);
        TxtAvalPrestCliRecebeIdPedido = findViewById(R.id.TxtAvalPrestCliRecebeIdPedido);

        String RecebeIdPed = getIntent().getStringExtra("IdPedido");
        TxtAvalPrestCliRecebeIdPedido.setText(RecebeIdPed);

        String RecebeCPFCli = getIntent().getStringExtra("DadosCPFCliente");
        TxtAvalPrestCliRecebeCPFCli.setText(RecebeCPFCli);

        Connection connection = connectionclass();
        try {
            if (connection != null) {
                String sqlselect = "Select REALIZAR_SERVICO.CodReali_Serv,REALIZAR_SERVICO.IdServ,REALIZAR_SERVICO.IdCli, "+
                        " REALIZAR_SERVICO.IdPres,REALIZAR_SERVICO.Valor_Ava,PRESTADOR.NomePres,SERVICO.TipoServ "+
                " from REALIZAR_SERVICO "+
                " Inner join PRESTADOR "+
                " on REALIZAR_SERVICO.IdPres = PRESTADOR.CodPres "+
                " Inner join SERVICO "+
                " On REALIZAR_SERVICO.IdServ = SERVICO.CodServ where CodReali_Serv = "+TxtAvalPrestCliRecebeIdPedido.getText().toString();

                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sqlselect);

                while (rs.next()){
                    TxtAvaliarPrestCliNomePrest.setText(rs.getString(6));
                    TxtAvaliarPrestCliServPrest.setText(rs.getString(7));

                }
            }
        } catch (Exception exception) {
            Log.e("Error", exception.getMessage());
        }


        RatingBarAvalPrestCli.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                TxtAvaliarPrestCliNumStars.setText(""+v);
            }
        });

        ImgBtnAvalPrestCliConfAval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlinsert = "UPDATE REALIZAR_SERVICO "+
                        " SET Valor_Ava = "+TxtAvaliarPrestCliNumStars.getText().toString()+
                        " WHERE CodReali_Serv = "+TxtAvalPrestCliRecebeIdPedido.getText().toString();

                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);

                    }

                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                Toast.makeText(getApplicationContext(), "Prestador "+TxtAvaliarPrestCliNomePrest.getText().toString()+
                        " Avaliado com nota: "+TxtAvaliarPrestCliNumStars.getText().toString(),Toast.LENGTH_LONG).show();

               /* Intent intent = new Intent(AvaliarPrestadorCli.this,DadosUserCliente.class);
                intent.putExtra("DadosCPFCliente",TxtAvalPrestCliRecebeCPFCli.getText().toString());
                startActivity(intent);
                finish();*/

            }
        });





    }



    @SuppressLint("NewApi")
    public Connection connectionclass() {
        Connection con = null;
        String ip = "192.168.254.62", port = "1433", username = "sa", password = "12345", databasename = "ELIPHANT_PLATAFORMA";
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + databasename + ";User=" + username + ";password=" + password + ";";
            con = DriverManager.getConnection(connectionUrl);
        } catch (Exception exception) {
            Log.e("Error", exception.getMessage());
        }
        return con;
    }

}