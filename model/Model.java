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
            double moveTime = move; // 0.0167 = 60 times per second
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
                            callActions(triggerSource);
                        }
                        setGravity(ball, moveTime);
                        setFriction(ball, moveTime);
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

    //TODO fix gravity + Friction ( though i think its gravity )
    private void setGravity(Ball ball,double time) {
        if(!ball.stopped()) {
            ball.setVelo(ball.getVelo().plus(new Vect(0, (Math.sqrt(gravityConstant) * 20) * time)));
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
                    flipper.moveFlipper(0.017);
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
                if(!flipper.isPressed()){
                    flipper.setPressed(true);
                }else{
                    flipper.setPressed(false);
                }
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

    //TODO Marking the taken points for the ball is something that probably requires its own method,
    //TODO after all anytime we switch to build-mode we need to update the balls taken points
    public boolean addBall(String type, String name, String xPos, String yPos, String xVelo, String yVelo) {

        double x = Double.parseDouble(xPos);
        double y = Double.parseDouble(yPos);
        double xv = Double.parseDouble(xVelo);
        double yv = Double.parseDouble(yVelo);
        Point.Double p = new Point.Double(Math.floor(x),Math.floor(y));

        if (!gf.isPointTaken(p)) {
            balls.add(new Ball(type, name, x, y, xv, yv, 0.25));


            Point squareToAddBall = new Point((int) Double.parseDouble(xPos), (int) Double.parseDouble(yPos));

            int maxWidth = squareToAddBall.x + 1;
            int maxHeight = squareToAddBall.y + 1;
            int leastWidth = squareToAddBall.x;
            int leastHeight = squareToAddBall.y;

     
            //TODO need to think about invalid points
            Ball ball = balls.get(balls.size() - 1);
            /*if (ball.getExactX() - ball.getRadius() < leastWidth) {
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
            }*/
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

    public boolean addTrigger(String source, String target){
        if(!source.equals("") && !target.equals("")) {
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
                gizmos.remove(abstractGizmo);
                x = Math.floor(x);
                y = Math.floor(y);
                gf.removeTakenPoint((int) flooredx,(int) flooredy);
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

    private boolean removeAbsorber(double x, double y) {
        double flooredx = Math.floor(x);
        double flooredy = Math.floor(y);

        for (AbsorberGizmo ab : absorbers) {
            if ((flooredx >= ab.getxPos() && flooredx <= ab.getxPos2()) && (flooredy >= ab.getyPos() && flooredy <= ab.getyPos2())) {
                if(triggers.containsKey(ab.getName())){
                    triggers.remove(ab.getName());
                }
                absorbers.remove(ab);
                for (int i = (int)ab.getyPos(); i <= ab.getyPos2(); i++) {
                    for (int j = (int)ab.getxPos(); j <= ab.getxPos2(); j++) {
                        gf.removeTakenPoint(j, i);
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

        for (Flipper flipper : flippers) {
            if (flipperCheck(flipper.getXPivot(),flipper.getYPivot(),x,y)) {
                gf.removeTakenPoint((int) flipper.getXPivot(), (int) flipper.getYPivot());
                gf.removeTakenPoint((int) flipper.getXPivot() + 1, (int) flipper.getYPivot());
                gf.removeTakenPoint((int) flipper.getXPivot(), (int) flipper.getYPivot() + 1);
                gf.removeTakenPoint((int) flipper.getXPivot() + 1, (int) flipper.getYPivot() + 1);
                if(triggers.containsKey(flipper.getName())){
                    triggers.remove(flipper.getName());
                }
                flippers.remove(flipper);
                return true;
            }

        }
        return false;
    }

    public boolean removeKeybind(int key, String gizmoName) {
        if(keyDownMap.size() > 0) {
            if (keyDownMap.containsKey(key)) {
                keyDownMap.remove(key, gizmoName);
                return true;
            }
        }
        return false;
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
            name = directory + "\\" + fileName + ".txt";
        } else {
            name = directory + "/" + fileName + ".txt";
        }
        File file = new File((name));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (AbstractGizmo gizmo : gizmos) {
                writer.write(gizmo.toString() + "\n");
                if(gizmo.getRotation() > 0){
                    for(int i = 0; i < gizmo.getRotation(); i++){
                        writer.write("Rotate " + gizmo.getName() + "\n");
                    }
                }
            }

            for (Flipper flipper : flippers) {
                writer.write(flipper.toString() + "\n");
                if(flipper.getRotation() > 0){
                    for(int i = 0; i < flipper.getRotation(); i++){
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
                writer.write("KeyConnect key " + i + " " + "down" + " " + keyDownMap.get(i));
            }

            for(int i : keyUpMap.keySet()){
                writer.write("KeyConnect key " + i + " " + "up" + " " + keyUpMap.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

                        if(token.toLowerCase().equals("connect")){
                            String nameA = tokenizer.nextToken();
                            String nameB = tokenizer.nextToken();
                            addTrigger(nameA, nameB);
                        }

                        if(token.toLowerCase().equals("keyconnect")){
                            tokenizer.nextToken();
                            String key = tokenizer.nextToken();
                            String type = tokenizer.nextToken();
                            String gizmoName = tokenizer.nextToken();

                            if(type.toLowerCase().equals("down")){
                                keyDownMap.put(Integer.parseInt(key),gizmoName);
                            }else{
                                keyUpMap.put(Integer.parseInt(key),gizmoName);
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

    public Map<Integer,String> getKeyDownMap(){
        return keyDownMap;
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

}
