package jinde.appfb02.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import jinde.appfb02.R;

public class VistaClienteActivity extends AppCompatActivity {
    private TextView textViewId;
    private TextView textViewNombre;
    private TextView textViewApellido;
    private TextView textViewCorreo;
    private TextView textViewDirrecion;
    private TextView textViewTelefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_cliente);

        textViewId = findViewById(R.id.textViewCodigo);
        textViewNombre = findViewById(R.id.textViewNombre);
        textViewApellido = findViewById(R.id.textViewApellido);
        textViewCorreo = findViewById(R.id.textViewCorreo);
        textViewDirrecion = findViewById(R.id.textViewDireccion);
        textViewTelefono = findViewById(R.id.textViewTelefono);
        llenarCliente();

        //Button To Back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void llenarCliente() {
        Bundle bundle = getIntent().getExtras();
        String codigo = bundle.getString("codigo").toString();
        String correo = bundle.getString("correo").toString();
        String nombre = bundle.getString("nombre").toString();
        String apellido = bundle.getString("apellido").toString();
        String direccion = bundle.getString("direccion").toString();
        String telefono = bundle.getString("telefono").toString();
        textViewId.setText(codigo);
        textViewNombre.setText(nombre);
        textViewApellido.setText(apellido);
        textViewCorreo.setText(correo);
        textViewDirrecion.setText(direccion);
        textViewTelefono.setText(telefono);


    }

}

