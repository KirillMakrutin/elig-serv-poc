package com.kmakrutin.jpa.inheritance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
public class RuleSet {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Integer id;

    @Column
    private String name;

    public RuleSet() {
    }

    public RuleSet(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "root_element_id", referencedColumnName = "id")
    private LogicalGroupElement rootGroup;

    public String toExpression() {
        return rootGroup != null ? rootGroup.toExpression() : "";
    }

    @JsonIgnore
    @Transient
    public List<Element> getRules() {
        return rootGroup.getElements().stream().flatMap(Element::toRuleStream).collect(Collectors.toList());
    }
}
