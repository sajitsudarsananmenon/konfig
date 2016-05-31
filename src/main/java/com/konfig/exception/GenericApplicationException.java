package com.konfig.exception;

import javax.ws.rs.WebApplicationException;

/**
 * 
 * @author Sajit Sudarsanan
 * 
 *         Generic Application Exception for konfig application
 *
 */
public class GenericApplicationException extends Exception {

	private static final long serialVersionUID = 6033173075717997275L;
	Exception thrownException = null;

	public GenericApplicationException(Exception e) {
		super(e);
		this.thrownException = e;
	}

}
