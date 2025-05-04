package no.josefushighscore.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "`user_sign_in_tracking`")
public class TrackUserSignins {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`id`")
    private Long userSignInId;

    @Column(name = "`user_id`")
    private Long userId;

    @Column(name = "`sign_in_count`")
    private int signInCount;

    @Column(name = "`last_sign_in`")
    private LocalDateTime lastSignIn;

    // Constructors
    public TrackUserSignins() {
        // Default constructor
    }

    public TrackUserSignins(Long userId, int signInCount, LocalDateTime lastSignIn) {
        this.userId = userId;
        this.signInCount = signInCount;
        this.lastSignIn = lastSignIn;
    }

    // Getters and Setters
    public Long getUserSignInId() {
        return userSignInId;
    }

    public void setUserSignInId(Long userSignInId) {
        this.userSignInId = userSignInId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getSignInCount() {
        return signInCount;
    }

    public void setSignInCount(int signInCount) {
        this.signInCount = signInCount;
    }

    public LocalDateTime getLastSignIn() {
        return lastSignIn;
    }

    public void setLastSignIn(LocalDateTime lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

    @Override
    public String toString() {
        return "TrackUserSignins{" +
                "userSignInId=" + userSignInId +
                ", userId=" + userId +
                ", signInCount=" + signInCount +
                ", lastSignIn=" + lastSignIn +
                '}';
    }

}
