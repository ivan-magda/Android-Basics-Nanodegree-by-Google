package com.ivanmagda.reportcard;

import java.util.Arrays;

public class ReportCard {

    private static final String LOG_TAG = ReportCard.class.getSimpleName();

    static public class Subject {
        private String name;
        private int grade;
        private String comments;

        public Subject(String name, int grade, String comments) {
            this.name = name;
            this.grade = grade;
            this.comments = comments;
        }

        public String getName() {
            return name;
        }

        public int getGrade() {
            return grade;
        }

        public String getComments() {
            return comments;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setGrade(int grade) {
            if (grade >= 2 && grade <= 5) {
                this.grade = grade;
            } else {
                throw new IllegalArgumentException("Grade must be between 5-2");
            }
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        @Override
        public String toString() {
            return "Subject{" +
                    "name='" + name + '\'' +
                    ", grade=" + grade +
                    ", comments='" + comments + '\'' +
                    '}';
        }

    }

    private String studentName;
    private int year;
    private Subject[] subjects;

    public ReportCard(String studentName, int year, Subject[] subjects) {
        this.studentName = studentName;
        this.year = year;
        this.subjects = subjects;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getYear() {
        return year;
    }

    public Subject[] getSubjects() {
        return subjects;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSubjects(Subject[] subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "ReportCard{" +
                "studentName='" + studentName + '\'' +
                ", year=" + year +
                ", subjects=" + Arrays.toString(subjects) +
                '}';
    }
}
