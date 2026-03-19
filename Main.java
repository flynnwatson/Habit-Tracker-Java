
/**
 * HabitTracker using java 
 * Author: Flynn Watson student id:49061461
 
 *
 * ------------------------------------------------------------
 * dates and notes 
 * ------------------------------------------------------------
 *
 * 2025-09-30 — setting up the project and coming up with a idea 
 *   - I created my folder structure (src/test/lib).
 *   - Decided on a habit tracking idea just thought it would work best
 *   
 *
 * 2025-10-02 — Started Habit + HabitGroup classes.
 *   - Started putting in my methods only basic ones to startoff with
 *   - Tested manually by printing out habits in console.
 *   - I notced that there was  duplicates showing up so i planned to handle this in HabitTracker.
 *
 * 2025-10-05 — Wrote HabitTracker class and simple CLI.
 *   - Built addHabit and simple grouping model.
 *   - tried and done a basic menu working for adding and viewing habits.
 *  
 *
 * 2025-10-10 — Early testing and cleanup.
 *   - Added HabitTest and HabitTrackerTest using JUnit.
 *   - Learned how to compile/run JUnit from terminal.
 *   - Fixed logic bug where removing a group didn’t clear its habits.
 *
 * 2025-10-15 — Implemented file saving/loading (FileManager).
 *   - Chose plain-text format so I could debug by hand.
 *   - Added FileManager.saveTree() and loadTree() methods.
 *   - Did a few manual saves to verify format looked consistent.
 *
 * 2025-10-20 — Automated testing for file I/O.
 *   - Created FileIoTest.java for round-trip save/load verification.
 *   - Used temporary files to isolate tests.
 *   - Confirmed data persists correctly between runs.
 *
 * 2025-10-22 — Bug fixes + edge cases
 *   - Handled empty trackers and blank habit names.
 *   - Cleaned up output formatting in Main.java menu.
 *
 * 2025-10-25 — Final testing and polish.
 *   - Added HabitGroupTest + IntegrationTest for full coverage.
 *   - Wrote README and added documentation comments.
 *   - Final compile and all tests passin
 *
 * ------------------------------------------------------------
 * the Design Summary
 *   I kept the file format as  simple as possible to avoid me being to confused (group headers + habit lines)
 *   so it’s easy to open and debug manually. I really tried to focus on getting the simple things rigtht like readibility etc
 * ------------------------------------------------------------
 */


// Main class gonna make everything relate to this 
public class HabitTracker {

    //
    private HabitGroup root = new HabitGroup("ROOT"); // everything hangs of this root node 

    
    // need empty tracker cause root is already set above 
    public HabitTracker() {
        // nothing to do
    }

    //is used when loading from file 
    public HabitTracker(HabitGroup root) {
        this.root = (root != null) ? root : new HabitGroup("ROOT");
    }

    public HabitGroup getRoot() { return root; }

    // only gonna swap the root if the new one is null
    public void setRoot(HabitGroup g) {
        if (g != null) root = g;  // like a defnsive check
    }

  

    // If the group exists return it otherwise return ROOT.
    
    private HabitGroup getOrCreateGroup(String groupName) {
        if (groupName == null || groupName.trim().isEmpty()) {
            return root; // blank means just go back to ROOT
        }
        HabitGroup g = root.findGroup(groupName.trim());
        return (g != null) ? g : root;
    }

    // Just a tiny thingy so stuff doesnt ger repeated 
    private static boolean equalsIgnoreCaseTrim(String a, String b) {
        if (a == null || b == null) return false;
        return a.trim().equalsIgnoreCase(b.trim());
    }

    //now for user 

    // adds a subgroup under parent but if it cant be found it returns back to ROOT 
    public void addGroup(String parentName, String groupName) {
        if (groupName == null || groupName.trim().isEmpty()) return; // nothing to add
        HabitGroup parent = getOrCreateGroup(parentName);
        parent.addGroup(new HabitGroup(groupName.trim()));
    }

    // same goal as my addgroup method above
    // makesit so i avoid duplicated
    public void addHabit(String groupName, String habitName) {
        if (habitName == null || habitName.trim().isEmpty()) return; // ignore blank input
        HabitGroup group = getOrCreateGroup(groupName);

        // reduncy check etc 
        for (Habit h : group.getHabits()) {
            if (equalsIgnoreCaseTrim(h.getName(), habitName)) return;
        }
        group.addHabit(new Habit(habitName.trim()));
    }

    //goes anf trys to find if not return null
    public Habit findHabit(String habitName) {
        if (habitName == null) return null;
        return findHabitRecursive(root, habitName.trim().toLowerCase());
    }

    // depth first search and comparing highercase lowercase
    private Habit findHabitRecursive(HabitGroup g, String nameLower) {
        for (Habit h : g.getHabits()) {
            if (h.getName().toLowerCase().equals(nameLower)) return h;
        }
        for (HabitGroup child : g.getChildren()) {
            Habit found = findHabitRecursive(child, nameLower);
            if (found != null) return found;
        }
        return null;
    }

    // mark habit complete by name and if it returns to be true that means its found
    public boolean markHabitComplete(String habitName) {
        Habit h = findHabit(habitName);
        if (h == null) return false;
        h.markComplete();
        return true; //found 
    }

    // reets the done toay
    public void resetAllForNewDay() {
        resetRecursive(root);
    }

    private void resetRecursive(HabitGroup g) {
        for (Habit h : g.getHabits()) h.resetToday();
        for (HabitGroup child : g.getChildren()) resetRecursive(child);
    }

    // Count of how many habits are completed 
    public int countCompletedToday() {
        return countCompletedRecursive(root);
    }

    private int countCompletedRecursive(HabitGroup g) {
        int sum = 0;
        for (Habit h : g.getHabits()) if (h.isComplete()) sum++;
        for (HabitGroup child : g.getChildren()) sum += countCompletedRecursive(child);
        return sum;
    }

    // console display stuff etc
    public String renderAll() {
        StringBuilder sb = new StringBuilder();
        root.render(sb, "");
        return sb.toString();
    }

    // just in case of old stuff 
    public HabitGroup getRootGroup() {
        return root;
    }

    
}
