package com.github.javarushcommunity.jrtb.service;

/**
 * Service for finding new articles
 */
public interface FindNewPostsService {

    /**
     * Find new articles and notify subscribers about it
     */
    void findNewPosts();
}
