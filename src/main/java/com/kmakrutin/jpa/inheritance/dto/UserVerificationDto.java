package com.kmakrutin.jpa.inheritance.dto;

import com.kmakrutin.jpa.inheritance.entity.Attribute;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
public class UserVerificationDto {
    private final Map<Attribute, List<String>> userAttributes = new HashMap<>();
    private int ruleSetId;

    public int getRuleSetId() {
        return ruleSetId;
    }

    public void setRuleSetId(int ruleSetId) {
        this.ruleSetId = ruleSetId;
    }

    public Map<Attribute, List<String>> getUserAttributes() {
        return userAttributes;
    }

    public UserVerificationDto addAttribute(Attribute attribute, String attributeValue) {
        this.userAttributes.computeIfAbsent(attribute, key -> new ArrayList<>()).add(attributeValue);
        return this;
    }
}
