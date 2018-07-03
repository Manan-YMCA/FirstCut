package com.manan.dev.shineymca.Models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Event {

    private String Poster, Name, Description, Venue, SpecialNotes, Clubname;
    private long Date, Time;
    private ArrayList<Coordinator> Coordinators;
    private ArrayList<FAQ> Faq;

    public Event(String poster, String name, String description, String venue, String specialNotes, String clubname, long date, long time, ArrayList<Coordinator> coordinators, ArrayList<FAQ> faq, long round) {
        Poster = poster;
        Name = name;
        Description = description;
        Venue = venue;
        SpecialNotes = specialNotes;
        Clubname = clubname;
        Date = date;
        Time = time;
        Coordinators = coordinators;
        Faq = faq;
        this.round = round;
    }

    @Exclude
    private long round;

    public Event() {
    }



    public Event(String poster, String name, String description, String venue, String specialNotes, String clubname, long date, long time, ArrayList<Coordinator> coordinators, ArrayList<FAQ> faq) {
        Poster = poster;
        Name = name;
        Description = description;
        Venue = venue;
        SpecialNotes = specialNotes;
        Clubname = clubname;
        Date = date;
        Time = time;
        Coordinators = coordinators;
        Faq = faq;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public String getSpecialNotes() {
        return SpecialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        SpecialNotes = specialNotes;
    }

    public String getClubname() {
        return Clubname;
    }

    public void setClubname(String clubname) {
        Clubname = clubname;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    public ArrayList<Coordinator> getCoordinators() {
        return Coordinators;
    }

    public void setCoordinators(ArrayList<Coordinator> coordinators) {
        Coordinators = coordinators;
    }

    public ArrayList<FAQ> getFaq() {
        return Faq;
    }

    public void setFaq(ArrayList<FAQ> faq) {
        Faq = faq;
    }

    public long getRound() {
        return round;
    }

    public void setRound(long round) {
        this.round = round;
    }
}
