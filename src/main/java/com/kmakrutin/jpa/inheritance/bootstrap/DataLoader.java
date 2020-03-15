package com.kmakrutin.jpa.inheritance.bootstrap;

import com.kmakrutin.jpa.inheritance.entity.*;
import com.kmakrutin.jpa.inheritance.repo.RuleSetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {
    private final RuleSetRepo repo;

    @Override
    public void run(String... args) {
        RuleSet ruleSet = new RuleSet();
        ruleSet.setName("Test-" + UUID.randomUUID().toString());

        LogicalGroupElement rootGroupElement = new LogicalGroupElement();
        rootGroupElement.setOperator(Operator.OR);

        HostCodeRuleElement ruleElement1 = new HostCodeRuleElement();
        ruleElement1.setCode("A");
        ruleElement1.setInclusion(Inclusion.INCLUDE);
        ruleElement1.setMatching(Matching.ANY);
        ruleElement1.setAttribute(Attribute.COLLEAGUE_CODE);

        HostCodeRuleElement ruleElement2 = new HostCodeRuleElement();
        ruleElement2.setCode("B");
        ruleElement2.setInclusion(Inclusion.EXCLUDE);
        ruleElement2.setMatching(Matching.ALL);
        ruleElement2.setAttribute(Attribute.CLASS_CODE);

        LogicalGroupElement nestedGroupElement = new LogicalGroupElement();
        nestedGroupElement.setOperator(Operator.AND);

        HostCodeRuleElement ruleElement3 = new HostCodeRuleElement();
        ruleElement3.setCode("C");
        ruleElement3.setInclusion(Inclusion.EXCLUDE);
        ruleElement3.setMatching(Matching.ANY);
        ruleElement3.setAttribute(Attribute.COLLEAGUE_CODE);

        HostCodeRuleElement ruleElement4 = new HostCodeRuleElement();
        ruleElement4.setCode("D");
        ruleElement4.setInclusion(Inclusion.EXCLUDE);
        ruleElement4.setMatching(Matching.ANY);
        ruleElement4.setAttribute(Attribute.STUDENT_LEVEL);

        BooleanAttributeRuleElement booleanAttributeRuleElement = new BooleanAttributeRuleElement();
        booleanAttributeRuleElement.setEnabled(true);
        booleanAttributeRuleElement.setAttribute(Attribute.VETERAN_STATUS);

        nestedGroupElement.setElements(Arrays.asList(ruleElement3, ruleElement4));

        rootGroupElement.setElements(Arrays.asList(ruleElement1, booleanAttributeRuleElement, nestedGroupElement, ruleElement2));
        ruleSet.setRootGroup(rootGroupElement);

        repo.save(ruleSet);
    }
}
