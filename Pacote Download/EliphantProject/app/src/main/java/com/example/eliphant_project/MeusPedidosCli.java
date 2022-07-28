package com.example.eliphant_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MeusPedidosCli extends AppCompatActivity {

    TextView TxtMeusPedCliRecebeIdCli,tx1,tx2,tx3,tx4,tx5,tx6,TxtMeusPedCliRecebeCPFCli;

    ImageButton ImgBtnMeusPedCliDadosUser,ImgBtnMeusPedCliSelecServ,ImgBtnMeusPedCliAtualizar,ImgBtnMeusPedCliDown,ImgBtnMeusPedCliMostrarFinalizar;

    EditText EdTxtMeusPedCliIdPedido,EdTxtMeusPedCliDataEve,EdTxtMeusPedCliEndEve,
            EdTxtMeusPedCliNConvEve,EdTxtMeusPedCliInfAddEve,EdTxtMeusPedCliSitPed,EdTxtMeusPedCliNomePrest;

    Button BtnMeusPedCliFinalizar;

    String Situaçao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_pedidos_cli);

        tx1 = findViewById(R.id.tx1);
        tx2 = findViewById(R.id.tx2);
        tx3 = findViewById(R.id.tx3);
        tx4 = findViewById(R.id.tx4);
        tx5 = findViewById(R.id.tx5);
        tx6 = findViewById(R.id.tx6);

        EdTxtMeusPedCliNomePrest = findViewById(R.id.EdTxtMeusPedCliNomePrest);
        EdTxtMeusPedCliIdPedido = findViewById(R.id.EdTxtMeusPedCliIdPedido);
        EdTxtMeusPedCliDataEve = findViewById(R.id.EdTxtMeusPedCliDataEve);
        EdTxtMeusPedCliEndEve = findViewById(R.id.EdTxtMeusPedCliEndEve);
        EdTxtMeusPedCliEndEve = findViewById(R.id.EdTxtMeusPedCliEndEve);
        EdTxtMeusPedCliNConvEve = findViewById(R.id.EdTxtMeusPedCliNConvEve);
        EdTxtMeusPedCliInfAddEve = findViewById(R.id.EdTxtMeusPedCliInfAddEve);
        EdTxtMeusPedCliSitPed = findViewById(R.id.EdTxtMeusPedCliSitPed);

        ImgBtnMeusPedCliMostrarFinalizar = findViewById(R.id.ImgBtnMeusPedCliMostrarFinalizar);
        BtnMeusPedCliFinalizar = findViewById(R.id.BtnMeusPedCliFinalizar);
        TxtMeusPedCliRecebeCPFCli = findViewById(R.id.TxtMeusPedCliRecebeCPFCli);
        ImgBtnMeusPedCliDown = findViewById(R.id.ImgBtnMeusPedCliDown);
        ImgBtnMeusPedCliAtualizar = findViewById(R.id.ImgBtnMeusPedCliAtualizar);
        ImgBtnMeusPedCliSelecServ = findViewById(R.id.ImgBtnMeusPedCliSelecServ);
        ImgBtnMeusPedCliDadosUser = findViewById(R.id.ImgBtnMeusPedCliDadosUser);
        TxtMeusPedCliRecebeIdCli = findViewById(R.id.TxtMeusPedCliRecebeIdCli);

        String RecebeIdCli = getIntent().getStringExtra("IdCliente");
        TxtMeusPedCliRecebeIdCli.setText(RecebeIdCli);

        String RecebeCPFCli = getIntent().getStringExtra("DadosCPFCliente");
        TxtMeusPedCliRecebeCPFCli.setText(RecebeCPFCli);

        ImgBtnMeusPedCliDadosUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeusPedidosCli.this,DadosUserCliente.class);
                intent.putExtra("DadosCPFCliente",TxtMeusPedCliRecebeCPFCli.getText().toString());
                startActivity(intent);

            }
        });

        ImgBtnMeusPedCliSelecServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeusPedidosCli.this,SelecionarServicoCli.class);
                intent.putExtra("CPFCliente",TxtMeusPedCliRecebeCPFCli.getText().toString());
                startActivity(intent);

            }
        });



        ImgBtnMeusPedCliAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlselect = "Select * from CLIENTE where  CPFcli = '" + TxtMeusPedCliRecebeCPFCli.getText().toString() + "'";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlselect);

                        while (rs.next()){
                           TxtMeusPedCliRecebeIdCli.setText(rs.getString(1));
                        }
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }


                try {
                    if (connection != null) {
                        String sqlselect = "Select REALIZAR_SERVICO.CodReali_Serv,REALIZAR_SERVICO.IdServ,REALIZAR_SERVICO.IdCli, "+
                                " REALIZAR_SERVICO.IdPres,REALIZAR_SERVICO.Data_evento,REALIZAR_SERVICO.Endereco_evento, "+
                                " REALIZAR_SERVICO.Num_convidados,REALIZAR_SERVICO.Inf_adicionais,REALIZAR_SERVICO.Valor_Ava, "+
                                " REALIZAR_SERVICO.Situacao,PRESTADOR.NomePres "+
                                " from REALIZAR_SERVICO "+
                                " Inner join PRESTADOR "+
                                " on REALIZAR_SERVICO.IdPres = PRESTADOR.CodPres where IdCli = "+TxtMeusPedCliRecebeIdCli.getText().toString();

                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlselect);

                        while (rs.next()){
                            EdTxtMeusPedCliIdPedido.setText(rs.getString(1));
                            EdTxtMeusPedCliDataEve.setText(rs.getString(5));
                            EdTxtMeusPedCliEndEve.setText(rs.getString(6));
                            EdTxtMeusPedCliNConvEve.setText(rs.getString(7));
                            EdTxtMeusPedCliInfAddEve.setText(rs.getString(8));
                            EdTxtMeusPedCliNomePrest.setText(rs.getString(11));
                            EdTxtMeusPedCliSitPed.setText(rs.getString(10));

                        }
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }



            }
        });

        ImgBtnMeusPedCliDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(EdTxtMeusPedCliIdPedido.getText().toString() != "")
                {
                    EdTxtMeusPedCliDataEve.setVisibility(View.VISIBLE);
                    EdTxtMeusPedCliEndEve.setVisibility(View.VISIBLE);
                    EdTxtMeusPedCliNConvEve.setVisibility(View.VISIBLE);
                    EdTxtMeusPedCliInfAddEve.setVisibility(View.VISIBLE);
                    EdTxtMeusPedCliNomePrest.setVisibility(View.VISIBLE);
                    EdTxtMeusPedCliSitPed.setVisibility(View.VISIBLE);
                    tx1.setVisibility(View.VISIBLE);
                    tx2.setVisibility(View.VISIBLE);
                    tx3.setVisibility(View.VISIBLE);
                    tx4.setVisibility(View.VISIBLE);
                    tx5.setVisibility(View.VISIBLE);
                    tx6.setVisibility(View.VISIBLE);
                    ImgBtnMeusPedCliMostrarFinalizar.setVisibility(View.VISIBLE);

                }


                    EdTxtMeusPedCliSitPed.setTextColor(getResources().getColor(R.color.RosaEliphant));;


            }
        });

        ImgBtnMeusPedCliMostrarFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtnMeusPedCliFinalizar.setVisibility(View.VISIBLE);
            }
        });

        BtnMeusPedCliFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlinsert = "UPDATE REALIZAR_SERVICO "+
                                " SET Situacao = 'Finalizado', DataF_Serv = getdate() "+
                                " WHERE CodReali_Serv = "+ EdTxtMeusPedCliIdPedido.getText().toString();

                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                BtnMeusPedCliFinalizar.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(), "Pedido Número: "+EdTxtMeusPedCliIdPedido.getText().toString()+" Finalizado",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MeusPedidosCli.this,AvaliarPrestadorCli.class);
                intent.putExtra("IdPedido",EdTxtMeusPedCliIdPedido.getText().toString());
                intent.putExtra("DadosCPFCliente",TxtMeusPedCliRecebeCPFCli.getText().toString());
                startActivity(intent);

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