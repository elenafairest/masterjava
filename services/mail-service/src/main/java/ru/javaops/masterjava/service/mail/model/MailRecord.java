package ru.javaops.masterjava.service.mail.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailRecord {
    private Integer id;
    @Column("to_list")
    private String toList;
    @Column("cc_list")
    private String ccList;
    private String subject;
    private String body;
    private String status;
    @Column("error_message")
    private String errorMessage;
    @Column("sent_time")
    private LocalDateTime sentTime;
}
