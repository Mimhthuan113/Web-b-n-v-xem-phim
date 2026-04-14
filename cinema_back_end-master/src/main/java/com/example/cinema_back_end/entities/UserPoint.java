package com.example.cinema_back_end.entities;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "user_points")
public class UserPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Phương thức để thêm điểm
    public void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }

    // Phương thức để trừ điểm (khi người dùng đổi vé miễn phí)
    public void subtractPoints(int pointsToSubtract) {
        this.points -= pointsToSubtract;
    }
}
