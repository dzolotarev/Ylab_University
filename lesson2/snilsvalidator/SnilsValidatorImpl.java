package lesson2.snilsvalidator;

public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {
        if (snils == null || !snils.matches("[0-9]{11}")) {
            return false;
        }
        //суммы произведения цифр 1-9
        int preControlSum = calculatePreControlSumm(snils);
        //вычисляем контрольную сумму
        int calculatedControlSum = calculateControlSum(preControlSum);
        //сравниваем и возвращаем
        return calculatedControlSum == Integer.parseInt(snils.substring(9));
    }

    //вычисляем сумму произведения цифр 1-9
    private int calculatePreControlSumm(String snils) {
        int preControlSum = 0;
        for (int i = 0; i < 9; i++) {
            preControlSum += Character.digit(snils.charAt(i), 10) * (9 - i);
        }
        return preControlSum;
    }

    //вычисляем контрольную сумму на основе суммы произведения цифр 1-9
    private int calculateControlSum(int preControlSum) {
        if (preControlSum < 100) {
            return preControlSum;
        } else if (preControlSum > 100) {
            int remOfDivision = preControlSum % 101;
            if (remOfDivision != 100) {
                return remOfDivision;
            }
        }
        return 0;
    }
}
