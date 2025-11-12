package numbers;

import java.util.List;
import java.util.Set;

class NumbersImpl implements Numbers {

    private static NumbersImpl instance;

    private NumbersImpl() {}

    static NumbersImpl getInstance() {
        if (instance == null) {
            instance = new NumbersImpl();
        }
        return instance;
    }

    // Aufgabe 1
    @Override
    public long sum(int[] numbers) {
        long result = 0;
        for (int n : numbers) {
            result += n;
        }
        return result;
    }

    // Aufgabe 2
    @Override
    public long sum_positive_even_numbers(int[] numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("illegal argument: null");
        }

        long sum = 0;
        for (int n : numbers) {
            if (n > 0 && n % 2 == 0) {
                sum += n;
            }
        }
        return sum;
    }

    // Aufgabe 3–8: noch nicht implementiert
   @Override
    public long sum_recursive(int[] numbers, int i) {
        if (numbers == null) {
            throw new IllegalArgumentException("illegal argument: null");
    }

    // Abbruchbedingung: Wenn Index außerhalb des Arrays liegt
        if (i >= numbers.length) {
            return 0;
    }

    // Rekursion: aktuelles Element + Summe der restlichen
    return numbers[i] + sum_recursive(numbers, i + 1);
}

    @Override
    public int findFirst(int[] numbers, int x) {
        if (numbers == null) {
            throw new IllegalArgumentException("illegal argument: null");
    }

    for (int i = 0; i < numbers.length; i++) {
        if (numbers[i] == x) {
            return i; // erste gefundene Position
        }
    }

        return -1; // nicht gefunden
}

    @Override
    public int findLast(int[] numbers, int x) {
        if (numbers == null) {
            throw new IllegalArgumentException("illegal argument: null");
    }

    // Von hinten durchs Array laufen
    for (int i = numbers.length - 1; i >= 0; i--) {
        if (numbers[i] == x) {
            return i; // erstes von hinten gefundenes Element
        }
    }

    return -1; // nicht gefunden
}

    @Override
    public List<Integer> findAll(int[] numbers, int x) {
        if (numbers == null) {
            throw new IllegalArgumentException("illegal argument: null");
    }

    List<Integer> indices = new java.util.ArrayList<>();

    for (int i = 0; i < numbers.length; i++) {
        if (numbers[i] == x) {
            indices.add(i);
        }
    }

    return indices;
}

    @Override
    public Set<Pair> findSums(int[] numbers, int sum) {
        if (numbers == null) {
            throw new IllegalArgumentException("illegal argument: null");
    }

        Set<Pair> result = new java.util.HashSet<>();

        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == sum) {
                    int a = Math.min(numbers[i], numbers[j]);
                    int b = Math.max(numbers[i], numbers[j]);
                    result.add(new Pair(a, b));
            }
        }
    }

    return result;
}

    @Override
    public Set<Set<Integer>> findAllSums(int[] numbers, int sum) {
        if (numbers == null) {
            throw new IllegalArgumentException("illegal argument: null");
    }

        Set<Set<Integer>> result = new java.util.HashSet<>();
        backtrack(numbers, sum, 0, new java.util.HashSet<>(), result);
        return result;
}

    private void backtrack(int[] numbers, int remaining, int start, Set<Integer> current, Set<Set<Integer>> result) {
        if (remaining == 0) {
            result.add(new java.util.HashSet<>(current));
            return;
    }
        if (remaining < 0) {
            return;
    }
        for (int i = start; i < numbers.length; i++) {
            current.add(numbers[i]);
            backtrack(numbers, remaining - numbers[i], i + 1, current, result);
            current.remove(numbers[i]);
        }
    }
}