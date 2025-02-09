import java.util.ArrayList;
// Create a user class for creating user object and doing necessary operations on these users.
public class User {
    // The unique ID of the user
    private String userId;
    // A set of users this user is following
    private MyHashSet<User> followeds;
    // A set of users followers of this user
    private MyHashSet<User> followers;
    // A list of posts created by this user
    private ArrayList<Post> posts;
    // A set of posts liked by this user
    private MyHashSet<Post> likedPosts;
    // A set of posts seen by this user
    private MyHashSet<Post> seenPosts;

    // Constructor for user objects
    public User(String userId) {
        this.userId = userId;
        this.followeds = new MyHashSet<>();
        this.followers = new MyHashSet<>();
        this.posts = new ArrayList<>();
        this.likedPosts = new MyHashSet<>();
        this.seenPosts = new MyHashSet<>();

    }
    //Returns the ID of a user
    public String getUserId() {
        return userId;
    }
    // Returns the set of users this user is following
    public MyHashSet<User> getFollows() {
        return followeds;
    }
    //Returns the post of this user
    public ArrayList<Post> getPosts() {
        return posts;
    }
    //Returns the seen posts by this user
    public MyHashSet<Post> getSeenPosts() {
        return seenPosts;
    }

    //Create a method for following user
    public void follow(User followedUser) {
        // Add the followed user to this user's following list
        followeds.add(followedUser);
        // Add this user to the followed user's followers list
        followedUser.followers.add(this);
    }
    // Create a method for unfollowing user
    public void unfollow(User followedUser) {
        // Remove the followed user from this user's following list
        followeds.remove(followedUser);
        // Remove this user from the followed user's followers list
        followedUser.followers.remove(this);
    }
    //Return true if the given user in followeds set
    public boolean isInFollows(User user) {
        return followeds.contains(user);
    }
    //Adds a post to this user's arraylist of posts
    public void addPost(Post post) {
        posts.add(post);
    }
    //Marks a post as seen by this user.
    public void seePost(Post post) {
        seenPosts.add(post);
    }

    //Likes a post from this user.
    public void like(Post post) {
        likedPosts.add(post);
    }
    // removes a like from a post by this user.
    public void unlike(Post post) {
        likedPosts.remove(post);
    }
    //Checks if the user has liked the given post.
    public boolean isInLikedPost(Post post) {
        return likedPosts.contains(post);
    }


}
