package com.kmakrutin.jpa.inheritance.entity;

import com.kmakrutin.jpa.inheritance.dto.UserVerificationDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(ElementType.Constants.HOST_CODE_ATTRIBUTE)
public class HostCodeRuleElement extends Element {
    @Column(name = "value")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column
    private Attribute attribute;

    @Column(name = "inclusion")
    @Enumerated(EnumType.STRING)
    private Inclusion inclusion;

    @Column(name = "matching")
    @Enumerated(EnumType.STRING)
    private Matching matching;

    @Override
    public boolean evaluate(UserVerificationDto userVerificationDto) {
        // TODO code is comma-separated value?
        List<Object> codes = userVerificationDto.getUserAttributes().getOrDefault(attribute, Collections.emptyList());

        if (Inclusion.INCLUDE.equals(inclusion)) {
            return codes.stream().anyMatch(attributeCode -> code.equalsIgnoreCase(String.valueOf(attributeCode))) && (Matching.ANY.equals(matching) || codes.size() == 1);
        } else {
            return codes.stream().noneMatch(attributeCode -> code.equalsIgnoreCase(String.valueOf(attributeCode))) || (Matching.ALL.equals(matching) && codes.size() != 1);
        }
    }

    @Override
    public Stream<Element> toRuleStream() {
        return Stream.of(this);
    }
}
