package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.Pair;
import com.fengjx.reload.common.utils.ArrayUtils;
import com.fengjx.reload.common.utils.StrUtils;
import com.fengjx.reload.common.utils.ThreadUtils;
import com.fengjx.reload.watcher.App;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.cli.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author fengjianxin
 */
@Singleton
public class CommandListener implements Runnable {

    @Inject
    private App app;
    @Inject
    private CmdFactory cmdFactory;
    private Thread thread;

    public static Option help = Option.builder("h")
            .longOpt("help")
            .required(false)
            .hasArg(false)
            .build();

    public void listen() {
        thread = ThreadUtils.run("command-listener", true, this);
    }

    @Override
    public void run() {
        doWork();
    }

    private void doWork() {
        while (app.isRunning()) {
            try {
                AnsiLog.info("输入指令，'h' 查看帮助");
                String line = new Scanner(System.in).nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] arr = line.split("\\s+");
                String action = arr[0];
                Cmd cmd = cmdFactory.getCmd(action);
                if (cmd != null) {
                    Options options = new Options();
                    options.addOption(help);
                    cmd.options(options);
                    String cmdLineSyntax = StrUtils.isBlank(cmd.lineSyntax()) ? action : cmd.lineSyntax();
                    cmdLineSyntax += "\nSUMMARY:\n" + cmd.help() + "\n\nOPTIONS:\n";
                    Pair<Boolean, CommandLine> pair = parseArgs(cmdLineSyntax, ArrayUtils.sliceOfArray(arr, 1), options);
                    if (!pair.getFirst() && pair.getSecond() != null) {
                        cmd.handle(pair.getSecond());
                    }
                }
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                AnsiLog.error("cmd exec error: {}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private Pair<Boolean, CommandLine> parseArgs(String cmdLineSyntax, String[] args, Options options) throws ParseException {
        boolean hasHelp = false;
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);
        if (commandLine.hasOption(help.getOpt())) {
            hasHelp = true;
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(cmdLineSyntax, options);
        }
        return Pair.make(hasHelp, commandLine);
    }

    public void stop() {
        try {
            if (thread.isInterrupted()) {
                return;
            }
            thread.interrupt();
            thread.join(3000);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        AnsiLog.info("commandListener stop");
    }

}
