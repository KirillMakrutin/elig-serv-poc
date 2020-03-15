package com.kmakrutin.jpa.inheritance.controller;

import com.kmakrutin.jpa.inheritance.ExpressionEvaluator;
import com.kmakrutin.jpa.inheritance.dto.UserVerificationDto;
import com.kmakrutin.jpa.inheritance.entity.RuleSet;
import com.kmakrutin.jpa.inheritance.repo.RuleSetRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RuleSetController {
    private final RuleSetRepo repo;
    private final ExpressionEvaluator expressionEvaluator;

    @GetMapping("/rules")
    List<RuleSet> findAll() {
        List<RuleSet> all = repo.findAll();
        all.forEach(ruleSet -> log.info(ruleSet.toExpression()));
        return all;
    }

    @GetMapping("/rules/squeeze")
    List<RuleSet> findAllSqueezed() {
        return repo.findAllSqueezed();
    }

    @PostMapping(value = "/rules/match", consumes = "application/json")
    boolean match(@RequestBody UserVerificationDto userVerificationDto) {
        return repo.findById(userVerificationDto.getRuleSetId())
                .map(ruleSet -> expressionEvaluator.evaluate(ruleSet, userVerificationDto))
                .orElse(false);
    }

    @PostMapping(value = "/rule", produces = "application/json", consumes = "application/json")
    RuleSet save(@RequestBody RuleSet ruleSet) {
        return repo.save(ruleSet);
    }
}
