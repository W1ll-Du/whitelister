package io.github.w1ll_du.whitelister.command;

import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx);
    String getName();

    default List<String> getAliases() {
        return List.of();
    }
}
