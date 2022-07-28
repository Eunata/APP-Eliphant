package com.example.eliphant_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView editTextNome = (TextView) findViewById(R.id.editTextNome);
        TextView editTextTel = (TextView) findViewById(R.id.editTextTel);
        TextView editTextCPF = (TextView) findViewById(R.id.editTextCPF);
        Button btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        Button BtnUpdate = (Button) findViewById(R.id.BtnUpdate);
        Button BtnDelete = (Button) findViewById(R.id.BtnDelete);
        Button BtnGet = (Button) findViewById(R.id.BtnGet);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlinsert = "Insert into DadosUsuario values ('" + editTextNome.getText().toString() + "','" + editTextTel.getText().toString() + "','" + editTextCPF.getText().toString() + "')";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }
            }
        });

        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlinsert = "Update DadosUsuario set Nome = '" + editTextNome.getText().toString() + "',Telefone = '" + editTextTel.getText().toString() + "'  where CPF =   '" + editTextCPF.getText().toString() + "'";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }
            }
        });

        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqldelete = "Delete DadosUsuario where  CPF = '" + editTextCPF.getText().toString() + "'";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqldelete);
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }
            }
        });

        BtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlselect = "Select * from DadosUsuario where  CPF = '" + editTextCPF.getText().toString() + "'";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlselect);

                        while (rs.next()){
                            editTextNome.setText(rs.getString(2));
                            editTextTel.setText(rs.getString(3));
                        }
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }
            }
        });

    }




    @SuppressLint("NewApi")
    public Connection connectionclass() {
        Connection con = null;
        String ip = "192.168.100.172", port = "1433", username = "sa", password = "12345", databasename = "EliphantProject";
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
