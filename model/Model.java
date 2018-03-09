package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.io.*;
import java.util.*;

public class Model extends Observable{

    private GizmoFactory gf;
    private Ball ball;
    private Walls gws;
    private List<AbstractGizmo> gizmos;
    private List<AbsorberGizmo> absorbers;
    private List<Flipper> flippers;
    private List<Ball> balls;

    public Model() {
        gws = new Walls(0, 0, 20, 20);
        gizmos = new ArrayList<>();
        absorbers = new ArrayList<>();
        flippers = new ArrayList<>();
        balls = new ArrayList<>();
        gf = new GizmoFactory();

    }

    //TODO Make this method multi-ball capable - later on
    public void moveBall(double move) {
        if (move > 0) {
            double moveTime = move; // 0.05 = 20 times per second as per Gizmoball
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

                setGravity(ball);
                setFriction(ball,moveTime);
                this.setChanged();
                this.notifyObservers();

            }
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

    private void setGravity(Ball ball){
        ball.setVelo(ball.getVelo().plus(new Vect(0,(25*10)*0.00981)));
    }

    private void setFriction(Ball ball, double time){
        double mu1 = 0.025; //per/second
        double mu2 = 0.025; //per/L
        double oldX = ball.getVelo().x();
        double oldY = ball.getVelo().y();
        double nyV = 0.0;
        double nxV = 0.0;
        //Vnew = Vold * (1 - mu * delta_t - mu2 * |Vold| * delta_t)
        nxV = (oldX * (1 - (mu1/20) * time - (mu2*0.05) * Math.abs(oldX) * time));
        nyV = (oldY * (1 - (mu1/20)* time - (mu2*0.05) * Math.abs(oldY) * time));
        ball.setVelo(new Vect(nxV,nyV));
    }

    //TODO Add collisions with flippers / balls
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

        for(AbstractGizmo gizmo : gizmos){
            if(!gizmo.getType().toLowerCase().equals("circle")) {
                for (LineSegment line : gizmo.getLines()) {
                    time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
                    }
                }
            }
            for(Circle circle: gizmo.getCircles()){
                time=Geometry.timeUntilCircleCollision(circle,ballCircle,ballVelocity);
                if(time<shortestTime){
                    shortestTime=time;
                    newVelo=Geometry.reflectCircle(circle.getCenter(),ballCircle.getCenter(),ballVelocity,1.0);
                }
            }
        }

        for(AbsorberGizmo absorber : absorbers){
            for (LineSegment line : absorber.getLines()) {
                time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
                }
            }
            for(Circle circle: absorber.getCircles()){
                time=Geometry.timeUntilCircleCollision(circle,ballCircle,ballVelocity);
                if(time<shortestTime){
                    shortestTime=time;
                    newVelo=Geometry.reflectCircle(circle.getCenter(),ballCircle.getCenter(),ballVelocity,1.0);
                }
            }
        }

        //Add check for flipper collision here


        //Add check for ball-to-ball collisions here


        return new CollisionDetails(shortestTime, newVelo);
    }

    public List<AbstractGizmo> getGizmos(){
        return gizmos;
    }

    public List<AbsorberGizmo> getAbsorbers(){
        return absorbers;
    }

    public List<Flipper> getFlippers(){
        return flippers;
    }

    public List<Ball> getBalls(){
        return balls;
    }

    public boolean addGizmo(String type, String name, String xPos, String yPos){
        AbstractGizmo gizmo = gf.createGizmo(type,name,xPos,yPos);
        if(gizmo != null) {
            gizmos.add(gizmo);
            this.setChanged();
            this.notifyObservers();
            return true;
        }else{
            return false;
        }
    }

    public void addAbsorber(String type, String name, String xPos1, String yPos1,String xPos2, String yPos2){
        absorbers.add(gf.createAbsorber(type,name,xPos1,yPos1,xPos2,yPos2));
        this.setChanged();
        this.notifyObservers();
    }

    public void addFlipper(String type, String name, String xPos, String yPos){
        flippers.add(gf.createFlipper(type,name,xPos,yPos));
    }

    public boolean addBall(String type, String name, String xPos, String yPos, String xVelo, String yVelo){
        gf.addTakenPoint((int)Double.parseDouble(xPos),(int)Double.parseDouble(yPos));
        double x = Double.parseDouble(xPos);
        double y = Double.parseDouble(yPos);
        double xv = Double.parseDouble(xVelo);
        double yv = Double.parseDouble(yVelo);
        balls.add(new Ball(type,name,x,y,5,5,0.25));
        return true;
    }

    public void setBallSpeed(String name,int xv, int yv) {
        for(Ball  ball : balls) {
            if(ball.getName().equals(name)) {
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
    public void save(String directory,String fileName){
        String name;
        if(isWindows()) {
            name = directory + "\\" + fileName + ".txt";
        }else{
            name = directory + "/" + fileName + ".txt";
        }
        File file = new File((name));
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for(AbstractGizmo gizmo : gizmos){
                writer.write(gizmo.toString() + "\n");
            }

            for(Flipper flipper : flippers){
                writer.write(flipper.toString() + "\n");
            }

            for(AbsorberGizmo absorber : absorbers){
                writer.write(absorber.toString() + "\n");
            }

            for(Ball ball : balls){
                writer.write(ball.toString() + "\n");
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //TODO load CONNECT / KEYCONNECT commands
    public boolean load(String directory,String fileName) {
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
                            addGizmo(token,tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("triangle")) {
                            addGizmo(token,tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("circle")) {
                            addGizmo(token,tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("absorber")) {
                            addAbsorber(token,tokenizer.nextToken(),tokenizer.nextToken(),
                                    tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("leftflipper")) {
                            addFlipper(token,tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("rightflipper")) {
                            addFlipper(token,tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken());
                        }

                        if (token.toLowerCase().equals("ball")) {
                            addBall(token,tokenizer.nextToken(),tokenizer.nextToken(),
                                    tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken());
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
        }catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Loading model...");
        this.setChanged();
        this.notifyObservers();
        return true;
    }

    //TODO clear triggers as well when they are implemented
    public void clearModel(){
        System.out.println("Clearing model...");
        gizmos.clear();
        flippers.clear();
        balls.clear();
        gf.clearPoints();
        this.setChanged();
        this.notifyObservers();
    }

    public boolean removeGizmo(double x,double y,int L){
        for(AbstractGizmo abstractGizmo: gizmos){
            int tempX=(int)x/L;
            int tempY=(int)y/L;
            if(abstractGizmo.getxPos()==tempX && abstractGizmo.getyPos()==tempY){
                gizmos.remove(abstractGizmo);
                gf.removeTakenPoint(tempX,tempY);
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        for(Ball ball :balls){
            x=x/(double) L;
            y=y/(double) L;
            double distanceFromClickToCenter=Math.abs(Math.pow(x-ball.getExactX(),2))+Math.abs(Math.pow(y-ball.getExactY(),2));
            if(distanceFromClickToCenter<=Math.pow(ball.getRadius(),2)){
                balls.remove(ball);
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }



}
