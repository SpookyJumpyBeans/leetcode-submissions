// 658. Find K Closest Elements
// https://leetcode.com/problems/find-k-closest-elements/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    vector<int> findClosestElements(vector<int>& arr, int k, int x) {
        arr.push_back(x);
        sort(arr.begin(), arr.end());
        int indOf = -1;
        for(int i = 0; i<arr.size(); i++)
        {
            if(arr[i]==x)
            {
                indOf = i;
            }
        }
        vector<int> ans;
        int p1 = indOf-1;
        int p2 = indOf+1;
        while(k-->0)
        {
            if(p1>=0 && p2<arr.size())
            {
               if(abs(arr[p1]-x)>abs(arr[p2]-x))
               {
                ans.push_back(arr[p2]);
                p2++;
               }
               else if(abs(arr[p1]-x)<=abs(arr[p2]-x))
               {
                ans.push_back(arr[p1]);
                p1--;
               }
            }
            else if(p1>=0 && p2>=arr.size())
            {
                ans.push_back(arr[p1]);
                p1--;
            }
            else
            {
                ans.push_back(arr[p2]);
                p2++;
            }
        }
        sort(ans.begin(), ans.end());
        return ans;
    }
};
