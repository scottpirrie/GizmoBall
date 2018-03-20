package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;
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
    private Map<Integer, ArrayList<String>> keyDownMap;
    private Map<Integer, ArrayList<String>> keyUpMap;
    private Map<String, List<String>> triggers;
    private double gravityConstant;
    private double frictionConstant;
    private String triggerSource;

    public Model() {
        gws = new Walls();
        gizmos = new ArrayList<>();
        absorbers = new ArrayList<>();
        flippers = new ArrayList<>();
        balls = new ArrayList<>();
        keyDownMap = new HashMap<>();
        keyUpMap = new HashMap<>();
        triggers = new HashMap<>();
        gf = new GizmoFactory();
        gravityConstant = 25;
        frictionConstant = 0.045;

    }

    public void setGravityConstant(double value) {
        this.gravityConstant = gravityConstant * value/50;
    }

    public void setFrictionConstant(double value) {
        this.frictionConstant = frictionConstant * value/50;
    }

    public void moveBall(double move) {
        if (move > 0) {
            double moveTime = move;
            if (!balls.isEmpty()) {
                for (Ball b : balls) {
                    ball = b;
                    if (ball != null && !ball.stopped()) {
                        CollisionDetails cd = timeUntilCollision();
                        double tuc = cd.getTuc();
                        if (tuc > moveTime) {
                            ball = moveBallForTime(ball, moveTime);
                        } else {
                            ball = moveBallForTime(ball, tuc);
                            ball.setVelo(cd.getVelo());
                        }
                        setGravity(ball, moveTime);
                        setFriction(ball, moveTime);
                        callActions(triggerSource,tuc);
                    }
                }
            }
            moveFlipper(moveTime);
            this.setChanged();
            this.notifyObservers();
        }
    }

    private void moveFlipper(double moveTime) {
        for (Flipper f : flippers) {
            f.moveFlipper(moveTime);
        }
    }

    private Ball moveBallForTime(Ball ball, double time) {
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

    private void setGravity(Ball ball, double time) {
        if (!ball.stopped()) {
            ball.setVelo(ball.getVelo().plus(new Vect(0, (gravityConstant *time))));
        }
    }

    private void setFriction(Ball ball, double time) {
        double mu1 = frictionConstant; //per/second
        double mu2 = frictionConstant; //per/L
        double oldX = ball.getVelo().x();
        double oldY = ball.getVelo().y();
        double nyV = 0.0;
        double nxV = 0.0;

        //Vnew = Vold * (1 - mu * delta_t - mu2 * |Vold| * delta_t)
        nxV = oldX * (1 - mu1 * time - mu2 * Math.abs(oldX) * time);
        nyV = oldY * (1 - mu1 * time - mu2 * Math.abs(oldY) * time);
        Vect newV = ball.getVelo().minus(new Vect(nxV, nyV));
        ball.setVelo(ball.getVelo().minus(newV));
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
                triggerSource = gws.getName();
            }
        }

        for (AbstractGizmo gizmo : gizmos) {
            if (!gizmo.getType().toLowerCase().equals("circle")) {
                for (LineSegment line : gizmo.getLines()) {
                    time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
                        triggerSource = gizmo.getName();
                    }
                }
            }
            for (Circle circle : gizmo.getCircles()) {
                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ballVelocity, 1.0);
                    triggerSource = gizmo.getName();
                }
            }
        }

        for (AbsorberGizmo absorber : absorbers) {
            for (LineSegment line : absorber.getLines()) {
                time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    triggerSource = absorber.getName();
                    newVelo = Geometry.reflectWall(line, ball.getVelo(), -1.0);
                    captureBall(absorber, ball);
                }
            }
            for (Circle circle : absorber.getCircles()) {
                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    triggerSource = absorber.getName();
                    newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ballVelocity, -1.0);
                    captureBall(absorber, ball);
                }
            }
        }

        for (Flipper flipper : flippers) {
            if (flipper.getTheta() > 0 && flipper.getTheta() < 90) {
                for (LineSegment line : flipper.getLines()) {
                    time = Geometry.timeUntilRotatingWallCollision(line, new Vect(line.p1().x(), line.p1().y()),
                            Math.toRadians(1080), ballCircle, ballVelocity);

                    if (time < shortestTime) {
                        shortestTime = time;
                        triggerSource = flipper.getName();
                        newVelo = Geometry.reflectRotatingWall(line, new Vect(line.p1().x(), line.p1().y()),
                                Math.toRadians(1080), ballCircle, ballVelocity, 0.95);
                    }
                }

                for (Circle circle : flipper.getCircles()) {
                    time = Geometry.timeUntilRotatingCircleCollision(circle, new Vect(circle.getCenter().x(), circle.getCenter().y()),
                            Math.toRadians(1080), ballCircle, ballVelocity);

                    if (time < shortestTime) {
                        shortestTime = time;
                        triggerSource = flipper.getName();
                        newVelo = Geometry.reflectRotatingCircle(circle, new Vect(circle.getCenter().x(), circle.getCenter().y()),
                                Math.toRadians(1080), ballCircle, ballVelocity, 0.95);
                    }
                }
            } else {
                for (LineSegment line : flipper.getLines()) {
                    time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        triggerSource = flipper.getName();
                        newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
                    }
                }

                for (Circle circle : flipper.getCircles()) {
                    time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        triggerSource = flipper.getName();
                        newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ballVelocity, 1.0);
                    }
                }
            }
        }

        return new CollisionDetails(shortestTime, newVelo);
    }

    private void captureBall(AbsorberGizmo absorber, Ball ball) {
        boolean XCheck = ball.getExactX() >= absorber.getxPos() && ball.getExactX() <= absorber.getxPos2();
        boolean YCheck = ball.getExactY() >= absorber.getyPos() - ball.getRadius()
                && ball.getExactY() <= absorber.getyPos2() + ball.getRadius();

        if (XCheck && YCheck) {
            absorber.setBall(ball);
            ball.stop();
            ball.setExactX(absorber.getxPos2() - ball.getRadius());
            ball.setExactY(absorber.getyPos2() - ball.getRadius());
        }
    }

    private void callActions(String source, double tuc) {
        List<String> temp = triggers.get(source);
        Timer timer = new Timer();
        long time = Double.valueOf(tuc * 1000).longValue();
        System.out.println(tuc + " " + time);

        if (temp != null) {
            for (String name : temp) {
                for (AbstractGizmo gizmo : gizmos) {
                    if (name.equals(gizmo.getName())) {
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                gizmo.doAction();
                            }
                        }, time);
                    }
                }

                for (Flipper flipper : flippers) {
                    if (name.equals(flipper.getName())) {
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                flipper.doAction();
                            }
                        }, time);
                    }
                }

                for (AbsorberGizmo absorber : absorbers) {
                    if (name.equals(absorber.getName())) {
                        if (absorber.getBall() != null) {
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    absorber.doAction();
                                }
                            }, time);
                        }
                    }
                }
            }
        }
    }

    private void keybindActions(ArrayList<String> actions) {
        for (AbstractGizmo gizmo : gizmos) {
            for (String name : actions) {
                if (name.equals(gizmo.getName())) {
                    gizmo.doAction();
                }
            }
        }

        for (Flipper flipper : flippers) {
            for (String name : actions) {
                if (name.equals(flipper.getName())) {
                    if (flipper.isPressed()) {
                        flipper.moveFlipper(0.0167);
                    }
                }
            }
        }

        for (AbsorberGizmo absorber : absorbers) {
            for (String name : actions) {
                if (name.equals(absorber.getName())) {
                    if (absorber.getBall() != null) {
                        absorber.doAction();
                    }
                }
            }
        }
    }

    public void changeFlipperStatus(int key) {
       // String name = "";
        ArrayList<String> flipperNames=new ArrayList<>();
        if (keyDownMap.containsKey(key)) {
            flipperNames = keyDownMap.get(key);
           // name = keyDownMap.get(key);
        } else if (keyUpMap.containsKey(key)) {
             flipperNames = keyDownMap.get(key);
            //name = keyUpMap.get(key);
        }
        if(!flipperNames.isEmpty()) {
            for (Flipper flipper : flippers) {
                for (String name: flipperNames)
                if (name.equals(flipper.getName())) {
                    flipper.setPressed();
                }
            }
        }
    }

    public void keybindAction(int key) {
        if (keyDownMap.containsKey(key)) {
            keybindActions(keyDownMap.get(key));
        } else if (keyUpMap.containsKey(key)) {
            keybindActions(keyUpMap.get(key));
        }
    }

    public boolean addGizmo(String type, String name, String xPos, String yPos) {
        AbstractGizmo gizmo = gf.createGizmo(type, name, xPos, yPos);
        if (gizmo != null) {
            gizmos.add(gizmo);
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }

    public boolean addAbsorber(String type, String name, String xPos1, String yPos1, String xPos2, String yPos2) {
        AbsorberGizmo absorberGizmo = gf.createAbsorber(type, name, xPos1, yPos1, xPos2, yPos2);
        if (absorberGizmo != null) {
            absorbers.add(absorberGizmo);
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
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

    public boolean moveGizmo(String name, String xPos, String yPos) {

        for (AbstractGizmo gizmo : gizmos) {
            if (gizmo.getName().equals(name)) {
                gf.removeTakenPoint(gizmo.getxPos(), gizmo.getyPos());
                Point.Double p = new Point.Double(Double.parseDouble(xPos), Double.parseDouble(yPos));

                if (!gf.isPointTaken(p)) {
                    gizmo.move(Double.parseDouble(xPos), Double.parseDouble(yPos));
                    gf.addTakenPoint(gizmo.getxPos(), gizmo.getyPos());
                    this.setChanged();
                    this.notifyObservers();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveBall(String name, String xPos, String yPos) {
        double toCheckX = Math.floor(Double.parseDouble(xPos));
        double toCheckY = Math.floor(Double.parseDouble(yPos));

        for (Ball ball : balls) {
            if (ball.getName().equals(name)) {

                Point.Double p = new Point.Double(toCheckX, toCheckY);


                if (!gf.isPointTaken(p)) {
                    ball.move(Double.parseDouble(xPos), Double.parseDouble(yPos));

                    this.setChanged();
                    this.notifyObservers();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean MoveFlipper(String name, String xPos, String yPos) {

        for (Flipper flipper : flippers) {
            if (flipper.getName().equals(name)) {
                double xPivot = Math.floor(flipper.getXPivot());
                double yPivot = Math.floor(flipper.getYPivot());

                gf.removeTakenPoint(xPivot, yPivot);
                gf.removeTakenPoint(xPivot, yPivot + 1);
                gf.removeTakenPoint(xPivot + 1, yPivot);
                gf.removeTakenPoint(xPivot + 1, yPivot + 1);

                Point.Double p = new Point.Double(Double.parseDouble(xPos), Double.parseDouble(yPos));
                if (!gf.isPointTaken(p)) {
                    // move theFlipper
                    flipper.move(Double.parseDouble(xPos), Double.parseDouble(yPos));
                    gf.addTakenPoint(flipper.getXPivot(), flipper.getYPivot());
                    gf.addTakenPoint(flipper.getXPivot(), flipper.getYPivot() + 1);
                    gf.addTakenPoint(flipper.getXPivot() + 1, flipper.getYPivot());
                    gf.addTakenPoint(flipper.getXPivot() + 1, flipper.getYPivot() + 1);

                    this.setChanged();
                    this.notifyObservers();
                    return true;
                }

            }
        }
        return false;
    }

    public boolean moveAbsorber(String name, String xPos, String yPos, String xPos2, String yPos2) {
        double x1 = Double.parseDouble(xPos);
        double x2 = Double.parseDouble(xPos2);
        double y1 = Double.parseDouble(yPos);
        double y2 = Double.parseDouble(yPos2);

        for (AbsorberGizmo ab : absorbers) {
            if (ab.getName().equals(name)) {
                for (double i = ab.getyPos(); i <= ab.getyPos2(); i++) {
                    for (double j = ab.getxPos(); j <= ab.getxPos2(); j++) {

                        gf.removeTakenPoint((int) j, (int) i);
                    }
                }

                for (double i = y1; i <= y2; i++) {
                    for (double j = x1; j <= x2; j++) {
                        Point.Double p = new Point.Double(j, i);
                        if (gf.isPointTaken(p)) {
                            return false;
                        }

                    }
                }

                ab.move(x1, x2, y1, y2);
                for (double i = y1; i < y2; i++) {
                    for (double j = x1; j < x2; j++) {
                        gf.addTakenPoint(j, i);
                    }
                }

                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }

    public boolean addBall(String type, String name, String xPos, String yPos, String xVelo, String yVelo) {
        double x = 0.0;
        double y = 0.0;
        double xv = 0.0;
        double yv = 0.0;

        try {
            x = Double.parseDouble(xPos);
            y = Double.parseDouble(yPos);
            xv = Double.parseDouble(xVelo);
            yv = Double.parseDouble(yVelo);
        } catch (NumberFormatException e) {
            return false;
        }

        Point.Double p = new Point.Double(Math.floor(x), Math.floor(y));

        if (!gf.isPointTaken(p)) {
            balls.add(new Ball(type, name, x, y, xv, yv, 0.25));
            Point squareToAddBall = new Point((int) Double.parseDouble(xPos), (int) Double.parseDouble(yPos));
            gf.addTakenPoint(squareToAddBall.x, squareToAddBall.y);
            //TODO need to think about invalid points
            Ball ball = balls.get(balls.size() - 1);
            addBallsTakenPoints(ball);
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }

    public void cleanUpWhenBallMoves() {
        for (Ball ball : balls) {
            removeBallsTakenPoint(ball);
        }
    }

    public void setNewBallsTakenPoints() {
        for (Ball ball : balls) {
            addBallsTakenPoints(ball);
        }
    }

    public boolean addKeyBind(int key, String gizmoName) {
        if (checkName(gizmoName)) {
            if (!keyDownMap.containsKey(key)) {
                ArrayList<String> toAdd = new ArrayList<>();
                toAdd.add(gizmoName);
                keyDownMap.put(key, toAdd);
                return true;
            } else {
                ArrayList<String> actions = keyDownMap.get(key);
                actions.add(gizmoName);
                keyDownMap.put(key, actions);
                return true;
            }

        }
        return false;
    }




    public boolean addTrigger(String source, String target) {
        if(checkName(source) && checkName(target)) {
            if (!source.equals("") && !target.equals("")) {
                List<String> temp = triggers.get(source);
                if (temp == null) {
                    temp = new ArrayList<>();
                    temp.add(target);
                    triggers.put(source, temp);
                    return true;
                } else {
                    if (!temp.contains(target)) {
                        triggers.get(source).add(target);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean rotate(double x, double y) {
        return rotateGizmo(x, y) || rotateFlipper(x, y);
    }

    private boolean rotateGizmo(double x, double y) {

        for (AbstractGizmo abstractGizmo : gizmos) {
            if (abstractGizmo.getxPos() == Math.floor(x) && abstractGizmo.getyPos() == Math.floor(y)) {
                abstractGizmo.rotate();
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }

    private boolean rotateFlipper(double x, double y) {
        for (Flipper flipper : flippers) {
            if (flipperCheck(flipper.getXPivot(), flipper.getYPivot(), Math.floor(x), Math.floor(y))) {
                flipper.rotate();
                this.setChanged();
                this.notifyObservers();
                return true;
            }

        }
        return false;
    }

    public boolean remove(double x, double y) {
        return removeBall(x, y) || removeAbsorber(x, y) || removeGizmo(x, y) || removeFlipper(x, y);
    }

    private boolean removeGizmo(double x, double y) {
        double flooredx = Math.floor(x);
        double flooredy = Math.floor(y);

        for (AbstractGizmo abstractGizmo : gizmos) {
            if (abstractGizmo.getxPos() == flooredx && abstractGizmo.getyPos() == flooredy) {
                if (triggers.containsKey(abstractGizmo.getName())) {
                    triggers.remove(abstractGizmo.getName());
                }
                removeKeybind(abstractGizmo.getName());
                gizmos.remove(abstractGizmo);
                gf.removeTakenPoint(flooredx, flooredy);
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
                removeBallsTakenPoint(ball);
                balls.remove(ball);
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }

    private boolean removeAbsorber(double x, double y) {
        double flooredx = Math.floor(x);
        double flooredy = Math.floor(y);

        for (AbsorberGizmo ab : absorbers) {
            if ((flooredx >= ab.getxPos() && flooredx <= ab.getxPos2()) && (flooredy >= ab.getyPos() && flooredy <= ab.getyPos2())) {
                for (double i = ab.getyPos(); i <= ab.getyPos2(); i++) {
                    for (double j = ab.getxPos(); j <= ab.getxPos2(); j++) {
                        gf.removeTakenPoint(j, i);
                    }
                }

                if (triggers.containsKey(ab.getName())) {
                    triggers.remove(ab.getName());
                }
                removeKeybind(ab.getName());
                absorbers.remove(ab);
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }

    private boolean removeFlipper(double x, double y) {

        for (Flipper flipper : flippers) {
            double pivotX = Math.floor(flipper.getXPivot());
            double pivotY = Math.floor(flipper.getYPivot());

            if (flipperCheck(flipper.getXPivot(), flipper.getYPivot(), x, y)) {
                gf.removeTakenPoint(pivotX, pivotY);
                gf.removeTakenPoint(pivotX + 1, pivotY);
                gf.removeTakenPoint(pivotX, pivotY + 1);
                gf.removeTakenPoint(pivotX + 1, pivotY + 1);
                if (triggers.containsKey(flipper.getName())) {
                    triggers.remove(flipper.getName());
                }
                removeKeybind(flipper.getName());
                flippers.remove(flipper);
                this.setChanged();
                this.notifyObservers();
                return true;
            }

        }
        return false;
    }

    public boolean removeKeybind(int key, String gizmoName) {
        boolean removed = false;

        if (!keyDownMap.isEmpty()) {
            if(keyDownMap.containsKey(key)){
                keyDownMap.remove(key);
            }
           // keyDownMap.entrySet().removeIf(e -> keyDownMap.get(key).equals(gizmoName));
            removed = true;
        }

        if (!keyUpMap.isEmpty()) {
            if(keyUpMap.containsKey(key)){
                keyUpMap.remove(key);
            }
           // keyUpMap.entrySet().removeIf(e -> keyDownMap.get(key).equals(gizmoName));
            removed = true;
        }

        return removed;
    }

    private void removeKeybind(String gizmoName) {

        if (!keyDownMap.isEmpty()) {
            keyDownMap.entrySet().removeIf(e -> e.getValue().equals(gizmoName));
        }

        if (!keyUpMap.isEmpty()) {
            keyUpMap.entrySet().removeIf(e -> e.getValue().equals(gizmoName));
        }

    }

    public boolean removeTrigger(String source, String target) {
        List<String> temp = triggers.get(source);

        if (temp == null) {
            return false;
        } else if (!temp.contains(target)) {
            return false;
        } else {
            triggers.get(source).remove(target);
            return true;
        }
    }

    public String findName(double x, double y) {
        String gizmo = findGizmo(x, y);
        if (gizmo != null && !gizmo.isEmpty()) {
            String[] temp = gizmo.split(" ");
            return temp[1];
        }
        return "";
    }

    public boolean checkName(String gizmoName){

        for(AbstractGizmo gizmo:gizmos){
            if(gizmo.getName().equals(gizmoName)){
                return true;
            }
        }
        for(Flipper flipper:flippers){
            System.out.println("Name to check: "+flipper.getName());
            if(flipper.getName().equals(gizmoName)){
                return true;
            }
        }
        for(AbsorberGizmo ab:absorbers){
            if(ab.getName().equals(gizmoName)){
                return true;
            }
        }
        for(Ball ball:balls){
            if(ball.getName().equals(gizmoName)){
                return true;
            }
        }
        return false;
    }
    public String findGizmo(double x, double y) {
        double flooredx = Math.floor(x);
        double flooredy = Math.floor(y);

        for (AbstractGizmo abstractGizmo : gizmos) {
            if (abstractGizmo.getxPos() == flooredx && abstractGizmo.getyPos() == flooredy) {
                return abstractGizmo.getType() + " " + abstractGizmo.getName() + " " + abstractGizmo.getxPos() + " " + abstractGizmo.getyPos();
            }
        }
        for (AbsorberGizmo ab : absorbers) {
            if ((flooredx >= ab.getxPos() && flooredx <= ab.getxPos2()) && (flooredy >= ab.getyPos() && flooredy <= ab.getyPos2())) {
                double height = ab.getyPos2() - ab.getyPos();
                double width = ab.getxPos2() - ab.getxPos();
                return ab.getType() + " " + ab.getName() + " " + ab.getxPos() + " " + ab.getyPos() + " " + height + " " + width;
            }

        }

        for (Ball ball : balls) {
            if ((x <= ball.getExactX() + ball.getRadius() && x >= ball.getExactX() - ball.getRadius())
                    && (y <= ball.getExactY() + ball.getRadius() && y >= ball.getExactY() - ball.getRadius())) {
                return ball.getType() + " " + ball.getName() + " " + ball.getExactX() + " " + ball.getExactY();
            }

        }

        for (Flipper flipper : flippers) {
            if (flipperCheck(flipper.getXPivot(), flipper.getYPivot(), flooredx, flooredy)) {
                return flipper.getType() + " " + flipper.getName() + " " + flipper.getXPivot() + " " + flipper.getYPivot();
            }

        }
        return "";
    }

    private boolean flipperCheck(double flipperX, double flipperY, double targetX, double targetY) {
        targetX = Math.floor(targetX);
        targetY = Math.floor(targetY);

        if (flipperX == targetX && flipperY == targetY) {
            return true;
        } else if (flipperX + 1 == targetX && flipperY + 1 == targetY) {
            return true;
        } else if (flipperX + 1 == targetX && flipperY == targetY) {
            return true;
        } else if (flipperX == targetX && flipperY + 1 == targetY) {
            return true;
        }
        return false;
    }


    //TODO Marking the taken points for the ball is something that probably requires its own method,
    //TODO after all anytime we switch to build-mode we need to update the balls taken points
    private void removeBallsTakenPoint(Ball ball) {
        Point.Double squareToAddBall = new Point.Double(Math.floor(ball.getExactX()), Math.floor(ball.getExactY()));
        gf.removeTakenPoint(squareToAddBall.x, squareToAddBall.y);

        Point.Double upRight = new Point.Double(ball.getExactX() + ball.getRadius(), ball.getExactY() - ball.getRadius());
        Point.Double upLeft = new Point.Double(ball.getExactX() - ball.getRadius(), ball.getExactY() - ball.getRadius());
        Point.Double downLeft = new Point.Double(ball.getExactX() - ball.getRadius(), ball.getExactY() + ball.getRadius());
        Point.Double downRight = new Point.Double(ball.getExactX() + ball.getRadius(), ball.getExactY() + ball.getRadius());

        if (upRight.x > squareToAddBall.x) {
            double xToAdd = Math.floor(upRight.x);
            double yToAdd = Math.floor(upRight.y);
            gf.removeTakenPoint(xToAdd, yToAdd);
        }
        if (upLeft.x < squareToAddBall.x) {// works
            double xToAdd = Math.floor(upLeft.x);
            double yToAdd = Math.floor(upLeft.y);
            gf.removeTakenPoint(xToAdd, yToAdd);
        }
        if (downLeft.y > squareToAddBall.y) {
            double xToAdd = Math.floor(downLeft.x);
            double yToAdd = Math.floor(downLeft.y);
            gf.removeTakenPoint(xToAdd, yToAdd);
        }
        if (downRight.x > squareToAddBall.x && downRight.y > squareToAddBall.y) { // works
            double xToAdd = Math.floor(downRight.x);
            double yToAdd = Math.floor(downRight.y);
            gf.removeTakenPoint(xToAdd, yToAdd);
        }

        this.setChanged();
        this.notifyObservers();
    }

    private void addBallsTakenPoints(Ball ball) {
        Point.Double squareToAddBall = new Point.Double(Math.floor(ball.getExactX()), Math.floor(ball.getExactY()));
        gf.addTakenPoint(squareToAddBall.x, squareToAddBall.y);

        Point.Double upRight = new Point.Double(ball.getExactX() + ball.getRadius(), ball.getExactY() - ball.getRadius());
        Point.Double upLeft = new Point.Double(ball.getExactX() - ball.getRadius(), ball.getExactY() - ball.getRadius());
        Point.Double downLeft = new Point.Double(ball.getExactX() - ball.getRadius(), ball.getExactY() + ball.getRadius());
        Point.Double downRight = new Point.Double(ball.getExactX() + ball.getRadius(), ball.getExactY() + ball.getRadius());

        if (upRight.x > squareToAddBall.x) {
            double xToAdd = Math.floor(upRight.x);
            double yToAdd = Math.floor(upRight.y);
            gf.addTakenPoint(xToAdd, yToAdd);
        }
        if (upLeft.x < squareToAddBall.x) {// works
            double xToAdd = Math.floor(upLeft.x);
            double yToAdd = Math.floor(upLeft.y);
            gf.addTakenPoint(xToAdd, yToAdd);
        }
        if (downLeft.y > squareToAddBall.y) {
            double xToAdd = Math.floor(downLeft.x);
            double yToAdd = Math.floor(downLeft.y);
            gf.addTakenPoint(xToAdd, yToAdd);
        }
        if (downRight.x > squareToAddBall.x && downRight.y > squareToAddBall.y) { // works
            double xToAdd = Math.floor(downRight.x);
            double yToAdd = Math.floor(downRight.y);
            gf.addTakenPoint(xToAdd, yToAdd);
        }

        this.setChanged();
        this.notifyObservers();
    }

    public void save(String directory, String fileName) {
        FileParser fp = new FileParser(this);
        fp.save(directory,fileName);
    }

    public boolean load(String directory, String fileName) {
        FileParser fp = new FileParser(this);
        if(fp.load(directory,fileName)){
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
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

    Map<String, List<String>> getTriggers(){
        return triggers;
    }

    Map<Integer,ArrayList<String>>  getKeyDownMap(){
        return keyDownMap;
    }

    Map<Integer,ArrayList<String>> getKeyUpMap(){
        return keyUpMap;
    }

    void clearModel() {
        absorbers.clear();
        gizmos.clear();
        flippers.clear();
        balls.clear();
        gf.clearPoints();
        triggers.clear();
        keyDownMap.clear();
        keyUpMap.clear();
        this.setChanged();
        this.notifyObservers();
    }

}
