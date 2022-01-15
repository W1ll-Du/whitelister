package io.github.w1ll_du.whitelister.command;


public abstract class AAdminCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        // make sure is owner
        if (ctx.getConf().get("owner_id").equals(ctx.getAuthor().getId())) {
            handle2(ctx);
        }
    }

    protected abstract void handle2(CommandContext ctx);
}
