package com.juniordevmind.shared.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomMessage<T> {
    private String messageId;
    private T payload;
    private LocalDateTime messageDate;

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
