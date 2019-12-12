package sample;

import java.util.LinkedList;

public class Lesson {
    private LinkedList<LessonPage> lesson = new LinkedList();

    Lesson(int[] pages) {
        int[] var2 = pages;
        int var3 = pages.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            int i = var2[var4];
            this.lesson.add(new LessonPage(i));
        }

    }

    public LinkedList<LessonPage> getLesson() {
        return this.lesson;
    }
}
