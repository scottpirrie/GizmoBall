package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

class FileParser {

    private final Model model;

    FileParser(Model model){
        this.model = model;
    }

    private boolean isWindows() {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.contains("win"));
    }

    void save(String directory, String fileName) {
        String name;
        if (isWindows()) {
            name = directory + "\\" + fileName + ".giz";
        } else {
            name = directory + "/" + fileName + ".giz";
        }
        File file = new File((name));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (AbstractGizmo gizmo : model.getGizmos()) {
                writer.write(gizmo.toString() + "\n");
                if (gizmo.getRotation() > 0) {
                    for (int i = 0; i <= gizmo.getRotation(); i++) {
                        writer.write("Rotate " + gizmo.getName() + "\n");
                    }
                }
            }

            for (Flipper flipper : model.getFlippers()) {
                writer.write(flipper.toString() + "\n");
                if (flipper.getRotation() > 0) {
                    for (int i = 0; i <= flipper.getRotation(); i++) {
                        writer.write("Rotate " + flipper.getName() + "\n");
                    }
                }
            }

            for (AbsorberGizmo absorber : model.getAbsorbers()) {
                writer.write(absorber.toString() + "\n");
            }

            for (Ball ball : model.getBalls()) {
                writer.write(ball.toString() + "\n");
            }

            for (String s : model.getTriggers().keySet()) {
                for (String ss : model.getTriggers().get(s)) {
                    writer.write("Connect " + s + " " + ss + "\n");
                }
            }

            for (int i : model.getKeyDownMap().keySet()) {
                writer.write("KeyConnect key " + i + " " + "down" + " " + model.getKeyDownMap().get(i) + "\n");
            }

            for (int i : model.getKeyUpMap().keySet()) {
                writer.write("KeyConnect key " + i + " " + "up" + " " + model.getKeyUpMap().get(i) + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean load(String directory, String fileName) {
        model.clearModel();
        String name;

        if (isWindows()) {
            name = directory + "\\" + fileName;
        } else {
            name = directory + "/" + fileName;
        }

        if (!name.contains(".giz")) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(new File(name)))) {
            // fix load
            StringTokenizer tokenizer;
            String line;
            while ((line = br.readLine()) != null) {

                try {

                    tokenizer = new StringTokenizer(line);
                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();

                        if (token.toLowerCase().equals("square")) {
                            if (!model.addGizmo(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken())) {
                                model.clearModel();
                                return false;
                            }
                        }

                        else if (token.toLowerCase().equals("triangle")) {
                            if (!model.addGizmo(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken())) {
                                model.clearModel();
                                return false;
                            }
                        }

                        else if (token.toLowerCase().equals("circle")) {
                            if (!model.addGizmo(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken())) {
                                model.clearModel();
                                return false;
                            }
                        }

                       else if (token.toLowerCase().equals("absorber")) {
                            if (!model.addAbsorber(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken())) {
                                model.clearModel();
                                return false;
                            }
                        }

                       else if (token.toLowerCase().equals("leftflipper")) {
                            if (!model.addFlipper(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken())) {
                                model.clearModel();
                                return false;
                            }
                        }

                       else if (token.toLowerCase().equals("rightflipper")) {
                            if (!model.addFlipper(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken())) {
                                model.clearModel();
                                return false;
                            }
                        }

                       else if (token.toLowerCase().equals("ball")) {
                            if (!model.addBall(token, tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken())) {
                                model.clearModel();
                                return false;
                            }
                        }

                        else if (token.toLowerCase().equals("rotate")) {
                            String target = tokenizer.nextToken();
                            for (AbstractGizmo t : model.getGizmos()) {
                                if (t.getName().equals(target)) {
                                    t.rotate();
                                }
                            }

                            for (Flipper f : model.getFlippers()) {
                                if (f.getName().equals(target)) {
                                    f.rotate();
                                }
                            }
                        }

                       else if (token.toLowerCase().equals("connect")) {
                            String nameA = tokenizer.nextToken();
                            String nameB = tokenizer.nextToken();

                            if (!model.addTrigger(nameA, nameB)) {
                                model.clearModel();
                                return false;
                            }
                        }

                        else if (token.toLowerCase().equals("keyconnect")) {
                            tokenizer.nextToken();
                            String key = tokenizer.nextToken();
                            String type = tokenizer.nextToken();
                            String gizmoName = tokenizer.nextToken();

                            if (type.toLowerCase().equals("down")) {
                                model.addKeyBind(Integer.parseInt(key),gizmoName);
                            } else {
                                if (model.checkGizmoExists(gizmoName)) {
                                    if (model.getKeyUpMap().containsKey(Integer.parseInt(key))) {
                                        List<String> actions = model.getKeyUpMap().get(Integer.parseInt(key));
                                        actions.add(gizmoName);
                                        model.getKeyUpMap().put(Integer.parseInt(key), actions);
                                    } else {
                                        ArrayList<String> toAdd = new ArrayList<>();
                                        toAdd.add(gizmoName);
                                        model.getKeyUpMap().put(Integer.parseInt(key), toAdd);
                                    }
                                }
                            }

                        }

                    }
                } catch (NoSuchElementException e) {
                    return false;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
