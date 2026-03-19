/**
 * Class: FileManager.java
 * Author: Flynn Watson student number : 49061461
 *
 * Description:
 * Handles saving and loading the entire HabitTracker data structure
 * to and from a plain-text file (habits.txt).
 *
 * ------------------------------------------------------------
 * Dates and Notes 
 * ------------------------------------------------------------
 *
 * 2025-10-15 — Implemented file I/O system.
 *   - Created saveTree() and loadTree() methods.
 *   - Chose plain-text format for simplicity and easy debugging.
 *
 * 2025-10-20 — Testing and improvement.
 *   - Created FileIoTest.java to verify saving/loading works.
 *   - Fixed path issues and added temporary file handling.
 *
 * 2025-10-25 — Polished and cleaned up.
 *   - Added better error handling for empty files and null values.
 *   - Added documentation to explain format.
 */




import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all the file saving  and loading for the Habit Tracker.
 * Pretty much just writes everything to habits.txt and brings it back next time
 */
public class FileManager {

    // name of the file we’re using lives inside same folder asprogram
    private static final String FILE_NAME = "habits.txt";

    // saves the current habit tree to a text file.
    // I use PrintWriter because it's simple n auto closes for me 
    public static void saveTree(HabitGroup root) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {

            ArrayList<String> lines = new ArrayList<>();
            root.toFileLines(lines, ""); // turns everything into lines first

            for (String s : lines) out.println(s); // print each line into the file

            // file now has GROUP| and HABIT| lines way easier to open in notepad
        } catch (IOException e) {
            System.err.println("Could not save file: " + e.getMessage()); // prints red err msg if smthing goes wrong
        }
    }

    // Loads the tree back from habits.txt if it exists
    // If not it will just make a fresh ROOT group so the program doesnt break
    public static HabitGroup loadTree() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return new HabitGroup("ROOT"); // if missing, start blank

        List<String> lines = new ArrayList<>();

        // reading all lines from the file into a list
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) lines.add(line); // basic loop
        } catch (IOException e) {
            System.err.println("Could not read file: " + e.getMessage()); // small t
        }

        // convert the list of lines back into HabitGroup tree
        HabitGroup g = HabitGroup.fromFileLines(lines);

        // if somehow that failed, return new ROOT instead of crashing
        return (g == null) ? new HabitGroup("ROOT") : g;
    }
}
