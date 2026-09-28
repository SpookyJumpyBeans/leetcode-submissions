// 2751. Robot Collisions
// https://leetcode.com/problems/robot-collisions/
// Hard | Java | Accepted 2026-09-27
// Runtime 71 ms | Memory 137.2 MB

class Solution {
    public record Robot(int position, int health, char dir) {}
    public List<Integer>  survivedRobotsHealths(int[] positions, int[] healths, String directions) {
        Robot[] sorted = new Robot[positions.length];
        for(int i = 0; i<positions.length; i++)
        {
            sorted[i] = new Robot(positions[i], healths[i], directions.charAt(i)); //Sort the robots by their positions ascending
        }
        //OPTIMIZATION, instead of sorting objects, sort the indices of the positions
        Arrays.sort(sorted, (a, b) -> Integer.compare(a.position(), b.position()));
        Stack<Robot> stack = new Stack<>(); //This is the stack that will simulate the collisions
        for(int i = 0; i<sorted.length; i++)
        {
            Robot curr = sorted[i]; //Get the current robot at the smallest index
            boolean survived = true; //This keeps track of the robot and whether it's still alive
            while(!stack.isEmpty() && stack.peek().dir()=='R' && curr.dir() =='L' && survived)
            //While the stack isn't empty and the top robot on the stack is going right and the current one is going left (this is the only case when they collide) and the current one is also still alive
            {
                Robot top = stack.peek(); //Get the top robot
                if(curr.health() > top.health()) //If the current robot has more health, we want to destroy the top robot
                {
                    stack.pop(); //Destroy 
                    curr = new Robot(curr.position(), curr.health()-1, curr.dir()); //Set the current robot to the same robot but with health points deducted by 1
                    //The current robot isn't destroyed (it won the fight) so we keep survived as true because we now have to see if the next robot on the stack is going to collide too
                }
                else if(curr.health() < top.health()) //If the current robot's health is less than the top robot's health, the current robot will be destroyed
                {
                    stack.pop(); //Pop the top robot
                    stack.push(new Robot(top.position(), top.health()-1, top.dir())); //The top robot won so the top robot's health is deducted by 1
                    survived = false; //The current robot died so set survived to false
                }
                else
                {
                    stack.pop(); //If they both have the same health they both get destroyed
                    survived = false; //Pop the top and don't push anything
                    //Set survived to false
                }
            }
            if(survived) //If the robot survived (This case is only when the robot kills all the Right moving robots on the stack, any other time the current robot will be destroyed)
            {
                stack.push(curr); //Push the robot to the stack (stack is empty)
            }
        }
        Map<Integer, Robot> map = new HashMap<>();
        while(!stack.isEmpty()) //We now map everything back
        {
            Robot rob = stack.pop();
            map.put(rob.position(), rob);
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i : positions)
        {
            if(map.containsKey(i))
            {
                ans.add(map.get(i).health()); //Add them back to the position the robots were given
            }
        }
        return ans;
    }
}
