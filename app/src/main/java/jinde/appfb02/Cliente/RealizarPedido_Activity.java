package jinde.appfb02.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pedido);
        //Initialize firebase Auth
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Initialize local variables
        textView = findViewById(R.id.textView3);

        //Recollect intent data
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("mail").toString();

        textView.setText("Bienvenido  : "+email);

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

                intent2.putExtra("mail", textView.getText().toString());
                startActivityForResult(intent2, 100);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}