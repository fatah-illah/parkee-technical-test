import java.util.HashMap;

public class PairSum {
    public static int countPairs(int[] A, int k) {
        HashMap<Integer, Integer> freqMap = new HashMap<>(); int count = 0;

        for (int num : A) {
            int complement = k - num;
            if (freqMap.containsKey(complement)) {
                count += freqMap.get(complement); }
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1); }
        return count; }
    public static void main(String[] args) {
        int[] A = {1, 5, 7, -1, 5};
        int k = 6;
        System.out.println(countPairs(A, k)); // Output: 2
    } }
