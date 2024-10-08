package com.example.project3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.project3.entity.Member;

@EnableJpaRepositories
@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
  @Query("SELECT m FROM Member m WHERE m.MaTV = :MaTV AND m.Password = :Password")
  Optional<Member> findOneByIdAndPassword(int MaTV, String Password);

  @Query("SELECT m FROM Member m WHERE m.Email = :Email")
  Member findByEmail(String Email);

  @Query("SELECT m FROM Member m WHERE m.MaTV = :MaTV")
  boolean existsByMaTV(int MaTV);

  @Query("SELECT m FROM Member m WHERE m.Email = :Email")
  boolean existsByEmail(String Email);

  @Query("SELECT m FROM Member m WHERE m.SDT = :SDT")
  boolean existsBySDT(String SDT);

  public Member findByResetPasswordToken(String token);
}
