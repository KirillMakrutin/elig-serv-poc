package com.kmakrutin.jpa.inheritance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kmakrutin.jpa.inheritance.dto.UserVerificationDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.*;
import java.util.stream.Stream;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LogicalGroupElement.class, name = ElementType.Constants.LOGICAL_GROUP),
        @JsonSubTypes.Type(value = HostCodeRuleElement.class, name = ElementType.Constants.HOST_CODE_ATTRIBUTE),
        @JsonSubTypes.Type(value = BooleanAttributeRuleElement.class, name = ElementType.Constants.BOOLEAN_ATTRIBUTE)
})
@EqualsAndHashCode
@Data
@Entity
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Polymorphism(type = PolymorphismType.IMPLICIT)
public abstract class Element {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long id;

    @Transient
    public abstract boolean checkEligibility(UserVerificationDto userVerificationDto);

    @JsonIgnore
    @Transient
    public String toExpression() {
        return "var" + id;
    }

    @JsonIgnore
    @Transient
    public boolean evaluate(UserVerificationDto userVerificationDto) {
        throw new UnsupportedOperationException();
    }

    @Transient
    @JsonIgnore
    public abstract Stream<Element> toRuleStream();
}
