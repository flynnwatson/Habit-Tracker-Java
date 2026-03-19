 * Class: HabitGroup.java
 * Author: Flynn Watson student num: 49061461)
 *
 * Description:
 * Stores and manages a list of Habit objects under one category name
 * for example like 'health' or 'study' Handles adding and removing habits within the group.
 *
 * ------------------------------------------------------------
 * Dates and Notes 
 * ------------------------------------------------------------
 *
 * 2025-10-02 — Started developing with Habit.java.
 *   - Set up list structure to hold Habit objects.
 *   - Added methods for addHabit() and listing habits.
 *
 * 2025-10-15 — Testing and fixes.
 *   - Adjusted removal logic to avoid ConcurrentModificationException.
 *   - Verified habits display correctly under group names.
 *
 * 
 */





import java.util.ArrayList;

/**
 * One "bucket" of habits with a name like health or stud and optional subgroups.
 * keeping this flexible so we can nest groups inside groups if we want.
 */
public class HabitGroup {

    private String name; //name showed in file

    
    private ArrayList<HabitGroup> children = new ArrayList<>();  // starting to build a tree 

    // actual habits that live directly under this group
    private ArrayList<Habit> habits = new ArrayList<>();

    // Basic constructor to make sure no weird spaces etc 
    public HabitGroup(String name) { this.name = name.trim(); }

    // simple getters 
    public String getName() { return name; }
    public ArrayList<HabitGroup> getChildren() { return children; }
    public ArrayList<Habit> getHabits() { return habits; }

    // Add child group or habit group infield 
    public void addGroup(HabitGroup child) { children.add(child); }
    public void addHabit(Habit habit) { habits.add(habit); }

    // Look through all subgroups recusrively to find a group by the name 
    
    public HabitGroup findGroup(String target) {
        if (name.equalsIgnoreCase(target.trim())) return this;
        for (HabitGroup g : children) {
            HabitGroup found = g.findGroup(target);
            if (found != null) return found;
        }
        return null; // didn’t find one with that name
    }

    // Print this group and everything under it with nice indentation.
    // Example: health
    // drink water
    // mediate 
    // actually excerise more flynn more 
   
    //  Study
    //   do shti on time
    // java like 30 mins a day
    public void render(StringBuilder sb, String indent) {
        sb.append(indent).append("📁 ").append(name).append("\n"); // herewego
        for (Habit h : habits)
            sb.append(indent).append("  • ").append(h.toString()).append("\n");
        for (HabitGroup child : children)
            child.render(sb, indent + "  ");
    }

    // 
    // keeping  as super simple line baned format so I can open habits.txt in my notepd
    
    public void toFileLines(ArrayList<String> out, String indent) {
        out.add(indent + "GROUP|" + name);
        for (Habit h : habits)
            out.add(indent + "HABIT|" + h.toFileLine());  //rememebr
        for (HabitGroup child : children)
            child.toFileLines(out, indent + "  ");
    }

    // entry point for turning saves back into tree
    public static HabitGroup fromFileLines(java.util.List<String> lines) {
        return parseAtIndent(lines, 0, 0).node;
    }

    // u
    // Reads each line, figures out how many leading spaces there are, using resruisve parser
    // and uses that to decide which group the line belongs to
    private static ParseResult parseAtIndent(java.util.List<String> lines, int startIdx, int indentSpaces) {
        HabitGroup current = null;
        int i = startIdx;
        while (i < lines.size()) {
            String line = lines.get(i);
            int leading = countLeadingSpaces(line);

            // ifwe see less indent than we started with it means this block has finished.
            if (leading < indentSpaces) break;

            // If we see more indent that means this is a child block  parse that subtree.
            if (leading > indentSpaces) {
                ParseResult child = parseAtIndent(lines, i, leading);
                if (current != null && child.node != null) current.addGroup(child.node);
                i = child.nextIndex;
                continue;
            }

            // Same indent level as the current block  handle the line itself.
            String trimmed = line.trim();
            if (trimmed.startsWith("GROUP|")) {
                String gname = trimmed.substring(6);
                if (current == null) current = new HabitGroup(gname);
                else current.addGroup(new HabitGroup(gname));
            } else if (trimmed.startsWith("HABIT|")) {
                String payload = trimmed.substring(6);
                Habit h = Habit.fromFileLine(payload);
                if (current == null) current = new HabitGroup("ROOT"); // fallback, shouldn’t really happen
                if (h != null) current.addHabit(h);
            }
            i++;
        }
        return new ParseResult(current, i);
    }

    // Counts how many spaces are at the start of the line.
    // this is how we track depth in the text file.
    private static int countLeadingSpaces(String s) {
        int c = 0;
        while (c < s.length() && s.charAt(c) == ' ') c++;
        return c;
    }

    // lil hellper to carry node
    private static class ParseResult {
        HabitGroup node;
        int nextIndex;
        ParseResult(HabitGroup n, int idx) { node = n; nextIndex = idx; }
    }
}
