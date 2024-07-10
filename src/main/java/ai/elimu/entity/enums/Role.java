package ai.elimu.entity.enums;

public enum Role {
    
    ADMIN, // Access to upload and update APKs
    ANALYST, // Access to analyze learning events and assessment events
    CONTRIBUTOR, // Access to add and edit educational content
    EDITOR // Same as CONTRIBUTOR, but with acceess to delete educational content
}
