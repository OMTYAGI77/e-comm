package com.one.vm.core;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// Responsible for return success/Failure message
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDataRs implements Serializable {

    private static final long serialVersionUID = -1308725518620161628L;

    private String message;

}
