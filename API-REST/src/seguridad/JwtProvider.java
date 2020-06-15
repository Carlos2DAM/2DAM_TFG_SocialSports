package seguridad;

import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

	private byte[] secret = Base64.getDecoder().decode("FzrjEf54HygsZxHaHqmPBGfvilRIET2e5srD70iLLTs=");
	
	public String generarToken(String correo) {
		String jws = Jwts.builder()
			.setHeaderParam("typ","JWT")
			.setSubject(correo)
		    .setIssuer("SocialSports.com")
		    .setIssuedAt(new Date())
		    .signWith(Keys.hmacShaKeyFor(secret))
		    .compact();
		return jws;
	}
	
	public boolean validateToken(String token){
		Jws<Claims> jws;
		try {
		    jws = Jwts.parserBuilder() 
		    .setSigningKey(Keys.hmacShaKeyFor(secret))
		    .build()                    
		    .parseClaimsJws(token); 
		
			return true;
			
		} catch (JwtException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public String getCorreo(String token) {
		Jws<Claims> jws;
		try {
			jws = Jwts.parserBuilder() 
				    .setSigningKey(Keys.hmacShaKeyFor(secret))
				    .build()                    
				    .parseClaimsJws(token);
			
			return jws.getBody().getSubject();
		}catch(JwtException ex) {
			return "";
		}
	}
}