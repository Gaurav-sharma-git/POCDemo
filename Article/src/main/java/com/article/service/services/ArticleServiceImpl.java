package com.article.service.services;

import com.article.service.entities.Article;
import com.article.service.entities.SubscriberDTO;
import com.article.service.entities.Subscription;
import com.article.service.repositories.ArticleRepository;
import com.article.service.repositories.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserServiceClient userServiceClient;
    @Override
    public Article createArticle(Article article, Long authorUserId) {
        log.info("Into createArticle at service level");
        article.setAuthorUserId(authorUserId);
        Article article1 = articleRepository.save(article);
        return article1;
    }

    @Override
    public Article getArticleById(int articleId) {
        log.info("Into getArticleById at service level");
        return articleRepository.findById(articleId).orElse(null);
    }


    @Override
    public void subscribeToArticle(Subscription subscription) {
        log.info("Into subscribeToArticle at service level");
        subscriptionRepository.save(subscription);
    }

    @Override
    public List<SubscriberDTO> getSubscribers(int articleId) {
        log.info("Into getSubscribers at service level");
        List<Subscription> subscriptions = subscriptionRepository.findByArticleId(articleId);
        List<Long> subscriberUserIds = subscriptions.stream().map(Subscription::getSubscriberUserId).collect(Collectors.toList());


          // without stream
//        List<SubscriberDTO> list = new ArrayList<>();
//        for(long userId : subscriberUserIds)
//        {
//            SubscriberDTO user  = userServiceClient.getUser(userId);
//            list.add(user);
//        }


        // with stream
        List<SubscriberDTO> list = subscriberUserIds.stream()
                .map(userServiceClient::getUser)
                .collect(Collectors.toList());
        return  list;
    }

    @Override
    public List<Article> searchArticlesByTitle(String prefix) {
        log.info("Into searchArticlesByTitle at service level");
       List<Article> articles = articleRepository.findByTitleStartingWithIgnoreCase(prefix);
       return articles;
    }

}
