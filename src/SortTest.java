import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by renjz on 2018/4/14.
 */
public class SortTest {
    public static void main(String[] args) {

//        CyclicBarrier cb = new CyclicBarrier(3, () -> {
//        });
//        int[] a = {6, 4, 1, 5, 9};
//        System.out.println(Arrays.toString(a));
//        selectSort(a);
//        System.out.println(Arrays.toString(a));
        int s = 1;
        change(s);
        System.out.println(s);
    }

    public static void change(int s) {
        s = 4;
    }

    public static void insertionSort(int... elements) {
        for (int i = 1; i < elements.length; i++) {
            int insertion = elements[i], j;
            for (j = i; j > 0 && insertion < elements[j - 1]; j--) {
                elements[j] = elements[j - 1];
            }
            elements[j] = insertion;
        }
    }

    public static void selectSort(int... elements) {
        for (int i = 0; i < elements.length; i++) {
            int min = i;
            for (int j = i + 1; j < elements.length; j++) {
                if (elements[min] > elements[j]) {
                    min = j;
                }
            }
            if (min != i) {
                elements[min] = elements[min] ^ elements[i];
                elements[i] = elements[min] ^ elements[i];
                elements[min] = elements[min] ^ elements[i];
            }
        }
    }
}
