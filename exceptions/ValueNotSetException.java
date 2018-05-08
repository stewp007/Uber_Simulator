package exceptions;

public class ValueNotSetException extends RuntimeException{
	public ValueNotSetException(String message){
		super(message);
	}
}