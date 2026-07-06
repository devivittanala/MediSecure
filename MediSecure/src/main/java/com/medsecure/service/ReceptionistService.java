package com.medsecure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsecure.model.Receptionist;
import com.medsecure.repository.ReceptionistRepository;

@Service
public class ReceptionistService {

    @Autowired
    private ReceptionistRepository repository;

    public List<Receptionist> getAllReceptionists() {
        return repository.findAll();
    }

    public Receptionist getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Receptionist receptionist) {
        repository.save(receptionist);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
}