package com.agk.crm.entity;


import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table( name = "user_info" )
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String serName;

    @Column(unique = false, nullable = false)
    private String patrol;

    @Column(unique = true, nullable = true)
    private String phone;

    @Column(nullable = true)
    private Date dataBirth;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}
