package com.niaa.apirest;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.niaa.apirest.Modelo.Producto;
import com.niaa.apirest.Utils.Api;
import com.niaa.apirest.Utils.ProductoService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView JsonTxtView;
    private double iva;
    private double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonTxtView = findViewById(R.id.jsonText);
        getProducto();
    }

    private void getProducto(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.8:8001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductoService productoService = retrofit.create(ProductoService.class);

        Call<List<Producto>>call = productoService.getProducto();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if(!response.isSuccessful()){
                    JsonTxtView.setText("Codigo: "+response.code());
                    return;
                }
                List<Producto> productoList = response.body();

                for(Producto producto: productoList){
                    iva = (producto.getPrecio()*0.16);
                    total = (producto.getPrecio()+iva);
                    String content = "";
                    content += "ID: "+producto.getId() + "\n";
                    content += "MARCA: "+producto.getMarca() + "\n";
                    content += "TIPO: "+producto.getTipo() + "\n";
                    content += "MODELO: "+producto.getModelo() + "\n";
                    content += "PRECIO: "+producto.getPrecio() + "\n";
                    content += "IVA: "+iva+"\n";
                    content += "TOTAL: "+total+"\n\n\n";
                    JsonTxtView.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                JsonTxtView.setText(t.getMessage());
            }
        });

    }

}
