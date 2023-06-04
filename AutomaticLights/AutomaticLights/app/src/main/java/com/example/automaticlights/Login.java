package com.example.automaticlights;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private Button loginbtn;
    private EditText edituser;
    private EditText editpword;
    private DBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button segpag = findViewById(R.id.button3);
        segpag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registo.class);
                startActivity(intent);
            }
        });

        loginbtn = findViewById(R.id.button2);
        edituser = findViewById(R.id.editTextTextEmailAddress2);
        editpword = findViewById(R.id.editTextTextPassword2);
        dbh = new DBHelper(this);
        //dbh.populateUsers();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userdtx = edituser.getText().toString();
                String passetx = editpword.getText().toString();
                if (TextUtils.isEmpty(userdtx) || TextUtils.isEmpty(passetx)) {
                    Toast.makeText(Login.this, "Introduza Email e Palavra-Passe", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkuser = dbh.checkUserPass(userdtx, passetx);
                    if (checkuser) {
                        Toast.makeText(Login.this, "Login feito com sucesso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Email ou Palavra-passe errada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
