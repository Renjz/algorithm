package sort.strategy;

/**
 * @author renjingzhi
 */
public enum SortStrategy {

    /**
     * 冒泡排序
     */
    BUBBLE {
        /**
         * 1.每次比较两个元素
         * 2.如果前一个元素大，则两个元素交换位置
         * 3.后移一个位置，比较下面两个元素
         * 4.当碰到第一个排定的元素后，返回数组左端重新开始下一轮排序
         *
         * 不变性：out右侧数据的有序性
         *
         * 时间复杂度：O(N^2)
         * 比较次数：N*(N-1)/2≈N^2
         * 平均交换次数：N*(N-1)/4≈N^2/4
         */
        @Override
        public int[] sort(int... elements) {
            // out：外层已排定的元素的位置
            for (int out = elements.length - 1; out > 0; out--) {
                // in：内层前一个元素的位置
                for (int in = 0; in < out; in++) {
                    if (elements[in] > elements[in + 1]) {
                        // int temp = elements[in];
                        // elements[in] = elements[in + 1];
                        //  elements[in + 1] = temp;
                        elements[in] = elements[in] ^ elements[in + 1];
                        elements[in + 1] = elements[in] ^ elements[in + 1];
                        elements[in] = elements[in] ^ elements[in + 1];
                    }
                }
            }
            return elements;
        }
    },
    /**
     * 鸡尾酒排序
     */
    COCKTAIL {
        // 冒泡排序算法的改进
        // 最差时间复杂度 ---- O(n^2)
        // 最优时间复杂度 ---- 如果序列在一开始已经大部分排序过的话,会接近O(n)
        // 平均时间复杂度 ---- O(n^2)
        // 所需辅助空间 ------ O(1)
        // 稳定性 ------------ 稳定
        @Override
        public int[] sort(int... elements) {
            int left = 0; // 初始化边界
            int right = elements.length - 1;
            while (left < right) {
                for (int i = left; i < right; i++) // 前半轮,将最大元素放到后面
                {
                    if (elements[i] > elements[i + 1]) {
                        swap(elements, i, i + 1);
                    }
                }
                right--;
                for (int i = right; i > left; i--) // 后半轮,将最小元素放到前面
                {
                    if (elements[i - 1] > elements[i]) {
                        swap(elements, i - 1, i);
                    }
                }
                left++;
            }
            return elements;
        }

        void swap(int arr[], int i, int j) {
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
    },
    /**
     * 选择排序
     */
    SELECTION {
        /**
         * 1.进行选择排序就是把所有的元素扫描一遍，从中选出最小的元素
         * 2.最小的元素和最左的元素交换位置
         * 3.再次扫描时，从1号位置开始扫描最小的元素，和1号位置的元素交换，以此类推
         *
         * 不变性：下标小于或等于out的位置的数据总是有序的
         *
         * 时间复杂度：O(N^2)
         * 比较次数：N*(N-1)/2≈N^2
         * 平均交换次数：N
         */
        @Override
        public int[] sort(int... elements) {
            for (int out = 0; out < elements.length; out++) {
                int min = out;
                for (int in = out + 1; in < elements.length; in++) {
                    if (elements[in] < elements[min]) {
                        min = in;
                    }
                }

                if (min != out) {
                    int temp = elements[min];
                    elements[min] = elements[out];
                    elements[out] = temp;
                }
            }
            return elements;
        }
    },
    /**
     * 插入排序
     */
    INSERTION {
        /**
         * 不变性：局部有序
         *
         * 时间复杂度：O(N^2)
         * 比较次数：N*(N-1)/4≈N^2
         * 平均交换次数：N*(N-1)/4≈N^2
         */
        @Override
        public int[] sort(int... elements) {
            // i，要插入到有序集合的数的下标
            for (int i = 1, j; i < elements.length; i++) {
                int insert = elements[i];// 要插入的数
                // 假如insert比前面的值小，则将前面的值后移
                // j，后移时被覆盖的数的下标
                for (j = i; j > 0 && insert < elements[j - 1]; j--) {
                    elements[j] = elements[j - 1];
                }
                elements[j] = insert;
            }
            return elements;
        }
    },
    /**
     * 希尔排序(Knuth间隔序列)
     */
    KNUTH_SHELL {
        @Override
        public int[] sort(int... elements) {
            // 计算Knuth间隔
            int h = 1;// 间隔
            for (int count = elements.length / 3; h <= count; ) {
                h = h * 3 + 1;
            }

            // 希尔排序
            for (; h > 0; h = (h - 1) / 3) {
                for (int outer = h, inner; outer < elements.length; outer++) {
                    int temp = elements[outer];
                    for (inner = outer; inner > h - 1 && elements[inner - h] >= temp; inner -= h) {
                        elements[inner] = elements[inner - h];
                    }
                    elements[inner] = temp;
                }
            }
            return elements;
        }
    },
    /**
     * 归并排序
     */
    RESURSIVE_MERGE {
        @Override
        public int[] sort(int... elements) {
            int[] temp = new int[elements.length];// 在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
            sort(elements, 0, elements.length - 1, temp);
            return temp;
        }

        private void sort(int[] arr, int left, int right, int[] temp) {
            if (left < right) {
                int mid = (left + right) / 2;
                sort(arr, left, mid, temp);// 左边归并排序，使得左子序列有序
                sort(arr, mid + 1, right, temp);// 右边归并排序，使得右子序列有序
                merge(arr, left, mid, right, temp);// 将两个有序子数组合并操作
            }
        }

        private void merge(int[] arr, int left, int mid, int right, int[] temp) {
            int i = left;// 左序列指针
            int j = mid + 1;// 右序列指针
            int t = 0;// 临时数组指针
            while (i <= mid && j <= right) {
                if (arr[i] <= arr[j]) {
                    temp[t++] = arr[i++];
                } else {
                    temp[t++] = arr[j++];
                }
            }
            while (i <= mid) {// 将左边剩余元素填充进temp中
                temp[t++] = arr[i++];
            }
            while (j <= right) {// 将右序列剩余元素填充进temp中
                temp[t++] = arr[j++];
            }
            t = 0;
            // 将temp中的元素全部拷贝到原数组中
            while (left <= right) {
                arr[left++] = temp[t++];
            }
        }
    },
    /**
     * 快速排序
     */
    QUICK {
        @Override
        public int[] sort(int... elements) {
            sort(elements, 0, elements.length - 1);
            return elements;
        }

        public void sort(int[] a, int low, int high) {
            int start = low;
            int end = high;
            int key = a[low];

            while (end > start) {
                //从后往前比较
                while (end > start && a[end] >= key)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                    end--;
                if (a[end] <= key) {
                    int temp = a[end];
                    a[end] = a[start];
                    a[start] = temp;
                }
                //从前往后比较
                while (end > start && a[start] <= key)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                    start++;
                if (a[start] >= key) {
                    int temp = a[start];
                    a[start] = a[end];
                    a[end] = temp;
                }
                //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
            }
            //递归
            if (start > low) sort(a, low, start - 1);//左边序列。第一个索引位置到关键值索引-1
            if (end < high) sort(a, end + 1, high);//右边序列。从关键值索引+1到最后一个
        }
    };

    /**
     * 根据指定策略从小到大排序
     *
     * @param elements
     * @return
     */
    public abstract int[] sort(int... elements);
}
