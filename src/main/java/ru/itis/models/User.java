package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.itis.dto.UserDto;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "finproj", name = "users_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private UserState userState;
    @Column(name = "confirm_link")
    private String confirmLink;
    @Column(name = "ava_path")
    private String avaPath;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "finproj", name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Booking> bookings;

    public User() {
        roles = new HashSet<>();
    }


    public static User fromUserDto(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .avaPath(userDto.getAvaPath())
                .build();
    }

    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .avaPath(user.getAvaPath())
                .email(user.getEmail())
                .bookings(user.getBookings().stream().map(Booking::toBookingDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }
}
