import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExperidException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodeJWT;
import sun.misc.BASE64Decoder;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Token {
    private Algorithm alg = null;

    public Token() throws UnsupportedEncodingException{
        this.alg = ALgorithm.HMAC256("secret");
    }

    public String createToken(){
        String token = JWT.create().withIssuer("auth0").withExpiresAt(new Date(System.currentTimeMillis()+TimeUnit.DAYS.toMillis(30))).withClaim("nome",1234).sign(this.getAlgorithm());
        System.out.println("token"+token);
        return token;
    }

    public boolean verifyToken(String token){
        try{
            JWTVerifier veri = JWT.require(this.getAlgorithm()).withIssuer("auth0").build();

        }catch(TokenExpiredException e){
            System.out.println("Token expirou");
            return false;

        }
    }

    public DecodeJWT decodeToken(String token){
        Base64.Decoder dec = Base64.getUrlDecoder();
        DecodedJWT decode = JWT.decode(token);
        System.out.println("decoded jwt " + new String(dec.decode(decode.getPayload())));
        return decode;
    }

    public Algorithm getAlgorithm(){
        return alg;
    }
} 