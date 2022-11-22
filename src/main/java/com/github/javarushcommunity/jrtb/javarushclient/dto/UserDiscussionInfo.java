package com.github.javarushcommunity.jrtb.javarushclient.dto;

import lombok.Data;

/**
 * DTO for User Discussion Info
 */
@Data
public class UserDiscussionInfo {
    private Boolean isBookmarked;
    private Integer lastTime;
    private Integer newCommentsCount;
}
