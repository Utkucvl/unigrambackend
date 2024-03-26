package unigram.demo.security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${questapp.app.secret}")
    private String APP_SECRET;
    @Value("${questapp.expires.in}")
    private Long EXPIRES_IN;

    public String generateJwtToken(Authentication auth ){
        JWTUserDetails userDetails = (JWTUserDetails) auth.getPrincipal();
        Date expireDate = new Date( new Date().getTime() + EXPIRES_IN);
        String token = Jwts.builder().setSubject(Long.toString(userDetails.getId())).setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();
        return token;
    }
    Long getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());

    }

    boolean validateToken(String token ){
        try{
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        }catch (SignatureException e ){
            return false ;
        }
        catch (UnsupportedJwtException e ){
            return false ;
        }
        catch (MalformedJwtException e ){
            return false ;
        }
        catch (ExpiredJwtException e ){
            return false ;
        }
        catch (IllegalArgumentException e ){
            return false ;
        }

    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

    public String generateJwtTokenByUserName(int userId) {
        Date expireDate = new Date( new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userId)).setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();
    }
}
