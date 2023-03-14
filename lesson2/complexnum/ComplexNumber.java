package lesson2.complexnum;

public class ComplexNumber {
    private double real;
    private double imagine;

    //комплексное число без мнимой части
    public ComplexNumber(double real) {
        this.real = real;
    }

    //комплексное число с мнимой частью
    public ComplexNumber(double real, double imagine) {
        this.real = real;
        this.imagine = imagine;
    }

    //сложение комплекстных чисел: (a + bi) + (c + di) = (a + c) + (b + d)i
    public ComplexNumber sum(ComplexNumber num) {
        double real = this.real + num.real;
        double imagine = this.imagine + num.imagine;
        return new ComplexNumber(real, imagine);
    }

    //вычитание комплексных чисел: (a + bi) - (c + di) = (a - c) + (b - d)i
    public ComplexNumber subtr(ComplexNumber num) {
        double real = this.real - num.real;
        double imagine = this.imagine - num.imagine;
        return new ComplexNumber(real, imagine);
    }

    //умножение комплексных чисел:  (a + bi) · (c + di) = (ac – bd) + (ad + bc)i
    public ComplexNumber mult(ComplexNumber num) {
        double real = this.real * num.real - this.imagine * num.imagine;
        double imagine = this.real * num.imagine + this.imagine * num.real;
        return new ComplexNumber(real, imagine);
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