// 2096. Step-By-Step Directions From a Binary Tree Node to Another
// https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/
// Medium | C++ | Accepted 2026-08-07
// Runtime 20 ms | Memory 211.5 MB

/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
 *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
 *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
 * };
 */
class Solution {
public:
    vector<string> paths;
    string getDirections(TreeNode* root, int startValue, int destValue) {
        string p1 = "", p2 = "";
        rec(root, startValue, p1);
        rec(root, destValue, p2);
        int i = 0;
        while(i<p1.size() && i<p2.size() && p1[i]==p2[i])
        {
           i++;
        }
        string step(p1.size()-i, 'U');
        return step+p2.substr(i);
    }

    bool rec(TreeNode* root, int targ, string& path)
    {
        if(root==nullptr)
        {
            return false;
        }
        if(root->val==targ)
        {
            return true;
        }
        path+="L";
        if(rec(root->left, targ, path))
        {
            return true;
        }
        path.pop_back();
        path+="R";
        if(rec(root->right, targ, path))
        {
            return true;
        }
        path.pop_back();
        return false;
    }
};
