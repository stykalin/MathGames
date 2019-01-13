package ru.alstmedia.calculations;

public enum MathSign {
    PLUS("сумма"),
    MINUS("разность");

    private String name;

    MathSign(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return name;
    }
}
