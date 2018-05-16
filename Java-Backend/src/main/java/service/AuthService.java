package service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.Account;
import org.mindrot.jbcrypt.BCrypt;
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

    public String login(String username, String password) throws Exception {
        Account loginUser = userRepository.getUser(username);
        if(loginUser == null){
            throw new Exception("Login error");
        }
        if(BCrypt.checkpw(password,loginUser.getEncryptedPassword() ))
        {
            return genToken(loginUser);
        }
        else
        {
            throw new Exception("Login error");
        }

    }

    private String genToken(Account account) throws UnsupportedEncodingException {
        Calendar now = Calendar.getInstance();
        Calendar expired = Calendar.getInstance();

        expired.add(Calendar.HOUR, 1);

        return Jwts.builder()
                .setSubject(String.valueOf(account.getId()))
                .setId("15a96c27-f703-4f1b-adbd-e4c1b007cb83")
                .setIssuedAt( now.getTime())
                .setExpiration( expired.getTime())
                .claim("name", account.getUserName())
                .claim("admin", false)
                .claim("userid", account.getId())
                .signWith(SignatureAlgorithm.HS512, "kwetter".getBytes("UTF-8"))
                .compact();
    }
}
