package com.niaa.apirest.Utils;

import com.niaa.apirest.Modelo.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductoService {

    @GET("listar")
    Call<List<Producto>> getProducto();


}
