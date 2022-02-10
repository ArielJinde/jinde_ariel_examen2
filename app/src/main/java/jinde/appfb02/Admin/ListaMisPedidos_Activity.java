package jinde.appfb02.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import jinde.appfb02.Clases.Pedidos;
import jinde.appfb02.R;

public class ListaMisPedidos_Activity extends AppCompatActivity {
    private TextView text;
    private ListView listViewDatos;
    //Variables Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mis_pedidos);

        text = findViewById(R.id.textView4);
        Bundle bundle = getIntent().getExtras();
        String mail = bundle.getString("mail").toString();
        String nom   = bundle.getString("nombre").toString();
        String ape = bundle.getString("apellido").toString();
        text.setText("Lista de pedidos de:  " + mail);

        //Disabled MisPedidos

        listViewDatos = findViewById(R.id.listView_Datos);
        //Initialize firebase Auth
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Button To Back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cargarPedidos(nom,ape);
    }

    public void cargarPedidos(String nombreS,String apellidoS) {

        List<Pedidos> pedidos = new ArrayList<>();
        databaseReference.child("pedidos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int i = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        i++;
                        String id = ds.getKey();

                        String nombre = (String) ds.child("0").child("nombre").getValue();
                        String apellido = (String)ds.child("0").child("apellido").getValue();
                        String direccion = (String)ds.child("0").child("direccion").getValue();
                        String correo =(String) ds.child("1").child("correo").getValue();
                        String totalparcial ="dsd";
                        double total = 0.2;

                        if (correo!=null && nombre.equals(nombreS) && apellido.equals(apellidoS))
                            pedidos.add(new Pedidos(id, correo, nombre, apellido, direccion, totalparcial, total));
                    }

                    ArrayAdapter<Pedidos> adapter = new ArrayAdapter<Pedidos>(ListaMisPedidos_Activity.this,
                            android.R.layout.simple_list_item_1, pedidos);
                    listViewDatos.setAdapter(adapter);
                    listViewDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Pedidos pedidos1 = (Pedidos) adapterView.getItemAtPosition(i);

                            /*Intent intent = new Intent(ListaPedidos.this, Factura.class);
                            intent.putExtra("idPedido", pedidos1.getId().toString());
                            intent.putExtra("nombre", pedidos1.getNombre().toString());
                            intent.putExtra("apellido", pedidos1.getApellido().toString());
                            intent.putExtra("correo", pedidos1.getCorreo().toString());
                            intent.putExtra("totalP", pedidos1.getTotalparcial().toString());
                            intent.putExtra("total", String.valueOf(pedidos1.getTotal()));


                            startActivity(intent);*/


                            Toast.makeText(getApplicationContext(), "Item: " + pedidos1.getCorreo().toString() + " " + i,
                                    Toast.LENGTH_LONG).show();

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //return pedidos;
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        MenuItem item = menu.findItem(R.id.item_misOrdenes);
        item.setVisible(false);
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
            case android.R.id.home:
                //devolverDatos();
                Intent intent = new Intent();
                intent.putExtra("restore", text.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                Log.i("ActionBar", "Atrás!");

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}