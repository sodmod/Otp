package com.memmcol.Otp.Repository;

import com.memmcol.Otp.Entity.CustomerEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Component
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Override
    @NotNull
    Optional<CustomerEntity> findById(@NotNull Long id);

    @Modifying
    @Query(value = "update Customer_Entity  set status = true where email = ?1 and Otp = ?2", nativeQuery = true)
    void findByEmail(@NotNull String email, int Otp);

    Optional<CustomerEntity> findByEmail(@NotNull String email);

    @Query(value = "select c.otp from Customer_Entity c where c.email = ?1", nativeQuery = true)
    Integer findByEmail1(@NotNull String email);

    @Query(value = "select c.status from Customer_Entity c where c.email = ?1", nativeQuery = true)
    Boolean findByStatus(@NotNull String email);

    @Modifying
    @Query(value = "update Customer_Entity set confirmed_at = ?1 where email = ?2", nativeQuery = true)
    void findByConfirmedAt(@NotNull LocalDateTime confirmedAt, @NotNull String email);

    @Modifying
    @Query(value = "update Customer_Entity set otp =?1 where email = ?2", nativeQuery = true)
    void updateOtp(int otp, @NotNull String email);

}
