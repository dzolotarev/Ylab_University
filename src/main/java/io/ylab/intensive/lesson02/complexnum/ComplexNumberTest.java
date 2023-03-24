package io.ylab.intensive.lesson02.complexnum;

public class ComplexNumberTest {
    public static void main(String[] args) {
        ComplexNumber number = new ComplexNumber(3.23);
        ComplexNumber number1 = new ComplexNumber(5.24);
        ComplexNumber sum = number.sum(number1);
        ComplexNumber subtr = number.subtr(number1);
        ComplexNumber mult = number.mult(number1);
        System.out.println(sum);
        System.out.println(subtr);
        System.out.println(mult);
        System.out.println(subtr.abs());

        number = new ComplexNumber(1.0, 2.0);
        number1 = new ComplexNumber(0, 2.0);

        System.out.println(number.sum(number1));
        System.out.println(number1.subtr(number));
        System.out.println(number.mult(number1));
        System.out.println(number.abs());
        System.out.println(number1.abs());

        number1 = new ComplexNumber(2.33, 3.44);

        System.out.println(number.sum(number1));
        System.out.println(number.subtr(number1));
        System.out.println(number.mult(number1));

    }
}
