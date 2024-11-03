package it.unibo.exceptions.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static it.unibo.exceptions.arithmetic.ArithmeticUtil.nullIfNumberOrException;
import static java.lang.Double.parseDouble;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * A service implementing a simple interpreter for an arithmetic language.
 * Runs expressions composed of numbers and binary operators
 * (see KEYWORDS), respecting operator priorities.
 */
public final class ArithmeticService {

    /**
     * Multiplication.
     */
    public static final String TIMES = "times";
    /**
     * Addition.
     */
    public static final String PLUS = "plus";
    /**
     * Subtraction.
     */
    public static final String MINUS = "minus";
    /**
     * Division.
     */
    public static final String DIVIDED = "divided";
    /**
     * Available language keywords.
     */
    public static final Set<String> KEYWORDS = Set.of(TIMES, PLUS, MINUS, DIVIDED);

    private final List<String> commandQueue;

    /**
     * Builds a new service that interprets the provided commands.
     *
     * @param commands the commands to interpret.
     */
    public ArithmeticService(final List<String> commands) {
        this.commandQueue = new ArrayList<>(Objects.requireNonNull(commands)); // Defensive mutable copy
    }

    /**
     * Runs the recorded commands.
     *
     * @return the result of the process
     */
    public String process() throws IllegalArgumentException {
        try {
            if (this.commandQueue.isEmpty()) {
                throw new IllegalArgumentException("No commands sent, no result available");
            }
            while (this.commandQueue.size() != 1) {
                final var nextMultiplication = this.commandQueue.indexOf(TIMES);
                final var nextDivision = this.commandQueue.indexOf(DIVIDED);
                final var nextPriorityOp = nextMultiplication >= 0 && nextDivision >= 0
                    ? min(nextMultiplication, nextDivision)
                    : max(nextMultiplication, nextDivision);
                if (nextPriorityOp >= 0) {
                    computeAt(nextPriorityOp);
                } else {
                    final var nextSum = this.commandQueue.indexOf(PLUS);
                    final var nextMinus = this.commandQueue.indexOf(MINUS);
                    final var nextOp = nextSum >= 0 && nextMinus >= 0
                        ? min(nextSum, nextMinus)
                        : max(nextSum, nextMinus);
                    if (nextOp != -1) {
                        if (this.commandQueue.size() < 3) {
                            throw new IllegalArgumentException("Inconsistent operation: " + this.commandQueue);
                        }
                        computeAt(nextOp);
                    } else if (this.commandQueue.size() > 1) {
                        throw new IllegalArgumentException("Inconsistent state: " + this.commandQueue);
                    }
                }
            }
            final var finalResult = this.commandQueue.get(0);
            final var possibleException = nullIfNumberOrException(finalResult);
            if (possibleException != null) {
                throw new IllegalArgumentException("Invalid result of operation: " + finalResult);
            }
            return finalResult;
        } finally {
            this.commandQueue.clear();
        }
        /*
         * The this.commandQueue should be cleared, no matter what, when the method exits
         * But how?
         */
    }

    private void computeAt(final int operatorIndex) throws IllegalStateException {
        if (operatorIndex == 0) {
            throw new IllegalStateException("Illegal start of operation: " + this.commandQueue);
        }
        if (this.commandQueue.size() < 3) {
            throw new IllegalStateException("Not enough operands: " + this.commandQueue);
        }
        if (this.commandQueue.size() < operatorIndex + 1) {
            throw new IllegalStateException("Missing right operand: " + this.commandQueue);
        }
        final var rightOperand = this.commandQueue.remove(operatorIndex + 1);
        final var leftOperand = this.commandQueue.remove(operatorIndex - 1);
        if (KEYWORDS.contains(rightOperand) || KEYWORDS.contains(leftOperand)) {
            throw new IllegalStateException(
                "Expected a number, but got " + leftOperand + " and " + rightOperand + " in " + this.commandQueue
            );
        }
        final var right = parseDouble(rightOperand);
        final var left = parseDouble(leftOperand);
        final var operand = this.commandQueue.get(operatorIndex - 1);
        final var result =  switch (operand) {
            case PLUS -> left + right;
            case MINUS -> left - right;
            case TIMES -> left * right;
            case DIVIDED -> left / right;
            default ->  {
                throw new IllegalStateException("Unknown operand " + operand);
            }
        };
        this.commandQueue.set(operatorIndex - 1, Double.toString(result));
    }
}
