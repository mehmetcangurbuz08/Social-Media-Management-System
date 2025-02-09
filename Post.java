// Create a post class for creating post objects and creating necessary methods on these posts.
public class Post implements Comparable<Post> {
    // Unique post ID for the post
    private String postId;
    // The number of likes for the post
    private int likeNumber;
    // The post content
    private String content;
    // The user who posted the post.
    private User user;

    //Constructor for the post object.
    public Post(String postId, String content, User author) {
        this.postId = postId;
        this.likeNumber = 0;
        this.content = content;
        this.user = author;

    }
    //Returns the ID of the post.
    public String getPostId() {
        return postId;
    }
    // Returns the user of the post.
    public User getUser() {
        return user;
    }
    //Returns the number of likes for the post
    public int getNumberOfLike() {
        return likeNumber;
    }
    //Increments the like number by 1
    public void incrementLikes() {
        this.likeNumber++;
    }
    //Decrements the like number by 1
    public void decrementLikes() {
        if (this.likeNumber > 0) {
            this.likeNumber--; // Decrease the like count if it's greater than 0
        } else {
            this.likeNumber = 0; // Ensure it stays at 0
        }
    }
    // Override a method for compare post according to their Post ID
    @Override
    public int compareTo(Post comparedPost) {
        return comparedPost.postId.compareTo(this.postId);
    }

}
