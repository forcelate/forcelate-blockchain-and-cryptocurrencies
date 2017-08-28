package com.jdv.entity;

import com.jdv.utils.HashUtils;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Block {
    private long index;
    private long timestamp;
    private String data;
    private String previousHash;
    private String hash;

    public static Block getGenesis() {
        return new Block(0, 1504137600, "Terminator Genisys", "0", "09a942594b2be3a9ab83959f8e40ce14cc617819600a43a13e03794acd63c3f0");
    }

    public String recalculateHash() {
        return HashUtils.nextHash(this.getIndex(), this.getTimestamp(), this.getData(), this.getPreviousHash());
    }
}
