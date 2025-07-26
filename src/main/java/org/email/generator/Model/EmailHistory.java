package org.email.generator.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "email_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String originalEmail;

    @Column(columnDefinition = "TEXT")
    private String replyEmail;

    private String tone;
    private String language;

    private String timestamp = java.time.LocalDateTime.now().toString(); // Optional
}
