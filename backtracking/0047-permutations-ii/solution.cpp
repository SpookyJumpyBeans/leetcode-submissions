// 47. Permutations II
// https://leetcode.com/problems/permutations-ii/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    vector<vector<int>> ans;
    vector<int> num;
    vector<bool> visited;
    vector<vector<int>> permuteUnique(vector<int>& nums) {
        num = nums;
        visited = vector<bool>(nums.size(), false);
        sort(num.begin(), num.end());
        vector<int> temp;
        recurse(temp);
        return ans;
    }

    void recurse(vector<int> temp)
    {
        if(temp.size()==num.size())
        {
            ans.push_back(temp);
            return;
        }
        for(int i = 0; i<num.size(); i++)
        {  
            if(i>0 && num[i]==num[i-1] && !visited[i-1])
            {
                continue;
            }
            if(!visited[i])
            {
                visited[i] = true;
                temp.push_back(num[i]);
                recurse(temp);
                visited[i] = false;
                temp.pop_back();
            }
        }
    }
};
