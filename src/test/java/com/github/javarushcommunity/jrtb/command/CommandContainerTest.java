package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService =
                Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService =
                Mockito.mock(TelegramUserService.class);
        commandContainer = new CommandContainer(sendBotMessageService,
                telegramUserService);
    }
}
