package SpringSecurity.JWT.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String Secret = "B0D89FB867B3BBDEDF016817B99A9BE95E85B865B2C0850F11AFFBF7B58D17A197C6CE77B9878DBBA9A7DEE7149F9D82F38DBFBC1E0D7D69C1C762CA2AD6B71B";
	private static final long VALIDITY = TimeUnit.MINUTES.toMillis(300);
	
	public String generateToken(UserDetails userDetails) {
		System.out.println("userDetails are : " + userDetails.getUsername());
		System.out.println("userDetails are : " + userDetails.getPassword());
		
		Map<String, String> claims = new HashMap<>();
		claims.put("iss", "ashok");
		return Jwts.builder()
				.claims(claims)
				.subject(userDetails.getUsername())
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
				.signWith(generateKey())
				.compact();
	}
	
	private SecretKey generateKey() {
		byte[] decodedKey = Base64.getDecoder().decode(Secret);
		return Keys.hmacShaKeyFor(decodedKey);
	}

	public String extractUsername(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.getSubject();
	}

	public boolean isTokenValid(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.getExpiration().after(Date.from(Instant.now()));
	}

	private Claims getClaims(String jwt) {
		return Jwts.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
	}
	
}
