package app.game.gamefield.elements.mobiles.players;

import app.game.gamefield.elements.immovables.Degradable;
import app.game.gamefield.elements.mobiles.Mobile;
import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.elements.rendering.HitBox;
import app.game.gamefield.elements.rendering.InterchangeableImage;
import app.game.gamefield.elements.rendering.LoopingPictures;
import app.game.gamefield.rooms.Room;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.awt.geom.Point2D;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

/**
 * Character
 */
public class Player extends Mobile {
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private int money, bombs, keys;
    protected InterchangeableImage head;
    protected LoopingPictures legs;
    private boolean isPrintingHead;

    public enum Characters {
        Isaac;
    }

    public Player(GameValues gameValues, Characters c, Point2D.Double location) {
        super(gameValues, location);
        money = bombs = keys = 0;
        sizeInBlocks = new Point2D.Double(.9, 1);
        setCharacterStats(c);
        setFullHealth();
    }

    private void setCharacterStats(Characters character) {
        switch (character) {
        case Isaac:
            this.accelerationRate = 12.0;
            this.maxSpeed = 6.0;
            this.maxHealth = 6;
            loadImages(gameValues.ISSAC_FILE);
            break;
        default:
            break;
        }
    }

    private void loadImages(String imageLocation) {
        SpriteSheet ss = new SpriteSheet(imageLocation);
        loadLegs(ss);
        loadHeads(ss);
    }

    @Override
    public void tick(Room r) {
        accelerate(moveUp, moveDown, moveLeft, moveRight);
        updateVelocity();
        // head.setCurrentImageIndex(6);
        setImagesBasedOnVelocity();
        updateLoopingPictures();
        testCollisionAndMove(r);
        // System.out.println("Position: " + location.getX() + ", " + location.getY());
    }

    @Override
    protected Point2D.Double onCollision(Double newLocation, Drawable collidingElement, Room room) {
        
        if (collidingElement.getClass() == Degradable.class) {
            return degradableCollision(newLocation, collidingElement, room);
        }
        
        //Default is stop motion and remain still.
        this.velocityPercent.x = this.velocityPercent.y = 0;
        return this.location;
        //this.velocityPercent
    }

    private Point2D.Double degradableCollision(Double newLocation, Drawable collidingElement, Room room) {
        Point2D.Double onlyY = new Point2D.Double(newLocation.getX(), location.getY());
        Drawable onlyYCollision = room.checkCollisions(this, onlyY);
        if (onlyYCollision==null) {
            this.velocityPercent.y = 0;
            return onlyY;
        }

        Point2D.Double onlyX = new Point2D.Double(location.getX(), newLocation.getY());
        Drawable onlyXCollision = room.checkCollisions(this, onlyX);
        if (onlyXCollision==null) {
            this.velocityPercent.x = 0;
            return onlyX;
        }

        this.velocityPercent.x = this.velocityPercent.y = 0;
        return this.location;
    }

    @Override
    public void render(Graphics g) {
        isPrintingHead = false;
        super.render(g);

        isPrintingHead = true;
        super.render(g);
    }

    @Override
    protected Point2D.Double getLocation() {
        return getHitBoxLocation();
    }

    @Override
    protected Point2D.Double getSizeInBlocks() {
        return getHitBoxSizeInBlocks();
    }

    @Override
    protected Point2D.Double getHitBoxLocation() {
        if (isPrintingHead) {
            return head.getHitBox().getCenterOfHitBox(location, sizeInBlocks);
        }   else {
            return legs.getHitBox().getCenterOfHitBox(location, sizeInBlocks);
        }
    }

    @Override
    protected Point2D.Double getHitBoxSizeInBlocks() {
        if (isPrintingHead) {
            return head.getHitBox().getHitBoxSize(sizeInBlocks);
        }   else {
            return legs.getHitBox().getHitBoxSize(sizeInBlocks);
        }
    }

    @Override
    public BufferedImage getImage() {
        if (isPrintingHead) {
            return head.getCurrentImage();
        }   else {
            return legs.getCurrentImage();
        }
    }

    private void setImagesBasedOnVelocity() {
        double xVel = velocityPercent.getX();
        double yVel = velocityPercent.getY();
            double max = Math.max(Math.abs(xVel), Math.abs(yVel));
            if (Math.abs(xVel)==max) {
                if (xVel > 0) {
                    head.setCurrentImageIndex(2);
                    legs.setCurrentImageIndex(10);
                }   else if(xVel < 0) {
                    head.setCurrentImageIndex(6);
                    legs.setCurrentImageIndex(20);
                }   else {
                    legs.setCurrentImageIndex(30);
                    head.setCurrentImageIndex(0);
                }
            }   else {
                legs.setCurrentImageIndex(2); //TODO maybe start on a varying part of the cycle for up/down
                if (yVel >= 0) {
                    head.setCurrentImageIndex(0);
                }   else if(yVel < 0) {
                    head.setCurrentImageIndex(4);
                }
            }
    }

    private void updateLoopingPictures() {
        legs.tick();
        this.image = legs.getCurrentImage();
    }

    public void keyPressed(KeyEvent e) {
        // System.out.println("Key Pressed");
        if (e.getKeyCode() == gameValues.moveUpKey) {
            this.moveUp = true;
        } else if (e.getKeyCode() == gameValues.moveDownKey) {
            this.moveDown = true;
        } else if (e.getKeyCode() == gameValues.moveLeftKey) {
            this.moveLeft = true;
        } else if (e.getKeyCode() == gameValues.moveRightKey) {
            this.moveRight = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == gameValues.moveUpKey) {
            this.moveUp = false;
        } else if (e.getKeyCode() == gameValues.moveDownKey) {
            this.moveDown = false;
        } else if (e.getKeyCode() == gameValues.moveLeftKey) {
            this.moveLeft = false;
        } else if (e.getKeyCode() == gameValues.moveRightKey) {
            this.moveRight = false;
        }
    }

    @Override
    protected HitBox getHitBox() {
        return legs.getHitBox();
    }

    private void loadLegs(SpriteSheet spriteSheet) {
        final int frontBackLegs = 10;
        final int rightLegs = 10;
        final int leftLegs = 10;
        final int totalLegs = frontBackLegs + rightLegs + leftLegs;
        Point[] ranges = {
            new Point(0, frontBackLegs-1), 
            new Point(frontBackLegs, rightLegs+frontBackLegs-1),
            new Point(frontBackLegs+rightLegs, frontBackLegs+rightLegs+leftLegs-1),
            new Point(totalLegs, totalLegs)
        };

        HitBox legHitBox = new HitBox(gameValues.LEGS_X_SIZE_PERCENT, gameValues.LEGS_Y_SIZE_PERCENT, gameValues.LEGS_X_OFFSET_PERCENT, gameValues.LEGS_Y_OFFSET_PERCENT);
        this.legs = new LoopingPictures(totalLegs+1, legHitBox, gameValues.TICKS_PER_PICTURE_STEP, ranges);

        final int maxInRow = 8;
        final int startingSpot = 6;
        final int endingSpot = 25;

        for (int spot = startingSpot; spot <= endingSpot + leftLegs; spot++) {
            if (spot <= endingSpot) {
                this.legs.setImage(spot - startingSpot, spriteSheet.shrink(spriteSheet.grabImage(spot % maxInRow,
                        spot / maxInRow, 1, 1, gameValues.PLAYER_SHEET_BOX_SIZE)));
            } else {
                this.legs.setImage(spot - startingSpot,
                        spriteSheet.flipLeftRight(spriteSheet.shrink(spriteSheet.grabImage((spot - leftLegs) % maxInRow,
                                (spot - leftLegs) / maxInRow, 1, 1, gameValues.PLAYER_SHEET_BOX_SIZE))));
            }
        }

        this.legs.setImage(totalLegs, spriteSheet.shrink(spriteSheet.grabImage(0, 1, 1, 1, gameValues.PLAYER_SHEET_BOX_SIZE)));
        this.legs.setCurrentImageIndex(totalLegs);


        //Print corners
        System.out.println("Player corners");
        System.out.println("Center: " + location);
        System.out.println("Size: " + sizeInBlocks);
        System.out.println("TL: " + (location.getX()-sizeInBlocks.getX()/2.0) + ", " + (location.getY()-sizeInBlocks.getY()/2.0));
        System.out.println("BR: " + (location.getX()+sizeInBlocks.getX()/2.0) + ", " + (location.getY()+sizeInBlocks.getY()/2.0));

        System.out.println("Leg corners");
        System.out.println("Center: " + legs.getHitBox().getCenterOfHitBox(location, sizeInBlocks));
        System.out.println("Size: " + legs.getHitBox().getHitBoxSize(sizeInBlocks));
        System.out.println("TL: " + (legs.getHitBox().getLeftOfHitBox(location.getX(), sizeInBlocks.getX()) + ", " + (legs.getHitBox().getTopOfHitBox(location.getY(), sizeInBlocks.getY()))));
        System.out.println("BR: " + (legs.getHitBox().getRightOfHitBox(location.getX(), sizeInBlocks.getX()) + ", " + (legs.getHitBox().getBottomOfHitBox(location.getY(), sizeInBlocks.getY()))));
    }

    private void loadHeads(SpriteSheet spriteSheet) {
        final int frontHead = 2;
        final int rightHead = 2;
        final int leftHead = 2;
        final int backHead = 2;
        final int totalHead = frontHead + rightHead + leftHead + backHead;
        HitBox headHitBox = new HitBox(gameValues.HEAD_X_SIZE_PERCENT, gameValues.HEAD_Y_SIZE_PERCENT, gameValues.HEAD_X_OFFSET_PERCENT, gameValues.HEAD_Y_OFFSET_PERCENT);
        this.head = new InterchangeableImage(totalHead, headHitBox);

        final int startingSpot = 0;
        final int endingSpot = 5;

        for (int spot = startingSpot; spot <= endingSpot + leftHead; spot++) {
            if (spot <= endingSpot) {
                this.head.setImage(spot - startingSpot,
                        spriteSheet.shrink(spriteSheet.grabImage(spot, 0, 1, 1, gameValues.PLAYER_SHEET_BOX_SIZE)));
            } else {
                this.head.setImage(spot - startingSpot, spriteSheet.flipLeftRight(spriteSheet.shrink(spriteSheet
                        .grabImage((spot - (leftHead + backHead)), 0, 1, 1, gameValues.PLAYER_SHEET_BOX_SIZE))));
            }
        }
    }
}