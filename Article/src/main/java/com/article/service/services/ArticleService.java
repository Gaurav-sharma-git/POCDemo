package com.article.service.services;

import com.article.service.entities.Article;

import com.article.service.entities.SubscriberDTO;
import com.article.service.entities.Subscription;


import java.util.List;

public interface ArticleService {

    // Create a new article
    Article createArticle(Article articleDto, Long authorUserId);

    // Retrieve an article by ID
    Article getArticleById(int articleId);

    // Subscribe to an article
    void subscribeToArticle(Subscription subscription);
    // Retrieve subscribers of an article
    List<SubscriberDTO> getSubscribers(int articleId);
    // Example: Search employees whose names start with a given prefix
    List<Article> searchArticlesByTitle(String prefix);
}

