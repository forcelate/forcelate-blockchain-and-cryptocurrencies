package com.jdv.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HashUtils {

    public static String nextHash(long index, long timestamp, String data, String previousHash) {
        String blockData = index + timestamp + data + previousHash;
        return DigestUtils.sha512Hex(blockData);
    }
}
