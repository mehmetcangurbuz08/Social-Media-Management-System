// Create an Instagram manager class to write all necessary main functions and create necessary data structures for these functions.
import java.util.ArrayList;

public class InstagramManager {
    // Create a hashmap for users.
    private MyHashMap<String, User> allUser;
    // Create a hashmap for posts.
    private MyHashMap<String, Post> allPost;

    //Constructor for Instagram manager class.
    public InstagramManager() {
        allUser = new MyHashMap<>();
        allPost = new MyHashMap<>();
    }

    // Creates a method to create a new user with the user ID.
    public String create_user(String userId) {
        // Create a user object
        User addedUser = new User(userId);
        // Check if the user ID already in the allUser Hashmap
        if (allUser.containsKey(userId)) {
            return "Some error occurred in create_user.";
        }else {
            // Add the user to the user allUser Hashmap if it is not already in the hashmap.
            allUser.put(userId, addedUser);
            return "Created user with Id " + userId + ".";
        }
    }
    // Create a method that allows users to follow other users given their IDs.
    public String follow_user(String followerId, String followedId) {
        // Create users
        User follower = allUser.get(followerId);
        User followed = allUser.get(followedId);

        // Check if either user doesn't exist
        if (follower == null || followed == null) {
            return "Some error occurred in follow_user.";
        }else if (follower.equals(followed)) { // Check if followerId and followedId are the same
            return "Some error occurred in follow_user.";
        }else if (follower.isInFollows(followed)) { // Check if follower is already following fallowed user
            return "Some error occurred in follow_user.";
        }else {
            // Do the follow action in the user class. This action adds users to the exact hashsets.
            follower.follow(followed);
            return followerId + " followed " + followedId + ".";
        }
    }

    // Create a method that allows users to unfollow other users given their IDs.
    public String unfollow_user(String unfollowerId, String unfollowedId) {
        // Create users
        User unfollower = allUser.get(unfollowerId);
        User unfollowed = allUser.get(unfollowedId);

        // Check if either user doesn't exist
        if (unfollower == null || unfollowed == null) {
            return "Some error occurred in unfollow_user.";
        }else if (unfollower.equals(unfollowed)) { // Check if unfollowerId and unfollowedId are the same
            return "Some error occurred in unfollow_user.";
        }else if (!unfollower.isInFollows(unfollowed)) { // Check if unfollower is not currently following unfollowed
            return "Some error occurred in unfollow_user.";
        }else {
            // Perform the unfollow action that remove users from the exact hashsets.
            unfollower.unfollow(unfollowed);
            return unfollowerId + " unfollowed " + unfollowedId + ".";
        }
    }
    // Create a method to creates new posts for the given user.
    public String create_post(String userId, String postId, String content) {
        // Retrieve the user from the allUser hashmap.
        User currentUser = allUser.get(userId);

        if (currentUser == null ) { // Check if the user exists
            return "Some error occurred in create_post.";
        }else if (allPost.containsKey(postId)) { // Check if the post ID is already in use
            return "Some error occurred in create_post.";
        }else {
            // Create a new post object with the given information.
            Post newPost = new Post(postId, content, currentUser);
            // Add the post to the user's arraylist of posts
            currentUser.addPost(newPost);
            // Add the post to the allPost hashmap
            allPost.put(postId, newPost);
            return userId + " created a post with Id " + postId + ".";

        }
    }
    //Create a method to see given user's given post.
    public String see_post(String userId, String postId) {
        // Retrieve the given user from the allUser hashmap.
        User user = allUser.get(userId);
        // Retrieve the given post from the allPost hashmap.
        Post post = allPost.get(postId);
        // Check if either the user or post does not exist
        if (user == null || post == null) {
            return "Some error occurred in see_post.";
        }else{
            // Mark the post as seen by the user with using seePost method in the User class
            user.seePost(post);
            return userId + " saw " + postId + ".";
        }
    }
    //Create a method to see given user's all post.
    public String see_all_posts_from_user(String userSawID, String userSeenId) {
        // Retrieve the given user from the allUser hashmap.
        User userSaw = allUser.get(userSawID);
        // Retrieve the given user from the allUser hashmap for seeing his posts.
        User userSeen = allUser.get(userSeenId);

        // Check if either user does not exist
        if (userSaw == null || userSeen == null) {
            return "Some error occurred in see_all_posts_from_user.";
        }else {
            // Get all posts from the userSeen with getPosts method in the user.
            ArrayList<Post> viewedPosts = userSeen.getPosts();

            // Mark each post as seen by the userSaw with using for-each loop
            for (Post post : viewedPosts) {
                userSaw.seePost(post);
            }
            return userSawID + " saw all posts of " + userSeenId + ".";
        }
    }

    // Create a method that allows the given user to like the given post
    public String toggle_like(String userId, String postId) {
        // Retrieve the given user from the allUser hashmap.
        User currentuser = allUser.get(userId);
        // Retrieve the given post from the allPost hashmap.
        Post postToLike = allPost.get(postId);
        // Check if either the user or post does not exist
        if (currentuser == null || postToLike == null) {
            return "Some error occurred in toggle_like.";
        }else {
            // Mark the post as seen by the user with using seePost method in the User class
            currentuser.seePost(postToLike); // Mark the postToLike as seen
            // Check if the user has already liked the post
            if (currentuser.isInLikedPost(postToLike)) {
                // If the user has liked the post,unlike it and decrement the like number
                currentuser.unlike(postToLike);
                postToLike.decrementLikes();
                return userId + " unliked " + postId + ".";
            } else {
                //If the user has not liked the post,like it and increment the like number
                currentuser.like(postToLike);
                postToLike.incrementLikes();
                return userId + " liked " + postId + ".";
            }
        }
    }
    //Create a method to generate feed for given user in the size of feedSize which is given.
    public String generate_feed(String userId, int feedSize) {
        // Retrieve the given user from the allUser hashmap.
        User currentUser = allUser.get(userId);

        //Check if the user exists
        if (currentUser == null) {
            return "Some error occurred in generate_feed.";
        }

        // Get the list of followed users by the given user with using getFollows method in the user class.
        MyHashSet<User> followedUsers = currentUser.getFollows();

        // Check if the user's followed list is empty
        if (followedUsers.isEmpty()) {
            return "Feed for " + userId + ":\nNo more posts available for " + userId + ".";
        }
        //Get the posts that the user has already seen
        MyHashSet<Post> seenPosts = currentUser.getSeenPosts();
        //Create an arraylist of feeds.
        ArrayList<Post> feed = new ArrayList<>();

        // Get unseen posts from all followed users with using foreach loops for every user in the followed list.
        for (User followedUser : followedUsers) {
            // Create an arraylist for followed users posts.
            ArrayList<Post> postsByFollowedUser = followedUser.getPosts();
            for (Post post : postsByFollowedUser) {
                // Add only unseen posts to the feed arraylist.
                if (!seenPosts.contains(post) ) {
                    feed.add(post);
                }
            }
        }

        // Sort the feed posts using heap sort according to like numbers and post ID's.
        heapSortPosts(feed);

        // Get the initial output message with using getResultForFeed
        return getResultForFeed(userId, feedSize, feed);
    }
    // Create a method for writing the output message of generate feed method.
    private static String getResultForFeed(String userId, int feedSize, ArrayList<Post> feed) {
        // Create a string for result.
        String result = "Feed for " + userId + ":";
        //Create a postAddedToFeed variable to count posts in the feed.
        int postsAddedToFeed = 0;

        // Looping the posts in the feed
        for (Post post : feed) {
            if (postsAddedToFeed >= feedSize) {
                break; // Stop if we have added enough posts
            }
            // Add the post info's to the result string
            result += "\nPost ID: " + post.getPostId()  + ", Author: " + post.getUser().getUserId() + ", Likes: " + post.getNumberOfLike();
            //İncrement a post count by 1.
            postsAddedToFeed++;
        }

        // Add a warning if less posts added than requested
        if (postsAddedToFeed < feedSize) {
            result += "\nNo more posts available for " + userId + ".";
        }
        return result;
    }


    // Create a method for a user to scroll through their feed and interact with posts.
    public String scroll_through_feed(String userId, int numOfScroll, int[] likes) {
        // Retrieve the given user from the allUser hashmap.
        User user = allUser.get(userId);

        // Check if the user exists
        if (user == null) {
            return "Some error occurred in scroll_through_feed.";
        }

        // Get the list of users the current user is followed
        MyHashSet<User> followedUsers = user.getFollows();

        // Check if the user's followed list is empty
        if (followedUsers.isEmpty()) {
            return userId + " is scrolling through feed:\nNo more posts in feed.";
        }

        //Get the posts that the user has already seen
        MyHashSet<Post> seenPosts = user.getSeenPosts();
        //Create an arraylist of feeds.
        ArrayList<Post> feed = new ArrayList<>();

        // Get unseen posts from all followed users with using foreach loops for every user in the followed list.
        for (User followedUser : followedUsers) {
            // Create an arraylist for followed users posts.
            ArrayList<Post> userPosts = followedUser.getPosts();
            for (Post post : userPosts) {
                // Add only unseen posts to the feed arraylist.
                if (!seenPosts.contains(post)) {
                    feed.add(post);
                }
            }
        }

        // Sort the feed posts using heap sort according to like numbers and post ID's.
        heapSortPosts(feed);

        // Give the output with using getResultForScroll method.
        return getResultForScroll(user, feed, numOfScroll, likes);
    }

    // Create a method for writing the output message of scroll through feed method.
    private String getResultForScroll(User user, ArrayList<Post> feed, int numOfScroll, int[] likes) {
        // Create a String named result.
        String result = user.getUserId() + " is scrolling through feed:";
        //Create a scrolledPosts variable to count posts in the feed.
        int scrolledPosts = 0;

        // Loop each post in the feed
        for (int i = 0; i < feed.size() && scrolledPosts < numOfScroll; i++) {
            // Retrieve the post from the sorted feed
            Post post = feed.get(i);
            // Mark the post as seen by the user
            user.seePost(post);

            // Check if the user liked the post
            if (likes[scrolledPosts] == 1) {
                // If the user liked the post increment like number.
                if (!user.isInLikedPost(post)) {
                    user.like(post);
                    post.incrementLikes();
                } else {
                    // If the post was already liked, unlike it and decrement the like number.
                    user.unlike(post);
                    post.decrementLikes();
                }
                // Add to the output for liked scrolls.
                result += "\n" + user.getUserId() + " saw " + post.getPostId() + " while scrolling and clicked the like button.";
            } else if (likes[scrolledPosts] == 0) {
                // Add to the output for scrolls that is not liked.
                result += "\n" + user.getUserId() + " saw " + post.getPostId() + " while scrolling.";
            }
            //İncrement a post count by 1.
            scrolledPosts++;
        }

        // If less posts were scrolled than requested, give a warning message
        if (scrolledPosts < numOfScroll) {
            result += "\nNo more posts in feed.";
        }
        return result;
    }
    // Create a method to sort post of the given user according to like number and post ID.
    public String sort_posts(String userId) {
        // Retrieve the given user from the allUser hashmap.
        User user = allUser.get(userId);
        // Check if the user exists
        if (user == null) {
            return "Some error occurred in sort_posts.";
        }
        //Create an arraylist for given user's posts.
        ArrayList<Post> userPosts = user.getPosts();

        //If the user not posted any post hive the message.
        if (userPosts.isEmpty()) {
            return "No posts from " + userId + ".";
        }

        // Create the output message
        String result = "Sorting " + userId + "'s posts:";

        // Sort posts using heap sort
        heapSortPosts(userPosts);

        // Append each post's info's to the output string
        for (Post post : userPosts) {
            result += "\n" + post.getPostId() + ", Likes: " + post.getNumberOfLike();
        }

        return result;
    }

    // Create a method for Heap Sort Implementation
    private void heapSortPosts(ArrayList<Post> posts) {
        // Create a variable to hold size of the arraylist.
        int size = posts.size();

        // Create the max heap from the arraylist
        for (int i = size / 2 - 1; i >= 0; i--) {
            percolateDown(posts, size, i);
        }

        // Remove elements from the heap one by one
        for (int i = size - 1; i > 0; i--) {
            // Move the max element
            Post temp = posts.get(0);
            posts.set(0, posts.get(i));
            posts.set(i, temp);

            // Call percolate down on the removed heap.
            percolateDown(posts, i, 0);
        }

    }
    // Create a method to rearrange the max heap.
    private void percolateDown(ArrayList<Post> posts, int size, int i) {
        // Initialize largest as root
        int largest = i;
        int left = 2 * i + 1; // Left child
        int right = 2 * i + 2; // Right child

        // Check if left child exists and is bigger than root
        if (left < size && comparePosts(posts.get(left), posts.get(largest)) > 0) {
            largest = left;
        }

        // Check if right child exists and is bigger than the largest
        if (right < size && comparePosts(posts.get(right), posts.get(largest)) > 0) {
            largest = right;
        }

        // If largest is not the root
        if (largest != i) {
            // Change root with the largest
            Post change = posts.get(i);
            posts.set(i, posts.get(largest));
            posts.set(largest, change);

            // Recursively percolate down the changed subtree
            percolateDown(posts, size, largest);
        }
    }
    // Create a method for sorting post according to their like number and post ID.
    private int comparePosts(Post p1, Post p2) {
        // Check if the number of likes is different or not
        if (p1.getNumberOfLike() != p2.getNumberOfLike()) {
            // If likes are different, sort by the number of likes in decreasing direction
            if (p2.getNumberOfLike() > p1.getNumberOfLike()) {
                return 1; // If Post p2 has more likes,it comes first
            } else {
                return -1; // If Post p1 has more likes, it comes first
            }
        }else { // If likes are same, sort by the post ID with using compareTo method which overridden in post class.
            return p2.getPostId().compareTo(p1.getPostId());
        }
    }
}
