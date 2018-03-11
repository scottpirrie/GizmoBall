package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Model extends Observable {

    private GizmoFactory gf;
    private Ball ball;
    private Walls gws;
    private List<AbstractGizmo> gizmos;
    private List<AbsorberGizmo> absorbers;
    private List<Flipper> flippers;
    private List<Ball> balls;
    private Map<Integer,String> keyBinds;
    private double gravityConstant;
    private  double frictionConstant;

    public Model() {
        gws = new Walls(0, 0, 20, 20);
        gizmos = new ArrayList<>();
        absorbers = new ArrayList<>();
        flippers = new ArrayList<>();
        balls = new ArrayList<>();
        keyBinds = new HashMap<>();
        gf = new GizmoFactory();
        gravityConstant= 0.00981;
        frictionConstant=0.0;

    }

    public double getGravityConstant() {
        return gravityConstant;
    }

    public void setGravityConstant(double gravityConstant) {
        this.gravityConstant = gravityConstant;
    }

    public double getFrictionConstant() {
        return frictionConstant;
    }

    public void setFrictionConstant(double frictionConstant) {
        this.frictionConstant = frictionConstant;
    }

    public void moveBall(double move) {
        if (move > 0) {
            double moveTime = move; // 0.0167 = 60 times per second

            ball = balls.get(0);
            if (ball != null && !ball.stopped()) {
                CollisionDetails cd = timeUntilCollision();
                double tuc = cd.getTuc();
                if (tuc > moveTime) {
                    ball = movelBallForTime(ball, moveTime);
                } else {
                    ball = movelBallForTime(ball, tuc);
                    ball.setVelo(cd.getVelo());
                    //Do triggers here
                }

                moveFlipper(moveTime);
                setGravity(ball);
                setFriction(ball, moveTime);
                this.setChanged();
                this.notifyObservers();

            }
        }
    }

    private void moveFlipper(double moveTime) {
        for (Flipper f : flippers) {
            f.moveFlipper(moveTime);
        }
    }


    private Ball movelBallForTime(Ball ball, double time) {
        double newX = 0.0;
        double newY = 0.0;
        double xVel = ball.getVelo().x();
        double yVel = ball.getVelo().y();
        newX = ball.getExactX() + (xVel * time);
        newY = ball.getExactY() + (yVel * time);
        ball.setExactX(newX);
        ball.setExactY(newY);
        return ball;
    }

    private void setGravity(Ball ball) {
        ball.setVelo(ball.getVelo().plus(new Vect(0, (25 * 10) * gravityConstant)));
    }

    private void setFriction(Ball ball, double time) {
        double mu1 = 0.025; //per/second
        double mu2 = 0.025; //per/L
        double oldX = ball.getVelo().x();
        double oldY = ball.getVelo().y();
        double nyV = 0.0;
        double nxV = 0.0;
        //Vnew = Vold * (1 - mu * delta_t - mu2 * |Vold| * delta_t)
        nxV = (oldX * (1 - (mu1 / 20) * time - (mu2 * 0.05) * Math.abs(oldX) * time));
        nyV = (oldY * (1 - (mu1 / 20) * time - (mu2 * 0.05) * Math.abs(oldY) * time));
        ball.setVelo(new Vect(nxV, nyV));
    }

    private CollisionDetails timeUntilCollision() {
        Circle ballCircle = ball.getCircle();
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = new Vect(0, 0);

        double shortestTime = Double.MAX_VALUE;
        double time = 0.0;

        for (LineSegment line : gws.getLineSegments()) {
            time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
            }
        }

        for (AbstractGizmo gizmo : gizmos) {
            if (!gizmo.getType().toLowerCase().equals("circle")) {
                for (LineSegment line : gizmo.getLines()) {
                    time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
                    }
                }
            }
            for (Circle circle : gizmo.getCircles()) {
                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ballVelocity, 1.0);
                }
            }
        }

        for (AbsorberGizmo absorber : absorbers) {
            for (LineSegment line : absorber.getLines()) {
                time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
                }
            }
            for (Circle circle : absorber.getCircles()) {
                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ballVelocity, 1.0);
                }
            }
        }

        for (Flipper flipper : flippers) {
            if (flipper.getThetaCheck() > 0 && flipper.getThetaCheck() < 90) {
                for (LineSegment line : flipper.getLines()) {
                    time = Geometry.timeUntilRotatingWallCollision(line, new Vect(line.p1().x(), line.p1().y()),
                            Math.toRadians(1080), ballCircle, ballVelocity);

                    if (time < shortestTime) {
                        shortestTime = time;
                        newVelo = Geometry.reflectRotatingWall(line, new Vect(line.p1().x(), line.p1().y()),
                                Math.toRadians(1080), ballCircle, ballVelocity, 0.95);
                    }
                }

                for (Circle circle : flipper.getCircles()) {
                    time = Geometry.timeUntilRotatingCircleCollision(circle, new Vect(circle.getCenter().x(), circle.getCenter().y()),
                            Math.toRadians(1080), ballCircle, ballVelocity);

                    if (time < shortestTime) {
                        shortestTime = time;
                        newVelo = Geometry.reflectRotatingCircle(circle, new Vect(circle.getCenter().x(), circle.getCenter().y()),
                                Math.toRadians(1080), ballCircle, ballVelocity, 0.95);
                    }
                }
            } else {
                for (LineSegment line : flipper.getLines()) {
                    time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
                    }
                }

                for (Circle circle : flipper.getCircles()) {
                    time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ballVelocity, 1.0);
                    }
                }
            }
        }


        //Add check for ball-to-ball collisions here


        return new CollisionDetails(shortestTime, newVelo);
    }

    public List<AbstractGizmo> getGizmos() {
        return gizmos;
    }

    public List<AbsorberGizmo> getAbsorbers() {
        return absorbers;
    }

    public List<Flipper> getFlippers() {
        return flippers;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public Map<Integer,String> getKeyBinds(){
        return keyBinds;
    }

    public boolean addGizmo(String type, String name, String xPos, String yPos) {
        AbstractGizmo gizmo = gf.createGizmo(type, name, xPos, yPos);
        if (gizmo != null) {
            gizmos.add(gizmo);
            this.setChanged();
            this.notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    public void addAbsorber(String type, String name, String xPos1, String yPos1, String xPos2, String yPos2) {
        absorbers.add(gf.createAbsorber(type, name, xPos1, yPos1, xPos2, yPos2));
        System.out.println("x1 " +xPos1 + " y1" + yPos1 + " x2 " + xPos2 + " y2 " + yPos2);
        this.setChanged();
        this.notifyObservers();
    }

    public boolean addFlipper(String type, String name, String xPos, String yPos) {
        Flipper flipper = gf.createFlipper(type, name, xPos, yPos);
        if (flipper != null) {
            flippers.add(flipper);
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }

    //TODO Marking the taken points for the ball is something that probably requires its own method,
    //TODO after all anytime we switch to build-mode we need to update the balls taken points
    public boolean addBall(String type, String name, String xPos, String yPos, String xVelo, String yVelo) {

        double x = Double.parseDouble(xPos);
        double y = Double.parseDouble(yPos);
        double xv = Double.parseDouble(xVelo);
        double yv = Double.parseDouble(yVelo);
        balls.add(new Ball(type, name, x, y, xv, yv, 0.25));


        Point squareToAddBall = new Point((int) Double.parseDouble(xPos), (int) Double.parseDouble(yPos));

        int maxWidth = squareToAddBall.x + 1;
        int maxHeight = squareToAddBall.y + 1;
        int leastWidth = squareToAddBall.x;
        int leastHeight = squareToAddBall.y;

        //if the left most point is outside the least width mark the left square as invalid
        //if the right most point is outside the max width mark the right square as invalid
        //if the top most point is outside the least height mark the top square as invalid
        //if the down most point is outside the max height mark the down square as invalid
        Ball ball = balls.get(balls.size() - 1);
        if (ball.getExactX() - ball.getRadius() < leastWidth) {
            gf.addTakenPoint(leastWidth, squareToAddBall.y);
        }
        if (ball.getExactX() + ball.getRadius() > maxWidth) {// works
            gf.addTakenPoint(maxWidth, squareToAddBall.y);
        }
        if (ball.getExactY() - ball.getRadius() < leastHeight) {
            gf.addTakenPoint(squareToAddBall.x, leastHeight);
        }
        if (ball.getExactY() + ball.getRadius() > maxHeight) { // works
            gf.addTakenPoint(squareToAddBall.x, maxHeight);
        }

        return true;
    }

    public boolean addKeyBind(int key,String gizmoName ){
        return false;
    }

    public void setBallSpeed(String name, int xv, int yv) {
        for (Ball ball : balls) {
            if (ball.getName().equals(name)) {
                System.out.println("FOUND BALL");
                ball.setVelo(new Vect(xv, yv));
            }
        }
    }

    private boolean isWindows() {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.contains("win"));
    }

    //TODO save CONNECT / KEYCONNECT commands + ROTATE
    public void save(String directory, String fileName) {
        String name;
        if (isWindows()) {
            name = directory + "\\" + fileName + ".txt";
        } else {
            name = directory + "/" + fileName + ".txt";
        }
        File file = new File((name));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (AbstractGizmo gizmo : gizmos) {
                writer.write(gizmo.toString() + "\n");
            }

            for (Flipper flipper : flippers) {
                writer.write(flipper.toString() + "\n");
            }

            for (AbsorberGizmo absorber : absorbers) {
                writer.write(absorber.toString() + "\n");
            }

            for (Ball ball : balls) {
                writer.write(ball.toString() + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO load CONNECT / KEYCONNECT commands
    public boolean load(String directory, String fileName) {
        clearModel();
        String name;

        if (isWindows()) {
            name = directory + "\\" + fileName;
        } else {
            name = directory + "/" + fileName;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(new File(name)))) {
            StringTokenizer tokenizer;
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    tokenizer = new StringTokenizer(line);
                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();

                        if (token.toLowerCase().equals("square")) {
                            addGizmo(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("triangle")) {
                            addGizmo(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("circle")) {
                            addGizmo(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("absorber")) {
                            addAbsorber(token, tokenizer.nextToken(), tokenizer.nextToken(),
                                    tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("leftflipper")) {
                            addFlipper(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("rightflipper")) {
                            addFlipper(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("ball")) {
                            addBall(token, tokenizer.nextToken(), tokenizer.nextToken(),
                                    tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("rotate")) {
                            String target = tokenizer.nextToken();
                            for (AbstractGizmo t : gizmos) {
                                if (t.getName().equals(target)) {
                                    t.rotate();
                                }
                            }
                        }
                    }
                } catch (NoSuchElementException e) {
                    br.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Loading model...");
        this.setChanged();
        this.notifyObservers();
        return true;
    }

    //TODO clear triggers as well when they are implemented
    private void clearModel() {
        System.out.println("Clearing model...");
        gizmos.clear();
        flippers.clear();
        balls.clear();
        gf.clearPoints();
        this.setChanged();
        this.notifyObservers();
    }

    public boolean remove(double x, double y) {
        return removeBall(x, y) || removeAbsorber(x, y) ||removeGizmo(x, y) || removeFlipper(x, y);
    }

    private boolean removeGizmo(double x, double y) {
        int tempX = (int) x;
        int tempY = (int) y;
        for (AbstractGizmo abstractGizmo : gizmos) {
            if (abstractGizmo.getxPos() == tempX && abstractGizmo.getyPos() == tempY) {
                gizmos.remove(abstractGizmo);
                gf.removeTakenPoint(tempX, tempY);
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }

    private boolean removeBall(double x, double y) {
        for (Ball ball : balls) {
            if ((x <= ball.getExactX() + ball.getRadius() && x >= ball.getExactX() - ball.getRadius())
                    && (y <= ball.getExactY() + ball.getRadius() && y >= ball.getExactY() - ball.getRadius())) {
                balls.remove(ball);
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }

    //added remove points
    private boolean removeAbsorber(double x, double y){
        int tempX = (int) x;
        int tempY = (int) y;
        for(AbsorberGizmo ab: absorbers){
            //if((tempX >= ab.getxPos() || tempY >= ab.getyPos()) && (tempX <= ab.getxPos2() || tempY <= ab.getyPos2())){
            if((tempX >= ab.getxPos() && tempX <= ab.getxPos2()) &&(tempY >= ab.getyPos() && tempY <= ab.getyPos2())){
                absorbers.remove(ab);
                for(int i=ab.getyPos(); i<=ab.getyPos2(); i++){
                    for(int j=ab.getxPos(); j<=ab.getxPos2(); j++){
                     
                        gf.removeTakenPoint(j,i);
                    }
                }
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }

    private boolean removeFlipper(double x, double y) {
        boolean flipperFound = false;
        x = (int) x;
        y = (int) y;

        for (Flipper flipper : flippers) {
            double maxX = 0.0;
            double maxY = 0.0;

            if (flipper.getXPivot() == x && flipper.getYPivot() == y) {
                flipperFound = true;
            } else if (flipper.getXPivot() + 1 == x && flipper.getYPivot() + 1 == y) {
                flipperFound = true;
            } else if (flipper.getXPivot() + 1 == x && flipper.getYPivot() == y) {
                flipperFound = true;
            } else if (flipper.getXPivot() == x && flipper.getYPivot() + 1 == y) {
                flipperFound = true;
            }

            if (flipperFound) {
                gf.removeTakenPoint((int) flipper.getXPivot(), (int) flipper.getYPivot());
                gf.removeTakenPoint((int) flipper.getXPivot() + 1, (int) flipper.getYPivot());
                gf.removeTakenPoint((int) flipper.getXPivot(), (int) flipper.getYPivot() + 1);
                gf.removeTakenPoint((int) flipper.getXPivot() + 1, (int) flipper.getYPivot() + 1);
                flippers.remove(flipper);
                break;
            }

        }
        return flipperFound;
    }

    public AbstractGizmo findGizmo(int x, int y) {
        for (AbstractGizmo abstractGizmo : gizmos) {
            if (abstractGizmo.getxPos() == x && abstractGizmo.getyPos() == y) {
                return abstractGizmo;
            }
        }
        return null;
    }
}
