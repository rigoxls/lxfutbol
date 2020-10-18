package com.lxfutbol.provider.exception;

public class ProviderNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public ProviderNotFoundException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
