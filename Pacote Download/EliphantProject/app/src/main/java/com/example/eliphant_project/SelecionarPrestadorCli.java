package com.example.eliphant_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SelecionarPrestadorCli extends AppCompatActivity {

    ImageButton ImgSelPrestCliProcurar,ImgBtnSelPrestCliPedidos;
    TextView TxtSelPrestCliRecebeIdServ,TxtSelPrestCliRecebeIdCli,TxtSelPrestCliRecebeIdPrest,
            TxtSelecPrestCliNome, TxtSelecPrestCliTipoServ, TxtSelecPrestCliAval,TxtSelPrestCliRecebeServSelecionado,TxtSelPrestCliRecebeCPFCli;
    ImageView ImgViewPrest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_prestador_cli);

        TxtSelPrestCliRecebeCPFCli = findViewById(R.id.TxtSelPrestCliRecebeCPFCli);
        TxtSelPrestCliRecebeIdServ = findViewById(R.id.TxtSelPrestCliRecebeIdServ);
        ImgBtnSelPrestCliPedidos = findViewById(R.id.ImgBtnSelPrestCliPedidos);
        TxtSelPrestCliRecebeIdPrest = findViewById(R.id.TxtSelPrestCliRecebeIdPrest);
        ImgSelPrestCliProcurar = findViewById(R.id. ImgSelPrestCliProcurar);
        TxtSelecPrestCliNome = findViewById(R.id.TxtSelecPrestCliNome);
        TxtSelecPrestCliTipoServ = findViewById(R.id.TxtSelecPrestCliTipoServ);
        TxtSelecPrestCliAval = findViewById(R.id.TxtSelecPrestCliAval);
        ImgViewPrest = findViewById(R.id.ImgViewPrest);
        TxtSelPrestCliRecebeServSelecionado = findViewById(R.id.TxtSelPrestCliRecebeServSelecionado);
        TxtSelPrestCliRecebeIdCli = findViewById(R.id.TxtSelPrestCliRecebeIdCli);


        String RecebeCPFCliente = getIntent().getStringExtra("DadosCPFCliente");
        TxtSelPrestCliRecebeCPFCli.setText(RecebeCPFCliente);

        String RecebeServicoSelCli = getIntent().getStringExtra("ServicoSelecionado");
        TxtSelPrestCliRecebeServSelecionado.setText(RecebeServicoSelCli);

        String RecebeIdCli = getIntent().getStringExtra("IdCliente");
        TxtSelPrestCliRecebeIdCli.setText(RecebeIdCli);


        ImgSelPrestCliProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TxtSelecPrestCliNome.setVisibility(View.VISIBLE);
                TxtSelecPrestCliTipoServ.setVisibility(View.VISIBLE);
                TxtSelecPrestCliAval.setVisibility(View.VISIBLE);
                ImgViewPrest.setVisibility(View.VISIBLE);

                    Connection connection = connectionclass();
                    try {
                        if (connection != null) {
                            String sqlselect = "Select SERVICO.CodServ,PRESTADOR.CodPres,PRESTADOR.NomePres,SERVICO.TipoServ,PRESTADOR.Valor_Ava   " +
                                    "from PRESTADOR   " +
                                    "Inner join SERVICO  " +
                                    " On PRESTADOR.CodPres = SERVICO.CodPres where SERVICO.TipoServ = '" + TxtSelPrestCliRecebeServSelecionado.getText().toString() +"'";
                            Statement st = connection.createStatement();
                            ResultSet rs = st.executeQuery(sqlselect);

                            while (rs.next()){
                                TxtSelPrestCliRecebeIdServ.setText(rs.getString(1));
                                TxtSelPrestCliRecebeIdPrest.setText(rs.getString(2));
                                TxtSelecPrestCliNome.setText(rs.getString(3));
                                TxtSelecPrestCliTipoServ.setText(rs.getString(4));
                                TxtSelecPrestCliAval.setText(rs.getString(5));
                            }
                        }
                    } catch (Exception exception) {
                        Log.e("Error", exception.getMessage());
                    }


            }
        });

        TxtSelecPrestCliNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlinsert = "Update REALIZAR_SERVICO "+
                       " set IdServ = " +TxtSelPrestCliRecebeIdServ.getText().toString()+ ", IdPres = "+ TxtSelPrestCliRecebeIdPrest.getText().toString() +
                        " where IdCli = "+TxtSelPrestCliRecebeIdCli.getText().toString();
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);

                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                Toast.makeText(getApplicationContext(),"Profissional Selecionado",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SelecionarPrestadorCli.this, MeusPedidosCli.class);
                intent.putExtra("IdCliente",TxtSelPrestCliRecebeIdCli.getText().toString());
                intent.putExtra("DadosCPFCliente",TxtSelPrestCliRecebeCPFCli.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        ImgBtnSelPrestCliPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


    }



    @SuppressLint("NewApi")
    public Connection connectionclass() {
        Connection con = null;
        String ip = "192.168.100.172", port = "1433", username = "sa", password = "12345", databasename = "ELIPHANT_PLATAFORMA";
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