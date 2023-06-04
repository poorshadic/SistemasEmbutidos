package com.example.automaticlights;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registo extends AppCompatActivity {

    private EditText uname;
    private EditText pword;
    private Button signupbtn;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        uname = findViewById(R.id.editTextTextEmailAddress2);
        pword = findViewById(R.id.editTextTextPassword2);
        signupbtn = findViewById(R.id.button4);
        db = new DBHelper(this);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unametext = uname.getText().toString();
                String pwordtext = pword.getText().toString();
                boolean savedata = db.insertData(unametext, pwordtext);

                if (TextUtils.isEmpty(unametext) || TextUtils.isEmpty(pwordtext)) {
                    Toast.makeText(Registo.this, "Introduzir Email e Palavra-Passe", Toast.LENGTH_SHORT).show();
                } else {
                    if (savedata) {
                        Toast.makeText(Registo.this, "Conta Criada com Sucesso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Registo.this, "Conta já Existe", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
