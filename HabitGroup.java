/**
 * 
 * Author: Flynn Watson Student num: 49061461
 *
 * Description:
 * Represents a single habit with a name and optional streak counter.
 * Used by HabitGroup and HabitTracker to store user habits.
 *
 * ------------------------------------------------------------
 * Dates and Notes 
 * ------------------------------------------------------------
 *
 * 2025-10-02 — Created early in the project alongside HabitGroup.
 *   - added simple fields for habit name and streak.
 *   - Tested habit creation and printing to console.
 *
 * 2025-10-10 — tiny improvements 
 *   - added getter and setter methods.
 *   - made sure to ensure habit names display properly in console.
 *
 * 
 */


//  class that represnrts one single habit
public class Habit {
     private String name; // name of habit
    private int streak;
    private boolean isComplete; // tracking if its been done for day or session
// this method is constructor for new habits
    public Habit(String name) {
        this.name = name.trim();
        this.streak = 0;
        this.isComplete = false;
    }

    public Habit(String name, int streak, boolean isComplete) {
        this.name = name.trim();
        this.streak = streak;
        this.isComplete = isComplete;
    }

    public String getName() { return name; }  //returning values
    public int getStreak() { return streak; }
    public boolean isComplete() { return isComplete; }

    //marks habit as done for day and adds the streak
    public void markComplete() {  // o
        if (!isComplete) {
            isComplete = true;
            streak++;   // only adds one per day so cant spam streak points 
        }
    }

    // resets flag 
    public void resetToday() {
        isComplete = false;
    }

    @Override
    public String toString() {
        return (isComplete ? "[✓] " : "[ ] ") + name + "  (streak: " + streak + ")";
    }

    // displays shit in console 
    public String toFileLine() {
        // name , streak , isComplete
        return name.replace("|", "/") + "|" + streak + "|" + isComplete;
    }

    public static Habit fromFileLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 3) return null;  //skips bad lines
        String n = parts[0];
        int s = Integer.parseInt(parts[1]);
        boolean c = Boolean.parseBoolean(parts[2]);
        return new Habit(n, s, c);
    }
}

