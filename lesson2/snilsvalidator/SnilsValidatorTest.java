package lesson2.snilsvalidator;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        System.out.println(new SnilsValidatorImpl().validate("901144044411")); //false
        System.out.println(new SnilsValidatorImpl().validate("901d4404441")); //false
        System.out.println(new SnilsValidatorImpl().validate(null)); //false
        System.out.println(new SnilsValidatorImpl().validate("01468870570")); //false
        System.out.println(new SnilsValidatorImpl().validate("90114404441")); //true
    }
}
