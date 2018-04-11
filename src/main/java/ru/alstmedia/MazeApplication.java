package ru.alstmedia;

/**
 * Created by Family on 10.04.2018.
 */
public class MazeApplication {

    public static final int SIZE = 16;

    public static void main(String[] args) {
        MazeGenerator mg = new MazeGenerator();

        mg.prepareField();
//        mg.printField();
    }
}
