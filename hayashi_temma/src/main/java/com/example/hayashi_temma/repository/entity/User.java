package com.example.hayashi_temma.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    private String account;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(name = "branch_id", nullable = false)
    private Integer branchId;

    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Column(name = "is_stopped", nullable = false)
    private Integer isStopped;
    // 0:稼働, 1:停止

    @Column(name = "created_date", nullable = false, insertable = false, updatable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date", nullable = false, insertable = false, updatable = false)
    private Timestamp updatedDate;

    //支店名
    //@ManyToOneは「多対一（Many-to-One）」の関係を定義
    //@JoinColumn(name = "user_id")	結合に使う外部キーの列を指定している
    @ManyToOne
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    private Branch branch;

    //部署名
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;
}
