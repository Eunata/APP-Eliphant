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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DadosUserCliente extends AppCompatActivity {

    ImageButton ImgBtnDadosAltCli,ImgBtnDadosCliVisivel,ImgBtnDadosUserCliLupa,ImgBtnDadosUserCliMeusPedidos,ImgBtnDadosCliExit;
    EditText EditTextDadosCliNome,EditTextDadosCliEmail
            ,EditTextDadosCliEndereco,EditTextDadosCliDataNasc,EditTextDadosCliTel;
    TextView TextViewRecebeCPFCli;
    Button BtnAltDadosCli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_user_cliente);

        ImgBtnDadosCliExit = findViewById(R.id.ImgBtnDadosCliExit);
        ImgBtnDadosUserCliMeusPedidos = findViewById(R.id.ImgBtnDadosUserCliMeusPedidos);
        ImgBtnDadosUserCliLupa = findViewById(R.id.ImgBtnDadosUserCliLupa);
        ImgBtnDadosAltCli = findViewById(R.id.ImgBtnDadosAltCli);
        EditTextDadosCliNome = findViewById(R.id.EditTextDadosCliNome);
        EditTextDadosCliEmail = findViewById(R.id.EditTextDadosCliEmail);
        EditTextDadosCliEndereco = findViewById(R.id.EditTextDadosCliEndereco);
        EditTextDadosCliDataNasc = findViewById(R.id.EditTextDadosCliDataNasc);
        EditTextDadosCliTel = findViewById(R.id.EditTextDadosCliTel);
        TextViewRecebeCPFCli = findViewById(R.id.TextViewRecebeCPFCli);
        BtnAltDadosCli = findViewById(R.id.BtnAltDadosCli);
        ImgBtnDadosCliVisivel = findViewById(R.id.ImgBtnDadosCliVisivel);


        String RecebeCPFCliente = getIntent().getStringExtra("DadosCPFCliente");
        TextViewRecebeCPFCli.setText(RecebeCPFCliente);

        ImgBtnDadosCliExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DadosUserCliente.this,TelaDeInicio.class);
                startActivity(intent);
                finish();
            }
        });


        ImgBtnDadosCliVisivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlselect = "Select * from CLIENTE where  CPFcli = '" + RecebeCPFCliente + "'";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlselect);

                        while (rs.next()){
                            EditTextDadosCliNome.setText(rs.getString(2));
                            EditTextDadosCliEmail.setText(rs.getString(4));
                            EditTextDadosCliEndereco.setText(rs.getString(5));
                            EditTextDadosCliDataNasc.setText(rs.getString(6));
                            EditTextDadosCliTel.setText(rs.getString(7));
                        }
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }


                EditTextDadosCliNome.setVisibility(View.VISIBLE);
                EditTextDadosCliEmail.setVisibility(View.VISIBLE);
                EditTextDadosCliEndereco.setVisibility(View.VISIBLE);
                EditTextDadosCliDataNasc.setVisibility(View.VISIBLE);
                EditTextDadosCliTel.setVisibility(View.VISIBLE);
            }
        });

        ImgBtnDadosAltCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextDadosCliNome.setEnabled(true);
                EditTextDadosCliEmail.setEnabled(true);
                EditTextDadosCliEndereco.setEnabled(true);
                EditTextDadosCliDataNasc.setEnabled(true);
                EditTextDadosCliTel.setEnabled(true);
                BtnAltDadosCli.setVisibility(View.VISIBLE);
            }
        });


        BtnAltDadosCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlinsert = "Update CLIENTE set NomeCli = '" + EditTextDadosCliNome.getText().toString() + "',EmailCli = '" + EditTextDadosCliEmail.getText().toString() +
                                "', EnderecoCli = '" + EditTextDadosCliEndereco.getText().toString() + "', DataNasCli = '" + EditTextDadosCliDataNasc.getText().toString() + "', FoneCli = '" + EditTextDadosCliTel.getText().toString() + "' where CPFcli = '" + TextViewRecebeCPFCli.getText().toString() + "'";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                EditTextDadosCliNome.setEnabled(false);
                EditTextDadosCliEmail.setEnabled(false);
                EditTextDadosCliEndereco.setEnabled(false);
                EditTextDadosCliDataNasc.setEnabled(false);
                EditTextDadosCliTel.setEnabled(false);
                BtnAltDadosCli.setVisibility(View.INVISIBLE);
            }
        });

        ImgBtnDadosUserCliLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DadosUserCliente.this,SelecionarServicoCli.class);
                intent.putExtra("CPFCliente",TextViewRecebeCPFCli.getText().toString());
                startActivity(intent);

            }
        });

        ImgBtnDadosUserCliMeusPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DadosUserCliente.this,MeusPedidosCli.class);
                intent.putExtra("DadosCPFCliente",TextViewRecebeCPFCli.getText().toString());
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