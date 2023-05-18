package com.backend.girnartour.constants;

import lombok.Getter;

import java.math.BigInteger;
import java.util.UUID;

@Getter
public class UserConstants {

    public static final String DEFAULT_ROLE="USER";

    public static final String JWT_SECRET="sharath";

    public static final String PATH="http://localhost:8088/api/v1/user/reset_password";

    public static final Integer SUCEESS_STATUS=200;

    public static final Integer UNSUCCESS_STATUS = 404;

    public static final String CACHE_KEY="emailKey";

    public static final String random_sequence= String.format("%040d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));

}
