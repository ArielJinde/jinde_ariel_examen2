package jinde.appfb02.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jinde.appfb02.Admin.ListaMisPedidos_Activity;
import jinde.appfb02.AuthActivity;
import jinde.appfb02.R;

@SuppressWarnings("ALL")
public class RealizarPedido_Activity extends AppCompatActivity {
    private TextView text, nombreT, apellidoT;
    private ListView listViewDatos;
    //Variables Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pedido);
        // Initialize local variables
        text = findViewById(R.id.textView4);
        nombreT = findViewById(R.id.textView_nombreL);
        apellidoT = findViewById(R.id.textView_ApellidoL);
        listViewDatos = findViewById(R.id.listView_Datos);

        //Initialize firebase Auth
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Traer Datos
        Bundle bundle = getIntent().getExtras();
        String mail = bundle.getString("mail").toString();
        String nom = bundle.getString("nombre").toString();
        String ape = bundle.getString("apellido").toString();

        //Button Back to Before
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {

       this.finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        MenuItem item = menu.findItem(R.id.item_misOrdenes);
        item.setVisible(false);
        return  true;
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
                Log.i("ActionBar", "Atrás!");
                finish();
                break;
            case R.id.item_misOrdenes:
                Intent intent2 = new Intent(getApplicationContext(), ListaMisPedidos_Activity.class);
                intent2.putExtra("nombre", nombreT.getText());
                intent2.putExtra("apellido", apellidoT.getText());
                intent2.putExtra("mail", text.getText().toString());
                startActivityForResult(intent2, 100);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}