package com.example.eliphant_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaDeInicio extends AppCompatActivity {

    Button BtnInicialCliente, BtnInicialPestServ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_inicio);

        BtnInicialCliente = findViewById(R.id.BtnInicialCliente);
        BtnInicialPestServ = findViewById(R.id.BtnInicialPrestServ);

        BtnInicialCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaDeInicio.this, LoginCliente.class);
                startActivity(intent);
                finish();
            }
        });

        BtnInicialPestServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaDeInicio.this, LoginPrestador.class);
                startActivity(intent);
                finish();
            }
        });

    }
}