// 2061. Number of Spaces Cleaning Robot Cleaned
// https://leetcode.com/problems/number-of-spaces-cleaning-robot-cleaned/
// Medium | Java | Accepted 2026-11-04
// Runtime 8 ms | Memory 68 MB

class Solution {
    boolean[][][] tracker;
    public int numberOfCleanRooms(int[][] room) {
        tracker = new boolean[room.length][room[0].length][4];
        tracker[0][0][0] = true;
        return recurse(room, 0, 0, 0);
    }

    public int recurse(int[][] room, int i, int j, int dir)
    {
        int tot = room[i][j] == 2 ? 0 : 1;
        room[i][j] = 2;
        if(dir==0 && j+1<room[0].length && room[i][j+1] != 1)
        {
            if(tracker[i][j+1][dir])
            {
                return 0;
            }
            tracker[i][j+1][dir] = true;
            tot += recurse(room, i, j+1, 0);
        }
        else if(dir==1 && i+1<room.length && room[i+1][j] != 1)
        {
            if(tracker[i+1][j][dir])
            {
                return 0;
            }
            tracker[i+1][j][dir] = true;
            tot += recurse(room, i+1, j, 1);
        }
        else if(dir==2 && j-1>=0  && room[i][j-1] != 1)
        {
            if(tracker[i][j-1][dir])
            {
                return 0;
            }
            tracker[i][j-1][dir] = true;
            tot += recurse(room, i, j-1, 2);
        }
        else if(dir==3 && i-1>=0 && room[i-1][j] != 1)
        {
            if(tracker[i-1][j][dir])
            {
                return 0;
            }
            tracker[i-1][j][dir] = true;
            tot += recurse(room, i-1, j, 3);
        }
        else if(!tracker[i][j][(dir+1)%4])
        {
            tracker[i][j][(dir+1)%4] = true;
            tot+=recurse(room, i, j, (dir+1)%4);
        }
        return tot;
    }
}
