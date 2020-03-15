package com.kmakrutin.jpa.inheritance;

import com.kmakrutin.jpa.inheritance.dto.UserVerificationDto;
import com.kmakrutin.jpa.inheritance.entity.RuleSet;

public interface ExpressionEvaluator {
    boolean evaluate(RuleSet ruleSet, UserVerificationDto userVerificationDto);
}
