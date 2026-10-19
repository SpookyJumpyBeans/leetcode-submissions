// 489. Robot Room Cleaner
// https://leetcode.com/problems/robot-room-cleaner/
// Hard | Java | Accepted 2026-10-16
// Runtime 10 ms | Memory 46.2 MB

/**
 * // This is the robot's control interface.
 * // You should not implement it, or speculate about its implementation
 * interface Robot {
 *     // Returns true if the cell in front is open and robot moves into the cell.
 *     // Returns false if the cell in front is blocked and robot stays in the current cell.
 *     public boolean move();
 *
 *     // Robot will stay in the same cell after calling turnLeft/turnRight.
 *     // Each turn will be 90 degrees.
 *     public void turnLeft();
 *     public void turnRight();
 *
 *     // Clean the current cell.
 *     public void clean();
 * }
 */

class Solution {
    // Stores visited virtual coordinates to prevent infinite loops.
    Set<String> visited = new HashSet<>();
    
    public void cleanRoom(Robot robot) {
        // Assume starting position is (0, 0) and starting direction is 0 (UP).
        backtrack(robot, 0, 0, 0);
    }
    
    public void backtrack(Robot robot, int r, int c, int dir) {
       robot.clean();
       visited.add(r + "," + c);
       
       // Coordinate changes for UP, RIGHT, DOWN, LEFT.
       // The order exactly matches turning right 90 degrees.
       int[][] move = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
       
       // Check all 4 adjacent cells. 
       // We need this loop and the (dir+i)%4 math because the robot might 
       // enter this method facing ANY direction. This ensures we calculate 
       // the correct virtual (r, c) coordinates for whatever way the physical 
       // robot is actually pointing before we command it to move.
       for (int i = 0; i < 4; i++) {
            
            // Calculate the absolute direction the robot is facing right now.
            int currDir = (dir + i) % 4;
            
            // Calculate the virtual coordinates of the cell directly in front of the robot.
            int r0 = r + move[currDir][0];
            int c0 = c + move[currDir][1];
            
            // If the cell is unvisited AND there is no wall (robot physically moves forward).
            if (!visited.contains(r0 + "," + c0) && robot.move()) {
                
                // Recursively clean from the new cell. 
                // We pass currDir so the next call knows which way the robot is facing.
                backtrack(robot, r0, c0, currDir);
                
                // BACKTRACK: The recursive call finished, but the physical robot 
                // is still sitting in the new cell. We must completely reverse its 
                // state to exactly how it was before the `robot.move()` command above.
                robot.turnRight(); 
                robot.turnRight(); // Turn 180 degrees
                robot.move();      // Step back into the previous cell
                robot.turnRight(); 
                robot.turnRight(); // Turn 180 degrees again to restore original orientation
            }
            
            // Turn the physical robot 90 degrees to face the next adjacent cell.
            // This physical turn aligns perfectly with the `i++` in the next loop iteration.
            robot.turnRight();
       }
    }
}
