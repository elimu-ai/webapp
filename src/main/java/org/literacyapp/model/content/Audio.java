package org.literacyapp.model.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Entity
public class Audio extends Multimedia {
    
    @NotNull
    @Lob
    @Column(length=104857600) // 100MB
    private byte[] bytes;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
