package com.app.emailserver.domain.model.mapper;

import com.app.emailserver.domain.model.data.email.EmailEntity;
import com.app.emailserver.domain.model.data.email.EmailAddress;
import com.app.emailserver.domain.model.data.email.EmailAddressCC;
import com.app.emailserver.domain.model.dto.input.EmailDto;
import com.app.emailserver.domain.model.dto.input.EmailAddressDto;
import com.app.emailserver.domain.model.dto.input.Emails;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    @Mapping(source = "emailId", target = "emailId")
    @Mapping(source = "emailFrom", target = "emailFrom")
    @Mapping(source = "emailTo", target = "emailTo")
    @Mapping(source = "emailCC", target = "emailCC")
    @Mapping(source = "emailBody", target = "emailBody")
    @Mapping(source = "state", target = "emailStatus.id")
    EmailEntity toEmailEntity(EmailDto emailDto);

    @Mapping(source = "emailId", target = "emailId")
    @Mapping(source = "emailFrom", target = "emailFrom")
    @Mapping(source = "emailTo", target = "emailTo")
    @Mapping(source = "emailCC", target = "emailCC")
    @Mapping(source = "emailBody", target = "emailBody")
    @Mapping(source = "emailStatus.id", target = "state")
    EmailDto toEmailDto(EmailEntity emailEntity);

    List<EmailEntity> toEmailEntities(List<EmailDto> emailDtos);

    List<EmailDto> toEmailDtos(List<EmailEntity> emailEntities);

    default Emails toEmailsResponse(List<EmailEntity> emailEntities) {
        return new Emails(toEmailDtos(emailEntities));
    }

    default List<EmailAddress> mapEmailAddressDtoListToEmailAddressList(List<EmailAddressDto> emailAddressDtos) {
        return emailAddressDtos.stream().map(this::mapEmailAddressDtoToEmailAddress).collect(Collectors.toList());
    }

    default List<EmailAddressCC> mapEmailAddressCCDtoListToEmailAddressCCList(List<EmailAddressDto> emailAddressCCDtos) {
        return emailAddressCCDtos.stream().map(this::mapEmailAddressCCDtoToEmailAddressCC).collect(Collectors.toList());
    }

    default EmailAddress mapEmailAddressDtoToEmailAddress(EmailAddressDto emailAddressDto) {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setEmail(emailAddressDto.getEmail());
        return emailAddress;
    }

    default EmailAddressCC mapEmailAddressCCDtoToEmailAddressCC(EmailAddressDto emailAddressCCDto) {
        EmailAddressCC emailAddressCC = new EmailAddressCC();
        emailAddressCC.setEmail(emailAddressCCDto.getEmail());
        return emailAddressCC;
    }

    @AfterMapping
    default void establishRelationships(@MappingTarget EmailEntity emailEntity) {
        if (emailEntity.getEmailTo() != null) {
            for (EmailAddress emailAddress : emailEntity.getEmailTo()) {
                emailAddress.setEmailEntity(emailEntity);
            }
        }
        if (emailEntity.getEmailCC() != null) {
            for (EmailAddressCC emailAddressCC : emailEntity.getEmailCC()) {
                emailAddressCC.setEmailEntity(emailEntity);
            }
        }
    }
}
