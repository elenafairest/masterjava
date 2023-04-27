package ru.javaops.masterjava.service.mail;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailJMSMessage implements Serializable {
    private static final long serialVersionUID = -7970625729578276901L;

    private @NotNull String users;
    private String subject;
    private @NotNull String body;
}
