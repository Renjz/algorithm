package sort;

import sort.strategy.SortStrategy;

/**
 * 具有排序功能的数组包装器
 * 
 * @author renjingzhi
 *
 */
public interface ArraySorter {

    public void setStrategy(SortStrategy strategy);

    public SortStrategy getStrategy();

    public void setElements(int... elements);

    public void insert(int element);

    public int[] sort();
}
