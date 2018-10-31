package com.thingabled.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Bcrypt {

	private static final Logger LOGGER = LoggerFactory.getLogger(Bcrypt.class);
	
	public static void main(String arg[]) {
		
		/*while (true) {
			BCryptPasswordEncoder enc = new BCryptPasswordEncoder(10);
			
			String encoded = enc.encode("ozgen");
			LOGGER.debug(encoded);
			
		}*/
		String salt=BCrypt.gensalt();
		LOGGER.debug(salt);
		String encoded =BCrypt.hashpw("ozgen", salt);
		LOGGER.debug(encoded);
		encoded =BCrypt.hashpw("ozgen", salt);
		
		
		
		boolean check=BCrypt.checkpw("ozgen", encoded);
		LOGGER.debug("check="+check);
	}

}
