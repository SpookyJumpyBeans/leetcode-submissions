// 1899. Merge Triplets to Form Target Triplet
// https://leetcode.com/problems/merge-triplets-to-form-target-triplet/
// Medium | java | Accepted on NeetCode 2026-09-04

class Solution {
    public boolean mergeTriplets(int[][] triplets, int[] target) {
        ArrayList<int[]> res = new ArrayList<>();
        for(int i = 0; i<triplets.length; i++)
        {
            if(!(triplets[i][0]>target[0] || triplets[i][1]>target[1] || triplets[i][2]>target[2]))
            {
                res.add(triplets[i]);
            }
        }
        boolean f1 = false;
        boolean f2 = false;
        boolean f3 = false;
        for(int j = 0; j<res.size(); j++)
        {
            if(target[0]==res.get(j)[0])
            {
                f1 = true;
            }
            if(target[1]==res.get(j)[1])
            {
                f2 = true;
            }
            if(target[2]==res.get(j)[2])
            {
                f3 = true;
            }
        }
        return f1&&f2&&f3;
    }
}
