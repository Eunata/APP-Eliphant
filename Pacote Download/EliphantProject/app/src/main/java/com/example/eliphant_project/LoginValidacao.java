package com.example.eliphant_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
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

public class LoginValidacao extends AppCompatActivity {

    Connection con;
    EditText editTextValidacaoCPF,editTextValidacaoTelefone;
    Button btnValidacaoNovo,btnValidacaoEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_validacao);

         editTextValidacaoCPF =  findViewById(R.id.editTextValidacaoCPF);
         editTextValidacaoTelefone = findViewById(R.id.editTextValidacaoTelefone);
        btnValidacaoNovo =  findViewById(R.id.btnValidacaoNovo);
        btnValidacaoEntrar =  findViewById(R.id.btnValidacaoEntrar);

        btnValidacaoNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginValidacao.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnValidacaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               new LoginValidacao.checkLogin().execute();
            }
        });

    }

    public class checkLogin extends AsyncTask<String, String, String>{

        String z = null;
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected String doInBackground(String... strings) {
            Connection con = null;
            String ip = "192.168.100.172", port = "1433", username = "sa", password = "12345", databasename = "EliphantProject";
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                String connectionUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + databasename + ";User=" + username + ";password=" + password + ";";
                con = DriverManager.getConnection(connectionUrl);
            } catch (Exception exception) {
                Log.e("Error", exception.getMessage());
            }

            /*con = Conexao(Conexao.un.toString(),Conexao.pass.toString(),Conexao.db.toString(),Conexao.ip.toString());*/
            if(con == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginValidacao.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {
                    String sql = "SELECT * FROM DadosUsuario WHERE CPF = '" + editTextValidacaoCPF.getText() + "' AND Telefone = '" + editTextValidacaoTelefone.getText() + "' ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginValidacao.this, "Login Success", Toast.LENGTH_LONG).show();
                            }
                        });
                        z = "Success";

                        Intent intent = new Intent(LoginValidacao.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginValidacao.this, "Check email or password", Toast.LENGTH_LONG).show();
                            }
                        });

                        editTextValidacaoCPF.setText("");
                        editTextValidacaoTelefone.setText("");
                    }
                } catch (Exception e) {
                    isSuccess = false;
                    Log.e("SQL Error : ", e.getMessage());
                }
            }
            return z;
        }
    }
    
    
    /*public class checklogin extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }*/


    @SuppressLint("NewApi")
    public Connection connectionclass() {
        Connection con = null;
        String ip = "192.168.254.50", port = "1433", username = "sa", password = "12345", databasename = "EliphantProject";
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