package domain.violations;

/**
 * Is a {@link Violation} but adds extra information about the speed
 */
public class SpeedViolation extends Violation {
    private int speed;
    private int maximumSpeed;

    public SpeedViolation(){
        super.setType("Speed violation");
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(int maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    @Override
    public String toString() {
        return String.format("%s\nMeasured speed: %d km/h, allowed speed %d km/h", super.toString(), speed, maximumSpeed);
    }
}
