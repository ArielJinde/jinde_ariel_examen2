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


        buttonRegistar = findViewById(R.id.buttonCrerUser);


        buttonRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cedula = editCedula.getText().toString().trim();
                Nombre = editTexNombre.getText().toString().trim();
                Apellido = editTextApellido.getText().toString().trim();
                Mail = editTextMail.getText().toString().trim();
                Password = editTextPassword.getText().toString().trim();
                Telefono = editText_Telf.getText().toString().trim();
                if (validadorDeCedula(Cedula)) {
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
                                Toast.makeText(getApplicationContext(), "Usuario Creado " +
                                        "!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
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

    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
// Coeficientes de validación cédula
// El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            Toast.makeText(getApplicationContext(), "Una excepcion ocurrio en " +
                    "el proceso de validadcion" +
                    "!", Toast.LENGTH_SHORT).show();
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            Toast.makeText(getApplicationContext(), "La Cédula ingresada es Incorrecta" +
                    "!", Toast.LENGTH_SHORT).show();
        }
        return cedulaCorrecta;
    }
}