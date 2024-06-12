package com.app.emailserver.domain.model.dto.input;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    public Long emailId;
    public String emailFrom;
    public List<EmailAddressDto> emailTo;
    public List<EmailAddressDto> emailCC;
    public String emailBody;
    public Long state;
}
