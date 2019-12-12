package sample;

import java.util.LinkedList;

public class Lesson {

    private LinkedList<LessonPage> lesson = new LinkedList<>();

    Lesson(int[] pages) {
        int length = pages.length;

        for(int k = 0; k < length; ++k) {
            int i = pages[k];
            this.lesson.add(new LessonPage(i));
        }

    }

    public LinkedList<LessonPage> getLesson() {
        return this.lesson;
    }
}
