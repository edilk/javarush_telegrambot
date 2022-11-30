package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.javarushclient.JavaRushPostClient;
import com.github.javarushcommunity.jrtb.javarushclient.dto.PostInfo;
import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindNewPostsServiceImpl implements FindNewPostsService {

    public static final String JAVARUSH_WEB_POST_FORMAT = "https://javarush.com/groups/posts/%s";

    private final GroupSubService groupSubService;
    private final JavaRushPostClient javaRushPostClient;
    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public FindNewPostsServiceImpl(GroupSubService groupSubService,
                                   JavaRushPostClient javaRushPostClient,
                                   SendBotMessageService sendBotMessageService) {
        this.groupSubService = groupSubService;
        this.javaRushPostClient = javaRushPostClient;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void findNewPosts() {
        groupSubService.findAll().forEach(groupSub -> {
            List<PostInfo> newPosts = javaRushPostClient
                    .findNewPosts(groupSub.getId(), groupSub.getLastPostId());

            setNewLastPostId(groupSub, newPosts);

            notifySubscribersAboutNewPosts(groupSub, newPosts);
        });
    }

    private void notifySubscribersAboutNewPosts(GroupSub gSub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);
        List<String> messageWithNewArticles = newPosts.stream()
                .map(post -> String.format("✨Вышла новая статья <b>%s</b> в группе <b>%s</b>.✨\n\n" +
                        "<b>Описание:</b> %s\n\n" +
                        "<b>Ссылка:</b> %s\n",
                        post.getTitle(), gSub.getTitle(), post.getDescription(),
                        getPostUrl(post.getKey())))
                .collect(Collectors.toList());

        gSub.getUsers().stream()
                .filter(TelegramUser::isActive)
                .forEach(it -> sendBotMessageService.sendMessage(it.getChatId(), messageWithNewArticles));
    }

    private void setNewLastPostId(GroupSub groupSub, List<PostInfo> newPosts) {
        newPosts.stream().mapToInt(PostInfo::getId).max()
                .ifPresent(id -> {
                    groupSub.setLastPostId(id);
                    groupSubService.save(groupSub);
                });
    }

    private String getPostUrl(String key) {
        return String.format(JAVARUSH_WEB_POST_FORMAT, key);
    }
}
