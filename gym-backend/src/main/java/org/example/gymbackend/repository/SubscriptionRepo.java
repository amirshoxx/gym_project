package org.example.gymbackend.repository;
import org.example.gymbackend.entity.Subscription;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.projection.SubscriptionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, UUID> {
    @Query(value = "SELECT u.id as userId, s.id AS id, " +
            "u.phone_number AS phoneNumber, " +
            "u.image AS image, " +
            "u.full_name AS fullName, " +
            "s.price AS price, " +
            "s.start_time AS startTime, " +
            "s.status AS status, " +
            "s.name AS name, " +
            "s.end_time AS endTime, " +
            "s.day_count AS dayCount, " +
            "s.limited AS limited " +
            "FROM subscription s " +
            "right JOIN users u ON s.user_id = u.id " +
            "ORDER BY u.full_name",
            nativeQuery = true)

    List<SubscriptionProjection> getSubsc();
     Optional<Subscription> findByUserId(UUID userId);
    Optional<Subscription> findById(UUID id);
    @Query(value = "SELECT DISTINCT u.id as userId, s.id AS id, " +
            "u.phone_number AS phoneNumber, " +
            "u.image AS image, " +
            "u.full_name AS fullName, " +
            "s.price AS price, " +
            "s.start_time AS startTime, " +
            "s.status AS status, " +
            "s.name AS name, " +
            "s.end_time AS endTime, " +
            "s.day_count AS dayCount, " +
            "s.limited AS limited " +
            "FROM subscription s " +
            "RIGHT JOIN users u ON s.user_id = u.id " +
            "WHERE u.phone_number LIKE CONCAT('%', :phoneNumber, '%') " +
            "ORDER BY u.full_name",
            nativeQuery = true)
    List<SubscriptionProjection> search(@Param("phoneNumber") String phoneNumber);

}
