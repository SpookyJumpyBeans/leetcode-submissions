// 1146. Snapshot Array
// https://leetcode.com/problems/snapshot-array/
// Medium | Java | Accepted 2026-08-11
// Runtime 52 ms | Memory 129.9 MB

class SnapshotArray {
    private class Node {
        int snapId;
        int val;
        Node(int id, int v)
        {
            snapId = id;
            val = v;
        }
    }
    List<Node>[] snapshots; //Contains the nodes of the changes to the array 
    int snapshot;
    public SnapshotArray(int length) {
        snapshots = new ArrayList[length]; //Make an array of ArrayLists containing the changes
        snapshot = 0;
        for(int i = 0; i<length; i++)
        {
            snapshots[i] = new ArrayList<>();
            snapshots[i].add(new Node(0, 0));
        }
    }
    
    public void set(int index, int val) {
        List<Node> list = snapshots[index]; //First get the list of changes for the specific index
        Node last = list.get(list.size()-1); //Get the most recent change
        if(last.snapId==snapshot) //This checks if the next snapshot is going to be this current Node, meaning we need to update the values of the node
        {
            last.val = val;
        }
        else
        {
            snapshots[index].add(new Node(snapshot, val)); //If the snapshot values are different, this means we called snap and took a snapshot of the current node and need to add a new node for the next snapshot
        } 
    }
    
    public int snap() {
        return snapshot++;
    }
    
    public int get(int index, int snap_id) {
        int l = 0; //Uses binary search to find the snap_id 
        int r = snapshots[index].size()-1;
        int ans = 0;
        List<Node> temp = snapshots[index];
        while(l<=r)
        {
            int mid = (l+r)/2;
            if(temp.get(mid).snapId>snap_id)
            {
                r = mid-1;
            }
            else if(temp.get(mid).snapId<=snap_id)
            {
                l = mid+1;
                ans = mid; //If the snapId at mid is less than or equal to, then that means it could be mid
            }
        }
        return temp.get(ans).val;
    }
}

/**
 * Your SnapshotArray object will be instantiated and called as such:
 * SnapshotArray obj = new SnapshotArray(length);
 * obj.set(index,val);
 * int param_2 = obj.snap();
 * int param_3 = obj.get(index,snap_id);
 */
