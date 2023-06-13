package bookclip.server.domain.member.entity;

import bookclip.server.global.audit.Auditable;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MemberId;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}
