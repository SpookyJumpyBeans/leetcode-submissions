// 2402. Meeting Rooms III
// https://leetcode.com/problems/meeting-rooms-iii/
// Hard | Java | Accepted 2026-08-18
// Runtime 78 ms | Memory 155.1 MB

class Solution {
    public int mostBooked(int n, int[][] meetings) {
        int[] count = new int[n]; //Frequency tracker of the rooms used
        PriorityQueue<Integer> roomsFree = new PriorityQueue<>(); //Keep a min heap of the rooms free, so we can easily pick the room with the lowest number to use for a meeting
        PriorityQueue<long[]> endTimes = new PriorityQueue<>((a, b)->{ //Keep a min heap of the end time of the meeting as well as what room it's in, also, ensure that if the end times are the same, then we want to sort based on the smaller room number
            if(a[0]==b[0])
            {
                return Long.compare(a[1], b[1]);
            }
            return Long.compare(a[0], b[0]);
        });
        for(int i = 0; i<n; i++) //Add all rooms initially to the roomsFree heap
        {
            roomsFree.add(i);
        }
        Arrays.sort(meetings, (a, b)->Integer.compare(a[0], b[0])); //Sort the meetings by start time
        for(int i = 0; i<meetings.length; i++) //Go through every meeting
        {
            long start = meetings[i][0]; //Get the start and end time of the current meeting
            long end = meetings[i][1];
            while(!endTimes.isEmpty() && endTimes.peek()[0]<=start) //While the end times of meetings that were pushed to the meetings heap is less than or equal to the start time, we want to add these meeting rooms back to the roomsFree heap, as these meetings technically have ended since this new meeting has come
            {
                roomsFree.add((int)endTimes.poll()[1]); //Add these ended meetings back onto the roomsFree
            }
            if(roomsFree.size()>0) //If there are free rooms 
            {
                int room = roomsFree.poll();
                count[room]++; //Simply poll the top free room and increase its count by 1
                endTimes.add(new long[]{end, room}); //Add the meeting's end time and the room to the meetings heap
            }
            else
            { 
                long[] nextFree = endTimes.poll(); //If there aren't any free rooms, we have to wait for the earliest meeting to end
                //The next free is the top of the endTimes heap
                end = end+nextFree[0]-start; //This meeting that just ended, it either 
                count[(int)nextFree[1]]++;
                endTimes.add(new long[]{end, nextFree[1]});
            }
        }
        int max = 0;
        int temp = 0;
        for(int i = 0; i<n; i++)
        {
            if(count[i]>max)
            {
                max = count[i];
                temp = i;
            }
        }
        return temp;
    }
}
