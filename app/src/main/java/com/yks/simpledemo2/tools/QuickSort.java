package com.yks.simpledemo2.tools;

/**
 * 描述：冒泡排序快速算法
 * 作者：zzh
 * time:2019/07/03
 */
public class QuickSort {
    public String doQuickSort(int[] array){
        for (int i=0;i<array.length-1;i++){
            boolean flag = true;
            for (int j=0;j<array.length-1-i;j++){
                if (array[j] > array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                    flag = false;
                }
            }
            if (flag) break;
        }
        String res = "";
        for (int i=0;i<array.length;i++){
            res += array[i] + ",";
        }
        return res.substring(0,res.length()-1);
    }
}
