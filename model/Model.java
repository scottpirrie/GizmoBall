package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.io.*;
import java.util.*;

public class Model extends Observable{

    private int L;
    private GizmoFactory gf;
    private Ball ball;
    private Walls gws;
    private List<AbstractGizmo> gizmos;
    private List<Flipper> flippers;
    private List<Ball> balls;

    //TODO Somehow get a value for L into the model...
    //TODO Should we pass it in at the start? In Main? Some kind of "pre-launch" set up?
    public Model() {
        this.L=500/20;

        gws = new Walls(0, 0, L*20, L*20);
        gizmos = new ArrayList<>();
        flippers = new ArrayList<>();
        balls = new ArrayList<>();
        gf = new GizmoFactory(L);

        //TODO probably remove ball from creation on initialisation
        ball = new Ball("Ball","B",4*L, 4*L, 100, 100,L/4);
        balls.add(ball); //Testing purposes
    }

    public void moveBall(double move) {
        if (move > 0) {
            double moveTime = move; // 0.05 = 20 times per second as per Gizmoball

            if (ball != null && !ball.stopped()) {
                CollisionDetails cd = timeUntilCollision();
                double tuc = cd.getTuc();
                if (tuc > moveTime) {
                    ball = movelBallForTime(ball, moveTime);
                } else {
                    ball = movelBallForTime(ball, tuc);
                    ball.setVelo(cd.getVelo());
                }

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

        //Add check for flipper collision here


        //Add check for ball-to-ball collisions here


        return new CollisionDetails(shortestTime, newVelo);
    }

    public List<AbstractGizmo> getGizmos(){
        return gizmos;
    }

    public List<Flipper> getFlippers(){
        return flippers;
    }

    public List<Ball> getBalls(){
        return balls;
    }

    public void addGizmo(String type, String name, String xPos, String yPos){
        gizmos.add(gf.createGizmo(type,name,xPos,yPos));
    }

    public void addAbsorber(String type, String name, String xPos1, String yPos1,String xPos2, String yPos2){
        gizmos.add(gf.createAbsorber(type,name,xPos1,yPos1,xPos2,yPos2));
    }

    public void addFlipper(String type, String name, String xPos, String yPos){
        flippers.add(gf.createFlipper(type,name,xPos,yPos));
    }

    public void addBall(String type, String name, String xPos, String yPos, String xVelo, String yVelo){
        double x = Double.parseDouble(xPos);
        double y = Double.parseDouble(yPos);
        double xv = Double.parseDouble(xVelo);
        double yv = Double.parseDouble(yVelo);
        balls.add(new Ball(type,name,x,y,xv,yv,L/2));
    }

    public void setBallSpeed(String name,int xv, int yv) {
        for(Ball  ball : balls) {
            if(ball.getName().equals(name)) {
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
                System.out.println("Reading...");
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

        this.setChanged();
        this.notifyObservers();
        return true;
    }

    //TODO clear triggers as well when they are implemented
    private void clearModel(){
        gizmos.clear();
        flippers.clear();
        balls.clear();
        //Probably triggers clear as well
    }

}
