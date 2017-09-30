package com.mmall.pojo;

import java.util.List;

public class MoodLogPic {
    private MoodLog moodLog;
    private List<String> moodImageUrl;

    public MoodLogPic(MoodLog moodLog, List<String> moodImageUrl) {
        this.moodLog = moodLog;
        this.moodImageUrl = moodImageUrl;
    }

    public MoodLog getMoodLog() {
        return moodLog;
    }

    public void setMoodLog(MoodLog moodLog) {
        this.moodLog = moodLog;
    }

    public List<String> getMoodImageUrl() {
        return moodImageUrl;
    }

    public void setMoodImageUrl(List<String> moodImageUrl) {
        this.moodImageUrl = moodImageUrl;
    }
}
