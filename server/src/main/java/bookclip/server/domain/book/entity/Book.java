package bookclip.server.domain.book.entity;

import bookclip.server.global.audit.Auditable;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
public class Book extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column
    private String editor;

    @Column
    private int bookPages;

    // 파일로 변경할 것
    @Column
    private String bookImage;

    // enum으로 변경할 것
    @Column
    private String genre;
}
