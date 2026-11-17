package com.hanks.htextview.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CharacterUtils {
    public static List<CharacterDiffResult> diff(CharSequence oldText, CharSequence newText) {
        List<CharacterDiffResult> differentList = new ArrayList<>();
        Set<Integer> skip = new HashSet<>();
        for (int i = 0; i < oldText.length(); i++) {
            char c = oldText.charAt(i);
            int j = 0;
            while (true) {
                if (j < newText.length()) {
                    if (!skip.contains(Integer.valueOf(j)) && c == newText.charAt(j)) {
                        skip.add(Integer.valueOf(j));
                        CharacterDiffResult different = new CharacterDiffResult();
                        different.c = c;
                        different.fromIndex = i;
                        different.moveIndex = j;
                        differentList.add(different);
                        break;
                    }
                    j++;
                } else {
                    break;
                }
            }
        }
        return differentList;
    }

    public static int needMove(int index, List<CharacterDiffResult> differentList) {
        for (CharacterDiffResult different : differentList) {
            if (different.fromIndex == index) {
                return different.moveIndex;
            }
        }
        return -1;
    }

    public static boolean stayHere(int index, List<CharacterDiffResult> differentList) {
        for (CharacterDiffResult different : differentList) {
            if (different.moveIndex == index) {
                return true;
            }
        }
        return false;
    }

    public static float getOffset(int from, int move, float progress, float startX, float oldStartX, List<Float> gaps, List<Float> oldGaps) {
        float dist = startX;
        for (int i = 0; i < move; i++) {
            dist += gaps.get(i).floatValue();
        }
        float cur = oldStartX;
        for (int i2 = 0; i2 < from; i2++) {
            cur += oldGaps.get(i2).floatValue();
        }
        return ((dist - cur) * progress) + cur;
    }
}
