package com.kmakrutin.jpa.inheritance.entity;

import com.kmakrutin.jpa.inheritance.dto.UserVerificationDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(ElementType.Constants.LOGICAL_GROUP)
public class LogicalGroupElement extends Element {
    @Enumerated(EnumType.STRING)
    @Column(name = "value")
    private Operator operator;

    @OrderColumn(name = "element_order")
    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "parent_element_id")
    private List<Element> elements;


    @Override
    public boolean checkEligibility(UserVerificationDto userVerificationDto) {
        return CollectionUtils.isEmpty(elements)
                || Operator.AND.equals(this.operator)
                ? elements.stream().allMatch(element -> element.checkEligibility(userVerificationDto))
                : elements.stream().anyMatch(element -> element.checkEligibility(userVerificationDto));
    }

    @Override
    public String toExpression() {
        return "("
                + elements.stream()
                .map(Element::toExpression)
                .collect(Collectors.joining(" " + this.operator.toString().toLowerCase() + " "))
                + ")";
    }

    @Override
    public Stream<Element> toRuleStream() {
        return elements.stream().flatMap(Element::toRuleStream);
    }
}
