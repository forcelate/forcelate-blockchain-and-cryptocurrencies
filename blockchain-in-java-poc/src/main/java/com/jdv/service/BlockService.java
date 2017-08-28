package com.jdv.service;

import com.jdv.entity.Block;
import com.jdv.utils.HashUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlockService {
    private static final List<Block> blockchainInMemory = new ArrayList<>();

    @PostConstruct
    public void init() {
        blockchainInMemory.add(Block.getGenesis());
    }

    public List<Block> getBlockchain() {
        return blockchainInMemory;
    }

    public Block nextBlock(String nextData) {
        Block latestBlock = getLatestBlock();
        long nextIndex = latestBlock.getIndex() + 1;
        long nextTimestamp = new Date().getTime();
        String previousHash = latestBlock.getPreviousHash();
        String nextHash = HashUtils.nextHash(nextIndex, nextTimestamp, nextData, previousHash);
        return new Block(nextIndex, nextTimestamp, nextData, previousHash, nextHash);
    }

    public boolean isNextBlockValid(Block nextBlock, Block previousBlock) {
        return (nextBlock.getIndex() != previousBlock.getIndex() ||
                !nextBlock.getPreviousHash().equals(previousBlock.getHash()) ||
                !nextBlock.getHash().equals(previousBlock.recalculateHash()));
    }

    // ---------------------------------------------------------------------------------------------
    // Private Methods
    // ---------------------------------------------------------------------------------------------
    private Block getLatestBlock() {
        return blockchainInMemory.get(blockchainInMemory.size() - 1);
    }
}
