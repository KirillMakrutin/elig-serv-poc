package com.kmakrutin.jpa.inheritance;

import com.kmakrutin.jpa.inheritance.dto.UserVerificationDto;
import com.kmakrutin.jpa.inheritance.entity.RuleSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jexl3.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EligibilityCodeExpressionEvaluator implements ExpressionEvaluator {
    private static final JexlEngine JEXL_ENGINE = new JexlBuilder().strict(true).silent(false).create();

    @Override
    public boolean evaluate(RuleSet ruleSet, UserVerificationDto userVerificationDto) {
        String expressionString = ruleSet.toExpression();

        log.info("Evaluating {} expression against verification data: {}", expressionString, userVerificationDto);

        JexlExpression expression = JEXL_ENGINE.createExpression(expressionString);
        JexlContext context = new MapContext();
        ruleSet.getRules().forEach(rule -> context.set(rule.toExpression(), rule.evaluate(userVerificationDto)));

        return (Boolean) expression.evaluate(context);
    }
}
