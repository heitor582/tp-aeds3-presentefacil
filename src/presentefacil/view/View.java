package view;

import java.util.List;
import java.util.Scanner;

import repository.GlobalMemory;
import repository.ViewStackMemory;

public abstract class View {
    protected final Scanner scanner = new Scanner(System.in);
    private final String applicationTitle = """
            PresenteFácil 1.0
            -----------------""";
    protected String viewName = "";
    private boolean registerOnPath = false;

    public View(final String viewName, final boolean registerOnPath) {
        this.viewName = viewName;
        this.registerOnPath = registerOnPath;
    }

    protected abstract void viewDisplay();

    private void printPath() {
        List<View> snapshot = ViewStackMemory.toList();
        String path = "";
        boolean first = true;
        for (int i = 0; i < snapshot.size(); i++) {
            View view = snapshot.get(i);
            if (view.registerOnPath) {
                if (!first)
                    path += " ";
                path += "> " + view.viewName;
                first = false;
            }
        }
        if (snapshot.size() > 2)
            path += " ";
        if (this.registerOnPath)
            path += "> " + this.viewName;
        if (path.length() > 0)
            System.out.println(path + "\n");
    }

    public void display() {
        clearScreen();
        System.out.println(applicationTitle);
        printPath();
        viewDisplay();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
        }
        System.out.flush();
    }

    protected void reload() {
        clearScreen();
        System.out.println(applicationTitle);
        printPath();
    }

    protected void nextPage(View newView) {
        ViewStackMemory.push(this);
        newView.display();
        this.back();
    }

    protected void alertMessage(final String message, Object... args) {
        try {
            System.out.printf(message + "\n", args);
            Thread.sleep(1000);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    protected void back() {
        View backView = ViewStackMemory.pop();
        backView.display();
    }

    protected void logout() {
        ViewStackMemory.reset();
        GlobalMemory.logout();
        this.nextPage(StartMenuView.INSTANCE);
    }

    protected void exit() {
        System.out.println("Saindo do sistema. Até logo!");
        System.exit(0);
    }
}
