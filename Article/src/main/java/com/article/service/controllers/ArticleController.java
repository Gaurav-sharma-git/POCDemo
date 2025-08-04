package com.article.service.controllers;

import com.article.service.entities.Article;

import com.article.service.entities.SubscriberDTO;
import com.article.service.entities.Subscription;
import com.article.service.services.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleController {

    @Autowired
    ArticleService articleService;


    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article, @RequestParam Long authorUserId) {
        log.info("Into createArticle at controller level");
        Article createdArticle = articleService.createArticle(article, authorUserId);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Article> getArticle(@PathVariable int articleId) {
        log.info("Into getArticleById at controller level");
        Article article = articleService.getArticleById(articleId);
        if (article != null) {
            return new ResponseEntity<>(article, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeToArticle(@RequestBody Subscription subscription) {
        log.info("Into subscribeToArticle at controller level");
        articleService.subscribeToArticle(subscription);
        return new ResponseEntity<>("Subscribed to article successfully",HttpStatus.OK);
    }

    @GetMapping("/{articleId}/subscribers")
    public ResponseEntity<?> getSubscribers(@PathVariable int articleId) {
        log.info("Into getSubscribers at controller level");
        List<SubscriberDTO> subscribers = articleService.getSubscribers(articleId);
        if(!subscribers.isEmpty()) {
            return new ResponseEntity<>(subscribers, HttpStatus.OK);
        }
        return new ResponseEntity<>("No Subscribers for this article", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchArticles(@RequestParam String namePrefix) {
        log.info("Into searchArticles at controller level");
        List<Article> articleList = articleService.searchArticlesByTitle(namePrefix);
        if(!articleList.isEmpty()) {
            return ResponseEntity.ok(articleList);
        }
        return new ResponseEntity<>("No Articles exists", HttpStatus.NOT_FOUND);
    }

    public void mymethodneww(){
        System.out.println("hfdd");
    }
}

