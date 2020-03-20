package com.kmakrutin.jpa.inheritance.entity;

import com.kmakrutin.jpa.inheritance.dto.UserVerificationDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(ElementType.Constants.HOST_CODE_ATTRIBUTE)
public class HostCodeRuleElement extends Element {
    @Column(name = "value")
    @Convert(converter = ListToStringConverter.class)
    private List<String> codes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column
    private Attribute attribute;

    @Column(name = "inclusion")
    @Enumerated(EnumType.STRING)
    private Inclusion inclusion;

    @Column(name = "matching")
    @Enumerated(EnumType.STRING)
    private Matching matching;

    public HostCodeRuleElement addCode(String code) {
        this.codes.add(code);
        return this;
    }

    @Override
    public boolean checkEligibility(UserVerificationDto userVerificationDto) {
        return evaluate(userVerificationDto);
    }

    @Override
    public boolean evaluate(UserVerificationDto userVerificationDto) {
        List<String> incomingCodes = userVerificationDto.getUserAttributes()
                .getOrDefault(attribute, Collections.emptyList())
                .stream()
                .map(String::valueOf)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        boolean codeMatched = Matching.ANY.equals(matching)
                ? CollectionUtils.containsAny(incomingCodes, this.codes )
                : CollectionUtils.containsAll(incomingCodes, this.codes);

        return (codeMatched && Inclusion.INCLUDE.equals(inclusion))
                || (!codeMatched && Inclusion.EXCLUDE.equals(inclusion));
    }

    @Override
    public Stream<Element> toRuleStream() {
        return Stream.of(this);
    }
}
