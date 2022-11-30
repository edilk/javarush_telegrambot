package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.dto.GroupStatDTO;
import com.github.javarushcommunity.jrtb.dto.StatisticsDTO;
import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static java.util.Collections.singletonList;

@DisplayName("Unit-level testing for StatisticsService")
public class StatisticsServiceTest {

    private GroupSubService groupSubService;
    private TelegramUserService telegramUserService;
    private StatisticsService statisticsService;

    @BeforeEach
    public void init() {
        groupSubService = Mockito.mock(GroupSubService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);
        statisticsService = new StatisticsServiceImpl(groupSubService, telegramUserService);
    }

    @Test
    public void shouldProperlySendStatDTO() {
        //given
        Mockito.when(telegramUserService.findAllInActiveUsers())
                .thenReturn(singletonList(new TelegramUser()));
        TelegramUser activeUser = new TelegramUser();
        activeUser.setGroupSubs(singletonList(new GroupSub()));
        Mockito.when(telegramUserService.retrieveAllActiveUsers())
                .thenReturn(singletonList(activeUser));
        GroupSub groupSub = new GroupSub();
        groupSub.setTitle("group");
        groupSub.setId(1);
        groupSub.setUsers(singletonList(new TelegramUser()));
        Mockito.when(groupSubService.findAll()).thenReturn(singletonList(groupSub));

        //when
        StatisticsDTO statisticsDTO = statisticsService.countBotStatistic();

        //then
        Assertions.assertNotNull(statisticsDTO);
        Assertions.assertEquals(1, statisticsDTO.getActiveUserCount());
        Assertions.assertEquals(1, statisticsDTO.getInactiveUserCount());
        Assertions.assertEquals(1.0, statisticsDTO.getAverageGroupCountByUser());
        Assertions.assertEquals(singletonList(new GroupStatDTO(
                groupSub.getId(), groupSub.getTitle(), groupSub.getUsers().size())),
                statisticsDTO.getGroupStatDTOs());
    }
}
