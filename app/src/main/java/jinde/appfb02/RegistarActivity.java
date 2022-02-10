package jinde.appfb02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistarActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private EditText editCedula, editTexNombre, editTextApellido, editTextMail, editTextPassword, editText_Telf;
    private String Cedula, Nombre, Apellido, Mail, Password, Telefono = "";

    private Button buttonRegistar;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // Initialize local variables
        editCedula = findViewById(R.id.editText_Cedula);
        editTexNombre = findViewById(R.id.editText_IngresarNom);
        editTextApellido = findViewById(R.id.editText_IngresarApe);
        editTextMail = findViewById(R.id.editText_EmailAddress);
        editTextPassword = findViewById(R.id.editTexT_Password);
        editText_Telf = findViewById(R.id.editText_Telefono);


        buttonRegistar = findViewById(R.id.buttonRegistar);


        buttonRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cedula = editCedula.getText().toString().trim();
                Nombre = editTexNombre.getText().toString().trim();
                Apellido = editTextApellido.getText().toString().trim();
                Mail = editTextMail.getText().toString().trim();
                Password = editTextPassword.getText().toString().trim();
                Telefono = editText_Telf.getText().toString().trim();

                if (Cedula.isEmpty() && Nombre.isEmpty() && Apellido.isEmpty() &&
                        Mail.isEmpty() && Password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campos Vacios !", Toast.LENGTH_SHORT).show();

                } else {

                    if (Password.length() >= 6) {
                        registrarUsario();
                    } else {
                        Toast.makeText(getApplicationContext(), "Campo Password alemnos 6 caracteres" +
                                "!", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }

    private void registrarUsario() {

        mAuth.createUserWithEmailAndPassword(Mail, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("cedula", Cedula);
                    map.put("nombre", Nombre);
                    map.put("apellido", Apellido);
                    map.put("password", Password);
                    map.put("email", Mail);
                    map.put("telefono", Mail);
                    map.put("tipo", "cliente");

                    String id = mAuth.getCurrentUser().getUid();

                    databaseReference.child("usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task.isSuccessful()) {
                            finish();
                            }else   {
                                Toast.makeText(getApplicationContext(), "No se creo el usario Correctamente" +
                                        "!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                } else {

                }
            }
        });
    }
}