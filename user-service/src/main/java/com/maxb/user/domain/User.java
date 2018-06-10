package com.maxb.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotNull
    private String userId;

    private String firstName;
    private String lastName;
    private String img;

    private String bdate = null;

    private String inviteCode = null;

    private int relation = 0;
    private int sex = 0;

    private int countryId = 0;
    private int cityId = 0;
    private String countryTitle = null;
    private String cityTitle = null;

    private long balance = 0;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private LocalDateTime registeredDate = LocalDateTime.now();
}
