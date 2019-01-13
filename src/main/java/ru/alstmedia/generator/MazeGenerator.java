package ru.alstmedia.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.alstmedia.calculations.MathExamples;
import ru.alstmedia.calculations.MathSign;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MazeGenerator {

    private static final Logger LOG = LogManager.getFormatterLogger(MazeGenerator.class);

    private static final int SIZE = 17;

    private static final String CORRECT_POINT_SYMBOL = "+"; //символ для ячейки с правильным примером
    private static final String WRONG_POINT_SYMBOL = "x"; //символ для ячейки с неверным примером
    private static final String INIT_POINT_SYMBOL = "*"; //символ для ячейки при инициализации таблицы

    private Random rand = new Random();
    private String[][] table;
    private Coordinates currentPointCoordinates;
    private static final Coordinates EXIT_POINT_COORDINATES = new Coordinates(SIZE - 1, SIZE - 1);

    private static String prevExample;

    public String[][] fillTable(MathSign sign, int resultNumber) {
        prepareTable();
        for (int i = 0; i < table.length; i++) {
            String[] rows = table[i];
            for (int j = 0; j < rows.length; j++) {
                String cell = rows[j];
                if (cell.equals(MazeGenerator.CORRECT_POINT_SYMBOL)) {
                    String mathExample;
                    do {
                        mathExample = MathExamples.getExample(sign, resultNumber);
                    } while (mathExample.equals(prevExample));
                    prevExample = mathExample;
                    table[i][j] = mathExample;
                } else {
                    int maxNumber;
                    do {
                        maxNumber = rand.nextInt(resultNumber * 2) + 1;
                    } while (maxNumber == resultNumber);
                    String mathExample = MathExamples.getExample(sign, maxNumber);
                    table[i][j] = mathExample;
                }
            }
        }
        return table;
    }

    private String[][] prepareTable() {
        initTable();
        do {
            makeNexStep();
        } while (!currentPointCoordinates.equals(EXIT_POINT_COORDINATES));
        setPointSign(EXIT_POINT_COORDINATES, CORRECT_POINT_SYMBOL);

        return table;
    }

    private void initTable() {
        table = new String[SIZE][SIZE];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                table[i][j] = INIT_POINT_SYMBOL;
            }
        }
        currentPointCoordinates = new Coordinates(0, 0);
        setPointSign(currentPointCoordinates, CORRECT_POINT_SYMBOL);
    }


    public void printTable(String[][] table) {
        LOG.info("Print table:");
        for (String[] row : table) {
            for (int j = 0; j < table.length; j++) {
                System.out.print(row[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void setPointSign(Coordinates point, String sign) {
//        LOG.debug(String.format("set %s in %s", sign, point));
        LOG.debug("set %s in %s", sign, point);
        table[point.getY()][point.getX()] = sign;
    }

    private void makeNexStep() {
        Direction direction = getAvailableDirection(currentPointCoordinates);
        markUnavailablePoints(currentPointCoordinates, direction);
        currentPointCoordinates = getDesiredCoordinates(currentPointCoordinates, direction);
        setPointSign(currentPointCoordinates, CORRECT_POINT_SYMBOL);

        LOG.debug(String.format("Dir: %s, curCoordinates: %s %n", direction, currentPointCoordinates));
    }

    private Direction getAvailableDirection(Coordinates point) {
        Direction direction = getRandomDirection();
        Coordinates desiredCoordinates = getDesiredCoordinates(point, direction);
        if (!isPointAvailable(desiredCoordinates)) {
            return getAvailableDirection(point);
        }
        if (!isNotBlocked(desiredCoordinates)) {
            initTable();
            return getAvailableDirection(currentPointCoordinates);
        }
        return direction;
    }

    private Coordinates getDesiredCoordinates(Coordinates point, Direction direction) {
        switch (direction) {
            case NORTH:
                if (canGoNorth(point)) {
                    return getNorthFrom(point);
                }
                return currentPointCoordinates;
            case EAST:
                return getEastFrom(point);
            case SOUTH:
                return getSouthFrom(point);
            case WEST:
                if (canGoWest(point)) {
                    return getWestFrom(point);
                }
                return currentPointCoordinates;
            default:
                throw new VerifyError("There is no realisation for direction: " + direction);
        }
    }

    private boolean canGoNorth(Coordinates point) {
        return point.getX() < SIZE - 2;
    }

    private boolean canGoWest(Coordinates point) {
        return point.getY() < SIZE - 2;
    }

    private Direction getRandomDirection() {
        int numDir = rand.nextInt(4);
        switch (numDir) {
            case 0:
                return Direction.NORTH;
            case 1:
                return Direction.EAST;
            case 2:
                return Direction.SOUTH;
            case 3:
                return Direction.WEST;
            default:
                throw new VerifyError("There is no realisation for numDir: " + numDir);
        }
    }


    private boolean isPointAvailable(Coordinates desiredCoordinates) {
        return isInBoundaries(desiredCoordinates)
                && isInitPoint(desiredCoordinates);
    }

    private boolean isInBoundaries(Coordinates point) {
        return point.getX() >= 0
                && point.getY() >= 0
                && point.getX() < SIZE
                && point.getY() < SIZE;
    }

    private boolean isInitPoint(Coordinates point) {
        String symbolInPoint = table[point.getY()][point.getX()];
        return symbolInPoint.equals(INIT_POINT_SYMBOL);
    }

    /**
     * Проверяем не заблокирован ли следующзий ход от указанной точки.
     *
     * @param point - координаты проверяемой точки
     * @return boolean
     */
    private boolean isNotBlocked(Coordinates point) {
        return isPointAvailable(getNorthFrom(point))
                || isPointAvailable(getEastFrom(point))
                || isPointAvailable(getSouthFrom(point))
                || isPointAvailable(getWestFrom(point));

    }

    private void markUnavailablePoints(Coordinates point, Direction direction) {
        switch (direction) {
            case NORTH:
                markUnavailablePoint(getEastFrom(point));
                markUnavailablePoint(getSouthFrom(point));
                markUnavailablePoint(getWestFrom(point));
                break;
            case EAST:
                markUnavailablePoint(getSouthFrom(point));
                markUnavailablePoint(getWestFrom(point));
                markUnavailablePoint(getNorthFrom(point));
                break;
            case SOUTH:
                markUnavailablePoint(getWestFrom(point));
                markUnavailablePoint(getNorthFrom(point));
                markUnavailablePoint(getEastFrom(point));
                break;
            case WEST:
                markUnavailablePoint(getNorthFrom(point));
                markUnavailablePoint(getEastFrom(point));
                markUnavailablePoint(getSouthFrom(point));
                break;
        }
    }

    private void markUnavailablePoint(Coordinates point) {
        if (isPointAvailable(point)) {
            setPointSign(point, WRONG_POINT_SYMBOL);
        }
    }

    private Coordinates getNorthFrom(Coordinates point) {
        return new Coordinates(point.getX(), point.getY() - 1);
    }

    private Coordinates getEastFrom(Coordinates point) {
        return new Coordinates(point.getX() + 1, point.getY());

    }

    private Coordinates getSouthFrom(Coordinates point) {
        return new Coordinates(point.getX(), point.getY() + 1);
    }

    private Coordinates getWestFrom(Coordinates point) {
        return new Coordinates(point.getX() - 1, point.getY());
    }


    public List<List<String>> getTableForTemplate(String[][] table) {
        return Arrays.stream(table)
                .map(Arrays::asList)
                .collect(Collectors.toList());
    }
}
