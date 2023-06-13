package bookclip.server.domain.clip.entity;

import bookclip.server.global.audit.Auditable;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
public class Clip extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clipId;

    @Column(nullable = false)
    private String content;
}
