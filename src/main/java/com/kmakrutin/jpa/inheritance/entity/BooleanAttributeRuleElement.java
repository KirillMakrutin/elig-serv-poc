package com.kmakrutin.jpa.inheritance.entity;

import com.kmakrutin.jpa.inheritance.dto.UserVerificationDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(ElementType.Constants.BOOLEAN_ATTRIBUTE)
public class BooleanAttributeRuleElement extends Element {
    @Type(type = "yes_no")
    @Column(name = "value")
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column
    private Attribute attribute;

    @Override
    public boolean evaluate(UserVerificationDto userVerificationDto) {
        List<Object> singleBooleanAttributeList = userVerificationDto.getUserAttributes().getOrDefault(attribute, Collections.emptyList());
        if (singleBooleanAttributeList.isEmpty()) {
            return true;
        }

        return enabled == Boolean.parseBoolean(singleBooleanAttributeList.get(0).toString());
    }

    @Override
    public Stream<Element> toRuleStream() {
        return Stream.of(this);
    }
}
