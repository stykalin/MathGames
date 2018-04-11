package ru.alstmedia;

import java.util.Objects;
import java.util.Random;

import static ru.alstmedia.MazeApplication.SIZE;

public class MazeGenerator {

    private String[][] field;
    private int x = 0, y = 0;
    private Random rand = new Random();
    private int bound = SIZE - 1;
    private String xS = "+"; //символ пути

    public String[][] prepareField() {
        field = new String[SIZE][SIZE];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = "-";
            }
        }


        field[y][x] = xS;
        int count = 0;
        while (!(x == bound && y == bound)) {
            if (nextStep()) {
                field[y][x] = xS;
                printField();
                count =0;
            }else {
                count++;
            }
            System.out.println();
            if (count > 30) break;
        }
        return field;
    }


    public void printField() {
        for (String[] aField : field) {
            for (int j = 0; j < field.length; j++) {
                System.out.print(aField[j] + " ");
            }
            System.out.println();
        }
    }

    public boolean nextStep() {
        int vector = rand.nextInt(4);
        int x_left, x_right, y_up, y_down;
        System.out.println("vc = " + vector + "; x = " + x + "; y = " + y);

        switch (vector) {
            case 0: //up
                y_up = y;
                y_up--;
                x_left = x != 0 ? x - 1 : x;
                x_right = x != bound ? x + 1 : x;
                if (y_up >= 0 && !Objects.equals(field[y_up][x], xS)
                        && !Objects.equals(field[y_up][x_left], xS)
                        && !Objects.equals(field[y_up][x_right], xS)
                        && x_right != bound) {
                    y--;
                }
                break;
            case 1: //right
                x_right = x;
                x_right++;
                y_up = y != 0 ? y - 1 : y;
                y_down = y != bound ? y + 1 : y;
                if (x_right <= bound
                        && !Objects.equals(field[y][x_right], xS)
                        && !Objects.equals(field[y_down][x_right], xS)
                        && !Objects.equals(field[y_up][x_right], xS)) {
                    x++;
                }
                break;
            case 2: //down
                y_down = y;
                y_down++;
                x_left = x != 0 ? x - 1 : x;
                x_right = x != bound ? x + 1 : x;
                int x_left_corner = x - 2;
                int y_left_corner = y - 2;
                if (y_down <= bound
                        && !Objects.equals(field[y_down][x], xS)
                        && !Objects.equals(field[y_down][x_left], xS)
                        && !Objects.equals(field[y_down][x_right], xS)
                        && !Objects.equals(field[y_left_corner][x_left_corner], xS)) {
                    y++;
                }
                break;
            case 3: //left
                x_left = x;
                x_left--;
                y_up = y != 0 ? y - 1 : y;
                y_down = y != bound ? y + 1 : y;
                if (x_left >= 1
                        && x_left != 0
                        && !Objects.equals(field[y][x_left], xS)
                        && !Objects.equals(field[y_down][x_left], xS)
                        && !Objects.equals(field[y_up][x_left], xS)
                        && !Objects.equals(field[y][x_left-1], xS)
                        && y_up != 0
                        && y_down != bound) {
                    x--;
                }
                break;
            default:
                break;
        }

        return !(Objects.equals(field[y][x], xS));
    }

}
