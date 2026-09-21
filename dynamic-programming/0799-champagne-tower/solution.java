// 799. Champagne Tower
// https://leetcode.com/problems/champagne-tower/
// Medium | Java | Accepted 2026-09-19
// Runtime 5 ms | Memory 47.2 MB

class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[][] fullness = new double[100][100];
        //Simulate the entire pour all at once
        fullness[0][0] = poured; //Set the first glass to contain all the pours
        for(int i = 0; i<100; i++) //Loop through all 100 rows
        {
            for(int j = 0; j<=i; j++) //Loop through all glasses in every row
            {
                if(i+1<100 && fullness[i][j]>1.0) //If this current glass has excess liquid (>1.0), we want to add half of the excess to the glasses in the row below on the right/left side of this current glass 
                {
                    double excess = fullness[i][j]-1; //Get the excess
                    fullness[i][j] = 1.0; //Set the current glass to 1.0 since we removed the excess
                    fullness[i+1][j] += excess/2.0; //Add half of the excess to the glasses below on the right/left hand side of this glass
                    fullness[i+1][j+1] += excess/2.0;
                }
            }
        }
        return Math.min(1.0, fullness[query_row][query_glass]); //If the last row's glasses have excess, we want to only return 1.0 (since a glass can only hold at most 1.0 cup), so return Math.min(1.0, or the fullness at the specific row and glass)
    }
}
