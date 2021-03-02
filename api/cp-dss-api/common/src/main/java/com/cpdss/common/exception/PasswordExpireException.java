package com.cpdss.common.exception;

import com.cpdss.common.utils.HttpStatusCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

/**
 * Password expire exception
 * Usage: com.cpdss.gateway.security.ship.ShipUserDetailService
 *
 * @author johnsoorajxavier
 * @since 03-02-2021
 */

@Getter
public class PasswordExpireException extends AuthenticationException {

    private String code;
    private HttpStatusCode status;

    public PasswordExpireException(String message, String code, HttpStatusCode status){
        super(message);
        this.code = code;
        this.status = status;
    }

    public PasswordExpireException(String message, String code, HttpStatusCode status, Throwable e){
        super(message, e);
        this.code = code;
        this.status = status;
    }
}
