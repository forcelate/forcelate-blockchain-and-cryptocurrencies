package com.jdv.resource;

import com.jdv.entity.Block;
import com.jdv.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlockResource {

    private final BlockService blockService;

    @Autowired
    public BlockResource(BlockService blockService) {
        this.blockService = blockService;
    }

    @RequestMapping(value = "blockchain", method = RequestMethod.GET)
    public List<Block> getBlocks() {
        return blockService.getBlockchain();
    }
}
