package net.m3tte.ego_weapons.gui.ingame;

import net.minecraft.util.text.TextFormatting;

import java.util.LinkedList;

public class DialogueEntry {

    private int timeStamp;

    private String[] lines;

    private TextFormatting formatting;

    private float size;

    public DialogueEntry(int timeStamp, String[] lines, String formatting, float size) {
        this.timeStamp = timeStamp;
        this.lines = lines;
        this.formatting = stringToFormat(formatting);
        this.size = size;
    }

    public static TextFormatting stringToFormat(String string) {

        return TextFormatting.getById(Integer.parseInt(string));
    }

    public float getSize() {
        return size;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public String[] getLines() {
        return lines;
    }

    public TextFormatting getFormatting() {
        return formatting;
    }
}
