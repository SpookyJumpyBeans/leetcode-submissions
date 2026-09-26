// 2286. Booking Concert Tickets in Groups
// https://leetcode.com/problems/booking-concert-tickets-in-groups/
// Hard | Java | Accepted 2026-09-25
// Runtime 149 ms | Memory 127.4 MB

class BookMyShow { 
 
    long[] treeSum; 
    int[] treeMax; 
    int seatsPerRow; 
    int rows; 

    //Segment Tree Method
    //Instead of making the segment tree out of nodes, use two arrays that store info for each index (node)
    //One holds the sum of all the available seats within a row range (This solves the scatter case, where we are trying to see if the total sum of all available seats from row 0 to row maxRow is >= the number of people)
    //One holds the maximum amount of seats available in one row within a row range (This solves the gather case, because we want to fit everyone on one row, so we check if the max amount of seats for one row from row 0 to row maxRow is >= the number of people)
    public BookMyShow(int n, int m) { 
        treeSum = new long[4*n]; //Make the tree for the sums of the possible row ranges
        treeMax = new int[4*n]; //Make the tree for the max rows in one row out of the possible row ranges
        seatsPerRow = m; //This is essentially the columns for the rows (how many seats are in every row)
        rows = n; 
        buildTree(0, 0, n-1); //Start by building the segment tree
    } 
     
    public void buildTree(int root, int l, int r) 
    { 
        treeSum[root] = (long)(r-l+1)*seatsPerRow; //Since every row will be empty at the start, we initiate the node at root to be the left and right indices (which represent the lower and upper row bounds) subtracted times the number of seats per row
        //This gives the total number of seats from row l to row r
        treeMax[root] = seatsPerRow; //The max for every range will be the total number of seats in a row
        if(l==r) //If we reach a leaf node (a single row), return
        { 
            return; 
        } 
        int mid = l + (r-l)/2; //Else, keep dividing the current row segment into halves
        buildTree(2*root+1, l, mid); //Left rows
        buildTree(2*root+2, mid+1, r); //Right rows
    } 
 
    public int getFirstRow(int root, int l, int r, int k, int maxRow) //Get first row method returns the index of the first row that has a total number of available seats >= k (the number of people) 
    //Root represents the node we're currently at, l and r represent the range of rows that node represents
    //maxRow is the query boundary
    //This solves the gather case
    { 
        if(treeMax[root]<k) //If the range has less seats than people, immediately return -1
        { 
            return -1; 
        } 
        if(l>maxRow) //If the starting row is already greater than the maxRow we can go to, we return -1 since it's out of bounds
        { 
            return -1; 
        } 
        if(treeMax[root]>=k && l==r) //If we're at a leaf node (a single row) and the row has >= k seats, return the row's index
        { 
            return l; 
        } 
        int mid = l + (r-l)/2;  //Get the middle of this range
        int left = getFirstRow(2*root+1, l, mid, k, maxRow); //Search in the left half
        if(left!=-1) //If successful, immediately return the left halve's answer, since we want the row with the smallest number
        { 
            return left; 
        } 
        int right = getFirstRow(2*root+2, mid+1, r, k, maxRow); //Look in right half
        return right; 
    } 
 
    public long getSum(int root, int l, int r, int maxRow) //Segment-tree range query pattern 
    //This sums up all the available seats from rows l to r
    { 
        if(l>maxRow) //If l is greater than the max row, immediately return 0 since this is out of bounds
        { 
            return 0;  
        } 
        if(r<=maxRow) //If the upper bound is <= maxRow, we don't need to keep breaking the interval in half, we can just return the value in treeSum for the total available seats from row 0 to row r
        //This prevents redundant recursive calls and prunes early
        { 
            return treeSum[root]; 
        } 
        int mid = l + (r-l)/2; //Find the divide point
        long left = getSum(2*root+1, l, mid, maxRow); //Get the sums of the left/right havles individually
        long right = getSum(2*root+2, mid+1, r, maxRow); 
        return left+right; //Return the sum of them
    } 

    public int getRowSeats(int root, int l, int r, int targ) //Gets the number of available seats in a specific target row
    {
        if(l==r) //If this is a leaf node (a single row), it's guaranteed this is going to be the target row we want
        {
            return treeMax[root];
        }
        int mid = l + (r-l)/2;
        if(targ<=mid) //This condition checks if the index of the target row we want is <= the middle split point
        //This ensures we look in only the subtree that has the target value
        //Rows <= mid are in the left subtree and rows > mid are in the right
        {
            return getRowSeats(2*root+1, l, mid, targ); //Look in left subtree
        }
        return getRowSeats(2*root+2, mid+1, r, targ); //Look in right subtree
    }
 
    public void updateTree(int root, int l, int r, int targ, int k) //This is for after we find a successful seating arrangement for gather/scatter and we have to update the rows after filling them with people
    //We use target because for scatter, we have to fill the rows up row by row, so just call updateTree multiple times for scatter
    { 
        if(l==r) //If l == r, we are at the target row
        { 
            treeSum[root] = treeSum[root]-k;  //Update the row by subtracting k from the number of seats available (k represents the demand for seats for this row)
            //For gather, k will equal the original k (total number of people), but for scatter, k will be the number of seats available for the row since there might not be enough seats to fit everyone on one row
            treeMax[root] = treeMax[root]-k;    
            return; 
        } 
        int mid = l + (r-l)/2; 
        if(targ<=mid) //Look in the correct subtree for our target row
        { 
            updateTree(2*root+1, l, mid, targ, k); 
        } 
        else 
        { 
            updateTree(2*root+2, mid+1, r, targ, k); 
        }  
        treeSum[root] = treeSum[2*root+1]+treeSum[2*root+2]; //After finding our target row, we now need to rebuild the tree from the now updated target node leaf and it's sibling
        //This updates the parent's row range with the updates row's values
        treeMax[root] = Math.max(treeMax[2*root+1], treeMax[2*root+2]); 
        //This will unwind the recursion from the leaves back up to the root node 0 and update the entire subtree that was affected by the change
    } 
 
    public int[] gather(int k, int maxRow) { 
        int firstRow = getFirstRow(0, 0, rows-1, k, maxRow); //For gather, first find the first row that has k or more available seats
        if(firstRow==-1) //getFirstRow returns -1 no rows have k or more available seats
        { 
            return new int[0]; //Return empty array
        } 
        int startSeating = seatsPerRow-getRowSeats(0, 0, rows-1, firstRow); //This calculates the column that we start seating people on the valid row
        updateTree(0, 0, rows-1, firstRow, k); //Now update that row by subtracting the k total people from its available seats and updating the whole segment tree
        return new int[]{firstRow, startSeating}; 
    } 
     
    public boolean scatter(int k, int maxRow) { 
        long sum = getSum(0, 0, rows-1, maxRow); //First get the total number of available seats from 0 to maxRow
        if(sum>=k) //If this total number of available seats is >= k, then it's possible
        { 
            while(k>0) //While the total number of people that need to be seated > 0
            { 
                int firstRow = getFirstRow(0, 0, rows-1, 1, maxRow); //Get the first row that has >= 1 seat available
                int seatsAvail = Math.min(k, getRowSeats(0, 0, rows-1, firstRow)); //Get the number of seats that row has 
                //If the seats available are greater than k, then we want to only use up k number of seats, not the whole row
                updateTree(0, 0, rows-1, firstRow, seatsAvail); //Update that specific row by subtracting the necessary amount of seats from that row's count
                k-=seatsAvail; //Decrement the total number of people
            } 
            return true;
        } 
        return false; 
    } 
} 
 
/** 
 * Your BookMyShow object will be instantiated and called as such: 
 * BookMyShow obj = new BookMyShow(n, m); 
 * int[] param_1 = obj.gather(k,maxRow); 
 * boolean param_2 = obj.scatter(k,maxRow); 
 */
