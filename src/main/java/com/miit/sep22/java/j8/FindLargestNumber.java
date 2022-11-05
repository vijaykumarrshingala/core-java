package com.miit.sep22.java.j8;

public class FindLargestNumber {

    static int[] nums = {2, 200, 4, 3, 500, 123, 900};

    public static void main(String[] args) {

        //int highestNum = 0;

        findLargestNumber_Solution1();

        findLargestNumber_Solution2_Sorting();

    }

    private static void findLargestNumber_Solution2_Sorting() {
        int tmp = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if(nums[i] > nums[j]) {
                    tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                }
            }
        }
        System.out.println("Solution-2 = "+nums[nums.length-1]);
    }

    private static void findLargestNumber_Solution1() {
        int currentNumber = 0;
        for (int i = 0; i < nums.length; i++) {
            int preNumber = 0;
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    preNumber = nums[i];
                } else {
                    preNumber = nums[j];
                }
                if(currentNumber > preNumber) {
                    preNumber = currentNumber;
                }
            }
            currentNumber = preNumber;
        }
        System.out.println("Solution-1 = "+currentNumber);
    }
}
