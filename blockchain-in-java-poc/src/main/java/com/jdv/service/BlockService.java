package com.jdv.service;

import com.jdv.entity.Block;
import com.jdv.utils.BlockUtils;
import com.jdv.utils.HashUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlockService {
    private static final ArrayList<Block> blockchainInMemory = new ArrayList<Block>() {{
        add(Block.getGenesis());
    }};

    public Block nextBlock(String nextData) {
        Block latestBlock = getLatestBlock();
        long nextIndex = latestBlock.getIndex() + 1;
        long nextTimestamp = new Date().getTime();
        String previousHash = latestBlock.getPreviousHash();
        String nextHash = HashUtils.nextHash(nextIndex, nextTimestamp, nextData, previousHash);
        return new Block(nextIndex, nextTimestamp, nextData, previousHash, nextHash);
    }

    // ---------------------------------------------------------------------------------------------
    // Private Methods
    // ---------------------------------------------------------------------------------------------
    private Block getLatestBlock() {
        return blockchainInMemory.get(blockchainInMemory.size() - 1);
    }
}
