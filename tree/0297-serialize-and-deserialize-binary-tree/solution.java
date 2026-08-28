// 297. Serialize and Deserialize Binary Tree
// https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
// Hard | Java | Accepted 2025-12-02
// Runtime 72 ms | Memory 48.1 MB

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if(root == null)
        {
            return "[]";
        }
        String serial = "[";
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int count = 0;
        while(!queue.isEmpty()&&root!=null)
        {
            int size = queue.size();
            for(int i = 0; i<size; i++)
            {
                TreeNode temp = queue.poll();    
                   if(temp==null)
            {
                serial+="null,";
            }        
            if(temp!=null)
            {
                serial+=temp.val;
                serial+=",";
                queue.add(temp.left);
                queue.add(temp.right);
            }
            }
            count++;
        }
        serial = serial.substring(0, serial.length()-1);
        serial+="]";
        return serial;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data.length()==2)
        {
            return null;
        }
        data = data.substring(1, data.length()-1);
        String[] arr = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(arr[0]));
        queue.add(root);
        int i = 1;
        while(i<arr.length && !queue.isEmpty())
        {
            TreeNode temp = queue.poll();
            if(!arr[i].equals("null"))
            {
                temp.left = new TreeNode(Integer.parseInt(arr[i]));
                queue.add(temp.left);
            }
            if(!arr[i+1].equals("null"))
            {
                temp.right = new TreeNode(Integer.parseInt(arr[i+1]));
                queue.add(temp.right);
            }
            i+=2;
        }
        return root;
    }

}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
