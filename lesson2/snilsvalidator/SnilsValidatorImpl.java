package lesson2.snilsvalidator;

public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {

        if (snils.length() != 11) {
            return false;
        }

        int preControlSum = 0;
        int controlSum = 0;

        for (int i = 0; i < snils.length(); i++) {
            //если встречается что-то отличное от цифры
            if (!Character.isDigit(snils.charAt(i))) {
                return false;
            }

            //достаем из номера контрольную сумму
            if (i == snils.length() - 2) {
                int tmp = Character.digit(snils.charAt(i), 10);
                controlSum = tmp * 10;
            } else if (i == snils.length() - 1) {
                int tmp = Character.digit(snils.charAt(i), 10);
                controlSum += tmp;

                //вычисляем сумму произведения цифр с 1 по 9
            } else {
                int tmp = Character.digit(snils.charAt(i), 10) * snils.length() - 2 - i;
                preControlSum += tmp;
            }
        }

        int calculatedControlSum = calculateControlSum(preControlSum);

        return calculatedControlSum == controlSum;
    }

    //метод проверяет и вычисляет контрольную сумму на основе суммы произведения цифр 1-9
    private int calculateControlSum(int preControlSum) {
        int result = 0;
        if (preControlSum < 100) {
            result = preControlSum;
        } else if (preControlSum > 100) {
            int remOfDivision = preControlSum % 101;
            if (remOfDivision == 100) {
                result = 0;
            } else {
                result = remOfDivision;
            }
        }
        return result;
    }
}
