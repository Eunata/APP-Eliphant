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
import android.widget.RatingBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DadosUserPrestador extends AppCompatActivity {

    ImageButton ImgBtnDadosPrestVisivel,ImgBtnDadosAltPrest,ImgBtnDadosUserPrestMaleta,ImgBtnDadosPrestExit;
    EditText EdTxtDadosPrestNome,EdTxtDadosPrestEmail,EdTxtDadosPrestEndereco,
            EdTxtDadosPrestDataNasc,EdTxtDadosPrestTel;
    Button BtnAltDadosPrest;
    TextView TxtRecebeCPFPrest,TxtRecebeAvalPrest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_user_prestador);

        ImgBtnDadosPrestExit = findViewById(R.id.ImgBtnDadosPrestExit);
        ImgBtnDadosUserPrestMaleta = findViewById(R.id.ImgBtnDadosUserPrestMaleta);
        ImgBtnDadosPrestVisivel = findViewById(R.id. ImgBtnDadosPrestVisivel);
        ImgBtnDadosAltPrest = findViewById(R.id.ImgBtnDadosAltPrest);
        EdTxtDadosPrestNome = findViewById(R.id.EdTxtDadosPrestNome);
        EdTxtDadosPrestEmail = findViewById(R.id.EdTxtDadosPrestEmail);
        EdTxtDadosPrestEndereco = findViewById(R.id.EdTxtDadosPrestEndereco);
        EdTxtDadosPrestDataNasc = findViewById(R.id.EdTxtDadosPrestDataNasc);
        EdTxtDadosPrestTel = findViewById(R.id.EdTxtDadosPrestTel);
        BtnAltDadosPrest = findViewById(R.id.BtnAltDadosPrest);
        TxtRecebeCPFPrest = findViewById(R.id.TxtRecebeCPFPrest);
        TxtRecebeAvalPrest = findViewById(R.id.TxtRecebeAvalPrest);

        String RecebeCPFPrestador = getIntent().getStringExtra("EnviarCPFPrestador");
        TxtRecebeCPFPrest.setText(RecebeCPFPrestador);

        ImgBtnDadosPrestExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DadosUserPrestador.this,TelaDeInicio.class);
                startActivity(intent);
                finish();
            }
        });

        ImgBtnDadosUserPrestMaleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DadosUserPrestador.this, MeusServicosPrest.class);
                intent.putExtra("CPFPrestador",TxtRecebeCPFPrest.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        ImgBtnDadosAltPrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EdTxtDadosPrestNome.setEnabled(true);
                EdTxtDadosPrestEmail.setEnabled(true);
                EdTxtDadosPrestEndereco.setEnabled(true);
                EdTxtDadosPrestDataNasc.setEnabled(true);
                EdTxtDadosPrestTel.setEnabled(true);
                BtnAltDadosPrest.setVisibility(View.VISIBLE);
            }
        });

        ImgBtnDadosPrestVisivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlselect = "Select * from PRESTADOR where  CPFpres = '" + TxtRecebeCPFPrest.getText().toString() + "'";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlselect);

                        while (rs.next()){
                            EdTxtDadosPrestNome.setText(rs.getString(2));
                            EdTxtDadosPrestEmail.setText(rs.getString(4));
                            EdTxtDadosPrestEndereco.setText(rs.getString(5));
                            EdTxtDadosPrestDataNasc.setText(rs.getString(6));
                            EdTxtDadosPrestTel.setText(rs.getString(7));
                            TxtRecebeAvalPrest.setText(rs.getString(10));
                        }
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }


                TxtRecebeAvalPrest.setVisibility(View.VISIBLE);
                EdTxtDadosPrestNome.setVisibility(View.VISIBLE);
                EdTxtDadosPrestEmail.setVisibility(View.VISIBLE);
                EdTxtDadosPrestEndereco.setVisibility(View.VISIBLE);
                EdTxtDadosPrestDataNasc.setVisibility(View.VISIBLE);
                EdTxtDadosPrestTel.setVisibility(View.VISIBLE);
            }
        });


        BtnAltDadosPrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlinsert = "Update PRESTADOR set NomePres = '" + EdTxtDadosPrestNome.getText().toString() + "',EmailPres = '" + EdTxtDadosPrestEmail.getText().toString() +
                                "', EnderecoPres = '" + EdTxtDadosPrestEndereco.getText().toString() + "', DataNasPres = '" + EdTxtDadosPrestDataNasc.getText().toString() + "', FonePres = '" + EdTxtDadosPrestTel.getText().toString() + "' where CPFpres = '" + TxtRecebeCPFPrest.getText().toString() + "'";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                EdTxtDadosPrestNome.setEnabled(false);
                EdTxtDadosPrestEmail.setEnabled(false);
                EdTxtDadosPrestEndereco.setEnabled(false);
                EdTxtDadosPrestDataNasc.setEnabled(false);
                EdTxtDadosPrestTel.setEnabled(false);
                BtnAltDadosPrest.setVisibility(View.INVISIBLE);
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