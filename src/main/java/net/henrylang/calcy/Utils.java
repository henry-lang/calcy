package net.henrylang.calcy;

public class Utils {
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int[] stringToGlyphs(String string) {
        var glyphs = new int[string.length()];
        for(int i = 0; i < string.length(); ++i) {
            glyphs[i] = string.charAt(i);
        }

        return glyphs;
    }

    public static int[] doubleToGlyphs(double num) {
        var chars = String.format("%.9g", num).replaceFirst("\\.?0+(e|$)", "$1").toCharArray();
        var intVersion = new int[chars.length];
        for(int i = 0; i < chars.length; i++) {
            intVersion[i] = chars[i];
        }
        return intVersion;
    }
}
