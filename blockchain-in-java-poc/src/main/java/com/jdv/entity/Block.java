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
        return new Block(0, 1504137600, "TerminatorGenisys", "0", "c30c20c418da89d60508d7c1a4cf2364bb7652289081535c9fbc24586453655eb0e60218588b49b596e4cf9171c5177ea9778a4fe691a0b4ce4df08396c8eb75");
    }

    public String recalculateHash() {
        return HashUtils.nextHash(this.getIndex(), this.getTimestamp(), this.getData(), this.getPreviousHash());
    }
}
