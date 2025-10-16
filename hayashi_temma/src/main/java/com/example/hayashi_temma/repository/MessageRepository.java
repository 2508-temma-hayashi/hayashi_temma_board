package com.example.hayashi_temma.repository;

import com.example.hayashi_temma.repository.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    //MessageEntityのmをすべて取り出して
    //userテーブルとJOINして
    //「条件追加」して
    //さらに条件追加して該当するもの残して
    //降順で取得
    @Query("""
    SELECT m FROM Message m
    JOIN FETCH m.user
    WHERE m.createdDate BETWEEN :startDate AND :endDate
    AND (:category IS NULL OR m.category LIKE %:category%)
    ORDER BY m.createdDate DESC
""")
    //上のクエリを動かすメソッド
    List<Message> findFilteredMessages(
            //クエリ内の変数にあてはめる
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("category") String category
    );


}
