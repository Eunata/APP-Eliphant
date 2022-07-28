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
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText EditTextCadCliData = (EditText)findViewById(R.id.EditTextCadCliData);
        TextView EditTextCadCliSenha = (TextView)findViewById(R.id.EditTextCadCliSenha);
        TextView EditTextCadCliTel = (TextView)findViewById(R.id.EditTextCadCliTel);
        TextView EditTextCadCliEndereco = (TextView)findViewById(R.id.EditTextCadCliEndereco);
        TextView EditTextCadCliEmail = (TextView)findViewById(R.id.EditTextCadCliEmail);
        TextView EditTextCadCliCPF = (TextView)findViewById(R.id.EditTextCadCliCPF);
        TextView EditTextSeuNome = (TextView)findViewById(R.id.EditTextSeuNome);
        Button BtnIserir = (Button)findViewById(R.id.BtnIserir);
        Button BtnCadCliVoltar = (Button)findViewById(R.id.BtnCadCliVoltar);

        BtnIserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlinsert = "Insert into CLIENTE(NomeCli,CPFcli,EmailCli,EnderecoCli,DataNasCli,FoneCli,SenhaCli,SituacaoCli) values ('" + EditTextSeuNome.getText().toString() +"','" + EditTextCadCliCPF.getText().toString() + "','" + EditTextCadCliEmail.getText().toString() + "','" +  EditTextCadCliEndereco.getText().toString() + "','" + EditTextCadCliData.getText() + "','" + EditTextCadCliTel.getText().toString() + "','" + EditTextCadCliSenha.getText().toString() + "','A')";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                Toast.makeText(getApplicationContext(),"Cadastrado Com sucessso",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity3.this, LoginCliente.class);
                startActivity(intent);
                finish();
            }
        });

        BtnCadCliVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity3.this, LoginCliente.class);
                startActivity(intent);
                finish();
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