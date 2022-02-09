package jinde.appfb02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import jinde.appfb02.Admin.Administar;
import jinde.appfb02.Admin.ListaMisPedidos_Activity;
import jinde.appfb02.Cliente.RealizarPedido_Activity;

@SuppressWarnings("ALL")
public class Home_Activity extends AppCompatActivity {

    private static String mail;
    private TextView tipoUsuario, correo, nombreT, apellidoT, datosRecuperados;
    private Button pedir, administrar;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize firebase Auth
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Initialize local variables
        tipoUsuario = findViewById(R.id.textView_TipoUser);
        correo = findViewById(R.id.textView_Correo);
        nombreT = findViewById(R.id.textView_Nombre);
        apellidoT = findViewById(R.id.textView_Apellido);
        pedir = findViewById(R.id.button_Pedir);
        administrar = findViewById(R.id.button_Admin);
        datosRecuperados = findViewById(R.id.textView_Datos);

        //Disables access bottom
        administrar.setVisibility(View.INVISIBLE);
        administrar.setEnabled(false);

        //Verified Instances
        if (savedInstanceState != null) {
            mail = savedInstanceState.getString("mail").toString();
            Toast.makeText(getApplicationContext(), "Save intace nulls",
                    Toast.LENGTH_SHORT).show();
        } else {
            //Recollect intent data
            Bundle bundle = getIntent().getExtras();
            mail = bundle.getString("mail").toString();
        }

        //Changer user data
        obtenerDatosUsuario(mail);

        //Button Back to Before
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Event Bottom to make pedido
        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RealizarPedido_Activity.class);
                intent.putExtra("mail", mail);
                startActivity(intent);
            }
        });

        //Event bottom to Admin
        administrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Administar.class);

                intent.putExtra("mail", mail);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                //Traendo datos de la activity admin
                String dato = data.getExtras().getString("restore");
                datosRecuperados.setText(dato);
            }
            if (requestCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Ningun Dato traido",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString("mail", mail);
        super.onSaveInstanceState(outState);

    }


    private void obtenerDatosUsuario(String email) {
        databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String tipoU = " ";
                    String nombre = " ";
                    String apellido = " ";
                    String id = "";

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String correo = ds.child("email").getValue().toString();

                        if (email.equals(correo)) {
                            id = ds.getKey();
                            if (ds.child("tipo").exists()) {
                                tipoU = ds.child("tipo").getValue().toString();
                                nombre = ds.child("nombre").getValue().toString();
                                apellido = ds.child("apellido").getValue().toString();

                            } else {
                                tipoU = "cliente";
                                nombre = ds.child("nombre").getValue().toString();
                                apellido = ds.child("apellido").getValue().toString();
                            }
                        }
                    }

                    tipoUsuario.setText(tipoU + " ");
                    habilitateAdmin(tipoUsuario.getText().toString().trim());
                    correo.setText(email + " ");
                    nombreT.setText(nombre + " ");
                    apellidoT.setText(apellido + " ");

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //[Method enabled bottom user as admin]
    private void habilitateAdmin(String user) {
        if (user.equals("administrador")) {
            administrar.setVisibility(View.VISIBLE);
            administrar.setEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        FirebaseAuth.getInstance().signOut();

        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_ayuda:
                Toast.makeText(Home_Activity.this, "Ayuda", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_cerrar:
                FirebaseAuth.getInstance().signOut();
                mAuth.signOut();
                startActivity(new Intent(Home_Activity.this, AuthActivity.class));
                finish();
                break;
            case R.id.item_misOrdenes:
                Intent intent = new Intent(getApplicationContext(), ListaMisPedidos_Activity.class);

                intent.putExtra("mail", mail);
                startActivityForResult(intent, 100);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}