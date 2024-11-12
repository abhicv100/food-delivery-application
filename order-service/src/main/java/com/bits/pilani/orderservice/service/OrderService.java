package com.bits.pilani.orderservice.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public LocalDateTime getEstimatedTime(int kms, LocalDateTime currentTime)
    {
        if(kms <= 2)
        {
            return currentTime.plusMinutes(15);
        }
        else if(kms <= 5)
        {
            return currentTime.plusMinutes(25);
        }
        else if(kms <= 10)
        {
            return currentTime.plusMinutes(40);
        }
        else if(kms <= 15)
        {
            return currentTime.plusMinutes(60);
        }
        else
        {
            return currentTime.plusMinutes(90);
        }
    }

}
