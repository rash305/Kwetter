package service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import repository.JPA;
import repository.UserRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

@Stateless
public class AuthService {

    @Inject
    @JPA
    UserRepository userRepository;

    public String login(String username, String password)throws UnsupportedEncodingException{
        return genToken();
    }

    private String genToken() throws UnsupportedEncodingException {
        Calendar now = Calendar.getInstance();
        Calendar expired = Calendar.getInstance();

        expired.add(Calendar.HOUR, 1);

        return Jwts.builder()
                .setSubject("1")
                .setId("15a96c27-f703-4f1b-adbd-e4c1b007cb83")
                .setIssuedAt( now.getTime())
                .setExpiration( expired.getTime())
                .claim("name", "John Doe")
                .claim("admin", true)
                .claim("userid", 1)
                .signWith(SignatureAlgorithm.HS512, "kwetter".getBytes("UTF-8"))
                .compact();
    }
}
