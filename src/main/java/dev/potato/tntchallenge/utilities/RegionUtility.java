package dev.potato.tntchallenge.utilities;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegionUtility implements Serializable {
    private BoundingBox wall1;
    private BoundingBox wall2;
    private BoundingBox wall3;
    private BoundingBox wall4;
    private BoundingBox floor;
    private BoundingBox placeArea;
    private BoundingBox winArea;
    private boolean isSetup;

    public BoundingBox getWall1() {
        return wall1;
    }

    public void setWall1(BoundingBox wall1) {
        this.wall1 = wall1;
    }

    public BoundingBox getWall2() {
        return wall2;
    }

    public void setWall2(BoundingBox wall2) {
        this.wall2 = wall2;
    }

    public BoundingBox getWall3() {
        return wall3;
    }

    public void setWall3(BoundingBox wall3) {
        this.wall3 = wall3;
    }

    public BoundingBox getWall4() {
        return wall4;
    }

    public void setWall4(BoundingBox wall4) {
        this.wall4 = wall4;
    }

    public BoundingBox getFloor() {
        return floor;
    }

    public void setFloor(BoundingBox floor) {
        this.floor = floor;
    }

    public BoundingBox getPlaceArea() {
        return placeArea;
    }

    public void setPlaceArea(BoundingBox placeArea) {
        this.placeArea = placeArea;
    }

    public BoundingBox getWinArea() {
        return winArea;
    }

    public void setWinArea(BoundingBox winArea) {
        this.winArea = winArea;
    }

    public boolean isSetup() {
        return isSetup;
    }

    public void setSetup(boolean setup) {
        isSetup = setup;
    }

    public List<Block> boundingBoxToList(BoundingBox boundingBox, World world) {
        List<Block> blocksFound = new ArrayList<>();
        for (int x = (int) boundingBox.getMinX(); x <= (int) boundingBox.getMaxX(); x++) {
            for (int y = (int) boundingBox.getMinY(); y <= (int) boundingBox.getMaxY(); y++) {
                for (int z = (int) boundingBox.getMinZ(); z <= (int) boundingBox.getMaxZ(); z++) {
                    Block block = world.getBlockAt(x, y, z);
                    blocksFound.add(block);
                }
            }
        }
        return blocksFound;
    }
}