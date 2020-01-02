package app.game.gamefield.elements.mobiles.players;

import app.game.gamefield.elements.mobiles.Mobile;
import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.elements.rendering.InterchangeableImage;
import app.game.gamefield.rooms.Room;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D.Double;

/**
 * Character
 */
public class Player extends Mobile {
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private int money, bombs, keys;
    protected InterchangeableImage legs, head;

    public enum Characters {
        Isaac;
    }

    public Player(GameValues gameValues, Characters c, Point2D.Double location) {
        super(gameValues, location);
        money = bombs = keys = 0;
        sizeInBlocks = new Point2D.Double(1, 1.25);
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
        testCollisionAndMove(r);
        // System.out.println("Position: " + location.getX() + ", " + location.getY());
    }

    @Override
    protected void onCollision(Double newLocation, Drawable collidingElement, Room room) {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(Graphics g) {
        final Point2D.Double currentLocation = this.location;
        final Point2D.Double currentSizeInBlocks = this.sizeInBlocks;

        final double headYPercent = .65;
        final double bodyYPercent = 1 - headYPercent;
        final double yOffset = currentSizeInBlocks.getY() * .27; // TODO make it based off the other
        final double bodyXShrinkage = currentSizeInBlocks.getX() * .4;

        // Body is below (more Y)
        this.location = new Point2D.Double(currentLocation.getX() + bodyXShrinkage / 2.0,
                currentLocation.getY() + yOffset);
        this.sizeInBlocks = new Point2D.Double(currentSizeInBlocks.getX() - bodyXShrinkage,
                currentSizeInBlocks.getY() * bodyYPercent);
        this.image = legs.getCurrentImage();
        super.render(g);

        // Head is above (less Y)
        this.location = new Point2D.Double(currentLocation.getX(), currentLocation.getY() - yOffset);
        this.sizeInBlocks = new Point2D.Double(currentSizeInBlocks.getX(), currentSizeInBlocks.getY() * headYPercent);
        this.image = head.getCurrentImage();
        super.render(g);

        this.location = currentLocation;
        this.sizeInBlocks = currentSizeInBlocks;
    }

    private void setImagesBasedOnVelocity() {
        double xVel = velocityPercent.getX();
        double yVel = velocityPercent.getY();
            double max = Math.max(Math.abs(xVel), Math.abs(yVel));
            if (Math.abs(xVel)==max) {
                if (xVel > 0) {
                    head.setCurrentImageIndex(2);
                }   else if(xVel < 0) {
                    head.setCurrentImageIndex(6);
                }   else {
                    head.setCurrentImageIndex(0);
                }
            }   else {
                if (yVel >= 0) {
                    head.setCurrentImageIndex(0);
                }   else if(yVel < 0) {
                    head.setCurrentImageIndex(4);
                }
            }
        
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

    private void loadLegs(SpriteSheet spriteSheet) {
        final int frontBackLegs = 10;
        final int rightLegs = 10;
        final int leftLegs = 10;
        final int totalLegs = frontBackLegs + rightLegs + leftLegs;
        this.legs = new InterchangeableImage(totalLegs);

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
    }

    private void loadHeads(SpriteSheet spriteSheet) {
        final int frontHead = 2;
        final int rightHead = 2;
        final int leftHead = 2;
        final int backHead = 2;
        final int totalHead = frontHead + rightHead + leftHead + backHead;
        this.head = new InterchangeableImage(totalHead);

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