/**
 * Created by yexiaoxin on 2017/8/30.
 */
public class GuiBingSort {
    public static void sort(int start, int end, int a[]) {
        if (start >= end || start < 0 || end > a.length || end - start < 1) return;
        int x = (start + end) / 2;

        sort(start, x, a);
        sort(x + 1, end, a);
        int m[] = new int[x - start + 1];
        int n[] = new int[end - x];
        for (int i = 0; i < x - start + 1; i++) {
            m[i] = a[i + start];
        }
        for (int j = 0; j < end - x; j++) {
            n[j] = a[x + j + 1];
        }
        int i = 0;
        int j = 0;
        for (int z = 0; z < end - start + 1; z++) {
            if (i < m.length && j < n.length && m[i] < n[j]) {
                a[start + z] = m[i];
                i++;
            } else if (i < m.length && j < n.length && m[i] >= n[j]) {
                a[start + z] = n[j];
                j++;
            } else if (j >= n.length && i < m.length) {
                a[start + z] = m[i];
                i++;
            } else if (j < n.length && i >= m.length) {
                a[start + z] = n[j];
                j++;
            }
        }


    }

    public static void test(int aa[]) {
        aa[1] = 3;
    }

    public static void main(String args[]) {
        int aa[] = {2, 1, 3, 6, 8, 5,4,7,9};
        sort(0, 8, aa);

        for (int i = 0; i < aa.length; i++) {
            System.out.println(aa[i]);
        }
    }
}
