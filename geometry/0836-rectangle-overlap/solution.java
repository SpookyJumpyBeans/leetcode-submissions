// 836. Rectangle Overlap
// https://leetcode.com/problems/rectangle-overlap/
// Easy | Java | Accepted 2026-09-17
// Runtime 0 ms | Memory 43 MB

class Solution {
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        //Easier to check for when the rectangles don't overlap
        if((rec1[0] <= rec2[0] && rec1[0] <= rec2[2]) && (rec1[2] <= rec2[0]  && rec1[2] <= rec2[2])) //If rec1 is completely to the left of rec2
        {
            return false;
        }
        if((rec1[1] <= rec2[1] && rec1[1] <= rec2[3]) && (rec1[3] <= rec2[1]  && rec1[3] <= rec2[3])) //If rec1 is on top of rec2
        {
            return false;
        }
        if((rec1[0] >= rec2[0] && rec1[0] >= rec2[2]) && (rec1[2] >= rec2[0]  && rec1[2] >= rec2[2])) //If rec1 is on the right of rec2
        {
            return false;
        }
        if((rec1[1] >= rec2[1] && rec1[1] >= rec2[3]) && (rec1[3] >= rec2[1]  && rec1[3] >= rec2[3])) //If rec1 is below rec2
        {
            return false;
        }
        return true;
    }
}
