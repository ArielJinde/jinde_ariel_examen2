package jinde.appfb02.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jinde.appfb02.AuthActivity;
import jinde.appfb02.Clases.Usuario;
import jinde.appfb02.R;

@SuppressWarnings("ALL")
public class Administar extends AppCompatActivity {

    private Button buttonTodos, infoClient;
    private TextView textviewPedidosC;
    private String usuario, clave;
    private Spinner spinner_ctgProduct, spinnerUsuarios;
    private int contador = 0;
    private ListView listViewDatos;
    //Variables Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar);

        //Initialize firebase Auth
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Initialize local variables
        textView = findViewById(R.id.textView2);
        buttonTodos = findViewById(R.id.buttonTodos);
        infoClient = findViewById(R.id.button_infoClient);
        spinner_ctgProduct = findViewById(R.id.spinnerPrpductos);
        spinnerUsuarios = findViewById(R.id.spinnerUsuarios);
        listViewDatos = findViewById(R.id.listView_Datos);
        textviewPedidosC = findViewById(R.id.textViewCantidadPedidos);


        //Recollect intent data
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("mail").toString();

        textView.setText("Bienvenido Admin : " + email);

        //Button To Back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Methods cargar controles
        cargarCategoria();
        cargarUsuarios();
        cantidadPedidos();

        //
        infoClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarOrdersClient();
            }
        });


    }

    private void mostrarOrdersClient() {
        Usuario usuario1 = (Usuario) spinnerUsuarios.getSelectedItem();
        Intent intent = new Intent(Administar.this, ListaMisPedidos_Activity.class);
        intent.putExtra("codigo", usuario1.getCodigo().toString());
        intent.putExtra("nombre", usuario1.getNombre().toString());
        intent.putExtra("apellido", usuario1.getApellido().toString());
        intent.putExtra("telefono", usuario1.getTelefono().toString());
        intent.putExtra("mail", usuario1.getCorreo().toString());
        intent.putExtra("direccion", usuario1.getDirrecion().toString());

        startActivity(intent);
    }

    private void cantidadPedidos() {
        databaseReference.child("pedidos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int i = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        i++;
                        contador++;
                    }
                    textviewPedidosC.setText(String.valueOf(contador));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cargarUsuarios() {
        final List<Usuario> usuarios = new ArrayList<>();
        databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String codigo = ds.getKey();
                        String telefono = ds.child("telefono").getValue().toString();
                        String apellido = ds.child("apellido").getValue().toString();
                        String nombre = ds.child("nombre").getValue().toString();
                        String email = ds.child("email").getValue().toString();
                        String direccion = ds.child("direccion").getValue().toString();

                        usuarios.add(new Usuario(codigo, telefono, nombre, apellido, email, direccion));
                    }

                    ArrayAdapter<Usuario> arrayAdapter =
                            new ArrayAdapter<>(Administar.this, android.R.layout.simple_dropdown_item_1line, usuarios);
                    spinnerUsuarios.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cargarCategoria() {
    }

    @Override
    public void onBackPressed() {
        this.finish();
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
                Toast.makeText(getApplicationContext(), "Ayuda", Toast.LENGTH_SHORT).show();

                break;
            case R.id.item_cerrar:
                FirebaseAuth.getInstance().signOut();
                mAuth.signOut();

                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                finish();
                break;
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                //devolverDatos();
                Intent intent = new Intent();
                intent.putExtra("restore", textView.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                Log.i("ActionBar", "Atrás!");

                finish();
                break;
            case R.id.item_misOrdenes:
                Intent intent2 = new Intent(getApplicationContext(), ListaMisPedidos_Activity.class);

                intent2.putExtra("mail", textView.getText().toString());
                startActivityForResult(intent2, 100);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    //Regresando  datos al activity anterior
    private void devolverDatos() {
        Intent intent = new Intent();
        intent.putExtra("restore", textView.getText().toString());
        setResult(Activity.RESULT_OK, intent);
    }
}