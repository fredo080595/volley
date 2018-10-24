package com.example.fredo.serviciosweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private EditText etUsuario,etContra;
    private Button btn;
    JSONArray ja;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bind();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  ConsultaPass("http://10.0.3.2/ejemplologin/consultarusuario.php?user="+usuario.getText().toString());
                ConsultaPass("http://192.168.8.102/webservices/recibirDatos.php?usua="+etUsuario.getText().toString());
            }
        });

    }
    private void ConsultaPass(String URL) {

        Log.i("url",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    ja = new JSONArray(response);
                    String contra = ja.getString(0);

                    if(contra.equals(etContra.getText().toString()) ){

                        Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, pantallaHola.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getApplicationContext(),"verifique su contraseña",Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(),"El usuario no existe en la base de datos", Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast();
            }
        });

        queue.add(stringRequest);



    }

    public void Bind(){
        etUsuario = (EditText)findViewById(R.id.editText);
        etContra = (EditText) findViewById(R.id.editText2);

        btn = (Button) findViewById(R.id.button);

    }

    public void toast(){
        Toast.makeText(this, "Error en la peticion",Toast.LENGTH_SHORT).show();
    }
}
