package com.example.eliphant_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CadastroPrestador extends AppCompatActivity {


    String[] OpcoesServicos = {"","Assessor de eventos","Animador: Contador de histórias","Animador: Escultura com balões","Animador: Mágico","Animador: Palhaço","Animador: Pintura Artistíca",
            "Animador: Recreação","Buffet: Almoço/Jantar","Buffet: Comidas Típicas","Buffet: Salgadinhos","Brindes: Caneca personalizada","Brindes: Chinelo Personalizado",
            "Brindes: Outros tipos de brindes","Confeitaria: Bolos","Confeitaria: Doces Personalizados","Confeitaria: Sobremesas","Convites: Designer para criação","Fotografia","Garçon","Recepcionista","Segurança"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_prestador);


        TextView TxtCadPrestRecebeIDPrest = (TextView)findViewById(R.id.TxtCadPrestRecebeIDPrest);
        TextView EditTextCadPrestNome = (TextView)findViewById(R.id.EditTextCadPrestNome);
        TextView EditTextCadPrestCPF = (TextView)findViewById(R.id.EditTextCadPrestCPF);
        TextView EditTextCadPrestEmail = (TextView)findViewById(R.id.EditTextCadPrestEmail);
        TextView EditTextCadPrestEndereco = (TextView)findViewById(R.id.EditTextCadPrestEndereco);
        EditText EditTextCadPrestDataNasc = (EditText) findViewById(R.id.EditTextCadPrestDataNasc);
        TextView EditTextCadPrestTel = (TextView)findViewById(R.id.EditTextCadPrestTel);
        TextView EditTextCadPrestSenha = (TextView)findViewById(R.id.EditTextCadPrestSenha);

        Spinner SpnCadPrestServPrest = (Spinner) findViewById(R.id.SpnCadPrestServPrest);
        TextView TxtOpcaoSelecionada = (TextView)findViewById(R.id.TxtOpcaoSelecionada);

        ImageButton ImgBtnCadPrestVoltar = (ImageButton)findViewById(R.id.ImgBtnCadPrestVoltar);
        ImageButton ImgBtnInserirServPrest = (ImageButton)findViewById(R.id.ImgBtnInserirServPrest);
        Button BtnCadPrestCadastrar = (Button)findViewById(R.id.BtnCadPrestCadastrar);
        Button BtnCadPrestFinalizarCad = (Button)findViewById(R.id.BtnCadPrestFinalizarCad);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, OpcoesServicos);
        SpnCadPrestServPrest.setAdapter(adapter);

        SpnCadPrestServPrest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            TxtOpcaoSelecionada.setText(OpcoesServicos[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ImgBtnInserirServPrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TxtOpcaoSelecionada.getText() != ""){
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlinsert = "Insert into SERVICO values('"+ TxtOpcaoSelecionada.getText()  + "','" + TxtCadPrestRecebeIDPrest.getText() + "')";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);

                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                    Toast.makeText(getApplicationContext(),"Serviço adicionado", Toast.LENGTH_LONG).show();

            }
                else if (TxtOpcaoSelecionada.getText() == ""){

                    Toast.makeText(getApplicationContext(),"Por favor selecione uma opção de serviço", Toast.LENGTH_SHORT).show();
                }
            }
        });


        BtnCadPrestCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlinsert = "Insert into PRESTADOR(NomePres,CPFpres,EmailPres,EnderecoPres,DataNasPres,FonePres,SenhaPres,Situacao) " +
                                "values ('" + EditTextCadPrestNome.getText().toString() +"','" + EditTextCadPrestCPF.getText().toString() + "','" + EditTextCadPrestEmail.getText().toString() + "','" +  EditTextCadPrestEndereco.getText().toString() + "','" + EditTextCadPrestDataNasc.getText() + "','" + EditTextCadPrestTel.getText().toString() + "','" + EditTextCadPrestSenha.getText().toString() + "','A')";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);

                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                try {
                    if (connection != null) {
                        String sqlselect = "Select * from PRESTADOR where  CPFpres = '" + EditTextCadPrestCPF.getText().toString() + "'";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlselect);

                        while (rs.next()){
                            TxtCadPrestRecebeIDPrest.setText(rs.getString(1));
                        }
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                Toast.makeText(getApplicationContext(),"Dados Salvos",Toast.LENGTH_SHORT).show();

                /*Intent intent = new Intent(CadastroPrestador.this, LoginPrestador.class);
                startActivity(intent);
                finish();*/
            }
        });

        BtnCadPrestFinalizarCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Cadastro Finalizado", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(CadastroPrestador.this, LoginPrestador.class);
                startActivity(intent);
                finish();
            }
        });

        ImgBtnCadPrestVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroPrestador.this, LoginPrestador.class);
                startActivity(intent);
                finish();
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