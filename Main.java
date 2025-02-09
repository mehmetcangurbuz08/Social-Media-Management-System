/**
 * This program is an application that provides general management of the social media app.
 * @author Mehmet Can Gürbüz, Student ID: 2022400177
 * @since Date: 24.11.2024
 */
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Use a StringBuilder to give output
        StringBuilder output = new StringBuilder();
        // Create a new InstagramManager instance
        InstagramManager newInstagram = new InstagramManager();

        // Read commands from the input file
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            // Iteratively read every line and complete the function
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String command = parts[0];
                String result ;
                // Process the functions
                if (command.equals("create_user")) {
                    result = newInstagram.create_user(parts[1]);
                } else if (command.equals("follow_user")) {
                    result = newInstagram.follow_user(parts[1], parts[2]);
                } else if (command.equals("unfollow_user")) {
                    result = newInstagram.unfollow_user(parts[1], parts[2]);
                } else if (command.equals("create_post")) {
                    // Handle spaces in content
                    String content = line.substring(line.indexOf(parts[3]));
                    result = newInstagram.create_post(parts[1], parts[2], content);
                } else if (command.equals("toggle_like")) {
                    result = newInstagram.toggle_like(parts[1], parts[2]);
                } else if (command.equals("see_post")) {
                    result = newInstagram.see_post(parts[1], parts[2]);
                } else if (command.equals("see_all_posts_from_user")) {
                    result = newInstagram.see_all_posts_from_user(parts[1], parts[2]);
                } else if (command.equals("generate_feed")) {
                    result = newInstagram.generate_feed(parts[1], Integer.parseInt(parts[2]));
                } else if (command.equals("scroll_through_feed")) {
                    int num = Integer.parseInt(parts[2]);
                    int[] likes = new int[num];
                    for (int i = 0; i < num; i++) {
                        likes[i] = Integer.parseInt(parts[3 + i]);
                    }
                    result = newInstagram.scroll_through_feed(parts[1], num, likes);
                } else if (command.equals("sort_posts")) {
                    result = newInstagram.sort_posts(parts[1]);
                } else {
                    result = "Command not found.";
                }
                //Add result to the output in every iteration.
                output.append(result).append("\n");
            }
        }
        //Write the output.txt file with using BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))) {
            writer.write(output.toString());
        }

    }
}
