package algat;

import java.util.LinkedList;

public class Lesson {

    private final LinkedList<LessonPage> lesson = new LinkedList<>();

    public Lesson(int[] pages) {

        for (int i : pages) {
            this.lesson.add(new LessonPage(i));
        }

    }

    public LinkedList<LessonPage> getLesson() {
        return this.lesson;
    }
}
