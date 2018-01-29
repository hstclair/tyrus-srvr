package com.dcc.scratch;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Permutifier {

    String[] elements;

    int permutationCount;

    int[] factors;

    public Permutifier(String[] elements) {

        this.elements = elements;

        permutationCount = cheapFactorial(elements.length);

        factors = buildFactors(elements.length, permutationCount);
    }

    int getPermutationCount() {
        return permutationCount;
    }

    String[] getPermutation(int index) {

        int[] target = computeTarget(index);

        return resolveTarget(target);
    }

    String[] resolveTarget(int[] target) {

        List<String> candidates = new LinkedList<>(Arrays.asList(elements));

        String[] result = new String[elements.length];

        for (int rank = 0; rank < result.length; rank++)
            result[rank] = candidates.remove(target[rank]);

        return result;
    }


    int[] computeTarget(int index) {
        int[] target = new int[elements.length];

        for (int rank = 0; rank < target.length; rank++) {

            if (rank == factors.length) {
                target[rank] = 0;

                continue;
            }

            target[rank] = index / factors[rank];

            index = index % factors[rank];
        }

        return target;
    }


    int[] buildFactors(int elementCount, int permutationCount) {
        int[] result = new int[elementCount - 1];

        int lastFactor = permutationCount;

        for (int index = 0; index < result.length; index++) {

            result[index] = lastFactor / (elementCount - index);

            lastFactor = result[index];
        }

        return result;
    }

    int cheapFactorial(int value) {

        if (value < 2)
            return 1;

        if (value == 2)
            return 2;

        return IntStream.rangeClosed(2, value)
                .reduce((a, b) -> a * b).orElse(1);
    }
}
