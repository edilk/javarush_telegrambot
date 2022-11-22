package com.github.javarushcommunity.jrtb.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Telegram user entity
 */
@Data
@Entity
@Table(name = "tg_user")
public class TelegramUser {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<GroupSub> groupSubs;
}
