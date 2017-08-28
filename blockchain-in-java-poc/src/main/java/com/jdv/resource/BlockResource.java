package com.jdv.resource;

import com.jdv.Application;
import com.jdv.entity.Block;
import com.jdv.service.BlockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlockResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockResource.class);

    private final BlockService blockService;

    @Autowired
    public BlockResource(BlockService blockService) {
        this.blockService = blockService;
    }

    @RequestMapping(value = "/blockchain", method = RequestMethod.GET)
    public ResponseEntity getBlockchain() {
        return ResponseEntity.ok().body(blockService.getBlockchain());
    }

    @RequestMapping(value = "/mine/{blockData}", method = RequestMethod.POST)
    public ResponseEntity mine(@PathVariable("blockData") String nextBlockData) {
        LOGGER.info("Mining blockchain with next block data '{}'", nextBlockData);
        boolean isAdded = blockService.addBlock(nextBlockData);
        if (isAdded) {
            LOGGER.info("Mining process succeeded with next block data '{}'", nextBlockData);
            return ResponseEntity.ok().build();
        } else {
            LOGGER.info("Mining process failed with next block data '{}'", nextBlockData);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
