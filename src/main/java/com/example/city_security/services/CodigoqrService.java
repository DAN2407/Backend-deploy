package com.example.city_security.services;

import com.example.city_security.models.dtos.RegisterCodeQRDTO;
import com.example.city_security.models.entities.CodigoQR;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CodigoqrService {
    void registerCodeQR(RegisterCodeQRDTO codeQRRegisterDTO);
    List<CodigoQR> findAll();
    boolean IsCodeQRValid(UUID id);

}
