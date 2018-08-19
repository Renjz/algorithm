package sort;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import sort.strategy.SortStrategy;

/**
 * 具有排序功能的数组包装器
 *
 * @author renjingzhi
 */
public class ArraySorterWrapper implements ArraySorter {

    protected int size;
    protected int[] elements;
    protected SortStrategy strategy;

    private ArraySorterWrapper(int size) {
        this.elements = new int[size];
    }

    private ArraySorterWrapper(int... elements) {
        this.elements = elements;
        this.size = elements.length;
    }

    private ArraySorterWrapper(SortStrategy sortStrategy, int size) {
        this.strategy = sortStrategy;
        this.elements = new int[size];
    }

    private ArraySorterWrapper(SortStrategy sortStrategy, int[] elements) {
        this.strategy = sortStrategy;
        this.elements = elements.clone();
        this.size = elements.length;
    }

    public void setElements(int... elements) {
        this.elements = elements.clone();
    }

    public void insert(int element) {
        this.elements[this.size] = element;
        this.size++;
    }

    public SortStrategy getStrategy() {
        return this.strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public int[] sort() {
        return this.strategy.sort(this.elements);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.elements);
    }

    public static ArraySorter getInstance(SortStrategy strategy, int[] elements) {
        final ArraySorter target = new ArraySorterWrapper(strategy, elements);
        InvocationHandler handler = genHandler(target);
        return (ArraySorter) Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                new Class[]{ArraySorter.class}, handler);
    }

    public static ArraySorter getInstance(SortStrategy strategy, int size) {
        final ArraySorter target = new ArraySorterWrapper(strategy, size);
        InvocationHandler handler = genHandler(target);
        return (ArraySorter) Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                new Class[]{ArraySorter.class}, handler);
    }

    private static InvocationHandler genHandler(ArraySorter target) {
        return new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("sort")) {
                    System.out.println("************ " + target.getStrategy().name() + " SORT ************");
                    System.out.println(target.toString());
                    long t0 = System.nanoTime();
                    Object val = method.invoke(target, args);
                    float time = (System.nanoTime() - t0) / 1000f;
                    System.out.println(target.toString());
                    System.out.printf("------------ %.3fms ------------\n\n", time);
                    return val;
                } else {
                    return method.invoke(target, args);
                }
            }
        };
    }
}
