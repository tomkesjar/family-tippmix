package kt.tippmix.model;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="player")
public class User implements UserDetails {
    public enum AuthProvider {
        LOCAL_USER, GOOGLE, FACEBOOK
    }

    public enum Role {
        PLAYER, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="username")
    private String userName;

    @Column(name="secretname")
    private String secretName;

    @Column(name="secretfilename")
    private String secretFileName;

    @Column(name="winner")
    private String favouriteNation;

    @Column(name="goal")
    private String goalScorerNationality;

    @Column(name="mostgoals")
    private String mostGoals;

    @Column(name="pw", nullable = true) //OAuth users won't have passwords
    @Nonnull private String pw;

    @Column(name="emailaddress", nullable = false, unique=true)
    @Nonnull private String email;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;        //LOCAL, GOOGLE, FACEBOOK

    @Enumerated(EnumType.STRING)
    @Column(name ="roles")
    private Role role;        //PLAYER, ADMIN

    private int points;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
        return true;
    }

    //    No Arg constructor required for JPA
    public User() {}

    //Constructor for LOCAL user
    public User(long id, String userName, String secretName, String secretFileName, String favouriteNation, String goalScorerNationality, String pw, String email, AuthProvider provider, Role role, int points) {
        this.id = id;
        this.userName = userName;
        this.secretName = secretName;
        this.secretFileName = secretFileName;
        this.favouriteNation = favouriteNation;
        this.goalScorerNationality = goalScorerNationality;
        this.pw = pw;
        this.email = email;
        this.provider = provider;
        this.role = role;
        this.points = points;
    }

    //Constructor for OAuth user (GOOGLE, FACEBOOK)
    public User(String userName, String secretName, String secretFileName, String favouriteNation, String goalScorerNationality, String email, AuthProvider provider, Role role) {
        this.userName = userName;
        this.secretName = secretName;
        this.secretFileName = secretFileName;
        this.favouriteNation = favouriteNation;
        this.goalScorerNationality = goalScorerNationality;
        this.points = 0;
        this.email = email;
        this.provider = provider;
        this.role = role;
        this. pw = null;    //Oauth does not give pw
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getSecretName() {
        return secretName;
    }

    public User setSecretName(String secretName) {
        this.secretName = secretName;
        return this;
    }

    public String getFavouriteNation() {
        return favouriteNation;
    }

    public User setFavouriteNation(String favouriteNation) {
        this.favouriteNation = favouriteNation;
        return this;
    }

    public String getGoalScorerNationality() {
        return goalScorerNationality;
    }

    public User setGoalScorerNationality(String goalScorerNationality) {
        this.goalScorerNationality = goalScorerNationality;
        return this;
    }

    public int getPoints() {
        return points;
    }

    public User setPoints(int points) {
        this.points = points;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public User setProvider(AuthProvider provider) {
        this.provider = provider;
        return this;
    }

    public String getPw() {
        return pw;
    }

    public User setPw(String pw) {
        this.pw = pw;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public String getSecretFileName() {
        return secretFileName;
    }

    public void setSecretFileName(String secretFileName) {
        this.secretFileName = secretFileName;
    }

    public String getMostGoals() {
        return mostGoals;
    }

    public void setMostGoals(String mostGoals) {
        this.mostGoals = mostGoals;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", secretName='" + secretName + '\'' +
                ", favouriteNation='" + favouriteNation + '\'' +
                ", goalScorerNationality='" + goalScorerNationality + '\'' +
                ", points=" + points +
                '}';
    }
}
