package com.one.aim.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentBO implements Serializable {

    private static final long serialVersionUID = 2303097610048429829L;

    private Long docid;

    private String name;

    private String title;

    private String type;

    private String description;

    private LocalDateTime on;

    private String by;

    public AttachmentBO(Long docid, String name, String type) {
        super();
        this.docid = docid;
        this.name = name;
        this.type = type;
    }

}
