package com.campusland.respository;

import java.util.List;

import com.campusland.respository.models.Factura;

public interface CrudRepositoryFactura {

    List<Factura> listar();

    void crear(Factura factura);
    
}
