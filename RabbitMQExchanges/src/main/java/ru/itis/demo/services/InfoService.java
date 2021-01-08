package ru.itis.demo.services;

import ru.itis.demo.dto.UserDTO;

public interface InfoService {
    void sendToExchange(UserDTO userDTO);
}
