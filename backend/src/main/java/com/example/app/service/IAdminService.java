package com.example.app.service;

import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface IAdminService {
    public void dropAll();
}
