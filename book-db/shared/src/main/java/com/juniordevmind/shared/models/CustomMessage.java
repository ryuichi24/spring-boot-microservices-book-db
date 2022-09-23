package com.juniordevmind.shared.models;

import java.util.Date;

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
    private Date messageDate;

    public void setPayload(T pl) {
        payload = pl;
    }
}
