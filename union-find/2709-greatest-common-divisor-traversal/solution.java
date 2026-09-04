// 2709. Greatest Common Divisor Traversal
// https://leetcode.com/problems/greatest-common-divisor-traversal/
// Hard | Java | Accepted 2026-09-04
// Runtime 178 ms | Memory 90.8 MB

class Solution {
    int[] parents;
    int[] sizes;
    int groups;
    public boolean canTraverseAllPairs(int[] nums) {
        if(nums.length==1)
        {
            return true;
        }
        parents = new int[nums.length+100001];
        sizes = new int[nums.length+100001];
        for(int i = 0; i<nums.length; i++)
        {
            parents[nums[i]] = nums[i];
            sizes[nums[i]] = 1;
        }
        for(int i = 0; i<nums.length; i++)
        {
           if(nums[i]==1)
           {
            return false;
           }
           Set<Integer> primeFactors = findPrimeFactors(nums[i]);
           for(int prime : primeFactors)
           {
                if(parents[prime]==0)
                {
                    parents[prime] = prime;
                }
                if(sizes[prime]==0)
                {
                    sizes[prime] = 1;
                }
                union(nums[i], prime);
           }
        }
        Integer parent = null;
        for(int i : nums)
        {
            if(parent==null)
            {
                parent = find(parents[i]);
            }
            else
            {
                if(find(parents[i])!=parent)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public int find(int node)
    {
        if(parents[node]==node)
        {
            return node;
        }
        parents[node] = find(parents[node]);
        return parents[node];
    }

    public void union(int node1, int node2)
    {
        int par1 = find(node1);
        int par2 = find(node2);
        if(par1==par2)
        {
            return;
        }
        if(sizes[par1]>sizes[par2])
        {
            int temp = par1;
            par1 = par2;
            par2 = temp;
        }
        parents[par1] = par2;
        sizes[par2]+=sizes[par1];
    }

    public Set<Integer> findPrimeFactors(int n) {
        Set<Integer> list = new HashSet<>();
        // 1. Print the number of 2s that divide n
        while (n % 2 == 0) {
            list.add(2);
            n /= 2;
        }

        // 2. n must be odd at this point. So we can skip one element (Note i = i + 2)
        // We only need to check up to the square root of n for efficiency
        for (int i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                list.add(i);
                n /= i;
            }
        }

        // 3. This condition is to handle the case when n is a prime number 
        // greater than 2 at the end of the loop
        if (n > 2) {
            list.add(n);
        }   
        return list;
    }
}
