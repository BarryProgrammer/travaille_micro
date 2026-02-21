package com.operis.project.infrastructure.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JWTParser {

    @Value("${code.secret}")
    private String codeSecret;

    public JWTClaimsSet parseToken(String token){
        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(codeSecret);

            if(!signedJWT.verify(verifier)){
                throw new RuntimeException("Invalid JWT signature");
            }

            return  signedJWT.getJWTClaimsSet();
        } catch (JOSEException | java.text.ParseException e) {
            throw new RuntimeException("Error white parsing JWT token ", e);

        }
    }
}
