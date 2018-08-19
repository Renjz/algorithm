package sort;

import sort.strategy.SortStrategy;

import java.util.Random;

/**
 * @author renjingzhi
 */
public class ArraySorterTest {
    public static void main(String[] args) {
        sortTest(1000, SortStrategy.values());
    }

    public static void sortTest(int count, SortStrategy... sortStrategy) {
        Random random = new Random();
        int[] elements = new int[count];
        for (int i = 0; i < count; i++) {
            int element = random.nextInt(10000) + 1;
            elements[i] = element;
        }
        for (SortStrategy strategy : sortStrategy) {
            ArraySorter sorter = ArraySorterWrapper.getInstance(strategy, elements);
            sorter.sort();
        }
    }
}
