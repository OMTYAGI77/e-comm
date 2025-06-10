package com.one.aim.rq;

import org.springframework.core.io.InputStreamSource;

import com.one.vm.core.BaseVM;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentRq extends BaseVM {

    private static final long serialVersionUID = -6325010288537883666L;

    private InputStreamSource byteSource;

    private String name;

    private String type;

    private String contentType;

}

