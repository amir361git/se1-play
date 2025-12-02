package streams;

import java.util.stream.Stream;
import java.util.List;
import java.util.Random;
import java.util.Comparator;

public class StreamsImpl implements Streams {

    private static final Streams INSTANCE = new StreamsImpl();

    private StreamsImpl() {}

    public static Streams getInstance() {
        return INSTANCE;
    }

    @Override
    public Stream<Integer> tenRandomNumbers() {
        Random random = new Random();
        return random.ints(10, 0, 1000).boxed();
    }

    @Override
    public Stream<Integer> tenEvenRandomNumbers() {
        return new Random().ints(10, 0, 1000)
                       .filter(n -> n % 2 == 0)
                       .boxed();
}

    @Override
    public Stream<Integer> tenSortedEvenRandomNumbers() {
        return new Random().ints(10, 0, 1000)
                       .filter(n -> n % 2 == 0)
                       .sorted()
                       .boxed();
}

    @Override
    public List<Integer> filteredNumbers(String filter, int limit) {
        return new Random().ints(0, 1000)
            .filter(n -> filterFunctions.get(filter).apply(n))
            .limit(limit)
            .boxed()
            .toList(); 
}

    @Override
    public List<String> filteredNames(List<String> names, String regex) {
        return names.stream()
            .filter(n -> n.matches(regex))
            .toList();
}

    @Override
    public List<String> sortedNames(List<String> names, int limit) {
        return names.stream()
            .sorted()
            .limit(limit)
            .toList();
}

    @Override
    public List<String> sortedNamesByLength(List<String> names) {
        return names.stream()
            .sorted(
                Comparator.comparingInt(String::length)
                          .thenComparing(Comparator.naturalOrder())
            )
            .toList();
}

    @Override
public long calculateOrderValue(List<Order> orders) {
    return orders.stream()
        .mapToLong((Order o) -> o.units() * o.unitPrice())
        .sum();
}

@Override
    public List<Order> sortOrdersByValue(List<Order> orders) {
        return orders.stream()
            .sorted(Comparator.comparingLong((Order o) -> o.units() * o.unitPrice())
                    .reversed())
            .toList();
    }
}