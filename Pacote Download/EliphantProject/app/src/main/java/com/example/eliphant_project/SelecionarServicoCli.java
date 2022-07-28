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

public class SelecionarServicoCli extends AppCompatActivity {

    ImageButton ImgBtnSelServCliDadosUser,ImgBtnSelServCliConfirm,ImgBtnSelServCliMeusPedidos;
    TextView TxtRecebeCpfCliente,TxtSelecServCliRecebeIDCli;
    Spinner SpnSelecionarServCli;
    String OpcaoSelecionadoCli;
    EditText EdTextSelServCliData, EdTextSelServCliLocal, EdTextSelServCliNConvidados, EdTextSelServCliInfAdd;
    Button BtnSelServCliConfirm;

    String[] SelecaodeservicoCli = {"","Assessor de eventos","Animador: Contador de histórias","Animador: Escultura com balões","Animador: Mágico","Animador: Palhaço","Animador: Pintura Artistíca",
            "Animador: Recreação","Buffet: Almoço/Jantar","Buffet: Comidas Típicas","Buffet: Salgadinhos","Brindes: Caneca personalizada","Brindes: Chinelo Personalizado",
            "Brindes: Outros tipos de brindes","Confeitaria: Bolos","Confeitaria: Doces Personalizados","Confeitaria: Sobremesas","Convites: Designer para criação","Fotografia","Garçon","Recepcionista","Segurança"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_servico_cli);

        TxtSelecServCliRecebeIDCli = findViewById(R.id.TxtSelecServCliRecebeIDCli);
        EdTextSelServCliData = findViewById(R.id.EdTextSelServCliData);
        EdTextSelServCliLocal = findViewById(R.id.EdTextSelServCliLocal);
        EdTextSelServCliNConvidados = findViewById(R.id.EdTextSelServCliNConvidados);
        EdTextSelServCliInfAdd = findViewById(R.id.EdTextSelServCliInfAdd);
        BtnSelServCliConfirm = findViewById(R.id.BtnSelServCliConfirm);

        SpnSelecionarServCli = findViewById(R.id.SpnSelecionarServCli);
        TxtRecebeCpfCliente = findViewById(R.id.TxtRecebeCpfCliente);
        ImgBtnSelServCliDadosUser = findViewById(R.id.ImgBtnSelServCliDadosUser);
        ImgBtnSelServCliConfirm = findViewById(R.id.ImgBtnSelServCliConfirm);
        ImgBtnSelServCliMeusPedidos = findViewById(R.id.ImgBtnSelServCliMeusPedidos);


        String RecebeCPFCliente = getIntent().getStringExtra("CPFCliente");
        TxtRecebeCpfCliente.setText(RecebeCPFCliente);

        ImgBtnSelServCliDadosUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelecionarServicoCli.this, DadosUserCliente.class);
                intent.putExtra("DadosCPFCliente",TxtRecebeCpfCliente.getText().toString());
                startActivity(intent);

            }
        });

        ImgBtnSelServCliMeusPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelecionarServicoCli.this, MeusPedidosCli.class);
                intent.putExtra("DadosCPFCliente",TxtRecebeCpfCliente.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,  SelecaodeservicoCli);
       SpnSelecionarServCli.setAdapter(adapter);

        SpnSelecionarServCli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OpcaoSelecionadoCli = (SelecaodeservicoCli[i]);

                EdTextSelServCliData.setVisibility(View.INVISIBLE);
                EdTextSelServCliLocal.setVisibility(View.INVISIBLE);
                EdTextSelServCliNConvidados.setVisibility(View.INVISIBLE);
                EdTextSelServCliInfAdd.setVisibility(View.INVISIBLE);
                BtnSelServCliConfirm.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ImgBtnSelServCliConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OpcaoSelecionadoCli != "")
                {
                    EdTextSelServCliData.setVisibility(View.VISIBLE);
                    EdTextSelServCliLocal.setVisibility(View.VISIBLE);
                    EdTextSelServCliNConvidados.setVisibility(View.VISIBLE);
                    EdTextSelServCliInfAdd.setVisibility(View.VISIBLE);
                    BtnSelServCliConfirm.setVisibility(View.VISIBLE);
                }
                else if(OpcaoSelecionadoCli == ""){
                    Toast.makeText(getApplicationContext(),"Por favor selecione uma opção",Toast.LENGTH_SHORT).show();
                }

            }
        });

        BtnSelServCliConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = connectionclass();
                try {
                    if (connection != null) {
                        String sqlselect = "Select * from CLIENTE  where  CPFcli = '" + TxtRecebeCpfCliente.getText().toString() + "'";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlselect);

                        while (rs.next()){
                            TxtSelecServCliRecebeIDCli.setText(rs.getString(1));
                        }
                    }
                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                try {
                    if (connection != null) {
                        String sqlinsert = "Insert into  REALIZAR_SERVICO(IdCli,Data_evento,Endereco_evento,Num_convidados,Inf_adicionais,Situacao) " +
                                "values ('" +TxtSelecServCliRecebeIDCli.getText().toString() +"','" + EdTextSelServCliData.getText().toString() + "','" + EdTextSelServCliLocal.getText() + "','" +  EdTextSelServCliNConvidados.getText().toString() + "','" + EdTextSelServCliInfAdd.getText().toString() + "','Solicitado')";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(sqlinsert);

                    }

                } catch (Exception exception) {
                    Log.e("Error", exception.getMessage());
                }

                Toast.makeText(getApplicationContext(),"Informações Salvas",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SelecionarServicoCli.this, SelecionarPrestadorCli.class);
                intent.putExtra("ServicoSelecionado",OpcaoSelecionadoCli);
                intent.putExtra("IdCliente",TxtSelecServCliRecebeIDCli.getText());
                intent.putExtra("DadosCPFCliente",TxtRecebeCpfCliente.getText().toString());
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