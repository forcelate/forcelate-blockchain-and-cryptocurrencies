package com.jdv.utils;

import com.jdv.entity.Block;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashUtilsTests {

    @Test
    public void genesisNextBlock() {
        // Arrange
        Block block = Block.getGenesis();

        // Act
        String nextHash = HashUtils.nextHash(block.getIndex(), block.getTimestamp(), block.getData(), block.getPreviousHash());

        // Assert
        assertEquals(block.getHash(), nextHash);
    }
}
