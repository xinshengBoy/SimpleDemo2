package com.yks.simpledemo2.tools;

/**
 * 描述：二分查找  数组数字必须是有序的
 * 作者：zzh
 * time:2019/07/04
 */
public class HalfFind {

    /**
     * 描述：递归查找key值所在的位置
     * 作者：zzh
     * @param array 数组
     * @param key 关键字
     * @param low 开始处
     * @param high 结束处
     * @return 找到的位置
     */
    public int recursionBinarySearch(int[] array,int key,int low,int high){
        if (key < array[low] || key > array[high] || low > high){
            return -1;
        }
        int middle=(low&high) + ((low^high) >> 1);
        if (array[middle] > key){
            //比关键字大则在左区域
            return recursionBinarySearch(array,key,low,middle-1);
        }else if (array[middle] < key){
            //比关键字小则在右区域
            return recursionBinarySearch(array,key,middle+1,high);
        }else {
            return middle;
        }
    }

    /**
     * 描述：while循环查找key所在的位置
     * 作者：zzh
     * @param array 数组
     * @param key  关键字
     * @return 位置
     */
    public int commonBinarySearch(int[]array,int key){
        int low = 0;
        int high = array.length - 1;
        int middle = 0;
        if (key < array[low] || key > array[high] || low > high){
            return -1;
        }

        while (low <= high){
            middle = (low + high) / 2;
            if (array[middle] > key){
                //比关键字大则在左区域
                high = middle - 1;
            }else if (array[middle] < key){
                low = middle + 1;
            }else {
                return middle;
            }
        }
        return -1;//最后仍未找到，返回-1
    }
}
