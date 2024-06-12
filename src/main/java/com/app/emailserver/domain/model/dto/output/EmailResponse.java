package com.app.emailserver.domain.model.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailResponse extends ErrorResponse{
    public EmailResponse(int status, String message) {
        super(status, message);
    }
}
