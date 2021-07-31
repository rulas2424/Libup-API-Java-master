package com.americadigital.libupapi.Pojos.UserAdmin;

public class ActivePlan {
    public boolean isPlanActive;
    public int notificationsAllowed;
    public int notificationsUsed;
    public String dateEnded;

    public ActivePlan(boolean isPlanActive, int notificationsAllowed, int notificationsUsed, String dateEnded) {
        this.isPlanActive = isPlanActive;
        this.notificationsAllowed = notificationsAllowed;
        this.notificationsUsed = notificationsUsed;
        this.dateEnded = dateEnded;
    }
}
