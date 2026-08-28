// 355. Design Twitter
// https://leetcode.com/problems/design-twitter/
// Medium | Java | Accepted 2025-12-18
// Runtime 32 ms | Memory 56.6 MB

class Twitter {
    private static int timeStamp = 0;
    private Map<Integer, User> map;

    private class Tweet implements Comparable<Tweet>
    {
        private int id;
        private int time;
        Tweet next;

        public Tweet(int id)
        {
            this.id = id;
            this.time = timeStamp;
            timeStamp++;
            next = null;
        }

        public int getTime()
        {
            return time;
        }

        public int getId()
        {
            return id;
        }

         public int compareTo(Tweet other) {
            return Integer.compare(this.getTime(), other.getTime());
        }
    }

    private class User {
        private int uid;
        private Set<Integer> follows;
        private Tweet head;

        public User (int id)
        {
            uid = id;
            follows = new HashSet<>();
            follow(id);
            head = null;
        }
        
        public void postTweet(int tweetId)
        {
            Tweet tweet = new Tweet(tweetId);
            tweet.next = head;
            head = tweet;
        }

        public void follow(int followId)
        {
            follows.add(followId);
        }

        public void unfollow(int unfollowId)
        {
            follows.remove(unfollowId);
        }

        public Set<Integer> getFollows()
        {
            return follows;
        }

        public Tweet getHead()
        {
            return head;
        }

    }
    
    public Twitter() {
        map = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(map.containsKey(userId))
        {
            map.get(userId).postTweet(tweetId);
        }
        else
        {
            User user = new User(userId);
            user.postTweet(tweetId);
            map.put(userId, user);
        }
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> queue = new PriorityQueue<>(Collections.reverseOrder());
        if(!map.containsKey(userId))
        {
            map.put(userId, new User(userId));
        }
        Set<Integer> temp = map.get(userId).getFollows();
        List<Integer> newsfeed = new ArrayList<>();
        for(int id : temp)
        {
            if(map.get(id).getHead()!=null)
            {
            queue.add(map.get(id).getHead());
            }
        }
        for(int i = 0; i<10; i++)
        {
            Tweet pop = queue.poll();
            if(pop!=null)
            {
                newsfeed.add(pop.getId());
            }
            if(pop!=null && pop.next!=null)
            {
                queue.add(pop.next);
            }
        }
        return newsfeed;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!map.containsKey(followerId))
        {
            map.put(followerId,new User(followerId));
        }
        if(!map.containsKey(followeeId))
        {
             map.put(followeeId,new User(followeeId));
        }
        map.get(followerId).follow(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
         if(map.containsKey(followerId) && followerId != followeeId)
        {
            map.get(followerId).unfollow(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
