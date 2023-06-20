/**
 * This Java source file was generated by the Gradle 'init' task.
 */
package jackbox.editor.core.editors.jackbox2.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.abc.ScriptPack;
import com.jpexs.decompiler.flash.abc.types.ScriptInfo;
import com.jpexs.decompiler.flash.tags.DefineEditTextTag;
import com.jpexs.decompiler.flash.tags.Tag;
import com.jpexs.decompiler.flash.tags.base.TextTag;
import jackbox.editor.core.utils.SwfEditor;

import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * This class is used to edit the core (main menu) part of Jackbox Party Pack 2
 */
public class Jackbox2CoreEditor {
    SWF swfGamePicker;

    public Jackbox2CoreEditor() throws IOException, InterruptedException {
    }

    // GamePicker

    /**
     * Setup the GamePicker SWF file
     * This file can be found at The Jackbox Party Pack 2/games/PartyPack/GamePicker.swf
     *
     * @param fileGamePicker The GamePicker SWF file
     * @throws IOException          If an I/O error occurs
     * @throws InterruptedException If the thread is interrupted
     */
    public void setSwfGamePicker(InputStream fileGamePicker) throws IOException, InterruptedException {
        this.swfGamePicker = new SWF(fileGamePicker, true);
    }

    /**
     * Get the GamePicker.swf editable data
     *
     * @return The data in a json format
     */
    public String getGamePickerData() throws Exception {

        JackBox2CoreForm form = new JackBox2CoreForm();
        SwfEditor swfEditor = new SwfEditor(this.swfGamePicker);

        //Process all text tags
        form.back = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.BACK);
        form.phoneOrTablet = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.PHONE_OR_TABLET);
        form.familyMode = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.FAMILY_MODE);
        form.optionalContentFilter = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.OPTIONAL_CONTENT_FILTER);
        form.audience = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.AUDIENCE);
        form.nonPlayerCanJoin = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.NON_PLAYER_CAN_JOIN);
        form.menuItem = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.MENU_ITEM);
        form.close = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.CLOSE[0]);
        form.on = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.ON);
        form.off = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.OFF);
        form.volumeControl = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.VOLUME_CONTROL[0]);
        form.audienceMode = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.AUDIENCE_MODE[0]);
        form.audienceTimer = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.AUDIENCE_TIMER[0]);
        form.familyFriendlyFilter = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.FAMILY_FRIENDLY_FILTER[0]);
        form.fullScreenMode = swfEditor.getDefineEditTextTag(Jackbox2CoreIds.GamePicker.FULL_SCREEN_MODE[0]);

        //Get scripts
        /*List<ScriptPack> scriptPacks =
                this.swfGamePicker.getAS3Packs();
        System.out.println("scripts : " + scriptPacks.size());
        for (ScriptPack scriptPack : scriptPacks) {
            int strings = scriptPack.abc.constants.getStringCount();
            System.out.println(strings);
            for (int i = 0; i < strings; i++) {
                String string = scriptPack.abc.constants.getString(i);
                System.out.println(string);
            }
        }*/

        String json = new ObjectMapper().writeValueAsString(form);
        return json;
    }

    /**
     * Modify the GamePicker.swf file
     *
     * @param json The data in a json format
     * @return The modified file
     */
    public byte[] modifyGamePickerData(String json) throws Exception {
        JackBox2CoreForm form = new ObjectMapper().readValue(json, JackBox2CoreForm.class);
        SwfEditor swfEditor = new SwfEditor(this.swfGamePicker);

        //Process all tags
        swfEditor.setDefineEditTextTag(Jackbox2CoreIds.GamePicker.BACK, form.back);
        swfEditor.setDefineEditTextTag(Jackbox2CoreIds.GamePicker.PHONE_OR_TABLET, form.phoneOrTablet);
        swfEditor.setDefineEditTextTag(Jackbox2CoreIds.GamePicker.FAMILY_MODE, form.familyMode);
        swfEditor.setDefineEditTextTag(Jackbox2CoreIds.GamePicker.OPTIONAL_CONTENT_FILTER, form.optionalContentFilter);
        swfEditor.setDefineEditTextTag(Jackbox2CoreIds.GamePicker.AUDIENCE, form.audience);
        swfEditor.setDefineEditTextTag(Jackbox2CoreIds.GamePicker.NON_PLAYER_CAN_JOIN, form.nonPlayerCanJoin);
        swfEditor.setDefineEditTextTag(Jackbox2CoreIds.GamePicker.MENU_ITEM, form.menuItem);
        swfEditor.setDefineEditTextTags(Jackbox2CoreIds.GamePicker.CLOSE, form.close);
        swfEditor.setDefineEditTextTag(Jackbox2CoreIds.GamePicker.ON, form.on);
        swfEditor.setDefineEditTextTag(Jackbox2CoreIds.GamePicker.OFF, form.off);

        swfEditor.setDefineEditTextTags(Jackbox2CoreIds.GamePicker.VOLUME_CONTROL, form.volumeControl);
        swfEditor.setDefineEditTextTags(Jackbox2CoreIds.GamePicker.AUDIENCE_MODE, form.audienceMode);
        swfEditor.setDefineEditTextTags(Jackbox2CoreIds.GamePicker.AUDIENCE_TIMER, form.audienceTimer);
        swfEditor.setDefineEditTextTags(Jackbox2CoreIds.GamePicker.FAMILY_FRIENDLY_FILTER, form.familyFriendlyFilter);
        swfEditor.setDefineEditTextTags(Jackbox2CoreIds.GamePicker.FULL_SCREEN_MODE, form.fullScreenMode);

        //Save SWF
        return swfEditor.apply();
    }

    public byte[] getGamePickerImageSettings() throws Exception {
        SwfEditor swfEditor = new SwfEditor(this.swfGamePicker);
        return swfEditor.getDefineBitsLosslessTag(Jackbox2CoreIds.GamePicker.IMAGE_SETTINGS);
    }

    public byte[] getGamePickerImageInstructions() throws Exception {
        SwfEditor swfEditor = new SwfEditor(this.swfGamePicker);
        return swfEditor.getDefineBitsLosslessTag(Jackbox2CoreIds.GamePicker.IMAGE_INSTRUCTIONS);
    }

    public byte[] getGamePickerImageInstructions2() throws Exception {
        SwfEditor swfEditor = new SwfEditor(this.swfGamePicker);
        return swfEditor.getDefineBitsLosslessTag(Jackbox2CoreIds.GamePicker.IMAGE_INSTRUCTION2);
    }

    // GamePicker
}
