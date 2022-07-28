package com.example.eliphant_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MeusServicosPrest extends AppCompatActivity {

    TextView TextViewRecebeCPFPrest,TxtMeusServPrestRecebeIDPrest,txp1,txp2,txp3,txp4,txp5,txp6;


    EditText EdTxtMeusServPrestIdPedido,EdTxtMeusServPrestDataEve,EdTxtMeusServPrestEndEve,
            EdTxtMeusServPrestNConv,EdTxtMeusServPrestInfAdd,EdTxtMeusServPrestNomeCli,EdTxtMeusServPrestContCli;

    ImageButton ImgBtnDadosUserPrest,ImgBtnMeusServPrestAtualizar,ImgBtnMeusServPrestDown,ImgBtnMeusServPrestWhatsapp;

    Button BtnMeusServPrestRejeitar,BtnMeusServPrestAceitar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_servicos_prest);

        txp1 = findViewById(R.id.txp1);
        txp2 = findViewById(R.id.txp2);
        txp3= findViewById(R.id.txp3);
        txp4 = findViewById(R.id.txp4);
        txp5 = findViewById(R.id.txp5);
        txp6 = findViewById(R.id.txp6);

        TxtMeusServPrestRecebeIDPrest = findViewById(R.id.TxtMeusServPrestRecebeIDPrest);
        TextViewRecebeCPFPrest = findViewById(R.id.TextViewRecebeCPFPrest);

        EdTxtMeusServPrestContCli = findViewById(R.id.EdTxtMeusServPrestContCli);
        EdTxtMeusServPrestIdPedido = findViewById(R.id.EdTxtMeusServPrestIdPedido);
        EdTxtMeusServPrestDataEve = findViewById(R.id.EdTxtMeusServPrestDataEve);
        EdTxtMeusServPrestEndEve = findViewById(R.id.EdTxtMeusServPrestEndEve);
        EdTxtMeusServPrestNConv = findViewById(R.id.EdTxtMeusServPrestNConv);
        EdTxtMeusServPrestInfAdd = findViewById(R.id.EdTxtMeusServPrestInfAdd);
        EdTxtMeusServPrestNomeCli = findViewById(R.id.EdTxtMeusServPrestNomeCli);

        BtnMeusServPrestRejeitar = findViewById(R.id.BtnMeusServPrestRejeitar);
        BtnMeusServPrestAceitar = findViewById(R.id.BtnMeusServPrestAceitar);
        ImgBtnMeusServPrestDown = findViewById(R.id.ImgBtnMeusServPrestDown);
        ImgBtnMeusServPrestAtualizar = findViewById(R.id.ImgBtnMeusServPrestAtualizar);
        ImgBtnDadosUserPrest = findViewById(R.id.ImgBtnDadosUserPrest);
        ImgBtnMeusServPrestWhatsapp = findViewById(R.id.ImgBtnMeusServPrestWhatsapp);


        String RecebeCPFPrestador = getIntent().getStringExtra("CPFPrestador");
        TextViewRecebeCPFPrest.setText(RecebeCPFPrestador);

        ImgBtnDadosUserPrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeusServicosPrest.this, DadosUserPrestador.class);
                intent.putExtra("EnviarCPFPrestador",TextViewRecebeCPFPrest.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        ImgBtnMeusServPrestAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlselect = "Select * from PRESTADOR where  CPFpres = '" + TextViewRecebeCPFPrest.getText().toString() + "'";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlselect);

                        while (rs.next()){
                            TxtMeusServPrestRecebeIDPrest.setText(rs.getString(1));
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
                                " REALIZAR_SERVICO.Situacao,CLIENTE.NomeCli,CLIENTE.FoneCli "+
                                " from REALIZAR_SERVICO "+
                                " Inner join CLIENTE "+
                                " on REALIZAR_SERVICO.IdCli = CLIENTE.CodCli where IdPres = "+ TxtMeusServPrestRecebeIDPrest.getText().toString();

                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlselect);

                        while (rs.next()){
                            EdTxtMeusServPrestIdPedido.setText(rs.getString(1));
                            EdTxtMeusServPrestDataEve.setText(rs.getString(5));
                            EdTxtMeusServPrestEndEve.setText(rs.getString(6));
                            EdTxtMeusServPrestNConv.setText(rs.getString(7));
                            EdTxtMeusServPrestInfAdd.setText(rs.getString(8));
                            EdTxtMeusServPrestNomeCli.setText(rs.getString(11));
                            EdTxtMeusServPrestContCli.setText(rs.getString(12));

                        }
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }


            }
        });

        ImgBtnMeusServPrestDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EdTxtMeusServPrestDataEve.setVisibility(View.VISIBLE);
                EdTxtMeusServPrestEndEve.setVisibility(View.VISIBLE);
                EdTxtMeusServPrestNConv.setVisibility(View.VISIBLE);
                EdTxtMeusServPrestInfAdd.setVisibility(View.VISIBLE);
                EdTxtMeusServPrestNomeCli.setVisibility(View.VISIBLE);
                txp1.setVisibility(View.VISIBLE);
                txp2.setVisibility(View.VISIBLE);
                txp3.setVisibility(View.VISIBLE);
                txp4.setVisibility(View.VISIBLE);
                txp5.setVisibility(View.VISIBLE);
                BtnMeusServPrestAceitar.setVisibility(View.VISIBLE);
                BtnMeusServPrestRejeitar.setVisibility(View.VISIBLE);

            }
        });

        BtnMeusServPrestAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EdTxtMeusServPrestContCli.setVisibility(View.VISIBLE);
                txp6.setVisibility(View.VISIBLE);
                ImgBtnMeusServPrestWhatsapp.setVisibility(View.VISIBLE);
                BtnMeusServPrestRejeitar.setVisibility(View.INVISIBLE);

                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlinsert = "UPDATE REALIZAR_SERVICO "+
                        " SET Situacao = 'Em processo', DataI_Serv = getdate() "+
                       " WHERE CodReali_Serv = "+ EdTxtMeusServPrestIdPedido.getText().toString();

                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                Toast.makeText(getApplicationContext(), "Pedido Número: "+EdTxtMeusServPrestIdPedido.getText().toString()+" aceito",Toast.LENGTH_LONG).show();
                BtnMeusServPrestAceitar.setVisibility(View.INVISIBLE);

            }
        });

        ImgBtnMeusServPrestWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.whatsapp.com/")));
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