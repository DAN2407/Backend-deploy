package com.example.city_security.services.serviceImpl;

import com.example.city_security.services.StateService;
import org.springframework.stereotype.Service;

@Service
public class StateServiceImpl implements StateService {
    private volatile int state = 0;

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void activateState() {
        state = 1;
        resetStateAfterDelay();
    }

    @Override
    public void resetStateAfterDelay() {
        new Thread(() -> {
            try {
                Thread.sleep(10000); // Esperar 10 segundos
                state = 0;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
