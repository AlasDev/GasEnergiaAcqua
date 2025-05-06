package com.alas.gasenergiaacqua.util;

import org.springframework.context.annotation.Configuration;
import at.favre.lib.crypto.bcrypt.BCrypt;

@Configuration
public class PasswordUtil {
    /**
     * @param password the raw password you want to hash
     * @return the hashed password
     */
    public String hash(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    /**
     * @param password the raw password
     * @param hashed the hashed password you want to campare the raw password with
     * @return 'true' if they match, 'false' otherwise
     */
    public boolean match(String password, String hashed) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashed);
        return result.verified;
    }
}
