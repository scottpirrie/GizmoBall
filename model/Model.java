package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.awt.*;
import java.awt.geom.Point2D;
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
    private Map<Integer,String> keyDownMap;
    private Map<Integer,String> keyUpMap;
    private Map<String,List<String>> triggers;
    private double gravityConstant;
    private double frictionConstant;
    private String triggerSource;

    public Model() {
        gws = new Walls("OuterWalls",0, 0, 20, 20);
        gizmos = new ArrayList<>();
        absorbers = new ArrayList<>();
        flippers = new ArrayList<>();
        balls = new ArrayList<>();
        keyDownMap = new HashMap<>();
        keyUpMap = new HashMap<>();
        triggers = new HashMap<>();
        gf = new GizmoFactory();
        gravityConstant = 25;
        frictionConstant = 0.025;

    }

    public void setGravityConstant(double gravityConstant) {
        this.gravityConstant = gravityConstant;
    }

    public void setFrictionConstant(double frictionConstant) {
        this.frictionConstant = frictionConstant;
    }

    public void moveBall(double move) {
        if (move > 0) {
            double moveTime = move;
            if(!balls.isEmpty()) {
                for(Ball b : balls) {
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
                    }
                }
            }
            callActions(triggerSource);
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

    //TODO fix gravity + Friction ( though i think its gravity )
    private void setGravity(Ball ball,double time) {
        if(!ball.stopped()) {
            ball.setVelo(ball.getVelo().plus(new Vect(0, (gravityConstant * time))));
        }
    }

    private void setFriction(Ball ball, double time) {
        System.out.println("Time : "+time);
        double mu1 = frictionConstant; //per/second
        double mu2 = frictionConstant; //per/L
        double oldX = ball.getVelo().x();
        double oldY = ball.getVelo().y();
        double nyV = 0.0;
        double nxV = 0.0;
        Vect newV;
        mu1 = mu1 / time;

        //Vnew = Vold * (1 - mu * delta_t - mu2 * |Vold| * delta_t)
        nxV = oldX * (1 - mu1 * time - mu2 * Math.abs(oldX) * time);
        nyV = oldY * (1 - mu1 * time - mu2 * Math.abs(oldY) * time);
        newV = ball.getVelo().minus(new Vect(nxV, nyV));
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
                triggerSource = "OuterWalls";
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
                    captureBall(absorber,ball);
                }
            }
            for (Circle circle : absorber.getCircles()) {
                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    triggerSource = absorber.getName();
                    newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ballVelocity, -1.0);
                    captureBall(absorber,ball);
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

    private void captureBall(AbsorberGizmo absorber, Ball ball){
        boolean XCheck = ball.getExactX() >= absorber.getxPos() && ball.getExactX() <= absorber.getxPos2();
        boolean YCheck = ball.getExactY() >= absorber.getyPos() - ball.getRadius()
                && ball.getExactY() <= absorber.getyPos2() + ball.getRadius();

        if(XCheck && YCheck){
                absorber.setBall(ball);
                ball.stop();
                ball.setExactX(absorber.getxPos2() - ball.getRadius());
                ball.setExactY(absorber.getyPos2() - ball.getRadius());
        }
    }

    private void callActions(String source){
        List<String> temp = triggers.get(source);

        if(temp != null) {
            for (String name : temp) {

                for (AbstractGizmo gizmo : gizmos) {
                    if (name.equals(gizmo.getName())) {
                        gizmo.doAction();
                    }
                }

                for (Flipper flipper : flippers) {
                    if (name.equals(flipper.getName())) {
                        flipper.doAction();
                    }
                }

                for (AbsorberGizmo absorber : absorbers) {
                    if (name.equals(absorber.getName())) {
                        if(absorber.getBall() != null) {
                            absorber.doAction();
                        }
                    }
                }
            }
        }
    }

    private void keybindActions(String name){
        for (AbstractGizmo gizmo : gizmos) {
            if (name.equals(gizmo.getName())) {
                gizmo.doAction();
            }
        }

        for (Flipper flipper : flippers) {
            if (name.equals(flipper.getName())) {
                if(flipper.isPressed()) {
                    flipper.moveFlipper(0.0167);
                }
            }
        }

        for (AbsorberGizmo absorber : absorbers) {
            if (name.equals(absorber.getName())) {
                if(absorber.getBall() != null) {
                    absorber.doAction();
                }
            }
        }
    }

    public void changeFlipperStatus(int key){
        String name ="";
        if(keyDownMap.containsKey(key)){
            name = keyDownMap.get(key);
        }else if(keyUpMap.containsKey(key)){
            name = keyUpMap.get(key);
        }
        for (Flipper flipper : flippers) {
            if (name.equals(flipper.getName())) {
                flipper.setPressed();
            }
        }
    }

    public void keybindAction(int key){
        if(keyDownMap.containsKey(key)){
            keybindActions(keyDownMap.get(key));
        }else if(keyUpMap.containsKey(key)) {
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
        } else {
            return false;
        }
    }

    public boolean addAbsorber(String type, String name, String xPos1, String yPos1, String xPos2, String yPos2) {
        AbsorberGizmo absorberGizmo = gf.createAbsorber(type, name, xPos1, yPos1, xPos2, yPos2);
        if(absorberGizmo != null) {
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

    public boolean moveGizmo(String name,String xPos,String yPos){

        for(AbstractGizmo gizmo:gizmos){
            if(gizmo.getName().equals(name)){
                gf.removeTakenPoint(gizmo.getxPos(),gizmo.getyPos());
                Point.Double p = new Point.Double(Double.parseDouble(xPos),Double.parseDouble(yPos));

                if(!gf.isPointTaken(p)){
                    gizmo.move(Double.parseDouble(xPos),Double.parseDouble(yPos));
                    gf.addTakenPoint(gizmo.getxPos(),gizmo.getyPos());
                    this.setChanged();
                    this.notifyObservers();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveBall(String name,String xPos,String yPos){
        double toCheckX = Math.floor(Double.parseDouble(xPos));
        double toCheckY = Math.floor(Double.parseDouble(yPos));

        for(Ball ball:balls){
            if(ball.getName().equals(name)){

                Point.Double p = new Point.Double(toCheckX,toCheckY);


                if(!gf.isPointTaken(p)){
                    ball.move(Double.parseDouble(xPos),Double.parseDouble(yPos));

                    this.setChanged();
                    this.notifyObservers();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean MoveFlipper(String name,String xPos,String yPos){

        for(Flipper flipper:flippers){
            if(flipper.getName().equals(name)){
                double xPivot = Math.floor(flipper.getXPivot());
                double yPivot = Math.floor(flipper.getYPivot());

                gf.removeTakenPoint(xPivot,yPivot);
                gf.removeTakenPoint(xPivot,yPivot+1);
                gf.removeTakenPoint(xPivot+1,yPivot);
                gf.removeTakenPoint(xPivot+1,yPivot+1);

                Point.Double p = new Point.Double(Double.parseDouble(xPos),Double.parseDouble(yPos));
                if(!gf.isPointTaken(p)) {
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

    public boolean moveAbsorber(String name,String xPos,String yPos,String xPos2,String yPos2){
        double x1=Double.parseDouble(xPos);
        double x2=Double.parseDouble(xPos2);
        double y1=Double.parseDouble(yPos);
        double y2=Double.parseDouble(yPos2);

        for(AbsorberGizmo ab:absorbers){
            if(ab.getName().equals(name)){
                for(double i=ab.getyPos(); i<=ab.getyPos2(); i++){
                    for(double j=ab.getxPos(); j<=ab.getxPos2(); j++){

                        gf.removeTakenPoint((int)j,(int)i);
                    }
                }

                for(double i=y1; i<=y2; i++){
                    for(double j=x1; j<=x2; j++){
                        Point.Double p = new Point.Double(j,i);
                        if(gf.isPointTaken(p)){
                            return false;
                        }

                    }
                }

                ab.move(x1,x2,y1,y2);
                for(double i=y1; i<y2; i++){
                    for(double j=x1; j<x2; j++){
                        gf.addTakenPoint(j,i);
                    }
                }

                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }
    //TODO Marking the taken points for the ball is something that probably requires its own method,
    //TODO after all anytime we switch to build-mode we need to update the balls taken points

    public void removeBalsTakenPoint(Ball ball){

            Point squareToAddBall = new Point((int) ball.getExactX(), (int) ball.getExactY());
            Point.Double upRight = new Point.Double(ball.getExactX() + ball.getRadius(), ball.getExactY() - ball.getRadius());
            Point.Double upLeft = new Point.Double(ball.getExactX() - ball.getRadius(), ball.getExactY() - ball.getRadius());
            Point.Double downLeft = new Point.Double(ball.getExactX() - ball.getRadius(), ball.getExactY() + ball.getRadius());
            Point.Double downRight = new Point.Double(ball.getExactX() + ball.getRadius(), ball.getExactY() + ball.getRadius());
            if (upRight.x > squareToAddBall.x) {
                System.out.println("upRight");
                int xToAdd = (int) upRight.x;
                int yToAdd = (int) upRight.y;
                gf.removeTakenPoint(xToAdd, yToAdd);
            }
            if (upLeft.x < squareToAddBall.x) {// works
                System.out.println("Upleft");
                int xToAdd = (int) upLeft.x;
                int yToAdd = (int) upLeft.y;
                gf.removeTakenPoint(xToAdd, yToAdd);
            }
            if (downLeft.y > squareToAddBall.y) {
                System.out.println("downLeft");
                int xToAdd = (int) downLeft.x;
                int yToAdd = (int) downLeft.y;
                gf.removeTakenPoint(xToAdd, yToAdd);
            }
            if (downRight.x > squareToAddBall.x && downRight.y > squareToAddBall.y) { // works
                System.out.println("downRight");
                int xToAdd = (int) downRight.x;
                int yToAdd = (int) downRight.y;
                gf.removeTakenPoint(xToAdd, yToAdd);
            }

            this.setChanged();
            this.notifyObservers();
        }


    public void addBallsTakenPoints(Ball ball){

            Point squareToAddBall = new Point((int) ball.getExactX(), (int) ball.getExactY());

            //addGizmo("square","testing",String.valueOf(squareToAddBall.x),String.valueOf(squareToAddBall.y));

            gf.addTakenPoint(squareToAddBall.x, squareToAddBall.y);
            //TODO need to think about invalid points

            Point.Double upRight = new Point.Double(ball.getExactX() + ball.getRadius(), ball.getExactY() - ball.getRadius());
            Point.Double upLeft = new Point.Double(ball.getExactX() - ball.getRadius(), ball.getExactY() - ball.getRadius());
            Point.Double downLeft = new Point.Double(ball.getExactX() - ball.getRadius(), ball.getExactY() + ball.getRadius());
            Point.Double downRight = new Point.Double(ball.getExactX() + ball.getRadius(), ball.getExactY() + ball.getRadius());
            if (upRight.x > squareToAddBall.x) {
                System.out.println("upRight");
                int xToAdd = (int) upRight.x;
                int yToAdd = (int) upRight.y;
                gf.addTakenPoint(xToAdd, yToAdd);
            }
            if (upLeft.x < squareToAddBall.x) {// works
                System.out.println("Upleft");
                int xToAdd = (int) upLeft.x;
                int yToAdd = (int) upLeft.y;
                gf.addTakenPoint(xToAdd, yToAdd);
            }
            if (downLeft.y > squareToAddBall.y) {
                System.out.println("downLeft");
                int xToAdd = (int) downLeft.x;
                int yToAdd = (int) downLeft.y;
                gf.addTakenPoint(xToAdd, yToAdd);
            }
            if (downRight.x > squareToAddBall.x && downRight.y > squareToAddBall.y) { // works
                System.out.println("downRight");
                int xToAdd = (int) downRight.x;
                int yToAdd = (int) downRight.y;
                gf.addTakenPoint(xToAdd, yToAdd);
            }
            this.setChanged();
            this.notifyObservers();
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
        }catch (NumberFormatException e){
            return false;
        }

        Point.Double p = new Point.Double(Math.floor(x),Math.floor(y));

        if (!gf.isPointTaken(p)) {
            balls.add(new Ball(type, name, x, y, xv, yv, 0.25));


            Point squareToAddBall = new Point((int) Double.parseDouble(xPos), (int) Double.parseDouble(yPos));

          //addGizmo("square","testing",String.valueOf(squareToAddBall.x),String.valueOf(squareToAddBall.y));

            gf.addTakenPoint(squareToAddBall.x,squareToAddBall.y);
            //TODO need to think about invalid points
            Ball ball = balls.get(balls.size() - 1);
            Point.Double upRight = new Point.Double(ball.getExactX()+ball.getRadius(),ball.getExactY()-ball.getRadius());
            Point.Double upLeft = new Point.Double(ball.getExactX()-ball.getRadius(),ball.getExactY()-ball.getRadius());
            Point.Double downLeft = new Point.Double(ball.getExactX()-ball.getRadius(),ball.getExactY()+ball.getRadius());
            Point.Double downRight= new Point.Double(ball.getExactX()+ball.getRadius(),ball.getExactY()+ball.getRadius());
            if (upRight.x > squareToAddBall.x) {
                System.out.println("upRight");
                int xToAdd = (int)upRight.x;
                int yToAdd=(int) upRight.y;
                gf.addTakenPoint(xToAdd,yToAdd);
            }
            if (upLeft.x < squareToAddBall.x) {// works
                System.out.println("Upleft");
                int xToAdd = (int)upLeft.x;
                int yToAdd=(int) upLeft.y;
                gf.addTakenPoint(xToAdd,yToAdd);
            }
            if (downLeft.y > squareToAddBall.y) {
                System.out.println("downLeft");
                int xToAdd = (int)downLeft.x;
                int yToAdd=(int) downLeft.y;
                gf.addTakenPoint(xToAdd,yToAdd);
            }
            if (downRight.x > squareToAddBall.x && downRight.y>squareToAddBall.y) { // works
                System.out.println("downRight");
                int xToAdd = (int)downRight.x;
                int yToAdd=(int) downRight.y;
                gf.addTakenPoint(xToAdd,yToAdd);
            }
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }

    public boolean addKeyBind(int key, String gizmoName) {
        System.out.println(key);
        if (!keyDownMap.containsKey(key)) {
            keyDownMap.put(key, gizmoName);
            return true;
        }

        return false;
    }

    private boolean isSourceAGizmo(String source){
        for(AbstractGizmo gizmo:gizmos){
            if(gizmo.getName().equals(source)){
                return true;
            }
        }

        for(Flipper flipper:flippers){
            if(flipper.getName().equals(source)){
                return true;
            }
        }

        for(AbsorberGizmo absorberGizmo: absorbers){
            if(absorberGizmo.getName().equals(source)){
                return true;
            }
        }

        for(Ball ball:balls){
            if(ball.getName().equals(source)){
                return true;
            }
        }
        return false;
    }

    public boolean addTrigger(String source, String target){
        if(!source.equals("") && !target.equals("")) {
            List<String> temp = triggers.get(source);
            if (temp == null) {
                if(isSourceAGizmo(source)) {
                    temp = new ArrayList<>();
                    temp.add(target);
                    triggers.put(source, temp);
                    return true;
                }
            } else {
                if (!temp.contains(target)) {
                    triggers.get(source).add(target);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean rotate(double x, double y){
        return rotateGizmo(x,y) || rotateFlipper(x,y);
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

    private boolean rotateFlipper(double x, double y){
        for (Flipper flipper : flippers) {
            if (flipperCheck(flipper.getXPivot(),flipper.getYPivot(),Math.floor(x),Math.floor(y))) {
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
                if(triggers.containsKey(abstractGizmo.getName())){
                    triggers.remove(abstractGizmo.getName());
                }
                removeKeybind(abstractGizmo.getName());
                gizmos.remove(abstractGizmo);
                gf.removeTakenPoint(flooredx,flooredy);
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
               Point squareToAddBall = new Point((int)ball.getExactX(),(int)ball.getExactY());
                Point.Double upRight = new Point.Double(ball.getExactX()+ball.getRadius(),ball.getExactY()-ball.getRadius());
                Point.Double upLeft = new Point.Double(ball.getExactX()-ball.getRadius(),ball.getExactY()-ball.getRadius());
                Point.Double downLeft = new Point.Double(ball.getExactX()-ball.getRadius(),ball.getExactY()+ball.getRadius());
                Point.Double downRight= new Point.Double(ball.getExactX()+ball.getRadius(),ball.getExactY()+ball.getRadius());
                if (upRight.x > squareToAddBall.x) {
                    System.out.println("upRight");
                    int xToAdd = (int)upRight.x;
                    int yToAdd=(int) upRight.y;
                    gf.removeTakenPoint(xToAdd,yToAdd);
                }
                if (upLeft.x < squareToAddBall.x) {// works
                    System.out.println("Upleft");
                    int xToAdd = (int)upLeft.x;
                    int yToAdd=(int) upLeft.y;
                    gf.removeTakenPoint(xToAdd,yToAdd);
                }
                if (downLeft.y > squareToAddBall.y) {
                    System.out.println("downLeft");
                    int xToAdd = (int)downLeft.x;
                    int yToAdd=(int) downLeft.y;
                    gf.removeTakenPoint(xToAdd,yToAdd);
                }
                if (downRight.x > squareToAddBall.x && downRight.y>squareToAddBall.y) { // works
                    System.out.println("downRight");
                    int xToAdd = (int)downRight.x;
                    int yToAdd=(int) downRight.y;
                    gf.removeTakenPoint(xToAdd,yToAdd);
                }
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

                if(triggers.containsKey(ab.getName())){
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

            if (flipperCheck(flipper.getXPivot(),flipper.getYPivot(),x,y)) {
                gf.removeTakenPoint(pivotX, pivotY);
                gf.removeTakenPoint(pivotX + 1, pivotY);
                gf.removeTakenPoint(pivotX, pivotY + 1);
                gf.removeTakenPoint(pivotX + 1, pivotY + 1);
                if(triggers.containsKey(flipper.getName())){
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

        if (!keyDownMap.isEmpty()){
            keyDownMap.entrySet().removeIf(e -> keyDownMap.get(key).equals(gizmoName));
            removed = true;
        }

        if (!keyUpMap.isEmpty()){
            keyUpMap.entrySet().removeIf(e -> keyDownMap.get(key).equals(gizmoName));
            removed = true;
        }

        return removed;
    }

    private void removeKeybind(String gizmoName) {

        if (!keyDownMap.isEmpty()){
            keyDownMap.entrySet().removeIf(e -> e.getValue().equals(gizmoName));
        }

        if (!keyUpMap.isEmpty()){
            keyUpMap.entrySet().removeIf(e -> e.getValue().equals(gizmoName));
        }

    }

    public boolean removeTrigger(String source, String target){
        List<String> temp = triggers.get(source);

        if(temp == null){
            return false;
        }else if(!temp.contains(target)){
            return false;
        }else{
            triggers.get(source).remove(target);
            return true;
        }
    }

    public String findName(double x, double y){
        String gizmo = findGizmo(x,y);
        if(gizmo != null && !gizmo.isEmpty()) {
            String[] temp = gizmo.split(" ");
            return temp[1];
        }
        return "";
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
                double height = ab.getyPos2()-ab.getyPos();
                double width = ab.getxPos2()-ab.getxPos();
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
            if (flipperCheck(flipper.getXPivot(),flipper.getYPivot(),flooredx,flooredy)) {
                return flipper.getType()+" "+flipper.getName()+" "+flipper.getXPivot()+" "+flipper.getYPivot();
            }

        }
        return "";
    }

    private boolean flipperCheck(double flipperX, double flipperY, double targetX, double targetY){
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

    private boolean isWindows() {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.contains("win"));
    }

    public void save(String directory, String fileName) {
        String name;
        if (isWindows()) {
            name = directory + "\\" + fileName + ".giz";
        } else {
            name = directory + "/" + fileName + ".giz";
        }
        File file = new File((name));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (AbstractGizmo gizmo : gizmos) {
                writer.write(gizmo.toString() + "\n");
                if(gizmo.getRotation() > 0){
                    for(int i = 0; i <= gizmo.getRotation(); i++){
                        writer.write("Rotate " + gizmo.getName() + "\n");
                    }
                }
            }

            for (Flipper flipper : flippers) {
                writer.write(flipper.toString() + "\n");
                if(flipper.getRotation() > 0){
                    for(int i = 0; i <= flipper.getRotation(); i++){
                        writer.write("Rotate " + flipper.getName() + "\n");
                    }
                }
            }

            for (AbsorberGizmo absorber : absorbers) {
                writer.write(absorber.toString() + "\n");
            }

            for (Ball ball : balls) {
                writer.write(ball.toString() + "\n");
            }

            for(String s : triggers.keySet()){
                for(String ss : triggers.get(s)){
                    writer.write("Connect " + s + " " + ss + "\n");
                }
            }

            for(int i : keyDownMap.keySet()){
                writer.write("KeyConnect key " + i + " " + "down" + " " + keyDownMap.get(i) + "\n");
            }

            for(int i : keyUpMap.keySet()){
                writer.write("KeyConnect key " + i + " " + "up" + " " + keyUpMap.get(i) + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean load(String directory, String fileName) {
        clearModel();
        String name;
        boolean success=true;
        if (isWindows()) {
            name = directory + "\\" + fileName;
        } else {
            name = directory + "/" + fileName;
        }
        char lastCharacter = name.charAt(name.length()-1);
        char secondLast=name.charAt(name.length()-2);
        char thirdLast = name.charAt(name.length()-3);
        char fourthLast = name.charAt(name.length()-4);

        char[] extension = {fourthLast,thirdLast,secondLast,lastCharacter};
        String sExentsion = new String(extension);
        if(!sExentsion.equals(".giz")){
            return false;
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
                            success=addGizmo(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                            if(!success){
                                clearModel();
                                return false;
                            }
                        }

                        if (token.toLowerCase().equals("triangle")) {
                            success=addGizmo(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                            if(!success){
                                clearModel();
                                return false;
                            }
                        }

                        if (token.toLowerCase().equals("circle")) {
                            success=addGizmo(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                            if(!success){
                                clearModel();
                                return false;
                            }
                        }

                        if (token.toLowerCase().equals("absorber")) {
                            success=addAbsorber(token, tokenizer.nextToken(), tokenizer.nextToken(),
                                    tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                            if(!success){
                                clearModel();
                                return false;
                            }
                        }

                        if (token.toLowerCase().equals("leftflipper")) {
                            success=addFlipper(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                            if(!success){
                                clearModel();
                                return false;
                            }
                        }

                        if (token.toLowerCase().equals("rightflipper")) {
                            success=addFlipper(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                            if(!success){
                                clearModel();
                                return false;
                            }
                        }

                        if (token.toLowerCase().equals("ball")) {
                            success=addBall(token, tokenizer.nextToken(), tokenizer.nextToken(),
                                    tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                            if(!success){
                                clearModel();
                                return false;
                            }
                        }

                        if (token.toLowerCase().equals("rotate")) {
                            String target = tokenizer.nextToken();
                            for (AbstractGizmo t : gizmos) {
                                if (t.getName().equals(target)) {
                                    t.rotate();
                                }
                            }

                            for(Flipper f : flippers){
                                if(f.getName().equals(target)){
                                    f.rotate();
                                }
                            }
                        }

                        if(token.toLowerCase().equals("connect")){
                            String nameA = tokenizer.nextToken();
                            String nameB = tokenizer.nextToken();

                            success=addTrigger(nameA, nameB);
                            if(!success){
                                clearModel();
                                return false;
                            }
                        }

                        if(token.toLowerCase().equals("keyconnect")){
                            tokenizer.nextToken();
                            String key = tokenizer.nextToken();
                            String type = tokenizer.nextToken();
                            String gizmoName = tokenizer.nextToken();

                            if(type.toLowerCase().equals("down")){
                                keyDownMap.put(Integer.parseInt(key), gizmoName);
                            }else{
                                keyUpMap.put(Integer.parseInt(key),gizmoName);
                            }

                        }

                    }
                } catch (NoSuchElementException e) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setChanged();
        this.notifyObservers();
        return true;
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

    private void clearModel() {
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
 public void cleanUpWhenBallMoves(){
        for(Ball ball:balls){
            removeBalsTakenPoint(ball);
        }
 }

 public void setNewBallsTakenPoints(){
     for (Ball ball:balls){
         addBallsTakenPoints(ball);
     }
 }
}
