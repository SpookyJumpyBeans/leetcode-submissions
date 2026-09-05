// 701. Insert into a Binary Search Tree
// https://leetcode.com/problems/insert-into-a-binary-search-tree/
// Medium | C++ | Accepted on NeetCode 2026-09-04

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
    TreeNode* insertIntoBST(TreeNode* root, int val) {
        if(root==nullptr)
        {
            root = new TreeNode(val);
            return root;
        }
        TreeNode* temp = root;
        recurse(temp, val);
        return temp;
    }

    void recurse(TreeNode* temp, int v)
    {
        if(v>temp->val && temp->right == nullptr)
        {
            temp->right = new TreeNode(v);
            return;
        }
        if(v<temp->val && temp->left == nullptr)
        {
            temp->left = new TreeNode(v);
            return;
        }
        else 
        {
            if(v>temp->val)
            {
                recurse(temp->right, v);
            }
            else
            {
                recurse(temp->left, v);
            }
        }
    }
};
