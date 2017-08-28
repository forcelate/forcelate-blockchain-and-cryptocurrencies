package com.jdv.service;

import com.jdv.entity.Block;
import com.jdv.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockService.class);

    private static final List<Block> blockchainInMemory = new ArrayList<>();

    @PostConstruct
    public void init() {
        blockchainInMemory.add(Block.getGenesis());
    }

    public List<Block> getBlockchain() {
        return blockchainInMemory;
    }

    public boolean addBlock(String nextBlockData) {
        Block nextBlock = nextBlock(nextBlockData);
        if (isValid(nextBlock)) {
            blockchainInMemory.add(nextBlock);
            return true;
        }
        return false;
    }

    // ---------------------------------------------------------------------------------------------
    // Private Methods
    // ---------------------------------------------------------------------------------------------
    private Block getLatestBlock() {
        return blockchainInMemory.get(blockchainInMemory.size() - 1);
    }

    private Block nextBlock(String nextBlockData) {
        Block latestBlock = getLatestBlock();
        long nextIndex = latestBlock.getIndex() + 1;
        long nextTimestamp = new Date().getTime() / 1000;
        String nextPreviousHash = latestBlock.getHash();
        String nextHash = HashUtils.nextHash(nextIndex, nextTimestamp, nextBlockData, nextPreviousHash);
        return new Block(nextIndex, nextTimestamp, nextBlockData, nextPreviousHash, nextHash);
    }

    private boolean isValid(Block nextBlock) {
        Block previousBlock = getLatestBlock();
        if (nextBlock.getIndex() != previousBlock.getIndex() + 1) {
            LOGGER.info("Indexes verification failed. Expected index = '{}'", previousBlock.getIndex() + 1);
            return false;
        }
        if (!nextBlock.getPreviousHash().equals(previousBlock.getHash())) {
            LOGGER.info("Previous hash verification failed. Expected previous hash = '{}'", previousBlock.getHash());
            return false;
        }
        String expectedHash = nextBlock.recalculateHash();
        // WARNING: here could be mining algorithm check
        if (!nextBlock.getHash().equals(expectedHash)) {
            LOGGER.info("Hash verification failed. Expected hash = '{}'", expectedHash);
            return false;
        }
        return true;
    }
}
