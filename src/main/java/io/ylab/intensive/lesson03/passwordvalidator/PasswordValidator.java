package io.ylab.intensive.lesson03.passwordvalidator;

/**
 * @author Denis Zolotarev
 */
public class PasswordValidator {
    private static final String REGEX = "^[A-Za-z0-9_]+$";
    private static final int MAX_LENGTH = 20;

    public static boolean validateLoginAndPassword(String login, String password, String confirmPassword) {
        if (login == null || password == null || confirmPassword == null) {
            return false;
        }

        try {
            PasswordValidator validator = new PasswordValidator();
            validator.checkLogin(login);
            validator.checkPassword(password, confirmPassword);
        } catch (WrongLoginException | WrongPasswordException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private void checkLogin(String login) throws WrongLoginException {
        if (!login.matches(REGEX)) {
            throw new WrongLoginException("Логин содержит недопустимые символы");
        }
        if (login.length() >= MAX_LENGTH) {
            throw new WrongLoginException("Логин слишком длинный");
        }
    }

    private void checkPassword(String password, String confirmPassword) throws WrongPasswordException {
        if (!password.matches(REGEX)) {
            throw new WrongPasswordException("Пароль содержит недопустимые символы");
        }
        if (password.length() >= MAX_LENGTH) {
            throw new WrongPasswordException("Пароль слишком длинный");
        }
        if (!password.equals(confirmPassword)) {
            throw new WrongPasswordException("Пароль и подтверждение не совпадают");
        }
    }
}
