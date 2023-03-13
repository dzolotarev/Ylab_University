package lesson2.complexnum;

public class ComplexNumber {
    private double real;
    private double imagine;

    //комплексное число без мнимой части
    public ComplexNumber(double real) {
        this.real = real;
    }

    //комплексное число с минмой частью
    public ComplexNumber(double real, double imagine) {
        this(real);
        this.imagine = imagine;
    }

    //сложение комплекстных чисел: (a + bi) + (c + di) = (a + c) + (b + d)i
    public ComplexNumber sum(ComplexNumber num) {
        ComplexNumber result;
        double imagine;

        double real = this.real + num.real;
        if (this.imagine != 0 || num.imagine != 0) {
            imagine = this.imagine + num.imagine;
            result = new ComplexNumber(real, imagine);
        } else {
            result = new ComplexNumber(real);
        }
        return result;
    }

    //вычитание комплексных чисел: (a + bi) - (c + di) = (a - c) + (b - d)i
    public ComplexNumber subtr(ComplexNumber num) {
        ComplexNumber result;
        double imagine;
        double real = this.real - num.real;
        if (this.imagine != 0 || num.imagine != 0) {
            imagine = this.imagine - num.imagine;
            result = new ComplexNumber(real, imagine);
        } else {
            result = new ComplexNumber(real);
        }
        return result;
    }

    //умножение комплексных чисел:  (a + bi) · (c + di) = (ac – bd) + (ad + bc)i
    public ComplexNumber mult(ComplexNumber num) {
        ComplexNumber result;
        double real;
        double imagine;
        if (this.imagine != 0 || num.imagine != 0) {
            real = this.real * num.real - this.imagine * num.imagine;
            imagine = this.real * num.imagine + this.imagine * num.real;
            result = new ComplexNumber(real, imagine);
        } else {
            real = this.real * num.real;
            result = new ComplexNumber(real);
        }
        return result;
    }

    //модуль комплексного числа
    public double abs() {
        return Math.sqrt(this.real * this.real + this.imagine * this.imagine);
    }

    //записи комплексного числа выглядит так: z = x + y*i, где x - действительная часть комплексного числа, y - мнимая часть
    //i - мнимая единица
    @Override
    public String toString() {
        String result;
        if (imagine == 0) {
            result = String.format("%f", real);
        } else if (real == 0) {
            result = String.format("%f*i", imagine);
        } else if (imagine < 0) {
            result = String.format("%f - %f*i", real, Math.abs(imagine));
        } else {
            result = String.format("%f + %f*i", real, imagine);
        }
        return result;
    }
}