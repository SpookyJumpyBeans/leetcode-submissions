// 112. Path Sum
// https://leetcode.com/problems/path-sum/
// Easy | C++ | Accepted 2026-08-05
// Runtime 0 ms | Memory 21.6 MB

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
    bool ans = false;
    int targ;
    bool hasPathSum(TreeNode* root, int targetSum) {
        if(root==nullptr)
        {
            return false;
        }
        targ = targetSum;
        recurse(root, root->val);
        return ans;
    }

    void recurse(TreeNode* root, int sum)
    {
        if(sum==targ && root->left==nullptr && sum==targ && root->right == nullptr)
        {
            ans = true; 
            return;
        }
        if(root->left!=nullptr)
        {
        recurse(root->left, sum+root->left->val);
        }
        if(root->right!=nullptr)
        {
        recurse(root->right, sum+root->right->val);
        }
    }
};
