package io.kjm015.javadiscordbot.models;

public record CommandEvent(String commandName, String commandArguments, String sender) {}
