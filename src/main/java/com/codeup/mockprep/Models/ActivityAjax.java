package com.codeup.mockprep.Models;

public class ActivityAjax {

    private long number_in_secs;
    private long question_id;

    public ActivityAjax(long number_in_secs, long question_id) {
        this.number_in_secs = number_in_secs;
        this.question_id = question_id;
    }

    public long getNumber_in_secs() {
        return number_in_secs;
    }

    public void setNumber_in_secs(long number_in_secs) {
        this.number_in_secs = number_in_secs;
    }

    public long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }
}
