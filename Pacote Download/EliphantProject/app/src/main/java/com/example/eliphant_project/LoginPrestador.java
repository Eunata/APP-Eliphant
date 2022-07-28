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
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginPrestador extends AppCompatActivity {


    EditText EditTextLogPrestCPF, EditTextLogPrestSenha;
    Button BtnLogPrestEntrar, BtnLogPrestNovoUser;
    Connection con;
    ImageButton ImgBtnLogPrestVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_prestador);

        EditTextLogPrestCPF = findViewById(R.id.EditTextLogPrestCPF);
        EditTextLogPrestSenha = findViewById(R.id.EditTextLogPrestSenha);
        BtnLogPrestEntrar = findViewById(R.id.BtnLogPrestEntrar);
        BtnLogPrestNovoUser = findViewById(R.id.BtnLogPrestNovoUser);
        ImgBtnLogPrestVoltar = findViewById(R.id.ImgBtnLogPrestVoltar);


        ImgBtnLogPrestVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPrestador.this, TelaDeInicio.class);
                startActivity(intent);
                finish();
            }
        });

        BtnLogPrestNovoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPrestador.this, CadastroPrestador.class);
                startActivity(intent);
                finish();
            }
        });

        BtnLogPrestEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginPrestador.checkLogin().execute();
            }
        });
    }


    public class checkLogin extends AsyncTask<String, String, String> {

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
            String ip = "192.168.100.172", port = "1433", username = "sa", password = "12345", databasename = "ELIPHANT_PLATAFORMA";
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
                        Toast.makeText(LoginPrestador.this,"Verifique a conexão de internet",Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {
                    String sql = "SELECT * FROM PRESTADOR WHERE CPFpres = '" + EditTextLogPrestCPF.getText() + "' AND SenhaPres = '" + EditTextLogPrestSenha.getText() + "' ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginPrestador.this, "Logado Com Sucesso", Toast.LENGTH_LONG).show();
                            }
                        });
                        z = "Success";

                        Intent intent = new Intent(LoginPrestador.this, MeusServicosPrest.class);
                        intent.putExtra("CPFPrestador",EditTextLogPrestCPF.getText().toString());
                        startActivity(intent);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginPrestador.this, "Verifique CPF e senha", Toast.LENGTH_LONG).show();
                            }
                        });

                        EditTextLogPrestCPF.setText("");
                        EditTextLogPrestSenha.setText("");
                    }
                } catch (Exception e) {
                    isSuccess = false;
                    Log.e("SQL Error : ", e.getMessage());
                }
            }
            return z;
        }
    }



    @SuppressLint("NewApi")
    public Connection connectionclass() {
        Connection con = null;
        String ip = "192.168.254.50", port = "1433", username = "sa", password = "12345", databasename = "ELIPHANT_PLATAFORMA";
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