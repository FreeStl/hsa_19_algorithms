package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.fill;

public class CountingSort {

    public void sort(int[] in) {
        int max = 0;
        for (var n : in)
            if (n > max)
                max = n;

        var count = new int[max + 1];
        fill(count, 0);

        for (int n : in)
            ++count[n];

        for (int i = 1; i < count.length; i++)
            count[i] += count[i - 1];

        var out = new int[in.length];

        for (int i = in.length - 1; i >= 0; i--) {
            out[count[in[i]] - 1] = in[i];
            --count[in[i]];
        }

        for (int i = 0; i < in.length; ++i)
            in[i] = out[i];
    }
}
