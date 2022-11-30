package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.dto.GroupStatDTO;
import com.github.javarushcommunity.jrtb.dto.StatisticsDTO;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static com.github.javarushcommunity.jrtb.command.AbstractCommandTest.prepareUpdate;
import static com.github.javarushcommunity.jrtb.command.CommandName.STAT;
import static com.github.javarushcommunity.jrtb.command.StatCommand.STAT_MESSAGE;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest {

    private SendBotMessageService sendBotMessageService;
    private StatisticsService statisticsService;
    private Command statCommand;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        statisticsService = Mockito.mock(StatisticsService.class);
        statCommand = new StatCommand(sendBotMessageService, statisticsService);
    }

    @Test
    public void shouldProperlySendMessage() {
        //given
        Long chatId = 1234567L;
        GroupStatDTO groupStatDTO = new GroupStatDTO(1, "group", 1);
        StatisticsDTO statisticsDTO = new StatisticsDTO(1, 1, Collections.singletonList(groupStatDTO), 2.5);
        Mockito.when(statisticsService.countBotStatistic())
                .thenReturn(statisticsDTO);

        //when
        statCommand.execute(prepareUpdate(chatId, STAT.getCommandName()));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, String.format(STAT_MESSAGE,
                statisticsDTO.getActiveUserCount(),
                statisticsDTO.getInactiveUserCount(),
                statisticsDTO.getAverageGroupCountByUser(),
                String.format("%s (id = %s) - %s подписчиков",
                        groupStatDTO.getTitle(),
                        groupStatDTO.getId(),
                        groupStatDTO.getActiveUserCount())));
    }
}
