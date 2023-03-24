package io.ylab.intensive.lesson03.passwordvalidator;

/**
 * @author Denis Zolotarev
 */
public class WrongPasswordException extends Exception {

    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
