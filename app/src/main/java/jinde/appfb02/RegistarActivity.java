package jinde.appfb02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class RegistarActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editCedula, editTexNombre, editTextApellido, editTextMail, editTextPassword;
    private String Cedula, Nombre, Apellido, Mail, Password = "";

    private Button buttonRegistar;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize local variables
        editCedula = findViewById(R.id.editText_Cedula);
        editTexNombre = findViewById(R.id.editText_IngresarNom);
        editTextApellido = findViewById(R.id.editText_IngresarApe);
        editTextMail = findViewById(R.id.editText_EmailAddress);
        editTextPassword = findViewById(R.id.editTexT_Password);

        Cedula = editCedula.getText().toString();
        Nombre = editTexNombre.getText().toString();
        Apellido = editTextApellido.getText().toString();
        Mail = editTextMail.getText().toString();
        Password = editTextPassword.getText().toString();


        buttonRegistar = findViewById(R.id.buttonRegistar);


        
    }
}