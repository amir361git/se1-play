package streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Function;

public class StreamsImpl implements Streams {

    private static final Random RANDOM = new Random();

    @Override
    public Stream<Integer> tenRandomNumbers() {
        return RANDOM.ints(0, 1000)
                .boxed()
                .limit(10);
    }

    @Override
    public Stream<Integer> tenEvenRandomNumbers() {
        return RANDOM.ints(0, 1000)
                .filter(n -> n % 2 == 0)
                .boxed()
                .limit(10);
    }

    @Override
    public Stream<Integer> tenSortedEvenRandomNumbers() {
        return tenEvenRandomNumbers()
                .sorted();
    }

    private boolean isThreeDigitPrime(int n) {
        if (n < 100 || n > 999) return false;
        if (n % 2 == 0) return n == 2;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Override
    public List<Integer> filteredNumbers(String filter, int limit) {
        if (filter == null || filter.isEmpty() || !filterFunctions.containsKey(filter)) {
            throw new IllegalArgumentException(
                    "filter null, empty or unknown: \"" + filter + "\"");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("negative limit: " + limit);
        }

        Function<Integer, Boolean> func = filterFunctions.get(filter);

        return RANDOM.ints(0, 1000)
                .boxed()
                .filter(n ->
                        filter.equals("prime3")
                                ? isThreeDigitPrime(n)
                                : func.apply(n)
                )
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> filteredNames(List<String> names, String regex) {
        if (names == null || regex == null) {
            throw new IllegalArgumentException("names or regex argument is null.");
        }
        return names.stream()
                .filter(n -> n.matches(regex))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> sortedNames(List<String> names, int limit) {
        if (names == null) {
            throw new IllegalArgumentException("names argument is null.");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("limit argument is negative: " + limit + ".");
        }

        return names.stream()
                .sorted()
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> sortedNamesByLength(List<String> names) {
        if (names == null) {
            throw new IllegalArgumentException("names argument is null.");
        }

        return names.stream()
                .sorted(Comparator.comparing(String::length)
                        .thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public long calculateOrderValue(List<Order> orders) {
        if (orders == null) {
            throw new IllegalArgumentException("orders argument is null.");
        }

        return orders.stream()
                .mapToLong(o -> o.units() * o.unitPrice())
                .sum();
    }

    @Override
    public List<Order> sortOrdersByValue(List<Order> orders) {
        if (orders == null) {
            throw new IllegalArgumentException("orders argument is null.");
        }

        return orders.stream()
                .sorted(Comparator.comparingLong(
                        o -> -(o.units() * o.unitPrice())))
                .collect(Collectors.toList());
    }

    public static Streams getInstance() {
        return new StreamsImpl();
    }
}