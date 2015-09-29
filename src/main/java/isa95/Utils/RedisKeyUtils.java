package isa95.Utils;

/**
 * Created by user on 29/09/15.
 */
public final class RedisKeyUtils {

    private final static String KEY_LEVEL_SEPARATOR = ":";

    /**get the root of the current key*/
    public static String getRoot (String key){
        if (key!=null) {
            int idx = key.indexOf(KEY_LEVEL_SEPARATOR);
            if (idx >=0)
                return key.substring(0, idx);
            else
                return key;
        }
        return null;
    }

    /**return a new String with a new level added */
    public static String pushLevel (String key, Object newLevel){
        if (key != null){
            return key.concat(KEY_LEVEL_SEPARATOR).concat(String.valueOf(newLevel));
        }
        return null;
    }

    /**return a new String with levels added */
    public static String pushLevels (String key, final String... levels){
        if (key != null && levels != null && levels.length > 0){
            String concatKey = key;
            for (String s : levels)
                concatKey = concatKey.concat(KEY_LEVEL_SEPARATOR).concat(s);
            return concatKey;
        }
        return key;
    }

    /**return a new String with latest level removed*/
    public static String popLevel (String key){
        int idx = key.lastIndexOf(KEY_LEVEL_SEPARATOR);
        if (idx >=0) {
            return key.substring(0, idx);
        }
        return null;
    }

}
